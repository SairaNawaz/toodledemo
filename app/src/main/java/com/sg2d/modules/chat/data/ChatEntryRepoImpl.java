package com.sg2d.modules.chat.data;

import android.util.Log;

import com.sg2d.app.AppDatabase;
import com.sg2d.app.utils.Constants;
import com.sg2d.modules.chat.data.local.ChatEntryDao;
import com.sg2d.modules.chat.data.local.ChatEntryTable;
import com.sg2d.modules.chat.data.remote.AddChatEntryApiResponse;
import com.sg2d.modules.chat.data.remote.ChatApiService;
import com.sg2d.modules.chat.data.remote.GetChatEntryApiResponse;
import com.sg2d.modules.chat.entities.ChatEntry;
import com.sg2d.modules.chat.entities.ChatEntrySynced;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.sg2d.app.utils.Constants.TYPE_FILE;

@Singleton
public class ChatEntryRepoImpl implements ChatEntryRepo {

    private static final String TAG = ChatEntryRepoImpl.class.getSimpleName();
    private AppDatabase appDatabase;
    private ChatApiService customerApiService;

    @Inject
    public ChatEntryRepoImpl(AppDatabase appDatabase, ChatApiService customerApiService) {
        this.appDatabase = appDatabase;
        this.customerApiService = customerApiService;
    }

    @Override
    public Single<ChatEntry> addChatEntryLocal(ChatEntry chatEntry) {
        return addToLocal(chatEntry);
    }

    @Override
    public Single<ChatEntrySynced> updateChatEntryLocal(ChatEntry oldChatEntry, ChatEntry newChatEntry) {
        appDatabase.beginTransaction();
        try {
            ChatEntryTable entryTable = appDatabase.chatEntryDao().getChatEntry(oldChatEntry.getMsg_id());
            entryTable.setMsg_id(newChatEntry.getMsg_id());
            entryTable.setBody(newChatEntry.getBody());
            entryTable.setTimestamp(newChatEntry.getTimestamp());
            entryTable.setSynced(newChatEntry.isSynced());
            entryTable.setError(newChatEntry.isError());
            appDatabase.chatEntryDao().updateChatItem(entryTable);
            appDatabase.setTransactionSuccessful();
        } finally {
            appDatabase.endTransaction();
        }
        return Single.just(new ChatEntrySynced(oldChatEntry, newChatEntry));
    }

    @Override
    public Single<ChatEntry> addChatEntryRemote(ChatEntry chatEntry) {
        return addToRemote(chatEntry);
    }

    @Override
    public Flowable<List<ChatEntry>> getChatEntry(int myId, int customerId) {
        //define your single source of truth : is it cache? database? or cloud?
        Flowable<List<ChatEntry>> local = fetchFromLocal(myId, customerId);
        Flowable<List<ChatEntry>> remote = fetchFromRemote(myId, customerId);
        return Flowable.merge(local, remote).firstElement().toFlowable();
    }


    private Single<ChatEntry> addToLocal(ChatEntry chatEntry) {
        ChatEntryTable lastId = appDatabase.chatEntryDao().getLastMsgId();
        chatEntry.setMsg_id(lastId.getMsg_id() + 1);
        Single<Long> insert = appDatabase.chatEntryDao().insert(new ChatEntryTable(chatEntry.getMsg_id(), chatEntry.getUserid(),
                chatEntry.getRecipientid(), chatEntry.getTimestamp(), chatEntry.getBody(),
                chatEntry.getStatus(), chatEntry.getType(), chatEntry.isSynced(), chatEntry.isError()));
        return insert.flatMap(result -> {
            Log.d("Sync", "Sync Chat to local");
            return Single.just(chatEntry);
        });
    }

    private Single<ChatEntry> addToRemote(ChatEntry chatEntry) {
        MultipartBody.Builder body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        body.addFormDataPart("userid", chatEntry.getUserid() + "")
                .addFormDataPart("recipientid", chatEntry.getRecipientid() + "")
                .addFormDataPart("type", chatEntry.getType());
        if (chatEntry.getType().equalsIgnoreCase(TYPE_FILE)) {
            body.addFormDataPart("body", "FAKE");
            File imageFile = new File(chatEntry.getFileBody().getTempPath());
            body.addFormDataPart("image", imageFile.getName(), RequestBody
                    .create(MediaType.parse("image/*"), imageFile));
        } else {
            body.addFormDataPart("body", chatEntry.getBody());
        }

        Single<AddChatEntryApiResponse> insert = customerApiService.addChatEntry(Constants.API_TOKEN, body.build());
        return insert.flatMap(result -> {
            Log.d("Sync", "Sync Chat to remote");
            ChatEntry newEntry = new ChatEntry(result.getMsg_id(), result.getUserid(),
                    result.getRecipientid(),
                    result.getTimestamp(), result.getBody(), result.getStatus(), result.getType(), true, false);

            return Single.just(newEntry);
        });
    }

    private Flowable<List<ChatEntry>> fetchFromLocal(int myId, int customerId) {

        Flowable<List<ChatEntryTable>> entries = appDatabase.chatEntryDao().getAll(myId, customerId);
        List<ChatEntry> data = new ArrayList<>();
        return entries.flatMap(customerEntryTables -> {

            if (!customerEntryTables.isEmpty()) {
                for (ChatEntryTable row :
                        customerEntryTables) {
                    //int id, int userid, int recipientid, String timestamp, String body, int status, String type
                    data.add(new ChatEntry(row.getMsg_id(), row.getUserid(), row.getRecipientid()
                            , row.getTimestamp(), row.getBody(), row.getStatus(), row.getType(), row.isSynced(), row.isError()));
                }
                return Flowable.just(data);
            }

            Log.d(TAG, "Returning flowable with invalid entry from local");
            return Flowable.empty();
        });

    }


    private Flowable<List<ChatEntry>> fetchFromRemote(int myId, int customer_id) {

        Log.d(TAG, "fetchFromRemote enter");
        Flowable<GetChatEntryApiResponse> getRequest = customerApiService.getChatEntry(Constants.API_TOKEN, myId, customer_id);

        return getRequest.flatMap(customerEntryApiResponse -> {
            List<ChatEntryTable> data = new ArrayList<>();
            List<ChatEntry> data1 = new ArrayList<>();
            Log.d(TAG, "received response from remote");
            List<GetChatEntryApiResponse.MessageContent> result = customerEntryApiResponse.getMsg_content();
            for (int i = 0; i < result.size(); i++) {
                //int id, int userid, int recipientid, String timestamp, String body, int status, String type
                GetChatEntryApiResponse.MessageContent row = result.get(i);
                data.add(new ChatEntryTable(row.getId(), row.getUserid(),
                        row.getRecipientid(), row.getTimestamp(), row.getBody(), row.getStatus(), row.getType(), true, false));
                data1.add(new ChatEntry(row.getId(), row.getUserid(),
                        row.getRecipientid(), row.getTimestamp(), row.getBody(), row.getStatus(), row.getType(), true, false));
            }

            appDatabase.beginTransaction();
            try {
                ChatEntryDao entryDao = appDatabase.chatEntryDao();
                //entryDao.delete();
                entryDao.insertBulk(data);
                appDatabase.setTransactionSuccessful();
            } finally {
                appDatabase.endTransaction();
            }
            Log.d(TAG, "added new entry into app database table");

            Log.d(TAG, "Sending entry from remote");

            return Flowable.just(data1);
        });
    }

}

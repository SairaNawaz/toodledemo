package com.sg2d.modules.chat.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ChatEntryDao {

    @Query("SELECT * FROM ChatEntryTable WHERE userid = :userId AND recipientid = :customerId")
    Flowable<List<ChatEntryTable>> getAllCustomerChat(int userId, int customerId);

    @Query("SELECT * FROM ChatEntryTable WHERE (userid =:userId AND recipientid =:recipientid) " +
            "OR (userid =:recipientid AND recipientid =:userId)  ORDER BY msg_id Asc")
    Flowable<List<ChatEntryTable>> getAll(int userId, int recipientid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insert(ChatEntryTable chatEntry);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulk(List<ChatEntryTable> chatEntry);

    @Query("DELETE FROM ChatEntryTable")
    void delete();

    @Delete
    void delete(ChatEntryTable chatEntry);

    @Query("SELECT * FROM ChatEntryTable WHERE msg_id =:oldMsgId")
    ChatEntryTable getChatEntry(int oldMsgId);

    @Query("SELECT * FROM ChatEntryTable ORDER BY msg_id Desc LIMIT 1")
    ChatEntryTable getLastMsgId();

    @Update
    void updateChatItem(ChatEntryTable chatEntryTable);
}

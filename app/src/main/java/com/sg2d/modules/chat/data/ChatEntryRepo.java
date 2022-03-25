package com.sg2d.modules.chat.data;


import com.sg2d.modules.chat.entities.ChatEntry;
import com.sg2d.modules.chat.entities.ChatEntrySynced;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface ChatEntryRepo {
    Single<ChatEntry> addChatEntryLocal(ChatEntry chatEntry);

    Single<ChatEntrySynced> updateChatEntryLocal(ChatEntry oldChatEntry,ChatEntry newChatEntry);

    Single<ChatEntry> addChatEntryRemote(ChatEntry chatEntry);

    Flowable<List<ChatEntry>> getChatEntry(int myId, int customerId);
}

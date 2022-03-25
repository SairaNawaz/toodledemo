package com.sg2d.modules.chat.entities;

public class ChatEntrySynced {

    public ChatEntry oldEntry;
    public ChatEntry syncedEntry;

    public ChatEntry getOldEntry() {
        return oldEntry;
    }

    public void setOldEntry(ChatEntry oldEntry) {
        this.oldEntry = oldEntry;
    }

    public ChatEntrySynced(ChatEntry oldEntry, ChatEntry syncedEntry) {
        this.oldEntry = oldEntry;
        this.syncedEntry = syncedEntry;
    }

    public ChatEntry getSyncedEntry() {
        return syncedEntry;
    }

    public void setSyncedEntry(ChatEntry syncedEntry) {
        this.syncedEntry = syncedEntry;
    }
}

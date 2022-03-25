package com.sg2d.app;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.sg2d.modules.auth.data.local.AuthEntryTable;
import com.sg2d.modules.auth.data.local.AuthJsonConverters;
import com.sg2d.modules.chat.data.local.ChatEntryDao;
import com.sg2d.modules.chat.data.local.ChatEntryTable;
import com.sg2d.modules.chat.data.local.ChatJsonConverters;
import com.sg2d.modules.customers.data.local.AuthEntryDao;
import com.sg2d.modules.customers.data.local.CustomerEntryDao;
import com.sg2d.modules.customers.data.local.CustomerEntryTable;
import com.sg2d.modules.customers.data.local.CustomerJsonConverters;


@Database(entities = {AuthEntryTable.class, CustomerEntryTable.class, ChatEntryTable.class}, version = 1)
@TypeConverters({AuthJsonConverters.class, CustomerJsonConverters.class, ChatJsonConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract AuthEntryDao authEntryDao();

    public abstract CustomerEntryDao customerEntryDao();

    public abstract ChatEntryDao chatEntryDao();
}

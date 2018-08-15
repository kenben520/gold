package com.lingxi.net.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.lingxi.net.common.util.NetSdkLog;

public class NetDefaultCache implements NetCache {
    private SQLiteHelper mSQLiteHelper;
    private long mReserveTime = 24 * 3600 * 1000L;

    protected NetDefaultCache(Context context, String dbName, String tbName,
                              int version, long reserveTime) {
        mSQLiteHelper = new SQLiteHelper(context, dbName, null, version, tbName);
        if (reserveTime > 0) {
            mReserveTime = reserveTime;
        } else if (reserveTime == 0) {
            mReserveTime = 365 * 24 * 3600 * 1000L;
        }
    }

    public static NetDefaultCache getNetDefaultCache(Context context) {
        return new NetDefaultCache(context, "simplecache.db", "defaulttable", 1, -1);
    }

    @Override
    public String get(String key, boolean needEncrypt) {
        CacheObject co = mSQLiteHelper.getCOByKey(key);
        if (co == null) {
            return null;
        } else {
            return co.value;
        }
    }

    @Override
    public String put(String key, String value, boolean needEncrypt) {
        String oldValue = null;
        CacheObject co = mSQLiteHelper.getCOByKey(key);
        if (co != null) {
            oldValue = co.value;
            mSQLiteHelper.updateCOByKey(key, value);
        } else {
            mSQLiteHelper.insertCOByKey(key, value);
        }
        return oldValue;
    }

    @Override
    public String remove(String key) {
        String oldValue = null;
        CacheObject co = mSQLiteHelper.getCOByKey(key);
        if (co != null) {
            oldValue = co.value;
            mSQLiteHelper.deleteCOByKey(key);
        }
        return oldValue;
    }

    @Override
    public long getCachedTime(String key) {
        String time = get(key + "_cache_time", false);
        if (TextUtils.isEmpty(time))
            return 0;
        long v = 0;
        try {
            v = Long.parseLong(time);
            return v;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void updateCachedTime(String key, long value) {
        put(key + "_cache_time", Long.toString(value), false);
    }

    public class SQLiteHelper extends SQLiteOpenHelper {
        public String Lock = "dblock";
        private String mTableName;

        /**
         * @param context
         * @param name
         * @param factory
         * @param version
         */
        public SQLiteHelper(Context context, String name,
                            CursorFactory factory, int version, String tbName) {
            super(context, name, factory, version);
            mTableName = tbName;
            try {
                SQLiteDatabase sqLiteDatabase = getWritableDatabase();
                onCreate(getWritableDatabase());
                sqLiteDatabase.close();
            } catch (Exception e) {
                NetSdkLog.e("SQLiteHelper", e.toString());
            }
        }

        /**
         * @param key
         * @param value
         */
        public void updateCOByKey(String key, String value) {
            synchronized (Lock) {
                SQLiteDatabase db = null;
                try {
                    db = getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("value", value);
                    values.put("cacheTime", System.currentTimeMillis());
                    db.update(mTableName, values, "key=?", new String[]{key});
                } finally {
                    if (db != null) db.close();
                }
            }
        }

        /**
         * @param key
         * @param value
         */
        public void insertCOByKey(String key, String value) {
            synchronized (Lock) {
                SQLiteDatabase db = null;
                try {
                    db = getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("key", key);
                    values.put("value", value);
                    values.put("cacheTime", System.currentTimeMillis());
                    db.insert(mTableName, null, values);
                } finally {
                    if (db != null) db.close();
                }
            }
        }

        /**
         * @param key
         */
        public void deleteCOByKey(String key) {
            synchronized (Lock) {
                SQLiteDatabase db = null;
                try {
                    db = getWritableDatabase();
                    db.delete(mTableName, "key=?", new String[]{key});
                } finally {
                    if (db != null) db.close();
                }
            }
        }

        /**
         * @param key
         * @return
         */
        public CacheObject getCOByKey(String key) {
            synchronized (Lock) {
                Cursor c = null;
                SQLiteDatabase db = null;
                try {
                    db = getReadableDatabase();
                    c = db.query(
                            mTableName, new String[]{"value", "cacheTime"}, "key=?",
                            new String[]{key}, null, null, null);
                    if (c.moveToFirst()) {
                        String value = c.getString(0);
                        long cacheTime = c.getLong(1);
                        CacheObject co = new CacheObject(key, value);
                        co.cacheTime = cacheTime;
                        return co;
                    } else {
                        return null;
                    }
                } finally {
                    if (c != null) c.close();
                    /*if (db != null) db.close();*/
                }
            }
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table if not exists " + mTableName + "("
                    + "key varchar(128) primary key,"
                    + "value varchar(4096),"
                    + "cacheTime long)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

    }

}

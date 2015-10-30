package org.team10424012.whisky;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yy on 10/30/15.
 */
public class DbOpenHelper extends SQLiteOpenHelper{
    private static String name = "whisky.db";
    private static int version = 1;

    private Context context;

    public DbOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            InputStream is = context.getAssets().open("init.sql");
            String sql = IOUtils.toString(is, "UTF-8");
            db.execSQL(sql);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

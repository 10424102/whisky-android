package org.team10424102.whisky.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by yy on 10/30/15.
 */
public class SimpleDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "main.db";
    public static final int DATABASE_VERSION = 2;
    private static final String TAG = "SimpleDatabaseHelper";
    private Context mContext;

    public SimpleDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    /**
     * 脚本 init.sql 里的分号只做分割 SQL 命令这一个用途
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            InputStream is = mContext.getAssets().open("init.sql");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            String script = "";
            while ((line = br.readLine()) != null) {
                script += line.trim();
            }
            String[] sqls = script.split(";");
            for (int i = 0; i < sqls.length; i++) {
                Log.d(TAG, "执行SQL：" + sqls[i]);
                db.execSQL(sqls[i]);
            }
            Log.d(TAG, "数据库重建完毕，版本：" + DATABASE_VERSION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            onCreate(db);
        }
    }
}

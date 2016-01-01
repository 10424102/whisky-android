package org.team10424102.whisky.components;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.team10424102.whisky.dev.IDebugData;
import org.team10424102.whisky.dev.UserListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 11/7/15.
 */
public class PersistenceService implements IDebugData {
    private static final String TAG = "PersistenceService";
    private SimpleDatabaseHelper mDatabaseHelper;
    private SQLiteDatabase db;

    public PersistenceService(Context context) {
        mDatabaseHelper = new SimpleDatabaseHelper(context);
        db = mDatabaseHelper.getWritableDatabase();
    }

    public SQLiteDatabase getSQLiteDatabase() {
        return db;
    }

    public String getToken(String phone) {
        Log.d(TAG, String.format("准备获取手机号码 %s 所对应的 token", phone));
        Cursor cursor = db.rawQuery("select * from t_token where phone = ?", new String[]{phone});
        String token = null;
        if (cursor.moveToFirst()) {
            token = cursor.getString(cursor.getColumnIndex("token"));
        }
        cursor.close();
        Log.d(TAG, "获得的token是：" + token);
        return token;
    }

    public void saveToken(String phone, String token) {
        Log.d(TAG, String.format("准备保存手机号码 %s 及其 token：%s", phone, token));
        db.beginTransaction();
        ContentValues initialValues = new ContentValues();
        initialValues.put("phone", phone); // the execution is different if _id is 2
        initialValues.put("token", token);

        int id = (int) db.insertWithOnConflict("t_token", null, initialValues,
                SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update("t_token", initialValues, "phone=?", new String[]{phone});
        }
        //db.execSQL("insert into t_token values (?, ?)", new Object[]{phone, token});
        db.setTransactionSuccessful(); // 没有这句话会导致数据存不进数据库
        db.endTransaction();
        Log.d(TAG, "保存完毕");
        showAllTokens();
    }

    private void showAllTokens() {
        Log.d(TAG, "下面是保存在数据库内的所有 token");
        Cursor cursor = db.rawQuery("select * from t_token", null);
        while (cursor.moveToNext()) {
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String token = cursor.getString(cursor.getColumnIndex("token"));
            Log.d(TAG, String.format("[phone=%s, token=%s]", phone, token));
        }
        cursor.close();
    }

    @Override
    public List<UserListItem> getAllUsers() {
        Cursor cursor = db.rawQuery("select * from t_token", null);
        List<UserListItem> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String token = cursor.getString(cursor.getColumnIndex("token"));
            UserListItem user = new UserListItem();
            user.setPhone(phone);
            user.setToken(token);
            users.add(user);
        }
        cursor.close();
        return users;
    }

    public void closeDatabase() {
        db.close();
    }
}

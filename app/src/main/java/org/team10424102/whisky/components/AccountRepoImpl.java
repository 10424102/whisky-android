package org.team10424102.whisky.components;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.team10424102.whisky.App;
import org.team10424102.whisky.models.Profile;

import java.util.List;

public class AccountRepoImpl implements AccountRepo {

    private final String TABLE_NAME = "t_user";
    private final String COLUMN_PHONE = "phone";
    private final String COLUMN_TOKEN = "token";
    private final String COLUMN_USED_COUNT = "used_count";
    private final String COLUMN_USERNAME = "username";
    private final String COLUMN_NICKNAME = "nickname";
    private final String COLUMN_SIGNATURE = "signature";
    private final String COLUMN_EMAIL = "email";
    private final String COLUMN_HIGHSCHOOL = "highschool";
    private final String COLUMN_HOMETOWN = "hometown";
    private final String COLUMN_GENDER = "gender";
    private final String COLUMN_BIRTHDAY = "birthday";
    private final String COLUMN_COLLEGE = "college";
    private final String COLUMN_ACADEMY = "academy";
    private final String COLUMN_GRADE = "grade";
    private final String COLUMN_VISIABLE = "visiable";
    private final String[] ALL_COLUMNS = new String[] {
            COLUMN_PHONE, COLUMN_TOKEN, COLUMN_USED_COUNT, COLUMN_USERNAME, COLUMN_NICKNAME,
            COLUMN_SIGNATURE, COLUMN_EMAIL, COLUMN_HIGHSCHOOL, COLUMN_HOMETOWN, COLUMN_GENDER,
            COLUMN_BIRTHDAY, COLUMN_COLLEGE, COLUMN_ACADEMY, COLUMN_GRADE, COLUMN_VISIABLE
    };

    private final SQLiteDatabase db;
    private final Context mContext;


    public AccountRepoImpl(Context context) {
        mContext = context;
        App app = (App)context.getApplicationContext();
        PersistenceService persistenceService =
                (PersistenceService)app.getComponent(PersistenceService.class);
        db = persistenceService.getSQLiteDatabase();
    }

    @Override
    public List<Account> all() {
        Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);
        while (cursor.moveToNext()) {
            BlackServerAccount account = new BlackServerAccount();
            account.setUsedCount(cursor.getInt(cursor.getColumnIndex(COLUMN_USED_COUNT)));
            account.setVisiable(cursor.getInt(cursor.getColumnIndex(COLUMN_VISIABLE)) != 0);

            String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
            String token = cursor.getString(cursor.getColumnIndex(COLUMN_TOKEN));

            PhoneTokenAuthentication auth = new PhoneTokenAuthentication(mContext, phone, token);
            account.setAuth(auth);

            Profile profile =  account.getProfile();

            profile.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            profile.setNickname(cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME)));
            profile.setSignature(cursor.getString(cursor.getColumnIndex(COLUMN_SIGNATURE)));
            profile.setHighschool(cursor.getString(cursor.getColumnIndex(COLUMN_HIGHSCHOOL)));
            profile.setHometown(cursor.getString(cursor.getColumnIndex(COLUMN_HOMETOWN)));

            // TODO: 1/1/16 to be continued







        }
        cursor.close();
        return null;
    }

    @Override
    public void save(Account account) {
    }
}

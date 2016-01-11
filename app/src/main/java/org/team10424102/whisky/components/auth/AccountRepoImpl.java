package org.team10424102.whisky.components.auth;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.team10424102.whisky.App;
import org.team10424102.whisky.components.PersistenceService;
import org.team10424102.whisky.models.LazyImage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

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
    private final String COLUMN_BIRTHDAY = "date(birthday)"; // YYYY-MM-DD
    private final String COLUMN_COLLEGE = "college";
    private final String COLUMN_ACADEMY = "academy";
    private final String COLUMN_GRADE = "grade";
    private final String COLUMN_VISIABLE = "visiable";
    private final String COLUMN_AVATAR_HASH = "avatar_hash";         // 32 chars
    private final String COLUMN_BACKGROUND_HASH = "background_HASH"; // 32 chars
    private final String[] ALL_COLUMNS = new String[]{
            COLUMN_PHONE, COLUMN_TOKEN, COLUMN_USED_COUNT, COLUMN_USERNAME, COLUMN_NICKNAME,
            COLUMN_SIGNATURE, COLUMN_EMAIL, COLUMN_HIGHSCHOOL, COLUMN_HOMETOWN, COLUMN_GENDER,
            COLUMN_BIRTHDAY, COLUMN_COLLEGE, COLUMN_ACADEMY, COLUMN_GRADE, COLUMN_VISIABLE,
            COLUMN_AVATAR_HASH, COLUMN_BACKGROUND_HASH
    };

    private final SQLiteDatabase db;

    public AccountRepoImpl(PersistenceService persistenceService) {
        db = persistenceService.getSQLiteDatabase();
    }

    @Override
    public List<Account> all() {
        Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);

        List<Account> result = new ArrayList<>(cursor.getCount());

        while (cursor.moveToNext()) {
            String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
            String token = cursor.getString(cursor.getColumnIndex(COLUMN_TOKEN));

            BlackServerAccount account = new BlackServerAccount(phone);

            account.setToken(token);

            account.setUsedCount(cursor.getInt(cursor.getColumnIndex(COLUMN_USED_COUNT)));
            account.setVisiable(cursor.getInt(cursor.getColumnIndex(COLUMN_VISIABLE)) != 0);


            int year = 0, month = 0, day = 0;
            String birthdayStr = cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY));
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = null;
            try {
                birthday = format.parse(birthdayStr);
                Calendar cal = Calendar.getInstance();
                cal.setTime(birthday);
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
            } catch (Exception e) {
                // no-op
            }

            BlackServerUserProfile profile = new BlackServerUserProfile.Builder()
                    .phone(phone)
                    .username(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)))
                    .nickname(cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME)))
                    .signature(cursor.getString(cursor.getColumnIndex(COLUMN_SIGNATURE)))
                    .highschool(cursor.getString(cursor.getColumnIndex(COLUMN_HIGHSCHOOL)))
                    .hometown(cursor.getString(cursor.getColumnIndex(COLUMN_HOMETOWN)))
                    .gender(cursor.getInt(cursor.getColumnIndex(COLUMN_GENDER)))
                    .birthday(year, month, day)
                    .college(cursor.getString(cursor.getColumnIndex(COLUMN_COLLEGE)))
                    .academy(cursor.getString(cursor.getColumnIndex(COLUMN_ACADEMY)))
                    .grade(cursor.getString(cursor.getColumnIndex(COLUMN_ACADEMY)))
                    .avatar(null, cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR_HASH)))
                    .background(null, cursor.getString(cursor.getColumnIndex(COLUMN_BACKGROUND_HASH)))
                    .build();

            account.setProfile(profile);

            result.add(account);
        }
        cursor.close();
        return result;
    }

    @Override
    public void save(Account account) {
        Timber.e("saving account not implemented");
    }

    @Override
    public Account findByIdentity(AccountIdentity identity) {
        return null;
    }
}

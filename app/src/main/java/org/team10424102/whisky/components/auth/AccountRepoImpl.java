package org.team10424102.whisky.components.auth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.team10424102.whisky.App;
import org.team10424102.whisky.components.PersistenceService;
import org.team10424102.whisky.models.LazyImage;
import org.team10424102.whisky.models.Profile;
import org.team10424102.whisky.models.enums.Gender;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private final String[] ALL_COLUMNS = new String[] {
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
            BlackServerAccount account = new BlackServerAccount();
            account.setUsedCount(cursor.getInt(cursor.getColumnIndex(COLUMN_USED_COUNT)));
            account.setVisiable(cursor.getInt(cursor.getColumnIndex(COLUMN_VISIABLE)) != 0);

            String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
            String token = cursor.getString(cursor.getColumnIndex(COLUMN_TOKEN));

            PhoneTokenAuthentication auth = new PhoneTokenAuthentication(phone, token);
            account.setAuth(auth);

            Profile profile =  account.getProfile();

            profile.setPhone(phone);
            profile.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            profile.setNickname(cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME)));
            profile.setSignature(cursor.getString(cursor.getColumnIndex(COLUMN_SIGNATURE)));
            profile.setHighschool(cursor.getString(cursor.getColumnIndex(COLUMN_HIGHSCHOOL)));
            profile.setHometown(cursor.getString(cursor.getColumnIndex(COLUMN_HOMETOWN)));

            String genderStr = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER));
            Gender gender = Gender.UNKNOWN;
            try {
                gender = Gender.valueOf(genderStr);
            }catch (Exception e){
                // no-op
            }
            profile.setGender(gender);

            String birthdayStr = cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY));
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = null;
            try {
                birthday = format.parse(birthdayStr);
            } catch (Exception e) {
                // no-op
            }
            profile.setBirthday(birthday);

            profile.setCollege(cursor.getString(cursor.getColumnIndex(COLUMN_COLLEGE)));
            profile.setAcademy(cursor.getString(cursor.getColumnIndex(COLUMN_ACADEMY)));
            profile.setGrade(cursor.getString(cursor.getColumnIndex(COLUMN_ACADEMY)));

            String avatarHash = cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR_HASH));
            LazyImage avatar = new LazyImage(null, avatarHash, App.DEFAULT_AVATAR);
            profile.setAvatar(avatar);
            String backgroundHash = cursor.getString(cursor.getColumnIndex(COLUMN_BACKGROUND_HASH));
            LazyImage background = new LazyImage(null, backgroundHash, App.DEFAULT_BACKGROUND);
            profile.setBackground(background);

            result.add(account);
        }
        cursor.close();
        return result;
    }

    @Override
    public void save(Account account) {
        Timber.i("未实现保存账户功能");
    }
}

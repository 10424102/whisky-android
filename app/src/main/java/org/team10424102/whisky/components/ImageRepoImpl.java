package org.team10424102.whisky.components;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class ImageRepoImpl implements ImageRepo {

    private final String TABLE_NAME = "t_image";
    private final String COLUMN_DATA = "data";
    private final String COLUMN_HASH = "hash";
    private final String COLUMN_HITS = "hits";

    private final SQLiteDatabase db;

    public ImageRepoImpl(PersistenceService persistenceService) {
        db = persistenceService.getSQLiteDatabase();
    }

    @Override
    public Bitmap getImage(String hash) {
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_DATA, COLUMN_HASH, COLUMN_HITS},
                "hash=?", new String[]{hash}, null, null, null);
        if (!cursor.moveToNext()) return null;
        byte[] data = cursor.getBlob(cursor.getColumnIndex(COLUMN_DATA));
        int hits = cursor.getInt(cursor.getColumnIndex(COLUMN_HITS)) + 1;
        ContentValues values = new ContentValues();
        values.put(COLUMN_HITS, hits);
        db.insert(TABLE_NAME, null, values);
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    @Override
    public void save(String hash, Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HASH, hash);
        values.put(COLUMN_DATA, data);
        values.put(COLUMN_HITS, 0);
        db.insert(TABLE_NAME, null, values);
    }
}

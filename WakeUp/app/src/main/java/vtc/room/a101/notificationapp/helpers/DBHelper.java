package vtc.room.a101.notificationapp.helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vtc.room.a101.notificationapp.models.NotificationModel;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "notifications.db";

    private static final String TABLE_NAME = "usernotification";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_ISTURNED = "isturned";
    private static final String CREATE_COMMAND =
            "CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY, "
                    + COLUMN_NAME + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_TIME
                    + " TEXT, " + COLUMN_IMAGE + " INTEGER, " + COLUMN_ISTURNED + " INTEGER)";
    private static final String DROP_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static DBHelper db = null;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DBHelper getInstance(Context context) {
        if (db == null) {
            db = new DBHelper(context);
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_COMMAND);
        onCreate(sqLiteDatabase);
    }

    public void insertItem(NotificationModel model) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, model.getName());
        values.put(COLUMN_DATE, model.getDate());
        values.put(COLUMN_TIME, model.getTime());
        values.put(COLUMN_IMAGE, model.getImage());
        values.put(COLUMN_ISTURNED, model.isTurned());
        db.insert(TABLE_NAME, null, values);
    }

    public void removeItem(String name) {
        final SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME, " name = " + "'" + name + "'", null);
    }

    public NotificationModel getItem(int id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final String query = "SELECT * FROM " + TABLE_NAME;
        @SuppressLint("Recycle")
        final Cursor cursor = db.rawQuery(query, null);
        if (cursor.isBeforeFirst()) {
            cursor.moveToFirst();
        }
        cursor.move(id);
        final String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        final String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
        final String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
        final int image = cursor.getInt(cursor.getColumnIndex(COLUMN_IMAGE));
        final int isTurned = cursor.getInt(cursor.getColumnIndex(COLUMN_ISTURNED));
        return new NotificationModel(name, date, time, image, isTurned);
    }

    public int getItemsCount() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        final SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        final Cursor cursor = db.rawQuery(query, null);
        final int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void removeAllItems() {
        final SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

}

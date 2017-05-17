package com.ium.baratheon.progetto_ium;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import android.app.Activity;



/**
 * Created by Tommaso on 20/04/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    // Versione Database
    private static final int DATABASE_VERSION = 1;
    // Nome Database
    private static final String DATABASE_NAME = "CourtDB";
    // Nomi tabelle
    private static final String COURT_TABLE_NAME = "court";
    private static final String COURT_COLUMN_NAME = "name";
    private static final String COURT_COLUMN_MAIL = "mail";
    private static final String COURT_COLUMN_ID = "_id";
    private static final String COURT_COLUMN_PHONE_NUMBER = "phoneNumber";
    private static final String COURT_COLUMN_BEGIN = "begin";
    private static final String COURT_COLUMN_END = "end";
    private static final String COURT_COLUMN_FIELD_NUMBER = "fieldNumber";
    private static final String COURT_COLUMN_PRICE = "price";
    private static final String COURT_COLUMN_COURT_PIC = "courtPic";


    private static final String USER_TABLE_NAME = "user";
    private static final String USER_COLUMN_USERNAME = "username";
    private static final String USER_COLUMN_PASSWORD = "password";
    private static final String USER_COLUMN_NAME = "name";
    private static final String USER_COLUMN_SURNAME = "surname";
    private static final String USER_COLUMN_EMAIL = "email";
    private static final String USER_COLUMN_AGE = "age";
    private static final String USER_COLUMN_PROFILE_PIC = "profilePic";
    private static final String USER_COLUMN_FAVORITES = "favorites";
    private static final String USER_COLUMN_RESERVATION = "reservation";


    public static final String RESERVATION_TABLE_NAME = "reservation";
    private static final String RESERVATION_COLUMN_DAY = "day";
    private static final String RESERVATION_COLUMN_BEGIN = "begin";
    private static final String RESERVATION_COLUMN_END = "end";
    private static final String RESERVATION_COLUMN_COURT = "court";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    private static final String create_table_court
            = "CREATE TABLE " + COURT_TABLE_NAME + "("
                + COURT_COLUMN_NAME + " TEXT ,"
                + COURT_COLUMN_MAIL + " TEXT,"
                + COURT_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COURT_COLUMN_PHONE_NUMBER + " INTEGER,"
                + COURT_COLUMN_BEGIN + " TIME,"
                + COURT_COLUMN_END + " TIME,"
                + COURT_COLUMN_FIELD_NUMBER + " INTEGER,"
                + COURT_COLUMN_PRICE + " INTEGER"
                + COURT_COLUMN_COURT_PIC + " BLOB" +")"; //BLOB?

    private static final String create_table_user
            = "CREATE TABLE " + USER_TABLE_NAME + "("
                + USER_COLUMN_USERNAME + " TEXT PRIMARY KEY,"
                + USER_COLUMN_PASSWORD + " TEXT,"
                + USER_COLUMN_NAME + " TEXT,"
                + USER_COLUMN_SURNAME + " TEXT,"
                + USER_COLUMN_EMAIL + " TEXT,"
                + USER_COLUMN_AGE + " INTEGER,"
                + USER_COLUMN_PROFILE_PIC + " BLOB,"
                + USER_COLUMN_RESERVATION + " TEXT,"
                + USER_COLUMN_RESERVATION + " TEXT" + ")";

    private static final String create_table_reservation
            = "CREATE TABLE" + RESERVATION_TABLE_NAME + "("
                + RESERVATION_COLUMN_DAY + "DATE,"
                + RESERVATION_COLUMN_BEGIN + "TIME,"
                + RESERVATION_COLUMN_END + "TIME,"
                + RESERVATION_COLUMN_COURT + "INTEGER," + ")";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table_court);
        db.execSQL(create_table_user);
        db.execSQL(create_table_reservation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Elimina tabelle vecchie se esistono
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + COURT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RESERVATION_TABLE_NAME);
        // Ricrea le tabelle
        onCreate(db);
    }

    public void insertCourt(Court court) {
        ContentValues values = new ContentValues();
        values.put(COURT_COLUMN_NAME, court.getName());
        values.put(COURT_COLUMN_MAIL, court.getMail());
        values.put(COURT_COLUMN_ID, court.getID());
        values.put(COURT_COLUMN_PHONE_NUMBER, court.getPhoneNumber());
        values.put(COURT_COLUMN_BEGIN, court.getBegin());
        values.put(COURT_COLUMN_END, court.getEnd());
        values.put(COURT_COLUMN_FIELD_NUMBER, court.getFieldNumber());
        values.put(COURT_COLUMN_PRICE, court.getPrice());
        //values.put(COURT_COLUMN_COURT_PIC, convertBitmap(court.getCourt());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(COURT_TABLE_NAME, null, values);
        db.close();
    }

    public void insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_USERNAME, user.getUsername());
        values.put(USER_COLUMN_PASSWORD, user.getPassword());
        values.put(USER_TABLE_NAME, user.getName());
        values.put(USER_COLUMN_SURNAME, user.getSurname());
        values.put(USER_COLUMN_EMAIL, user.getEmail());
        values.put(USER_COLUMN_AGE, user.getAge());
        //values.put(USER_COLUMN_PROFILE_PIC, convertBitmap(user.getProfilePic()));
        //values.put(USER_COLUMN_FAVORITES, user.getFavorites());
        //values.put(USER_COLUMN_RESERVATION, user.getReservation());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(USER_TABLE_NAME, null, values);
        db.close();
    }

    public Court selectCourt(int ID) {
        String query = "Select * FROM " + COURT_TABLE_NAME + " WHERE " + COURT_COLUMN_ID + " =  \"" + ID + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Court court = new Court();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();

            court.setName(cursor.getString(0));
            court.setMail(cursor.getString(1));
            court.setID(Integer.parseInt(cursor.getString(2)));
            court.setPhoneNumber(cursor.getString(3));
            court.setBegin(Integer.parseInt(cursor.getString(4)));
            court.setEnd(Integer.parseInt(cursor.getString(5)));
            court.setFieldNumber(Integer.parseInt(cursor.getString(6)));
            court.setPrice(Integer.parseInt(cursor.getString(7)));
            db.close();

        } else {
            court = null;
        }
        db.close();

        return court;
    }

    public Cursor getCourt(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + COURT_TABLE_NAME + " WHERE " +
                COURT_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
        return res;
    }

    public Cursor getAllCourts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + COURT_TABLE_NAME, null );
        return res;
    }

    public boolean updateCourt(Court court) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURT_COLUMN_NAME, court.getName());
        contentValues.put(COURT_COLUMN_MAIL, court.getMail());
        contentValues.put(COURT_COLUMN_ID, court.getID());
        contentValues.put(COURT_COLUMN_PHONE_NUMBER, court.getPhoneNumber());
        contentValues.put(COURT_COLUMN_BEGIN, court.getBegin());
        contentValues.put(COURT_COLUMN_END, court.getEnd());
        contentValues.put(COURT_COLUMN_FIELD_NUMBER, court.getFieldNumber());
        contentValues.put(COURT_COLUMN_PRICE, court.getPrice());
        db.update(COURT_TABLE_NAME, contentValues, COURT_COLUMN_ID + " = ? ", new String[] { Integer.toString(court.getID()) } );
        return true;
    }

    public Integer deleteCourt(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(COURT_TABLE_NAME,
                COURT_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

/*
    public boolean dropCourt(int ID) {
        boolean result = false;
        String query = "Select * FROM " + COURT_TABLE_NAME + " WHERE " + "id" + " =  \"" + ID + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Court court = new Court();

        if (cursor.moveToFirst()) {
            court.setID(Integer.parseInt(cursor.getString(3)));
            db.delete(COURT_TABLE_NAME, ID + " = ?",
                    new String[]{String.valueOf(court.getID())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
*/
/*
    //Converte bitmap in byte array
    byte[] convertBitmap(Bitmap img) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG,0 /*ignored for PNG*//*,bos);
        byte[] bitmapdata = bos.toByteArray();
        return bitmapdata;
    }
/*
    //Store
    void storeImgDB() {
        SQLiteDatabase db = this.openOrCreateDatabase("imagedatabase", this.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS imagetable(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + "image BLOB"+ ");");
        ContentValues values = new ContentValues();
        values.put("image", bitmapdata);
        long row_id = db.insert("imagetable", null, values);
    }

    //Retrieve
    Bitmap retrieveImg(){
        Cursor cursor = db.query("imagetable", new String[]{"image"},null, null, null, null, null);
        System.out.println("—–getcolumn count" + cursor.moveToFirst());

        //get it as a ByteArray
        byte[] mybyte = cursor.getBlob(0);

        //the cursor is not needed anymore
        cursor.close();

        //convert it back to an image
        ByteArrayInputStream imageStream = new ByteArrayInputStream(mybyte);
        Bitmap img = BitmapFactory.decodeStream(imageStream);
        ((ImageView) findViewById(R.id.view_image)).setImageBitmap(img);

        return img;
    }
*/

    int boolToInt(boolean value){
        if(value){
            return 0;
        }
        else return 1;
    }

}

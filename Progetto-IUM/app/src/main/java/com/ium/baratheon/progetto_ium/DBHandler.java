package com.ium.baratheon.progetto_ium;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 *
 * Created by Tommaso on 20/04/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    // Versione Database
    private static final int DATABASE_VERSION = 1;
    // Nome Database
    private static final String DATABASE_NAME = "CourtDB";

    //Nome tabella Court
    private static final String COURT_TABLE_NAME = "court";
    //Nomi colonne Court
    private static final String COURT_COLUMN_NAME = "name";
    private static final String COURT_COLUMN_MAIL = "mail";
    private static final String COURT_COLUMN_ID = "_id";
    private static final String COURT_COLUMN_PHONE_NUMBER = "phoneNumber";
    private static final String COURT_COLUMN_BEGIN = "begin";
    private static final String COURT_COLUMN_END = "end";
    private static final String COURT_COLUMN_FIELD_NUMBER = "fieldNumber";
    private static final String COURT_COLUMN_PRICE = "price";
    private static final String COURT_COLUMN_COURT_PIC = "courtPic";

    //Nome tabella User
    private static final String USER_TABLE_NAME = "user";
    //Nomi colonne User
    private static final String USER_COLUMN_USERNAME = "username";
    private static final String USER_COLUMN_PASSWORD = "password";
    private static final String USER_COLUMN_NAME = "name";
    private static final String USER_COLUMN_SURNAME = "surname";
    private static final String USER_COLUMN_EMAIL = "email";
    private static final String USER_COLUMN_AGE = "age";
    private static final String USER_COLUMN_PROFILE_PIC = "profilePic";

    //Nome tabella Reservation
    private static final String RESERVATION_TABLE_NAME = "reservation";
    //Nome colonne Reservation
    private static final String RESERVATION_COLUMN_ID = "_id";
    private static final String RESERVATION_COLUMN_DAY = "day";
    private static final String RESERVATION_COLUMN_BEGIN = "begin";
    private static final String RESERVATION_COLUMN_END = "end";
    private static final String RESERVATION_COLUMN_COURT = "court";
    private static final String RESERVATION_COLUMN_USER = "user";

    //Nome tabella Favorites
    private static final String FAVORITES_TABLE_NAME = "favorites";
    //Nome colonne Favorites
    private static final String FAVORITES_COLUMN_USER = "user";
    private static final String FAVORITES_COLUMN_COURT = "court";

    private static DBHandler db;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this;
    }

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        db = this;
    }

    //Stringa creazione tabella Court
    private static final String create_table_court
            = "CREATE TABLE " + COURT_TABLE_NAME + "("
            + COURT_COLUMN_NAME + " TEXT,"
            + COURT_COLUMN_MAIL + " TEXT,"
            + COURT_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COURT_COLUMN_PHONE_NUMBER + " TEXT,"
            + COURT_COLUMN_BEGIN + " INTEGER,"
            + COURT_COLUMN_END + " INTEGER,"
            + COURT_COLUMN_FIELD_NUMBER + " INTEGER,"
            + COURT_COLUMN_PRICE + " INTEGER, "
            + COURT_COLUMN_COURT_PIC + " BLOB);"; //BLOB?

    //Stringa inserimento di default Court
    private static final String insert_default_courts =
            "INSERT INTO " + COURT_TABLE_NAME +
            " VALUES ('Pincopallino', 'pincopallino@yahoo.it', 1, '0123456789', 16, 23, 6, 35, null),\n" +
            "('Sempronio', 'sempronio@yahoo.it', 2, '0123456789', 15, 22, 2, 40, null),\n" +
            "('Caio', 'caio@yahoo.it', 3, '0123456789', 16, 24, 4, 25, null),\n" +
            "('Gnegnegnegne', 'gnegnegnegne@yahoo.it', 4, '0123456789', 17, 24, 3, 30, null),\n" +
            "('Lalalalala', 'lalalalala@yahoo.it', 5, '0123456789', 16, 23, 2, 30, null);";

    //Stringa creazione tabella User
    private static final String create_table_user
            = "CREATE TABLE " + USER_TABLE_NAME + "("
            + USER_COLUMN_USERNAME + " TEXT PRIMARY KEY,"
            + USER_COLUMN_PASSWORD + " TEXT,"
            + USER_COLUMN_NAME + " TEXT,"
            + USER_COLUMN_SURNAME + " TEXT,"
            + USER_COLUMN_EMAIL + " TEXT,"
            + USER_COLUMN_AGE + " INTEGER,"
            + USER_COLUMN_PROFILE_PIC + " BLOB)";

    //Stringa inserimento di default User
    private static final String insert_default_users =
            "INSERT INTO " + USER_TABLE_NAME +
            " VALUES ('admin', 'admin', 'Admin', 'Admin', 'admin@admin.it', 22, null),\n" +
            "('admin1', 'admin1', 'Admin1', 'Admin1', 'admin1@admin.it', 21, null),\n" +
            "('admin2', 'admin2', 'Admin2', 'Admin2', 'admin2@admin.it', 22, null),\n" +
            "('admin3', 'admin3', 'Admin3', 'Admin3', 'admin3@admin.it', 23, null);\n";

    //Stringa creazione tabella Reservation
    private static final String create_table_reservation
            = "CREATE TABLE " + RESERVATION_TABLE_NAME + "( "
            + RESERVATION_COLUMN_ID + " INTEGER PRIMARY KEY, "
            + RESERVATION_COLUMN_DAY + " TEXT, "
            + RESERVATION_COLUMN_BEGIN + " INTEGER, "
            + RESERVATION_COLUMN_END + " INTEGER, "
            + RESERVATION_COLUMN_COURT + " INTEGER, "
            + RESERVATION_COLUMN_USER + " TEXT)";

    //Stringa inserimento di default Reservation
    private static final String insert_default_reservations =
            "INSERT INTO " + RESERVATION_TABLE_NAME +
            " VALUES (1, '22-06-2017', 18, 19, 1, 'admin'),\n" +
            "(2, '10-06-2017', 19, 20, 2, 'admin'),\n" +
            "(3, '25-05-2017', 20, 21, 3, 'admin'),\n" +
            "(4, '12-05-2017', 17, 18, 4, 'admin'),\n" +
            "(5, '02-05-2017', 21, 22, 5, 'admin');";

    //Stringa creazione tabella Favorites
    private static final String create_table_favorites
            = "CREATE TABLE " + FAVORITES_TABLE_NAME + "("
            + FAVORITES_COLUMN_USER + " TEXT,"
            + FAVORITES_COLUMN_COURT + " INTEGER,"
            + " UNIQUE(" + FAVORITES_COLUMN_USER + ", " + FAVORITES_COLUMN_COURT + "))";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table_court);
        db.execSQL(insert_default_courts);
        db.execSQL(create_table_user);
        db.execSQL(insert_default_users);
        db.execSQL(create_table_reservation);
        db.execSQL(insert_default_reservations);
        db.execSQL(create_table_favorites);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Elimina tabelle vecchie se esistono
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + COURT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RESERVATION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITES_TABLE_NAME);
        // Ricrea le tabelle
        onCreate(db);
    }

    //Inserisce un Court nella tabella Court
    public void insertCourt(Court court) {
        ContentValues values = new ContentValues();
        values.put(COURT_COLUMN_NAME, court.getName());
        values.put(COURT_COLUMN_MAIL, court.getMail());
        values.put(COURT_COLUMN_ID, court.getID());

        //System.out.print("\n\n\n\n\n\n\n\n\n\n\n" + court.getID() + "\n\n\n\n\n\n\n\n\n\n\n") ;
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

    //Inserisce uno User nella tabella User
    public void insertUser(User user) {
        ContentValues values = new ContentValues();

        values.put(USER_COLUMN_USERNAME, user.getUsername());
        values.put(USER_COLUMN_PASSWORD, user.getPassword());
        values.put(USER_COLUMN_NAME, user.getName());
        values.put(USER_COLUMN_SURNAME, user.getSurname());
        values.put(USER_COLUMN_EMAIL, user.getEmail());
        values.put(USER_COLUMN_AGE, user.getAge());

        byte[] bArray = null;

        if(user.getProfilePic()!=null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            user.getProfilePic().compress(Bitmap.CompressFormat.PNG, 100, bos);
            bArray = bos.toByteArray();
        }

        values.put(USER_COLUMN_PROFILE_PIC, bArray);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(USER_TABLE_NAME, null, values);
        db.close();
    }

    //Inserisce una Reservation nella tabella Reservation
    public void insertReservation(Reservation reservation) {
        long millis;
        ContentValues values = new ContentValues();

        //values.put(RESERVATION_COLUMN_BEGIN, reservation.getBegin();
        values.put(RESERVATION_COLUMN_END, reservation.getEnd());
        values.put(RESERVATION_COLUMN_COURT, reservation.getCourt().getID());
        values.put(RESERVATION_COLUMN_DAY, reservation.getDay().getTimeInMillis()); //setTimeInMillis(long millis)

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(RESERVATION_TABLE_NAME, null, values);
        db.close();
    }

    //Estrae dalla tabella User la righa avente Username -> username
    public User selectUser(String username) {
        String query = "Select * FROM " + USER_TABLE_NAME + " WHERE " + USER_COLUMN_USERNAME + " =  \"" + username + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        User user = new User();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();

            user.setUsername(cursor.getString(0));
            user.setPassword(cursor.getString(1));
            user.setName(cursor.getString(2));
            user.setSurname(cursor.getString(3));
            user.setEmail(cursor.getString(4));
            user.setAge(Integer.parseInt(cursor.getString(5)));
            /*user.setProfilePic(cursor.getString(6));
            user.setReservation(cursor.getString(7));
            user.setFavorites(cursor.getString(8));*/

            db.close();

        } else {
            user = null;
        }
        db.close();

        return user;
    }

    //Estrae dalla tablla Court la riga con id -> ID e crea un Court corrispondente
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

    //Estrae dalla tabella Court la riga con id -> coutID e crea una Reservation corrispondente
    public Reservation selectReservation(int courtId) {
        String query = "Select * FROM " + RESERVATION_TABLE_NAME + " WHERE " + RESERVATION_COLUMN_COURT + " =  \"" + courtId + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Reservation reservation = new Reservation();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();

            reservation.setBegin(Integer.parseInt(cursor.getString(0)));
            reservation.setEnd(Integer.parseInt(cursor.getString(1)));
            //reservation.setDay(cursor.getString(2));
            reservation.setCourt(reservation.reservationList.get(Integer.parseInt(cursor.getString(3))).getCourt());

            db.close();

        } else {
            reservation = null;
        }
        db.close();

        return reservation;
    }

    /*
    //Estrae dalla tabella Username la riga con Username -> username e restituisce un Cursor che la contiene
    public Cursor getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " +
                USER_COLUMN_USERNAME + " = ? ", new String[]{username});
        return res;
    }

    //Estrae dalla tabella Court la riga con ID -> id e restituisce un Cursor che la contiene
    public Cursor getCourt(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + COURT_TABLE_NAME + " WHERE " +
                COURT_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
        return res;
    }

    //Estrae dalla tabella Reservation la riga con Court -> courtId e restituisce un Cursor che la contiene
    public Cursor getReservation(int courtId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + RESERVATION_TABLE_NAME + " WHERE " +
                RESERVATION_COLUMN_COURT + " = ? ", new String[]{Integer.toString(courtId)});
        return res;
    }
    */


    //Restituisce un Cursor contenente tutte le righe dell tabella User
    /*public Cursor getAllUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + USER_TABLE_NAME, null );
        return res;
    }*/

    public List<User> getAllUser(){
        List<User> userList = new ArrayList<>();
        User user = new User();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + USER_TABLE_NAME, null );
        if (res.moveToFirst()) {
            res.moveToFirst();

            user.setUsername(res.getString(0));
            user.setPassword(res.getString(1));
            user.setName(res.getString(2));
            user.setSurname(res.getString(3));
            user.setEmail(res.getString(4));
            user.setAge(Integer.parseInt(res.getString(5)));
            /*user.setProfilePic(cursor.getString(6));
            user.setReservation(cursor.getString(7));
            user.setFavorites(cursor.getString(8));*/
            userList.add(user);

            db.close();

        } else {
            user = null;
        }
        //System.out.println(res.getCount());
        return userList;
    }

    public List<Reservation> getUserReservation(String username){
        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation = new Reservation();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + RESERVATION_TABLE_NAME, null );
        if (res.moveToFirst()) {
            res.moveToFirst();

            //reservation.setDay(res.getString(0));
            reservation.setEnd(Integer.parseInt(res.getString(1)));
            reservation.setBegin(Integer.parseInt(res.getString(2)));
            //reservation.setCourt(res.getString(3));

            reservationList.add(reservation);

            db.close();

        } else {
            reservation = null;
        }
        //System.out.println(res.getCount());
        return reservationList;
    }

    //Restituisce un Cursor contenente tutte le righe dell tabella Court
    public Cursor getAllCourt() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + COURT_TABLE_NAME, null );
        return res;
    }

    public Cursor getAllReservation(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + RESERVATION_TABLE_NAME, null );
        return res;
    }

    //Aggiorna la righa del db corrispondente allo User in ingresso
    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_COLUMN_NAME, user.getName());
        contentValues.put(USER_COLUMN_PASSWORD, user.getPassword());
        contentValues.put(USER_COLUMN_NAME, user.getName());
        contentValues.put(USER_COLUMN_SURNAME, user.getSurname());
        contentValues.put(USER_COLUMN_EMAIL, user.getEmail());
        contentValues.put(USER_COLUMN_AGE, user.getAge());
        /*contentValues.put(USER_COLUMN_PROFILE_PIC, user.getProfilePic());
        contentValues.put(USER_COLUMN_RESERVATION, user.getReservation());
        contentValues.put(USER_COLUMN_FAVORITES, user.getFavorites());*/

        db.update(USER_TABLE_NAME, contentValues, USER_COLUMN_USERNAME + " = ? ", new String[] { user.getUsername() } );
        return true;
    }

    //Aggiorna la righa del db corrispondente al Court in ingresso
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

    //Elimina dal db il Court con ID -> id
    public Integer deleteCourt(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(COURT_TABLE_NAME, COURT_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) });
    }

    //Elimina dal db lo User con username -> id
    public Integer deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USER_TABLE_NAME, USER_COLUMN_USERNAME + " = ? ", new String[] { username });
    }

    //Elimina dal db la Reservation con ID -> id
    public Integer deleteReservation(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(RESERVATION_TABLE_NAME, COURT_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) });
    }

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

    public User authenticate(String username, String password){
        User user = User.getUser(username, password);
        if(user == null) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " +
                            USER_COLUMN_USERNAME + "=? AND " + USER_COLUMN_PASSWORD + " =?",
                    new String[]{username, password});

            if (!c.moveToNext()) {
                return null;
            }

            byte[] byteArray = c.getBlob(6);
            Bitmap bm;
            if (byteArray != null) {
                bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            } else {
                bm = null;
            }

            Cursor c1 = db.rawQuery("SELECT " + RESERVATION_COLUMN_ID + " FROM " + RESERVATION_TABLE_NAME + " WHERE " +
                    RESERVATION_COLUMN_USER + "=?", new String[]{username});

            List<Integer> reservationList = new ArrayList<>();

            while (c1.moveToNext()) {
                reservationList.add(c1.getInt(0));
            }

            c1 = db.rawQuery("SELECT " + FAVORITES_COLUMN_COURT + " FROM " + FAVORITES_TABLE_NAME + " WHERE " +
                    FAVORITES_COLUMN_USER + "=?", new String[]{username});

            List<Integer> favoritesList = new ArrayList<>();

            while (c1.moveToNext()) {
                favoritesList.add(c1.getInt(0));
            }

            user = new User(c.getString(0), c.getString(1), c.getString(2),
                    c.getString(3), c.getString(4), c.getInt(5), bm, reservationList, favoritesList);

            c.close();
            c1.close();
        }

        return user;
    }

    public void setAllCourts(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + COURT_TABLE_NAME, null );

        while(res.moveToNext()){
            new Court(res.getString(0), res.getString(1), res.getInt(2), res.getString(3),
                    res.getInt(4), res.getInt(5), res.getInt(6), res.getInt(7));
        }
    }

    void setAllReservations(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + RESERVATION_TABLE_NAME, null );

        while(res.moveToNext()){
            Court c = Court.getCourt(res.getInt(4));
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALIAN);
            try {
                cal.setTime(sdf.parse(res.getString(1)));
            }
            catch(ParseException e){
                e.printStackTrace();
            }
            new Reservation(res.getInt(0), cal, res.getInt(2), res.getInt(3), c, true);
        }

        res.close();
    }

    public void deleteFavorite(String username, Integer id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =  db.rawQuery( "DELETE FROM " + FAVORITES_TABLE_NAME +
                " WHERE " + FAVORITES_COLUMN_USER + "=? AND " + FAVORITES_COLUMN_COURT + "=?", new String[]{username, id.toString()});
    }

    public void insertFavorite(String username, Integer id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =  db.rawQuery( "INSERT INTO " + FAVORITES_TABLE_NAME +
                " VALUES(?,?)", new String[]{username, id.toString()});
    }

    List<Reservation> retrieveReservation(User u, List<Integer> deletedItems){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Reservation> list = new ArrayList<>();

        String query = "SELECT * FROM " + RESERVATION_TABLE_NAME +
                " WHERE " + RESERVATION_COLUMN_ID + " IN ( ";

        for(Integer i: deletedItems){
            query += i.toString() + ", ";
        }


        query = query.substring(0, query.length() - 2) + " ) AND " + RESERVATION_COLUMN_USER + "=?";

        Cursor c =  db.rawQuery( query, new String[]{u.getUsername()});

        while(c.moveToNext()){
            Court court = Court.getCourt(c.getInt(4));
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALIAN);
            try {
                cal.setTime(sdf.parse(c.getString(1)));
            }
            catch(ParseException e){
                cal.setTime(new Date());
            }
            list.add(new Reservation(c.getInt(0), cal, c.getInt(2) , c.getInt(3), court, false));
        }

        c.close();

        return list;
    }

    void deleteReservations(List<Integer> deletedItems){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "DELETE FROM " + RESERVATION_TABLE_NAME +
                " WHERE " + RESERVATION_COLUMN_ID + " IN ( ";

        for(Integer i: deletedItems){
            query += i.toString() + ", ";
        }

        query = query.substring(0, query.length() - 2) + " )";

        Cursor c =  db.rawQuery( query, new String[]{});
        c.close();
    }

    Boolean lookForReservation(Reservation r){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + RESERVATION_TABLE_NAME +
                " WHERE " + RESERVATION_COLUMN_DAY + " = " + r.getDay().get(Calendar.DAY_OF_MONTH) +
                "-" + r.getDay().get(Calendar.MONTH) + "-" + r.getDay().get(Calendar.YEAR) +
                " AND " + RESERVATION_COLUMN_COURT + " = " + r.getCourt().getID() +
                " AND " + RESERVATION_COLUMN_BEGIN + " = " + r.getBegin() +
                " AND " + RESERVATION_COLUMN_END + " = " + r.getEnd() + ";";

        Cursor c =  db.rawQuery( query, new String[]{});
        boolean check = c.moveToNext();
        c.close();
        return check;
    }

    public static DBHandler getInstance(){
        return db;
    }
}

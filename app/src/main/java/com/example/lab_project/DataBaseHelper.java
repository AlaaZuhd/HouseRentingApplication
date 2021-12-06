package com.example.lab_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("CREATING TABLE WILL STRAT NOW ->");
        sqLiteDatabase.execSQL("CREATE TABLE TENANT(EMAIL_ADDRESS TEXT PRIMARY KEY,FIRST_NAME TEXT,LAST_NAME TEXT,GENDER TEXT,PASSWORD TEXT,NATIONALITY TEXT,SALARY DOUBLE,OCCUPATION TEXT,FAMILY_SIZE INTEGER,CURRENT_COUNTRY TEXT,CITY TEXT,PHONE_NUMBER TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE RENTING_AGENCY(EMAIL_ADDRESS TEXT PRIMARY KEY,NAME TEXT,PASSWORD TEXT,COUNTRY TEXT,CITY TEXT,PHONE_NUMBER TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE PROPERTY(PROPERTY_ID INTEGER PRIMARY KEY AUTOINCREMENT, CITY TEXT,POSTAL_ADDRESS TEXT,SURFACE_AREA DOUBLE,CONSTRUCTION_YEAR INTEGER, NUMBER_OF_BED_ROOMS INTEGER,RENTAL_PRICE DOUBLE, BALCONY BOOLEAN, GARDEN BOOLEAN, AVAILABILITY_DATE TEXT, DESCRIPTION TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE PROPERTY_IMAGE(PROPERTY_IMAGE_ID INTEGER PRIMARY KEY AUTOINCREMENT,IMAGE_NAME ,IMAGE BOLB,PROPERTY TEXT)");
        System.out.println("CREATING TABLES DONE SUCESSFULLY");
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase){
        System.out.println("CREATING TABLE WILL STRAT NOW ->");
        //sqLiteDatabase.execSQL("CREATE TABLE TENANT(EMAIL_ADDRESS TEXT PRIMARY KEY,FIRST_NAME TEXT,LAST_NAME TEXT,GENDER TEXT,PASSWORD TEXT,NATIONALITY TEXT,SALARY DOUBLE,OCCUPATION TEXT,FAMILY_SIZE INTEGER,CURRENT_COUNTRY TEXT,CITY TEXT,PHONE_NUMBER TEXT)");
        //sqLiteDatabase.execSQL("CREATE TABLE RENTING_AGENCY(EMAIL_ADDRESS TEXT PRIMARY KEY,NAME TEXT,PASSWORD TEXT,COUNTRY TEXT,CITY TEXT,PHONE_NUMBER TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE PROPERTY(PROPERTY_ID INTEGER PRIMARY KEY AUTOINCREMENT, CITY TEXT,POSTAL_ADDRESS TEXT,SURFACE_AREA DOUBLE,CONSTRUCTION_YEAR INTEGER, NUMBER_OF_BED_ROOMS INTEGER,RENTAL_PRICE DOUBLE, BALCONY BOOLEAN, GARDEN BOOLEAN, AVAILABILITY_DATE TEXT, DESCRIPTION TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE PROPERTY_IMAGE(PROPERTY_IMAGE_ID INTEGER PRIMARY KEY AUTOINCREMENT,IMAGE_NAME ,IMAGE BOLB,PROPERTY TEXT)");
        System.out.println("CREATING TABLES DONE SUCESSFULLY");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("CREATE TABLE TENANT(EMAIL_ADDRESS TEXT PRIMARY KEY,FIRST_NAME TEXT,LAST_NAME TEXT,GENDER TEXT,PASSWORD TEXT,NATIONALITY TEXT,SALARY DOUBLE,OCCUPATION TEXT,FAMILY_SIZE INTEGER,CURRENT_COUNTRY TEXT,CITY TEXT,PHONE_NUMBER TEXT)");
//        sqLiteDatabase.execSQL("CREATE TABLE RENTING_AGENCY(EMAIL_ADDRESS TEXT PRIMARY KEY,NAME TEXT,PASSWORD TEXT,COUNTRY TEXT,CITY TEXT,PHONE_NUMBER TEXT)");
    }

    public void insert_tenant(Tenant tenant) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL_ADDRESS", tenant.getEmail_address());
        contentValues.put("FIRST_NAME", tenant.getFirst_name());
        contentValues.put("LAST_NAME", tenant.getLast_name());
        contentValues.put("GENDER", tenant.getGender());
        contentValues.put("PASSWORD", tenant.getPassword());
        contentValues.put("NATIONALITY", tenant.getNationality());
        contentValues.put("SALARY", tenant.getSalary());
        contentValues.put("OCCUPATION", tenant.getOccupation());
        contentValues.put("FAMILY_SIZE", tenant.getFamily_size());
        contentValues.put("CURRENT_COUNTRY", tenant.getCurrent_country());
        contentValues.put("CITY", tenant.getCity());
        contentValues.put("PHONE_NUMBER", tenant.getPhone_number());
        sqLiteDatabase.insert("TENANT", null, contentValues);
    }
    public void insert_renting_agency(RentingAgency agency) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL_ADDRESS", agency.getEmail_address());
        contentValues.put("NAME", agency.getName());
        contentValues.put("PASSWORD", agency.getPassword());
        contentValues.put("COUNTRY", agency.getCountry());
        contentValues.put("CITY", agency.getCity());
        contentValues.put("PHONE_NUMBER", agency.getPhone_number());
        sqLiteDatabase.insert("RENTING_AGENCY", null, contentValues);
    }


    public Cursor get_all_tenants() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM TENANT", null);
    }
    public Cursor get_all_renting_agencies() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM RENTING_AGENCY", null);
    }

    public Cursor get_tenant(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM TENANT WHERE EMAIL_ADDRESS=\""+email+"\"",null);
    }
    public Cursor get_renting_agency(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM RENTING_AGENCY WHERE EMAIL_ADDRESS=\""+email+"\"",null);
    }

}


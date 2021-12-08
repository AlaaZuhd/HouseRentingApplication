package com.example.lab_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import com.example.lab_project.models.Image;
import com.example.lab_project.models.Property;
import com.example.lab_project.models.RentingAgency;
import com.example.lab_project.models.Tenant;

import java.io.ByteArrayOutputStream;


public class DataBaseHelper extends SQLiteOpenHelper {

    String create_tenant_table_statement =              "CREATE TABLE TENANT(" +
            "EMAIL_ADDRESS TEXT PRIMARY KEY," +
            "FIRST_NAME TEXT," +
            "LAST_NAME TEXT," +
            "GENDER TEXT," +
            "PASSWORD TEXT," +
            "NATIONALITY TEXT," +
            "SALARY DOUBLE," +
            "OCCUPATION TEXT," +
            "FAMILY_SIZE INTEGER," +
            "CURRENT_COUNTRY TEXT," +
            "CITY TEXT," +
            "PHONE_NUMBER TEXT)";
    String create_renting_agency_table_statement =      "CREATE TABLE RENTING_AGENCY(" +
            "EMAIL_ADDRESS TEXT PRIMARY KEY," +
            "NAME TEXT," +
            "PASSWORD TEXT," +
            "COUNTRY TEXT," +
            "CITY TEXT," +
            "PHONE_NUMBER TEXT)";
    String create_property_table_statement =            "CREATE TABLE PROPERTY(" +
            "PROPERTY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "CITY TEXT," +
            "POSTAL_ADDRESS TEXT," +
            "SURFACE_AREA DOUBLE," +
            "CONSTRUCTION_YEAR INTEGER, " +
            "NUMBER_OF_BED_ROOMS INTEGER," +
            "RENTAL_PRICE DOUBLE, " +
            "STATUS BOOLEAN, " +
            "BALCONY BOOLEAN, " +
            "GARDEN BOOLEAN," +
            "AVAILABILITY_DATE TEXT, " +
            "DESCRIPTION TEXT, " +
            "RENTING_AGENCY_EMAIL_ADDRESS TEXT," +
            "FOREIGN KEY (RENTING_AGENCY_EMAIL_ADDRESS) REFERENCES RENTING_AGENCY (EMAIL_ADDRESS))";
    String create_property_image_table_statement =      "CREATE TABLE PROPERTY_IMAGE(" +
            "PROPERTY_IMAGE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "IMAGE_NAME TEXT," +
            "IMAGE BLOB," +
            "PROPERTY_ID INTEGER, " +
            "FOREIGN KEY (PROPERTY_ID) REFERENCES PROPERTY (PROPERTY_ID))";
    String create_tenant_property_table_statement =     "CREATE TABLE TENANT_PROPERTY(" +
            "TENANT_ID TEXT," +
            "PROPERTY_ID INTEGER," +
            "START_DATE TEXT," +
            "END_DATE TEXT, " +
            "FOREIGN KEY (TENANT_ID) REFERENCES TENANT (EMAIL_ADDRESS)," +
            "FOREIGN KEY (PROPERTY_ID) REFERENCES PROPERTY (PROPERTY_ID)," +
            "PRIMARY KEY(TENANT_ID, PROPERTY_ID, START_DATE, END_DATE)";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("CREATING TABLE WILL STRAT NOW ->");
        create_table(create_tenant_table_statement, sqLiteDatabase);
        create_table(create_renting_agency_table_statement, sqLiteDatabase);
        create_table(create_property_table_statement, sqLiteDatabase);
        create_table(create_property_image_table_statement, sqLiteDatabase);
        create_table(create_tenant_property_table_statement, sqLiteDatabase);
        System.out.println("CREATING TABLES DONE SUCESSFULLY");
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase){
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TENANT");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS RENTING_AGENCY");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PROPERTY");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PROPERTY_IMAGE");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TENANT_PROPERTY");
//        System.out.println("CREATING TABLE WILL STRAT NOW ->");
//        create_table(create_tenant_table_statement, sqLiteDatabase);
//        create_table(create_renting_agency_table_statement, sqLiteDatabase);
//        create_table(create_property_table_statement, sqLiteDatabase);
//        create_table(create_property_image_table_statement, sqLiteDatabase);
//        create_table(create_tenant_property_table_statement, sqLiteDatabase);

//        System.out.println("CREATING TABLES DONE SUCESSFULLY");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        create_table(create_tenant_table_statement, sqLiteDatabase);
//        create_table(create_renting_agency_table_statement, sqLiteDatabase);
//        create_table(create_property_table_statement, sqLiteDatabase);
//        create_table(create_property_image_table_statement, sqLiteDatabase);
    }

    public void create_table(String create_table_statment, SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(create_table_statment);
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

        int recorde_id = (int)sqLiteDatabase.insert("TENANT", null, contentValues);
        System.out.println("Record id for the inserted tenant is: " + recorde_id);
        if(recorde_id != -1)
            tenant.setTenant_id(recorde_id);
        else{
            // show toast message for the probelm
        }
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
        int recorde_id = (int)sqLiteDatabase.insert("RENTING_AGENCY", null, contentValues);
        System.out.println("Record id for agency is: " + recorde_id);
        if(recorde_id != -1)
            agency.setRenting_agency_id(recorde_id);
        else{
            // show toast message for the probelm
        }
    }

    public void insert_property(Property property) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CITY", property.getCity());
        contentValues.put("POSTAL_ADDRESS", property.getPostal_address());
        contentValues.put("SURFACE_AREA", property.getSurface_area());
        contentValues.put("CONSTRUCTION_YEAR", property.getConstruction_year());
        contentValues.put("NUMBER_OF_BED_ROOMS", property.getNumber_of_bedrooms());
        contentValues.put("RENTAL_PRICE", property.getRental_price());
        contentValues.put("STATUS", property.isStatus());
        contentValues.put("BALCONY", property.isBalcony());
        contentValues.put("GARDEN", property.isGarden());
        contentValues.put("AVAILABILITY_DATE", property.getAvailability_date().toString());
        contentValues.put("DESCRIPTION", property.getDescription());
        contentValues.put("PROPERTY_OWNER", property.getProperty_owner().getEmail_address());

        int recorde_id = (int)sqLiteDatabase.insert("PROPERTY", null, contentValues);
        if(recorde_id != -1)
            property.setProperty_id(recorde_id);
        else{
            // show toast message for the probelm
        }
    }

    public void insert_property_image(Image image) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ByteArrayOutputStream image_output_stream = null;
        byte[] image_bytes = new byte[]{};
        image.getImage().compress(Bitmap.CompressFormat.PNG, 100, image_output_stream);
        image_bytes = image_output_stream.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put("IMAGE_NAME", image.getImage_name());
        contentValues.put("IMAGE", image_bytes);
        contentValues.put("PROPERTY", image.getProperty().getProperty_id());

        int recorde_id = (int)sqLiteDatabase.insert("PROPERTY_IMAGE", null, contentValues);
        if(recorde_id != -1)
            image.setImage_id(recorde_id);
        else{
            // show toast message for the probelm
        }
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


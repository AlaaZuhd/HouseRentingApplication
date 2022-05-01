package com.example.lab_project.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import com.example.lab_project.PropertySummaryFragment;
import com.example.lab_project.models.Image;
import com.example.lab_project.models.Notification;
import com.example.lab_project.models.Property;
import com.example.lab_project.models.RentingAgency;
import com.example.lab_project.models.RequestProperty;
import com.example.lab_project.models.Tenant;
import com.example.lab_project.models.User;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class DataBaseHelper extends SQLiteOpenHelper {

    String create_user_table_statement =            "CREATE TABLE USER(" +
            "EMAIL_ADDRESS TEXT PRIMARY KEY," +
            "TYPE INTEGER, " +
            "PASSWORD TEXT," +
            "COUNTRY TEXT," +
            "CITY TEXT," +
            "PHONE_NUMBER TEXT)";

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
            "PHONE_NUMBER TEXT, " +
            "FOREIGN KEY (EMAIL_ADDRESS) REFERENCES USER (EMAIL_ADDRESS))";
    String create_renting_agency_table_statement =      "CREATE TABLE RENTING_AGENCY(" +
            "EMAIL_ADDRESS TEXT PRIMARY KEY," +
            "NAME TEXT," +
            "PASSWORD TEXT," +
            "COUNTRY TEXT," +
            "CITY TEXT," +
            "PHONE_NUMBER TEXT," +
            "FOREIGN KEY (EMAIL_ADDRESS) REFERENCES USER (EMAIL_ADDRESS))";
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
            "RENTING_AGENCY_EMAIL_ADDRESS TEXT, " +
            "POSTING_DATE TEXT, " +
            "IS_ACTIVE BOOLEAN, " +
            "FOREIGN KEY (RENTING_AGENCY_EMAIL_ADDRESS) REFERENCES RENTING_AGENCY (EMAIL_ADDRESS))";
    String create_property_image_table_statement =      "CREATE TABLE PROPERTY_IMAGE(" +
            "PROPERTY_IMAGE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "IMAGE_NAME TEXT," +
            "IMAGE BLOB," +
            "PROPERTY_ID INTEGER, " +
            "FOREIGN KEY (PROPERTY_ID) REFERENCES PROPERTY (PROPERTY_ID) ON DELETE CASCADE)";
    String create_tenant_property_table_statement =     "CREATE TABLE TENANT_PROPERTY(" +
            "TENANT_ID TEXT," +
            "PROPERTY_ID INTEGER, " +
            "START_DATE TEXT, " +
            "END_DATE TEXT, " +
            "FOREIGN KEY (TENANT_ID) REFERENCES TENANT (EMAIL_ADDRESS)," +
            "FOREIGN KEY (PROPERTY_ID) REFERENCES PROPERTY (PROPERTY_ID)," +
            "PRIMARY KEY(TENANT_ID, PROPERTY_ID, START_DATE, END_DATE))";
    String create_property_request_table_statement =     "CREATE TABLE PROPERTY_REQUEST(" +
            "REQUEST_PROPERTY_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "PROPERTY_ID INTEGER," +
            "TENANT_ID TEXT, " +
            "REQUEST_DATE TEXT," +
            "IS_APPROVED BOOLEAN DEFAULT 0, " +
            "IS_REJECTED BOOLEAN DEFAULT 0, " +
            "FROM_DATE TEXT, " +
            "TO_DATE TEXT, " +
            "FOREIGN KEY (TENANT_ID) REFERENCES TENANT (EMAIL_ADDRESS) ON DELETE CASCADE ," +
            "FOREIGN KEY (PROPERTY_ID) REFERENCES PROPERTY (PROPERTY_ID) ON DELETE CASCADE )" ;
    String create_notification_table_statement =     "CREATE TABLE NOTIFICATION(" +
            "NOTIFICATION_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "FROM_ID TEXT, " +
            "TO_ID TEXT, " +
            "REQUEST_ID INTEGER, " +
            "IS_READ BOOLEAN DEFAULT 0, " +
            "SENDING_DATE TEXT, " +
            "NOTIFICATION_TYPE TEXT, " +
            "FOREIGN KEY (FROM_ID) REFERENCES USER (EMAIL_ADDRESS) ON DELETE CASCADE ," +
            "FOREIGN KEY (TO_ID) REFERENCES USER (EMAIL_ADDRESS) ON DELETE CASCADE," +
            "FOREIGN KEY (REQUEST_ID) REFERENCES PROPERTY_REQUEST (REQUEST_ID) ON DELETE CASCADE)" ;

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        create_table(create_user_table_statement, sqLiteDatabase);
        create_table(create_tenant_table_statement, sqLiteDatabase);
        create_table(create_renting_agency_table_statement, sqLiteDatabase);
        create_table(create_property_table_statement, sqLiteDatabase);
        create_table(create_property_image_table_statement, sqLiteDatabase);
        create_table(create_tenant_property_table_statement, sqLiteDatabase);
        create_table(create_property_request_table_statement, sqLiteDatabase);
        create_table(create_notification_table_statement, sqLiteDatabase);
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase){
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS USER");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TENANT");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS RENTING_AGENCY");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PROPERTY");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PROPERTY_IMAGE");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TENANT_PROPERTY");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PROPERTY_REQUEST");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS NOTIFICATION");
//        create_table(create_user_table_statement, sqLiteDatabase);
//        create_table(create_tenant_table_statement, sqLiteDatabase);
//        create_table(create_renting_agency_table_statement, sqLiteDatabase);
//        create_table(create_property_table_statement, sqLiteDatabase);
//        create_table(create_property_image_table_statement, sqLiteDatabase);
//        create_table(create_tenant_property_table_statement, sqLiteDatabase);
//        create_table(create_property_request_table_statement, sqLiteDatabase);
//        create_table(create_notification_table_statement, sqLiteDatabase);
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

    public void  insert_user(User user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL_ADDRESS", user.getEmail_address());
        contentValues.put("PASSWORD", user.getPassword());
        contentValues.put("COUNTRY", user.getCountry());
        contentValues.put("CITY", user.getCity());
        contentValues.put("PHONE_NUMBER", user.getPhone_number());
        contentValues.put("TYPE", 0);
        int recorde_id = (int)sqLiteDatabase.insert("USER", null, contentValues);
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
        if(recorde_id != -1)
            agency.setRenting_agency_id(recorde_id);
        else{
            // show toast message for the probelm
        }
    }

    public void insert_property(Property property) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PROPERTY_IMAGE");
//        create_table(create_property_image_table_statement, sqLiteDatabase);

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
        contentValues.put("RENTING_AGENCY_EMAIL_ADDRESS", property.getRenting_agency_owner_id());
        contentValues.put("POSTING_DATE", property.getPosting_date().toString());
        contentValues.put("IS_ACTIVE", property.isIs_active());

        int recorde_id = (int)sqLiteDatabase.insert("PROPERTY", null, contentValues);
        if(recorde_id != -1)
            property.setProperty_id(recorde_id);
        else{
            // show toast message for the probelm
        }
    }

    public void insert_property_image(Image image) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        //ByteArrayOutputStream image_output_stream = null;
        //byte[] image_bytes = new byte[]{};
        //image.getImage().compress(Bitmap.CompressFormat.PNG, 100, image_output_stream);
        //image_bytes = image_output_stream.toByteArray();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.getImage().compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put("IMAGE_NAME", image.getImage_name());
        contentValues.put("IMAGE", imageInByte);
        contentValues.put("PROPERTY_ID", image.getProperty_id());

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

    public Cursor get_most_up_to_five_added_properties(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROPERTY ORDER BY PROPERTY_ID DESC LIMIT 5",null);
    }

    public Cursor get_active_properties(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROPERTY WHERE IS_ACTIVE = 1  ORDER BY PROPERTY_ID DESC",null);
    }

    public Cursor get_properties_for_a_renting_agency(String renting_agency_email_addree){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROPERTY  WHERE RENTING_AGENCY_EMAIL_ADDRESS=\"" + renting_agency_email_addree + "\" ORDER BY PROPERTY_ID DESC",null);
    }

    public boolean delete_property_by_id(int property_id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.delete("PROPERTY", "PROPERTY_ID =" + property_id, null) > 0;
    }

    public boolean update_property(Property property){
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


        int num_of_rows = sqLiteDatabase.update("PROPERTY", contentValues, "PROPERTY_ID ="+ property.getProperty_id(), null);
        if(num_of_rows == 0) return false;
        else return true;
    }

    public boolean update_is_active_property(int property_id, boolean is_active){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IS_ACTIVE", is_active);
        int num_of_rows = sqLiteDatabase.update("PROPERTY", contentValues, "PROPERTY_ID ="+ property_id, null);
        if(num_of_rows == 0) return false;
        else return true;
    }

    public boolean update_posting_date_property(int property_id, String posting_date){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("POSTING_DATE", posting_date);
        int num_of_rows = sqLiteDatabase.update("PROPERTY", contentValues, "PROPERTY_ID ="+ property_id, null);
        if(num_of_rows == 0) return false;
        else return true;
    }

    public Cursor get_one_image_for_property(Integer property_id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROPERTY_IMAGE WHERE PROPERTY_ID=" + property_id + " LIMIT 1",null);
    }

    public Cursor get_all_image_for_property(Integer property_id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROPERTY_IMAGE WHERE PROPERTY_ID=" + property_id ,null);
    }

    public boolean delete_image_by_id(int image_id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.delete("PROPERTY_IMAGE", "PROPERTY_IMAGE_ID =" + image_id, null) > 0;
    }

    public Cursor search_property(String query){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(query,null);
    }

    public Cursor search_tenant_property(String query){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(query,null);
    }

    public Cursor search_request_property(String query){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(query,null);
    }

    public Cursor search_notification(String query){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(query,null);
    }

    public boolean delete_notification_by_id(int notification_id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.delete("NOTIFICATION", "NOTIFICATION_ID =" + notification_id, null) > 0;
    }

    public boolean delete_all_notifications_for_user(String user_id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.delete("NOTIFICATION", "TO_ID =\"" + user_id + "\"", null) > 0;
    }

    public boolean update_tenant(ContentValues contentValues, String tenant_id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update("TENANT", contentValues, "EMAIL_ADDRESS =\""+ tenant_id + "\"", null) > 0? true: false;
    }


    public boolean update_renting_agency(ContentValues contentValues, String renting_agency_id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update("RENTING_AGENCY", contentValues, "EMAIL_ADDRESS =\""+ renting_agency_id + "\"", null) > 0? true: false;
    }

    public Cursor get_properties_rented_by_specific_tenant(String tenant_id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM TENANT_PROPERTY WHERE TENANT_ID=\""+tenant_id+"\"",null);
    }

    public Cursor get_property_information(int property_id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROPERTY WHERE PROPERTY_ID=\""+property_id+"\"",null);
    }

    public void insert_property_tenant(String tenant_id, int property_id, String start_date, String end_date){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENANT_ID", tenant_id);
        contentValues.put("PROPERTY_ID", property_id);
        contentValues.put("START_DATE", start_date);
        contentValues.put("END_DATE", end_date);
        int recorde_id = (int)sqLiteDatabase.insert("TENANT_PROPERTY", null, contentValues);

    }

    public Cursor get_properties_for_specific_agency(String agency_username){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROPERTY WHERE RENTING_AGENCY_EMAIL_ADDRESS=\""+agency_username+"\"",null);
    }

    public Cursor get_tenants_renting_specific_property(int property_id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM TENANT_PROPERTY WHERE PROPERTY_ID=" +property_id,null);
    }

    public int insert_request_property(RequestProperty request_property){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PROPERTY_ID", request_property.getProperty_id());
        contentValues.put("TENANT_ID", request_property.getTenant_id());
        contentValues.put("REQUEST_DATE", request_property.getRequest_date());
        contentValues.put("FROM_DATE", request_property.getFrom_date());
        contentValues.put("TO_DATE", request_property.getTo_date());
        int recorde_id = (int)sqLiteDatabase.insert("PROPERTY_REQUEST", null, contentValues);
        if(recorde_id != -1)
            request_property.setRequest_property_id(recorde_id);
        else{
            // show toast message for the probelm
        }
        return recorde_id;
    }

    public void get_tables_names(){
        SQLiteDatabase database = getWritableDatabase();
        Cursor c = database.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table'", null);
//        Assert.assertNotNull(c);

        String actual = "";
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                actual += c.getString(0) + ",";
                c.moveToNext();
            }
        }
        c.close();
    }

    public Cursor get_pending_property_request_by_tenant(String tenant_id, int property_id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROPERTY_REQUEST WHERE IS_APPROVED = 0 AND IS_REJECTED = 0 AND TENANT_ID=\"" + tenant_id + "\" and PROPERTY_ID=" + property_id,null);
    }

    public Cursor get_property_request(int request_id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROPERTY_REQUEST WHERE REQUEST_PROPERTY_ID=" + request_id , null);
    }

    public Cursor get_all_property_requests_for_property(int property_id, boolean is_approved, boolean is_rejected){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String property_status = " AND IS_APPROVED=" + (is_approved? 1: 0 ) + " AND IS_REJECTED=" + (is_rejected? 1: 0);
        return sqLiteDatabase.rawQuery("SELECT * FROM PROPERTY_REQUEST WHERE PROPERTY_ID=" + property_id + ""+ property_status , null);
    }

    public int insert_notification(Notification notification){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FROM_ID", notification.getFrom_id());
        contentValues.put("TO_ID", notification.getTo_id());
        contentValues.put("REQUEST_ID", notification.getRequest_id());
        contentValues.put("IS_READ", notification.isIs_read());
        contentValues.put("SENDING_DATE", notification.getSending_date());
        contentValues.put("NOTIFICATION_TYPE", notification.getNotification_type());
        int recorde_id = (int)sqLiteDatabase.insert("NOTIFICATION", null, contentValues);
        return recorde_id;
    }


    public Cursor get_user_in_notification(String to_id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM NOTIFICATION WHERE TO_ID=\"" + to_id + "\"",null);
    }

    public Cursor get_user_out_notification(String from_id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM NOTIFICATION WHERE FROM_ID=\"" + from_id + "\"",null);
    }

    public boolean reject_request(int request_id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IS_REJECTED", true);

        int num_of_rows = sqLiteDatabase.update("PROPERTY_REQUEST", contentValues, "REQUEST_PROPERTY_ID ="+ request_id, null);
        if(num_of_rows == 0) return false;
        else return true;
    }

    public boolean approve_request(int request_id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IS_APPROVED", true);

        int num_of_rows = sqLiteDatabase.update("PROPERTY_REQUEST", contentValues, "REQUEST_PROPERTY_ID ="+ request_id, null);
        if(num_of_rows == 0) return false;
        else return true;
    }

    public boolean update_is_read_notification(int notification_id, boolean is_read){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IS_READ", 1);

        int num_of_rows = sqLiteDatabase.update("NOTIFICATION", contentValues, "NOTIFICATION_ID ="+ notification_id, null);
        if(num_of_rows == 0) return false;
        else return true;
    }



}


//hash passwords
package com.example.lab_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    EditText email;
    EditText password;
    String editText_default_color;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefManager =SharedPrefManager.getInstance(this);
        setContentView(R.layout.activity_login);
        email=(EditText) findViewById(R.id.login_email);
        password=(EditText)findViewById(R.id.login_password);
        //If there are any email in the shared preference-> display it
        String shared_pref_email = sharedPrefManager.readString("email_address","noValue");
        if (!shared_pref_email.equals("noValue")){
            email.setText(shared_pref_email);
        }
        DataBaseHelper dataBaseHelper =new DataBaseHelper(LoginActivity.this,"Lab_Project.db",null,1);
        //Adding a random tenant:
//        Tenant new_tenant = new Tenant("test@gmail.com","alaa","rawan","Female","123","123","ads",123,"12",12,"da","asd","052");
//        dataBaseHelper.insert_tenant(new_tenant);
        //dataBaseHelper.insert_tenant(new_tenant);
        //Adding a random renting-agency:
        //RentingAgency new_agency = new RentingAgency("t@gmail.com","Test","321","321","xe","xe","052");
        //dataBaseHelper.insert_renting_agency(new_agency);
//        RentingAgency new_agency = new RentingAgency("ALAA@ALAA.COM","Test","321","321","xe","xe","052");
//        Property new_property = new Property("Ram",344,34.4,2002,5,20.2,false,true,true, Date.valueOf("2015-03-31"),"hhh",new_agency);
//        dataBaseHelper.insert_property(new_property);


        email.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });


        //sign in button handling
        Button sign_in = (Button)findViewById(R.id.login_sign_in_button);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor tempp = dataBaseHelper.get_all_renting_agencies();
                while (tempp.moveToNext()) { //User found
                    System.out.println("Email: " + tempp.getString(0) + ", password: " + tempp.getString(2));
                }


                if(email.getText().toString().equals("")){
                    email.setError("Wrong Email");
                }
                if (password.getText().toString().equals("")){
                    password.setError("Wrong Password");
                }
                else {
                    System.out.println("Email is: " + email.getText().toString() + ", password is: " + password.getText().toString());
                    //Connect to database and get password and compare it
                    //check if a tenant or agency
                    Cursor temp = dataBaseHelper.get_tenant(email.getText().toString());
                    int flag = 0;
                    while (temp.moveToNext()) { //User found
                        System.out.println("Tenant");
                        if (password.getText().toString().equals(temp.getString(4))) {
                            System.out.println("You can Enter as tenant!");
                            //move to home layout as tenant
//                            intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
//                            LoginActivity.this.startActivity(intent);
//                            LoginActivity.this.finish();

                            intent = new Intent(LoginActivity.this, HomeActivity.class);
                            LoginActivity.this.startActivity(intent);
                            LoginActivity.this.finish();
                        } else { //wrong password
                            //password_error.setVisibility(View.VISIBLE);
                            password.setError("Wrong Password");
                            password.setBackgroundColor(Color.parseColor("#66FF0000"));
                        }
                        flag = 1;
                    }
                    if (flag == 0) {
                        System.out.println("renting agency");
                        temp = dataBaseHelper.get_renting_agency(email.getText().toString());
                        while (temp.moveToNext()) { //User found
                            System.out.println("pass: "+ password.getText().toString() + ", real pass: " + temp.getString(2));
                            if (password.getText().toString().equals(temp.getString(2))) {
                                System.out.println("You can Enter as renting agency!");
                                //move to home layout as renting agency
//                                intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
//                                LoginActivity.this.startActivity(intent);
//                                LoginActivity.this.finish();
                                intent = new Intent(LoginActivity.this, HomeActivity.class);
                                LoginActivity.this.startActivity(intent);
                                LoginActivity.this.finish();
                            } else { //wrong password
                                //password_error.setVisibility(View.VISIBLE);
                                password.setError("Wrong Password");
                                password.setBackgroundColor(Color.parseColor("#66FF0000"));
                            }
                            flag = 1;
                        }
                    }
                    if (flag == 0){
                        email.setError("Wrong Email");
                        email.setBackgroundColor(Color.parseColor("#66FF0000"));
                    }
                }
                //if valid->go to home layout
                //if not->declare error
            }
        });

        Button sign_up = (Button)findViewById(R.id.login_sign_up_button);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(LoginActivity.this, SignupActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }
    public void OnRememberMeClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.login_remember_me_check_box:
                if (checked){
                    sharedPrefManager.writeString("email_address",email.getText().toString());
                } else
                    break;
        }
    }
}
package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;

public class ViewUserActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextView startDate, endDate;
    int date_flag;
    private TextView name, gender, mobile, nic;
    SQLiteDatabase db;
    Button view, cancel;
    EditText registration_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        db=openOrCreateDatabase("Fitness_Center", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS member(reg_no VARCHAR,name VARCHAR,gender VARCHAR, mobile INTEGER, nic VARCHAR, s_date VARCHAR, e_date VARCHAR);");

        startDate = findViewById(R.id.tv_start_date);
        endDate = findViewById(R.id.tv_end_date);

        registration_number = findViewById(R.id.et_registration_number);
        name = findViewById(R.id.et_name);
        gender = findViewById(R.id.et_gender);
        mobile = findViewById(R.id.et_mobile);
        nic = findViewById(R.id.et_nic_number);

        view = findViewById(R.id.btn_view);
        cancel = findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(registration_number.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Reg Number");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM member WHERE reg_no='"+registration_number.getText()+"'", null);
                if(c.moveToFirst())
                {
                    name.setText(c.getString(1));
                    gender.setText(c.getString(2));
                    mobile.setText(c.getString(3));
                    nic.setText(c.getString(4));
                    startDate.setText(c.getString(5));
                    endDate.setText(c.getString(6));
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                    clearText();
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());

        if (date_flag==1){
            startDate.setText(currentDateString);
        }
        if(date_flag==2){
            endDate.setText(currentDateString);
        }
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        registration_number.setText("");
        name.setText("");
        gender.setText("");
        mobile.setText("");
        nic.setText("");
        startDate.setText("Start Date");
        endDate.setText("End Date");
        registration_number.requestFocus();
    }
}

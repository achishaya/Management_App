package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    LinearLayout addUSer, update, viewUser, viewAll, deleteUser, payment;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=openOrCreateDatabase("Fitness_Center", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS member(reg_no VARCHAR,name VARCHAR,gender VARCHAR, mobile INTEGER, nic VARCHAR, s_date VARCHAR, e_date VARCHAR);");

        addUSer = findViewById(R.id.addUser);
        update = findViewById(R.id.updateUser);
        viewUser = findViewById(R.id.viewUser);
        viewAll = findViewById(R.id.viewAll);
        deleteUser = findViewById(R.id.deleteUser);
        payment = findViewById(R.id.payment);

        addUSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateUserActivity.class);
                startActivity(intent);
            }
        });

        viewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewUserActivity.class);
                startActivity(intent);
            }
        });


        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c=db.rawQuery("SELECT * FROM member", null);
                if(c.getCount()==0)
                {
                    showMessage("Error", "No records found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(c.moveToNext())
                {
                    buffer.append("Reg No: "+c.getString(0)+"\n");
                    buffer.append("Name: "+c.getString(1)+"\n");
                    buffer.append("Gender: "+c.getString(2)+"\n");
                    buffer.append("Mobile No: "+c.getString(3)+"\n");
                    buffer.append("NIC: "+c.getString(4)+"\n");
                    buffer.append("Start Date: "+c.getString(5)+"\n");
                    buffer.append("End Date: "+c.getString(6)+"\n\n");
                }
                showMessage("User Details", buffer.toString());
            }
        });

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteUserActivity.class);
                startActivity(intent);
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());

                Cursor c=db.rawQuery("SELECT * FROM member WHERE e_date='"+currentDate+"'", null);
                if(c.getCount()==0)
                {
                    showMessage("Error", "No Records Found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(c.moveToNext())
                {
                    buffer.append("Reg No: "+c.getString(0)+"\n");
                    buffer.append("Name: "+c.getString(1)+"\n");
                    buffer.append("Gender: "+c.getString(2)+"\n");
                    buffer.append("Mobile No: "+c.getString(3)+"\n");
                    buffer.append("NIC: "+c.getString(4)+"\n");
                    buffer.append("Start Date: "+c.getString(5)+"\n");
                    buffer.append("End Date: "+c.getString(6)+"\n\n");
                }
                showMessage("User Details", buffer.toString());
            }
        });
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteUserActivity extends AppCompatActivity {

    SQLiteDatabase db;
    EditText registration_no;
    Button delete, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        db=openOrCreateDatabase("Fitness_Center", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS member(reg_no VARCHAR,name VARCHAR,gender VARCHAR, mobile INTEGER, nic VARCHAR, s_date VARCHAR, e_date VARCHAR);");

        registration_no = findViewById(R.id.et_registration_number);
        delete = findViewById(R.id.btn_delete);

        cancel = findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(registration_no.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please Enter Registration Number");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM member WHERE reg_no='"+registration_no.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("DELETE FROM member WHERE reg_no='"+registration_no.getText()+"'");
                    showMessage("Success", "Record Deleted");
                }
                else
                {
                    showMessage("Error", "Invalid Registration Number");
                }
                clearText();
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
    public void clearText()
    {
        registration_no.setText("");
        registration_no.requestFocus();
    }
}

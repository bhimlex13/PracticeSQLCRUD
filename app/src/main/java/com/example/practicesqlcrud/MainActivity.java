package com.example.practicesqlcrud;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    TextView text;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DatabaseHelper(MainActivity.this);
//        db.deleteDatabase(MainActivity.this);
        db.getReadableDatabase();

        text = findViewById(R.id.textShow);

        Cursor cursor = db.getAllProductsById(1);
        cursor.move(1);

        String aaa = cursor.getString(0);

        text.setText(aaa);

    }
}
package com.example.myletterindex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myletterindex.view.LetterView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private LetterView letterindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        letterindex = (LetterView) findViewById(R.id.letter_index);
        textView = (TextView) findViewById(R.id.text_letter);
        letterindex.setShowText(textView);
        letterindex.setOnSlidingListener(new LetterView.OnSlidingListener() {
            @Override
            public void onSliding(String letter) {
                Toast.makeText(MainActivity.this, letter, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.kabir.huiping.iotsocket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        count = 0;

    }

    public void onButtonClick(View view) {
        String info = (String) getResources().getText(R.string.test);
        info = info + " " + count++;
        editText.setText(info);
    }

}

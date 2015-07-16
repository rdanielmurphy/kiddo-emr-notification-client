package com.example.danielmurphy.kiddoemrclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by danielmurphy on 4/4/15.
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private Button registerForTipsBtn, viewTipsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewTipsButton = (Button) findViewById(R.id.button);
        registerForTipsBtn = (Button) findViewById(R.id.button2);

        viewTipsButton.setOnClickListener(this);
        registerForTipsBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(registerForTipsBtn)) {
            Intent i = new Intent(this, RegisterPhoneActivity.class);
            startActivity(i);
        }
        else {
            Intent i = new Intent(this, AndroidListViewCursorAdaptorActivity.class);
            startActivity(i);
        }
    }
}

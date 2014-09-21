package com.example.myfirstapp;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.api.android.Chronometer;
import com.simple.observer.Observer;
import com.example.myfirstapp.R;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, Observer {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private Chronometer chronometer;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        findViewById(R.id.init_button).setOnClickListener(this);
        ((Button) findViewById(R.id.start_button)).setOnClickListener(this);
        ((Button) findViewById(R.id.stop_button)).setOnClickListener(this);
        textView = (TextView)findViewById(R.id.viewheight);
        Spinner spinner = (Spinner) findViewById(R.id.dropDownTimeUnitSymbol);
        chronometer.addObserver(this);
        String[] values =  {"s","m","h"};
        ArrayAdapter<String> arrayAdaptater = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,values);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search :
                openSearch();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSearch() {
        Toast.makeText(this, "Search button pressed", Toast.LENGTH_SHORT).show();
    }

    private void openSettings() {
        Toast.makeText(this, "Settings button pressed", Toast.LENGTH_SHORT).show();
    }

   /* public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/
   /*Intent intent = new Intent(getBaseContext(), SignoutActivity.class);
    intent.putExtra("EXTRA_SESSION_ID", sessionId);
    startActivity(intent)*/
    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.init_button:
                chronometer.setBase(SystemClock.elapsedRealtime());
                break;
            case R.id.start_button:

                chronometer.start();
                break;
            case R.id.stop_button:
                chronometer.stop();
                /*chronometer.get*/
                break;
        }

    }

    @Override
    public void update() {
        Vibrator vibrator = (Vibrator)
                this.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }
}

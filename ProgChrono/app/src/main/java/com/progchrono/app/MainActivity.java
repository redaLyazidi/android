package com.progchrono.app;

import static com.api.android.util.StringsUtil.isReallyNullOrEmpty;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.api.android.chronometer.model.ChronometerProgram;

import com.api.android.chronometer.model.Series;
import com.api.android.util.ViewUtil;
import com.simple.observer.Observer;
import com.api.android.Chronometer;


public class MainActivity extends AbstractActivity implements View.OnClickListener, Observer {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private Chronometer chronometer;

    private TextView textView;

    private Button startButton;
    private Button stopButton;
    private Button initButton;
    private Button turnButton;
    private TextView numberUnitTextView;
    private Spinner unitSymbolspinner;
    private TextView timesTextView;


    private LinearLayout turnsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        initButton = getAndSetOnClickListener(R.id.init_button);
        startButton = getAndSetOnClickListener(R.id.start_button);
        stopButton =  getAndSetOnClickListener(R.id.stop_button);
        turnButton =  getAndSetOnClickListener(R.id.turn_button);
        textView = findTextViewById(R.id.viewheight);
        turnsLayout = findLinearLayoutById(R.id.turns);
        unitSymbolspinner = findSpinnerById(R.id.dropDownTimeUnitSymbol);
        numberUnitTextView = findTextViewById(R.id.numberUnit);
        timesTextView = findTextViewById(R.id.timesValue);
        chronometer.addObserver(this);
        String[] values =  {"s","m","h"};
        ArrayAdapter<String> arrayAdaptater = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,values);
        stopButton.setVisibility(View.GONE);
        turnButton.setVisibility(View.GONE);


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
            case R.id.action_bar :
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
                chronometer.init();
                break;
            case R.id.start_button:

                chronometer.start();
                ViewUtil.switchView(startButton, stopButton);
                ViewUtil.switchView(initButton, turnButton);
                setProgramToChronometer();

                break;
            case R.id.stop_button:
                chronometer.stop();
                ViewUtil.switchView(stopButton,startButton);
                ViewUtil.switchView(turnButton, initButton);
                break;

            case R.id.turn_button :
                displayTurn();
            default:
                break;
        }

    }

    private void setProgramToChronometer() {
        final CharSequence numberUnitText = numberUnitTextView.getText();
        final String numberUnit = numberUnitText != null ? numberUnitText.toString() : "";
        final String unitSymbol = unitSymbolspinner.getSelectedItem().toString();
        final CharSequence timesText = timesTextView.getText();
        final String times = timesText != null ? timesText.toString() : "";
        final boolean withProgram = !isReallyNullOrEmpty(numberUnit) && !isReallyNullOrEmpty(unitSymbol) && !isReallyNullOrEmpty(times);
        chronometer.setCanUpdate(withProgram);
        if (withProgram)  {
            Series series = new Series();
            series.setNumberUnit(numberUnit);
            series.setUnitSymbol(unitSymbol);
            series.setTimes(times);
            chronometer.getChronometerProgram().addSeries(series);
        }
    }

    @Override
    public void update() {
        vibrate(500);
    }

    public void vibrate(int numberOfMilliseconds) {
        Vibrator vibrator = (Vibrator)
                this.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(numberOfMilliseconds);
    }

    public void displayTurn() {
        String currentTime = chronometer.getCurrentTimeAsText();
        TextView text = new TextView(getApplicationContext());
        text.setText(currentTime);
        turnsLayout.addView(text,0);

    }

    private Button getAndSetOnClickListener(int idButton) {
        Button button = findButtonById(idButton);
        button.setOnClickListener(this);
        return button;
    }
}
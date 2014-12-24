package com.progchrono.app;

import static com.api.android.util.StringsUtil.isReallyNullOrEmpty;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.api.android.chronometer.model.Series;
import com.api.android.util.ViewUtil;
import com.simple.observer.Observer;
import com.api.android.Chronometer;


public class MainActivity extends AbstractActivity implements View.OnClickListener, Observer {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private static final int NUMBER_UNIT_INDEX = 0;

    private static final int SPINNER_INDEX = 1;

    private static final int TIMES_INDEX = 3;

    private Chronometer chronometer;

    private TextView textView;

    private Button startButton;
    private Button stopButton;
    private Button initButton;
    private Button turnButton;
    private TextView numberUnitTextView;
    private Spinner unitSymbolspinner;
    private TextView timesTextView;
    private ImageButton firstPlus;
    private ScrollView serieScrollView;
    private LinearLayout seriesMainLayout;



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
        firstPlus = getImageButtonAndSetOnClickListener(R.id.firstPlus);
        textView = findTextViewById(R.id.viewheight);
        turnsLayout = findLinearLayoutById(R.id.turns);
        unitSymbolspinner = findSpinnerById(R.id.dropDownTimeUnitSymbol);
        numberUnitTextView = findTextViewById(R.id.numberUnit);
        timesTextView = findTextViewById(R.id.timesValue);
        chronometer.addObserver(this);
        stopButton.setVisibility(View.GONE);
        turnButton.setVisibility(View.GONE);
        serieScrollView = findSrollViewById(R.id.seriescrollview);
        seriesMainLayout = findLinearLayoutById(R.id.seriesmainlayout);

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

        final int viewId = v.getId();
        switch(viewId) {
            case R.id.init_button:
                chronometer.init();
                break;
            case R.id.start_button:

                chronometer.start();
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                ViewUtil.switchView(startButton, stopButton);
                ViewUtil.switchView(initButton, turnButton);
                setProgramToChronometer();

                break;
            case R.id.stop_button:
                chronometer.stop();
                getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                ViewUtil.switchView(stopButton,startButton);
                ViewUtil.switchView(turnButton, initButton);
                break;

            case R.id.turn_button :
                displayTurn();
                break;

            case R.id.firstPlus :
                addNewSerie(v);
                break;

            default:
                break;
        }

    }

    private Series createSeriesFromView(TextView numberUnitTextView, Spinner unitSymbolspinner, TextView timesTextView) {
        Series series = null;
        final CharSequence numberUnitText = numberUnitTextView.getText();
        final String numberUnit = numberUnitText != null ? numberUnitText.toString() : "";
        final String unitSymbol = unitSymbolspinner.getSelectedItem().toString();
        final CharSequence timesText = timesTextView.getText();
        final String times = timesText != null ? timesText.toString() : "";
        final boolean withProgram = !isReallyNullOrEmpty(numberUnit) && !isReallyNullOrEmpty(unitSymbol) && !isReallyNullOrEmpty(times);

        if (withProgram) {
           series = new Series();
           series.setNumberUnit(numberUnit);
           series.setUnitSymbol(unitSymbol);
           series.setTimes(times);
         }
        return series;

    }

    private void setProgramToChronometer() {
        /*Series currentSeries = createSeriesFromView(numberUnitTextView,unitSymbolspinner,timesTextView);*/
        Series currentSeries;
        final int numberSeries = seriesMainLayout.getChildCount();
        for (int i = 0; i < numberSeries; ++i) {
            LinearLayout currentLayout = (LinearLayout) seriesMainLayout.getChildAt(i);
            TextView currentNumberUnit = (TextView) currentLayout.getChildAt(NUMBER_UNIT_INDEX);
            Spinner currentSpinner = (Spinner) currentLayout.getChildAt(SPINNER_INDEX);
            TextView currentTimes = (TextView) currentLayout.getChildAt(TIMES_INDEX);
            currentSeries = createSeriesFromView(currentNumberUnit,currentSpinner,currentTimes);
            if (currentSeries != null) {
                chronometer.getChronometerProgram().addSeries(currentSeries);
                chronometer.setCanUpdate(true);
            }

        }

    }

   /* private void setProgramToChronometer() {
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
    }*/

    @Override
    public void update() {
        Log.d("vibrate","");
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

    private ImageButton getImageButtonAndSetOnClickListener(int idButton) {
        ImageButton button = findImageButtonById(idButton);
        button.setOnClickListener(this);
        return button;
    }

    public void addNewSerie(View view) {
        ViewGroup serieMainLayout = findViewGroupById(R.id.seriesandchronolayout);
      /*mainLayout.addView(new SeriesLinearLayout(getApplicationContext()),2);*/
        LinearLayout v = (LinearLayout) getLayoutInflater().inflate(R.layout.newserielayout, null);
        serieMainLayout.addView(v,1);

    }

    public void removeSerie(View view) {
       ImageButton minusButton = (ImageButton) view;
       LinearLayout currentLayout = (LinearLayout) view.getParent();
       currentLayout.removeAllViews();
       ((ViewGroup) currentLayout.getParent()).removeView(currentLayout);
        currentLayout = null;

    }


}
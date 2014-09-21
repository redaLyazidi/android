package com.api.android;

/*
 * The Android chronometer widget revised so as to count milliseconds
 */

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.widget.TextView;
import com.simple.observer.Observable;
import com.simple.observer.Observer;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;


public class Chronometer extends TextView  implements Observable {

    @SuppressWarnings("unused")
    private static final String TAG = "Chronometer";

    private Set<Observer> observers = new LinkedHashSet<>();

    private long mBase;
    private boolean mVisible;
    private boolean mStarted;
    private boolean mRunning;
    private OnChronometerTickListener mOnChronometerTickListener;

    private static final int TICK_WHAT = 2;

    public static final char TIME_SEPARATOR =':';

    private long timeElapsed;

    public Chronometer(Context context) {
        this (context, null, 0);
    }

    public Chronometer(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public Chronometer(Context context, AttributeSet attrs, int defStyle) {
        super (context, attrs, defStyle);

        init();
    }

    @Override
    public boolean addObserver(Observer observer) {
        return observers.add(observer);
    }

    @Override
    public boolean addAllObserver(Collection<? extends Observer> observer) {
        return false;
    }

    @Override
    public void notifyAllObservers() {
       for (Observer currentObserver : observers) {
            currentObserver.update();
        }
    }

    public interface OnChronometerTickListener {

        void onChronometerTick(Chronometer chronometer);
    }

    private void init() {
        mBase = SystemClock.elapsedRealtime();
        updateText(mBase);
    }

    public void setBase(long base) {
        mBase = base;
        dispatchChronometerTick();
        updateText(SystemClock.elapsedRealtime());
    }

    public long getBase() {
        return mBase;
    }

    public void setOnChronometerTickListener(
            OnChronometerTickListener listener) {
        mOnChronometerTickListener = listener;
    }

    public OnChronometerTickListener getOnChronometerTickListener() {
        return mOnChronometerTickListener;
    }

    public void start() {
        /*Base = SystemClock.elapsedRealtime();*/
        mStarted = true;
        updateRunning();
    }

    public void stop() {
        mStarted = false;
        updateRunning();
    }


    public void setStarted(boolean started) {
        mStarted = started;
        updateRunning();
    }

    @Override
    protected void onDetachedFromWindow() {
        super .onDetachedFromWindow();
        mVisible = false;
        updateRunning();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super .onWindowVisibilityChanged(visibility);
        mVisible = visibility == VISIBLE;
        updateRunning();
    }

    private synchronized void updateText(long now) {


        setText(getTimeAsText(now));
        if (hasToVibrate()) {
          notifyAllObservers();
        }
    }

    public String getTimeAsText(long now) {

        timeElapsed = now - mBase;

        DecimalFormat df = new DecimalFormat("00");
        int hours = (int)(timeElapsed / (3600 * 1000));
        int remaining = (int)(timeElapsed % (3600 * 1000));

        int minutes = (int)(remaining / (60 * 1000));
        remaining = (int)(remaining % (60 * 1000));

        int seconds = (int)(remaining / 1000);
        remaining = (int)(remaining % (1000));

        int milliseconds = (int)(((int)timeElapsed % 1000) / 100);

        StringBuilder text = new StringBuilder();

        if (hours > 0) {
            text.append(df.format(hours)).append(TIME_SEPARATOR);
        }

        text.append(df.format(minutes)).append(TIME_SEPARATOR);
        text.append(df.format(seconds)).append(TIME_SEPARATOR);
        text.append(milliseconds);
        return text.toString();
    }

    private int getRemaining(long timeElapsed) {
        return (int)(timeElapsed % (3600 * 1000));
    }

    private void updateRunning() {
        boolean running = mVisible && mStarted;
        if (running != mRunning) {
            if (running) {
                updateText(SystemClock.elapsedRealtime());
                dispatchChronometerTick();
                mHandler.sendMessageDelayed(Message.obtain(mHandler,
                        TICK_WHAT), 100);
            } else {
                mHandler.removeMessages(TICK_WHAT);
            }
            mRunning = running;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message m) {
            if (mRunning) {
                updateText(SystemClock.elapsedRealtime());
                dispatchChronometerTick();
                sendMessageDelayed(Message.obtain(this , TICK_WHAT),
                        100);
            }
        }
    };

    void dispatchChronometerTick() {
        if (mOnChronometerTickListener != null) {
            mOnChronometerTickListener.onChronometerTick(this);
        }
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public int getHours() {
        return (int)(timeElapsed / (3600 * 1000));
    }


    public int getSeconds() {
        int remaining = (int)(timeElapsed % (3600 * 1000));

        int minutes = (int)(remaining / (60 * 1000));
        remaining = (int)(remaining % (60 * 1000));

        return  (int)(remaining / 1000);
    }

    public boolean hasToVibrate() {
        int seconds = getSeconds();
        return seconds != 0 && seconds % 10 == 0;
    }

}
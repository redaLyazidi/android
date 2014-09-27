package com.api.android;

/*
 * The Android chronometer widget revised so as to count milliseconds
 */

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
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

    private long base;

    private long timeStartUnleashed = 0L;
    private long timeStopUnleashed = 0L;
    private long spendingTime;
    private boolean mVisible;
    private boolean started;
    private boolean initiazed = true;
    private boolean running;
    private long begin,end;
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
        if (!started) {
            base = SystemClock.elapsedRealtime();
            initiazed = true;
            updateText(base);
        }

    }

    public void setBase(long base) {
        this.base = base;
        dispatchChronometerTick();
        updateText(SystemClock.elapsedRealtime());
    }

    public long getBase() {
        return base;
    }

    public void setOnChronometerTickListener(
            OnChronometerTickListener listener) {
        mOnChronometerTickListener = listener;
    }

    public OnChronometerTickListener getOnChronometerTickListener() {
        return mOnChronometerTickListener;
    }

    public void start() {
        if (initiazed) {
            base = SystemClock.elapsedRealtime();
        }
        if (! started) {
            timeStartUnleashed = SystemClock.elapsedRealtime();
        }
        spendingTime  = !initiazed ? timeStartUnleashed - timeStopUnleashed : 0L;
        started = true;
        initiazed = false;
        updateRunning();
    }

    public void stop() {
        started = false;
        timeStopUnleashed = SystemClock.elapsedRealtime();
        updateRunning();
    }


    public void setStarted(boolean started) {
        this.started = started;
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
        timeElapsed = now - base - spendingTime;

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
        boolean running = mVisible && started;
        if (running != this.running) {
            if (running) {
                updateText(SystemClock.elapsedRealtime());
                dispatchChronometerTick();
                mHandler.sendMessageDelayed(Message.obtain(mHandler,
                        TICK_WHAT), 100);
            } else {
                mHandler.removeMessages(TICK_WHAT);
            }
            this.running = running;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message m) {
            if (running) {
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



    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getMilliseconds() {
        return end-begin;
    }

    public int getSeconds() {
        return (int)(end - begin) / 1000;
    }

    public int getMinutes() {
        return (int) (end - begin) / 60000;
    }

    public int getHours() {
        return (int) (end - begin) / 3600000;
    }

    public boolean hasToVibrate() {
        int seconds = getSeconds();
        return seconds != 0 && seconds % 10 == 0;
    }

    public long getTimeStartUnleashed() {
        return timeStartUnleashed;
    }

    public void setTimeStartUnleashed(long timeStartUnleashed) {
        this.timeStartUnleashed = timeStartUnleashed;
    }

    public long getTimeStopUnleashed() {
        return timeStopUnleashed;
    }

    public void setTimeStopUnleashed(long timeStopUnleashed) {
        this.timeStopUnleashed = timeStopUnleashed;
    }
}
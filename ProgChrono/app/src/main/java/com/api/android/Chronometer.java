package com.api.android;

/*
 * The Android chronometer widget revised so as to count milliseconds
 */

import static com.api.android.util.CollectionArrayUtil.toStringArray;
import static com.api.android.util.CollectionArrayUtil.containsLong;
import static com.api.android.util.CollectionArrayUtil.asListLong;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import com.api.android.chronometer.model.ChronometerProgram;
import com.api.android.util.CollectionArrayUtil;
import com.simple.observer.Observable;
import com.simple.observer.Observer;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Chronometer extends TextView  implements Observable {

    @SuppressWarnings("unused")
    private static final String TAG = "Chronometer";

    private Set<Observer> observers = new LinkedHashSet<>();

    private long base;

    private long timeStartUnleashed = 0L;
    private long timeStopUnleashed = 0L;
    private long oldBase;
    private boolean mVisible;
    private boolean started;
    private boolean initiazed = true;
    private boolean running;
    private boolean canUpdate = false;
    private OnChronometerTickListener mOnChronometerTickListener;
    private ChronometerProgram chronometerProgram;

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
        chronometerProgram = new ChronometerProgram();
        init();
    }

    @Override
    public boolean addObserver(Observer observer) {
        return this.observers.add(observer);
    }

    @Override
    public boolean addAllObserver(Collection<? extends Observer> observers)  {
        return this.observers.addAll(observers);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer currentObserver : observers) {
            currentObserver.update();
        }
    }

    public ChronometerProgram getChronometerProgram() {
        return chronometerProgram;
    }

    public interface OnChronometerTickListener {

        void onChronometerTick(Chronometer chronometer);
    }

    public void init() {
        if (!started) {
            base = SystemClock.elapsedRealtime();
            initiazed = true;
            oldBase = 0L;
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
        if (! started) {
            base = SystemClock.elapsedRealtime();
            timeStartUnleashed = SystemClock.elapsedRealtime();
            started = true;
            initiazed = false;
            updateRunning();
        }
    }

    public void stop() {
        started = false;
        oldBase += SystemClock.elapsedRealtime() - base;
        chronometerProgram.getAllSeries().clear();
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
        if (canUpdate && hasToUpdate()) {
            notifyAllObservers();
        }
    }

    public String getCurrentTimeAsText() {
        long now = SystemClock.elapsedRealtime();
        return getTimeAsText(now);
    }

    public String getTimeAsText(long now) {
        timeElapsed = calculTimeElapsed(now);

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

    public long calculTimeElapsed(long now) {
        timeElapsed = now - base + oldBase;
        return timeElapsed;
    }

    /**
    * Get the current seconds (0 to 59)
    * @return current seconds
    */
    public int getSeconds() {
        int hours = (int)(timeElapsed / (3600 * 1000));
        int remaining = (int)(timeElapsed % (3600 * 1000));

        int minutes = (int)(remaining / (60 * 1000));
        remaining = (int)(remaining % (60 * 1000));

        int seconds = (int)(remaining / 1000);
        remaining = (int)(remaining % (1000));
        return seconds;
    }

    /**
     * Get the total number of seconds (goes over 59)
     * @return total numbers of seconds
     */
    public int getTotalSeconds() {
        return (int) TimeUnit.MILLISECONDS.toSeconds(timeElapsed);
    }

    public int getMinutes() {
        int minutes = (int)(timeElapsed / (60 * 1000));
        int remaining = (int)(timeElapsed % (60 * 1000));
        return minutes;
    }

    public int getHours() {
        int hours = (int) (timeElapsed / (3600 * 1000));
        int remaining = (int) (timeElapsed % (3600 * 1000));
        return hours;
    }


    public synchronized boolean hasToUpdate() {
        if (timeElapsed == 0L) {
            return false;
        }
        int seconds = getTotalSeconds();
        final List<Long> timesToReact = chronometerProgram.retrieveValueToReactFromSeriesInSeconds();
        long secondsLong = (long) seconds;
        /*Log.d(timesToReact.toString(),"value to react");
        Log.d("current seconds", String.valueOf(seconds));*/
        return timesToReact.contains(secondsLong);
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

    public boolean isCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public List<Long> getProgram() {
        List<Long> program = new LinkedList<>();
        return program;
    }

    public boolean isInitialized() {
        return initiazed;
    }
}

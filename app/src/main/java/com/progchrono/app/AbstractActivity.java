package com.progchrono.app;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.*;
import com.api.android.Chronometer;

/**
 * Created by reda on 10/12/14.
 */
public class AbstractActivity extends ActionBarActivity {

    protected <T extends View> T findViewByIdWithoutCast(int id) {
        return (T) super.findViewById(id);
    }

    protected LinearLayout findLinearLayoutById(int id) {
        return findViewByIdWithoutCast(id);
    }

    protected RelativeLayout findRelativeLayoutById(int id) {
        return findViewByIdWithoutCast(id);
    }

    protected Button findButtonById(int id) {
        return findViewByIdWithoutCast(id);
    }

    protected TextView findTextViewById(int id) {
        return findViewByIdWithoutCast(id);
    }

    protected Chronometer findChronometerById(int id) {
        return findViewByIdWithoutCast(id);
    }

    protected Spinner findSpinnerById(int id) {
        return findViewByIdWithoutCast(id);
    }

}

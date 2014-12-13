package com.api.android.custom.view;

import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.widget.*;
import com.progchrono.app.R;

/**
 * Created by reda on 12/2/14.
 * Custom LinearLayout for Serie
 */
public class SeriesLinearLayout extends LinearLayout {

    /**
     * Type of the input in the text view
     */
    public static final int INPUT_TYPE = InputType.TYPE_NUMBER_FLAG_SIGNED;

    /**
     * Width of the text field to enter the number of time's unit
     */
    public static final int NUMBER_UNIT_WIDTH = 67;

    public static final String SPINNER_PROMPT ="Choose a times unit";

    public static final String[] SPINNER_DROP_DOWN_ARRAY = {"s","m","h"};

    private static final int ID_LAYOUT_DROP_DOWN_LIST = R.layout.support_simple_spinner_dropdown_item;

    private static final String TIMES_TEXT = "times :";

    /**
     * Width of the spinner
     */
    public static final int SPINNER_WIDTH = 50;

    /**
     * Text size
     */
    public static final int TEXT_SIZE = 10;

    /**
     * Text size
     */
    public static final int TIMES_TEXT_VIEW_WIDTH = 100;

    /**
     * Default constructor
     * @param context Context of the Activity
     */
    public SeriesLinearLayout(Context context) {

        super(context);
        init(context);
    }


    protected void init(Context context) {
        EditText numberUnitEditText = new EditText(context);
        numberUnitEditText.setInputType(INPUT_TYPE);
        numberUnitEditText.setEms(TEXT_SIZE);
        numberUnitEditText.setWidth(NUMBER_UNIT_WIDTH);
        /*numberUnitEditText.setHeight();*/
        addView(numberUnitEditText);

        Spinner spinner = new Spinner(context);
        numberUnitEditText.setWidth(SPINNER_WIDTH);
        spinner.setPrompt(SPINNER_PROMPT);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, ID_LAYOUT_DROP_DOWN_LIST,SPINNER_DROP_DOWN_ARRAY);
        spinnerAdapter.setDropDownViewResource(ID_LAYOUT_DROP_DOWN_LIST);
        spinner.setAdapter(spinnerAdapter);
        addView(spinner);

        TextView timesTextView = new TextView(context);
        timesTextView.setText(TIMES_TEXT);
        timesTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        timesTextView.setTextAppearance(context,R.style.TextAppearance_AppCompat_Widget_PopupMenu_Large);
        addView(timesTextView);

        EditText timesEditText = new EditText(context);
        timesEditText.setInputType(INPUT_TYPE);
        timesEditText.setWidth(TIMES_TEXT_VIEW_WIDTH);
        numberUnitEditText.setEms(TEXT_SIZE);
        addView(timesEditText);

        ImageButton imageButtonPlus = new ImageButton(context);
        imageButtonPlus.setImageResource(R.drawable.icone_plus);
        addView(imageButtonPlus);






    }
}

package com.github.twinsod.notes.ui.customView;

import android.content.Context;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by User on 08-Jun-17.
 */

public class DataTextView extends android.support.v7.widget.AppCompatTextView{
    public DataTextView(Context context) {
        super(context);
    }

    public DataTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DataTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLongDate(long date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String dateText = formatter.format(date);
        setText(dateText);
    }
}

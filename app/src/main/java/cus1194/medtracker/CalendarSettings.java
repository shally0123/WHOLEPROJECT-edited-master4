package cus1194.medtracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by pruan086 on 3/22/2017.
 */

public class CalendarSettings implements DatePickerDialog.OnDateSetListener {



    Context context;
    public CalendarSettings(Context context)
    {
        this.context = context;
    }



    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
        Toast.makeText(context, "Selected Date: " +dayOfMonth+ " / " +monthOfYear+ " / " +year, Toast.LENGTH_LONG).show();
    }
}

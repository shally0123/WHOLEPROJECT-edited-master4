package cus1194.medtracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by pruan086 on 3/22/2017.
 */

public class CalendarSettings extends DialogFragment implements DatePickerDialog.OnDateSetListener {



   /* Context context;
    public CalendarSettings(Context context)
    {
        this.context = context;
    }*/
   @Override
   public Dialog onCreateDialog (Bundle savedInstanceState)
   {

       Calendar calendar = Calendar.getInstance();
       int year = calendar.get(Calendar.YEAR);
       int month = calendar.get(Calendar.MONTH);
       int day = calendar.get(Calendar.DAY_OF_MONTH);

       DatePickerDialog dialog;
       dialog = new DatePickerDialog(getActivity(), this, year,month,day);

       return dialog;

   }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
        Toast.makeText(getContext(), "Selected Date: " +dayOfMonth+ " / " +monthOfYear+ " / " +year, Toast.LENGTH_LONG).show();
    }
}

package cus1194.medtracker;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientCalendarFragment extends Fragment {


    View v;
    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);


    public PatientCalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_calendar, container, false);
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    Button b;




    public void onCreatedView(View view,Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);

        b = (Button) v.findViewById(R.id.button1);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                    CalendarSettings cs = new CalendarSettings(getActivity());
                    DatePickerDialog dialog;
                    dialog = new DatePickerDialog(getActivity(), cs, year, month, day);
                    dialog.show();


            }
        });





    }


}

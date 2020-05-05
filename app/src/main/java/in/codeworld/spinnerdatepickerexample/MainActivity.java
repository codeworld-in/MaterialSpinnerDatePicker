package in.codeworld.spinnerdatepickerexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import in.codeworld.spinnerdatepicker.MaterialSpinnerDatePicker;

public class MainActivity extends AppCompatActivity {

    TextView selectedDate;
    Button selectNewDate, selectNewDateDemo2, noTitle, mixedColorDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectNewDate = findViewById(R.id.select_new_date);
        selectedDate = findViewById(R.id.selected_date);
        selectNewDateDemo2 = findViewById(R.id.select_new_date2);

        final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(2020, 2, 25);

        selectNewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialSpinnerDatePicker datePicker = new MaterialSpinnerDatePicker(MainActivity.this)
                        .setDividerColor(R.color.colorPrimary)
                        .setNextButtonColor(R.color.colorPrimary)
                        .setNextButtonTextColor(R.color.white)
                        .setTopBarBGColor(R.color.colorPrimary)
                        .setNextButtonText("Next")
                        .setYearRange(2000, 2020)
                        .setDefaultDateToToday()
                        .setDate(calendar)
                        .setCloseOnTouchOutSide(true)
                        .setTitle("Color Demo 1 ")
                        .setOnDateSelectedListener(new MaterialSpinnerDatePicker.MaterialDatePickerListener() {
                            @Override
                            public void onDateSelected(String dateString, String rawDateString, Date dateObject) {
                                selectedDate.setText("Raw String  : " + rawDateString + "\n" + "Date String : " + dateString + "\nDate Object Instance : " + dateObject.toString());
                            }
                        });

                datePicker.show();
            }
        });

        selectNewDateDemo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialSpinnerDatePicker datePicker = new MaterialSpinnerDatePicker(MainActivity.this)
                        .setDividerColor(R.color.colorAccent)
                        .setNextButtonColor(R.color.colorAccent)
                        .setNextButtonTextColor(R.color.white)
                        .setTopBarBGColor(R.color.colorAccent)
                        .setNextButtonText("Next")
                        .setYearRange(2000, 2020)
                        .setDefaultDateToToday()
                        .setCloseOnTouchOutSide(true)
                        .setTitle("Color Demo 2 ")
                        .setOnDateSelectedListener(new MaterialSpinnerDatePicker.MaterialDatePickerListener() {
                            @Override
                            public void onDateSelected(String dateString, String rawDateString, Date dateObject) {
                                selectedDate.setText("Raw String  : " + rawDateString + "\n" + "Date String : " + dateString + "\nDate Object Instance : " + dateObject.toString());
                            }
                        });

                datePicker.show();
            }
        });

        noTitle = findViewById(R.id.no_title_no_topbar);
        noTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialSpinnerDatePicker datePicker = new MaterialSpinnerDatePicker(MainActivity.this)
                        .setDividerColor(R.color.green)
                        .setNextButtonColor(R.color.green)
                        .setNextButtonTextColor(R.color.white)
                        .setTopBarBGColor(R.color.green)
                        .setNextButtonText("Next")
                        .setYearRange(2000, 2020)
                        .setCloseOnTouchOutSide(true)
                        .hideTitle()
                        .hideTopBar()
                        .setOnDateSelectedListener(new MaterialSpinnerDatePicker.MaterialDatePickerListener() {
                            @Override
                            public void onDateSelected(String dateString, String rawDateString, Date dateObject) {
                                selectedDate.setText("Raw String  : " + rawDateString + "\n" + "Date String : " + dateString + "\nDate Object Instance : " + dateObject.toString());
                            }
                        });

                datePicker.show();
            }
        });

        mixedColorDemo = findViewById(R.id.mixed_color_demo);
        mixedColorDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialSpinnerDatePicker datePicker = new MaterialSpinnerDatePicker(MainActivity.this)
                        .setDividerColor(R.color.yellow)
                        .setTopBarTextColor(R.color.black)
                        .setNextButtonColor(R.color.green)
                        .setNextButtonTextColor(R.color.white)
                        .setTopBarBGColor(R.color.yellow)
                        .setTitleTextColor(R.color.colorPrimary)
                        .setNextButtonText("Next")
                        .setYearRange(2000, 2020)
                        .setDefaultDateToToday()
                        .setCloseOnTouchOutSide(true)
                        .setTitle("Demo Mixed Colors ")
                        .setOnDateSelectedListener(new MaterialSpinnerDatePicker.MaterialDatePickerListener() {
                            @Override
                            public void onDateSelected(String dateString, String rawDateString, Date dateObject) {
                                selectedDate.setText("Raw String  : " + rawDateString + "\n" + "Date String : " + dateString + "\nDate Object Instance : " + dateObject.toString());
                            }
                        });

                datePicker.show();
            }
        });


    }
}

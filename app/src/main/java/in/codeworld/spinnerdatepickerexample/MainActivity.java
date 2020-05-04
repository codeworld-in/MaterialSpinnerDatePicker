package in.codeworld.spinnerdatepickerexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import in.codeworld.spinnerdatepicker.SpinnerDatePicker;

public class MainActivity extends AppCompatActivity {

    TextView selectedDate;
    Button selectNewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectNewDate = findViewById(R.id.select_new_date);
        selectedDate = findViewById(R.id.selected_date);

        final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(2020, 2, 25);

        selectNewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerDatePicker datePicker = new SpinnerDatePicker(MainActivity.this, new SpinnerDatePicker.DatePickerCallback() {
                    @Override
                    public void onDateSelected(String dateString, String rawDateString, Date dateObject) {
                        selectedDate.setText(rawDateString);
                    }

                }).setDividerColor(R.color.colorPrimary)
                        .setNextButtonColor(R.color.colorPrimary)
                        .setNextButtonTextColor(R.color.white)
                        .setTopBarBGColor(R.color.colorPrimary)
                        .setNextButtonText("Continue")
                        .setYearRange(2000, 2020)
                        .setDefaultDateToToday()
                        .setDate(calendar)
                        .setCloseOnTouchOutSide(true).setTitle("Select Starting Date : ");

                datePicker.show();
            }
        });


    }
}

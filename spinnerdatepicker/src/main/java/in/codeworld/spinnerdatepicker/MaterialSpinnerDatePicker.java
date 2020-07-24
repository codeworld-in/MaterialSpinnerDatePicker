package in.codeworld.spinnerdatepicker;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import in.codeworld.spinnerdatepicker.adapter.DateAdapter;
import in.codeworld.spinnerdatepicker.databinding.SpinnerDatePickerDailogueBinding;
import in.codeworld.spinnerdatepicker.others.Constants;
import in.codeworld.spinnerdatepicker.others.Utils;

public class MaterialSpinnerDatePicker extends Dialog {

    private Activity activity;
    private SpinnerDatePickerDailogueBinding binding;

    private DateAdapter dayAdapter, monthAdapter, yearAdapter;
    private LinearLayoutManager daysLayoutManager, monthsLayoutManager, yearsLayoutManger;

    private int selectedDay, selectedMonthIndex, selectedYear;
    private String TAG = "SpinnerDatePicker";

    private MaterialDatePickerListener callback;
    private int startingYear = -1, endingYear = -1;

    public MaterialSpinnerDatePicker(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
        binding = SpinnerDatePickerDailogueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        daysLayoutManager = new LinearLayoutManager(activity);
        monthsLayoutManager = new LinearLayoutManager(activity);
        yearsLayoutManger = new LinearLayoutManager(activity);

        dayAdapter = new DateAdapter(Constants.CONTENT_TYPE_DAY, activity, new RecyclerViewCallback() {
            @Override
            public void onHeightGot(int height) {

            }

        });
        monthAdapter = new DateAdapter(Constants.CONTENT_TYPE_MONTH, activity, new RecyclerViewCallback() {
            @Override
            public void onHeightGot(int height) {

            }

        });
        yearAdapter = new DateAdapter(Constants.CONTENT_TYPE_YEAR, activity, new RecyclerViewCallback() {
            @Override
            public void onHeightGot(int height) {

            }


        });

        setOnSpinnerValueChangedListners();
        colorDivider();

        binding.dayRv.setMinValue(1);
        binding.dayRv.setMaxValue(31);
        binding.dayRv.setValue(1);

        binding.monthsRv.setMinValue(1);
        binding.monthsRv.setMaxValue(12);
        binding.monthsRv.setValue(1);

        binding.yearRv.setMaxValue(2022);
        binding.yearRv.setMinValue(2020);

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String dateString = selectedDay + "-" + selectedMonthIndex + "-" + selectedYear;
                try {
                    Date dateObject = sdf.parse(dateString);
                    Log.d(TAG, "Date String : " + dateString + " date object : " + dateObject.toString());
                    if (callback != null)
                        callback.onDateSelected(binding.selectedDate.getText().toString(), dateString, dateObject);
                    dismiss();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        binding.dayRv.setValue(1);

    }

    private void setOnSpinnerValueChangedListners() {

        binding.dayRv.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                getSelectedValues();
                try {
                    binding.selectedDate.setText(selectedDay + " " + Constants.MONTHS_COMPLETE_ARRAY[selectedMonthIndex - 1] + " " + selectedYear);
                } catch (Exception e) {
                    Log.d(TAG, "Error while updating the date text : ");
                }

            }
        });

        binding.monthsRv.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                getSelectedValues();
                binding.selectedDate.setText(selectedDay + " " + Constants.MONTHS_COMPLETE_ARRAY[selectedMonthIndex - 1] + " " + selectedYear);
                resetAdapterIfNeeded();
            }
        });

        binding.yearRv.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                getSelectedValues();
                binding.selectedDate.setText(selectedDay + " " + Constants.MONTHS_COMPLETE_ARRAY[selectedMonthIndex - 1] + " " + selectedYear);
                resetAdapterIfNeeded();
            }
        });
    }

    private void getSelectedValues() {
        selectedMonthIndex = binding.monthsRv.getValue();
        selectedDay = binding.dayRv.getValue();
        selectedYear = binding.yearRv.getValue();

    }

    private void colorDivider() {

        NumberPicker[] ids = new NumberPicker[]{binding.dayRv, binding.monthsRv, binding.yearRv};

        for (NumberPicker picker : ids) {
            // picker.getDividerDrawable().setColorFilter(activity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        }

    }

    private void resetAdapterIfNeeded() {

        getSelectedValues();

        if (Utils.isLeapYear(selectedYear)) {
            if (selectedMonthIndex == 2) {
                Log.d("SpinnerTAG", "Is Leap year and feb slected true ");
                setLeapYearAdapterForDate(true);
            } else {
                setDaysAdapterForMonth();
            }

        } else {
            if (selectedMonthIndex == 2) {
                setLeapYearAdapterForDate(false);
            } else {
                setDaysAdapterForMonth();
            }
        }
        try {
            binding.selectedDate.setText(selectedDay + " " + Constants.MONTHS_COMPLETE_ARRAY[selectedMonthIndex - 1] + " " + selectedYear);
        } catch (Exception e) {
            Log.d(TAG, "Error while updating the date text : ");
        }
    }

    private void setDaysAdapterForMonth() {

        ArrayList<String> days = new ArrayList<>();
        days.add("");
        if (Utils.isMonthContain31Days(selectedMonthIndex)) {
            binding.dayRv.setMaxValue(31);
            binding.dayRv.setMinValue(1);
        } else {
            binding.dayRv.setMaxValue(30);
            binding.dayRv.setMinValue(1);
        }

    }

    private void setLeapYearAdapterForDate(boolean isLeapYear) {

        ArrayList<String> days = new ArrayList<>();
        days.add("");
        if (isLeapYear) {
            binding.dayRv.setMaxValue(29);
        } else {
            binding.dayRv.setMaxValue(28);
        }

    }

    public MaterialSpinnerDatePicker setYearRange(int startingYear, int endingYear) {
        binding.yearRv.setMinValue(startingYear);
        binding.yearRv.setMaxValue(endingYear);
        return this;
    }

    public MaterialSpinnerDatePicker setDividerColor(int colorResourceID) {

        NumberPicker[] ids = new NumberPicker[]{binding.dayRv, binding.monthsRv, binding.yearRv};

        for (NumberPicker picker : ids) {
            // picker.getDividerDrawable().setColorFilter(activity.getResources().getColor(colorResourceID), PorterDuff.Mode.MULTIPLY);
        }
        return this;
    }

    public MaterialSpinnerDatePicker setTopBarBGColor(int colorResourceID) {
        binding.selectedDate.setBackgroundColor(activity.getResources().getColor(colorResourceID));
        return this;
    }

    public MaterialSpinnerDatePicker setNextButtonColor(int colorResourceID) {
        binding.next.setBackgroundColor(activity.getResources().getColor(colorResourceID));
        return this;
    }

    public MaterialSpinnerDatePicker setTitle(String title) {
        binding.title.setText(title);
        return this;
    }

    public MaterialSpinnerDatePicker setNextButtonText(String text) {
        binding.next.setText(text);
        return this;
    }

    public MaterialSpinnerDatePicker setNextButtonTextColor(int colorResourceId) {
        binding.next.setTextColor(activity.getResources().getColor(colorResourceId));
        return this;
    }

    public MaterialSpinnerDatePicker setCloseOnTouchOutSide(boolean newValue) {
        this.setCancelable(newValue);
        return this;
    }

    public MaterialSpinnerDatePicker setDefaultDateToToday() {

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);

        binding.dayRv.setMinValue(1);
        binding.dayRv.setMaxValue(31);

        binding.monthsRv.setMinValue(1);
        binding.monthsRv.setMaxValue(12);

        binding.yearRv.setMaxValue(year + 20);
        binding.yearRv.setMinValue(year - 5);


        try {
            binding.yearRv.setValue(year);
            binding.dayRv.setValue(day);
            binding.monthsRv.setValue(month);

        } catch (Exception e) {
            Log.d("DatePickerDailouge", "Unable to set current date: " + e);
        }

        return this;

    }

    public MaterialSpinnerDatePicker setDate(Calendar calendar) {

        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);

        binding.dayRv.setMinValue(1);
        binding.dayRv.setMaxValue(31);

        binding.monthsRv.setMinValue(1);
        binding.monthsRv.setMaxValue(12);

        binding.yearRv.setMaxValue(year + 20);
        binding.yearRv.setMinValue(year - 5);


        try {
            binding.yearRv.setValue(year);
            binding.dayRv.setValue(day);
            binding.monthsRv.setValue(month);
        } catch (Exception e) {
            Log.d("DatePickerDailouge", "Unable to set preselected date: " + e);
        }

        return this;
    }

    public MaterialSpinnerDatePicker setDate(int year, int month, int day) {

        binding.dayRv.setMinValue(1);
        binding.dayRv.setMaxValue(31);

        binding.monthsRv.setMinValue(1);
        binding.monthsRv.setMaxValue(12);

        binding.yearRv.setMaxValue(year + 20);
        binding.yearRv.setMinValue(year - 5);

        try {
            binding.yearRv.setValue(year);
            binding.dayRv.setValue(day);
            binding.monthsRv.setValue(month);
        } catch (Exception e) {
            Log.d("DatePickerDailouge", "Unable to set preselected date: " + e);
        }

        return this;
    }

    public MaterialSpinnerDatePicker hideTopBar() {
        binding.selectedDate.setVisibility(View.GONE);
        return this;
    }

    public MaterialSpinnerDatePicker setTopBarTextColor(int colorResource) {
        binding.selectedDate.setTextColor(activity.getResources().getColor(colorResource));
        return this;
    }

    public MaterialSpinnerDatePicker setTitleTextColor(int colorResource) {
        binding.title.setTextColor(activity.getResources().getColor(colorResource));
        return this;
    }

    public MaterialSpinnerDatePicker hideTitle() {
        binding.title.setVisibility(View.GONE);
        return this;
    }

    public MaterialSpinnerDatePicker setOnDateSelectedListener(MaterialDatePickerListener materialDatePickerListener) {
        this.callback = materialDatePickerListener;
        return this;
    }

    public interface MaterialDatePickerListener {
        public void onDateSelected(String dateString, String rawDateString, Date dateObject);
    }

    public interface RecyclerViewCallback {
        public void onHeightGot(int height);
    }

}

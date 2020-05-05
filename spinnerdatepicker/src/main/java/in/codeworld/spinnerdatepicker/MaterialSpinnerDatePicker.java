package in.codeworld.spinnerdatepicker;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

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
    RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (recyclerView == binding.dayRv) {
                int visibleItemCount = daysLayoutManager.getChildCount();
                int totalItemCount = daysLayoutManager.getItemCount();
                int firstVisibleItemPosition = daysLayoutManager.findFirstVisibleItemPosition();
                final int lastItem = firstVisibleItemPosition + visibleItemCount;
                Log.d("SpinnerTAG", " Selected  Day : " + dayAdapter.getItem(firstVisibleItemPosition + 1));
                selectedDay = Integer.parseInt(dayAdapter.getItem(firstVisibleItemPosition + 1));

                try {
                    binding.selectedDate.setText(selectedDay + " " + Constants.MONTHS_COMPLETE_ARRAY[selectedMonthIndex - 1] + " " + selectedYear);
                } catch (Exception e) {
                    Log.d(TAG, "Error while updating the date text : ");
                }


            } else if (recyclerView == binding.monthsRv) {
                int visibleItemCount = monthsLayoutManager.getChildCount();
                int totalItemCount = monthsLayoutManager.getItemCount();
                int firstVisibleItemPosition = monthsLayoutManager.findFirstVisibleItemPosition();
                final int lastItem = firstVisibleItemPosition + visibleItemCount;
                Log.d("SpinnerTAG", " Selected Month : " + monthAdapter.getItem(firstVisibleItemPosition + 1));
                selectedMonthIndex = firstVisibleItemPosition + 1;
                resetAdapterIfNeeded();

            } else if (recyclerView == binding.yearRv) {
                int visibleItemCount = yearsLayoutManger.getChildCount();
                int totalItemCount = yearsLayoutManger.getItemCount();
                int firstVisibleItemPosition = yearsLayoutManger.findFirstVisibleItemPosition();
                final int lastItem = firstVisibleItemPosition + visibleItemCount;
                Log.d("SpinnerTAG", " Selected Month : " + yearAdapter.getItem(firstVisibleItemPosition + 1));
                selectedYear = Integer.parseInt(yearAdapter.getItem(firstVisibleItemPosition + 1));
                resetAdapterIfNeeded();

            }

        }
    };
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

        binding.dayRv.setLayoutManager(daysLayoutManager);
        binding.monthsRv.setLayoutManager(monthsLayoutManager);
        binding.yearRv.setLayoutManager(yearsLayoutManger);

        binding.dayRv.setAdapter(dayAdapter);
        binding.monthsRv.setAdapter(monthAdapter);
        binding.yearRv.setAdapter(yearAdapter);


        binding.dayRv.addOnScrollListener(recyclerViewOnScrollListener);
        binding.monthsRv.addOnScrollListener(recyclerViewOnScrollListener);
        binding.yearRv.addOnScrollListener(recyclerViewOnScrollListener);
        colorDivider();

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

        binding.dayRv.scrollToPosition(1);
        binding.monthsRv.scrollToPosition(1);
        binding.yearRv.scrollToPosition(1);

        SnapHelper snapHelperDay = new LinearSnapHelper();
        snapHelperDay.attachToRecyclerView(binding.dayRv);

        SnapHelper snapHelperMonth = new LinearSnapHelper();
        snapHelperMonth.attachToRecyclerView(binding.monthsRv);

        SnapHelper snapHelperYear = new LinearSnapHelper();
        snapHelperYear.attachToRecyclerView(binding.yearRv);


    }

    private void colorDivider() {

        int[] ids = new int[]{R.id.divider1, R.id.divider2, R.id.divider3, R.id.divider4, R.id.divider5, R.id.divider6};

        for (int id : ids) {
            View view = findViewById(id);
            view.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

    }

    private void resetAdapterIfNeeded() {

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
            for (int i = 1; i <= 31; i++) {
                days.add(String.valueOf(i));
            }

        } else {
            for (int i = 1; i <= 30; i++) {
                days.add(String.valueOf(i));
            }
        }

        days.add("");

        dayAdapter.setDataSet(days);
        dayAdapter.notifyDataSetChanged();
        if (selectedDay < days.size()) {
            binding.dayRv.smoothScrollToPosition(selectedDay);
            Log.d(TAG, "Scrolling to preselected item : " + selectedDay);
        } else {
            binding.dayRv.smoothScrollToPosition(selectedDay - 1);
            Log.d(TAG, "Scrolling to last item : " + selectedDay);
        }


    }

    private void setLeapYearAdapterForDate(boolean isLeapYear) {

        ArrayList<String> days = new ArrayList<>();
        days.add("");
        if (isLeapYear) {
            for (int i = 1; i <= 29; i++) {
                days.add(String.valueOf(i));
            }

        } else {
            for (int i = 1; i <= 28; i++) {
                days.add(String.valueOf(i));
            }
        }

        days.add("");

        dayAdapter.setDataSet(days);
        dayAdapter.notifyDataSetChanged();
        if (selectedDay < days.size()) {
            binding.dayRv.smoothScrollToPosition(selectedDay);
            Log.d(TAG, "Scrolling to preselected item : " + selectedDay);
        } else {
            binding.dayRv.smoothScrollToPosition(selectedDay - 1);
            Log.d(TAG, "Scrolling to last item : " + selectedDay);
        }


    }

    public MaterialSpinnerDatePicker setYearRange(int startingYear, int endingYear) {
        this.startingYear = startingYear;
        this.endingYear = endingYear;
        ArrayList<String> years = new ArrayList<>();
        years.add("");
        for (int i = startingYear; i <= endingYear; i++) {
            years.add(String.valueOf(i));

        }
        years.add("");
        yearAdapter.setDataSet(years);
        yearAdapter.notifyDataSetChanged();

        return this;
    }

    public MaterialSpinnerDatePicker setDividerColor(int colorResourceID) {
        int[] ids = new int[]{R.id.divider1, R.id.divider2, R.id.divider3, R.id.divider4, R.id.divider5, R.id.divider6};
        for (int id : ids) {
            View view = findViewById(id);
            view.setBackgroundColor(activity.getResources().getColor(colorResourceID));
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

        int yearPos = yearAdapter.getPositionOfItem(String.valueOf(year));
        int dayPos = dayAdapter.getPositionOfItem(String.valueOf(day));
        int monthPos = monthAdapter.getPositionOfItem(String.valueOf(Constants.MONTHS_COMPLETE_ARRAY[month - 1]));

        Log.d(TAG, "Got items : year : " + year + " day: " + day + " month:" + month);
        Log.d(TAG, "Got items : year : " + yearPos + " daypos: " + dayPos + " monthPos:" + monthPos);

        try {

            binding.yearRv.scrollToPosition(yearPos);
            binding.dayRv.scrollToPosition(dayPos - 1);
            binding.monthsRv.scrollToPosition(monthPos);
        } catch (Exception e) {
            Log.d("DatePickerDailouge", "Unable to set current date: " + e);
        }

        return this;

    }

    public MaterialSpinnerDatePicker setDate(Calendar calendar) {

        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);

        int yearPos = yearAdapter.getPositionOfItem(String.valueOf(year));
        int dayPos = dayAdapter.getPositionOfItem(String.valueOf(day));
        int monthPos = monthAdapter.getPositionOfItem(String.valueOf(Constants.MONTHS_COMPLETE_ARRAY[month - 1]));

        Log.d(TAG, "Got items : year : " + year + " day: " + day + " month:" + month);
        Log.d(TAG, "Got items : year : " + yearPos + " daypos: " + dayPos + " monthPos:" + monthPos);

        try {

            binding.yearRv.scrollToPosition(yearPos);
            binding.dayRv.scrollToPosition(dayPos - 1);
            binding.monthsRv.scrollToPosition(monthPos);
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

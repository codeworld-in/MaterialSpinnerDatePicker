package in.codeworld.spinnerdatepicker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import in.codeworld.spinnerdatepicker.R;
import in.codeworld.spinnerdatepicker.SpinnerDatePicker;
import in.codeworld.spinnerdatepicker.others.Constants;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {

    public String contentType;
    private int height;
    private Context context;
    private ArrayList<String> dataSet;
    private SpinnerDatePicker.RecyclerViewCallback callback;

    public DateAdapter(String contentType, Context context, SpinnerDatePicker.RecyclerViewCallback callback) {
        this.contentType = contentType;
        this.context = context;
        dataSet = new ArrayList<>();
        dataSet.add("");
        if (contentType.equals(Constants.CONTENT_TYPE_DAY)) {
            for (int i = 1; i <= 31; i++) {
                dataSet.add(String.valueOf(i));
            }

        } else if (contentType.equals(Constants.CONTENT_TYPE_MONTH)) {
            dataSet.addAll(Arrays.asList(Constants.MONTHS_COMPLETE_ARRAY));

        } else if (contentType.equals(Constants.CONTENT_TYPE_YEAR)) {
            for (int i = 1900; i < 2050; i++) {
                dataSet.add(String.valueOf(i));
            }
        }
        dataSet.add("");

        this.callback = callback;
    }

    public ArrayList<String> getDataSet() {
        return dataSet;
    }

    public void setDataSet(ArrayList<String> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.date_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.content.setText(dataSet.get(position));

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public String getItem(int position) {
        return dataSet.get(position);
    }

    public int getPositionOfItem(String valueOf) {
        for (int i = 0; i < dataSet.size(); i++) {
            if (valueOf.equals(dataSet.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView content;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            itemView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int width = itemView.getMeasuredWidth();
            height = itemView.getMeasuredHeight();
        }
    }
}

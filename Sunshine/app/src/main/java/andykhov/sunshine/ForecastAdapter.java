package andykhov.sunshine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Andy on 10/14/17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private String[] mForecastData;

    public ForecastAdapter(String[] forecastData) {
        mForecastData = forecastData;
    }

    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_forecast, parent, false);

        ViewHolder viewHolder = new ViewHolder(textView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mForecastTextView.setText(mForecastData[position]);
    }

    @Override
    public int getItemCount() {
        return mForecastData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mForecastTextView;

        public ViewHolder(TextView v) {
            super(v);
            mForecastTextView = v;
        }
    }

}

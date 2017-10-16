package andykhov.sunshine;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        return new ViewHolder(textView);
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
            mForecastTextView = (TextView) itemView.findViewById(R.id.forecast_text_view);
        }
    }

}

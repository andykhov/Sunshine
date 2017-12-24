package andykhov.sunshine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Andy on 10/14/17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<Day> mDays;

    public ForecastAdapter(List<Day> days) {
        mDays = days;
    }

    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_day, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTempHighView.setText(Double.toString(mDays.get(position).tempHigh));
        viewHolder.mTempLowView.setText(Double.toString(mDays.get(position).tempLow));
        viewHolder.mSummary.setText(mDays.get(position).summary);
    }

    @Override
    public int getItemCount() {
        return mDays.size();
    }

    public void setNewForecast(List<Day> days) {
        for (int i = 0; i < mDays.size(); i++) {
            mDays.set(i, days.get(i));
            this.notifyItemChanged(i);
        }
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView mTempHighView;
        protected TextView mTempLowView;
        protected TextView mSummary;

        public ViewHolder(View v) {
            super(v);
            mTempHighView = (TextView) v.findViewById(R.id.tempHigh);
            mTempLowView = (TextView) v.findViewById(R.id.tempLow);
            mSummary = (TextView) v.findViewById(R.id.summary);
        }
    }

}

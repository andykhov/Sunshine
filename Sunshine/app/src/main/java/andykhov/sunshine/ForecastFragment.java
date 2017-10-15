package andykhov.sunshine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Andy on 10/14/17.
 */

public class ForecastFragment extends Fragment {
    RecyclerView mForecastRecyclerView;
    RecyclerView.LayoutManager mForecastLayoutManager;
    ForecastAdapter mForecastAdapter;

    public ForecastFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);

        mForecastRecyclerView = (RecyclerView) rootView.findViewById(R.id.forecast_list);
        mForecastLayoutManager = new LinearLayoutManager(getContext());
        mForecastAdapter = new ForecastAdapter(createFillerData(20));

        mForecastRecyclerView.setAdapter(mForecastAdapter);

        return rootView;
    }

    private String[] createFillerData(int size) {
        String[] forecastData = new String[size];

        for (int i = 0; i < size; i++) {
            forecastData[i] = "hot - 95f/85f - Wednesday";
        }

        return forecastData;
    }
}

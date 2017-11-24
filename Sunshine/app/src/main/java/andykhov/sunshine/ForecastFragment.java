package andykhov.sunshine;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Andy on 10/14/17.
 */

public class ForecastFragment extends Fragment {

    private final int CHECK_PERMISSION_FINE_LOCATION = 1;

    private RecyclerView mForecastRecyclerView;
    private RecyclerView.LayoutManager mForecastLayoutManager;
    private ForecastAdapter mForecastAdapter;
    private ArrayList<Day> mDays;

    public ForecastFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);
        mDays = createFillerData();
        setupRecyclerView(rootView);
        updateCurrentLocation();
        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(
            int resultCode, String[] permissions, int[] grantResults) {

        if (resultCode == CHECK_PERMISSION_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateCurrentLocation();
            }
        }
    }

    private void onLocationUpdated(Location location) {
        updateForecast(location);
    }

    private void setupRecyclerView(View rootView) {
        mForecastRecyclerView = (RecyclerView) rootView.findViewById(R.id.forecast_list);
        mForecastLayoutManager = new LinearLayoutManager(getContext());
        mForecastAdapter = new ForecastAdapter(mDays);
        mForecastRecyclerView.setLayoutManager(mForecastLayoutManager);
        mForecastRecyclerView.setAdapter(mForecastAdapter);
    }

    private void updateForecast(Location location) {
        OkHttpClient httpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(buildDarkSkyUrl(location))
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getActivity(), "Connection problem", Toast.LENGTH_SHORT);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    final Forecast forecast = gson.fromJson(response.body().string(), Forecast.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mForecastAdapter.setNewForecast(forecast.dailyForecast.days);
                        }
                    });
                    response.body().close();
                }
            }
        });
    }

    private void updateCurrentLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    CHECK_PERMISSION_FINE_LOCATION);
        } else {
            LocationServices.getFusedLocationProviderClient(getActivity()).getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                onLocationUpdated(location);
                            }
                        }
                    });
        }
    }

    private String buildDarkSkyUrl(Location location) {
        StringBuilder url = new StringBuilder();
        url.append("https://api.darksky.net/forecast");
        url.append("/" + BuildConfig.DarkSkyApiKey);
        url.append("/" + location.getLatitude() + "," + location.getLongitude());

        return url.toString();
    }

    private ArrayList<Day> createFillerData() {
        ArrayList<Day> days = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            days.add(new Day("empty", "empty", 0, 0, 0));
        }

        return days;
    }
}
package andykhov.sunshine;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class ForecastFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final int CHECK_PERMISSION_COARSE_LOCATION = 1;

    RecyclerView mForecastRecyclerView;
    RecyclerView.LayoutManager mForecastLayoutManager;
    ForecastAdapter mForecastAdapter;
    Location mCurrentLocation;
    ArrayList<Day> mDays;

    public ForecastFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);
        setupRecyclerView(rootView);

        return rootView;
    }

    @Override
    public void onConnected(Bundle connectionHint) {}

    @Override
    public void onConnectionSuspended(int cause) {}

    @Override
    public void onConnectionFailed(ConnectionResult result) {}

    private void setupRecyclerView(View rootView) {
        mForecastRecyclerView = (RecyclerView) rootView.findViewById(R.id.forecast_list);
        mForecastLayoutManager = new LinearLayoutManager(getContext());
        mForecastAdapter = new ForecastAdapter(mDays);
        mForecastRecyclerView.setLayoutManager(mForecastLayoutManager);
        mForecastRecyclerView.setAdapter(mForecastAdapter);
    }

    private void getForecastJSONData() {
        OkHttpClient httpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(buildDarkSkyUrl())
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
                    Forecast forecastData = gson.fromJson(response.body().string(), Forecast.class);
                    response.body().close();
                }
            }
        });
    }

    private String buildDarkSkyUrl() {
        StringBuilder url = new StringBuilder();
        url.append("https://api.darksky.net/forecast");
        url.append("/" + BuildConfig.DarkSkyApiKey);
        url.append("/" + mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude());

        return url.toString();
    }
}
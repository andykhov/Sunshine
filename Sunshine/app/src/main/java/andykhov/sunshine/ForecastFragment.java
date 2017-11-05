package andykhov.sunshine;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
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
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;

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
        setupGoogleApiClient();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();

        Log.e("onStart", "Here");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume", "here");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onPause", "here");
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        Log.e("onStop", "Here");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy", "here");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("onDetach", "here");
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        else
            requestLocationPermission();

        getForecastJSONData();
    }

    @Override
    public void onConnectionSuspended(int cause) {}

    @Override
    public void onConnectionFailed(ConnectionResult result) {}

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (requestCode == CHECK_PERMISSION_COARSE_LOCATION) {
            if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                mCurrentLocation = LocationServices.FusedLocationApi.
                        getLastLocation(mGoogleApiClient);
            }
        }
    }

    private void requestLocationPermission() {
        String[] permission = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(getActivity(), permission, CHECK_PERMISSION_COARSE_LOCATION);
    }

    private void setupRecyclerView(View rootView) {
        mForecastRecyclerView = (RecyclerView) rootView.findViewById(R.id.forecast_list);
        mForecastLayoutManager = new LinearLayoutManager(getContext());
        mForecastAdapter = new ForecastAdapter(createFillerData(50));
        mForecastRecyclerView.setLayoutManager(mForecastLayoutManager);
        mForecastRecyclerView.setAdapter(mForecastAdapter);
    }

    private void setupGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
        }
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
                    Log.e("response" , response.body().string());
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

    //temporary function to create filler data in the recycler view
    private String[] createFillerData(int size) {
        String[] forecastData = new String[size];

        for (int i = 0; i < size; i++) {
            forecastData[i] = "filler";
        }

        return forecastData;
    }
}
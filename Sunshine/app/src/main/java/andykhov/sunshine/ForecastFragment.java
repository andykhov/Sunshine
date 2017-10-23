package andykhov.sunshine;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by Andy on 10/14/17.
 */

public class ForecastFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks {

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
    public void onConnected(Bundle connectionHint) {
        if (!hasLocationPermission())
            requestLocationPermission();
    }

    @Override
    public void onConnectionSuspended(int cause) {}

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (requestCode == CHECK_PERMISSION_COARSE_LOCATION) {
            if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission(getContext(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                mCurrentLocation = LocationServices.FusedLocationApi.
                        getLastLocation(mGoogleApiClient);
            }
        }
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        String[] permission = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};

        ActivityCompat.requestPermissions(getActivity(), permission, CHECK_PERMISSION_COARSE_LOCATION);
    }

    private void setupRecyclerView(View rootView) {
        mForecastRecyclerView = (RecyclerView) rootView.findViewById(R.id.forecast_list);
        mForecastLayoutManager = new LinearLayoutManager(getContext());
        mForecastAdapter = new ForecastAdapter(createFillerData(20));
        mForecastRecyclerView.setLayoutManager(mForecastLayoutManager);
        mForecastRecyclerView.setAdapter(mForecastAdapter);
    }

    private void setupGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .enableAutoManage(getActivity(), null)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private String[] createFillerData(int size) {
        String[] forecastData = new String[size];

        for (int i = 0; i < size; i++) {
            forecastData[i] = "hot - 95f/85f - Wednesday";
        }

        return forecastData;
    }
}

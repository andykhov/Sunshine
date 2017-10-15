package andykhov.sunshine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ForecastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        if (findViewById(R.id.container) != null && savedInstanceState != null) {
            addForecastFragment();
        }
    }

    private void addForecastFragment() {
        ForecastFragment forecastFragment = new ForecastFragment();

        // pass Intent's extras if activity started with special instructions
        forecastFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().add(R.id.container, forecastFragment);
    }
}
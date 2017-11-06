package andykhov.sunshine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Andy on 11/4/17.
 */

public class Forecast {
    @SerializedName("daily")
    @Expose
    private DailyForecast dailyForecast;

    public DailyForecast getDailyForecast() {
        return dailyForecast;
    }
}

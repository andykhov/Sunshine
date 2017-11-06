package andykhov.sunshine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Andy on 11/5/17.
 */

public class DailyForecast {
    @SerializedName("summary")
    @Expose
    private String summary;

    @SerializedName("data")
    @Expose
    private ArrayList<DayForecast> dayForecastData;

    public String getSummary() {
        return summary;
    }

    public DayForecast getDayForecast(int index) {
        return dayForecastData.get(index);
    }

    public class DayForecast {
        @SerializedName("summary")
        @Expose
        private String summary;

        @SerializedName("icon")
        @Expose
        private String icon;

        @SerializedName("time")
        @Expose
        private int time;

        @SerializedName("temperatureHigh")
        @Expose
        private double tempHigh;

        @SerializedName("temperatureLow")
        @Expose
        private double tempLow;

        public String getSummary() {
            return summary;
        }

        public String getIcon() {
            return icon;
        }

        public int getTime() {
            return time;
        }

        public double getTempHigh() {
            return tempHigh;
        }

        public double getTempLow() {
            return tempLow;
        }
    }

}

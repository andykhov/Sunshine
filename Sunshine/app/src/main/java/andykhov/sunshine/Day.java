package andykhov.sunshine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Andy on 11/13/17.
 */

public class Day {
    @SerializedName("summary")
    @Expose
    public String summary;

    @SerializedName("icon")
    @Expose
    public String icon;

    @SerializedName("time")
    @Expose
    public int time;

    @SerializedName("temperatureHigh")
    @Expose
    public double tempHigh;

    @SerializedName("temperatureLow")
    @Expose
    public double tempLow;
}

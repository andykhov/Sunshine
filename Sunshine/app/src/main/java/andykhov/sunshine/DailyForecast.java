package andykhov.sunshine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Andy on 11/5/17.
 */

public class DailyForecast {
    @SerializedName("data")
    @Expose
    public ArrayList<Day> days;
}

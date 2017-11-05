package andykhov.sunshine;

/**
 * Created by Andy on 11/4/17.
 */

public class Forecast {
    private String summary, icon;
    private int tempHigh, tempLow;

    public Forecast(String summary, String icon, int tempHigh, int tempLow) {
        this.summary = summary;
        this.icon = icon;
        this.tempHigh = tempHigh;
        this.tempLow = tempLow;
    }


    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public int getTempHigh() {
        return tempHigh;
    }

    public int getTempLow() {
        return tempLow;
    }
}

package arc.haldun.hurda.database.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class ChargeMix {

    private String p01_name;
    private Double p02_unitRate;
    private Double p03_percentage;
    private Double p04_meltFactor;

    public ChargeMix(String p01_name, Double p02_unitRate, Double p03_percentage, Double p04_meltFactor) {
        this.p01_name = p01_name;
        this.p02_unitRate = p02_unitRate;
        this.p03_percentage = p03_percentage;
        this.p04_meltFactor = p04_meltFactor;
    }

    public ChargeMix(JSONObject jsonObject) {
        setP01_name(jsonObject.optString("p01_name"));
        setP02_unitRate(jsonObject.optDouble("p02_unit_rate"));
        setP03_percentage(jsonObject.optDouble("p03_percentage"));
        setP04_meltFactor(jsonObject.optDouble("p04_melt_factor"));
    }

    public String getP01_name() {
        return p01_name;
    }

    public void setP01_name(String p01_name) {
        this.p01_name = p01_name;
    }

    public Double getP02_unitRate() {
        return p02_unitRate;
    }

    public void setP02_unitRate(Double p02_unitRate) {
        this.p02_unitRate = p02_unitRate;
    }

    public Double getP03_percentage() {
        return p03_percentage;
    }

    public void setP03_percentage(Double p03_percentage) {
        this.p03_percentage = p03_percentage;
    }

    public Double getP04_meltFactor() {
        return p04_meltFactor;
    }

    public void setP04_meltFactor(Double p04_meltFactor) {
        this.p04_meltFactor = p04_meltFactor;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("p01_name", p01_name);
        jsonObject.put("p02_unit_rate", p02_unitRate);
        jsonObject.put("p03_percentage", p03_percentage);
        jsonObject.put("p04_melt_factor", p04_meltFactor);
        return jsonObject;
    }

    @Override
    public String toString() {
        try {
            return toJson().toString(4);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}

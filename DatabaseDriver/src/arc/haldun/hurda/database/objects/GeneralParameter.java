package arc.haldun.hurda.database.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class GeneralParameter {

    private String name;
    private double value;

    public GeneralParameter(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public GeneralParameter(JSONObject jsonObject) {
        this.name = jsonObject.optString("name");
        this.value = jsonObject.optDouble("value");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("value", value);
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

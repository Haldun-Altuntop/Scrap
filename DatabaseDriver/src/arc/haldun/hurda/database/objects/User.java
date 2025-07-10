package arc.haldun.hurda.database.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private int id;
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(JSONObject userJson) {

        id = userJson.optInt("id");
        name = userJson.optString("name");
        password = userJson.optString("password");
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JSONObject toJson() {

        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", id);
            jsonObject.put("name", name);
            jsonObject.put("password", password);

            return jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
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

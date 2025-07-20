package arc.haldun.hurda.server.context.user;

import arc.haldun.hurda.database.DatabaseManager;
import arc.haldun.hurda.database.objects.User;
import arc.haldun.hurda.server.SessionManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LoginContext implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            InputStream is = exchange.getRequestBody();
            byte[] requestData = is.readAllBytes();
            is.close();

            String requestString = new String(requestData);
            JSONObject requestJson = new JSONObject(requestString);

            String userName = requestJson.optString("user-name");
            String password = requestJson.optString("password");

            User user = new User(userName, password);

            boolean res = DatabaseManager.login(user);

            JSONObject responseJson = new JSONObject();
            responseJson.put("result", res);

            if (res) {
                String sessionId = SessionManager.instance.addSession(user);
                responseJson.put("session-id", sessionId);
            }

            String resposneString = responseJson.toString(4);
            byte[] responseData = resposneString.getBytes(StandardCharsets.UTF_8);

            exchange.sendResponseHeaders(200, responseData.length);

            OutputStream os = exchange.getResponseBody();
            os.write(responseData);
            os.close();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}

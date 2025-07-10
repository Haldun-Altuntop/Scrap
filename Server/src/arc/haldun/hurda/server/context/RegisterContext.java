package arc.haldun.hurda.server.context;

import arc.haldun.hurda.database.DatabaseManager;
import arc.haldun.hurda.database.OperationFailedException;
import arc.haldun.hurda.database.objects.User;
import arc.haldun.hurda.server.SessionManager;
import arc.haldun.hurda.server.Utilities;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class RegisterContext implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            InputStream is = exchange.getRequestBody();
            byte[] requestData = is.readAllBytes();
            is.close();
            System.out.println("istek okundu");

            String requestString = new String(requestData, StandardCharsets.UTF_8);

            JSONObject requestJson = new JSONObject(requestString);

            String userName = requestJson.getString("user-name");
            String password = requestJson.getString("password");
            System.out.println("kullanıcı okundu: " + userName);

            User user = new User(userName, password);

            DatabaseManager.addUser(user);
            String sessionId = SessionManager.instance.addSession(user);
            System.out.println("kullanıcı eklendi");

            JSONObject responseJson = new JSONObject();
            responseJson.put("session-id", sessionId);

            String responseString = responseJson.toString(4);
            byte[] responseData = responseString.getBytes(StandardCharsets.UTF_8);

            exchange.sendResponseHeaders(200, responseData.length);

            OutputStream os = exchange.getResponseBody();
            os.write(responseData);
            os.close();

            exchange.close();

        } catch (JSONException e) {
            Utilities.sendError(exchange, 400, "Invalid JSON: " + e.getMessage());
        } catch (OperationFailedException e) {
            Utilities.sendError(exchange, 500, "Operation failed: " + e.getMessage());
        } catch (Exception e) {
            Utilities.sendError(exchange, 500, "Internal server error: " + e.getMessage());
        } finally {
            exchange.close();
        }
    }
}

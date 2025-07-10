package arc.haldun.hurda.server.context;

import arc.haldun.hurda.database.DatabaseManager;
import arc.haldun.hurda.database.OperationFailedException;
import arc.haldun.hurda.database.objects.Scrap;
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
import java.sql.SQLException;

public class UpdateScrapContext implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            InputStream is = exchange.getRequestBody();
            byte[] requestData = is.readAllBytes();
            is.close();

            String requestStr = new String(requestData, StandardCharsets.UTF_8);

            JSONObject requestJson = new JSONObject(requestStr);
            String sessionId = requestJson.getString("session-id");

            boolean permitted = SessionManager.instance.has(sessionId);

            if (permitted) {
                Scrap scrap = new Scrap(requestJson.getJSONObject("scrap"));
                DatabaseManager.updateScrap(scrap);
            }

            JSONObject responseJson = new JSONObject();
            responseJson.put("permitted", permitted);

            byte[] responseData = Utilities.jsonToByteArray(responseJson);

            exchange.sendResponseHeaders(200, responseData.length);

            OutputStream os = exchange.getResponseBody();
            os.write(responseData);
            os.close();

        } catch (OperationFailedException e) {

            try {
                JSONObject responseJson = new JSONObject();
                responseJson.put("msg", e.getMessage());

                byte[] responseData = responseJson.toString().getBytes(StandardCharsets.UTF_8);

                exchange.sendResponseHeaders(200, responseData.length);

                OutputStream os = exchange.getResponseBody();
                os.write(responseData);
                os.close();

            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        } catch (JSONException e) {
            Utilities.sendError(exchange, 400, "Invalid JSON: " + e.getMessage());
        }  catch (SQLException e) {
            Utilities.sendError(exchange, 500, "Database error");
        } finally {
            exchange.close();
        }
    }
}

package arc.haldun.hurda.server.context.scrap;

import arc.haldun.hurda.database.DatabaseManager;
import arc.haldun.hurda.database.OperationFailedException;
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

public class DeleteScrapContext implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            InputStream is = exchange.getRequestBody();
            byte[] requestData = is.readAllBytes();
            is.close();

            String requestStr = new String(requestData, StandardCharsets.UTF_8);

            JSONObject requestJson = new JSONObject(requestStr);
            String sessionId = requestJson.optString("session-id", "");

            boolean permitted = SessionManager.instance.has(sessionId);

            if (permitted) {
                String scrapName = requestJson.getString("scrapName");
                DatabaseManager.deleteScrap(scrapName);
            }

            JSONObject responseJson = new JSONObject();
            responseJson.put("permitted", permitted);

            byte[] responseData = Utilities.jsonToByteArray(responseJson);

            exchange.sendResponseHeaders(200, responseData.length);

            OutputStream os = exchange.getResponseBody();
            os.write(responseData);
            os.close();

        } catch (JSONException | SQLException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
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
        } finally {
            exchange.close();
        }
    }
}

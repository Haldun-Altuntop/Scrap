package arc.haldun.hurda.server.context;

import arc.haldun.hurda.database.DatabaseManager;
import arc.haldun.hurda.database.OperationFailedException;
import arc.haldun.hurda.database.objects.Scrap;
import arc.haldun.hurda.server.SessionManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class GetScrapContext implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            InputStream is = exchange.getRequestBody();
            byte[] requestData = is.readAllBytes();
            is.close();

            String requestStr = new String(requestData, StandardCharsets.UTF_8);

            JSONObject requestJson = new JSONObject(requestStr);
            String scrapName = requestJson.getString("scrapName");
            String sessionId = requestJson.getString("session-id");

            boolean permitted = SessionManager.instance.has(sessionId);

            JSONObject responseJson = new JSONObject();
            responseJson.put("permitted", permitted);

            if (permitted) {
                Scrap[] scraps;

                if (scrapName.isEmpty()) {
                    scraps = DatabaseManager.getAllScraps();
                } else {
                    Scrap scrap = DatabaseManager.getScrap(scrapName);
                    scraps = new Scrap[] {scrap};
                }

                for (int i = 0; i < scraps.length; i++) {
                    Scrap scrap = scraps[i];

                    responseJson.put(String.valueOf(i), scrap.toJson());
                }
            }

            String responseStr = responseJson.toString();

            byte[] responseData = responseStr.getBytes(StandardCharsets.UTF_8);

            exchange.sendResponseHeaders(200, responseData.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseData);
            }

        } catch (JSONException | SQLException e) {
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

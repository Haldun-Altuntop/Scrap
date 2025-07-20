package arc.haldun.hurda.server.context.chargemix;

import arc.haldun.hurda.database.DatabaseManager;
import arc.haldun.hurda.database.OperationFailedException;
import arc.haldun.hurda.database.objects.ChargeMix;
import arc.haldun.hurda.server.SessionManager;
import arc.haldun.hurda.server.Utilities;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GetChargeMixContext implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            InputStream is = exchange.getRequestBody();
            byte[] requestData = is.readAllBytes();
            is.close();

            JSONObject requestObject = Utilities.byteArrayToJson(requestData);

            String sessionId = requestObject.getString("session-id");

            boolean permitted = SessionManager.instance.has(sessionId);

            JSONObject responseJson = new JSONObject();
            responseJson.put("permitted", permitted);

            if (permitted) {

                ChargeMix[] chargeMixes;

                String chargeMixName = requestObject.optString("charge-mix-name", "");

                if (chargeMixName.isEmpty()) { // Boşsa tüm cm'leri istiyordur
                    chargeMixes = DatabaseManager.getAllChargeMixes();
                } else {
                    // TODO: eğer lazım olursa tek tek almayı da kodla (IDatabase içinde yok)
                    chargeMixes = new ChargeMix[1];
                    chargeMixes[0] = new ChargeMix(
                            "temp",
                            0d,
                            0d,
                            0d
                    );
                }

                for (int i = 0; i < chargeMixes.length; i++) {
                    responseJson.put(String.valueOf(i),  chargeMixes[i].toJson());
                }
            }

            byte[] responseData = Utilities.jsonToByteArray(responseJson);

            exchange.sendResponseHeaders(200, responseData.length);

            OutputStream os = exchange.getResponseBody();
            os.write(responseData);
            os.close();

        } catch (JSONException e) {
            Utilities.sendError(exchange, 400, "Invalid JSON: " + e.getMessage());
        } catch (OperationFailedException e) {
            Utilities.sendError(exchange, 500, "Internal server error: " + e.getMessage());
        } finally {
            exchange.close();
        }
    }
}

package arc.haldun.hurda.server.context.generalparameter;

import arc.haldun.hurda.database.DatabaseManager;
import arc.haldun.hurda.server.SessionManager;
import arc.haldun.hurda.server.Utilities;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DeleteGeneralParameterContext implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            InputStream is = exchange.getRequestBody();
            byte[] requestData = is.readAllBytes();
            is.close();

            JSONObject requestJson = Utilities.byteArrayToJson(requestData);

            String sessionId = requestJson.getString("session-id");
            boolean permitted = SessionManager.instance.has(sessionId);

            JSONObject responseJson = new JSONObject();

            responseJson.put("permitted", permitted);

            if (permitted) {
                String parameterName = requestJson.getString("parameter-name");
                DatabaseManager.deleteGeneralParameter(parameterName);
            }

            byte[] responseData = Utilities.jsonToByteArray(responseJson);

            exchange.sendResponseHeaders(200, responseData.length);

            OutputStream os = exchange.getResponseBody();
            os.write(responseData);
            os.close();

        } catch (JSONException e) {
            Utilities.sendError(exchange, 400, "Invalid JSON: " + e.getMessage());
        } catch (Exception e) {
            Utilities.sendError(exchange, 500, "Internal server error: " + e.getMessage());
        } finally {
            exchange.close();
        }
    }
}

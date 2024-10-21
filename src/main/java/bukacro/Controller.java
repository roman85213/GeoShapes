package bukacro;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Controller {
    private final String URL = "https://medoro.cz/task/api/shape";

    public Controller() {
        String jsonData;
        try {
            jsonData = makeRequest();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        String fullData = calculateMissingValues(jsonData);
        ShapeGUI shapeGUI = new ShapeGUI(jsonData, fullData);
        shapeGUI.setTitle("Tvary");
        shapeGUI.pack();
        shapeGUI.setVisible(true);
    }

    public String makeRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return response.body();
    }

    private String fulfillData(String jsonData) {
        return calculateMissingValues(jsonData);
    }

    private String calculateMissingValues(String jsonData) {
        return jsonData;
    }
}

package bukacro;

import bukacro.model.Circle;
import bukacro.model.Rectangle;
import bukacro.model.Shape;
import bukacro.model.Triangle;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Controller {
    private final String URL = "https://medoro.cz/task/api/shape";

    public ArrayList<Shape> getShapes() throws JsonProcessingException {
        ArrayList<Shape> shapeList = new ArrayList<>();
        String jsonData;
        try {
            jsonData = makeRequest();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        Shape inputShape = objectMapper.readValue(jsonData, Shape.class);
        Shape fullShape = fulfillData(inputShape);
        shapeList.add(inputShape);
        shapeList.add(fullShape);
        return shapeList;
    }

    public String makeRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private Shape fulfillData(Shape inputShape) {
        if (inputShape.getType() != null) {
            return calculateMissingValues(inputShape);
        } else {
            String type = getTypeClass(inputShape);
            return new Shape(type, inputShape);
        }
    }

    private String getTypeClass(Shape inputShape) {
        if (inputShape.getA() != null)
            return "TRIANGLE";
        else if (inputShape.getHeight() != null)
            return "RECTANGLE";
        else if (inputShape.getRadius() != null)
            return "CIRCLE";
        else return null;
    }

    private Shape calculateMissingValues(Shape inputShape) {
        switch (inputShape.getType()) {
            case "RECTANGLE":
                if (inputShape.getWidth() == null)
                    return new Rectangle("RECTANGLE", inputShape.getArea() / inputShape.getHeight(), inputShape.getHeight(), inputShape.getArea(), inputShape.getCircumference());
                else if (inputShape.getHeight() == null)
                    return new Rectangle("RECTANGLE", inputShape.getWidth(), inputShape.getArea() / inputShape.getWidth(), inputShape.getArea(), inputShape.getCircumference());
                else if (inputShape.getArea() == null)
                    return new Rectangle("RECTANGLE", inputShape.getWidth(), inputShape.getHeight(), inputShape.getWidth() * inputShape.getHeight(), inputShape.getCircumference());
                else if (inputShape.getCircumference() == null)
                    return new Rectangle("RECTANGLE", inputShape.getWidth(), inputShape.getHeight(), inputShape.getWidth() * inputShape.getHeight(), 2 * (inputShape.getWidth() + inputShape.getHeight()));
                break;
            case "TRIANGLE":
                if (inputShape.getA() == null)
                    return new Triangle("TRIANGLE", inputShape.getCircumference() - inputShape.getB() - inputShape.getC(), inputShape.getB(), inputShape.getC(), inputShape.getArea(), inputShape.getCircumference());
                else if (inputShape.getB() == null)
                    return new Triangle("TRIANGLE", inputShape.getA(), inputShape.getCircumference() - inputShape.getA() - inputShape.getC(), inputShape.getC(), inputShape.getArea(), inputShape.getCircumference());
                else if (inputShape.getC() == null)
                    return new Triangle("TRIANGLE", inputShape.getA(), inputShape.getB(), inputShape.getCircumference() - inputShape.getA() - inputShape.getB(), inputShape.getArea(), inputShape.getCircumference());
                else if (inputShape.getArea() == null)
                    return new Triangle("TRIANGLE", inputShape.getA(), inputShape.getB(), inputShape.getC(), Math.sqrt(4 * Math.pow(inputShape.getA(), 2) * Math.pow(inputShape.getB(), 2) - Math.pow(Math.pow(inputShape.getA(), 2) + Math.pow(inputShape.getB(), 2) + Math.pow(inputShape.getC(), 2), 2)) / 4, inputShape.getCircumference());
                else if (inputShape.getCircumference() == null)
                    return new Triangle("TRIANGLE", inputShape.getA(), inputShape.getB(), inputShape.getC(), inputShape.getArea(), inputShape.getA() + inputShape.getB() + inputShape.getC());
                break;
            case "CIRCLE":
                if (inputShape.getRadius() == null)
                    return new Circle("CIRCLE", inputShape.getCircumference() / (2 * Math.PI), inputShape.getArea(), inputShape.getCircumference());
                if (inputShape.getArea() == null)
                    return new Circle("CIRCLE", inputShape.getRadius(), Math.PI * Math.pow(inputShape.getRadius(), 2), inputShape.getCircumference());
                if (inputShape.getCircumference() == null)
                    return new Circle("CIRCLE", inputShape.getRadius(), inputShape.getArea(), 2 * Math.PI * inputShape.getRadius());
                break;
        }
        return inputShape;
    }
}

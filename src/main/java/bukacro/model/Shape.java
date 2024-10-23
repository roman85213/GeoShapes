package bukacro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shape {
    private String type;
    private Double area, circumference, a, b, c, width, height, radius;

    public Shape(String type, Shape inputShape) {
        this.type = type;
        this.circumference = inputShape.circumference;
        this.a = inputShape.a;
        this.b = inputShape.b;
        this.c = inputShape.c;
        this.width = inputShape.width;
        this.height = inputShape.height;
        this.radius = inputShape.radius;
    }
}

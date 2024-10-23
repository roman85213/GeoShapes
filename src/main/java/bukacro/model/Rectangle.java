package bukacro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Rectangle extends Shape {
    private String type;
    private Double width, height, area, circumference;
}

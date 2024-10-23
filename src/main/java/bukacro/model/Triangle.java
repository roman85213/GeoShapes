package bukacro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Triangle extends Shape {
    private String type;
    private Double a, b, c, area, circumference;
}
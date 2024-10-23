package bukacro;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        Controller controller = new Controller();
        ShapeGUI shapeGUI = new ShapeGUI(controller);
        shapeGUI.setTitle("Tvary");
        shapeGUI.pack();
        shapeGUI.setVisible(true);
    }
}
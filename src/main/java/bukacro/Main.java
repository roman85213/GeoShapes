package bukacro;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        ShapeGUI shapeGUI = new ShapeGUI(controller);
        shapeGUI.setTitle("Tvary");
        shapeGUI.pack();
        shapeGUI.setVisible(true);
        System.exit(0);
    }
}
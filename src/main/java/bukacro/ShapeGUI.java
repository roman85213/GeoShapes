package bukacro;

import bukacro.model.Shape;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;

public class ShapeGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonMakeRequest;
    private JTextArea textAreaInput;
    private JTextArea textAreaFilled;
    private final Controller controller;

    public ShapeGUI(Controller controller) {
        this.controller = controller;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonMakeRequest);

        buttonMakeRequest.addActionListener(e -> {
            try {
                onMakeRequest();
            } catch (IOException | InterruptedException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> {
            try {
                onMakeRequest();
            } catch (IOException | InterruptedException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onMakeRequest() throws IOException, InterruptedException, IllegalAccessException {
        textAreaInput.setText("");
        textAreaFilled.setText("");
        ArrayList<Shape> shapes = controller.getShapes();
        writeShape(shapes.get(0), textAreaInput);
        writeShape(shapes.get(1), textAreaFilled);
    }

    private void writeShape(Shape inputShape, JTextArea textAreaInput) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Field[] fields = inputShape.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(inputShape) != null) {
                sb.append(String.format("%s: ", field.getName()));
                sb.append(field.get(inputShape));
                sb.append("\n");
            }
        }
        textAreaInput.append(sb.toString());
    }
}

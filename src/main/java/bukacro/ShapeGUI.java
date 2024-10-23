package bukacro;

import bukacro.model.Shape;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class ShapeGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonMakeRequest;
    private JTextArea textAreaInput;
    private JTextArea textAreaFilled;
    private Controller controller;

    public ShapeGUI(Controller controller) {
        this.controller = controller;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonMakeRequest);

        buttonMakeRequest.addActionListener(e -> {
            try {
                onMakeRequest();
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    onMakeRequest();
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> {
            try {
                onMakeRequest();
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onMakeRequest() throws IOException, InterruptedException {
        ArrayList<Shape> shapes = controller.getShapes();
        Shape inputShape = shapes.get(0);
        writeShape(inputShape, textAreaInput);
    }

    private void writeShape(Shape inputShape, JTextArea textAreaInput) {

    }
}

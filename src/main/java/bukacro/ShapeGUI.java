package bukacro;

import javax.swing.*;
import java.awt.event.*;

public class ShapeGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonMakeRequest;
    private JTextArea textAreaInput;
    private JTextArea textAreaFilled;
    private JButton buttonCancel;

    public ShapeGUI(String jsonData, String fullData) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonMakeRequest);

        buttonMakeRequest.addActionListener(e -> onMakeRequest());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onMakeRequest();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onMakeRequest(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onMakeRequest() {
        dispose();
    }
}

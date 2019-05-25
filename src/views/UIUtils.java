package views;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

final class UIUtils {

    static void addImageIcon(JButton button, String imagePath) {
        try {
            Image image = ImageIO.read(MainFrame.class.getResource(imagePath));
            button.setIcon(
                    new ImageIcon(image.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        } catch (Exception ignored) {
        }
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    static void addUndoFunctionality(JTextPane textPane) {
        final UndoManager undo = new UndoManager();
        Document document = textPane.getDocument();
        document.addUndoableEditListener(evt -> undo.addEdit(evt.getEdit()));
        textPane.getActionMap().put("Undo", new AbstractAction("Undo") {
            public void actionPerformed(ActionEvent evt) {
                try {
                    if (undo.canUndo())
                        undo.undo();
                } catch (CannotUndoException ignored) {
                }
            }
        });
        textPane.getInputMap().put(
                KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                        Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "Undo");
    }
}

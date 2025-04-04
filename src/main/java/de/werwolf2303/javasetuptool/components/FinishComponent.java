package de.werwolf2303.javasetuptool.components;

import de.werwolf2303.javasetuptool.Setup;
import de.werwolf2303.javasetuptool.swingextensions.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.util.HashMap;

/**
 * A component that is displayed when the user has finished the setup.
 * <br> <img alt="HTMLComponent" src="./doc-files/FinishComponent.png" />
 */
public class FinishComponent implements Component {
    JEditorPane pane;
    JImagePanel image;
    private JPanel contentPanel;

    @Override
    public void makeVisible(JFrame frame, JButton custom1, JButton custom2, JButton nextButton, JButton previousButton, JButton cancelButton, HashMap<String, Object> storage) {
        nextButton.setVisible(false);
        cancelButton.setText("Finish");
        contentPanel.setVisible(true);
    }

    @Override
    public void makeInvisible(JFrame frame, JButton custom1, JButton custom2, JButton nextButton, JButton previousButton, JButton cancelButton, HashMap<String, Object> storage) {
        contentPanel.setVisible(false);
    }

    @Override
    public void init(JFrame frame, int width, int height, HashMap<String, Object> setupVariables) {
        pane = new JEditorPane();
        pane.setBackground(new Color(0, 0, 0, 0));
        pane.setEditable(false);
        pane.setContentType("text/html");
        pane.setText("<h2 style='text-align:left'>Completing the " + setupVariables.getOrDefault("programname", "N/A") + " Setup Wizard</h2><br><br><br><a style='text-align:left'>Setup has finished installing " + setupVariables.getOrDefault("programname", "N/A") + " version " + setupVariables.getOrDefault("programversion", "N/A") + " <br>on your computer. The application may be launched by <br> selecting the installed icons.</a><br><br><a>Click Finish to exit Setup</a>");

        image = new JImagePanel();
        image.setBackground(new Color(0, 0, 0, 0));
        image.setMaximumSize(new Dimension(width, height));
        if (setupVariables.containsKey("programimage")) {
            image.setImage(new ByteArrayInputStream((byte[]) setupVariables.get("programimage")));
        } else {
            image.setImage(Setup.class.getResourceAsStream("/JSTBranding.png"));
        }

        image.setBounds(0, 0, image.getPreferredSize().width, height);

        pane.setBounds(image.getPreferredSize().width, 0, width - image.getPreferredSize().width, height);

        contentPanel.setLayout(null);
        contentPanel.add(image);
        contentPanel.add(pane);
    }

    @Override
    public boolean onNext() {
        return false;
    }

    @Override
    public boolean onPrevious() {
        return false;
    }

    @Override
    public void onCustom1() {

    }

    @Override
    public void onCustom2() {

    }

    @Override
    public JPanel getContainer() {
        return contentPanel;
    }

    private void createUIComponents() {
        image = new JImagePanel();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 0));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

}

package de.werwolf2303.javasetuptool.components;

import de.werwolf2303.javasetuptool.utils.StreamUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * A component that can be used to display HTML content.
 * <b>The rendered HTML is not interactive</b>
 * <br> <img alt="HTMLComponent" src="./doc-files/HTMLComponent.png" />
 */
public class HTMLComponent extends JScrollPane implements Component {
    boolean initialized = false;

    private JPanel contentPanel;

    private JEditorPane editorPane;

    {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(this);

        editorPane = new JEditorPane();
        editorPane.setBackground(new Color(0, 0, 0, 0));
        editorPane.setContentType("text/html");
        setViewportView(editorPane);
    }

    /**
     * Creates an instance of this class and displays the specified HTML
     *
     * @param html - HTML to load
     * @throws IOException - throws if the HTML could not be loaded
     */
    public HTMLComponent(String html) throws IOException {
        editorPane.setText(html);
    }

    /**
     * Creates an instance of this class and displays the specified url
     *
     * @param url - url to display
     * @throws IOException - throws if the url could not be loaded
     */
    public HTMLComponent(URL url) throws IOException {
        editorPane.setText(StreamUtils.inputStreamToString(url.openStream()));
    }

    @Override
    public void makeVisible(JFrame frame, JButton custom1, JButton custom2, JButton nextButton, JButton previousButton, JButton cancelButton, HashMap<String, Object> storage) {
        setVisible(true);
    }

    @Override
    public void makeInvisible(JFrame frame, JButton custom1, JButton custom2, JButton nextButton, JButton previousButton, JButton cancelButton, HashMap<String, Object> storage) {
        setVisible(false);
    }

    @Override
    public void init(JFrame frame, int width, int height, HashMap<String, Object> setupVariables) {
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
}

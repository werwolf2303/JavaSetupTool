package de.werwolf2303.javasetuptool.components;

import de.werwolf2303.javasetuptool.PublicValues;
import de.werwolf2303.javasetuptool.Setup;
import de.werwolf2303.javasetuptool.utils.StreamUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class HTMLComponent extends JPanel implements Component{
    String defaultHTML = "<a>Load HTML with the 'load()' function</a>";
    JEditorPane pane;

    public HTMLComponent() {
        pane = new JEditorPane();
        pane.setEditable(false);
        pane.setContentType("text/html");
        pane.setText(defaultHTML);
        add(pane, BorderLayout.CENTER);
    }

    public String getName() {
        return "HTMLComponent";
    }

    public JPanel drawable() {
        return this;
    }

    public void init() {
        pane.setPreferredSize(new Dimension(PublicValues.setup_width, PublicValues.setup_height - PublicValues.setup_bar_height));
    }

    public void nowVisible() {

    }

    public void onLeave() {

    }

    public void giveComponents(JButton next, JButton previous, JButton cancel, JButton custom1, JButton custom2, Runnable fin, Setup.SetupBuilder builder) {

    }

    public void load(String html) {
        pane.setText(html);
    }

    public void load(URL url) {
        try {
            pane.setText(StreamUtils.inputStreamToString(url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

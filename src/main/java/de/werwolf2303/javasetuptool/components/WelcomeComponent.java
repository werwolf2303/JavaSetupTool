package de.werwolf2303.javasetuptool.components;

import de.werwolf2303.javasetuptool.PublicValues;
import de.werwolf2303.javasetuptool.Setup;
import de.werwolf2303.javasetuptool.utils.Resources;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WelcomeComponent extends JPanel implements PrivateComponent {
    Setup setup;
    JEditorPane pane;
    JLabel image;
    InputStream imageStream = null;
    Setup.SetupBuilder builder;
    public WelcomeComponent(Setup setup) {
        this.setup = setup;
        setBackground(Color.white);
        pane = new JEditorPane();
        pane.setEditable(false);
        pane.setContentType("text/html");
        image = new JLabel();
        add(image, BorderLayout.WEST);
        add(pane, BorderLayout.EAST);
    }

    public void setImage(InputStream stream) {
        this.imageStream = stream;
    }

    public String getName() {
        return "FirstPageComponent";
    }
    public JPanel drawable() {
        return this;
    }

    public void init() {
        image.setPreferredSize(new Dimension(PublicValues.setup_width / 100 * 25, PublicValues.setup_height - PublicValues.setup_bar_height));
        pane.setPreferredSize(new Dimension(PublicValues.setup_width - PublicValues.setup_width / 100 * 25, PublicValues.setup_height - PublicValues.setup_bar_height));
        pane.setText("<h2 style='text-align:left'>Welcome to the " + setup.getProgramName() + " Setup Wizard</h2><br><br><br><a style='text-align:left'>This will install " + setup.getProgramName() + " version " + setup.getProgramVersion() + " on your computer.</a><br><br><a>Click next to continue, or cancel to exit Setup</a>");
        try {
            ImageIcon imageIcon = new ImageIcon(ImageIO.read(builder.imageStream));
            Image img = imageIcon.getImage();
            Image newimg = img.getScaledInstance(image.getWidth(), image.getHeight(),  java.awt.Image.SCALE_SMOOTH);
            image.setIcon(new ImageIcon(newimg));
        } catch (IOException ignored) {
        }
        repaint();
    }

    public void nowVisible() {

    }

    public void onLeave() {

    }

    public void giveComponents(JButton next, JButton previous, JButton cancel, JButton custom1, JButton custom2, Runnable fin, Setup.SetupBuilder builder) {
        this.builder = builder;
    }
}

package de.werwolf2303.javasetuptool.components;

import de.werwolf2303.javasetuptool.PublicValues;
import de.werwolf2303.javasetuptool.Setup;
import de.werwolf2303.javasetuptool.utils.Resources;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;
import java.io.*;

public class FinishComponent extends JPanel implements PrivateComponent {
    Setup setup;
    JEditorPane pane;
    JLabel image;
    InputStream stream = null;
    Setup.SetupBuilder builder;
    public FinishComponent(Setup setup) {
        this.setup = setup;
        setBackground(Color.white);
        pane = new JEditorPane();
        pane.setEditable(false);
        pane.setContentType("text/html");
        image = new JLabel();
        setLayout(null);
        add(image);
        add(pane);
    }

    public void setImage(InputStream stream) {
        this.stream = stream;
    }

    public String getName() {
        return "FirstPageComponent";
    }
    public JPanel drawable() {
        return this;
    }

    public void init() {
        setPreferredSize(new Dimension(PublicValues.setup_width, PublicValues.setup_height - PublicValues.setup_bar_height));
        //image.setPreferredSize(new Dimension(PublicValues.setup_width / 100 * 25, PublicValues.setup_height - PublicValues.setup_bar_height));
        image.setBounds(0, 0, PublicValues.setup_width / 100 * 25, PublicValues.setup_height - PublicValues.setup_bar_height);
        pane.setBounds((int) (image.getBounds().getX() + image.getBounds().getWidth() + 4), 0, (int) (PublicValues.setup_width - image.getBounds().getX() + image.getBounds().getWidth() + 4), PublicValues.setup_height - PublicValues.setup_bar_height);
        //pane.setPreferredSize(new Dimension(PublicValues.setup_width - PublicValues.setup_width / 100 * 25, PublicValues.setup_height - PublicValues.setup_bar_height));
        pane.setText("<h2 style='text-align:left'>Completing the " + setup.getProgramName() + " Setup Wizard</h2><br><br><br><a style='text-align:left'>Setup has finished installing " + setup.getProgramName() + " version " + setup.getProgramVersion() + " <br>on your computer. The application may be launched by <br> selecting the installed icons.</a><br><br><a>Click Finish to exit Setup</a>");
        if(image.getIcon() == null) {
            try {
                ImageIcon imageIcon = new ImageIcon(ImageIO.read(stream));
                Image img = imageIcon.getImage();
                Image newimg = img.getScaledInstance((int) image.getBounds().getWidth(), (int) image.getBounds().getHeight(), java.awt.Image.SCALE_SMOOTH);
                image.setIcon(new ImageIcon(newimg));
            } catch (IOException ignored) {
            }
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

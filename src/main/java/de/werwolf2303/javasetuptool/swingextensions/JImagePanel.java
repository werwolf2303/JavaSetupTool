package de.werwolf2303.javasetuptool.swingextensions;

import de.werwolf2303.javasetuptool.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class JImagePanel extends JPanel {
    private static final Logger log = LoggerFactory.getLogger(JImagePanel.class);
    private BufferedImage image = null;
    private byte[] imagebytes;
    public boolean keepAspectRatio = true;
    private int width,height = 0;

    public void setKeepAspectRatio(boolean keepAspectRatio) {
        this.keepAspectRatio = keepAspectRatio;
        refresh();
    }

    void refresh() {
        try {
            image = ImageIO.read(new ByteArrayInputStream(imagebytes));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.repaint();
    }

    public void setImage(InputStream inputStream) {
        try {
            imagebytes = StreamUtils.inputStreamToByteArray(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        refresh();
    }

    public InputStream getImageStream() {
        if (imagebytes == null || imagebytes.length == 0) {
            return null;
        }
        return new ByteArrayInputStream(imagebytes);
    }

    private void drawImage(Graphics graphics2D, BufferedImage image) {
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        int desiredWidth = this.getWidth();
        int desiredHeight = this.getHeight();
        double originalAspectRatio = (double) originalWidth / originalHeight;
        double desiredAspectRatio = (double) desiredWidth / desiredHeight;
        int newWidth, newHeight;
        int xOffset, yOffset;
        if (originalAspectRatio > desiredAspectRatio) {
            newWidth = desiredWidth;
            newHeight = (int) (desiredWidth / originalAspectRatio);
            xOffset = 0;
            yOffset = (desiredHeight - newHeight) / 2;
        } else {
            newWidth = (int) (desiredHeight * originalAspectRatio);
            newHeight = desiredHeight;
            xOffset = (desiredWidth - newWidth) / 2;
            yOffset = 0;
        }
        width = newWidth - xOffset;
        height = newHeight - yOffset;
        graphics2D.drawImage(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, yOffset, null);
    }

    @Override
    public Dimension getPreferredSize() {
        if(width != 0 && height != 0) {
            return new Dimension(width, height);
        }else {
            if(imagebytes == null) return new Dimension(0, 0);
            try {
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(imagebytes));
                int originalWidth = img.getWidth();
                int originalHeight = img.getHeight();
                double aspectRatio = (double) originalWidth / originalHeight;
                int newWidth = (int) (getMaximumSize().height * aspectRatio);
                return new Dimension(newWidth, getMaximumSize().height);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
            return;
        }
        if(this.keepAspectRatio) {
            drawImage(g, image);
        } else {
            assert g != null;
            g.drawImage(image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
        }
    }
}

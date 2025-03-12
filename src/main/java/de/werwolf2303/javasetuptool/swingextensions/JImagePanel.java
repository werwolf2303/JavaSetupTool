package de.werwolf2303.javasetuptool.swingextensions;

import de.werwolf2303.javasetuptool.utils.StreamUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class JImagePanel extends JPanel {
    private BufferedImage image = null;
    private byte[] imagebytes;
    public boolean keepAspectRatio = true;

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
        graphics2D.drawImage(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), xOffset, yOffset, null);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension dimension = new Dimension(0, 0);
        try {
            if(imagebytes == null) {
                return dimension;
            }
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imagebytes));
            dimension.setSize(img.getWidth(), img.getHeight());
        }catch (IOException e) {
            e.printStackTrace();
        }
        return dimension;
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

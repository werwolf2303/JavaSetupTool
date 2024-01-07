package de.werwolf2303.javasetuptool.components;

import de.werwolf2303.javasetuptool.Setup;

import javax.swing.*;
import java.awt.*;

public class CanvasComponent extends JPanel implements Component {
    @FunctionalInterface
    public interface DrawEvent {
        void trigger(Graphics g);
    }

    DrawEvent event = g -> {

    };

    public String getName() {
        return "CanvasComponent";
    }

    public void setOnDraw(DrawEvent event) {
        this.event = event;
    }

    public JPanel drawable() {
        return this;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.event.trigger(g);
    }

    public void init() {

    }

    public void nowVisible() {

    }

    public void onLeave() {

    }

    public void giveComponents(JButton next, JButton previous, JButton cancel, JButton custom1, JButton custom2, Runnable fin, Setup.SetupBuilder builder) {

    }
}

package de.werwolf2303.javasetuptool.components;

import de.werwolf2303.javasetuptool.Setup;

import javax.swing.*;

public class CanvasComponent extends JPanel implements Component {
    public String getName() {
        return "Canvas";
    }

    public JPanel drawable() {
        return this;
    }

    public void init() {

    }

    public void nowVisible() {

    }

    public void onLeave() {

    }

    public void giveComponents(JButton next, JButton previous, JButton cancel, JButton custom1, JButton custom2, Runnable fin, Setup.SetupBuilder builder) {

    }

    public void giveComponents(JButton next, JButton previous, JButton cancel, JButton custom1, JButton custom2, Runnable fin) {

    }
}

package de.werwolf2303.javasetuptool.components;

import de.werwolf2303.javasetuptool.swingextensions.ComponentViewManager;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Component that contains options for selecting the setup type.
 * <br>e.g. Typical, Custom, Complete
 * <br> <img alt="HTMLComponent" src="./doc-files/SetupTypeComponent.png" />
 */
public class SetupTypeComponent extends JPanel implements Component {
    //https://i.stack.imgur.com/845qn.jpg
    final ArrayList<Component> typicalcomponents = new ArrayList<>();
    final ArrayList<Component> customcomponents = new ArrayList<>();
    final ArrayList<Component> completecomponents = new ArrayList<>();
    boolean showTypical = false;
    boolean showComplete = false;
    boolean showCustom = false;
    HashMap<String, Object> storage;
    ComponentViewManager viewManager;
    JPanel selectionContent;
    JButton custom1;
    JButton custom2;
    JButton next;
    JButton cancel;
    JButton previous;
    JFrame frame;
    private JRadioButton custombutton;
    private JRadioButton completebutton;
    private JRadioButton typicalbutton;
    private JLabel completelabel;
    private JLabel typicallabel;
    private JPanel customPanel;
    private JPanel completePanel;
    private JPanel typicalPanel;
    private JLabel customText;

    @Override
    public void makeVisible(JFrame frame, JButton custom1, JButton custom2, JButton nextButton, JButton previousButton, JButton cancelButton, HashMap<String, Object> storage) {
        this.storage = storage;
        this.custom1 = custom1;
        this.custom2 = custom2;
        this.next = nextButton;
        this.cancel = cancelButton;
        this.frame = frame;
        this.previous = previousButton;
        setVisible(true);
    }

    @Override
    public void makeInvisible(JFrame frame, JButton custom1, JButton custom2, JButton nextButton, JButton previousButton, JButton cancelButton, HashMap<String, Object> storage) {
        setVisible(false);
    }

    @Override
    public void init(JFrame frame, int width, int height, HashMap<String, Object> setupVariables) {
        setLayout(new BorderLayout());

        selectionContent = new JPanel();
        selectionContent.setLayout(new BoxLayout(selectionContent, BoxLayout.Y_AXIS));
        selectionContent.add(Box.createVerticalGlue());

        customPanel = new JPanel();
        customPanel.setLayout(new BorderLayout(7, 0));
        customPanel.setVisible(false);

        custombutton = new JRadioButton("Custom");
        custombutton.setFont(getFont().deriveFont(Font.BOLD));
        customPanel.add(custombutton, BorderLayout.WEST);

        customText = new JLabel("Choose which program features you want installed and where they will be installed");
        customText.setBackground(new Color(0, 0, 0, 0));
        customPanel.add(customText, BorderLayout.CENTER);

        typicalPanel = new JPanel();
        typicalPanel.setLayout(new BorderLayout(7, 0));
        typicalPanel.setVisible(false);

        typicalbutton = new JRadioButton("Typical");
        typicalbutton.setFont(getFont().deriveFont(Font.BOLD));
        typicalPanel.add(typicalbutton, BorderLayout.WEST);

        typicallabel = new JLabel("Common program features will be installed");
        typicallabel.setFont(customText.getFont());
        typicalPanel.add(typicallabel, BorderLayout.CENTER);

        completePanel = new JPanel();
        completePanel.setLayout(new BorderLayout(7, 0));
        completePanel.setVisible(false);

        completebutton = new JRadioButton("Complete");
        completebutton.setFont(getFont().deriveFont(Font.BOLD));
        completePanel.add(completebutton, BorderLayout.WEST);

        completelabel = new JLabel("All program features will be installed. (Requires the most disk space.)");
        completelabel.setFont(customText.getFont());
        completePanel.add(completelabel, BorderLayout.CENTER);

        viewManager = new ComponentViewManager();
        viewManager.addPanel(selectionContent);
        selectionContent.setVisible(true);

        add(viewManager, BorderLayout.CENTER);

        if (showComplete) {
            completePanel.setVisible(true);
            completebutton.addActionListener(e -> {
                typicalbutton.setSelected(false);
                custombutton.setSelected(false);

                viewManager.removeAllComponents();
                viewManager.addPanel(selectionContent);
                for (Component component : completecomponents) {
                    viewManager.addComponent(component);
                    component.init(frame, width, height, setupVariables);
                }
                selectionContent.setVisible(true);

                storage.put("setuptype", SetupType.COMPLETE);
            });
            selectionContent.add(completePanel);
            selectionContent.add(Box.createVerticalGlue());
        }

        if (showTypical) {
            typicalPanel.setVisible(true);
            typicalbutton.addActionListener(e -> {
                custombutton.setSelected(false);
                completebutton.setSelected(false);

                viewManager.removeAllComponents();
                viewManager.addPanel(selectionContent);
                for (Component component : typicalcomponents) {
                    viewManager.addComponent(component);
                    component.init(frame, width, height, setupVariables);
                }
                selectionContent.setVisible(true);

                storage.put("setuptype", SetupType.TYPICAL);
            });
            selectionContent.add(typicalPanel);
            selectionContent.add(Box.createVerticalGlue());
        }

        if (showCustom) {
            customPanel.setVisible(true);
            custombutton.addActionListener(e -> {
                typicalbutton.setSelected(false);
                completebutton.setSelected(false);

                viewManager.removeAllComponents();
                viewManager.addPanel(selectionContent);
                for (Component component : customcomponents) {
                    viewManager.addComponent(component);
                    component.init(frame, width, height, setupVariables);
                }
                selectionContent.setVisible(true);

                storage.put("setuptype", SetupType.CUSTOM);
            });
            selectionContent.add(customPanel);
            selectionContent.add(Box.createVerticalGlue());
        }
    }

    @Override
    public boolean onNext() {
        if (!typicalbutton.isSelected() && !completebutton.isSelected() && !custombutton.isSelected()) {
            return true;
        }
        viewManager.next(frame, custom1, custom2, next, previous, cancel, storage);
        return viewManager.canGoNext();
    }

    @Override
    public boolean onPrevious() {
        viewManager.previous(frame, custom1, custom2, next, previous, cancel, storage);
        return viewManager.canGoPrevious();
    }

    @Override
    public void onCustom1() {

    }

    @Override
    public void onCustom2() {

    }

    @Override
    public JPanel getContainer() {
        return this;
    }

    /**
     * The available setup types
     */
    public enum SetupType {
        COMPLETE,
        TYPICAL,
        CUSTOM
    }

    /**
     * This method makes the custom option available with the components
     * specified in the argument
     *
     * @param components - The components to be displayed when custom was selected
     * @return the class itself
     */
    public SetupTypeComponent buildCustom(Component... components) {
        customcomponents.addAll(Arrays.asList(components));
        showCustom = true;
        return this;
    }

    /**
     * This method makes the typical option available with the components
     * specified in the argument
     *
     * @param components - The components to be displayed when typical was selected
     * @return the class itself
     */
    public SetupTypeComponent buildTypical(Component... components) {
        typicalcomponents.addAll(Arrays.asList(components));
        showTypical = true;
        return this;
    }

    /**
     * This method makes the complete option available with the components
     * specified in the argument
     *
     * @param components - The components to be displayed when complete was selected
     * @return the class itself
     */
    public SetupTypeComponent buildComplete(Component... components) {
        completecomponents.addAll(Arrays.asList(components));
        showComplete = true;
        return this;
    }
}

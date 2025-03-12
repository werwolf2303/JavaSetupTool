package de.werwolf2303.javasetuptool.swingextensions;

import de.werwolf2303.javasetuptool.components.Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to manage the component (Page) view
 */
public class ComponentViewManager extends JPanel {
    private final ArrayList<Element> components = new ArrayList<>();
    private int currentComponent = 0;

    /**
     * The type of the element
     * <br> e.g. PAGE or PANEL
     */
    public enum ElementType {
        PAGE,
        PANEL
    }

    /**
     * A class that represents an element in the view manager
     */
    public static class Element {
        /**
         * The component that is displayed
         * <br> Only when ElementType is PAGE
         * <br> Can be null
         */
        public Component component;
        /**
         * The JPanel that is displayed
         * <br> Only when ElementType is PANEL
         * <br> Can be null
         */
        public JPanel panel;
        /**
         * The type of this element
         * <br> This is used to determine if {@link #component} should be displayed or {@link #panel}
         */
        public ElementType type;

        /**
         * Constructor
         *
         * @param component - The component that is displayed in this element
         * @param panel - The JPanel that contains the element's content
         * @param type - The type of the element
         */
        public Element(Component component, JPanel panel, ElementType type) {
            this.component = component;
            this.panel = panel;
            this.type = type;
        }
    }

    /**
     * Constructor
     * @param components - List of displayable components
     */
    public ComponentViewManager(Component[] components) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for(Component component : components) {
            this.components.add(new Element(component, null, ElementType.PAGE));
            add(component.getContainer());
            component.getContainer().setVisible(false);
        }

        this.components.get(currentComponent).component.getContainer().setVisible(true);
    }

    /**
     * Constructor
     */
    public ComponentViewManager() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Add a component (Page) to the view manager
     * @param component - Component to add
     */
    public void addComponent(Component component) {
        components.add(new Element(component, null, ElementType.PAGE));
        add(component.getContainer());
        component.getContainer().setVisible(false);
    }

    /**
     * Add a panel to the view manager
     * @param panel - Panel to add
     */
    public void addPanel(JPanel panel) {
        components.add(new Element(null, panel, ElementType.PANEL));
        add(panel);
        panel.setVisible(false);
    }

    /**
     * Method to go to the next component (Page)
     * @param frame - The JFrame of the setup
     * @param custom1 - The custom 1 button
     * @param custom2 - The custom 2 button
     * @param nextButton - The next button
     * @param previousButton - The previous button
     * @param cancelButton - The cancel button
     * @param storage - The storage HashMap
     */
    public void next(
            JFrame frame,
            JButton custom1,
            JButton custom2,
            JButton nextButton,
            JButton previousButton,
            JButton cancelButton,
            HashMap<String, Object> storage
    ) {
        if(currentComponent == components.size()) {
            return;
        }
        switchComponent(frame, custom1, custom2, nextButton, previousButton, cancelButton, storage, true);
    }

    /**
     * Method to check if you can go to the next component (Page)
     * @return - true if you can go to the next component (Page)
     */
    public boolean canGoNext() {
        return currentComponent == components.size();
    }

    /**
     * Method to switch the current component (Prevents code duplication)
     * @param frame - The JFrame of the setup
     * @param custom1 - The custom 1 button
     * @param custom2 - The custom 2 button
     * @param nextButton - The next button
     * @param previousButton - The previous button
     * @param cancelButton - The cancel button
     * @param storage - The storage HashMap
     * @param forwards - True if you want to navigate forwards, otherwise backwards
     */
    private void switchComponent( JFrame frame,
                                  JButton custom1,
                                  JButton custom2,
                                  JButton nextButton,
                                  JButton previousButton,
                                  JButton cancelButton,
                                  HashMap<String, Object> storage,
                                  boolean forwards) {
        Element previous = components.get(currentComponent);
        switch (previous.type) {
            case PAGE:
                previous.component.makeInvisible(frame, custom1, custom2, nextButton, previousButton, cancelButton, storage);
                previous.component.getContainer().setVisible(false);
                break;
            case PANEL:
                previous.panel.setVisible(false);
        }
        if(forwards) {
            currentComponent++;
        }else{
            currentComponent--;
        }
        Element next = components.get(currentComponent);
        switch (next.type) {
            case PAGE:
                next.component.makeVisible(frame, custom1, custom2, nextButton, previousButton, cancelButton, storage);
                next.component.getContainer().setVisible(true);
                break;
            case PANEL:
                next.panel.setVisible(true);
        }
    }

    /**
     * Method to go to the previous component (Page)
     * @param frame - The JFrame of the setup
     * @param custom1 - The custom 1 button
     * @param custom2 - The custom 2 button
     * @param nextButton - The next button
     * @param previousButton - The previous button
     * @param cancelButton - The cancel button
     * @param storage - The storage HashMap
     */
    public void previous(
            JFrame frame,
            JButton custom1,
            JButton custom2,
            JButton nextButton,
            JButton previousButton,
            JButton cancelButton,
            HashMap<String, Object> storage
    ) {
        if(currentComponent == 0) {
            return;
        }
        switchComponent(frame, custom1, custom2, nextButton, previousButton, cancelButton, storage, false);
    }

    /**
     * Method to remove everything added to this view manager
     */
    public void removeAllComponents() {
        components.clear();
    }

    /**
     * Method to check if you can go to the previous component (Page)
     * @return - true if you can go to the previous component (Page)
     */
    public boolean canGoPrevious() {
        return currentComponent != 0;
    }

    /**
     * Method to get the current component
     * @return - the current component
     */
    public Element getCurrentComponent() {
        return components.get(currentComponent);
    }
}

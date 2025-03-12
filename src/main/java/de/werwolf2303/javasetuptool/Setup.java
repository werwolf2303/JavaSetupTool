package de.werwolf2303.javasetuptool;

import com.alee.laf.WebLookAndFeel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import de.werwolf2303.javasetuptool.components.Component;
import de.werwolf2303.javasetuptool.components.FinishComponent;
import de.werwolf2303.javasetuptool.components.WelcomeComponent;
import de.werwolf2303.javasetuptool.swingextensions.ComponentViewManager;
import de.werwolf2303.javasetuptool.utils.StreamUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Use Setup.Builder to construct a Setup instance
 */
@SuppressWarnings("unused")
public class Setup extends JFrame {
    /**
     * Instance of this class as variable
     */
    private Setup setup;
    /**
     * The logger of the setup
     */
    public static Logger logger = Logger.getLogger("JavaSetupTool");
    /**
     * The component view manager for managing the components (Pages)
     */
    private ComponentViewManager viewManager;
    /**
     * The content of the setup frame
     */
    private JPanel contentPanel;
    /**
     * The cancel button of the setup
     */
    private JButton cancelButton;
    /**
     * The ok button of the setup
     */
    private JButton nextButton;
    /**
     * The custom button 2 of the setup
     */
    private JButton custom2;
    /**
     * The custom button 1'of the setup
     */
    private JButton custom1;
    /**
     * The setup navigation container
     */
    private JPanel navigationPanel;
    /**
     * The setup navigation controls container
     */
    private JPanel navigationControlsPanel;
    /**
     * The previous button of the setup
     */
    private JButton previousButton;
    /**
     * The area where the components are displayed
     */
    private JPanel componentArea;
    /**
     * The values of the setup
     */
    private HashMap<String, Object> values;
    /**
     * The storage where components can put variables
     */
    private HashMap<String, Object> componentStorage;

    /**
     * Use Setup.Builder to construct a Setup instance
     */
    private Setup(HashMap<String, Object> values) {
        this.values = values;
        this.componentStorage = new HashMap<>();
        this.setup = this;

        try {
            if (values.containsKey("lookandfeel")) {
                try {
                    UIManager.setLookAndFeel((LookAndFeel) values.get("lookandfeel"));
                } catch (UnsupportedLookAndFeelException e) {
                    logger.warning("The custom look and feel is not supported on your operating system. Falling back to WebLookAndFeel");
                    UIManager.setLookAndFeel(new WebLookAndFeel());
                }
            } else {
                UIManager.setLookAndFeel(new WebLookAndFeel());
            }
        } catch (UnsupportedLookAndFeelException e) {
            logger.warning("WebLookAndFeel is not supported on your operating system. Falling back to Metal");
        }

        setTitle(values.getOrDefault("programname", "N/A") + " - " + values.getOrDefault("programversion", "N/A"));
        setContentPane(contentPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (values.containsKey("onfinish")) {
                    ((Runnable) values.get("onfinish")).run();
                }
            }
        });

        viewManager = new ComponentViewManager(((ArrayList<Component>) values.get("components")).toArray(new Component[0]));
        componentArea.add(viewManager, BorderLayout.CENTER);

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewManager.getCurrentComponent().component.onPrevious()) {
                    return; // Component handles the previous button
                }
                viewManager.previous(setup, custom1, custom2, nextButton, previousButton, cancelButton, componentStorage);
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewManager.getCurrentComponent().component.onNext()) {
                    return; // Component handles the next button
                }
                viewManager.next(setup, custom1, custom2, nextButton, previousButton, cancelButton, componentStorage);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        custom1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManager.getCurrentComponent().component.onCustom1();
            }
        });

        custom2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManager.getCurrentComponent().component.onCustom2();
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * 
     */
    private void $$$setupUI$$$() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 0));
        contentPanel.setMinimumSize(new Dimension(600, 400));
        contentPanel.setPreferredSize(new Dimension(600, 400));
        contentPanel.setRequestFocusEnabled(true);
        navigationPanel = new JPanel();
        navigationPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPanel.add(navigationPanel, BorderLayout.SOUTH);
        navigationControlsPanel = new JPanel();
        navigationControlsPanel.setLayout(new GridLayoutManager(1, 7, new Insets(10, 10, 10, 10), -1, -1));
        navigationPanel.add(navigationControlsPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        custom2 = new JButton();
        custom2.setText("Button");
        custom2.setVisible(false);
        navigationControlsPanel.add(custom2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        navigationControlsPanel.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        custom1 = new JButton();
        custom1.setText("Button");
        custom1.setVisible(false);
        navigationControlsPanel.add(custom1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nextButton = new JButton();
        nextButton.setMargin(new Insets(0, 0, 0, 0));
        nextButton.setText("Next >");
        navigationControlsPanel.add(nextButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        navigationControlsPanel.add(cancelButton, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        navigationControlsPanel.add(spacer2, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(5, -1), new Dimension(5, -1), new Dimension(5, -1), 0, false));
        previousButton = new JButton();
        previousButton.setDoubleBuffered(false);
        previousButton.setHorizontalAlignment(0);
        previousButton.setSelected(false);
        previousButton.setText("< Previous");
        previousButton.setVisible(false);
        navigationControlsPanel.add(previousButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        separator1.setForeground(new Color(-10921121));
        separator1.setMaximumSize(new Dimension(32767, 1));
        separator1.setMinimumSize(new Dimension(-1, 1));
        separator1.setPreferredSize(new Dimension(-1, 1));
        navigationPanel.add(separator1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 1), new Dimension(-1, 1), new Dimension(-1, 1), 0, false));
        componentArea = new JPanel();
        componentArea.setLayout(new BorderLayout(0, 0));
        componentArea.setAlignmentX(0.0f);
        componentArea.setAlignmentY(0.0f);
        contentPanel.add(componentArea, BorderLayout.CENTER);
    }

    /**
     * 
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

    /**
     * Setup instance builder
     */
    public static class Builder {
        /**
         * The hashmap which stores the variables of the builder (Format: String, Object)
         */
        private final HashMap<String, Object> values = new HashMap<>();

        /**
         * The image of the program to be installed (Displayed on the left side of the first page)
         *
         * @param image - Can be an input stream, a resource path or an url (String)
         * @return the builder instance
         * @throws IOException - Thrown if an error occurs while converting the image object into a byte array
         */
        public Builder setProgramImage(Object image) throws IOException {
            values.put("programimage", StreamUtils.inputStreamToByteArray(StreamUtils.imageObjectToInputStream(image)));
            return this;
        }

        /**
         * The icon of the program to be installed (Displayed as icon in the title bar of the setup frame)
         *
         * @param image - Can be an input stream, a resource path or an url (String)
         * @return the builder instance
         * @throws IOException - Thrown if an error occurs while converting the image object into a byte array
         */
        public Builder setSetupIcon(Object image) throws IOException {
            values.put("setupicon", StreamUtils.inputStreamToByteArray(StreamUtils.imageObjectToInputStream(image)));
            return this;
        }

        /**
         * The look and feel of the setup frame
         *
         * @param lookAndFeel - The look and feel of the setup frame
         * @return the builder instance
         */
        public Builder setCustomLookAndFeel(LookAndFeel lookAndFeel) {
            values.put("lookandfeel", lookAndFeel);
            return this;
        }

        /**
         * The name of the program to be installed
         *
         * @param name - The program name
         * @return the builder instance
         */
        public Builder setProgramName(String name) {
            values.put("programname", name);
            return this;
        }

        /**
         * This runnable will be executed when the setup closes
         *
         * @param runnable - Code to run on setup closure
         * @return the builder instance
         */
        public Builder setOnFinish(Runnable runnable) {
            values.put("onfinish", runnable);
            return this;
        }

        /**
         * The program's version number
         *
         * @param version - The program version
         * @return the builder instance
         */
        public Builder setProgramVersion(String version) {
            values.put("programversion", version);
            return this;
        }

        /**
         * <b>Required</b><br> The components of the setup (Pages)
         *
         * @param component - The components to be added
         * @return the builder instance
         * <b>FinishComponent and WelcomeComponent can't be added via addComponents, because they are added automatically</b>
         */
        @SuppressWarnings("unchecked") // I can safely cast in this case
        public Builder addComponents(Component... component) {
            values.putIfAbsent("components", new ArrayList<Component>());
            for (Component comp : component) {
                if (comp instanceof WelcomeComponent || comp instanceof FinishComponent) {
                    throw new IllegalArgumentException("Illegal components found! Note: FinishComponent and WelcomeComponent are added automatically");
                }
            }
            ArrayList<Component> components = (ArrayList<Component>) values.get("components");
            Collections.addAll(components, component);
            return this;
        }

        /**
         * Builds a setup instance with the supplied variables
         *
         * @return instance of Setup
         */
        public Setup build() {
            //Checking required values
            if (!values.containsKey("components")) {
                throw new IllegalArgumentException("No components (Pages) added");
            }
            ArrayList<Component> components = (ArrayList<Component>) values.get("components");
            components.add(0, new WelcomeComponent());
            components.add(components.size(), new FinishComponent());
            return new Setup(
                    values
            );
        }
    }

    /**
     * Opens the setup
     */
    public void open() {
        pack();
        ArrayList<Component> components = (ArrayList<Component>) values.get("components");
        for (Component component : components) {
            component.init(this, componentArea.getWidth(), componentArea.getHeight(), values);
        }
        components.get(0).makeVisible(this, custom1, custom2, nextButton, previousButton, cancelButton, componentStorage);
        setVisible(true);
    }

    /**
     * Closes the setup
     */
    public void close() {
        dispose();
    }
}

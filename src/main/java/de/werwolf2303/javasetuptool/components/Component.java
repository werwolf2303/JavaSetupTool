package de.werwolf2303.javasetuptool.components;

import javax.swing.*;
import java.util.HashMap;

/**
 * Make your class implement this interface to make it available as a component in the setup
 * <br>
 * <b>You are responsible for restoring the defaults for the elements given in makeVisible</b>
 */
public interface Component {
    /**
     * This method is called when the component should be made visible.
     * @param frame - The JFrame of the setup
     * @param custom1 - The first custom button (You can use this to add an extra button for your component)
     * @param custom2 - The second custom button (You can use this to add an extra button for your component)
     * @param nextButton - The next button of the setup
     * @param previousButton - The previous button of the setup
     * @param cancelButton - The cancel button of the setup
     * @param storage - Storage where components can put variables
     */
    void makeVisible(JFrame frame, JButton custom1, JButton custom2, JButton nextButton, JButton previousButton, JButton cancelButton, HashMap<String, Object> storage);

    /**
     * This method is called when the component should be made invisible.
     * @param frame - The JFrame of the setup
     * @param custom1 - The first custom button (You can use this to add an extra button for your component)
     * @param custom2 - The second custom button (You can use this to add an extra button for your component)
     * @param nextButton - The next button of the setup
     * @param previousButton - The previous button of the setup
     * @param cancelButton - The cancel button of the setup
     * @param storage - Storage where components can put variables
     */
    void makeInvisible(JFrame frame, JButton custom1, JButton custom2, JButton nextButton, JButton previousButton, JButton cancelButton, HashMap<String, Object> storage);

    /**
     * Called when preparing the setup window
     * @param frame - The JFrame of the setup
     * @param width - Width of the component area
     * @param height - Height of the component area
     * @param setupVariables - Values set in the setup builder
     */
    void init(JFrame frame, int width, int height, HashMap<String, Object> setupVariables);

    /**
     * Called when the user pressed the next button
     * @return - when true is returned, the setup will not handle the button press
     */
    boolean onNext();

    /**
     * Called when the user pressed the previous button
     * @return - when true is returned, the setup will not handle the button press
     */
    boolean onPrevious();

    /**
     * Called when the user pressed the custom 1 button
     */
    void onCustom1();

    /**
     * Called when the user pressed the custom 2 button
     */
    void onCustom2();

    /**
     * <b>!!Very important!!</b><br>
     * Return here the container of the component
     * @return - The container of the component
     */
    JPanel getContainer();
}

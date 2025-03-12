package de.werwolf2303.javasetuptool.components;

import org.fit.cssbox.awt.BrowserCanvas;
import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.io.*;
import org.fit.cssbox.layout.Dimension;
import org.xml.sax.SAXException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * This component contains an "Accept" and "Decline" button, that can be used for license agreements.
 * <br><img alt="AcceptComponent" src="./doc-files/AcceptComponent.png" />
 */
public class AcceptComponent extends JPanel implements Component {
    DocumentSource docSource;
    DOMSource domSource;
    JScrollPane pane;
    JEditorPane content;
    boolean scrolldown = false;
    JButton custom1;
    JButton custom2;
    JButton nextButton;
    JFrame frame;

    boolean didAccept = false;
    boolean initialized = false;
    private BrowserCanvas browserCanvas;

    AdjustmentListener listener = new AdjustmentListener() {
        @Override
        public void adjustmentValueChanged(AdjustmentEvent e) {
            if(!e.getValueIsAdjusting()){
                JScrollBar scrollBar = (JScrollBar) e.getAdjustable();
                int extent = scrollBar.getModel().getExtent();
                int maximum = scrollBar.getModel().getMaximum();
                if(extent + e.getValue() == maximum){
                    custom1.setEnabled(true);
                }
            }
        }
    };

    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }


    /**
     * Creates an instance of this class and displays the specified HTML
     *
     * @param html - HTML to load
     * @throws IOException - throws if the document cannot be loaded
     */
    public AcceptComponent(String html) throws IOException {
        docSource = new StreamDocumentSource(new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)), new URL("http://localhost"), "text/html");
        domSource = new DefaultDOMSource(docSource);
        createBrowserCanvas(domSource, docSource);
    }

    /**
     * Creates an instance of this class and displays the specified url
     *
     * @param url - url to display
     * @throws IOException - throws if the document cannot be loaded
     */
    public AcceptComponent(URL url) throws IOException {
        docSource = new DefaultDocumentSource(url);
        domSource = new DefaultDOMSource(docSource);
        createBrowserCanvas(domSource, docSource);
    }

    @Override
    public void makeVisible(JFrame frame, JButton custom1, JButton custom2, JButton nextButton, JButton previousButton, JButton cancelButton, HashMap<String, Object> storage) {
        this.custom1 = custom1;
        this.custom2 = custom2;
        this.frame = frame;
        this.nextButton = nextButton;

        if(!didAccept) {
            custom1.setText("Accept");
            custom1.setVisible(true);
            scrolldown = true;
            custom1.setEnabled(false);
            custom2.setText("Decline");
            custom2.setVisible(true);
            nextButton.setEnabled(false);
        }
        pane.getVerticalScrollBar().addAdjustmentListener(listener);
    }

    @Override
    public void makeInvisible(JFrame frame, JButton custom1, JButton custom2, JButton nextButton, JButton previousButton, JButton cancelButton, HashMap<String, Object> storage) {
        custom2.setVisible(false);
        nextButton.setEnabled(true);
        custom1.setVisible(false);
        pane.getVerticalScrollBar().removeAdjustmentListener(listener);
    }

    @Override
    public void init(JFrame frame, int width, int height, HashMap<String, Object> setupVariables) {
        content = new JEditorPane();
        content.setEditable(false);
        pane = new JScrollPane(content);
        add(pane, BorderLayout.CENTER);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                if(!initialized) {
                    initialized = true;
                }else return;
                browserCanvas.createLayout(new Dimension(pane.getViewport().getWidth(), pane.getViewport().getHeight()));
                revalidate();
                repaint();
            }
        });
        pane.setViewportView(browserCanvas);
    }

    @Override
    public boolean onNext() {
        return false;
    }

    @Override
    public boolean onPrevious() {
        return false;
    }

    @Override
    public void onCustom1() {
        didAccept = true;
        nextButton.setEnabled(true);
        custom1.setVisible(false);
        custom2.setVisible(false);
    }


    private void createBrowserCanvas(DOMSource domSource, DocumentSource docSource) {
        try {
            DOMAnalyzer da = new DOMAnalyzer(domSource.parse(), docSource.getURL());
            da.attributesToStyles();
            da.addStyleSheet(null, CSSNorm.stdStyleSheet(), DOMAnalyzer.Origin.AGENT);
            da.addStyleSheet(null, CSSNorm.userStyleSheet(), DOMAnalyzer.Origin.AGENT);
            da.getStyleSheets();
            browserCanvas = new BrowserCanvas(da.getRoot(), da, docSource.getURL());
            browserCanvas.repaint();
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCustom2() {
        frame.dispose();
    }

    @Override
    public JPanel getContainer() {
        return this;
    }
}

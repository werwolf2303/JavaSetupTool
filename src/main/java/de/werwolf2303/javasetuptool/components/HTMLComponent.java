package de.werwolf2303.javasetuptool.components;

import org.fit.cssbox.awt.BrowserCanvas;
import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.io.*;
import org.fit.cssbox.layout.Dimension;
import org.xml.sax.SAXException;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * A component that can be used to display HTML content.
 * <b>The rendered HTML is not interactive</b>
 * <br> <img alt="HTMLComponent" src="./doc-files/HTMLComponent.png" />
 */
public class HTMLComponent extends JScrollPane implements Component {
    DocumentSource docSource;
    DOMSource domSource;

    boolean initialized = false;

    private JPanel contentPanel;

    private BrowserCanvas browserCanvas;

    {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(this);
    }

    /**
     * Creates an instance of this class and displays the specified HTML
     *
     * @param html - HTML to load
     * @throws IOException - throws if the HTML could not be loaded
     */
    public HTMLComponent(String html) throws IOException {
        docSource = new StreamDocumentSource(new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)), new URL("http://localhost"), "text/html");
        domSource = new DefaultDOMSource(docSource);
        createBrowserCanvas(domSource, docSource);
        setViewportView(browserCanvas);
    }

    /**
     * Creates an instance of this class and displays the specified url
     *
     * @param url - url to display
     * @throws IOException - throws if the url could not be loaded
     */
    public HTMLComponent(URL url) throws IOException {
        docSource = new DefaultDocumentSource(url);
        domSource = new DefaultDOMSource(docSource);
        createBrowserCanvas(domSource, docSource);
        setViewportView(browserCanvas);
    }

    @Override
    public void makeVisible(JFrame frame, JButton custom1, JButton custom2, JButton nextButton, JButton previousButton, JButton cancelButton, HashMap<String, Object> storage) {
        setVisible(true);
    }

    @Override
    public void makeInvisible(JFrame frame, JButton custom1, JButton custom2, JButton nextButton, JButton previousButton, JButton cancelButton, HashMap<String, Object> storage) {
        setVisible(false);
    }

    @Override
    public void init(JFrame frame, int width, int height, HashMap<String, Object> setupVariables) {
        contentPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                if(!initialized) {
                    initialized = true;
                }else return;
                browserCanvas.createLayout(new Dimension(getViewport().getWidth(), getViewport().getHeight()));
                revalidate();
                repaint();
            }
        });
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

    }

    @Override
    public void onCustom2() {

    }

    @Override
    public JPanel getContainer() {
        return contentPanel;
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
}

package de.werwolf2303.javasetuptool.uninstaller;

import de.werwolf2303.javasetuptool.PublicValues;
import de.werwolf2303.javasetuptool.Test;
import de.werwolf2303.javasetuptool.utils.Resources;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Uninstaller {
    public static void main(String[] args) {
        open();
    }
    static String programname;
    static String programversion;

    public static ArrayList<String> fi = new ArrayList<String>();
    public static ArrayList<String> fo = new ArrayList<String>();

    public static void open() {
        final JFrame frame = new JFrame("Uninstaller");
        frame.setPreferredSize(new Dimension(375, 169));
        final JButton uninstall = new JButton("Uninstall");
        uninstall.setBounds(127, 106, 117, 29);
        frame.add(uninstall);

        JLabel lblNewLabel = new JLabel("This uninstaller will uninstall %progname%");
        lblNewLabel.setBounds(6, 18, 363, 16);
        frame.add(lblNewLabel);

        final JProgressBar progressBar = new JProgressBar();
        progressBar.setBounds(6, 60, 363, 20);
        frame.add(progressBar);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        InputStream is = null;
        try {
            is = new FileInputStream(new File(new File(Test.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getAbsolutePath(), "uninstaller.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);
            Node prognode = doc.getElementsByTagName("program").item(0);
            programversion = prognode.getAttributes().getNamedItem("version").getNodeValue();
            programname = prognode.getAttributes().getNamedItem("name").getNodeValue();
            if (doc.hasChildNodes()) {
                for(int i = 0; i < doc.getElementsByTagName("content").item(0).getChildNodes().getLength(); i++) {
                    Node node = doc.getElementsByTagName("content").item(0).getChildNodes().item(i);
                    if(node.getNodeName().equalsIgnoreCase("file")) {
                        fi.add(node.getAttributes().getNamedItem("src").getNodeValue());
                    }else{
                        if(node.getNodeName().equalsIgnoreCase("folder")) {
                            fo.add(node.getAttributes().getNamedItem("src").getNodeValue());
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
        } catch (SAXException e2) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        lblNewLabel.setText(lblNewLabel.getText().replace("%progname%", programname));
        frame.setVisible(true);
        frame.pack();

        uninstall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                progressBar.setValue(0);
                progressBar.setMaximum(fi.size() + fo.size());
                progressBar.setValue(progressBar.getMaximum());
                for(String f : fi) {
                    new java.io.File(f).delete();
                    progressBar.setValue(progressBar.getMaximum() - 1);
                }
                for(String f : fo) {
                    deleteFolder(new File(f));
                    progressBar.setValue(progressBar.getMaximum() - 1);
                }
                frame.dispose();
                JOptionPane.showMessageDialog(null, "Uninstall Complete", "Complete", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }



    public void buildUninstaller(ArrayList<String> files, ArrayList<String> folders, String progname, String progver, String path) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("uninstaller");
        doc.appendChild(rootElement);

        Element program = doc.createElement("program");
        program.setAttribute("version", progver);
        program.setAttribute("name", progname);
        rootElement.appendChild(program);

        Element content = doc.createElement("content");
        for(String s : files) {
            Element e = doc.createElement("file");
            e.setAttribute("src", s);
            content.appendChild(e);
        }
        for(String s : folders) {
            Element e = doc.createElement("folder");
            e.setAttribute("src", s);
            content.appendChild(e);
        }
        rootElement.appendChild(content);
        try {
            FileOutputStream output = new FileOutputStream(path);
            writeXml(doc, output);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}

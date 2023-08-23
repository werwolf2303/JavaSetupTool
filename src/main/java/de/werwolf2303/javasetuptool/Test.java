package de.werwolf2303.javasetuptool;

import de.werwolf2303.javasetuptool.components.*;
import de.werwolf2303.javasetuptool.uninstaller.Uninstaller;
import de.werwolf2303.javasetuptool.utils.Resources;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

public class Test {
    /*public static void main(String[] args) {
        Setup setup = new Setup();
        HTMLComponent two = new HTMLComponent();
        two.load("<h1>Page 2</h1>");
        InstallProgressComponent component = new InstallProgressComponent();
        component.addFileOperation(new InstallProgressComponent.FileOperationBuilder().setType(InstallProgressComponent.FileOperationTypes.CREATEDIR).setFrom(new File("testsetupinstall").getAbsolutePath()));
        AcceptComponent accept = new AcceptComponent();
        accept.setText("Why do we use it?\n" +
                "It is a long established fact that a reader will be distracted by the readable content of a page \nwhen looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content\n here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, an\nd a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, som\netimes by accident, sometimes on purpose (injected humour and the like).\n" +
                "\n" +
                "\n" +
                "Where does it come from?\n" +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It ha\ns roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of \nthe more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered th\ne undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.\n" +
                "\n" +
                "Where can I get some?\n" +
                "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteratio\nn in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum,\n you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefin\ned chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 L\natin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.");
        InstallFolderSelectComponent install = new InstallFolderSelectComponent();
        install.setDefaultPath(new File("."));
        setup.open(new Setup.SetupBuilder()
                .setProgramName("SpotifyXP")
                .setProgramVersion("2.0.0")
                .setProgramImage("test.png")
                .addComponent(two)
                .setInstallComponent(component)
                .addComponent(accept)
                .addComponent(install)
                .build());
    }*/


    public static void main(String[] args) throws ParserConfigurationException {
        Setup setup = new Setup();
        new CanvasComponent().setOnDraw(new CanvasComponent.DrawEvent() {
            @Override
            public void trigger(Graphics g) {

            }
        });
        HTMLComponent component = new HTMLComponent();
        InstallProgressComponent installProgressComponent = new InstallProgressComponent();
        component.load("<a>This text is displayed</a>");
        setup.open(new Setup.SetupBuilder()
                .addComponent(component)
                .setInstallComponent(installProgressComponent)
                .setProgramName("My Application")
                .setProgramVersion("1.0")
                .build());
    }
}

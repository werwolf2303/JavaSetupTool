package de.werwolf2303.javasetuptool.test;

import de.werwolf2303.javasetuptool.Setup;
import de.werwolf2303.javasetuptool.components.*;
import de.werwolf2303.javasetuptool.components.Component;
import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;

public class Test {

    public static void main(String[] args) throws IOException {
        InstallProgressComponent component = new InstallProgressComponent();
        Setup setup = new Setup.Builder()
                .setProgramName("Test Program")
                .setProgramVersion("1.0.0")
                .addComponents(
                        new AcceptComponent("<a>I'm an AcceptComponent with CSSBox for rendering</a>"),
                        new HTMLComponent("<a>I'm an HTMLComponent with CSSBox for rendering</a>"),
                        new FeatureSelectionComponent()
                                .addFeature(new FeatureSelectionComponent.FeatureBuilder()
                                        .setName("We").build())
                                .addFeature(new FeatureSelectionComponent.FeatureBuilder()
                                        .setName("are").build())
                                .addFeature(new FeatureSelectionComponent.FeatureBuilder()
                                        .setName("Features").build())
                        ,
                        component
                )
                .build();
        setup.open();
    }
}

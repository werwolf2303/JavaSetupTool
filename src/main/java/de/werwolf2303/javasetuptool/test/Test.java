package de.werwolf2303.javasetuptool.test;

import de.werwolf2303.javasetuptool.Setup;
import de.werwolf2303.javasetuptool.components.AcceptComponent;
import de.werwolf2303.javasetuptool.components.FeatureSelectionComponent;
import de.werwolf2303.javasetuptool.components.HTMLComponent;
import de.werwolf2303.javasetuptool.components.InstallProgressComponent;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        InstallProgressComponent component = new InstallProgressComponent();
        Setup setup = new Setup.Builder()
                .setProgramName("Test Program")
                .setProgramVersion("1.0.0")
                .addComponents(
                        new AcceptComponent("<a>I'm an AcceptComponent that supports html</a>"),
                        new HTMLComponent("<a>I'm an HTMLComponent that supports html</a>"),
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

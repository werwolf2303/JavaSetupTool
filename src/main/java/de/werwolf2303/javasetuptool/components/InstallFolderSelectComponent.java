package de.werwolf2303.javasetuptool.components;

import de.werwolf2303.javasetuptool.PublicValues;
import de.werwolf2303.javasetuptool.Setup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class InstallFolderSelectComponent extends JPanel implements Component {
    JTextField directorypath;
    JButton browse;
    JLabel diskspace;
    JLabel lblNewLabel;
    JLabel titlelabel;

    public InstallFolderSelectComponent() {
        directorypath = new JTextField();
        add(directorypath);
        directorypath.setColumns(10);

        setLayout(null);

        browse = new JButton("Browse...");
        add(browse);

        browse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.setProperty("apple.awt.fileDialogForDirectories", "true");
                FileDialog fd = new FileDialog(new Frame(), "Choose a Folder", FileDialog.LOAD);
                fd.setDirectory(System.getProperty("user.home"));
                fd.setVisible(true);
                if(!fd.getDirectory().equals("")) {
                    directorypath.setText(fd.getDirectory() + fd.getFile());
                    dirsel.run();
                }
            }
        });

        diskspace = new JLabel("At lease %space% of free disk space is required.");
        add(diskspace);

        lblNewLabel = new JLabel("To continue, click Next. If you would like to select a different folder, click Browse.");
        add(lblNewLabel);

        titlelabel = new JLabel("Setup will install %progname% into the following folder.");
        add(titlelabel);
    }

    public void setRequiredDiskSpace(String spaceMB) {
        diskspace.setText(diskspace.getText().replace("%space%", spaceMB + " MB"));
    }

    public void setDefaultPath(File file) {
        directorypath.setText(file.getAbsolutePath());
    }

    public JPanel drawable() {
        return this;
    }

    public void init() {
        setPreferredSize(new Dimension(PublicValues.setup_width, PublicValues.setup_height - PublicValues.setup_bar_height));
        directorypath.setBounds(6, 102, 437, 29);
        browse.setBounds(455, 103, 117, 29);
        lblNewLabel.setBounds(6, 78, 537, 16);
        titlelabel.setBounds(70, 26, 386, 16);
        diskspace.setBounds(6, 265, 323, 16);
        if (builder == null) {
            return;
        }
        titlelabel.setText(titlelabel.getText().replace("%progname%", builder.progname));
    }

    Runnable dirsel = new Runnable() {
        public void run() {
        }
    };

    public void onDirectorySelected(Runnable run) {
        this.dirsel = run;
    }

    public void nowVisible() {

    }

    public void onLeave() {

    }

    Setup.SetupBuilder builder;

    public void giveComponents(JButton next, JButton previous, JButton cancel, JButton custom1, JButton custom2, Runnable fin, Setup.SetupBuilder builder) {
        this.builder = builder;
    }
}

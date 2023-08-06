package de.werwolf2303.javasetuptool.components;

import de.werwolf2303.javasetuptool.PublicValues;
import de.werwolf2303.javasetuptool.Setup;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class InstallProgressComponent extends JPanel implements Component {
    ArrayList<FileOperation> fileOperations = new ArrayList<FileOperation>();
    JProgressBar progress;
    JTextArea operationlog;
    JLabel operationpath;

    public InstallProgressComponent() {
        setLayout(null);
        progress = new JProgressBar();
        add(progress);
        operationlog = new JTextArea();
        operationlog.setEditable(false);
        add(operationlog);
        operationpath = new JLabel();
        progress.setMinimum(0);
        progress.setValue(0);
        add(operationpath);
    }

    public static class FileOperationBuilder {
        private FileOperation internal = new FileOperation();
        public FileOperationBuilder setType(FileOperationTypes type) {
            internal.operationType = type;
            return this;
        }

        public FileOperationBuilder setFrom(InputStream stream) {
            internal.fromStream = stream;
            return this;
        }

        public FileOperationBuilder setFrom(String from) {
            internal.from = from;
            return this;
        }

        public FileOperationBuilder setTo(String to) {
            internal.to = to;
            return this;
        }

        public FileOperationBuilder setCustom(Runnable run) {
            internal.customCode = run;
            return this;
        }
    }

    public JPanel drawable() {
        return this;
    }

    public void addFileOperation(FileOperation operation) {
        fileOperations.add(operation);
    }

    public void addFileOperation(FileOperationBuilder builder) {
        fileOperations.add(builder.internal);
    }

    void doOperations() {
        progress.setMaximum(fileOperations.size());
        for(FileOperation operation : fileOperations) {
            switch (operation.operationType) {
                case COPY:
                    try {
                        operationpath.setText(new File(operation.to).getAbsolutePath());
                        operationlog.append("Copy from=>'" + operation.from + "' to=>'" + operation.to + "'\n");
                        Files.copy(Paths.get(operation.from), Paths.get(operation.to), StandardCopyOption.REPLACE_EXISTING);
                        operation.succeeded = true;
                    } catch (IOException e) {
                        operation.succeeded = false;
                    }
                    break;
                case MOVE:
                    try {
                        operationpath.setText(new File(operation.to).getAbsolutePath());
                        operationlog.append("Moving from=>'" + operation.from + "' to=>'" + operation.to + "'\n");
                        Files.move(Paths.get(operation.from), Paths.get(operation.to), StandardCopyOption.REPLACE_EXISTING);
                        operation.succeeded = true;
                    } catch (IOException e) {
                        operation.succeeded = false;
                    }
                    break;
                case CREATEFILE:
                    try {
                        operationpath.setText(new File(operation.from).getAbsolutePath());
                        operationlog.append("Creating file=>'" + operation.from + "'\n");
                        operation.succeeded = new File(operation.from).createNewFile();
                    } catch (IOException e) {
                        operation.succeeded = false;
                    }
                    break;
                case CREATEDIR:
                    operationpath.setText(new File(operation.from).getAbsolutePath());
                    operationlog.append("Creating directory =>'" + operation.from + "'\n");
                    operation.succeeded = new File(operation.from).mkdir();
                    break;
                case DELETE:
                    operationpath.setText(new File(operation.from).getAbsolutePath());
                    operationlog.append("Deleting =>'" + operation.from + "'\n");
                    operation.succeeded = new File(operation.from).delete();
                    break;
                case COPYSTREAM:
                    operationpath.setText(new File(operation.to).getAbsolutePath());
                    operationlog.append("Copying stream to=>" + operation.to + "\n");
                    try {
                        Files.copy(operation.fromStream, Paths.get(operation.to), StandardCopyOption.REPLACE_EXISTING);
                        operation.succeeded = true;
                    } catch (IOException e) {
                        operation.succeeded = false;
                    }
                    break;
                case CUSTOM:
                    operationlog.append("Executing custom java\n");
                    operation.customCode.run();
                    break;
            }
            progress.setValue(progress.getValue()+1);
        }
        operationsFinished();
    }

    public void init() {
        setPreferredSize(new Dimension(PublicValues.setup_width, PublicValues.setup_height - PublicValues.setup_bar_height));
        progress.setBounds(3, 64, PublicValues.setup_width - 6, 20);
        operationpath.setBounds(6, 36, PublicValues.setup_width - 6, 16);
        operationlog.setBounds(6, 96, PublicValues.setup_width - 6, 185);
    }

    public void nowVisible() {
        doOperations();
    }

    public void onLeave() {

    }

    JButton next;
    JButton prev;
    JButton cancel;
    JButton custom1;
    JButton custom2;
    Runnable fin;

    public void giveComponents(JButton next, JButton previous, JButton cancel, JButton custom1, JButton custom2, Runnable finish, Setup.SetupBuilder builder) {
        this.fin = finish;
        this.next = next;
        this.prev = previous;
        this.cancel = cancel;
        this.custom1 = custom1;
        this.custom2 = custom2;
    }

    void operationsFinished() {
        this.next.setText("Next >");
        this.next.setVisible(true);
        for(ActionListener l : this.next.getActionListeners()) {
            this.next.removeActionListener(l);
        }
        this.next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fin.run();
            }
        });
    }

    public enum FileOperationTypes {
        CREATEFILE,
        CREATEDIR,
        MOVE,
        COPY,
        COPYSTREAM,
        DELETE,
        CUSTOM
    }

    public static class FileOperation {
        String from = "";
        InputStream fromStream;
        String to = "";
        boolean succeeded = false;
        Runnable customCode;
        FileOperationTypes operationType;
    }
}

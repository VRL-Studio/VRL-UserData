package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.userdata.LoadUGXFileObservable;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.dialogs.FileDialogManager;
import eu.mihosoft.vrl.io.VFileFilter;
import eu.mihosoft.vrl.lang.VLangUtils;
import eu.mihosoft.vrl.reflection.LayoutType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.visual.MessageType;
import eu.mihosoft.vrl.visual.VBoxLayout;
import eu.mihosoft.vrl.visual.VTextField;
import groovy.lang.Script;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type = File.class, input = true, output = false, style = "ugx-load-dialog")
public class LoadUGXFileType extends TypeRepresentationBase {

    /// the text field to display
    private VTextField input;
    /// filter to restrict to ugx file
    private VFileFilter fileFilter = new VFileFilter();
    /// the current tag
    private String tag = null;

    /**
     * Constructor.
     */
    public LoadUGXFileType() {

        // create a Box and set it as layout
        VBoxLayout layout = new VBoxLayout(this, VBoxLayout.Y_AXIS);
        setLayout(layout);
        setLayoutType(LayoutType.STATIC);

        // set the name label
        nameLabel.setText("File Name (*.ugx):");
        nameLabel.setAlignmentX(0.0f);
        add(nameLabel);

        // create input field
        input = new VTextField(this, "");
        input.setHorizontalAlignment(JTextField.RIGHT);
        int height = (int) this.input.getMinimumSize().getHeight();
        input.setSize(new Dimension(120, height));
        input.setMinimumSize(new Dimension(120, height));
        input.setMaximumSize(new Dimension(120, height));
        input.setPreferredSize(new Dimension(120, height));
        input.setEditable(true);
        input.setAlignmentY(0.5f);
        input.setAlignmentX(0.0f);
        input.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setViewValue(new File(input.getText()));
            }
        });
        add(input);

        // hide connector, since no external data allowed
        setHideConnector(true);

        // set to ugx ending only
        ArrayList<String> endings = new ArrayList<String>();
        endings.add("ugx");
        fileFilter.setAcceptedEndings(endings);
        fileFilter.setDescription("*.ugx");

        // create a file manager
        final FileDialogManager fileManager = new FileDialogManager();

        // create a load button
        JButton button = new JButton("...");
        button.setMaximumSize(new Dimension(50, button.getMinimumSize().height));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File directory = null;
                if (getViewValueWithoutValidation() != null) {
                    directory = new File(getViewValueWithoutValidation().toString());
                    if (!directory.isDirectory()) {
                        directory = directory.getParentFile();
                    }
                }

                File file = fileManager.getLoadFile(getMainCanvas(),
                        directory, fileFilter, false);

                if (file != null) {
                    setViewValue(file);
                }
            }
        });

        add(button);
    }

    @Override
    public void setViewValue(Object o) {
        if (o instanceof File) {
            input.setText(((File) o).getAbsolutePath());
            input.setCaretPosition(input.getText().length());
            input.setToolTipText(input.getText());
            input.setHorizontalAlignment(JTextField.RIGHT);
        }
        //  Here we inform the Singleton, that the file has been scheduled
        notifyLoadUGXFileObservable();
    }

    @Override
    public Object getViewValue() {
        return new File(input.getText());
    }

    @Override
    public void emptyView() {
        input.setText("");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void evaluationRequest(Script script) {
        super.evaluationRequest(script);

        Object property = null;

        if (getValueOptions() != null) {

            if (getValueOptions().contains("tag")) {
                property = script.getProperty("tag");
            }

            if (property != null) {
                tag = (String) property;
            }
        }

        if (tag == null) {
            getMainCanvas().getMessageBox().addMessage("Invalid ParamInfo option",
                    "ParamInfo for ugx-subset-selection requires tag in options",
                    getConnector(), MessageType.ERROR);
        }

    }

    @Override
    public void addedToMethodRepresentation() {
        super.addedToMethodRepresentation();

        // register at Observable using tag
        notifyLoadUGXFileObservable();
    }

    protected void notifyLoadUGXFileObservable() {
        File file = new File(input.getText());
        int id = this.getParentMethod().getParentObject().getObjectID();
        Object o = ((VisualCanvas) getMainCanvas()).getInspector().getObject(id);
        int windowID = 0;

        //  Here we inform the Singleton, that the file no scheduled
        if (!file.getAbsolutePath().isEmpty() && file.isFile()) {
            String msg = LoadUGXFileObservable.getInstance().setSelectedFile(file, tag, o, windowID);
            if (!msg.isEmpty() && !getMainCanvas().isLoadingSession()) {
                getMainCanvas().getMessageBox().addMessage("Invalid ugx-File",
                        msg, getConnector(), MessageType.ERROR);
            }

        } else {
            LoadUGXFileObservable.getInstance().setInvalidFile(tag, o, windowID);
            if (!input.getText().isEmpty() && !getMainCanvas().isLoadingSession()) {
                getMainCanvas().getMessageBox().addMessage("Invalid ugx-File",
                        "Specified filename invalid: " + file.toString(),
                        getConnector(), MessageType.ERROR);
            }
        }
    }

    @Override
    public String getValueAsCode() {
        return "\""
                + VLangUtils.addEscapesToCode(getValue().toString()) + "\"";
    }
}

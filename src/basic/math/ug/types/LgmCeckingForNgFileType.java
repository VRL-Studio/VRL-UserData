/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.types;

import basic.math.helpers.FileUtils;
import eu.mihosoft.vrl.types.LoadFileType;
import eu.mihosoft.vrl.visual.MessageBox;
import eu.mihosoft.vrl.visual.MessageType;
import java.io.File;
import java.io.Serializable;

/**
 * This Typerepresentation provides the following functionality in addition to
 * its visualisation function.
 *
 * 1) In the open/load dialog you are only allowed to load files with the .lgm ending.
 * 2) The folder where the .lgm file is located is searched for the belonging .ng.
 *    The .ng file have be be named the same way the .lgm file is.
 *    If there is no belonging .ng file you will get an error message.
 *
 * @author night
 */
public class LgmCeckingForNgFileType extends LoadFileType implements Serializable {

    public LgmCeckingForNgFileType() {
        setValueName("lgmFile:"); //the name which is displayed above the input section

//        setSupportedStyle("lgm-ng"); //the style that should be used to actived this typerepresentation
        setStyleName("lgm-ng");

        //this part is treated as a groovy script with two variables: discription and endings
        //discription is used to show in the open dialog what kind of files are allowed (just text)
        //endings is an array of endings and is used to filter all files out in the open dialog,
        //  which doesn't have the corresponding ending
        setValueOptions("description=\"LGM Files (.lgm)\"; endings=[\".lgm\"]");
    }

    @Override
    public Object getViewValue() {
        File f = (File) super.getViewValue();

        if (f != null &&  f.isFile() &&
                f.getAbsolutePath().toLowerCase().endsWith(".lgm")) {

            //generate the path and filename which should be looked for
            String newFileName = FileUtils.removeFileExtension(
                    f.getAbsolutePath()) + ".ng";

            File newFile = new File(newFileName);

            //check if found .ng file is really a file and not a folder
            if (newFile.exists() &&  newFile.isFile()) {
                validateValue();

            } else {

                MessageBox box = getMainCanvas().getMessageBox();

                box.addUniqueMessage("File not Found:",
                        ">> the file \"<i><u>" + newFileName +
                        "</u></i>\" cannot be found!",
                        getConnector(),
                        MessageType.ERROR);
                invalidateValue();
            }
        }
        
        return f;
    }

}

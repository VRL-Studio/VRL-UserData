/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import java.io.File;

/**
 * A little helper class for removing the ending of a file name.
 *
 * if we have a file like "look.at.this.txt" we get "look.at.this"
 *
 * @author night
 */
public class FileUtils {

    //remove the file extension of the file entered
    public static String removeFileExtension(String fileName) {
        if (null != fileName && fileName.contains(".")) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        }
        return null;
    }

    /**
     * Deletes all files in a directory and all subdirectories if boolean removeSubFolders
     * is true.
     * @param folderPath the path where the containing files should be removed
     * @param removeSubFolders set true if also the subfolder should be deleted
     */
    public static void deleteFiles(String folderPath, boolean removeSubFolders) {

        System.err.println("start deleting files in: "+ folderPath);
        File dir = new File(folderPath);

        if (dir.isDirectory()) {

            for (File file : dir.listFiles()) {

                if (file.isDirectory()) {

                    if (removeSubFolders) {
                        deleteFiles(file.getAbsolutePath(), removeSubFolders);
                        file.delete();//remove the folder
                    }
                } else {
                    file.delete();
                }
            }
        }
        System.err.println("files deleted in: "+ folderPath);

    }
}

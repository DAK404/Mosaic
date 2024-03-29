/*
 *****************************************************
 *                                                   *
 * ! IMPORTANT ! DO NOT DELETE COMMENT ! IMPORTANT ! *
 *                                                   *
 *****************************************************
 *                                                   *
 *            THIS CODE IS RELEASE READY.            *
 *                                                   *
 *      THIS CODE HAS BEEN TESTED, REVIEWED AND      *
 *      REVISED. THIS CODE HAS NO KNOWN ISSUES,      *
 *      HENCE IT IS CONSIDERED AS RELEASE READY      *
 *                                                   *
 *****************************************************
 */

package Mosaic.API.Tools.Update;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
* Class which helps in installing the downloaded update package.
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  BB3-Mosaic-InsUpd-Updater|
* | Class Name  :  InstallUpdate            |
* | Since       :  0.0.1, 06-February-2020  |
* | Updated on  :  0.3.2, 28-September-2020 |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
final class InstallUpdate {
    List < String > fileList;
    private String curDir = System.getProperty("user.dir");
    private final String INPUT_ZIP_FILE = curDir + "/Update.zip";
    private final String OUTPUT_FOLDER = curDir;


    /**
    * Method which encapsulates the installation implementation.
    */
    protected boolean install() {
        InstallUpdate unZip = new InstallUpdate();
        return unZip.unZipIt(INPUT_ZIP_FILE, OUTPUT_FOLDER);
    }

    /**
    * The implementation for unzipping the package to copy new files in the directory hierarchy.
    *
    * @param zipFile       : The name of the file to be unzipped.
    * @param outputFolder  : The directory where the update must be installed.
    */
    private boolean unZipIt(String zipFile, String outputFolder) {
        byte[] buffer = new byte[1024];
        try {
            //create output directory is not exists
            File folder = new File(OUTPUT_FOLDER);
            if (!folder.exists()) {
                folder.mkdir();
            }
            //get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                if (ze.isDirectory()) {
                    ze = zis.getNextEntry();
                    continue;
                }
                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);
                if (newFile.exists()) {
                    newFile.delete();
                    continue;
                }
                System.out.println("[INFO] Installing : " + newFile.getAbsoluteFile());
                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            File zf = new File(INPUT_ZIP_FILE);
            zf.delete();
            System.out.println("Install Successful.");
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
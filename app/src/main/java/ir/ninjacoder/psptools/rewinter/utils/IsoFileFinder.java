package ir.ninjacoder.psptools.rewinter.utils;

import java.io.File;
import android.util.Log;

public class IsoFileFinder {

  public static boolean searchForIsoFiles(File directory) {
    boolean jarFileFound = false;

    if (directory.exists()) {
      if (directory.isDirectory()) {
        File[] files = directory.listFiles();
        if (files != null) {
          for (File file : files) {
            if (file.isDirectory()) {
              if (searchForIsoFiles(file)) {
                jarFileFound = true;
              }
            } else {
              if (file.getName().endsWith(".iso")) {
                Log.d("JarFileFinder", "🎉 Found a .jar file: " + file.getAbsolutePath());
                jarFileFound = true;
              }
            }
          }
        }
      }
    } else {
      Log.e("JarFileFinder", "😔 Directory does not exist.");
    }

    return jarFileFound;
  }
}

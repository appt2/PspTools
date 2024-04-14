package ir.ninjacoder.psptools.rewinter.utils;

import java.io.File;
import java.util.Comparator;
import java.util.Locale;

public class FileSortByName {

  public static Comparator<File> sortDateAsc() {
    return Comparator.comparingLong((File obj) -> obj.lastModified());
  }

  public static Comparator<File> sortDateDesc() {
    return (file1, file2) -> Long.compare(file2.lastModified(), file1.lastModified());
  }

  public static Comparator<File> sortNameAsc() {
    return Comparator.comparing((File file) -> file.getName().toLowerCase(Locale.getDefault()));
  }

  public static Comparator<File> sortNameDesc() {
    return (file1, file2) ->
        file2
            .getName()
            .toLowerCase(Locale.getDefault())
            .compareTo(file1.getName().toLowerCase(Locale.getDefault()));
  }

  public static Comparator<File> sortSizeAsc() {
    return Comparator.comparingLong((File obj) -> obj.length());
  }

  public static Comparator<File> sortSizeDesc() {
    return (file1, file2) -> Long.compare(file2.length(), file1.length());
  }
}

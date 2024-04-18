package ir.ninjacoder.psptools.rewinter.dialogs;

import android.content.Context;
import android.widget.Toast;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import ir.ninjacoder.psptools.rewinter.MainActivity;
import ir.ninjacoder.psptools.rewinter.utils.DialogUtil;

public class ExitUtils {
  protected Context c;
  private static int number = 0;

  public ExitUtils(Context c) {
    this.c = c;
    init();
  }

  void init() {
    number++;
    if (number == 2) {
      DialogUtil dialog = new DialogUtil(c);
      dialog.setTitle("exit");
      dialog.setCancelable(false);
      dialog.setMessage("Are you sure you want to leave the program?");
      dialog.setPositiveButton(
          android.R.string.ok,
          (ct, ccc) -> {
            number = 0;
            ((MainActivity) c).finishAffinity();
          });
      dialog.setNegativeButton(
          android.R.string.cancel,
          (cc, dodo) -> {
            number = 0;
          });
      dialog.show();
    } else Toast.makeText(c, "Click 2 Exit", 2).show();
  }
}

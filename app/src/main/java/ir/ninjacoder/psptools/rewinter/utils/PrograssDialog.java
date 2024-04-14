package ir.ninjacoder.psptools.rewinter.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import ir.ninjacoder.psptools.rewinter.databinding.SoftprograssbarBinding;

public class PrograssDialog {
  protected SoftprograssbarBinding binding;
  protected AlertDialog dialog;
  protected MaterialAlertDialogBuilder materialDialog;
  protected Context context;

  public PrograssDialog(Context context) {
    this.context = context;
    materialDialog = new MaterialAlertDialogBuilder(context);
    binding = SoftprograssbarBinding.inflate(LayoutInflater.from(context));
    materialDialog.setView(binding.getRoot());
    dialog = materialDialog.create();
  }

  public void setTitle(String title) {
    dialog.setTitle(title);
  }

  public void show() {
    dialog.show();
  }

  public void dismiss() {
    dialog.dismiss();
  }
}

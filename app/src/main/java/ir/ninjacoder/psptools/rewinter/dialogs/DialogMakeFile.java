package ir.ninjacoder.psptools.rewinter.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import ir.ninjacoder.psptools.rewinter.databinding.FoldermakerBinding;
import ir.ninjacoder.psptools.rewinter.interfaces.OnFileChange;
import ir.ninjacoder.psptools.rewinter.utils.DialogUtil;
import ir.ninjacoder.psptools.rewinter.utils.FileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DialogMakeFile {
  protected Context ctx;
  protected FoldermakerBinding bin;
  protected DialogUtil dialog;
  protected String path;
  private OnFileChange change;

  public DialogMakeFile(Context ctx, String path, OnFileChange change) {
    this.ctx = ctx;
    this.path = path;
    this.change = change;
    dialog = new DialogUtil(ctx);
    bin = FoldermakerBinding.inflate(LayoutInflater.from(ctx));
    dialog.setView(bin.getRoot());
    dialog.setTitle("make");
    bin.input.setHint(path);
    dialog.setPositiveButton("Folder", (c, f) -> createNewFolder());
    dialog.setNegativeButton("File", (c, ff) -> createNewFile());
    dialog.build();
  }

  public void createNewFolder() {
    if (FileUtil.isDirectory(path)) {
      var text = bin.input.getEditText().getText().toString();
      FileUtil.makeDir(path + "/" + text);
      change.onMakeFileChange();
    }
  }

  public void createNewFile() {
    try {
      var text = bin.input.getEditText().getText().toString();
      File file = new File(path, text);
      if (!file.exists()) {
        file.createNewFile();
        FileOutputStream in = new FileOutputStream(file);
        String sampleText = "This is a sample text for the new file.";
        in.write(sampleText.getBytes());
        in.close();
        change.onMakeFileChange();
      }
    } catch (IOException err) {

    }
  }
}

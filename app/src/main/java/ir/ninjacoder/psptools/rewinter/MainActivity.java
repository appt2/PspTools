package ir.ninjacoder.psptools.rewinter;

import android.Manifest;
import android.net.Uri;
import android.content.Intent;
import com.blankj.utilcode.util.FileUtils;
import ir.ninjacoder.psptools.rewinter.utils.FileSortByName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import org.ppsspp.ppsspp.PpssppActivity;
import android.util.Log;
import android.view.View;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.android.material.color.MaterialColors;
import ir.ninjacoder.psptools.rewinter.adapters.FileListAdapter;
import ir.ninjacoder.psptools.rewinter.databinding.ActivityMainBinding;
import ir.ninjacoder.psptools.rewinter.dialogs.DialogMakeFile;
import ir.ninjacoder.psptools.rewinter.interfaces.OnItemClick;
import ir.ninjacoder.psptools.rewinter.utils.BaseCompat;
import ir.ninjacoder.psptools.rewinter.utils.FileUtil;
import ir.ninjacoder.psptools.rewinter.utils.PrograssDialog;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import static android.view.View.VISIBLE;
import static android.view.View.GONE;

public class MainActivity extends BaseCompat implements OnItemClick {
  private ActivityMainBinding binding;
  private FileListAdapter filelist;
  private GridLayoutManager manger;
  private File file;
  protected List<File> fileList;
  protected String path = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    setSupportActionBar(binding.toolbar);
    setTitle("Pt");
    binding.toolbar.setTitleTextColor(
        MaterialColors.getColor(
            binding.toolbar, com.google.android.material.R.attr.colorPrimary, 0));
    fileListPr();
    path = FileUtil.getExternalStorageDir();
    reloadFile(path);
    onBack();
    listCh();

    binding.fb.setOnClickListener(
        x -> {
          var dialog =
              new DialogMakeFile(
                  this,
                  file.getAbsolutePath(),
                  () -> {
                    reloadFile(file.getAbsolutePath());
                  });
        });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    this.binding = null;
  }

  void onBack() {
    getOnBackPressedDispatcher()
        .addCallback(
            this,
            new OnBackPressedCallback(true) {
              @Override
              public void handleOnBackPressed() {
                if (file != null && !file.getAbsolutePath().equals(path)) {
                  reloadFile(file.getParent());
                } else {
                  finishAffinity();
                }
              }
            });
  }

  void reloadFile(String path) {
    showPrograss(true);
    new Thread(
            () -> {
              file = new File(path);
              File[] files = file.listFiles();
              fileList = new ArrayList<>(Arrays.asList(files));
              Collections.sort(fileList, FileSortByName.sortNameAsc());
              filelist = new FileListAdapter(fileList, this);
              manger = new GridLayoutManager(this, 2);
              runOnUiThread(
                  () -> {
                    binding.rv.setAdapter(filelist);
                    binding.rv.setLayoutManager(manger);
                    showPrograss(false);
                  });
            })
        .start();
  }

  void fileListPr() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_DENIED
        || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions(
          this,
          new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
          },
          1000);
    }
  }

  @Override
  public void onClick(File file, int pos, View view) {
    // TODO: Implement this method
    if (file != null) {
      if (file.isDirectory()) {
        reloadFile(file.getAbsolutePath());
      } else if (file.getName().endsWith(".iso")) {
        Uri uri = Uri.fromFile(file);
        respondToShortcutRequest(uri);
      }
    }
  }

  void listCh() {
    if (fileList == null || !fileList.isEmpty()) {
      binding.emptyview.setVisibility(View.GONE);
      binding.rv.setVisibility(View.VISIBLE);
    } else {
      binding.emptyview.setVisibility(View.VISIBLE);
      binding.rv.setVisibility(View.GONE);
    }
  }

  private void respondToShortcutRequest(Uri uri) {

    Intent shortcutIntent = new Intent(getApplicationContext(), PpssppActivity.class);
    shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    Log.i("TAG", "Shortcut URI: " + uri.toString());
    shortcutIntent.setData(uri);
    String path = uri.toString();
    shortcutIntent.putExtra(PpssppActivity.SHORTCUT_EXTRA_KEY, path);
    startActivity(shortcutIntent);
  }

  void showPrograss(boolean show) {
    binding.prograssBar.setVisibility(show ? VISIBLE : GONE);
  }
}

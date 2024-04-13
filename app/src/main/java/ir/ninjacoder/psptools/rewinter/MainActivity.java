package ir.ninjacoder.psptools.rewinter;

import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.android.material.color.MaterialColors;
import ir.ninjacoder.psptools.rewinter.adapters.FileListAdapter;
import ir.ninjacoder.psptools.rewinter.databinding.ActivityMainBinding;
import java.io.File;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
  private ActivityMainBinding binding;
  private FileListAdapter filelist;
  private GridLayoutManager manger;

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
    File file = new File("/sdcard/");
    File[] files = file.listFiles();
    filelist = new FileListAdapter(Arrays.asList(files));
    manger = new GridLayoutManager(this, 2);
    binding.rv.setAdapter(filelist);
    binding.rv.setLayoutManager(manger);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    this.binding = null;
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
}

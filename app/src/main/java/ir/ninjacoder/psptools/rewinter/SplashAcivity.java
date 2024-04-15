package ir.ninjacoder.psptools.rewinter;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import ir.ninjacoder.psptools.rewinter.databinding.LayoutSplashAcivityBinding;
import ir.ninjacoder.psptools.rewinter.utils.BaseCompat;

public class SplashAcivity extends BaseCompat {
  protected LayoutSplashAcivityBinding bind;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    bind = LayoutSplashAcivityBinding.inflate(getLayoutInflater());
    setContentView(bind.getRoot());
    fileListPr();
    
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
      
    }else{
      Handler hs = new Handler(Looper.getMainLooper());
    hs.postDelayed(
        () -> {
          var i = new Intent(this, MainActivity.class);
          startActivity(i);
        },
        3000);
    }
    
  }
}

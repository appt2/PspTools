package ir.ninjacoder.psptools.rewinter;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import ir.ninjacoder.psptools.rewinter.databinding.LayoutSplashAcivityBinding;
import ir.ninjacoder.psptools.rewinter.utils.BaseCompat;
import ir.ninjacoder.psptools.rewinter.utils.MatetialColorUtils;

public class SplashAcivity extends BaseCompat {
  protected LayoutSplashAcivityBinding bind;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    bind = LayoutSplashAcivityBinding.inflate(getLayoutInflater());
    setContentView(bind.getRoot());
    fileListPr();
    GradientDrawable d = new GradientDrawable();
    d.setColor(MaterialColors.getColor(this, MatetialColorUtils.getColorPrimary, 0));
    d.setStroke(2, MaterialColors.getColor(this, MatetialColorUtils.getcolorOutline, 0));
    d.setCornerRadius(33f);
    bind.img.setBackground(d);
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
    Handler hs = new Handler(Looper.getMainLooper());
    hs.postDelayed(
        () -> {
          var i = new Intent(this, MainActivity.class);
          startActivity(i);
        },
        3000);
  }
}

package ir.ninjacoder.psptools.rewinter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import ir.ninjacoder.psptools.rewinter.databinding.LayoutSplashAcivityBinding;
import ir.ninjacoder.psptools.rewinter.utils.BaseCompat;

public class SplashAcivity extends BaseCompat {
  protected LayoutSplashAcivityBinding bind;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    bind = LayoutSplashAcivityBinding.inflate(getLayoutInflater());
    setContentView(bind.getRoot());
    Handler hs = new Handler(Looper.getMainLooper());
    hs.postDelayed(
        () -> {
          var i = new Intent(this, MainActivity.class);
          startActivity(i);
        },
        3000);
  }
}

package ir.ninjacoder.psptools.rewinter.utils;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.color.MaterialColors;

public class BaseCompat extends AppCompatActivity {

  boolean sdk29 = Build.VERSION.SDK_INT >= 28;
  boolean sdk21 = Build.VERSION.SDK_INT >= 21;
  boolean sdk29s = Build.VERSION.SDK_INT >= 29;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    int color = MaterialColors.getColor(this,com.google.android.material.R.attr.colorSurface,0);
    int color2 = MaterialColors.getColor(this,com.google.android.material.R.attr.colorOnSurface,0);
    getWindow().setNavigationBarColor(sdk21 ? color : Color.BLACK);
    getWindow().setNavigationBarDividerColor(sdk29 ? color2 : 0);
    getWindow().setNavigationBarContrastEnforced(sdk29s);
  }
}

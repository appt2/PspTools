package ir.ninjacoder.psptools.rewinter.utils;

import android.app.Application;
import android.os.Build;
import static com.google.android.material.R.attr.*;
import com.google.android.material.color.DynamicColors;

public class MatetialColorUtils {
  public static int getColorPrimary = colorPrimary;
  public static int getColorAccent = colorAccent;
  public static int getcolorOnSurface = colorOnSurface;
  public static int getcolorSurface = colorSurface;
  public static int getcolorOutline = colorOutline;
  public static int getcolorControlHighlight = colorControlHighlight;
  protected static final boolean android12 = Build.VERSION.SDK_INT >= 33;

  public static void setMaterialColors(Application a) {
    if (android12 && DynamicColors.isDynamicColorAvailable())
      DynamicColors.applyToActivitiesIfAvailable(a);
  }
}

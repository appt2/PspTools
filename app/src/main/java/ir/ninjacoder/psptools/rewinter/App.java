package ir.ninjacoder.psptools.rewinter;

import android.app.Application;
import com.google.android.material.color.MaterialColorUtilitiesHelper;
import com.google.android.material.shape.CornerFamily;
import ir.ninjacoder.psptools.rewinter.utils.MatetialColorUtils;

public class App extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    // TODO: Implement this method
    MatetialColorUtils.setMaterialColors(this);
    
    
  }
}

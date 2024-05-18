package ir.ninjacoder.psptools.rewinter;

import android.app.Application;
import android.content.Context;
import com.google.android.material.color.MaterialColorUtilitiesHelper;
import com.google.android.material.shape.CornerFamily;
import ir.ninjacoder.psptools.rewinter.utils.MatetialColorUtils;

public class App extends Application {
  
  protected static Context ctx;
  
  public static Context getContext(){
    return ctx;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    // TODO: Implement this method
    MatetialColorUtils.setMaterialColors(this);
    
  }
}

package ir.ninjacoder.psptools.rewinter;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.color.DynamicColors;
import ir.ninjacoder.psptools.rewinter.databinding.LayoutSettingsAcivityBinding;
import ir.ninjacoder.psptools.rewinter.utils.BaseCompat;

public class SettingsAcivity extends BaseCompat {

  protected LayoutSettingsAcivityBinding bind;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    bind = LayoutSettingsAcivityBinding.inflate(getLayoutInflater());

    super.onCreate(savedInstanceState);
    setContentView(bind.getRoot());
    bind.switchMaterialmod.setText("Material Color?");

    bind.switchMaterialmod.setOnCheckedChangeListener(
    (btn, is) -> {
        if (is && DynamicColors.isDynamicColorAvailable()) {
            DynamicColors.applyToActivityIfAvailable(this);
        } else {
            DynamicColors.wrapContextIfAvailable(this);
        }
    });
  }
}

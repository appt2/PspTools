package ir.ninjacoder.psptools.rewinter.adapters;

import android.graphics.Color;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import ir.ninjacoder.psptools.rewinter.databinding.ItemlisthorBinding;
import ir.ninjacoder.psptools.rewinter.interfaces.OnItemClick;
import ir.ninjacoder.psptools.rewinter.interfaces.OnTreeViewClick;
import java.io.File;
import java.util.List;

public class ToolBarItemFileList extends RecyclerView.Adapter<ToolBarItemFileList.Holder> {
  private List<String> listFile;
  protected ItemlisthorBinding bi;
  protected OnTreeViewClick click;

  public ToolBarItemFileList(List<String> listFile, OnTreeViewClick click) {
    this.listFile = listFile;
    this.click = click;
  }

  @Override
  public Holder onCreateViewHolder(ViewGroup parant, int viewType) {

    return new Holder(
        ItemlisthorBinding.inflate(LayoutInflater.from(parant.getContext()), parant, false));
  }

  @Override
  public void onBindViewHolder(Holder holder, int pos) {
    String str = listFile.get(pos);
    File files = new File(str);

    holder.name.setText(files.getName());
    holder.name.setAlpha(files.isHidden() ? 0.5f : 1f);
    
    if (pos == 0) {
      holder.icon.setVisibility(View.VISIBLE);
      holder.name.setTextColor(Color.BLUE);
    }
    
  }

  @Override
  public int getItemCount() {
    return listFile.size();
  }

  class Holder extends RecyclerView.ViewHolder {

    protected ImageView icon;
    protected TextView name;

    public Holder(ItemlisthorBinding view) {
      super(view.getRoot());
      icon = view.iconplus;
      name = view.tvHolder;
    }
  }
}

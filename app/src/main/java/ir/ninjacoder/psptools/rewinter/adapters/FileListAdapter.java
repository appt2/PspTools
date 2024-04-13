package ir.ninjacoder.psptools.rewinter.adapters;

import android.view.LayoutInflater;
import android.view.View;
import ir.ninjacoder.psptools.rewinter.R;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import ir.ninjacoder.psptools.rewinter.databinding.FilelistBinding;
import ir.ninjacoder.psptools.rewinter.interfaces.OnItemClick;
import java.io.File;
import java.util.List;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.Holder> {
  private List<File> listFile;
  protected FilelistBinding bi;
  protected OnItemClick click;

  public FileListAdapter(List<File> listFile, OnItemClick click) {
    this.listFile = listFile;
    this.click = click;
  }

  @Override
  public Holder onCreateViewHolder(ViewGroup parant, int viewType) {

    return new Holder(
        FilelistBinding.inflate(LayoutInflater.from(parant.getContext()), parant, false));
  }

  @Override
  public void onBindViewHolder(Holder holder, int pos) {
    File files = listFile.get(pos);
    if (files.isDirectory()) {
      holder.icon.setImageResource(R.drawable.ic_launcher_foreground);
    }
    holder.name.setText(files.getName());
    holder.itemView.setOnClickListener(x -> click.onClick(files,holder.getAdapterPosition(),x));
  }

  @Override
  public int getItemCount() {
    return listFile.size();
  }

  class Holder extends RecyclerView.ViewHolder {

    protected ImageView icon;
    protected TextView name, sub;

    public Holder(FilelistBinding view) {
      super(view.getRoot());
      icon = view.icon;
      name = view.name;
      sub = view.sub;
    }
  }
}

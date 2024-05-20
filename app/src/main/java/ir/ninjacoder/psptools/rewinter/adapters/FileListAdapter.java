package ir.ninjacoder.psptools.rewinter.adapters;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.bluewhaleyt.materialfileicon.core.FileIconHelper;
import com.bumptech.glide.Glide;
import ir.ninjacoder.psptools.rewinter.R;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import ir.ninjacoder.psptools.rewinter.databinding.FilelistBinding;
import ir.ninjacoder.psptools.rewinter.interfaces.OnItemClick;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.io.IOException;
import java.util.List;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.Holder> {
  private List<File> listFile;
  protected FilelistBinding bi;
  protected OnItemClick click;
  protected Context context;

  public FileListAdapter(List<File> listFile, OnItemClick click, Context context) {
    this.listFile = listFile;
    this.click = click;
    this.context = context;
  }

  @Override
  public Holder onCreateViewHolder(ViewGroup parant, int viewType) {

    return new Holder(
        FilelistBinding.inflate(LayoutInflater.from(parant.getContext()), parant, false));
  }

  @Override
  public void onBindViewHolder(Holder holder, int pos) {
    File files = listFile.get(pos);
    FileIconHelper helper = new FileIconHelper(files.getPath());
    helper.setFilePath(files.getPath());
    helper.setDynamicFolderEnabled(true);
    helper.setEnvironmentEnabled(true);
    holder.icon.setImageResource(helper.getFileIcon());
    float startScale = 1.15f;
    float endScale = 1f;
    int animationDuration = 300;
    ValueAnimator animator = ValueAnimator.ofFloat(startScale, endScale);
    animator.setDuration(animationDuration);
    animator.setInterpolator(new FastOutSlowInInterpolator());
    animator.addUpdateListener(
        animation -> {
          float value = (float) animation.getAnimatedValue();
          holder.itemView.setScaleY(value);
          holder.itemView.setScaleX(value);
        });
    animator.start();

    if (getEnd(files)) {
      Glide.with(holder.icon.getContext())
          .load(files)
          .placeholder(R.drawable.ic_launcher_foreground)
          .into(holder.icon);
    }
    holder.itemView.setOnLongClickListener(
        v -> {
          copyFileNamesToClipboardFromPsp(listFile);
          return false;
        });
    holder.name.setText(files.getName());
    if (!files.isDirectory()) {
      setHolder(holder.sub, files);
    } else
      ThreadUtils.executeByIo(
          new ThreadUtils.Task<String>() {

            @Override
            public String doInBackground() throws Throwable {
              return getDirTotal(files);
            }

            @Override
            public void onFail(Throwable tr) {
              holder.sub.setText(tr.getLocalizedMessage());
            }

            @Override
            public void onSuccess(String str) {
              holder.sub.setText(str);
            }

            @Override
            public void onCancel() {}
          });
    holder.itemView.setOnClickListener(x -> click.onClick(files, holder.getAdapterPosition(), x));
    if (getEmptyFolder(files)) {
      holder.icon.setImageResource(R.drawable.ic_material_documents);
    }
    Path path = Paths.get(files.getName());
    try {
    	if (Files.isHidden(path)) {
      holder.itemView.setVisibility(View.INVISIBLE);
    } else holder.itemView.setVisibility(View.VISIBLE);
    } catch(IOException err) {
    	err.printStackTrace();
    }
  }

  public boolean getEmptyFolder(File file) {
    return file.isDirectory() && file.listFiles().length == 0;
  }

  @Override
  public int getItemCount() {
    return listFile.size();
  }

  public void removeItemWithAnimation(int pos, File file) {
    notifyItemRemoved(pos);
  }

  public List<File> getNonHiddenFiles(File directory) {
     
    return Arrays.stream(directory.listFiles())
        .filter(file -> !file.isHidden())
        .collect(Collectors.toList());
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

  public boolean getEnd(File fo) {
    var name = fo.getName();
    return name.endsWith(".jpg".toLowerCase()) || name.endsWith(".png".toLowerCase());
  }

  public String getDirTotal(File paths) {

    try {
      File directory = paths;

      List<File> fileList =
          Files.list(directory.toPath()).map(Path::toFile).collect(Collectors.toList());

      if (fileList.isEmpty()) {
        return "Folder : 0 Files: 0";
      } else {
        long folderCount = fileList.stream().filter(File::isDirectory).count();

        long fileCount = fileList.stream().filter(File::isFile).count();

        return "Folder : " + folderCount + " File : " + fileCount;
      }
    } catch (IOException e) {
      return e.getLocalizedMessage();
    }
  }

  protected String getSize(File files) {
    return "Size "
        + FileUtils.getSize(files)
        + "| "
        + "Modified"
        + String.valueOf(FileUtils.getFileLastModified(files));
  }

  public void setHolder(TextView view, File file) {
    ThreadUtils.executeByIo(
        new ThreadUtils.Task<String>() {

          @Override
          public String doInBackground() throws Throwable {
            return getSize(file);
          }

          @Override
          public void onSuccess(String str) {
            if (view != null) {
              view.setText(str);
            }
          }

          @Override
          public void onCancel() {}

          @Override
          public void onFail(Throwable ss) {

            ThreadUtils.runOnUiThread(() -> view.setText(ss.getLocalizedMessage()));
          }
        });
  }

  private void copyFileNamesToClipboardFromPsp(List<File> listFile) {
    StringBuilder fileNamesBuilder = new StringBuilder();
    for (File file : listFile) {
      if (file.isDirectory()) {
        continue;
      }
      fileNamesBuilder.append(file.getName()).append(" = ").append(file.getName()).append("\n");
    }
    String fileNames = fileNamesBuilder.toString();
    ClipboardUtils.copyText(fileNames);
  }

  
}

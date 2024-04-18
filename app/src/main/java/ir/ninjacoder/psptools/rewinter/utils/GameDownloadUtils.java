package ir.ninjacoder.psptools.rewinter.utils;

import android.content.Context;
import android.net.Uri;
import com.ixuea.android.downloader.callback.DownloadListener;
import com.ixuea.android.downloader.domain.DownloadInfo;
import com.ixuea.android.downloader.exception.DownloadException;
import ir.ninjacoder.psptools.rewinter.model.ListGameModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameDownloadUtils implements DownloadListener {
  private Context context;
  private ListGameModel model;
  protected DownloadInfo downloadInfo;
  protected
  /** for adpter game */
  List<ListGameModel> listGame = new ArrayList<>();

  public GameDownloadUtils(Context context) {
    this.context = context;
  }

  public void setUrlGame(String uri) {
    File file = model.getGamePath();
    downloadInfo = new DownloadInfo.Builder().setUrl(uri).setPath(file.getAbsolutePath()).build();
    downloadInfo.setDownloadListener(this);
    
  }

  @Override
  public void onStart() {}

  @Override
  public void onWaited() {}

  @Override
  public void onPaused() {}

  @Override
  public void onDownloading(long progress, long size) {
    model.setSize(String.valueOf(progress) + " " + String.valueOf(size));
  }

  @Override
  public void onRemoved() {}

  @Override
  public void onDownloadSuccess() {}

  @Override
  public void onDownloadFailed(DownloadException e) {}
}

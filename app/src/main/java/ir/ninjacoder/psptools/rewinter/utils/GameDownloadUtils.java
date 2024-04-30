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

public class GameDownloadUtils {
  private ListGameModel model;
  protected DownloadInfo downloadInfo;

  public void setRun() {
    try {
      File file = model.getGamePath();
      downloadInfo =
          new DownloadInfo.Builder()
              .setUrl(model.getName())
              .setPath(file.getAbsolutePath())
              .build();
    } catch (Exception d) {
      throw new RuntimeException("You not call #setDownloadCallBack");
    }
  }

  public void setDownloadCallBack(DownloadListener dow) {
    try {
      downloadInfo.setDownloadListener(dow);
    } catch (Exception err) {
      throw new RuntimeException("You not call setRun");
    }
  }
}

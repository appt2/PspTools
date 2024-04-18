package com.ixuea.android.downloader.callback;

import com.ixuea.android.downloader.db.DownloadDBController;
import com.ixuea.android.downloader.domain.DownloadInfo;

import java.util.List;

/** Created by ixuea(http://a.ixuea.com/3) on 19/9/2021. */
public interface DownloadManager {

  void download(DownloadInfo downloadInfo);

  void pause(DownloadInfo downloadInfo);

  void resume(DownloadInfo downloadInfo);

  void remove(DownloadInfo downloadInfo);

  void destroy();

  DownloadInfo getDownloadById(String id);

  List<DownloadInfo> findAllDownloading();

  List<DownloadInfo> findAllDownloaded();

  DownloadDBController getDownloadDBController();

  void resumeAll();

  void pauseAll();

  void onDownloadFailed(DownloadInfo downloadInfo);
}

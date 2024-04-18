package com.ixuea.android.downloader.domain;

import java.io.Serializable;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 19/9/2021.
 */

public class DownloadThreadInfo implements Serializable {

    /**
     * Each download thread id.
     */
    private int id;
    private int threadId;
    private String downloadInfoId;
    private String uri;
    private long start;
    private long end;
    private long progress;

    public DownloadThreadInfo(int threadId, String downloadInfoId, String uri, long start,
                              long end) {
        this.id = downloadInfoId.hashCode() + threadId;
        this.threadId = threadId;
        this.downloadInfoId = downloadInfoId;
        this.uri = uri;
        this.start = start;
        this.end = end;
    }

    public DownloadThreadInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDownloadInfoId() {
        return downloadInfoId;
    }

    public void setDownloadInfoId(String downloadInfoId) {
        this.downloadInfoId = downloadInfoId;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public boolean isThreadDownloadSuccess() {
        return progress >= (end - start);
    }
}

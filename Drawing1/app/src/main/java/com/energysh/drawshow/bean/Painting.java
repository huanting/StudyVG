package com.energysh.drawshow.bean;

import android.util.Log;

import com.energysh.drawshow.io.IOHelper;

/**
 * 一副画作，主要封装了各种路径
 *
 * @author hankcs
 */
public class Painting
{
    /**
     * 根目录
     */
    private String root;
    private String undoFolder;
    private String recordFolder;
    /**
     * 大图
     */
    private String snapshot;
    /**
     * 小图
     */
    private String thumbnail;
    /**
     * .project文件路径
     */
    private String projectFile;
    /**
     * 保存文件
     */
    private String savedFile;
    /**
     * 自动保存文件
     */
    private String resumeFile;

    public Painting(String root)
    {
        this.root = root;
        undoFolder = IOHelper.getUndoFolderOf(root);
        recordFolder = IOHelper.getRecordFolderOf(root);
        snapshot = IOHelper.getSnapshotFilePath(root);
        thumbnail = IOHelper.getThumbnailFilePath(root);
        projectFile = IOHelper.getProjectFilePath(root);
        savedFile = IOHelper.getSavedFilePath(root);
        resumeFile = IOHelper.getResumeFilePath(root);
    }

    public String getRoot()
    {
        Log.i("test", "getRoot " + root);
        return root;
    }

    public String getUndoFolder()
    {
        Log.i("test", "getUndoFolder " + undoFolder);

        return undoFolder;
    }

    public String getRecordFolder()
    {
        return recordFolder;
    }

    public String getSnapshot()
    {
        return snapshot;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public String getProjectFile()
    {
        return projectFile;
    }

    public String getSavedFile()
    {
        return savedFile;
    }

    public String getResumeFile()
    {
        return resumeFile;
    }
}
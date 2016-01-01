package com.energysh.drawshow.io;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import com.energysh.drawshow.util.Predefine;
import com.energysh.drawshow.util.TimeTool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * @author hankcs
 */
public class IOHelper
{
    public static final String THUMBNAIL_PNG = "thumbnail.png";
    public static final String PROJECT = ".project";

    private static int THUMB_WIDTH = 300;
    private static int THUMB_HEIGHT = 300;

    /**
     * SD卡目录中的应用目录
     */
    private static String ROOT;
    private static String STUDENT_FOLDER;
    private static String TEACHER_FOLDER;
    private static final String RECORD = "record/";
    private static final String UNDO = "undo/";

    /**
     * 初始化helper
     * @param context
     */
    public static boolean init(Context context)
    {
        ROOT = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED) ?
                Environment.getExternalStorageDirectory().getPath() :
                context.getFilesDir().getPath();
        ROOT += "/" + Predefine.TAG + "/";
        File root = new File(ROOT);
        if (!root.exists())
        {
            if (!root.mkdir())
            {
                Log.e(Predefine.TAG, "创建根目录失败！" + ROOT);
                return false;
            }
        }


        STUDENT_FOLDER = ROOT + "student/";
        File studentFolder = new File(STUDENT_FOLDER);
        if (!studentFolder.exists())
        {
            if (!studentFolder.mkdir())
            {
                Log.e(Predefine.TAG, "创建学生目录失败！" + STUDENT_FOLDER);
                return false;
            }
        }


        TEACHER_FOLDER = ROOT + "teacher/";
        File teacherFoler = new File(TEACHER_FOLDER);
        if (!teacherFoler.exists())
        {
            if (!teacherFoler.mkdir())
            {
                Log.e(Predefine.TAG, "创建教师目录失败！" + TEACHER_FOLDER);
                return false;
            }
        }

        return true;
    }

    public static String getRootPath()
    {
        return ROOT;
    }

    public static String getStudentFolder()
    {
        return STUDENT_FOLDER;
    }

    public static String getTeacherFolder() { return TEACHER_FOLDER;}

    /**
     * 创建目录
     * @param parent 父目录
     * @param folder 子目录
     * @return 子目录，null表示失败
     */
    public static String createFolder(String parent, String folder)
    {
        File folderFile = new File(parent + folder);
        if (!folderFile.exists() && !folderFile.mkdirs())
        {
            Log.e(Predefine.TAG, "创建目录失败！" + folderFile.getAbsolutePath());
            return null;
        }

        return parent + folder;
    }

    /**
     * 在学生目录下创建一个目录
     * @param folder
     * @return
     */
    public static String createFolderUnderStudent(String folder)
    {
        return createFolder(STUDENT_FOLDER, folder);
    }

    public static String createFolderUnderStudent()
    {
        return createFolderUnderStudent(TimeTool.current() + "/");
    }

    /**
     * 在学生目录下创建一个目录
     * @param folder
     * @return
     */
    public static String createFolderUnderTeacher(String folder)
    {
        return createFolder(TEACHER_FOLDER, folder);
    }

    public static String createFolderUnderTeacher()
    {
        return createFolderUnderTeacher(TimeTool.current() + "/");
    }

    public static String getRecordFolderOf(String folder)
    {
        return createFolder(folder, RECORD);
    }

    public static String getUndoFolderOf(String folder)
    {
        return createFolder(folder, UNDO);
    }

    public static String getSavedFilePath(String folder)
    {
        return folder + "final.vg";
    }

    public static String getResumeFilePath(String folder)
    {
        return folder + "resume.vg";
    }

    public static String getSnapshotFilePath(String folder)
    {
        return folder + "snapshot.png";
    }

    public static String getThumbnailFilePath(String folder)
    {
        return folder + THUMBNAIL_PNG;
    }

    public static String getProjectFilePath(String folder)
    {
        return folder + PROJECT;
    }

    /**
     * Gets the corresponding path to a file from the given content:// URI
     *
     * @param selectedVideoUri The content:// URI to find the file path from
     * @param contentResolver  The content resolver to use to perform the query.
     * @return the file path as a string
     */
    public static String getFilePathFromContentUri(Uri selectedVideoUri,
                                                   ContentResolver contentResolver)
    {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};

        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    public static Bitmap loadBitmap(String path)
    {
        return BitmapFactory.decodeFile(path);
    }


    public static Bitmap getScaleBitmap(String path)
    {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        float hh = THUMB_HEIGHT;
        float ww = THUMB_WIDTH;

        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww)
        {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        }
        else if (w < h && h > hh)
        {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例

        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(path, newOpts);
        return bitmap;
    }
//
//    // 图片质量压缩
//    private static Bitmap compressImage(Bitmap image)
//    {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 100;
//        while (baos.toByteArray().length / 1024 > 100)
//        { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();// 重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
//            options -= 10;// 每次都减少10
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
//        return bitmap;
//    }
}

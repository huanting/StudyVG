package com.energysh.drawshow.io;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.energysh.drawshow.R;
import com.energysh.drawshow.bean.Painting;
import com.energysh.drawshow.bean.PicTypeData;
import com.energysh.drawshow.util.Predefine;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Administrator on 2015/12/24.
 */
public class PicTypeHelper {

    public static ArrayList<PicTypeData> getTypeData(int typeId)
    {
        ArrayList<PicTypeData> tmpList = new ArrayList<PicTypeData>();
        switch (typeId)
        {
            case R.string.animal:

                File teacherFolder = new File(IOHelper.getTeacherFolder());
                if (!teacherFolder.exists())
                {
                    Log.w(Predefine.TAG, "用户目录未创建");
                    break;
                }

                for (File folder : teacherFolder.listFiles())
                {
                    if (folder.isFile())
                        continue;
                    String thumbPath = IOHelper.getThumbnailFilePath(folder.getAbsolutePath() + "/");
                    Matrix matrix = new Matrix();
                    // 缩放原图
                    matrix.postScale(0.25f, 0.25f);
                    Bitmap bitmap = IOHelper.loadBitmap(thumbPath);
                    if (bitmap != null)
                    {
                       PicTypeData data = new PicTypeData();
                        data.Thumb = bitmap;
                        data.Title = "";
                        data.Path = folder.getAbsolutePath() + "/";
                        tmpList.add(data);
                    }
                }
                break;
        }

        return tmpList;
    }


}

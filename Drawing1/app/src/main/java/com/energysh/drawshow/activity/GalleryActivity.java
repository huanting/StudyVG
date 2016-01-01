package com.energysh.drawshow.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.energysh.drawshow.Globals;
import com.energysh.drawshow.MainActivity;
import com.energysh.drawshow.SelectType;
import com.energysh.drawshow.adapter.SquareGridAdapter;
import com.energysh.drawshow.io.IOHelper;
import com.energysh.drawshow.util.Predefine;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hankcs
 */
public class GalleryActivity extends AbstractGridViewActivity
{

    @Override
    protected List<SquareGridAdapter.Item> fetchData()
    {
        File studentFolder = new File(IOHelper.getStudentFolder());
        if (!studentFolder.exists())
        {
            Log.w(Predefine.TAG, "用户目录未创建");
            return Collections.emptyList();
        }

        List<SquareGridAdapter.Item> itemList = new ArrayList<SquareGridAdapter.Item>();
        for (File folder : studentFolder.listFiles())
        {
            if (folder.isFile()) continue;
            String snapshotFilePath = IOHelper.getSnapshotFilePath(folder.getAbsolutePath() + "/");
            Bitmap bitmap = IOHelper.loadBitmap(snapshotFilePath);
            if (bitmap != null)
            {
                SquareGridAdapter.Item item = new SquareGridAdapter.Item(bitmap, -1);
                item.tag = folder.getAbsolutePath() + "/";
                itemList.add(item);
            }
        }

        return itemList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Globals.mSelectType = SelectType.STUDENT;
        String tag = (String) view.getTag();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("paintingPath", tag);
        startActivity(intent);
    }
}
package com.energysh.drawshow.activity;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.energysh.drawshow.Globals;
import com.energysh.drawshow.MainActivity;
import com.energysh.drawshow.R;
import com.energysh.drawshow.SelectType;
import com.energysh.drawshow.adapter.SquareGridAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainPageActivity extends AbstractGridViewActivity
{
    private Menu mOptionMenu;

    @Override
    protected List<SquareGridAdapter.Item> fetchData()
    {
        List<SquareGridAdapter.Item> itemList = new ArrayList<SquareGridAdapter.Item>();
        itemList.add(new SquareGridAdapter.Item(R.drawable.animal_h_11, R.string.animal, getResources()));
        itemList.add(new SquareGridAdapter.Item(R.drawable.anmyes_com_d_15, R.string.anmyes, getResources()));
        itemList.add(new SquareGridAdapter.Item(R.drawable.car_b_3, R.string.car, getResources()));
        return itemList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        int titleId = getTitleIdOf(view);
        Intent intent = new Intent(this, DetailPageActivity.class);
        Log.i("test", "titleId=" + titleId);
        intent.putExtra("titleId", titleId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionMenu = menu;
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_add)
        {
            Globals.mSelectType = SelectType.NEW;
            Intent intent = new Intent(MainPageActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menu_gallery)
        {
            Intent intent = new Intent(MainPageActivity.this, GalleryActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

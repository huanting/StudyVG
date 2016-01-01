package com.energysh.drawshow.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.energysh.drawshow.MainActivity;
import com.energysh.drawshow.R;
import com.energysh.drawshow.adapter.SquareGridAdapter;
import com.energysh.drawshow.bean.PicTypeData;
import com.energysh.drawshow.io.PicTypeHelper;

import java.util.ArrayList;
import java.util.List;

public class DetailPageActivity extends AbstractGridViewActivity
{
    List<SquareGridAdapter.Item> itemList = null;
    int mSelectCategoryId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        mSelectCategoryId = bundle.getInt("titleId");
        //getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        getActionBar().setTitle(getString(mSelectCategoryId));
        getActionBar().setDisplayUseLogoEnabled(false);


    }

    @Override
    protected List<SquareGridAdapter.Item> fetchData()
    {
        itemList = new ArrayList<SquareGridAdapter.Item>();

        switch (mSelectCategoryId)
        {
            case R.string.animal:
            {
                ArrayList<PicTypeData> animalData = PicTypeHelper.getTypeData(mSelectCategoryId);
                for(int i=0; i< animalData.size(); i++)
                {
                    itemList.add(new SquareGridAdapter.Item(animalData.get(i).Thumb, -1, animalData.get(i).Path));
                }
//                itemList.add(new SquareGridAdapter.Item(R.drawable.animal_a_3, R.string.chicken, getResources()));
//                itemList.add(new SquareGridAdapter.Item(R.drawable.animal_b_10, R.string.duck, getResources()));
//                itemList.add(new SquareGridAdapter.Item(R.drawable.animal_c_6, R.string.rat, getResources()));
//                itemList.add(new SquareGridAdapter.Item(R.drawable.animal_h_11, R.string.dog, getResources()));
//                itemList.add(new SquareGridAdapter.Item(R.drawable.animal_i_10, R.string.frog, getResources()));
//                itemList.add(new SquareGridAdapter.Item(R.drawable.animal_j_8, R.string.penguin, getResources()));
//                itemList.add(new SquareGridAdapter.Item(R.drawable.animal_m_5, R.string.goat, getResources()));
//                itemList.add(new SquareGridAdapter.Item(R.drawable.animal_n_5, R.string.rabbit, getResources()));
//                itemList.add(new SquareGridAdapter.Item(R.drawable.animal_p_5, R.string.snake, getResources()));
//                itemList.add(new SquareGridAdapter.Item(R.drawable.animal_q_5, R.string.owl, getResources()));
//                itemList.add(new SquareGridAdapter.Item(R.drawable.animal_r_5, R.string.reindeer, getResources()));
            }break;
            case R.string.anmyes:
            {
                itemList.add(new SquareGridAdapter.Item(R.drawable.anmyes_com_a_14, R.string.haizeiwang, getResources()));
                itemList.add(new SquareGridAdapter.Item(R.drawable.anmyes_com_d_15, R.string.mfsn, getResources()));
                itemList.add(new SquareGridAdapter.Item(R.drawable.anmyes_com_i_15, R.string.huoying, getResources()));
                itemList.add(new SquareGridAdapter.Item(R.drawable.anmyes_com_q_15, R.string.kenan, getResources()));
                itemList.add(new SquareGridAdapter.Item(R.drawable.anmyes_com_x_14, R.string.quanyecha, getResources()));
            }break;
        }

        return itemList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        Intent intent = new Intent(DetailPageActivity.this, MainActivity.class);
        intent.putExtra("paintingPath", itemList.get(position).path);
        startActivity(intent);
    }
}

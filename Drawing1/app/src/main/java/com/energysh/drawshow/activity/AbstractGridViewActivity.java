/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>me@hankcs.com</email>
 * <create-date>2015/12/23 9:50</create-date>
 *
 * <copyright file="AbstractGridViewActivity.java" company="码农场">
 * Copyright (c) 2008-2015, 码农场. All Right Reserved, http://www.hankcs.com/
 * This source is subject to Hankcs. Please contact Hankcs to get more information.
 * </copyright>
 */
package com.energysh.drawshow.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.energysh.drawshow.R;
import com.energysh.drawshow.adapter.SquareGridAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hankcs
 */
public abstract class AbstractGridViewActivity extends Activity implements AdapterView.OnItemClickListener
{

    private SquareGridAdapter squareGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        squareGridAdapter = new SquareGridAdapter(getBaseContext(), Collections.<SquareGridAdapter.Item>emptyList());
        GridView gridView = (GridView) findViewById(R.id.grid_view_for_main_page);
        gridView.setAdapter(squareGridAdapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        squareGridAdapter.setItems(fetchData());
        squareGridAdapter.notifyDataSetChanged();
    }

    protected abstract List<SquareGridAdapter.Item> fetchData();

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        int titleId = getTitleIdOf(view);
        String text = "未设置tag的" + position;
        if (titleId != -1) text = getResources().getString(titleId);
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected int getTitleIdOf(View view)
    {
        TextView textView = (TextView) view.findViewById(R.id.square_item_text_view);
        if (textView == null) return -1;
        Integer titleId = (Integer) textView.getTag();
        if (titleId == null) return -1;

        return titleId;
    }
}

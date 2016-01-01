package com.energysh.drawshow.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.energysh.drawshow.R;

import java.util.ArrayList;
import java.util.List;

public class SquareGridAdapter extends BaseAdapter
{
    public static class Item
    {
        public Bitmap image;
        public int textId;
        public String tag;
        public String path;

        public Item(int imageId, int textId, Resources res)
        {
            image = BitmapFactory.decodeResource(res, imageId);
            this.textId = textId;
        }

        public Item(Bitmap image, int textId)
        {
            this.image = image;
            this.textId = textId;
        }

        public Item(Bitmap image, int textId, String path)
        {
            this.image = image;
            this.textId = textId;
            this.path = path;
        }
    }

    private List<Item> mItems;
    private Context mContext;

    public SquareGridAdapter(Context mContext, List<Item> mItems)
    {
        this.mItems = mItems;
        this.mContext = mContext;
    }

    public SquareGridAdapter(Context context)
    {
        this(context, new ArrayList<Item>());
    }

    @Override
    public int getCount()
    {
        return mItems.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.square_item_icon);
        TextView text = (TextView) convertView.findViewById(R.id.square_item_text_view);
        Item item = (Item) getItem(position);
        image.setImageBitmap(item.image);
        if (item.textId != -1) text.setText(item.textId);
        else text.setVisibility(View.GONE);
        text.setTag(item.textId);           // 通过textId来辨识究竟点击了哪个item
        convertView.setTag(item.tag);
        return convertView;
    }

    public void setItems(List<Item> itemList)
    {
        mItems.clear();
        this.mItems = itemList;
    }
}

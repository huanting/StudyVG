package com.energysh.drawshow.bean;

import android.graphics.Color;

/**
 * Created by Administrator on 2015/12/23.
 */
public class EditToolData {

    public int mPenColor = Color.BLACK;
    public int mPenWidth=3;
    public final int mPenAlpha = 0xFF;

    public int mPenBlendColor = Color.BLACK;
    public int mPendBlendAlpha = 130;
    public int mPenBlendWidth=3;

    public final int mEraseColor = 0xFFFFFFFF;
    public int mEraseWidth=5;
    public final int mEraseAlpha = 0xFF;

    public int mEraseBlendColor = 0xFFFFFFFF;
    public int mEraseBlendWidth=5;
    public int mEraseBlendAlpha = 130;

    public RecordState mRecordState;
    public SelectType mSelectType;

    public EditToolData()
    {
        mRecordState = RecordState.STOP;
        mSelectType = SelectType.Pen;
        mDisplayTools = true;
    }

    public boolean mDisplayTools = true;
}

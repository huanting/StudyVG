package com.energysh.drawshow;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.energysh.drawshow.bean.*;
import com.energysh.drawshow.bean.SelectType;
import com.energysh.drawshow.customview.Slider;
import com.energysh.drawshow.dialog.LoadingDialog;
import com.energysh.drawshow.io.IOHelper;

import rhcad.touchvg.IGraphView;
import rhcad.touchvg.IGraphView.OnContentChangedListener;
import rhcad.touchvg.IGraphView.OnDrawGestureListener;
import rhcad.touchvg.IGraphView.OnSelectionChangedListener;
import rhcad.touchvg.IViewHelper;
import rhcad.touchvg.ViewFactory;
import rhcad.touchvg.core.GiContext;
import rhcad.touchvg.core.Longs;
import rhcad.touchvg.core.MgShape;
import rhcad.touchvg.core.MgShapeDoc;
import rhcad.touchvg.core.MgShapes;
import rhcad.touchvg.view.BaseGraphView;
import rhcad.touchvg.view.internal.BaseViewAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.energysh.drawshow.dialog.colorpicker.ColorPickerDialog;

public class MainActivity extends Activity implements OnSelectionChangedListener, ColorPickerDialog.OnColorPickedListener, Slider.OnValueChangedListener, OnDrawGestureListener
{
    private final int SELECT_PHOTO = 1;
    private Painting painting;

    //top part
    private LinearLayout mTopRightLinearlayout;
    private ImageButton mRecordBtn;
    private ImageButton mSavePartBtn;

    private LinearLayout mTopbarLinearlayout; //
    private ImageButton mUndoBtn;
    private ImageButton mRedoBtn;
    private ImageButton mSaveBtn;
    private ImageButton mShareBtn;


    //bottom part
    private LinearLayout mBottombarLinearlayout;
    private ImageButton mPenBtn;
    private ImageButton mPenBlendBtn;
    private ImageButton mEraseBtn;
    private ImageButton mEraseBlendBtn;

    private ImageButton mLeftBtn;
    private ImageButton mRightBtn;

    private ImageButton mGraphicsBtn;
    private ImageButton mPenWidthBtn;
    private ImageButton mColorSelectBtn;

    //graphics part
    private LinearLayout mGraphicsLinearlayout;
    private ImageButton mCircleBtn;
    private ImageButton mBgSelBtn;
    private ImageButton mSelectBtn;

    private Slider mLineWidthSeekbar;

    //data part
    private EditToolData mData = new EditToolData();
    private IViewHelper mHelperDrawing = ViewFactory.createHelper();
    private IViewHelper mHelperAnimation = ViewFactory.createHelper();

    /**
     * 不是第一次点击播放\下一步\上一步
     */
    private boolean notFirstClickPlay;
    static final int PLAY_STEP_FINISH = 1;
    static final int WAIT_DIALOG_DISMISS = 2;
    private Dialog mCustomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i("test", "onCreate");
        super.onCreate(savedInstanceState);

        if(Globals.mSelectType == com.energysh.drawshow.SelectType.TEACHER)
            setContentView(R.layout.activity_main);
        else
            setContentView(R.layout.activity_student_main);

        initData(savedInstanceState);

        ColorPickerDialog.init(this);
        ColorPickerDialog.getInstance().addOnColorPickedListener(this);

        initTopButtons();
        initBottomButtons();
        initGraphicButtons();

        updateButtons();
        initButtonState();

        mHelperDrawing.getGraphView().setOnGestureListener(this);

        mCustomDialog = new LoadingDialog(MainActivity.this);
    }

    private void initData(Bundle savedInstanceState)
    {
        final ViewGroup layout = (ViewGroup) this.findViewById(R.id.container);
        ViewGroup animLayout = (ViewGroup)findViewById(R.id.animation_container);

        if(Globals.mSelectType == com.energysh.drawshow.SelectType.TEACHER)
        {
            mHelperDrawing.createSurfaceView(this, layout, savedInstanceState);
            mHelperDrawing.setCommand("splines");
            mHelperDrawing.setLineColor(mData.mPenColor);
            mHelperDrawing.setStrokeWidth(mData.mPenWidth);
            animLayout.setVisibility(View.GONE);
        }
        else
        {
            mHelperDrawing.createSurfaceView(this, layout, savedInstanceState);
            mHelperDrawing.setCommand("splines");
            mHelperDrawing.setLineColor(mData.mPenColor);
            mHelperDrawing.setStrokeWidth(mData.mPenWidth);
            View viewDrawing = mHelperDrawing.getView();
            viewDrawing.setBackgroundColor(Color.TRANSPARENT);

            animLayout.setVisibility(View.VISIBLE);

            // 初始化动画的SurfaceView
            mHelperAnimation.createSurfaceView(this, (ViewGroup) findViewById(R.id.animation_container), savedInstanceState);
        }

        String paintingPath = getIntent().getStringExtra("paintingPath");
        if (paintingPath == null) painting = new Painting(IOHelper.createFolderUnderStudent());
        else
        {
            painting = new Painting(paintingPath);
            Log.i("test", "saveFile=" + painting.getSavedFile());
            //mHelperDrawing.loadFromFile(painting.getSavedFile());
        }
    }

    private void updateGraphicsLayoutVis()
    {
        //横屏情况
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            mGraphicsBtn.setVisibility(View.GONE);
            mGraphicsBtn.setVisibility(View.GONE);
            mGraphicsLinearlayout.setVisibility(Globals.mSelectType == com.energysh.drawshow.SelectType.STUDENT ? View.GONE : View.VISIBLE);
        }
        else
        {
            mGraphicsBtn.setVisibility( View.VISIBLE);
        }
    }

    private void initButtonState()
    {
        updateGraphicsLayoutVis();
        mLeftBtn.setVisibility(View.GONE);
        mRightBtn.setVisibility(Globals.mSelectType == com.energysh.drawshow.SelectType.STUDENT ? View.VISIBLE : View.GONE);
    }

    private void initBottomButtons()
    {
        mBottombarLinearlayout = (LinearLayout)findViewById(R.id.linearlayout_bottom);
        mPenBtn = (ImageButton)findViewById(R.id.btn_pen);
        mPenBlendBtn = (ImageButton)findViewById(R.id.btn_pen_blend);
        mEraseBtn = (ImageButton)findViewById(R.id.btn_erase);
        mEraseBlendBtn = (ImageButton)findViewById(R.id.btn_erase_blend);
        mGraphicsBtn = (ImageButton)findViewById(R.id.btn_graphics);
        mPenWidthBtn = (ImageButton)findViewById(R.id.btn_pen_width);
        mColorSelectBtn = (ImageButton)findViewById(R.id.btn_color_select);
//        mLineWidthSeekbar = (SeekBar)findViewById(R.id.seekbar_line_width);
//        mLineWidthSeekbar.setOnSeekBarChangeListener(this);

        mLineWidthSeekbar = (Slider)findViewById(R.id.seekbar_line_width);
        mLineWidthSeekbar.setOnValueChangedListener(this);

        mLeftBtn = (ImageButton)findViewById(R.id.imgbtn_left);
        mRightBtn = (ImageButton)findViewById(R.id.imgbtn_right);

        //曲线
        mPenBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelperDrawing.setLineColor(mData.mPenColor);
                mHelperDrawing.setLineAlpha(mData.mPenAlpha);
                mHelperDrawing.setStrokeWidth(mData.mPenWidth);
                mHelperDrawing.setCommand("splines");
                mData.mSelectType = SelectType.Pen;
            }
        });

        mPenBlendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelperDrawing.setLineColor(mData.mPenBlendColor);
                mHelperDrawing.setLineAlpha(mData.mPendBlendAlpha);
                mHelperDrawing.setStrokeWidth(mData.mPenBlendWidth);
                mHelperDrawing.setCommand("splines");
                mData.mSelectType = SelectType.PenBlend;
            }
        });

        mEraseBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelperDrawing.setLineColor(mData.mEraseColor);
                mHelperDrawing.setLineAlpha(mData.mEraseAlpha);
                mHelperDrawing.setStrokeWidth(mData.mEraseWidth);
                mHelperDrawing.setCommand("splines");
                mData.mSelectType = SelectType.Erase;
            }
        });

        mEraseBlendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelperDrawing.setLineColor(mData.mEraseBlendColor);
                mHelperDrawing.setLineAlpha(mData.mEraseBlendAlpha);
                mHelperDrawing.setStrokeWidth(mData.mEraseBlendWidth);
                mHelperDrawing.setCommand("splines");
                mData.mSelectType = SelectType.EraseBlend;
            }
        });

        mGraphicsBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mGraphicsLinearlayout.setVisibility(View.VISIBLE);
                mLineWidthSeekbar.setVisibility(View.GONE);
            }
        });

        mPenWidthBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mGraphicsLinearlayout.setVisibility(View.GONE);
                mLineWidthSeekbar.setVisibility(View.VISIBLE);
            }
        });

        mColorSelectBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog.getInstance().show();
            }
        });

        mLeftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!notFirstClickPlay)
                {
                    mHelperAnimation.coreView().startPlay(painting.getRecordFolder(), BaseViewAdapter.getTick(), false);
                    notFirstClickPlay = true;
                }
                beginThread(false);
                updateButtonWhenPlay();
            }
        });

        mRightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!notFirstClickPlay)
                {
                    mHelperAnimation.coreView().startPlay(painting.getRecordFolder(), BaseViewAdapter.getTick(), false);
                    notFirstClickPlay = true;
                }
                beginThread(true);

                updateButtonWhenPlay();
            }
        });
    }

    private void initTopButtons()
    {
        mTopRightLinearlayout = (LinearLayout)findViewById(R.id.linearlayout_righttop);
        mTopRightLinearlayout.setVisibility(Globals.mSelectType == com.energysh.drawshow.SelectType.TEACHER ? View.VISIBLE : View.GONE);

        mRecordBtn = (ImageButton)findViewById(R.id.btn_start_record);
        mRecordBtn.setImageResource(mData.mRecordState == RecordState.PLAY ? R.drawable.stop_record : R.drawable.start_record);
        mSavePartBtn = (ImageButton)findViewById(R.id.btn_save_part);

        mTopbarLinearlayout = (LinearLayout)findViewById(R.id.linearlayout_topbar);
        mUndoBtn = (ImageButton)findViewById(R.id.btn_undo);
        mRedoBtn = (ImageButton)findViewById(R.id.btn_redo);
        mSaveBtn = (ImageButton)findViewById(R.id.btn_save);
        mShareBtn = (ImageButton)findViewById(R.id.btn_share);

        mRecordBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mData.mRecordState == RecordState.PLAY)
                {
                    mData.mRecordState = RecordState.STOP;
                    mTopRightLinearlayout.setVisibility(View.GONE); //停止record后,不允许再次record

                    mRecordBtn.setImageResource(R.drawable.start_record);
                    mHelperDrawing.stopRecord();
                    savePNG();
                }
                else
                {
                    //开始record
                    mData.mRecordState = RecordState.PLAY;
                    mRecordBtn.setImageResource(R.drawable.stop_record);
                    mHelperDrawing.startRecord(painting.getRecordFolder());
                }
            }
        });

        mSavePartBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelperDrawing.coreView().recordPart(false);
            }
        });

        mUndoBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelperDrawing.undo();
            }
        });
        mRedoBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelperDrawing.redo();
            }
        });
        mHelperDrawing.getGraphView().setOnContentChangedListener(new OnContentChangedListener() {
            @Override
            public void onContentChanged(IGraphView view) {
                updateButtons();
            }
        });
        mHelperDrawing.startUndoRecord(painting.getUndoFolder());

        mSaveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//                mCustomDialog.show();
//                mHandler.sendEmptyMessageDelayed(WAIT_DIALOG_DISMISS, 2000);

                mHelperDrawing.saveToFile(painting.getSavedFile());
                savePNG();
            }
        });

        mShareBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void savePNG()
    {
        mHelperDrawing.beforeSavePNG();
        mHelperDrawing.savePNG(mHelperDrawing.extentSnapshot(4, true), painting.getSnapshot());
        mHelperDrawing.savePNG(IOHelper.getScaleBitmap(painting.getSnapshot()), painting.getThumbnail());
        mHelperDrawing.afterSavePNG();
    }

    private void initGraphicButtons()
    {
        mGraphicsLinearlayout = (LinearLayout)findViewById(R.id.linearlayout_graphics);
        mCircleBtn = (ImageButton)findViewById(R.id.btn_circle);
        mBgSelBtn = (ImageButton)findViewById(R.id.btn_bg);
        mSelectBtn = (ImageButton)findViewById(R.id.btn_select);


        mCircleBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelperDrawing.setLineColor(mData.mPenColor);
                mHelperDrawing.setLineAlpha(mData.mPenAlpha);
                mHelperDrawing.setStrokeWidth(mData.mPenWidth);
                mHelperDrawing.setCommand("circle2p");
            }
        });

        mBgSelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                launchChooser();
            }
        });

        mSelectBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelperDrawing.setCommand("select");
            }
        });


    }

    private void updateButtons()
    {
        mUndoBtn.setEnabled(mHelperDrawing.canUndo());
        mRedoBtn.setEnabled(mHelperDrawing.canRedo());
        mLineWidthSeekbar.setValue(mHelperDrawing.getStrokeWidth());
    }

    @Override
    public void onSelectionChanged(IGraphView view)
    {
        updateButtons();
    }

    @Override
    public void onDestroy()
    {

        super.onDestroy();
    }

    @Override
    public void onPause()
    {
        mHelperDrawing.onPause();
        super.onPause();
    }

    @Override
    public void onResume()
    {
        mHelperDrawing.onResume();
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mHelperDrawing.onSaveInstanceState(outState, painting.getRoot());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        mHelperDrawing.onRestoreInstanceState(savedInstanceState);
    }

    private void launchChooser()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK)
                {
                    final Uri imageUri = data.getData();
                    mHelperDrawing.insertImageFromFile(IOHelper.getFilePathFromContentUri(imageUri, getContentResolver()));
                }
                break;
        }
    }

    //颜色设置
    @Override
    public void colorChanged(int color) {

        switch (mData.mSelectType)
        {
            case Pen:
                mColorSelectBtn.setColorFilter(color);
                mData.mPenColor = color;
                mHelperDrawing.setLineColor(color);
                break;
            case PenBlend:
                mColorSelectBtn.setColorFilter(color);
                mData.mPenBlendColor = color;
                mData.mPendBlendAlpha = Color.alpha(color);
                mHelperDrawing.setLineColor(color);
                mHelperDrawing.setLineAlpha(Color.alpha(color));
                break;

            case Erase:
                break;

            case EraseBlend:
                mData.mEraseBlendColor = color;
                mData.mEraseBlendAlpha = Color.alpha(color);
                mHelperDrawing.setLineColor(color);
                mHelperDrawing.setLineAlpha(Color.alpha(color));
                break;
        }
    }

    @Override
    public boolean onPreGesture(int gestureType, float x, float y) {
        return false;
    }

    @Override
    public void onPostGesture(int gestureType, float x, float y) {

    }

    @Override
    public void onTouchDown() {
        updateBarVisiable();
    }

    @Override
    public void onSingleTap() {
        toggleBarVisiable();
    }

    private void beginThread(final boolean next)
    {
        final int duration = -100;  // 100ms
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                if (next) mHelperAnimation.coreView().nextPart(duration);
                else mHelperAnimation.coreView().prevPart(duration);
                Longs docs = new Longs();
                mHelperAnimation.coreView().acquireFrontDocs(docs);
                GiContext giContext = new GiContext();
                giContext.setLineAlpha(64);
                for (int i = 0; i < docs.count(); i++)
                {
                    MgShapeDoc doc = MgShapeDoc.fromHandle(docs.get(i));
                    if (doc == null) continue;
                    MgShapes shapes = doc.getCurrentShapes();
                    for (int j = 0; j < shapes.getShapeCount(); j++)
                    {
                        MgShape shape = shapes.getShapeAtIndex(j);
                        shape.setContext(giContext, GiContext.kLineAlpha);
                    }
                }
                ((BaseGraphView)mHelperAnimation.getGraphView()).viewAdapter().regenAll(true);
                Log.i("test", "isFirst=" + mHelperAnimation.coreView().isFirst(false) + ", isLast=" + mHelperAnimation.coreView().isLast(false));

                mHandler.sendEmptyMessage(PLAY_STEP_FINISH);
            }
        }).start();
    }


    //更新上一个  下一个按钮
    private void updateButtonWhenPlay()
    {
        mBottombarLinearlayout.setVisibility(View.GONE);
        mTopbarLinearlayout.setVisibility(View.GONE);
        mLeftBtn.setVisibility(View.GONE);
        mRightBtn.setVisibility(View.GONE);
        mGraphicsLinearlayout.setVisibility(View.GONE);
        mLineWidthSeekbar.setVisibility(View.GONE);
    }

    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == PLAY_STEP_FINISH)
            {
                mLeftBtn.setVisibility(mHelperAnimation.coreView().isFirst(false) ? View.GONE : View.VISIBLE);
                mRightBtn.setVisibility(mHelperAnimation.coreView().isLast(false) ? View.GONE : View.VISIBLE);
            }
            else if(msg.what == WAIT_DIALOG_DISMISS)
            {
                mCustomDialog.dismiss();
            }
        }
    };

    @Override
    public void onValueChanged(int value) {
        Log.i("test", " prog=" + value + ", penW=" + mData.mPenWidth);
        switch (mData.mSelectType)
        {
            case Pen:
                mData.mPenWidth = value;
                break;
            case PenBlend:
                mData.mPenBlendWidth = value;
                break;

            case Erase:
                mData.mEraseWidth = value;
                break;

            case EraseBlend:
                mData.mEraseBlendWidth = value;
                break;
        }
        mHelperDrawing.setStrokeWidth(value);
    }

    //touch down
    private void updateBarVisiable() {
        mData.mDisplayTools= false;
        if (mGraphicsLinearlayout.getVisibility() == View.VISIBLE)
            mGraphicsLinearlayout.setVisibility(View.GONE);

        if (mLineWidthSeekbar.getVisibility() == View.VISIBLE)
            mLineWidthSeekbar.setVisibility(View.GONE);

        if (mBottombarLinearlayout.getVisibility() == View.VISIBLE)
            mBottombarLinearlayout.setVisibility(View.GONE);

        if (mTopbarLinearlayout.getVisibility() == View.VISIBLE)
            mTopbarLinearlayout.setVisibility(View.GONE);
    }

    private void toggleBarVisiable()
    {
        mData.mDisplayTools = !mData.mDisplayTools;

        if(mData.mDisplayTools)
        {
            mBottombarLinearlayout.setVisibility(View.VISIBLE);
            mTopbarLinearlayout.setVisibility(View.VISIBLE);

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                if(Globals.mSelectType == com.energysh.drawshow.SelectType.TEACHER)
                {
                    mGraphicsLinearlayout.setVisibility(View.VISIBLE);
                }
                mLineWidthSeekbar.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            mBottombarLinearlayout.setVisibility(View.GONE);
            mTopbarLinearlayout.setVisibility(View.GONE);
            mGraphicsLinearlayout.setVisibility(View.GONE);
            mLineWidthSeekbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        mHelperDrawing.stopRecord();
        mHelperAnimation.stopRecord();

        mHelperDrawing.onDestroy();
        mHelperAnimation.onDestroy();
    }
}

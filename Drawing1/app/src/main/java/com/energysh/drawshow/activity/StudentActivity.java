package com.energysh.drawshow.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import com.energysh.drawshow.R;
import com.energysh.drawshow.dialog.colorpicker.ColorPickerDialog;
import com.energysh.drawshow.util.Predefine;
import rhcad.touchvg.IGraphView;
import rhcad.touchvg.IViewHelper;
import rhcad.touchvg.ViewFactory;
import rhcad.touchvg.core.*;
import rhcad.touchvg.view.BaseGraphView;
import rhcad.touchvg.view.internal.BaseViewAdapter;

public class StudentActivity extends Activity implements IGraphView.OnSelectionChangedListener
{
    private final int SELECT_PHOTO = 1;
    private IViewHelper mHelperDrawing = ViewFactory.createHelper();
    private IViewHelper mHelperAnimation = ViewFactory.createHelper();
    private static final String PATH = "mnt/sdcard/Drawing1/";
    private static final String RecordPath = PATH + "Record/";
    private SeekBar mLineWidthBar;
    /**
     * 不是第一次点击播放\下一步\上一步
     */
    private boolean notFirstClickPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        final ViewGroup layout = (ViewGroup) this.findViewById(R.id.container);
        mHelperDrawing.createSurfaceView(this, layout, savedInstanceState);
        mHelperDrawing.setCommand("splines");
        mHelperDrawing.setLineColor(Color.BLACK);
        View viewDrawing = mHelperDrawing.getView();
        viewDrawing.setBackgroundColor(Color.TRANSPARENT);
        // 初始化动画的SurfaceView
        mHelperAnimation.createSurfaceView(this, (ViewGroup) findViewById(R.id.animation_container), savedInstanceState);

        ColorPickerDialog.init(this);
        ColorPickerDialog.getInstance().addOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void colorChanged(int color) {
                mHelperDrawing.setLineColor(color);
                mHelperDrawing.setLineAlpha(Color.alpha(color));
            }
        });

        initButtons();
        initUndo();
        updateButtons();
    }

    private void initButtons()
    {
        findViewById(R.id.splines_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mHelperDrawing.setCommand("splines");
            }
        });
        mLineWidthBar = (SeekBar) findViewById(R.id.lineWidthBar);
        mLineWidthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mHelperDrawing.setStrokeWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHelperDrawing.setContextEditing(true);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHelperDrawing.setContextEditing(false);
            }
        });
        mHelperDrawing.getGraphView().setOnSelectionChangedListener(this);

        findViewById(R.id.colorpicker_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog.getInstance().show();
            }
        });

        findViewById(R.id.eraser).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mHelperDrawing.setLineAlpha(0xFFFFFFFF);
                mHelperDrawing.setLineColor(0xFFFFFFFF);
                mHelperDrawing.setCommand("splines");
            }
        });
        mLineWidthBar.setVisibility(View.GONE);
        findViewById(R.id.pen_width).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mLineWidthBar.setVisibility(mLineWidthBar.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mHelperDrawing.saveToFile(PATH + "final.vg");
            }
        });

        findViewById(R.id.button_next_part).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!notFirstClickPlay)
                {
                    mHelperAnimation.coreView().startPlay(RecordPath, BaseViewAdapter.getTick());
                    notFirstClickPlay = true;
                }
                beginThread(true);
            }
        });
        findViewById(R.id.button_prev_part).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!notFirstClickPlay)
                {
                    mHelperAnimation.coreView().startPlay(RecordPath, BaseViewAdapter.getTick());
                    notFirstClickPlay = true;
                }
                beginThread(false);
            }
        });
        final Button playAllButton = (Button) findViewById(R.id.button_play_all);
        playAllButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                playAllButton.setEnabled(false);
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mHelperAnimation.coreView().clear();
                        mHelperAnimation.coreView().startPlay(RecordPath, BaseViewAdapter.getTick());
                        mHelperAnimation.coreView().playAll(-100);
                        mHelperAnimation.coreView().stopPlay(false);
                        playAllButton.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                playAllButton.setEnabled(true);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private void initUndo()
    {
        findViewById(R.id.undo_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mHelperDrawing.undo();
            }
        });
        findViewById(R.id.redo_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mHelperDrawing.redo();
            }
        });
        mHelperDrawing.getGraphView().setOnContentChangedListener(new IGraphView.OnContentChangedListener()
        {
            @Override
            public void onContentChanged(IGraphView view)
            {
                updateButtons();
            }
        });
        mHelperDrawing.startUndoRecord(PATH + "undo");
    }

    private void updateButtons()
    {
        findViewById(R.id.undo_btn).setEnabled(mHelperDrawing.canUndo());
        findViewById(R.id.redo_btn).setEnabled(mHelperDrawing.canRedo());
        mLineWidthBar.setProgress(mHelperDrawing.getStrokeWidth());
    }

    @Override
    public void onSelectionChanged(IGraphView view)
    {
        updateButtons();
    }

    @Override
    public void onDestroy()
    {
        mHelperDrawing.onDestroy();
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
        mHelperDrawing.onSaveInstanceState(outState, PATH);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        mHelperDrawing.onRestoreInstanceState(savedInstanceState);
    }

    private void beginThread(final boolean next)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                if (next) mHelperAnimation.coreView().nextPart();
                else mHelperAnimation.coreView().prevPart();
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
            }
        }).start();
    }
}

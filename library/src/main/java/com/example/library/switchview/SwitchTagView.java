package com.example.library.switchview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.library.R;
import com.example.library.util.DensityUtil;


/**
 * Created by Niklaus on 2017/8/10.
 */

public class SwitchTagView extends LinearLayout {
    private static final int TYPE_BASKETBALL = 0;
    private static final int TYPE_FOOTBALL = 1;
    //动画停止
    private static final int STATUS_IDLE = 0;
    //正在执行显示动画
    private static final int STATUS_SHOWING = 1;
    //正在执行隐藏动画
    private static final int STATUS_HIDING = 2;

    private Context mContext;
    //选中的索引
    private int mSelectIndex;
    //默认宽高
    private int mDefaultHeight, mDefaultWidth;
    //左边标签name
    private String mLeftTagName;
    //右边标签name
    private String mRightTagName;
    //选中标签文本的颜色
    private int mSelectTextColor;
    //未选择标签文本的颜色
    private int mUnSelectTextColor;
    //选中标签背景资源
    private int mSelectTagBackgroundResource;
    //整个标签的背景
    private int mBackgroundResource;
    //是否正在执行动画
    private boolean mAniming;
    //动画的状态
    private int mAnimStatus = STATUS_IDLE;
    //动画插值器
    private AccelerateDecelerateInterpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private TextView mTvBasketball, mTvFootball;
    private OnSelectListener mOnSelectListener;

    public SwitchTagView(Context context) {
        this(context, null);
    }

    public SwitchTagView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchTagView);
            mLeftTagName = typedArray.getString(R.styleable.SwitchTagView_leftTagName);
            mRightTagName = typedArray.getString(R.styleable.SwitchTagView_rightTagName);
            mSelectTextColor = typedArray.getColor(R.styleable.SwitchTagView_selectTextColor, ContextCompat.getColor(context,R.color.yellow_fb0));
            mUnSelectTextColor = typedArray.getColor(R.styleable.SwitchTagView_selectTextColor, ContextCompat.getColor(context,R.color.black_9));
            mSelectTagBackgroundResource = typedArray.getInt(R.styleable.SwitchTagView_selectTagBackgroundResource, R.drawable.switch_tag_view_select_bg);
            mBackgroundResource = typedArray.getInt(R.styleable.SwitchTagView_selectTagBackgroundResource, R.drawable.switch_tag_view);
            typedArray.recycle();
        }
        setWillNotDraw(true);
        setBackgroundResource(mBackgroundResource);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);

        mDefaultWidth = DensityUtil.dip2px(context, 152);
        mDefaultHeight = DensityUtil.dip2px(context, 52);

        int pad2dp = DensityUtil.dip2px(context, 2);
        setPadding(pad2dp, pad2dp, pad2dp, pad2dp);

        removeAllViews();
        mTvBasketball = addView(mLeftTagName);
        mTvFootball = addView(mRightTagName);

        setSelect(mSelectIndex);
        initAnim();
        initListener();
    }

    /**
     * 添加文本
     * @param type
     * @return
     */
    private TextView addView(String type) {
        TextView textView = new TextView(mContext);
        textView.setText(type);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(DensityUtil.dip2px(mContext, 68), DensityUtil.dip2px(mContext, 32));
        addView(textView, params);
        return textView;
    }

    /**
     * 设置选中与未选中的样式
     * @param selectIndex
     */
    private void setSelect(int selectIndex) {
        if (selectIndex == TYPE_BASKETBALL) {
            mTvBasketball.setTextColor(mSelectTextColor);
            mTvBasketball.setBackgroundResource(mSelectTagBackgroundResource);
            mTvFootball.setTextColor(mUnSelectTextColor);
            mTvFootball.setBackgroundResource(android.R.color.transparent);
            mSelectIndex = TYPE_BASKETBALL;
        } else {
            mTvBasketball.setTextColor(mUnSelectTextColor);
            mTvBasketball.setBackgroundResource(android.R.color.transparent);
            mTvFootball.setTextColor(mSelectTextColor);
            mTvFootball.setBackgroundResource(mSelectTagBackgroundResource);
            mSelectIndex = TYPE_FOOTBALL;
        }
    }

    /**
     * 初始化Listener
     */
    private void initListener() {
        ViewClickListener listener = new ViewClickListener();
        mTvBasketball.setOnClickListener(listener);
        mTvFootball.setOnClickListener(listener);
    }

    /**
     * 外界调用设置监听
     * @param onSelectListener
     */
    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    /**
     * 外界调用选中索引
     * @param isBasketball
     */
    public void setSelect(boolean isBasketball) {
        setSelect(isBasketball ? TYPE_BASKETBALL : TYPE_FOOTBALL);
    }

    /**
     * 设置默认的宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = getExactlySize(widthMeasureSpec, mDefaultWidth);
        int heightSize = getExactlySize(heightMeasureSpec, mDefaultHeight);

        super.onMeasure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY)
                , MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY));
    }

    private int getExactlySize(int measureSpec, int defaultSize) {
        int newSize = MeasureSpec.getSize(measureSpec);
        if (MeasureSpec.getMode(measureSpec) != MeasureSpec.EXACTLY) {
            newSize = defaultSize;
        }
        return newSize;
    }

    /**
     * 设置动画
     */
    private void initAnim() {
        animate().setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mAniming = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAniming = false;
                mAnimStatus = STATUS_IDLE;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mAniming = false;
                mAnimStatus = STATUS_IDLE;
            }
        });
    }

    /**
     * 执行hide和show 动画
     * @param show
     */
    private void executeAnim(boolean show) {
        if ((show && mAnimStatus == STATUS_SHOWING) || (!show && mAnimStatus == STATUS_HIDING))
            return;

        if (mAniming) {
            animate().cancel();
        }
        animate()
                .setDuration(300)
                .setInterpolator(mInterpolator)
                .translationY(show ? 0 : DensityUtil.dip2px(mContext, 24 + 8) + getHeight())
                .start();
        mAnimStatus = show ? STATUS_SHOWING : STATUS_HIDING;
    }

    /**
     *
     */
    public void show() {
        executeAnim(true);
    }

    /**
     *
     */
    public void hide() {
        executeAnim(false);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable state = super.onSaveInstanceState();
        return new SaveState(state, mSelectIndex);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SaveState SaveState = (SaveState) state;
        super.onRestoreInstanceState(SaveState.getSuperState());
        if (mSelectIndex != SaveState.status) {
            setSelect(SaveState.status);
        }
        mSelectIndex = SaveState.status;
    }

    /**
     * 保存当前选中的索引，以便恢复操作
     */
    public static class SaveState extends BaseSavedState {
        private int status;

        public SaveState(Parcel source) {
            super(source);
            status = source.readInt();
        }

        public SaveState(Parcelable superState, int status) {
            super(superState);
            this.status = status;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(status);
        }

        public static final Creator<SaveState> CREATOR = new Creator<SaveState>() {
            @Override
            public SaveState createFromParcel(Parcel source) {
                return new SaveState(source);
            }

            @Override
            public SaveState[] newArray(int size) {
                return new SaveState[size];
            }
        };
    }

    public class ViewClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == mTvBasketball && mSelectIndex != TYPE_BASKETBALL) {
                setSelect(TYPE_BASKETBALL);
                if (mOnSelectListener != null) {
                    mOnSelectListener.setSelect(true);
                }
            } else if (v == mTvFootball && mSelectIndex != TYPE_FOOTBALL) {
                setSelect(TYPE_FOOTBALL);
                if (mOnSelectListener != null) {
                    mOnSelectListener.setSelect(false);
                }
            }
        }
    }

    public interface OnSelectListener {
        void setSelect(boolean isBasketball);
    }

}

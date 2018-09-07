package com.qdrs.sketchxu.wechat.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.qdrs.sketchxu.wechat.R;
import com.qdrs.sketchxu.wechat.WeChatApplication;
import com.qdrs.sketchxu.wechat.model.ACache;
import com.qdrs.sketchxu.wechat.model.ToastUtil;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageViewerActivity extends AppCompatActivity {
    private static final String TAG = "ImageViewerActivity";

    public static final int DURATION = 300;

    PhotoView imageView, bigImageView;
    private PicInfo mPicInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);

        adjustWindow();

        getData();

        setContentView(R.layout.activity_imageviewer);

        initView();
    }

    //全屏显示
    private void adjustWindow() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        WindowManager.LayoutParams attrs = getWindow().getAttributes();
//        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        getWindow().setAttributes(attrs);
    }

    @Override
    public void finish() {
        startExitAnimation();
    }

    private void getData() {
        Bundle data = getIntent().getExtras();
        mPicInfo = new PicInfo();
        mPicInfo.left = data.getInt("left");
        mPicInfo.top = data.getInt("top");
        mPicInfo.width = data.getInt("width");
        mPicInfo.height = data.getInt("height");
        mPicInfo.currentKey = data.getString("currentKey");
        mPicInfo.urlList = data.getStringArrayList("urlList");
        mPicInfo.currentIndex = mPicInfo.urlList.indexOf(mPicInfo.currentKey);

        Log.d(TAG, mPicInfo.toString());
    }

    private void initView() {
        imageView = findViewById(R.id.photoview);
        bigImageView = findViewById(R.id.photoview_large);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mPicInfo.width, mPicInfo.height);
        params.leftMargin = mPicInfo.left;
        params.topMargin = mPicInfo.top;

        imageView.setLayoutParams(params);
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                //| View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        /*View.SYSTEM_UI_FLAG_FULLSCREEN
        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                //| View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY*/
        imageView.setSystemUiVisibility(uiFlags);

        getWindow().getDecorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.alertMessage("click on decor");
                finish();
            }
        });
        getWindow().getDecorView().findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.alertMessage("click on decor");
                finish();
            }
        });
        imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                finish();
            }
        });

        showCurrentPic();
        startEnterAnimation();


    }

    private void startEnterAnimation() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        float scaleX = ((float)screenWidth) / mPicInfo.width;
        float scaleY = ((float)screenHeight) / mPicInfo.height;
        float scale = scaleX > scaleY ? scaleY : scaleX;

        float scaleWidth = scale * mPicInfo.width;
        float scaleHeight = scale * mPicInfo.height;
        float translateX = screenWidth / 2 - (mPicInfo.width / 2 + mPicInfo.left);
        float translateY = (screenHeight) / 2 - (mPicInfo.height / 2 + mPicInfo.top);

        Log.d(TAG, "scale:" + scale);
        Log.d(TAG, "scaleWidth:" + scaleWidth + " scaleHeight:" + scaleHeight);
        Log.d(TAG, "translateX:" + translateX + " translateY:" + translateY);

//        imageView.setX((screenWidth - scaleWidth) / 2);
//        imageView.setY((scaleHeight - scaleHeight) / 2);

        ObjectAnimator scaleXanimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1, scale);
        ObjectAnimator scaleYanimator = ObjectAnimator.ofFloat(imageView, "scaleY", 1, scale);
        ObjectAnimator translateXanimator = ObjectAnimator.ofFloat(imageView, "translationX", 0f, translateX);
        ObjectAnimator translateYanimator = ObjectAnimator.ofFloat(imageView, "translationY", 0f, translateY);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(findViewById(android.R.id.content), "alpha", 0, 1);

        //AnimationSet set = new AnimationSet(true);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(DURATION);
        set.playTogether( translateXanimator, translateYanimator, scaleXanimator, scaleYanimator/*, alphaAnimator*/);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
//                params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                params.leftMargin = 0;
//                params.topMargin = 0;
//                imageView.setLayoutParams(params);
                //imageView.setVisibility(View.GONE);
                //bigImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
//                params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                params.leftMargin = 0;
//                params.topMargin = 0;
//                imageView.setLayoutParams(params);
                //imageView.setVisibility(View.GONE);
                //bigImageView.setVisibility(View.VISIBLE);
            }
        });
        set.start();
    }

    public void startExitAnimation() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        float scaleX = ((float)screenWidth) / mPicInfo.width;
        float scaleY = ((float)screenHeight) / mPicInfo.height;
        float scale = scaleX > scaleY ? scaleY : scaleX;

        float scaleWidth = scale * mPicInfo.width;
        float scaleHeight = scale * mPicInfo.height;
        float translateX = screenWidth / 2 - (mPicInfo.width / 2 + mPicInfo.left);
        float translateY = (screenHeight) / 2 - (mPicInfo.height / 2 + mPicInfo.top);

        Log.d(TAG, "scale:" + scale);
        Log.d(TAG, "scaleWidth:" + scaleWidth + " scaleHeight:" + scaleHeight);
        Log.d(TAG, "translateX:" + translateX + " translateY:" + translateY);

//        imageView.setX((screenWidth - scaleWidth) / 2);
//        imageView.setY((scaleHeight - scaleHeight) / 2);

        ObjectAnimator scaleXanimator = ObjectAnimator.ofFloat(imageView, "scaleX", scale, 1);
        ObjectAnimator scaleYanimator = ObjectAnimator.ofFloat(imageView, "scaleY", scale, 1);
        ObjectAnimator translateXanimator = ObjectAnimator.ofFloat(imageView, "translationX", translateX, 0f);
        ObjectAnimator translateYanimator = ObjectAnimator.ofFloat(imageView, "translationY", translateY, -getStatusBarHeight(this)/4);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(findViewById(android.R.id.content), "alpha", 1, 0);

        //AnimationSet set = new AnimationSet(true);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(DURATION);
        set.playTogether( translateXanimator, translateYanimator, scaleXanimator, scaleYanimator/*, alphaAnimator*/);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);

                ImageViewerActivity.super.finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                ImageViewerActivity.super.finish();
                overridePendingTransition(0, 0);
            }
        });

        set.start();

    }

    private void showCurrentPic() {
        if (mPicInfo == null) {
            return;
        }

        Bitmap bitmap = ACache.get(WeChatApplication.getContext()).getAsBitmap(mPicInfo.currentKey);
        Log.d(TAG, "bitmap = " + bitmap);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            bigImageView.setImageBitmap(bitmap);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        try {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    private static final class PicInfo {
        int left;
        int top;
        int width;
        int height;
        String currentKey;
        int currentIndex;

        ArrayList<String> urlList;

        public String toString() {

            StringBuilder sb = new StringBuilder();
            sb.append("left:" + left)
                    .append("\n top:" + top)
                    .append("\n width:" + width)
                    .append("\n height:" + height)
                    .append("\n currentKey:" + currentKey)
                    .append("\n currentIndex:" + currentIndex)
                    .append("\n urlList:" + Arrays.toString(urlList.toArray()));

            return sb.toString();
        }
    }

}

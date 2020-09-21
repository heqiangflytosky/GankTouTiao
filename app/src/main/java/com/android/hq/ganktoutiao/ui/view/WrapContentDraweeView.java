package com.android.hq.ganktoutiao.ui.view;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

public class WrapContentDraweeView extends SimpleDraweeView {

    public WrapContentDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public WrapContentDraweeView(Context context) {
        super(context);
    }

    public WrapContentDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapContentDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WrapContentDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setImageURI(Uri uri, Object callerContext) {
        DraweeController controller = ((PipelineDraweeControllerBuilder) getControllerBuilder())
                .setControllerListener(listener)
                .setCallerContext(callerContext)
                .setUri(uri)
                .setOldController(getController())
                .build();
        setController(controller);
    }

    void updateViewSize(ImageInfo imageInfo) {
        if (imageInfo != null) {
            setAspectRatio((float) imageInfo.getWidth() / imageInfo.getHeight());
        }
    }

    private final ControllerListener listener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
            updateViewSize(imageInfo);
        }

        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            updateViewSize(imageInfo);
        }
    };
}

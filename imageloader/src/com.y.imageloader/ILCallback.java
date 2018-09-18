package com.y.imageloader;

import android.graphics.Bitmap;

public interface ILCallback {
    interface BitmapCallback{
        void next(Bitmap bitmap);
    }
    interface ProgressCallback{
        void start();
        void progress(double progress);
        void end();
    }
}

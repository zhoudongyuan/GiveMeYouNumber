package com.example.administrator.util;

import android.graphics.Bitmap;

/**
 * 切图后每一块图片的保存类
 * Created by Administrator on 2016/2/10.
 */

public class ImagePiece {
    private int index;
    private Bitmap bitmap;

    public ImagePiece(Bitmap bitmap, int index) {
        this.bitmap = bitmap;
        this.index = index;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ImagePiece() {

    }

    /*
    用于toSting的调试的作用
     */
    @Override
    public String toString() {
        return "ImagePiece{" +
                "bitmap=" + bitmap +
                ", index=" + index +
                '}';
    }
}

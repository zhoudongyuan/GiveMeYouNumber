package com.example.administrator.util;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * 切图片的工具类
 * <p/>
 * Created by Administrator on 2016/2/10.
 */
public class ImageSplitterUtil {
    /**
     * 传入bitmap，切成piece*piece块，返回List<ImagePiece>
     *
     * @param bitmap 传入一张bitmap的图片
     * @param piece  切成piece*piece块
     * @return List<ImagePiece>
     */
    public static List<ImagePiece> splitImage(Bitmap bitmap, int piece) {
        List<ImagePiece> imagePieces = new ArrayList<ImagePiece>();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        /**
         * 每一个块的宽度
         */
        int pieceWidth = Math.min(width, height) / piece;

        for (int i = 0; i < piece; i++)

        {
            for (int j = 0; j < piece; j++) {
                ImagePiece imagePiece = new ImagePiece();
                imagePiece.setIndex(j + i * piece);

                int x = j * pieceWidth;
                int y = i * pieceWidth;

                imagePiece.setBitmap(Bitmap.createBitmap(bitmap, x, y, pieceWidth, pieceWidth));

                imagePieces.add(imagePiece);
            }
        }

        return imagePieces;
    }

}

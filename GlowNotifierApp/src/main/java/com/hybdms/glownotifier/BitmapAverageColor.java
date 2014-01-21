/*
 * GlowNotifier Application for Android
 * Copyright (C) 2013 Youngbin Han<sukso96100@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.hybdms.glownotifier;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by youngbin on 14. 1. 11.
 */
public class BitmapAverageColor {
    public static int getAverageColorCodeRGB(Drawable image){

        //Get Bitmap Width and Height
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        int BitmapWidth = b.getWidth();
        int BitmapHeight = b.getHeight();
        int pixel;
        int pixelSumRed = 0;
        int pixelSumBlue = 0;
        int pixelSumGreen = 0;

        for (int i = 0; i < 100; i++) {
            for (int j = 70; j < 100; j++){
                //Get Pixels from bitmap
                pixel = b.getPixel((int) Math.round((i * BitmapWidth) / 100),
                                  (int) Math.round((j * BitmapHeight) / 100));

                //Get RGB code
                pixelSumRed += Color.red(pixel);
                pixelSumGreen += Color.green(pixel);
                pixelSumBlue += Color.blue(pixel);
            }
        }
        int averagePixelRed = pixelSumRed / 3000;
        int averagePixelBlue = pixelSumBlue / 3000;
        int averagePixelGreen = pixelSumGreen / 3000;

        int AverageColorCodeRGB = Color.rgb(averagePixelRed, averagePixelGreen, averagePixelBlue);

        return AverageColorCodeRGB;
    }

}

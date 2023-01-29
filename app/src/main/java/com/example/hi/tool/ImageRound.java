package com.example.hi.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

//将头像设置成圆图片，这段代码是我直接从网上复制的，说真的，我也没看懂写了个啥，总之就是把图片变成圆形展示而已
public class ImageRound extends androidx.appcompat.widget.AppCompatImageView{
    //圆形图片半径
    private int mRadius;

    public ImageRound(Context context) {
        super(context);
    }

    public ImageRound(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageRound(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //由于是圆形，宽高应保持一致
        int size = Math.min(getMeasuredWidth(),getMeasuredHeight());
        mRadius = size / 2;
        setMeasuredDimension(size,size);
    }
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        //画笔
        Paint mPaint = new Paint();
        Drawable drawable = getDrawable();
        if(drawable !=null){
            Bitmap bitmap = drawableToBitmap(getDrawable());
            //初始化BitmapShader，传入bitmap对象
            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
            //计算缩放比例
            //圆形的缩放比例
            float mScale = (mRadius * 2.0f) / Math.min(bitmap.getHeight(), bitmap.getWidth());
            Matrix matrix = new Matrix();
            matrix.setScale(mScale, mScale);
            bitmapShader.setLocalMatrix(matrix);
            mPaint.setShader(bitmapShader);

            //画圆形，指定好坐标，半径，画笔
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        }else{
            super.onDraw(canvas);
        }
    }
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}

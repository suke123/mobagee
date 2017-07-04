package jtp.c.dendai.ac.jp.action_sample;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Ground2 {
    private final Paint paint = new Paint();

    final Rect rect;

    public Ground2(int left, int top, int right, int bottom) {
        this.rect = new Rect(left, top, right, bottom);

        paint.setColor(Color.rgb(196, 180, 164)); // 茶色
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }

    public void move(int moveToLeft) {
        rect.offset(-moveToLeft, 0);
    }

    public boolean isShown(int width, int height) {
        return rect.intersects(0, 0, width, height);
    }

    public boolean isAvailable() {
        return rect.right > 0;
    }

    public boolean isSolid() {
        return true;
    }

}

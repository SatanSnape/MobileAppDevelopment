package com.example.lab_6;

import android.content.Context;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
    }

    class DrawView extends View {

        Paint p;
        RectF rectf;
        float[] points;
        float[] points1;

        public DrawView(Context context) {
            super(context);
            p = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawRGB(0, 0, 0);

            p.setColor(Color.WHITE);
            p.setAntiAlias(true);
            p.setStyle(Paint.Style.STROKE);

            Path path = new Path();

            float mid = getWidth() / 2;
            float min = Math.min(getWidth(), getHeight());
            float fat = min / 17;
            float half = min / 2;
            float rad = half - fat;
            mid = mid - half;

            p.setStrokeWidth(fat);
            p.setColor(Color.RED);
            p.setStyle(Paint.Style.STROKE);

            canvas.drawCircle(mid + half, half, rad, p);

            path.reset();

            p.setStyle(Paint.Style.FILL);


            // bottom left
            path.moveTo(mid + half * 0.5f, half * 1.10f);
            // bottom right
            path.lineTo(mid + half * 1.5f, half * 1.10f);
            // top left
            path.lineTo(mid + half * 0.68f, half * 0.5f);
            // bottom tip
            path.lineTo(mid + half * 1.0f, half * 1.45f);
            // top right
            path.lineTo(mid + half * 1.32f, half * 0.5f);
            // bottom left
            path.lineTo(mid + half * 0.5f, half * 1.10f);

            // bottom left
            path.moveTo(mid + half * 0.5f, half * 1.10f);
            // top left
            path.lineTo(mid + half * 0.68f, half * 0.5f);
            // top right
            path.lineTo(mid + half * 1.32f, half * 0.5f);
            // bottom right
            path.lineTo(mid + half * 1.5f, half * 1.10f);
            // bottom tip
            path.lineTo(mid + half * 1.0f, half * 1.45f);
            // bottom left
            path.lineTo(mid + half * 0.5f, half * 1.10f);


            path.close();
            canvas.drawPath(path, p);

            super.onDraw(canvas);

        }
    }
}
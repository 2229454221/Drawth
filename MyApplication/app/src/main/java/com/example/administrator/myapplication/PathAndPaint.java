package com.example.administrator.myapplication;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class PathAndPaint {
    private Paint paint;
    private Path path;

    public PathAndPaint(Path path,Paint paint) {
        this.paint = paint;
        this.path = path;
    }
    public Paint getPaint() {
        return paint;
    }
    public Path getPath() {
        return path;
    }
    public void setPaint(Paint paint) {
        this.paint = paint;
    }
    public void setPath(Path path) {
        this.path = path;
    }
}

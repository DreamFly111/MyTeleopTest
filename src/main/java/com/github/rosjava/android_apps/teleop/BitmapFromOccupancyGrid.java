package com.github.rosjava.android_apps.teleop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.jboss.netty.buffer.ChannelBuffer;
import org.ros.android.MessageCallable;

import nav_msgs.OccupancyGrid;

/**
 * Created by Dream on 2016/3/28.
 */
public class BitmapFromOccupancyGrid implements MessageCallable<Bitmap, nav_msgs.OccupancyGrid> {
    public BitmapFromOccupancyGrid() {
    }

    public Bitmap call(nav_msgs.OccupancyGrid message) {
        ChannelBuffer buffer = message.getData();
        byte[] data = buffer.array();
        return BitmapFactory.decodeByteArray(data, buffer.arrayOffset(), buffer.readableBytes());
    }
}

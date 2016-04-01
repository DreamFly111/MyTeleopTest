package com.github.rosjava.android_apps.teleop;

import org.apache.commons.logging.Log;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import nav_msgs.OccupancyGrid;

/**
 * Created by Dream on 2016/3/22.
 */
public class Listerner extends AbstractNodeMain {
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("android_apps_teleop/listerner");//命名规则要符合ROS
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        final Log log = connectedNode.getLog();//输出log信息
        Subscriber<OccupancyGrid> subscriber = connectedNode.newSubscriber("map", OccupancyGrid._TYPE);
        subscriber.addMessageListener(new MessageListener<OccupancyGrid>() {
            @Override
            public void onNewMessage(OccupancyGrid occupancyGrid) {
                log.info(occupancyGrid);
            }
        });
    }


}

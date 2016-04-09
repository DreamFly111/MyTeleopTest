package com.github.rosjava.android_apps.teleop;

import android.content.Context;
import android.util.AttributeSet;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.topic.Publisher;

import java.util.Timer;
import java.util.TimerTask;

import geometry_msgs.Twist;

/**
 * Created by Dream on 2016/4/4.
 */
public class OnOffNode extends AbstractNodeMain {

    private boolean holonomic;
    private volatile boolean publishVelocity;
    private Timer publisherTimer;
    private Twist currentVelocityCommand;
    private String topicName;
    private Publisher<Twist> publisher;
    private volatile float currentOrientation;

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("android_apps_teleop/onoff");
    }

    public OnOffNode(Context context) {
        super();
        this.initOnOff(context);
        this.topicName = "~on_off";
    }

    public OnOffNode(Context context, AttributeSet attrs) {
        super();
        this.initOnOff(context);
        this.topicName = "~on_off";
    }

    public OnOffNode(Context context, AttributeSet attrs, int defStyle) {
        super();
        this.topicName = "~on_off";
    }

    private void initOnOff(Context context) {
    }

    public void setHolonomic(boolean enabled) {
        this.holonomic = enabled;
    }

   /* private void publishVelocity(double linearVelocityX, double linearVelocityY, double angularVelocityZ) {
        this.currentVelocityCommand.getLinear().setX(linearVelocityX);
        this.currentVelocityCommand.getLinear().setY(-linearVelocityY);
        this.currentVelocityCommand.getLinear().setZ(0.0D);
        this.currentVelocityCommand.getAngular().setX(0.0D);
        this.currentVelocityCommand.getAngular().setY(0.0D);
        this.currentVelocityCommand.getAngular().setZ(-angularVelocityZ);
    }*/

    @Override
    public void onStart(ConnectedNode connectedNode) {
        this.publisher = connectedNode.newPublisher(this.topicName, "geometry_msgs/Twist");
        this.currentVelocityCommand = (Twist)this.publisher.newMessage();
       /* Subscriber subscriber = connectedNode.newSubscriber("odom", "nav_msgs/Odometry");
        subscriber.addMessageListener(this);*/
        this.publisherTimer = new Timer();
        this.publisherTimer.schedule(new TimerTask() {
            public void run() {
                if(OnOffNode.this.publishVelocity) {
                    OnOffNode.this.publisher.publish(OnOffNode.this.currentVelocityCommand);
                }

            }
        }, 0L, 80L);
    }

    @Override
    public void onError(Node node, Throwable throwable) {

    }

    @Override
    public void onShutdownComplete(Node node) {
        this.publisherTimer.cancel();
        this.publisherTimer.purge();
    }

    @Override
    public void onShutdown(Node node) {

    }
}

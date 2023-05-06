package com.dx;

import com.dx.executor.MqttV3Executor;
import com.dx.mqttclient.ConnectionPara;
import com.dx.mqttclient.SubscribePara;
import org.apache.log4j.BasicConfigurator;

/**
 * @author 32825
 */
public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        ConnectionPara cp = new ConnectionPara();
        MqttV3Executor me = new MqttV3Executor(cp, true, false);
        me.connect();
        SubscribePara sp=new SubscribePara();
        me.subscribeTopic(sp);
    }
}
/**
 * 
 */
package com.james.zookeeper.zkclient.loadbalanceother.algorithm;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.I0Itec.zkclient.ZkClient;

import com.james.zookeeper.zkclient.loadbalanceother.Constant;

/**
 * @ClassName: RoundRobinLoadBalance
 * @Description: TODO(describe what to do this class)
 * @author James.zhang
 * @date 2015年8月13日 下午6:48:02
 *
 */
public class RoundRobinLoadBalance implements LoadBlance {

    @Override
    public String select(String zkServer) {
        ZkClient zkClient = new ZkClient(zkServer);
        List<String> serverList = zkClient.getChildren(Constant.root);
        int round=0;
        if(!zkClient.exists(Constant.round)){
            zkClient.createPersistent(Constant.round);
            zkClient.writeData(Constant.round, 0);
        }else{
            round=(Integer)zkClient.readData(Constant.round);
            zkClient.writeData(Constant.round, ++round);
        }
        zkClient.close();
        if (serverList != null && serverList.size() > 0) {
            return serverList.get(round % serverList.size());
        } else {
            return null;
        }

    }

}

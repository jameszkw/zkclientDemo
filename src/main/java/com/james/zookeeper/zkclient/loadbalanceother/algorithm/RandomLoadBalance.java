/**
 * 
 */
package com.james.zookeeper.zkclient.loadbalanceother.algorithm;

import java.util.List;
import java.util.Random;

import org.I0Itec.zkclient.ZkClient;

import com.james.zookeeper.zkclient.loadbalanceother.Constant;

/**
 * @ClassName: RandomLoadBalance
 * @Description: TODO(describe what to do this class)
 * @author James.zhang
 * @date 2015年8月13日 下午6:47:13
 *
 */
public class RandomLoadBalance implements LoadBlance {

    @Override
    public String select(String zkServer) {
        ZkClient zkClient = new ZkClient(zkServer);
        List<String> serverList = zkClient.getChildren(Constant.root);
        zkClient.close();
        Random r=new Random();
        if(serverList.size()>=1){
            String server=serverList.get(r.nextInt(serverList.size()));
            return server;
        }else{
            return null;
        }

    }

}

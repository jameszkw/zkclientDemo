/**
 * 
 */
package com.james.zookeeper.zkclient.loadbalanceother.algorithm;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;

import com.james.zookeeper.zkclient.loadbalanceother.Constant;

/**
 * @ClassName: LeastActiveLoadBalance
 * @Description: TODO(describe what to do this class)
 * @author James.zhang
 * @date 2015年8月13日 下午6:44:48
 *
 */
public class LeastActiveLoadBalance implements LoadBlance {

    @Override
    public String select(String zkServer) {
        ZkClient zkClient = new ZkClient(zkServer);
        List<String> serverList = zkClient.getChildren(Constant.root);

        String tempServer = null;
        int tempConn = -1;
        for (int i = 0; i < serverList.size(); i++) {
            String server = serverList.get(i);
            if (zkClient.readData(Constant.root + "/" + server) != null) {
                int connNum = zkClient.readData(Constant.root + "/" + server);
                if (tempConn == -1) {
                    tempServer = server;
                    tempConn = connNum;
                }
                if (connNum < tempConn) {
                    tempServer = server;
                    tempConn = connNum;
                }
            }else{
                zkClient.close();
                return server;
            }
        }
        zkClient.close();
        if (tempServer != null && !tempServer.equals("")) {
            return tempServer;
        }

        return null;
    }

}

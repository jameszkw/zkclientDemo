/**
 * 
 */
package com.james.zookeeper.zkclient.loadbalanceother;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @ClassName: ClusterClient
 * @Description: TODO(describe what to do this class)
 * @author James.zhang
 * @date 2015年8月13日 下午6:40:12
 *
 */
public abstract class ClusterClient {
    public abstract void connect(ZkClient zkClient);
    public abstract String getAPPServer();
    public void setZkClient(ZkClient zkClient){
        this.zkClient=zkClient;
    }
    private ZkClient zkClient;

    public void failOver() {
        zkClient.subscribeChildChanges(Constant.root, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List currentChilds) throws Exception {
                boolean has = false;
                for (int i = 0; i < currentChilds.size(); i++) {
                    if (getAPPServer().equals(currentChilds.get(i))) {
                        has = true;
                        break;
                    }
                }
                if (!has) {
                    connect(zkClient);
                }
            }
        });
    }

    public void join(String client){
        if(!zkClient.exists(Constant.client)){
            zkClient.createPersistent(Constant.client);
        }
        if(!zkClient.exists(Constant.client+"/"+client)){
            zkClient.createEphemeral(Constant.client+"/"+client);
        }
    }

    public void leave(String client){
        if(zkClient.exists(Constant.client+"/"+client)){
            zkClient.delete(Constant.client+"/"+client);
        }
        zkClient.close();
    }
}

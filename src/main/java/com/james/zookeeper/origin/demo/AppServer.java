/**
 * 
 */
package com.james.zookeeper.origin.demo;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * @ClassName: AppServer
 * @Description: TODO(describe what to do this class)
 * @author James.zhang
 * @date 2015年8月16日 下午3:46:50
 *
 */
public class AppServer {
    private String groupNode = "root";
    private String subNode = "sub";

    /**
     * 连接zookeeper
     * @param address server的地址
     */
    public void connectZookeeper(String address) throws Exception {
        ZooKeeper zk = new ZooKeeper("10.0.1.44:2181", 5000, new Watcher() {
            public void process(WatchedEvent event) {
                // 不做处理
            }
        });
//        zk.exists("/root", true);
//        String createdRoot = zk.create("/root", "data".getBytes("utf-8"), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        System.out.println("create: " + createdRoot);
        // 在"/sgroup"下创建子节点
        // 子节点的类型设置为EPHEMERAL_SEQUENTIAL, 表明这是一个临时节点, 且在子节点的名称后面加上一串数字后缀
        // 将server的地址数据关联到新创建的子节点上
        String createdPath = zk.create("/" + groupNode + "/" + subNode, address.getBytes("utf-8"),
            Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("create: " + createdPath);
    }

    /**
     * server的工作逻辑写在这个方法中
     * 此处不做任何处理, 只让server sleep
     */
    public void handle() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        // 在参数中指定server的地址
    	String adress = "111";
        AppServer as = new AppServer();
        as.connectZookeeper(adress);

        as.handle();
    }
}

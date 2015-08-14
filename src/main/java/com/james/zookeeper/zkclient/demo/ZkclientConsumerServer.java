/**
 * 
 */
package com.james.zookeeper.zkclient.demo;

import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @ClassName: ZkclientConsumerServer
 * @Description: 服务消费者获取服务提供者地址列表
 * @author James.zhang
 * @date 2015年8月13日 下午4:20:10
 *
 */
public class ZkclientConsumerServer {
	String serviceName = "service1";
	String zkServerList = "10.0.1.44:2181";
	String SERVICE_PATH = "/node"+"/"+serviceName;//服务节点路径
	ZkClient zkClient = new ZkClient(zkServerList);
	List<String> serviceList = new ArrayList<String>();
	private void demo(){
		boolean serviceExists = zkClient.exists(SERVICE_PATH);
		if(serviceExists){//服务存在，取地址列表
			serviceList = zkClient.getChildren(SERVICE_PATH);
			System.out.println(serviceList.toString());
		} else {
			throw new RuntimeException("service not exists");
		}
		//注册事件监听
		zkClient.subscribeChildChanges(SERVICE_PATH, new IZkChildListener() {
		//选取其中一个
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds)
					throws Exception {
				// TODO Auto-generated method stub
				serviceList = currentChilds;
				System.out.println(serviceList.toString());
			}
		});
	}
	public static void main(String[] args) {
		ZkclientConsumerServer consumer = new ZkclientConsumerServer();
		consumer.demo();
	}
}

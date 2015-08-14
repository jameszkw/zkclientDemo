package com.james.zookeeper.zkclient.demo;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class ZkclientDemo {
	String serverList = "";
	String PATH = "";
	ZkClient zkClient = new ZkClient(serverList);
	
	//订阅子节点变化
	private void subscribeChildrenStateChangeDemo(){
		zkClient.subscribeChildChanges(PATH, new IZkChildListener() {
			
			@Override
			public void handleChildChange(String arg0, List<String> arg1)
					throws Exception {
				// TODO Auto-generated method stub
				
			}
		});
	}
	//订阅节点数据的变化
	private void subscribeDataChangesDemo(){
		zkClient.subscribeDataChanges(PATH, new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String arg0) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void handleDataChange(String arg0, Object arg1) throws Exception {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	//订阅节点连接及状态的变化
	private void subscribeNodeConnectStataDemo(){
		zkClient.subscribeStateChanges(new IZkStateListener() {
			
			@Override
			public void handleStateChanged(KeeperState arg0) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void handleSessionEstablishmentError(Throwable arg0)
					throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void handleNewSession() throws Exception {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void demo(){
		//创建节点
		zkClient.createPersistent(PATH);
		//创建子节点
		zkClient.create(PATH+"/child", "child znode", CreateMode.EPHEMERAL);
		//获得子节点
		List<String> children = zkClient.getChildren(PATH);
		//获得子节点个数
		int childCount = zkClient.countChildren(PATH);
		//判断节点是否存在
		zkClient.exists(PATH);
		//写入数据
		zkClient.writeData(PATH+"/child", "hello everyone");
		//读取节点数据
		Object obj = zkClient.readData(PATH+"/child");
		//删除节点
		zkClient.delete(PATH+"/child");
		
	}
	public static void main(String[] args) {
		
	}
}

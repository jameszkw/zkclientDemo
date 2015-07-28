package com.james.zookeeper.zkclient.demo;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class ZkclientDemo {
	public static void main(String[] args) {
		String serverList = "";
		ZkClient zkClient = new ZkClient(serverList);
		//创建节点
		String PATH = "";
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
}

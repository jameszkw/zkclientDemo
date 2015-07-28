package com.james.zookeeper.zkclient.demo;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class ZkclientDemo {
	public static void main(String[] args) {
		String serverList = "";
		ZkClient zkClient = new ZkClient(serverList);
		//�����ڵ�
		String PATH = "";
		zkClient.createPersistent(PATH);
		//�����ӽڵ�
		zkClient.create(PATH+"/child", "child znode", CreateMode.EPHEMERAL);
		//����ӽڵ�
		List<String> children = zkClient.getChildren(PATH);
		//����ӽڵ����
		int childCount = zkClient.countChildren(PATH);
		//�жϽڵ��Ƿ����
		zkClient.exists(PATH);
		//д������
		zkClient.writeData(PATH+"/child", "hello everyone");
		//��ȡ�ڵ�����
		Object obj = zkClient.readData(PATH+"/child");
		//ɾ���ڵ�
		zkClient.delete(PATH+"/child");
		
	}
}

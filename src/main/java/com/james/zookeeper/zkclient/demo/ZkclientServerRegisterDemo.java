/**
 * 
 */
package com.james.zookeeper.zkclient.demo;

import java.net.InetAddress;

import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;

/**
 * @ClassName: ZkclientRegisterDemo
 * @Description: 服务提供者向集群注册服务的实现
 * @author James.zhang
 * @date 2015年8月13日 下午3:57:36
 *
 */
public class ZkclientServerRegisterDemo implements Runnable{
	final static Logger logger = Logger.getLogger(ZkclientServerRegisterDemo.class);
	String serverList = "10.0.1.44:2181";
	String serviceName = "service1";
	ZkClient zkClient = new ZkClient(serverList);
	private void demo() throws Exception{
		logger.info("register");
		String PATH = "/node";//根节点路径
		boolean rootExists = zkClient.exists(PATH);
		if(!rootExists){
			zkClient.createPersistent(PATH);
		}
		boolean serverExists =zkClient.exists(PATH+"/"+serviceName);
		if(!serverExists){
			zkClient.createPersistent(PATH+"/"+serviceName);//创建服务节点
		}
		//注册当前服务器，可以在节点的数据里面存放节点的权重
		InetAddress addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress().toString();//获得本机ip
		zkClient.delete(PATH+"/"+serviceName+"/"+ip);
		//创建当前服务器节点
		zkClient.createEphemeral(PATH+"/"+serviceName+"/"+ip);
		System.out.println("regist server1 child ip");
		handle();
	}
	
	/**
     * this is very importent 
     */
    public void handle() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }
    
    @Override
    public void run() {
    	try {
			demo();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public static void main(String[] args) {
		ZkclientServerRegisterDemo demo = new ZkclientServerRegisterDemo();
		new Thread(demo).start();
	}
}

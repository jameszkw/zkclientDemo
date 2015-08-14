/**
 * 
 */
package com.james.zookeeper.zkclient.loadbalanceother;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import com.james.zookeeper.zkclient.loadbalanceother.algorithm.RoundRobinLoadBalance;

/**
 * @ClassName: Client
 * @Description: TODO(describe what to do this class)
 * @author James.zhang
 * @date 2015年8月13日 下午6:41:10
 *
 */
public class Client extends ClusterClient {
    private static String appServer;
    private static String zkServer = "127.0.0.1:2181";
    private static ClientBootstrap bootstrap;
    private static Client client;

    public static void main(String[] args) throws Exception {

        ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        bootstrap = new ClientBootstrap(factory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("encode", new StringEncoder());
                pipeline.addLast("decode", new StringDecoder());
                pipeline.addLast("handler", new DemoHandler());
                return pipeline;
            }
        });
        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("keepAlive", true);
        client=new Client();
        ZkClient zkClient = new ZkClient(zkServer);
        client.connect(zkClient);

        client.failOver();
    }

    @Override
    public void connect(ZkClient zkClient) {
        while (true) {
            try {
                RoundRobinLoadBalance loadBlance = new RoundRobinLoadBalance();
                //loadBlance.SetClient("127.0.0.1:"+new Random().nextInt(1000));
                String server = loadBlance.select(zkServer);
                if (server != null) {
                    String ip = server.split(":")[0];
                    int port = Integer.parseInt(server.split(":")[1]);
                    appServer = server;
                    System.out.println(server);
                    bootstrap.connect(new InetSocketAddress(ip, port));

                    client.setZkClient(zkClient);
                    client.join( "127.0.0.1:"+new Random().nextInt(5000));
                    ZookeeperConnStatistic.incrementConn(zkServer, appServer);
                    break;
                }
                Thread.sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {

                }
                connect(zkClient);
            }
        }

    }

	/* (non-Javadoc)
	 * @see com.james.zookeeper.zkclient.loadbalance.ClusterClient#getAPPServer()
	 */
	@Override
	public String getAPPServer() {
		// TODO Auto-generated method stub
		return null;
	}
}

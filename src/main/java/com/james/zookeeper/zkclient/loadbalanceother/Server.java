/**
 * 
 */
package com.james.zookeeper.zkclient.loadbalanceother;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * @ClassName: Server
 * @Description: TODO(describe what to do this class)
 * @author James.zhang
 * @date 2015年8月13日 下午6:48:33
 *
 */
public class Server {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ChannelFactory factory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());
            ServerBootstrap bootstrap = new ServerBootstrap (factory);
            bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
                public ChannelPipeline getPipeline() {
                     ChannelPipeline pipeline = Channels.pipeline();
                    pipeline.addLast("encode",new StringEncoder());
                    pipeline.addLast("decode",new StringDecoder());
                    pipeline.addLast("handler",new DemoHandler());
                    return pipeline;
                }
            });
            bootstrap.setOption("child.tcpNoDelay", true);
            bootstrap.setOption("child.keepAlive", true);
            bootstrap.bind(new InetSocketAddress(8081));

//            ClusterServer.join("127.0.0.1:8081", "127.0.0.1:2181");
    }

}

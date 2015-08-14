/**
 * 
 */
package com.james.zookeeper.zkclient.loadbalanceother;

import java.util.Date;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * @ClassName: DemoHandler
 * @Description: TODO(describe what to do this class)
 * @author James.zhang
 * @date 2015年8月13日 下午6:44:11
 *
 */
public class DemoHandler extends SimpleChannelUpstreamHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Thread.sleep(5000);
        System.out.println(e.getMessage());
        ctx.getChannel().write("bbb");
    }
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        e.getChannel().write("abcd");
    }


}

package org.sewatech.examples.network.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.sewatech.examples.network.Server;
import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;

/**
 * @author alexis
 */
public class EchoNettyServer implements Server {

    private final ConnectionlessBootstrap bootstrap;
    private final ChannelFactory factory;

    public EchoNettyServer() {
        factory = new NioDatagramChannelFactory(Executors.newCachedThreadPool());

        bootstrap = new ConnectionlessBootstrap(factory);

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() {
                return Channels.pipeline(new EchoServerHandler());
            }
        });
        bootstrap.bind(new InetSocketAddress(EchoClient.PORT));
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void close() throws Exception {
        factory.releaseExternalResources(); 
    }

    private static class EchoServerHandler extends SimpleChannelHandler {

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent event) throws IOException {
            ChannelBuffer buffer = (ChannelBuffer) event.getMessage();
            Channel channel = event.getChannel();
            channel.connect(event.getRemoteAddress());
            StringBuffer textReceived = new StringBuffer();
            while (buffer.readable()) {
                textReceived.append((char) buffer.readByte());
            }
            System.out.print("Received : " + textReceived);
            System.out.println(" From : " + channel.getRemoteAddress());
                
            buffer.clear();
            buffer.writeBytes(textReceived.toString().getBytes());
            channel.write(buffer);
            channel.disconnect();
        }
    }
}

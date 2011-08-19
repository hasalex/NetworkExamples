package org.sewatech.examples.network.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import org.sewatech.examples.network.Server;

/**
 *
 * @author alexis
 */
public class EchoNioServer implements Server {

    private final static int BUFFER_SIZE = 1024;

    private final DatagramChannel channel;
    
    public EchoNioServer() throws IOException {
        channel = DatagramChannel.open();
        DatagramSocket socket = channel.socket();
        SocketAddress address = new InetSocketAddress(EchoClient.PORT);
        socket.bind(address);
    }

    @Override
    public void start() throws Exception {
        ByteBuffer buffer;
        
        while (true) {
            // new buffer to avoid clear problems
            buffer = ByteBuffer.allocate(BUFFER_SIZE);
            SocketAddress client = channel.receive(buffer);
            buffer.flip();
            System.out.print("Data recieved : " + new String(buffer.array()));
            System.out.println(" From : " + client);
            channel.send(buffer, client);
        }
    }

    @Override
    public void close() throws Exception {
        channel.close();
    }
}

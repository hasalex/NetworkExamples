package org.sewatech.examples.network.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import org.sewatech.examples.network.Server;

/**
 * @author alexis
 */
public class EchoSimpleServer implements Server {

    private final DatagramSocket socket;

    public EchoSimpleServer() throws SocketException {
        socket = new DatagramSocket(EchoClient.PORT);
    }

    @Override
    public void start() throws Exception {
        while (true) {
            byte buffer[] = new byte[256];
            DatagramPacket data = new DatagramPacket(buffer, buffer.length);
            socket.receive(data);
            System.out.print("Data recieved : " + new String(data.getData()));
            System.out.println(" From : " + data.getAddress() + ":" + data.getPort());
            socket.send(data);
        }
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }
}

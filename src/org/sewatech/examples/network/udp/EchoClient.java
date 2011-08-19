package org.sewatech.examples.network.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author alexis
 */
public class EchoClient {
    final static int PORT = 8532;

    public static void main(String argv[]) throws Exception {
        InetAddress server = InetAddress.getByName(argv[0]);
        int length = argv[1].length();
        byte buffer[] = argv[1].getBytes();
        DatagramPacket dataSent = new DatagramPacket(buffer, length, server, PORT);
        DatagramSocket socket = new DatagramSocket();

        socket.send(dataSent);

        DatagramPacket dataRecieved = new DatagramPacket(new byte[length], length);
        socket.receive(dataRecieved);
        System.out.print("Data recieved : " + new String(dataRecieved.getData()));
        System.out.println(" From : " + dataRecieved.getAddress() + ":" + dataRecieved.getPort());
    }
}

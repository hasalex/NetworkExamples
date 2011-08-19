package org.sewatech.examples.network.tcp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author alexis
 */
public class EchoClient {

    final static int PORT = 8531;

    public static void main(String arg[]) throws Exception {
        InetAddress serverAdress = InetAddress.getByName(arg[0]);

        Socket socket = new Socket(serverAdress, PORT);
        OutputStream output = socket.getOutputStream();
        String text = arg[1];
        output.write(text.getBytes());
        output.flush();
        
        InputStream input = socket.getInputStream();
        byte[] buffer = new byte[1024];
        input.read(buffer);
        
        System.out.print("Data recieved : " + new String(buffer));
        System.out.println(" From : " + socket.getRemoteSocketAddress());
        socket.close();
    }
}

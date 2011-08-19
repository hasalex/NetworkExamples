package org.sewatech.examples.network.tcp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.sewatech.examples.network.Server;

/**
 * @author alexis
 */
public class EchoSimpleServer implements Server {

    private static final int BUFFER_SiZE = 8;
    private final ServerSocket server;

    public EchoSimpleServer() throws Exception {
        server = new ServerSocket(EchoClient.PORT);
    }

    @Override
    public void start() throws Exception {
        byte buffer[] = new byte[BUFFER_SiZE];
        int size;
        try (Socket socket = server.accept()) {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            while ((size = input.read(buffer)) != -1) {
                System.out.print(new String(buffer));
                output.write(buffer, 0, size);
            }
            output.flush();
            System.out.println();
        }
    }

    @Override
    public void close() throws Exception {
        server.close();
    }
}

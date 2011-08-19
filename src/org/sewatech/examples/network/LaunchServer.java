package org.sewatech.examples.network;

/**
 * @author alexis
 */
public class LaunchServer {

    public static void main(String... arg) throws Exception {
        String className = arg[0];

        Class<Server> clazz = (Class<Server>) Class.forName(className);
        try (Server server = clazz.newInstance()) {
            while (true) {
                server.start();
            }
        }
    }
}

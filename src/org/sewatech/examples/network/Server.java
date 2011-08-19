package org.sewatech.examples.network;

/**
 * @author alexis
 */
public interface Server extends AutoCloseable{
    void start() throws Exception;    
}

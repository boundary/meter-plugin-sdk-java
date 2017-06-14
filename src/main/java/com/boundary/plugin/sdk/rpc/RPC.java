package com.boundary.plugin.sdk.rpc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Santosh Patil
 * @Date 16-05-2017
 */
public class RPC {

    private static final org.slf4j.Logger LOG = LoggerFactory
            .getLogger(RPC.class);
    final static String HOSTNAME = "localhost";
    final static int PORTNUMBER = 9192;

    public static void send(final String content) {
        Socket socket = null;
        DataOutputStream dataOutputStream = null;
        try {
            socket = new Socket(HOSTNAME, PORTNUMBER);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeBytes(content);
            dataOutputStream.flush();
        } catch (IOException ex) {
            LOG.error("Exception occured while sending content to meter", ex);
        } finally {
            try {
                dataOutputStream.close();
                socket.close();
            } catch (IOException ex) {
                LOG.error("Exception occured while closing the socket output stream", ex);
            }
        }
    }
}

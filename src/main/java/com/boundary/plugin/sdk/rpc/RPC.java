package com.boundary.plugin.sdk.rpc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.LoggerFactory;

/**
 *
 * @author Santosh Patil,vitiwari
 * @Date 16-05-2017
 */
public class RPC {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(RPC.class);

    final static String HOSTNAME = "localhost";
    final static int PORTNUMBER = 9192;
    private static int connectionCount = 0;
    private static RPC rpc = new RPC();
    private Socket socket;
    private DataOutputStream dataOutputStream;

    private RPC() {
        super();
    }

    public static RPC getInstance() {
        if (rpc == null) {
            rpc = new RPC();
        }
        return rpc;
    }

    public synchronized boolean openConnection() {
        if (this.socket == null) {
            try {
                this.socket = new Socket(HOSTNAME, PORTNUMBER);
                this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
                connectionCount++;
                //socket.setKeepAlive(true);
                return true;
            } catch (UnknownHostException e) {
                LOG.error("Unable to open socket connection to host", e);
            } catch (IOException e) {
                LOG.error("Unable to open Socket Connection", e);
            }
        } else if (this.socket.isConnected()) {
            connectionCount++;
            return true;
        }
        return false;
    }

    public synchronized void send(final String content) {
        try {
            if (socket != null) {
                dataOutputStream.writeBytes(content);
                dataOutputStream.flush();
            } else {
                LOG.error("Unable to write the events, Socket connection is not open");
            }
        } catch (IOException ex) {
            LOG.error("Exception occured while sending content to meter", ex);
        }
    }

    public synchronized int sendList(final List<String> contentList) {
        int totalRecordsWritten = 0;
        try {
            if (socket != null && dataOutputStream != null) {
                for (String content : contentList) {
                    dataOutputStream.writeBytes(content);
                    totalRecordsWritten++;
                }
                dataOutputStream.flush();
            } else {
                LOG.error("Unable to write the events, Socket connection is not open");
            }
        } catch (IOException ex) {
            LOG.error("Exception occured while sending content to meter", ex);
        }
        return totalRecordsWritten;
    }

    public synchronized boolean closeConnection() {

        try {
            connectionCount--;
            if (connectionCount <= 0) {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                    dataOutputStream = null;
                }
                if (socket != null) {
                    socket.close();
                    socket = null;
                }
            }
            return true;
        } catch (IOException e) {
            LOG.error("Unable to close Socket Connection", e);
        }

        return false;
    }

}

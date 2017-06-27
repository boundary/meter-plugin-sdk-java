package com.boundary.plugin.sdk.rpc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.LoggerFactory;

/**
 *
 * @author Santosh Patil
 * @Date 16-05-2017
 */
public class RPC {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(RPC.class);

    final static String HOSTNAME = "localhost";
    final static int PORTNUMBER = 9192;
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

    public boolean openConnection() {
        if (this.socket == null) {
            try {
                this.socket = new Socket(HOSTNAME, PORTNUMBER);
                this.dataOutputStream = new DataOutputStream(socket.getOutputStream());;
                //socket.setKeepAlive(true);
                return true;
            } catch (UnknownHostException e) {
                LOG.error("Unable to open socket connection to host", e);
            } catch (IOException e) {
                LOG.error("Unable to open Socket Connection", e);
            }
        }
        return false;
    }

    public void send(final String content) {
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

    public int sendList(final List<String> contentList) {
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

    public boolean closeConnection() {

        try {
            if (socket != null) {
                socket.close();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            return true;
        } catch (IOException e) {
            LOG.error("Unable to close Socket Connection", e);
        }

        return false;
    }

}

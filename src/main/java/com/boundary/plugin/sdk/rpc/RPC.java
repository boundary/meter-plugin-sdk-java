package com.boundary.plugin.sdk.rpc;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

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
    private InputStream inStream;

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
                this.inStream = this.socket.getInputStream();
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

    public synchronized String send(final String contentRpcJson) {
        String result = null;
        try {
            if (socket != null) {
                BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                bos.write(contentRpcJson.getBytes("UTF-8"));
                bos.flush();
                result = convertStreamToString(this.inStream);
            } else {
                LOG.error("Unable to write the events, Socket connection is not open");
            }
        } catch (IOException ex) {
            LOG.error("Exception occured while sending content to meter", ex);
        }
        return result;
    }

    public synchronized boolean closeConnection() {

        try {
            connectionCount--;
            if (connectionCount <= 0) {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                    dataOutputStream = null;
                    inStream.close();
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

    private String convertStreamToString(InputStream instream) {
        int ch = 0;
        StringBuilder type = new StringBuilder();
        int count = 0;
        boolean isReadingComplete = false;
        while (!isReadingComplete) {
            try {
                ch = instream.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // NOTE: conversion from byte to char here works for ISO8859-1/US-ASCII
            // but fails for UTF etc.
            type.append((char) ch);
            switch ((char) ch) {
                case '{':
                    count++;
                    break;
                case '[':
                    count++;
                    break;
                case '}':
                    count--;
                    break;
                case ']':
                    count--;
                    break;
            }
            if (count == 0) {
                isReadingComplete = true;
                break;
            }
        }
        String data = type.toString();
        return data;
    }

}

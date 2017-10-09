package com.boundary.plugin.sdk.rpc;

import java.io.BufferedOutputStream;
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
    private BufferedOutputStream bufferedOutputStream;
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
    	LOG.debug("Open connection called ...");
        if (this.socket == null) {
        	LOG.debug("socket instance is null creating a socket connection");
            try {
                this.socket = new Socket(HOSTNAME, PORTNUMBER);
                LOG.debug("Socket Connection successfully created and assigned ");
                this.bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
                LOG.debug("Socket output stream created and assigned");
                this.inStream = this.socket.getInputStream();
                LOG.debug("Socket input Stream created and assigned");
                connectionCount++;
                LOG.debug("Connection count is {}",connectionCount);
                //socket.setKeepAlive(true);
                return true;
            } catch (UnknownHostException e) {
                LOG.error("Unable to open socket connection to host", e);
            } catch (IOException e) {
                LOG.error("Unable to open Socket Connection", e);
            }
        } else{
        	LOG.debug("Socket instance is not null, checking if the socket is still connected ...");
        	if (this.socket.isConnected()) {
	            connectionCount++;
	            LOG.debug("Socket is still connected, increasing connection count to {} ",connectionCount);
	            return true;
        	}
        }
        return false;
    }

    public synchronized String send(final String contentRpcJson) {
    	
    	LOG.debug("Send called for events list , {} ...",contentRpcJson);
        String result = null;
        try {
            if (socket != null) {
            	LOG.debug("writing the events to output stream ..");
            	byte[] utf8JsonString = contentRpcJson.getBytes("UTF-8");
            	bufferedOutputStream.write(utf8JsonString, 0, utf8JsonString.length);
                LOG.debug("Events written to output stream.");
                bufferedOutputStream.flush();
                LOG.debug("Output stream flushed, waiting for the input stream response .....");
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
        	LOG.debug("Closing the connection , total {} connections are open",connectionCount);
            connectionCount--;
            if (connectionCount <= 0) {
            	 LOG.debug(" total {} connections are open, its time to close the stream and sockets",connectionCount);
                if (bufferedOutputStream != null) {
                	bufferedOutputStream.close();
                	bufferedOutputStream = null;
                    inStream.close();
                    LOG.debug("Streams closed");
                }
                if (socket != null) {
                    socket.close();
                    socket = null;
                    LOG.debug("Socket closed");
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
        LOG.debug("Recieved Response --> {}",type);
        return data;
    }

}

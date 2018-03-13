// Copyright 2015 BMC Software, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.boundary.plugin.sdk;

import java.io.IOException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.plugin.sdk.rpc.RPC;

public class EventSinkAPI implements EventSink {

    private static Logger LOG = LoggerFactory.getLogger(EventSinkAPI.class);

    private final EventFormatter formatter;
    private RPC rpc;

    public EventSinkAPI() {
        formatter = new EventFormatter();
        rpc = RPC.getInstance();
    }

    @Override
    public void emit(Event event) {
        try {
            rpc.send(formatter.format(event));
        } catch (IOException e) {
            LOG.error("IOException : Event could not be sent, " + e.getMessage());
        }
    }

    @Override
    public String emit(String eventRpcJson) throws IOException {
        return rpc.send(eventRpcJson);

    }

    @Override
    public int openConnection() throws UnknownHostException, IOException {
        return rpc.openConnection();
    }

    @Override
    public int closeConnection() throws IOException {
        return rpc.closeConnection();
    }
}

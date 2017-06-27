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

import java.util.List;

import com.boundary.plugin.sdk.rpc.RPC;

public class EventSinkAPI implements EventSink {

    private final EventFormatter formatter;
    //private RPC rpc;

    public EventSinkAPI() {
        formatter = new EventFormatter();
    }

    @Override
    public void emit(Event event) {
    	RPC.send(formatter.format(event));
    }

    @Override
    public void emit(String event) {
    	RPC.send(event);
    }
    
    @Override
    public int emit(List<String> eventList) {
    	return RPC.sendList(eventList);
    }
}

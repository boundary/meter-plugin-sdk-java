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

public class EventSinkStandardOutput implements EventSink {

    private EventFormatter formatter;

    public EventSinkStandardOutput() {
        formatter = new EventFormatter();
    }

    @Override
    public void emit(Event event) {
        System.out.println(formatter.format(event));
    }

    @Override
    public String emit(final String eventRpcJson) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean openConnection() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean closeConnection() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

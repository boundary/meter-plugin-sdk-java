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
// Copyright 2014 Boundary, Inc.

package com.boundary.plugin.sdk;

import com.boundary.plugin.sdk.Event.EventSeverity;
import com.boundary.plugin.sdk.Event;

import java.util.List;
import java.util.ArrayList;

public class EventBuilder {

    private EventSeverity severity;
    private String title;
    private String message;
    private String host;
    private String source;
    private List<String> tags;

    public EventBuilder() {
        this.severity = EventSeverity.INFO;
        this.tags = new ArrayList<String>();
    }

    public Event build() {
        final Event event = new Event(severity, title, message, host, source, tags);
        return event; 
    }

    public EventBuilder setSeverity(EventSeverity severity) {
        this.severity = severity;
        return this;
    }

    public EventBuilder setTitle(final String title) {
        this.title = title;
        return this;
    }

    public EventBuilder setMessage(final String message) {
        this.message = message;
        return this;
    }

    public EventBuilder setHost(final String host) {
        this.host = host;
        return this;
    }

    public EventBuilder setSource(final String source) {
        this.source = source;
        return this;
    }

    public EventBuilder addTag(final String tag) {
        this.tags.add(tag);
        return this;
    }

}


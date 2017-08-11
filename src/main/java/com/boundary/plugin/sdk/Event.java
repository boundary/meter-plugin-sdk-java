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
import java.util.ArrayList;

/*
 * Encapsulates an Event that can be sent to the platform.
 */
public class Event {

    public enum EventSeverity {
        INFO, WARN, ERROR, CRITICAL;
    }

    private String title;
    private String message;
    private EventSeverity severity;
    private List<String> tags;
    private String host;
    private String source;

    public Event(final String title, final String message) {
        this.severity = EventSeverity.INFO;
        this.title = "";
        this.message = "";
        this.tags = new ArrayList<String>();
    }

    public Event(final EventSeverity severity, final String title, final String message, final String host, final String source, final List<String> tags) {
        this.severity = severity;
        this.title = title;
        this.message = message;
        this.host = host;
        this.source = source;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public EventSeverity getSeverity() {
        return severity;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getHost() {
        return host;
    }

    public String getSource() {
        return source;
    }

    public boolean hasTags() {
        return tags != null && tags.size() > 0;
    }
}

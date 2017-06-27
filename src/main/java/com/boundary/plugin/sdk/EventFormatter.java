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

import java.lang.StringBuffer;
import java.util.List;
import java.util.ArrayList;

import com.boundary.plugin.sdk.Event;
import com.boundary.plugin.sdk.Event.EventSeverity;

public class EventFormatter {

    public String formatTags(List<String> tags) {
        final StringBuffer sb = new StringBuffer();
        for (String tag : tags) {
            sb.append(tag);
            sb.append(',');
        } 
        String output = sb.toString();
        return output.substring(0, output.length()-1);
    }

    public String formatSeverity(EventSeverity severity) {
        switch(severity) {
            case INFO:
                return "info";
            case WARN:
                return "warn";
            case CRITICAL:
                return "critical";
            case ERROR:
                return "error";
        }

        return "";
    }

    private boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
    
    public String format(Event event) {

        final StringBuffer sb = new StringBuffer("_bevent:");

        if (!isNullOrEmpty(event.getTitle())) {
            sb.append(event.getTitle());
        }
        
        if (!isNullOrEmpty(event.getMessage())) {
            sb.append("|m:");
            sb.append(event.getMessage());
        }

        sb.append("|t:");
        sb.append(formatSeverity(event.getSeverity()));

        if (!isNullOrEmpty(event.getHost())) {
            sb.append("|h:");
            sb.append(event.getHost());
        }

        if (!isNullOrEmpty(event.getSource())) {
            sb.append("|s:");
            sb.append(event.getSource());
        }

        if (event.hasTags()) {
            sb.append("|tags:");
            sb.append(formatTags(event.getTags()));
        }

        return sb.toString();
    }

    public String rpcFormat(Event event) {

        final StringBuffer sb = new StringBuffer("{\"jsonrpc\":\"2.0\",\"method\":\"event\",\"params\":{\"data\":\"_bevent:");

        if (!isNullOrEmpty(event.getTitle())) {
            sb.append(event.getTitle());
        }

        if (!isNullOrEmpty(event.getMessage())) {
            sb.append("|m:");
            sb.append(event.getMessage());
        }

        sb.append("|t:");
        sb.append(formatSeverity(event.getSeverity()));

        if (!isNullOrEmpty(event.getHost())) {
            sb.append("|h:");
            sb.append(event.getHost());
        }

        if (!isNullOrEmpty(event.getSource())) {
            sb.append("|s:");
            sb.append(event.getSource());
        }

        if (event.hasTags()) {
            sb.append("|tags:");
            sb.append(formatTags(event.getTags()));
        }
        sb.append("\"}}");
        return sb.toString();
    }
}

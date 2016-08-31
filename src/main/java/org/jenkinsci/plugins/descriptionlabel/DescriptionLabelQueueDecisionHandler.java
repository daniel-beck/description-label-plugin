/*
 The MIT License (MIT)

Copyright (c) 2016, CloudBees, Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.jenkinsci.plugins.descriptionlabel;

import hudson.Extension;
import hudson.model.AbstractItem;
import hudson.model.Action;
import hudson.model.Label;
import hudson.model.Queue;
import hudson.model.labels.LabelAssignmentAction;
import hudson.model.queue.SubTask;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Locale;

@Extension
public class DescriptionLabelQueueDecisionHandler extends Queue.QueueDecisionHandler {
    @Override
    public boolean shouldSchedule(Queue.Task p, List<Action> actions) {
        if (p instanceof AbstractItem) {
            AbstractItem item = (AbstractItem) p;
            String description = item.getDescription();
            if (description.toLowerCase(Locale.US).contains("linux")) {
                actions.add(new DescriptionLabelAssignmentAction("linux"));
            }
        }
        return true;
    }

    public static class DescriptionLabelAssignmentAction implements LabelAssignmentAction {

        private Label label;

        private DescriptionLabelAssignmentAction(String labelName) {
            this.label = Label.get(labelName);
        }

        @Override
        public Label getAssignedLabel(@Nonnull SubTask task) {
            return label;
        }

        @Override
        public String getIconFileName() {
            return null;
        }

        @Override
        public String getDisplayName() {
            return null;
        }

        @Override
        public String getUrlName() {
            return null;
        }
    }
}

package org.graphwalker.core.statistics;

/*
 * #%L
 * GraphWalker Core
 * %%
 * Copyright (C) 2005 - 2014 GraphWalker
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import org.graphwalker.core.machine.Context;
import org.graphwalker.core.machine.Machine;
import org.graphwalker.core.model.Element;
import org.graphwalker.core.model.Path;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @author Nils Olsson
 */
public final class Profiler {

    private final Profile profile = new Profile();
    private long startTime = 0;

    public void start(Context context) {
        startTime = System.nanoTime();
    }

    public void stop(Context context) {
        Element element = context.getCurrentElement();
        if (null != element) {
            profile.addExecution(element, new Execution(startTime, System.nanoTime() - startTime));
        }
    }

    public boolean isVisited(Element element) {
        return profile.containsKey(element);
    }

    public long getTotalVisitCount() {
        return profile.getTotalExecutionCount();
    }

    public long getVisitCount(Element element) {
        return profile.getTotalExecutionCount(element);
    }

    public List<Element> getUnvisitedElements(Context context) {
        List<Element> elementList = new ArrayList<>();
        for (Element e : context.getModel().getElements()) {
            if (!isVisited(e)) {
                elementList.add(e);
            }
        }
        return elementList;
    }

    public Path<Element> getPath() {
        return profile.getPath();
    }

    public Profile getProfile() {
        return profile;
    }
}

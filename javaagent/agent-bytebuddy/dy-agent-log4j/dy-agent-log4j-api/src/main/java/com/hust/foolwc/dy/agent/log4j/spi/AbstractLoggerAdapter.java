/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package com.hust.foolwc.dy.agent.log4j.spi;

import com.hust.foolwc.dy.agent.log4j.LogManager;
import com.hust.foolwc.dy.agent.log4j.util.LoaderUtil;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.hust.foolwc.dy.agent.log4j.LogManager;

/**
 * Provides an abstract base class to use for implementing LoggerAdapter.
 * 
 * @param <L> the Logger class to adapt
 * @since 2.1
 */
public abstract class AbstractLoggerAdapter<L> implements LoggerAdapter<L> {

    /**
     * A map to store loggers for their given LoggerContexts.
     */
    protected final Map<LoggerContext, ConcurrentMap<String, L>> registry = new WeakHashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock (true);

    @Override
    public L getLogger(final String name) {
        final LoggerContext context = getContext();
        final ConcurrentMap<String, L> loggers = getLoggersInContext(context);
        final L logger = loggers.get(name);
        if (logger != null) {
            return logger;
        }
        loggers.putIfAbsent(name, newLogger(name, context));
        return loggers.get(name);
    }

    /**
     * Gets or creates the ConcurrentMap of named loggers for a given LoggerContext.
     *
     * @param context the LoggerContext to get loggers for
     * @return the map of loggers for the given LoggerContext
     */
    public ConcurrentMap<String, L> getLoggersInContext(final LoggerContext context) {
        ConcurrentMap<String, L> loggers;
        lock.readLock ().lock ();
        try {
            loggers = registry.get (context);
        } finally {
            lock.readLock ().unlock ();
        }

        if (loggers != null) {
            return loggers;
        } else {
            lock.writeLock ().lock ();
            try {
                loggers = registry.get (context);
                if (loggers == null) {
                    loggers = new ConcurrentHashMap<> ();
                    registry.put (context, loggers);
                }
                return loggers;
            } finally {
                lock.writeLock ().unlock ();
            }
        }
    }

    /**
     * Creates a new named logger for a given {@link LoggerContext}.
     *
     * @param name the name of the logger to create
     * @param context the LoggerContext this logger will be associated with
     * @return the new named logger
     */
    protected abstract L newLogger(final String name, final LoggerContext context);

    /**
     * Gets the {@link LoggerContext} that should be used to look up or create loggers. This is similar in spirit to the
     * {@code ContextSelector} class in {@code log4j-core}. However, implementations can rely on their own framework's
     * separation of contexts instead (or simply use a singleton).
     *
     * @return the LoggerContext to be used for lookup and creation purposes
     * @see LogManager#getContext(ClassLoader, boolean)
     * @see LogManager#getContext(String, boolean)
     */
    protected abstract LoggerContext getContext();

    /**
     * Gets the {@link LoggerContext} associated with the given caller class.
     *
     * @param callerClass the caller class
     * @return the LoggerContext for the calling class
     */
    protected LoggerContext getContext(final Class<?> callerClass) {
        ClassLoader cl = null;
        if (callerClass != null) {
            cl = callerClass.getClassLoader();
        }
        if (cl == null) {
            cl = LoaderUtil.getThreadContextClassLoader();
        }
        return LogManager.getContext(cl, false);
    }

    @Override
    public void close() {
        lock.writeLock ().lock ();
        try {
            registry.clear();
        } finally {
            lock.writeLock ().unlock ();
        }
    }
}
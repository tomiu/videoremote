package com.eo.videoremote.utils;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    public static String tag = "foo";

    private static int logLevel = Log.VERBOSE;

    public static void verbose(String message) {
        if (logLevel <= Log.VERBOSE) {
            //            Log.v(tag, message);
            Log.v(formatTag(), formatTrace(message)); // format trace by default - for testing
        }
    }

    public static void verbose(String tag, String message) {
        if (logLevel <= Log.VERBOSE) {
            //            Log.v(tag, message);
            Log.v(tag, formatTrace(message)); // format trace by default - for testing
        }
    }

    /**
     * Output method trace.<li>
     * Format:
     * <ul>
     * [(<b>classFileName</b>:<b>classLineNumber</b>): <b>classMethodName</b>()]: <b>message</b>
     * </ul>
     * </li>
     * 
     * @param message
     */
    public static void verboseT(String message) {
        if (logLevel <= Log.VERBOSE) {
            Log.v(formatTag(), formatTrace(message));
        }
    }

    /**
     * Output the entire stacktrace.
     * 
     * @param message
     */
    public static void verboseST(String message) {
        if (logLevel <= Log.VERBOSE) {
            Log.v(formatTag(), message, new Throwable());
        }
    }

    public static void verboseST(String message, int depth) {
        if (logLevel <= Log.VERBOSE) {
            Log.v(formatTag(), message, formatStackTrace(depth));
        }
    }

    public static void debug(String message) {
        if (logLevel <= Log.DEBUG) {
            //            Log.v(tag, message);
            Log.d(formatTag(), formatTrace(message)); // format trace by default - for testing
        }
    }

    public static void debug(String tag, String message) {
        if (logLevel <= Log.DEBUG) {
            //            Log.v(tag, message);
            Log.d(tag, formatTrace(message)); // format trace by default - for testing
        }
    }

    public static void debug(String tag, String message, Throwable e) {
        if (logLevel <= Log.DEBUG) {
            //            Log.v(tag, message);
            Log.d(tag, formatTrace(message), e); // format trace by default - for testing
        }
    }

    public static void debugToFile(String message, String filename) {
        if (logLevel <= Log.DEBUG) {
            //            Log.v(tag, message);
            Log.d(formatTag(), formatTrace(message)); // format trace by default - for testing

            File logFile = new File("sdcard/" + filename);
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                //BufferedWriter for performance, true to set append to file flag
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(message);
                buf.newLine();
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteLogFile(String filename) {
        File logFile = new File("sdcard/" + filename);
        if (logFile.exists()) {

            logFile.delete();

        }
    }

    /**
     * Output method trace.<li>
     * Format:
     * <ul>
     * [(<b>classFileName</b>:<b>classLineNumber</b>): <b>classMethodName</b>()]: <b>message</b>
     * </ul>
     * </li>
     * 
     * @param message
     */
    public static void debugT(String message) {
        if (logLevel <= Log.DEBUG) {
            Log.d(formatTag(), formatTrace(message));
        }
    }

    /**
     * Output the entire stacktrace.
     * 
     * @param message
     */
    public static void debugST(String message) {
        if (logLevel <= Log.DEBUG) {
            Log.d(formatTag(), message, new Throwable());
        }
    }

    public static void debugST(String message, int depth) {
        if (logLevel <= Log.DEBUG) {
            Log.d(formatTag(), message, formatStackTrace(depth));
        }
    }

    public static void info(String message) {
        if (logLevel <= Log.INFO) {
            //            Log.i(tag, message);
            Log.i(formatTag(), formatTrace(message)); // format trace by default - for testing
        }
    }

    public static void info(String tag, String message) {
        if (logLevel <= Log.INFO) {
            //            Log.i(tag, message);
            Log.i(tag, formatTrace(message)); // format trace by default - for testing
        }
    }

    /**
     * Output method trace.<li>
     * Format:
     * <ul>
     * [(<b>classFileName</b>:<b>classLineNumber</b>): <b>classMethodName</b>()]: <b>message</b>
     * </ul>
     * </li>
     * 
     * @param message
     */
    public static void infoT(String message) {
        if (logLevel <= Log.INFO) {
            Log.i(formatTag(), formatTrace(message));
        }
    }

    /**
     * Output the entire stacktrace.
     * 
     * @param message
     */
    public static void infoST(String message) {
        if (logLevel <= Log.INFO) {
            Log.i(formatTag(), message, new Throwable());
        }
    }

    public static void warning(String message) {
        if (logLevel <= Log.WARN) {
            //            Log.w(tag, message);
            Log.w(formatTag(), formatTrace(message)); // format trace by default - for testing
        }
    }

    public static void warning(String tag, String message) {
        if (logLevel <= Log.WARN) {
            //            Log.w(tag, message);
            Log.w(tag, formatTrace(message)); // format trace by default - for testing
        }
    }

    /**
     * Output method trace.<li>
     * Format:
     * <ul>
     * [(<b>classFileName</b>:<b>classLineNumber</b>): <b>classMethodName</b>()]: <b>message</b>
     * </ul>
     * </li>
     * 
     * @param message
     */
    public static void warningT(String message) {
        if (logLevel <= Log.WARN) {
            Log.w(formatTag(), formatTrace(message));
        }
    }

    /**
     * Output the entire stacktrace.
     * 
     * @param message
     */
    public static void warningST(String message) {
        if (logLevel <= Log.WARN) {
            Log.w(formatTag(), message, new Throwable());
        }
    }

    public static void error(String message) {
        if (logLevel <= Log.ERROR) {
            //            Log.e(tag, message);
            Log.e(formatTag(), formatTrace(message)); // format trace by default - for testing
        }
    }

    public static void error(String tag, String message) {
        if (logLevel <= Log.ERROR) {
            //            Log.e(tag, message);
            Log.e(tag, formatTrace(message)); // format trace by default - for testing
        }
    }

    public static void error(String tag, String message, Throwable e) {
        if (logLevel <= Log.ERROR) {
            //            Log.e(tag, message);
            Log.e(tag, formatTrace(message), e); // format trace by default - for testing
        }
    }

    /**
     * Output method trace.<li>
     * Format:
     * <ul>
     * [(<b>classFileName</b>:<b>classLineNumber</b>): <b>classMethodName</b>()]: <b>message</b>
     * </ul>
     * </li>
     * 
     * @param message
     */
    public static void errorT(String message) {
        if (logLevel <= Log.ERROR) {
            Log.e(formatTag(), formatTrace(message));
        }
    }

    /**
     * Output the entire stacktrace.
     * 
     * @param message
     */
    public static void errorST(String message) {
        if (logLevel <= Log.ERROR) {
            Log.e(formatTag(), message, new Throwable());
        }
    }

    /**
     * Output the given stacktrace.
     * 
     * @param message
     */
    public static void errorST(String message, Throwable throwable) {
        if (logLevel <= Log.ERROR) {
            Log.e(formatTag(), message, throwable);
        }
    }

    private static String formatTrace(String message) {
        return formatTrace(message, 3);
    }

    private static String formatTrace(String message, int level) {
        Throwable throwable = new Throwable();
        StackTraceElement traceElement = throwable.getStackTrace()[Math
                .min(level, throwable.getStackTrace().length - 1)]; // to prevent crashing when inlined by Proguard
        return "[(" + traceElement.getFileName() + ":" + traceElement.getLineNumber() + "): "
                + traceElement.getMethodName() + "()]: " + message;
    }

    private static String formatTag() {
        return formatTag(tag);
    }

    private static String formatTag(String tag) {
        return tag + ": " + Thread.currentThread().getName();
    }

    //    private static String formatStackTrace(int depth, String message) {
    //        StackTraceElement[] traceElement = new Throwable().getStackTrace();
    //        int offset = 1;
    //        int lenght = Math.min(depth + offset, traceElement.length);
    //
    //        StringBuffer stringBuffer = new StringBuffer(message);
    //        for (int i = offset; i < traceElement.length; i++) {
    //            stringBuffer.append(traceElement[i].getClassName());
    //        }
    //
    //        return stringBuffer.toString();
    //    }

    private static Throwable formatStackTrace(int depth) {
        Throwable throwable = new Throwable();
        StackTraceElement[] traceElements = throwable.getStackTrace();

        int offset = 2;
        int lenght = Math.min(depth, traceElements.length - offset);
        StackTraceElement[] stackTraceElements = new StackTraceElement[lenght];

        for (int i = 0; i < lenght; i++) {
            stackTraceElements[i] = traceElements[i + offset];
        }
        throwable.setStackTrace(stackTraceElements);

        return throwable;
    }

    public static void setGlobalLogLevelToVerbose() {
        logLevel = Log.VERBOSE;
    }

    public static void setGlobalLogLevelToDebug() {
        logLevel = Log.DEBUG;
    }

    public static void setGlobalLogLevelToInfo() {
        logLevel = Log.INFO;
    }

    public static void setGlobalLogLevelToWarning() {
        logLevel = Log.WARN;
    }

    public static void setGlobalLogLevelToError() {
        logLevel = Log.ERROR;
    }

    public static void setGlobalLogLevelToAssert() {
        logLevel = Log.ASSERT;
    }

    public static void disableLogging() {
        logLevel = Log.ASSERT + 1;
    }
}

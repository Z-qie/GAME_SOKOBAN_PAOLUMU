package org.ziqi;

/**
 * Debugger class is used as in-line debugging tool following the Debugging Guideline 4 introduced in lecture: Use Trace and Test Flags.
 *
 * @author Ziqi Yang
 */
public class Debugger {
    /**
     * This is a flag specifying if the debugging mode is to turn on and track down all in-line debuggers called from source code.
     */
    private static final boolean m_Trace = false;

    /**
     * This method is used as the beginning of the in-line debugger.
     * A message will be printed to standard output with <br/>
     * 1. the name of debugger caller class and method, <br/>
     * 2. a detail message, <br/>
     * 3. a flag word "BEGIN".
     *
     * @param test        When m_Trace flag is false, this parameter will specify if the debugger will be turned on.
     * @param debugString This is the string expected from caller as the detail message of debugger.
     */
    public static void debugBegin(boolean test, String debugString) {
        String debugDetail = ": " + debugString + " BEGIN...";

        if (test || m_Trace)
            System.out.println(getCallerInfo() + debugDetail);
    }

    /**
     * This method is used as the ending of the in-line debugger.
     * A message will be printed to standard output with <br/>
     * 1. the name of debugger caller class and method, <br/>
     * 2. a detail message, <br/>
     * 3. a flag word "END".
     *
     * @param test        When m_Trace flag is false, this parameter will specify if the debugger will be turned on.
     * @param debugString This is the string expected from caller as the detail message of debugger.
     */
    public static void debugEnd(boolean test, String debugString) {
        String debugDetail = ": " + debugString + " END";

        if (test || m_Trace)
            System.out.println(getCallerInfo() + debugDetail);
    }

    /**
     * This method is used to obtain the name of caller class and caller method of debugger and reformat the string.
     *
     * @return A formatted string is returned specific the name of caller class and caller method of debugger.
     */
    private static String getCallerInfo() {
        String callerClassName = Thread.currentThread().getStackTrace()[3].getClassName() + "::";
        String callerMethodName = Thread.currentThread().getStackTrace()[3].getMethodName() + "()";
        return callerClassName + callerMethodName;
    }
}

package hello.advanced.app.trace.callback;

import hello.advanced.app.trace.TraceStatus;
import hello.advanced.app.trace.logtrace.LogTrace;

public class TraceTemplate {

    private final LogTrace trace;

    public TraceTemplate(LogTrace logTrace) {
        this.trace = logTrace;
    }

    public <T> T excute(String message, TraceCallback<T> callback) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);
            T result = callback.call();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}

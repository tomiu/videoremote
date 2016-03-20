package com.eo.videoremote.interfaces;

/**
 * Created by tomiurankar on 04/03/16.
 */
public interface ThreadCallback<V> {
    /**
     * notifies just before background operation will start. Always should be called on the calling thread
     */
    void workerStart();
    /**
     * It is called when the background operation completes. Always should be called on the calling thread
     * If the operation is successful, {@code exception} will be {@code null}.
     */
    void workerEnd(V result, Exception exception);
}

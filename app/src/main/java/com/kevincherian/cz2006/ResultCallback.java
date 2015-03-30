package com.kevincherian.cz2006;


/**
 * Generic callback method for receiving results
 *
 * @param <T> Type of data to be received
 * @author Kevin Cherian
 */
public interface ResultCallback<T extends Object> {
    public void onResult(T data);
}

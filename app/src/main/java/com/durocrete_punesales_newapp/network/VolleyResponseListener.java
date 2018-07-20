package com.durocrete_punesales_newapp.network;

/**
 * Created by root on 16/7/16.
 */
public interface VolleyResponseListener<T> {


    void onResponse(T[] object, String s, String key);

    void onError(String message);

}

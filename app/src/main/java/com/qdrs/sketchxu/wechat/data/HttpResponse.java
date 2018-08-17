package com.qdrs.sketchxu.wechat.data;

import java.util.HashMap;
import java.util.Map;

public enum HttpResponse {

    PARA_ERROR(10001),

    LOGIN_PASSWORD_ERROR,

    LOGIN_USERNAME_ERROR,

    LOGIN_INTERNAL_ERROR,

    REGISTER_USER_EXIST,

    REGISTER_INTERNAL_ERROR,

    GetAllUsersFailNoUser,

    GetAllUsersFailInternalError;


    private int value;

    HttpResponse() {
        this(Count.currentValue);
    }

    HttpResponse(int value) {
        this.value = value;
        Count.currentValue += 1;
        Count.map.put(this.value, this);
    }

    public int getValue() {
        return value;
    }

    private static final class Count {
        private static int currentValue = 0;
        private static Map<Integer, HttpResponse> map= new HashMap<>();
    }

    public static HttpResponse getHttpResponse(int value) {
        return Count.map.get(value);
    }

}

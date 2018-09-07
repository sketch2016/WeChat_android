package com.qdrs.sketchxu.wechat.model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.qdrs.sketchxu.wechat.data.UserInfo;
import com.qdrs.sketchxu.wechat.data.UserMessageBody;

import java.util.List;

public class FormatUtil {

    public static String Object2JsonString(Object obj) {

        //GsonBuilder gb = new GsonBuilder();
        //String jsonUserInfo = gb.create().toJson(obj);

        return new Gson().toJson(obj);
    }

    public static JsonObject object2Json(Object obj) {
        JsonParser parser = new JsonParser();
        String str;
        if (obj instanceof String) {
            str = (String) obj;
        } else {
            str = Object2JsonString(obj);
        }
        JsonElement je = parser.parse(str);
        if (je.isJsonObject()) {
            return je.getAsJsonObject();
        }
        JsonObject jo = new JsonObject();


        return null;
    }

    public static Object json2Object(JsonObject jsonObject, Class clazz) {
        return new Gson().fromJson(jsonObject, clazz);
    }

    public static JsonObject String2Json(String str) {
        return object2Json(str);
    }

    public static JsonArray String2JsonArray(String str) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement je = parser.parse(str);
        if (je.isJsonArray()) {
            return je.getAsJsonArray();
        }

        return null;
    }


    public static <T> List<T> jsonArrayString2List(String jsonArrayString, Class<T> t) {
        //JSONArray jsonarray = JSONArray.fromObject(json);

        Gson gson = new Gson();
        List<T> list = gson.fromJson(jsonArrayString, new TypeToken<List<T>>(){}.getType());
        return list;
    }

    public static List<UserInfo> jsonArrayString2List(String jsonArrayString) {
        //JSONArray jsonarray = JSONArray.fromObject(json);

        Gson gson = new Gson();
        List<UserInfo> list = gson.fromJson(jsonArrayString, new TypeToken<List<UserInfo>>(){}.getType());
        return list;
    }

    public static List<UserMessageBody.MessageContent> jsonArrayString2MessageContentList(String jsonArrayString) {
        //JSONArray jsonarray = JSONArray.fromObject(json);

        Gson gson = new Gson();
        List<UserMessageBody.MessageContent> list = gson.fromJson(jsonArrayString, new TypeToken<List<UserMessageBody.MessageContent>>(){}.getType());
        return list;
    }

    public static <T> List<T> jsonArray2List(JsonArray jsonArray, Class<T> t) {
        Gson gson = new Gson();
        List<T> list = gson.fromJson(jsonArray, new TypeToken<List<T>>(){}.getType());
        return list;
    }

    public static String decorateStringToJsonArrayString(String str, int type) {
        return "[{\"type\":" + type
                + ",\"content\":\"" + str
                + "\"}]";
    }
}

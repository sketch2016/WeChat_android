package com.qdrs.sketchxu.wechat.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import com.qdrs.sketchxu.wechat.R;
import com.qdrs.sketchxu.wechat.model.ACache;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ACacheTest {
    private static final String TAG = "ACacheTest";
    private ACache mCache;

    private Context mContext;

    public ACacheTest(Context context) {
        mContext = context;
        mCache = ACache.get(context);
    }

    /*
     * 测试String
     */
    public void testString() {
        String nameBefore = mCache.getAsString("name");
        Log.d(TAG, "nameBefore = " + nameBefore);
        mCache.put("name", "sketch");
        String name = mCache.getAsString("name");
        Log.d(TAG, "name = " + name);
    }


    /*
     * 测试JSONObject
     */
    public void testJSONObject() {
        String jsonObject = "{\"name\":\"sketch\",\"age\":\"28\"}";
        try {
            mCache.put("name", new JSONObject(jsonObject));
            JSONObject obj = mCache.getAsJSONObject("name");
            Log.d(TAG, "jsonObject = " + obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /*
     * 测试JSONArray
     */
    public void testJSONArray() {
        String jsonArray = "[{\"type\":1,\"content\":\"hello1\"}," +
                "{\"type\":2,\"content\":\"emoji\"}," +
                "{\"type\":1,\"content\":\"hello2\"}," +
                "{\"type\":3,\"content\":\"pic\"}]";
        try {
            mCache.put("jsonArray", new JSONArray(jsonArray));
            JSONArray jarray = mCache.getAsJSONArray("jsonArray");
            Log.d(TAG, "jsonArray = " + jarray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /*
     * 测试Bitmap
     */
    public void testBitmap() {
        Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.default_pic);
        mCache.put("bitmap", b);
        Bitmap b1 = mCache.getAsBitmap("bitmap");
        Log.d(TAG, "b1.Bytes = " + b1.getByteCount());

    }

    /*
     * 测试Drawable
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void testDrawable() {
        Drawable d = mContext.getDrawable(R.mipmap.default_pic);
        mCache.put("drawable", d);
        Drawable d1 = mCache.getAsDrawable("drawable");
        Log.d(TAG, "drawable.height = " + d1.getIntrinsicHeight());
    }

    /*
     * 测试Serializable eg:ArrayList,HashMap
     */
    public void testSerializable() {
        ArrayList<String> list = new ArrayList();
        list.add("1");
        mCache.put("arrayList", list);
        ArrayList<String> list1 = (ArrayList<String>)mCache.getAsObject("arrayList");
        Log.d(TAG, "list1.size = " + list1.size());
    }

    /*
     * 测试byte
     */
    public void testByte() {
        byte[] bytes = new byte[]{1,2,3,4,5};
        mCache.put("bytes", bytes);
        byte[] bytes1 = mCache.getAsBinary("bytes");
        Log.d(TAG, "bytes1.size = " + bytes1.length);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void test() {
        testString();
        testJSONObject();
        testJSONArray();
        testBitmap();
        testDrawable();
        testSerializable();
        testByte();
    }
}

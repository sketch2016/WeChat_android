package com.qdrs.sketchxu.wechat.model;

import android.graphics.Bitmap;
import android.os.Message;
import android.util.Log;
import com.qdrs.sketchxu.wechat.Config;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;
import org.json.JSONObject;
import service.HttpService;

import java.util.Map;

public class QiNiuUtil {

    private static final String TAG = "QiNiuUtil";

    private static final int M = 1024 * 1024 * 8;

    private static final String ACCESS_KEY = "lm6f3peedWHayH-xyZdd-xSCqyoNyvRhS7jkdT0e";
    private static final String SECRET_KEY = "f78vnIyeozEbZgqOxemFNVfg4cs9Q0YifnuK9WS_";
    //要上传的空间
    private static final String bucketname = "xiangantest";
    private static final String doman = "7xko2c.com1.z0.glb.clouddn.com";

    //密钥配置
    private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    //private static UploadManager uploadManager = new UploadManager(new Configuration(Zone.autoZone()));

    private static String downloadToken;
    public static String getUpToken() {
        final String url = Config.HOST + Config.UPTOKEN + "?" +
                Config.SESSIONID +
                "=" + SharedPrefUtil.get().getString(Config.SESSIONID);
        Map result = HttpUtil.get(url);
        String uptoken = (String)result.get("response");
        //String uptoken = auth.uploadToken(bucketname);

        Log.d(TAG, "getUpToken: " + uptoken);
        return uptoken;
    }

    public static String getDownloadToken(String baseUrl) {
//        final String url = Config.HOST + Config.DOWNLOADTOKEN + "?" +
//                Config.SESSIONID +
//                "=" + SharedPrefUtil.get().getString(Config.SESSIONID) + "&" +
//                Config.DOWNLOADKEY +
//                "=" + downloadKey;
//        Map result = HttpUtil.get(url);
//        String downloadToken = (String)result.get("response");
        String downloadToken = auth.privateDownloadUrl(baseUrl);
        Log.d(TAG, "getDownloadToken: " + downloadToken);

        return downloadToken;
    }

    public static boolean uploadFile(String key, Bitmap inputStream, final Message message) {
        Log.d(TAG, "uploadFile: key = " + key);
        final boolean success = false;
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                //.recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
                //.recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        UploadManager uploadManager = new UploadManager(config);
        Log.i(TAG, "Bitmap.getByteCount = " + inputStream.getByteCount() + " width = " + inputStream.getWidth());
        int bytes = inputStream.getByteCount();
        if (bytes > M * 1) {
            float scale = ((float)M * 1) / bytes;
            Log.i(TAG, "scale = " + scale);
            inputStream = BitmapUtil.compressMatrix(inputStream, scale, scale);
        }
        byte[] byt = BitmapUtil.Bitmap2Bytes(inputStream);
        Log.i(TAG, "byt.length = " + byt.length);
        downloadToken = getDownloadToken(key);
        uploadManager.put(byt, key, getUpToken(),
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {

                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            Log.i(TAG, "Upload Success");
                            String baseUrl = "http://" + doman + "/" + key;
                            Log.i(TAG, "downloadtoken should be:" + getDownloadToken(baseUrl));
                            message.arg1 = info.isOK() ? 1 : 0;
                            HttpService.getInstance().getDownloadToken(key, message);
                        } else {
                            Log.i(TAG, "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i(TAG, key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);

        //System.out.println(response.toString());
        //Log.d(TAG, "response:" + response.bodyString());


        return success;
    }
}

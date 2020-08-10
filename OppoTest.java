import io.github.lz233.server.HttpRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class OppoTest {
    public static void main(String[] args) throws Exception {
        getQueryOtaUpdateUriJson();
    }

    public static void getQueryOtaUpdateUriJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", "2");
        jsonObject.put("otaPrefix", "OW19W3");
        jsonObject.put("otaVersion", "OW19W3_11.A.39_0390_202006301157");
        jsonObject.put("imei", ""); //Fill in the imei of the watch
        jsonObject.put("mode", "0");
        jsonObject.put("language", "zh-CN");
        jsonObject.put("productName", "OW19W3");
        jsonObject.put("time", System.currentTimeMillis());
        jsonObject.put("romVersion","OW19W3_11_A.39");
        jsonObject.put("androidVersion","Android8.1.0");
        jsonObject.put("colorOSVersion","V1.0.0");
        jsonObject.put("registrationId","unknown");
        jsonObject.put("type","0");
        System.out.println(jsonObject.toString());
        jsonObject = createEncryptJsonObject(jsonObject);
        System.out.println(jsonObject.toString());
        String result = HttpRequest.sendPost("https://iota.coloros.com/post/Query_Update", jsonObject.toString());
        System.out.println(result);
        System.out.println(createDecryptJsonObject(new JSONObject(result)).toString());
    }

    public static void getQueryDescriptionUriJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", "2");
        jsonObject.put("otaPrefix", "OW19W3");
        jsonObject.put("otaVersion", "OW19W3_11.A.39_0390_202006301157");
        jsonObject.put("imei", ""); //Fill in the imei of the watch
        jsonObject.put("mode", "0");
        jsonObject.put("language", "zh-CN");
        jsonObject.put("productName", "OW19W1");
        jsonObject.put("time", System.currentTimeMillis());
        System.out.println(jsonObject.toString());
        jsonObject = createEncryptJsonObject(jsonObject);
        System.out.println(jsonObject.toString());
        String result = HttpRequest.sendPost("https://iota.coloros.com/post/Query_Description", jsonObject.toString());
        System.out.println(result);
        System.out.println(createDecryptJsonObject(new JSONObject(result)).toString());
    }

    public static String getOtaVersionForDecription() {
        String otaVersion = "OW19W3_11.A.39_0390_202006301157";
        if (otaVersion == null) {
            return "";
        }
        String[] split = otaVersion.replace("_INT", "").split("_");
        int[] iArr = {4, 12, 14};
        char[] charArray = otaVersion.toCharArray();
        if (split.length != iArr[0] || split[3].length() != iArr[1] || split[2].length() != iArr[0]) {
            return otaVersion;
        }
        charArray[otaVersion.length() - iArr[2]] = '0';
        return String.valueOf(charArray);
    }

    public static String getLocale() {
        StringBuilder sb = new StringBuilder();
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String country = locale.getCountry();
        if (!language.equals("")) {
            sb.append(language);
            if (!country.equals("")) {
                sb.append("-");
                sb.append(country);
            }
        } else {
            sb.append("en");
        }
        return sb.toString();
    }

    public static JSONObject createEncryptJsonObject(JSONObject jSONObject) throws JSONException {
        String jSONObject2 = jSONObject.toString();
        String generateToken = AesEncryptUtils.generateToken();
        String encrypt = AesEncryptUtils.encrypt(jSONObject2, AesEncryptUtils.generateKey(generateToken));
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("params", encrypt + generateToken);
        return jSONObject3;
    }

    public static JSONObject createDecryptJsonObject(JSONObject jSONObject) throws JSONException {
        String string = jSONObject.getString("resps");
        return new JSONObject(AesEncryptUtils.decrypt(string.substring(0, string.length() - 15), AesEncryptUtils.generateKey(string.substring(string.length() - 15, string.length()))));
    }

}

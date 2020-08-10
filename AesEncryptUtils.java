import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

public class AesEncryptUtils {
    private static final String[] START_KEYS = {"oppo1997", "baed2017", "java7865", "231uiedn", "09e32ji6", "0oiu3jdy", "0pej387l", "2dkliuyt", "20odiuye", "87j3id7w"};

    public static String decrypt(String str, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
            Cipher instance = Cipher.getInstance("AES/ECB/PKCS5Padding");
            instance.init(2, secretKeySpec);
            //return new String(instance.doFinal(OtaBase64.decodeBase64(str.getBytes("UTF-8"))), "UTF-8");
            return new String(instance.doFinal(Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    public static String encrypt(String str, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
            Cipher instance = Cipher.getInstance("AES/ECB/PKCS5Padding");
            instance.init(1, secretKeySpec);
            //return OtaBase64.encodeBase64String(instance.doFinal(str.getBytes("UTF-8")));
            return Base64.getEncoder().encodeToString(instance.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            return null;
        }
    }

    public static String generateKey(String str) {
        String str2 = START_KEYS[convertInteger(str.substring(0, 1), 0)];
        return str2 + str.substring(4, 12);
    }

    public static int convertInteger(String str, int i) {
        if (str.equals("")) {
            return i;
        }
        return Integer.parseInt(str);
    }

    public static String generateToken() {
        return new Random().nextInt(10) + getSpecialVal(14);
    }

    public static String getSpecialVal(int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 64; i2 <= 90; i2++) {
            arrayList.add(String.valueOf((char) i2));
        }
        for (int i3 = 97; i3 <= 122; i3++) {
            arrayList.add(String.valueOf((char) i3));
        }
        for (int i4 = 33; i4 <= 43; i4++) {
            if (!(i4 == 34 || i4 == 39 || i4 == 42)) {
                arrayList.add(String.valueOf((char) i4));
            }
        }
        arrayList.add(String.valueOf('_'));
        for (int i5 = 0; i5 < 10; i5++) {
            arrayList.add(i5 + "");
        }
        int size = arrayList.size();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        do {
            sb.append((String) arrayList.get(random.nextInt(size - 1) + 1));
            i--;
        } while (i > 0);
        return sb.toString();
    }
}

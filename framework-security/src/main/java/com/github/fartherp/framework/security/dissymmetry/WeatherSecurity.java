/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.security.dissymmetry;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2016/3/4
 */
public class WeatherSecurity {
    private static final char LAST_2_BYTE = (char) Integer.parseInt("00000011", 2);
    private static final char LAST_4_BYTE = (char) Integer.parseInt("00001111", 2);
    private static final char LAST_6_BYTE = (char) Integer.parseInt("00111111", 2);
    private static final char LEAD_6_BYTE = (char) Integer.parseInt("11111100", 2);
    private static final char LEAD_4_BYTE = (char) Integer.parseInt("11110000", 2);
    private static final char LEAD_2_BYTE = (char) Integer.parseInt("11000000", 2);
    private static final char[] ENCODE_TABLE = new char[] { 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/',
    };

    public static String standardURLEncoder(String data, String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            mac.init(spec);
			byte[] byteHMAC = mac.doFinal(data.getBytes());
            if (byteHMAC != null) {
                String oauth = encode(byteHMAC);
				return URLEncoder.encode(oauth, "utf8");
			}
        } catch (Exception e1) {
            e1.printStackTrace();
        }
		return "";
    }

    public static String encode(byte[] from) {
        StringBuilder to = new StringBuilder((int) (from.length * 1.34) + 3);
        int num = 0;
        char currentByte = 0;
        for (int i = 0; i < from.length; i++) {
            num = num % 8;
            while (num < 8) {
                switch (num) {
                    case 0:
                        currentByte = (char) (from[i] & LEAD_6_BYTE);
                        currentByte = (char) (currentByte >>> 2);
                        break;
                    case 2:
                        currentByte = (char) (from[i] & LAST_6_BYTE);
                        break;
                    case 4:
                        currentByte = (char) (from[i] & LAST_4_BYTE);
                        currentByte = (char) (currentByte << 2);
                        if ((i + 1) < from.length) {
                            currentByte |= (from[i + 1] & LEAD_2_BYTE) >>> 6;
                        }
                        break;
                    case 6:
                        currentByte = (char) (from[i] & LAST_2_BYTE);
                        currentByte = (char) (currentByte << 4);
                        if ((i + 1) < from.length) {
                            currentByte |= (from[i + 1] & LEAD_4_BYTE) >>> 4;
                        }
                        break;
					default:
                }
                to.append(ENCODE_TABLE[currentByte]);
                num += 6;
            }
        }
        if (to.length() % 4 != 0) {
            for (int i = 4 - to.length() % 4; i > 0; i--) {
                to.append("=");
            }
        }
        return to.toString();
    }
}

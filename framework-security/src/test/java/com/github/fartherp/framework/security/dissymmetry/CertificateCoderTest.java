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

import org.testng.annotations.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2015/4/13
 */
public class CertificateCoderTest {

//	@Test
    public void main() {
        String password = "123456";
        String alias = "www.zlex.org";
        String certificatePath = "d:/zlex.cer";
        String keyStorePath = "d:/zlex.keystore";
        String inputStr = "Ceritifcate";
        byte[] data = inputStr.getBytes();

        CertificateCoder certificateCoder = new CertificateCoder();
        byte[] encrypt = certificateCoder.encryptByPublicKey(data, certificatePath);

        System.out.println(new String(encrypt));

        byte[] decrypt = certificateCoder.decryptByPrivateKey(encrypt, keyStorePath, alias, password);
        String outputStr = new String(decrypt);

        System.out.println("加密前[" + inputStr + "]解密后:[" + outputStr + "]");
        System.out.println(certificateCoder.verifyCertificate(certificatePath));
    }

    public void testHttps() throws Exception {
        String clientKeyStorePath = "d:/zlex-client.keystore";
        String clientPassword = "654321";
        URL url = new URL("https://www.zlex.org/examples/");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        conn.setDoInput(true);
        conn.setDoOutput(true);

        CertificateCoder.configSSLSocketFactory(conn, clientPassword, clientKeyStorePath, clientKeyStorePath);

        InputStream is = conn.getInputStream();

        int length = conn.getContentLength();

        DataInputStream dis = new DataInputStream(is);
        byte[] data = new byte[length];
        dis.readFully(data);

        dis.close();
        System.out.println(new String(data));
        conn.disconnect();
    }
}

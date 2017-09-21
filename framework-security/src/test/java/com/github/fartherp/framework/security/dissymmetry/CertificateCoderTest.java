/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.dissymmetry;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class CertificateCoderTest {
    public static void main(String [] args) {
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

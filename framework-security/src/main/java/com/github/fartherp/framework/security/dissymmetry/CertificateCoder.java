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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2015/4/13
 */
public class CertificateCoder {
    /**
     * Java密钥库(Java Key Store，JKS)KEY_STORE
     */
    public static final String KEY_STORE = "JKS";
    public static final String X509 = "X.509";
    public static final String SunX509 = "SunX509";
    public static final String SSL = "SSL";

    /**
     * 由KeyStore获得私钥
     *
     * @param keyStorePath the Key Store Path
     * @param alias the alias
     * @param password 密码
     * @return 私钥
     */
    private static PrivateKey getPrivateKey(String keyStorePath, String alias, String password) {
        try {
            KeyStore ks = getKeyStore(keyStorePath, password);
            return (PrivateKey) ks.getKey(alias, password.toCharArray());
        } catch (KeyStoreException | UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无此算法", e);
        }
	}

    /**
     * 由Certificate获得公钥
     *
     * @param certificatePath 路径
     * @return 公钥
     */
    private static PublicKey getPublicKey(String certificatePath) {
        Certificate certificate = getCertificate(certificatePath);
        return certificate.getPublicKey();
    }

    /**
     * 获得Certificate
     *
     * @param certificatePath 路径
     * @return the Certificate
     */
    private static Certificate getCertificate(String certificatePath) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(certificatePath);
            CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
            return certificateFactory.generateCertificate(in);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("没有此文件:" + certificatePath, e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得Certificate
     *
     * @param keyStorePath the Key Store Path
     * @param alias the alias
     * @param password 密码
     * @return 获得Certificate
     */
    private static Certificate getCertificate(String keyStorePath, String alias, String password) {
        try {
            KeyStore ks = getKeyStore(keyStorePath, password);
            return ks.getCertificate(alias);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得KeyStore
     *
     * @param keyStorePath the Key Store Path
     * @param password 密码
     * @return the KeyStore
     */
    private static KeyStore getKeyStore(String keyStorePath, String password) throws KeyStoreException {
        FileInputStream is = null;
        KeyStore ks = null;
        try {
            is = new FileInputStream(keyStorePath);
            ks = KeyStore.getInstance(KEY_STORE);
            ks.load(is, password.toCharArray());
            return ks;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("没有此文件:" + keyStorePath, e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("IO异常", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无此算法", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 私钥解密
     *
     * @param data 加密后数据
     * @param keyStorePath the Key Store Path
     * @param alias the alias
     * @param password 密码
     * @return 数组
     */
    public static byte[] decryptByPrivateKey(byte[] data, String keyStorePath, String alias, String password) {
        // 取得私钥
        PrivateKey privateKey = getPrivateKey(keyStorePath, alias, password);
        // 对数据解密
        return cipher(data, Cipher.DECRYPT_MODE, privateKey.getAlgorithm(), privateKey);
    }

    /**
     * 公钥加密
     *
     * @param data 加密前字符串
     * @param certificatePath 路径
     * @return 加密后字符串
     */
    public static byte[] encryptByPublicKey(byte[] data, String certificatePath) {
        // 取得公钥
        PublicKey publicKey = getPublicKey(certificatePath);
        // 对数据加密
        return cipher(data, Cipher.ENCRYPT_MODE, publicKey.getAlgorithm(), publicKey);
    }

    private static byte[] cipher(byte[] data, int mode, String algorithm, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(mode, key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无此算法", e);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效密钥", e);
        }
    }

    /**
     * 验证Certificate
     *
     * @param certificatePath 路径
     * @return true：验证成功，false：验证失败
     */
    public static boolean verifyCertificate(String certificatePath) {
        return verifyCertificate(new Date(), certificatePath);
    }

    /**
     * 验证Certificate是否过期或无效
     *
     * @param date the Date
     * @param certificatePath 路径
     * @return true：验证成功，false：验证失败
     */
    public static boolean verifyCertificate(Date date, String certificatePath) {
        try {
            // 取得证书
            Certificate certificate = getCertificate(certificatePath);
            // 验证证书是否过期或无效
            return verifyCertificate(date, certificate);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证证书是否过期或无效
     *
     * @param date the Date
     * @param certificate 路径
     * @return true：验证成功，false：验证失败
     */
    private static boolean verifyCertificate(Date date, Certificate certificate) {
        try {
            X509Certificate x509Certificate = (X509Certificate) certificate;
            x509Certificate.checkValidity(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 签名
     *
     * @param keyStorePath the Key Store Path
     * @param alias the alias
     * @param password 密码
     * @return 签名字符串
     */
    public static String sign(byte[] sign, String keyStorePath, String alias, String password) {
        // 获得证书
        X509Certificate x509Certificate = (X509Certificate) getCertificate(keyStorePath, alias, password);
        // 取得私钥
        PrivateKey privateKey = getPrivateKey(keyStorePath, alias, password);
        try {
            // 构建签名
            Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
            signature.initSign(privateKey);
            signature.update(sign);
            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无此算法", e);
        } catch (SignatureException e) {
            throw new RuntimeException("签名异常", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效密钥", e);
        }
    }

    /**
     * 验证签名
     *
     * @param data 需要签名数组
     * @param sign 签名字符串
     * @param certificatePath 路径
     * @return true：验证成功，false：验证失败
     */
    public static boolean verify(byte[] data, String sign, String certificatePath) {
        // 获得证书
        X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);
        // 获得公钥
        PublicKey publicKey = x509Certificate.getPublicKey();
        try {
            // 构建签名
            Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
            signature.initVerify(publicKey);
            signature.update(data);
            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无此算法", e);
        } catch (SignatureException e) {
            throw new RuntimeException("签名异常", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效密钥", e);
        }
    }

    /**
     * 验证Certificate
     *
     * @param keyStorePath the Key Store Path
     * @param alias the alias
     * @param password 密码
     * @return true：验证成功，false：验证失败
     */
    public static boolean verifyCertificate(Date date, String keyStorePath, String alias, String password) {
        try {
            Certificate certificate = getCertificate(keyStorePath, alias, password);
            return verifyCertificate(date, certificate);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证Certificate
     *
     * @param keyStorePath the Key Store Path
     * @param alias the alias
     * @param password 密码
     * @return true：验证成功，false：验证失败
     */
    public static boolean verifyCertificate(String keyStorePath, String alias, String password) {
        return verifyCertificate(new Date(), keyStorePath, alias, password);
    }

    /**
     * 获得SSLSocektFactory
     *
     * @param password 密码
     * @param keyStorePath 密钥库路径
     * @param trustKeyStorePath 信任库路径
     * @return the SSLSocektFactory
     */
    private static SSLSocketFactory getSSLSocketFactory(String password, String keyStorePath, String trustKeyStorePath) {
        try {
            // 初始化密钥库
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(SunX509);
            KeyStore keyStore = getKeyStore(keyStorePath, password);
            keyManagerFactory.init(keyStore, password.toCharArray());

            // 初始化信任库
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(SunX509);
            KeyStore trustkeyStore = getKeyStore(trustKeyStorePath, password);
            trustManagerFactory.init(trustkeyStore);

            // 初始化SSL上下文
            SSLContext ctx = SSLContext.getInstance(SSL);
            ctx.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            return ctx.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无此算法", e);
        } catch (UnrecoverableKeyException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
	}

    /**
     * 为HttpsURLConnection配置SSLSocketFactory
     *
     * @param conn HttpsURLConnection
     * @param password 密码
     * @param keyStorePath 密钥库路径
     * @param trustKeyStorePath 信任库路径
     */
    public static void configSSLSocketFactory(HttpsURLConnection conn, String password, String keyStorePath, String trustKeyStorePath) {
        conn.setSSLSocketFactory(getSSLSocketFactory(password, keyStorePath, trustKeyStorePath));
    }
}

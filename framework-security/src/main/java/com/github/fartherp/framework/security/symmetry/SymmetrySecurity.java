/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import com.github.fartherp.framework.security.ISecurity;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/10
 */
public abstract class SymmetrySecurity implements ISecurity {
    /**
     * 密钥
     */
    protected byte[] key;

    /**
     * 位移向量
     */
    protected byte[] desIv;

    /**
     * 加密
     * @param data 需要加密数据[数组]
     * @param key 加密密钥[数组]
     * @return 加密数据[数组]
     */
    public byte[] encrypt(byte[] data, byte[] key) {
        validation(data, key);
        try {
            Cipher cipher = getCipher();
            AlgorithmParameterSpec algorithmParameterSpec = getAlgorithmParameterSpec();
            // 传入加密标识与加密密钥
            cipher.init(Cipher.ENCRYPT_MODE, generateRandomKey(key), algorithmParameterSpec);
            // 加密数据
            return cipher.doFinal(data);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]无此填充", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]无此算法", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]无效密钥错误", e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]无效算法参数错误", e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "] (data length error)", e);
        } catch (BadPaddingException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]填充错误", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]无效密钥", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]不支持的编码", e);
        }
    }

    public byte[] encrypt(byte[] data) {
        return encrypt(data, this.key);
    }

    /**
     * 解密
     * @param data 需要解密数据[数组]
     * @param key 解密密钥[数组]
     * @return 解密数据[数组]
     */
    public byte[] decrypt(byte[] data, byte[] key) {
        validation(data, key);
        try {
            Cipher cipher = getCipher();
            AlgorithmParameterSpec algorithmParameterSpec = getAlgorithmParameterSpec();
            // 传入解密标识与解密密钥
            cipher.init(Cipher.DECRYPT_MODE, generateRandomKey(key), algorithmParameterSpec);
            return cipher.doFinal(data);// 解密数据
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]无此填充", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]无此算法", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]无效密钥错误", e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]无效算法参数错误", e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "] (data length error)", e);
        } catch (BadPaddingException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]填充错误", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]无效密钥", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]不支持的编码", e);
        }
    }

    public byte[] decrypt(byte[] data) {
        return decrypt(data, this.key);
    }

    public abstract void validation(byte[] data, byte[] key);

    /**
     * The Cipher.
     * @return the Cipher
     * @throws NoSuchPaddingException the NoSuchPaddingException
     * @throws NoSuchAlgorithmException the NoSuchAlgorithmException
     */
    public abstract Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException;

    /**
     * The Key.
     * @param key the key array
     * @return the Key
     * @throws NoSuchAlgorithmException the NoSuchAlgorithmException
     * @throws InvalidKeyException the InvalidKeyException
     * @throws InvalidKeySpecException the InvalidKeySpecException
     * @throws UnsupportedEncodingException the UnsupportedEncodingException
     */
    public abstract Key generateRandomKey(byte [] key) throws NoSuchAlgorithmException, InvalidKeyException,
            InvalidKeySpecException, UnsupportedEncodingException;

    /**
     * The AlgorithmParameterSpec.
     * @return the AlgorithmParameterSpec
     */
    public abstract AlgorithmParameterSpec getAlgorithmParameterSpec();
}

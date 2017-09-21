/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.dissymmetry;

import com.github.fartherp.framework.security.ISecurity;

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

/**
 * <p>RSA公钥&私钥</p>
 *
 * @author CK
 */
public class RSA extends DisSymmetrySecurity {
    public KeyPairGenerator getKeyPairGenerator() throws NoSuchAlgorithmException {
        return KeyPairGenerator.getInstance(ISecurity.RSA_ALGORITHM);
    }

    public KeyFactory getKeyFactory() throws NoSuchAlgorithmException {
        return KeyFactory.getInstance(ISecurity.RSA_ALGORITHM);
    }

    public Signature getSignature(KeyFactory keyFactory) throws NoSuchAlgorithmException {
        return Signature.getInstance(ISecurity.SIGNATURE_ALGORITHM);
    }
}

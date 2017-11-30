/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.dissymmetry;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/11/30
 */
public class DisSymmetryKey {
    private byte[] privateKey;
    private byte[] publicKey;

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(byte[] privateKey) {
        this.privateKey = privateKey;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }
}

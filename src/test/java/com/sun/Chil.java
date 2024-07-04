package com.sun;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @date 2024/5/23
 */
public class Chil {


    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, IOException, BadPaddingException, InvalidKeyException {
        // 初始化 Key 生成器，指定算法类型为 RSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        // 密钥长度为 2048 位
        keyPairGenerator.initialize(2048);

        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 获取公钥
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();

        // 获取私钥
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        System.out.println("公钥：" + Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded()));

        System.out.println("私钥：" + Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded()));

        // 要加密的原文
        byte[] content = "你好 springdoc.cn".getBytes(StandardCharsets.UTF_8);

        System.out.println("原文：" + new String(content, StandardCharsets.UTF_8));

        // 加密后的密文
        ByteArrayOutputStream encryptedout = new ByteArrayOutputStream();
        // 公钥加密
        encode(rsaPublicKey, new ByteArrayInputStream(content), encryptedout);

        System.out.println("加密后的密文：" + Base64.getEncoder().encodeToString(encryptedout.toByteArray()));


        // 解密后的原文
        ByteArrayOutputStream decryptedOut = new ByteArrayOutputStream();
        // 私钥解密
        decode(rsaPrivateKey, new ByteArrayInputStream(encryptedout.toByteArray()), decryptedOut);

        System.out.println("解密后的原文：" + new String(decryptedOut.toByteArray(), StandardCharsets.UTF_8));
    }

    /**
     * 加密
     *
     * @param key KEY
     * @param in  输入参数
     * @param out 输出加密后的密文
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IOException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static void encode(Key key, InputStream in, OutputStream out) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        // 最大的加密明文长度
        final int maxEncryptBlock = 245;

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] buffer = new byte[maxEncryptBlock];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            out.write(cipher.doFinal(buffer, 0, len));
        }
    }

    /**
     * 解密
     *
     * @param key KEY
     * @param in  输入参数
     * @param out 输出解密后的原文
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IOException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static void decode(Key key, InputStream in, OutputStream out) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {

        // 最大的加密明文长度
        final int maxDecryptBlock = 256;

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] buffer = new byte[maxDecryptBlock];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            out.write(cipher.doFinal(buffer, 0, len));
        }
    }
}

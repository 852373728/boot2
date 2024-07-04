package com.sun;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;


/**
 * @date 2024/7/3
 */
public class Siga {

    /**
     * 私钥签名
     *
     * @param key       私钥
     * @param algorithm 算法
     * @param in        输入数据
     * @return 签名
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws SignatureException
     */
    public static byte[] sign(RSAPrivateKey key, String algorithm, InputStream in)
            throws InvalidKeyException, NoSuchAlgorithmException, IOException, SignatureException {
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(key);
        byte[] buffer = new byte[4096];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            signature.update(buffer, 0, len);
        }
        return signature.sign();
    }

    /**
     * 公钥验签
     *
     * @param key       公钥
     * @param algorithm 算法
     * @param in        输入数据
     * @param sign      签名
     * @return 签名是否符合
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws IOException
     */
    public static boolean validate(RSAPublicKey key, String algorithm, InputStream in, byte[] sign)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        Signature signature = Signature.getInstance(algorithm);
        signature.initVerify(key);
        byte[] buffer = new byte[4096];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            signature.update(buffer, 0, len);
        }
        return signature.verify(sign);
    }

    public static void main(String[] args) throws Exception {

        // 生成 RSA 密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 公钥和私钥
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 要签名的原文
        byte[] content = "你好 springdoc.cn".getBytes(StandardCharsets.UTF_8);

        // 使用私钥对数据进行签名
        byte[] sign = sign(rsaPrivateKey, "SHA256WithRSA", new ByteArrayInputStream(content));
        String s = Base64.getEncoder().encodeToString(sign);
        System.out.println("数字签名：" + s);

        // 使用公钥验证签名
        boolean valid = validate(rsaPublicKey, "SHA256WithRSA", new ByteArrayInputStream(content), Base64.getDecoder().decode(s));
        System.out.println("验签结果：" + valid);
    }
}

package com.bright.dev.util;

import org.apache.commons.net.util.Base64;
import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static junit.framework.TestCase.assertTrue;

@Component
public class RsaHelper {

    //生成公钥、私钥对(keysize=1024)
    public KeyPairInfo getKeyPair() {
        return getKeyPair(1024);
    }

   //生成公钥、私钥对
    public KeyPairInfo getKeyPair(int keySize) {
        try {
            KeyPairInfo pairInfo = new KeyPairInfo(keySize);
            //公钥
            String publicKeyString = String.valueOf(loadPublicKeyPKCS12());
            pairInfo.setPublicKey(publicKeyString);

            //私钥
            String privateKeyString = String.valueOf(loadPrivateKeyPKCS12());
            pairInfo.setPrivateKey(privateKeyString);
            return pairInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取公钥对象
     *
     * @param publicKeyBase64
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public PublicKey getPublicKey(String publicKeyBase64)
            throws InvalidKeySpecException, NoSuchAlgorithmException {

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicpkcs8KeySpec =
                new X509EncodedKeySpec(Base64.decodeBase64(publicKeyBase64));
        PublicKey publicKey = keyFactory.generatePublic(publicpkcs8KeySpec);
        return publicKey;
    }

    /**
     * 获取私钥对象
     *
     * @param privateKeyBase64
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public PrivateKey getPrivateKey(String privateKeyBase64)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privatekcs8KeySpec =
                new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyBase64));
        PrivateKey privateKey = keyFactory.generatePrivate(privatekcs8KeySpec);
        return privateKey;
    }

    /**
     * 加载私钥
     * @throws Exception
     */
    public StringBuilder loadPrivateKeyPKCS12() throws Exception {
        try {
            String pemPath = this.getClass().getClassLoader().getResource("rsa_private_key4server.pem").getPath();
            InputStream in = new FileInputStream(new File(pemPath));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
           return sb;
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * 加载公钥
     * @throws Exception
     */
    public StringBuilder loadPublicKeyPKCS12() throws Exception {
        try {
            String pemPath = this.getClass().getClassLoader().getResource("rsa_public_key4server.pem").getPath();
            InputStream in = new FileInputStream(new File(pemPath));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            return sb;
        } catch (IOException e) {
            throw new Exception("公钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 使用共钥加密
     *
     * @param content         待加密内容
     * @param publicKeyBase64 公钥 base64 编码
     * @return 经过 base64 编码后的字符串
     */
    public String encipher(String content, String publicKeyBase64) {
        return encipher(content, publicKeyBase64, -1);
    }

    /**
     * 使用共钥加密（分段加密）
     *
     * @param content              待加密内容
     * @param publicKeyBase64      公钥 base64 编码
     * @param segmentSize 分段大小,一般小于 keySize/8（段小于等于0时，将不使用分段加密）
     * @return 经过 base64 编码后的字符串
     */
    public String encipher(String content, String publicKeyBase64, int segmentSize) {
        try {
            PublicKey publicKey = getPublicKey(publicKeyBase64);
            return encipher(content, publicKey, segmentSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分段加密
     *
     * @param ciphertext  密文
     * @param key         加密秘钥
     * @param segmentSize 分段大小，<=0 不分段
     * @return
     */
    public String encipher(String ciphertext, Key key, int segmentSize) {
        try {
            // 用公钥加密
            byte[] srcBytes = ciphertext.getBytes();

            // Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance("RSA");
            // 根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] resultBytes = null;

            if (segmentSize > 0)
                resultBytes = cipherDoFinal(cipher, srcBytes, segmentSize); //分段加密
            else
                resultBytes = cipher.doFinal(srcBytes);

            String base64Str = Base64.encodeBase64String(resultBytes);
            return base64Str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分段大小
     *
     * @param cipher
     * @param srcBytes
     * @param segmentSize
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    private byte[] cipherDoFinal(Cipher cipher, byte[] srcBytes, int segmentSize)
            throws IllegalBlockSizeException, BadPaddingException, IOException {
        if (segmentSize <= 0)
            throw new RuntimeException("分段大小必须大于0");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int inputLen = srcBytes.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > segmentSize) {
                cache = cipher.doFinal(srcBytes, offSet, segmentSize);
            } else {
                cache = cipher.doFinal(srcBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * segmentSize;
        }
        byte[] data = out.toByteArray();
        out.close();
        return data;
    }

    /**
     * 使用私钥解密
     *
     * @param contentBase64    待加密内容,base64 编码
     * @param privateKeyBase64 私钥 base64 编码
     * @return
     * @segmentSize 分段大小
     */
    public String decipher(String contentBase64, String privateKeyBase64) {
        return decipher(contentBase64, privateKeyBase64, -1);
    }

    /**
     * 使用私钥解密（分段解密）
     *
     * @param contentBase64    待解密内容,base64 编码
     * @param privateKeyBase64 私钥 base64 编码
     * @return
     * @segmentSize 分段大小
     */
    public String decipher(String contentBase64, String privateKeyBase64, int segmentSize) {
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyBase64);
            return decipher(contentBase64, privateKey, segmentSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分段解密
     *
     * @param contentBase64 密文
     * @param key           解密秘钥
     * @param segmentSize   分段大小（小于等于0不分段）
     * @return
     */
    public String decipher(String contentBase64, Key key, int segmentSize) {
        try {
            // 用私钥解密
            byte[] srcBytes = Base64.decodeBase64(contentBase64);
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher deCipher = Cipher.getInstance("RSA");
            // 根据公钥，对Cipher对象进行初始化
            deCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decBytes = null;//deCipher.doFinal(srcBytes);
            if (segmentSize > 0)
                decBytes = cipherDoFinal(deCipher, srcBytes, segmentSize); //分段加密
            else
                decBytes = deCipher.doFinal(srcBytes);

            String decrytStr = new String(decBytes);
            return decrytStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 秘钥对
     */
    public class KeyPairInfo {
        public KeyPairInfo(int keySize) {
            setKeySize(keySize);
        }

        public KeyPairInfo(String publicKey, String privateKey) {
            setPrivateKey(privateKey);
            setPublicKey(publicKey);
        }

        String privateKey;
        String publicKey;
        int keySize = 0;

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public int getKeySize() {
            return keySize;
        }

        public void setKeySize(int keySize) {
            this.keySize = keySize;
        }
    }

    @Test
    public void encipherSegmentTest() {
        int keySize=2048;
        RsaHelper rsa = new RsaHelper();
        KeyPairInfo info = rsa.getKeyPair(keySize); //每次调用的公钥 私钥都一样
        assertTrue(info!=null);
        System.out.println(info.getPublicKey());
        System.out.println(info.getPrivateKey());
        //Debug.printFormat("公钥：{0}", info.getPublicKey());
        //Debug.printFormat("私钥：{0}", info.getPrivateKey());

        String content="1.rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;\r\n"
                + "2.rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;\r\n"
                + "3.rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;\r\n"
                + "4.rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;\r\n"
                + "5.rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;\r\n"
                + "6.rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;\r\n"
                + "7.rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;\r\n"
                + "8.rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;\r\n"
                + "9.rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;\r\n"
                + "0.rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;rsa加密、解密测试;\r\n";
        //Debug.printFormat("明文：{0}", content);

        int enSegmentSize=245;//keysize=1024时，分段不能大于117 ；keysize>=2048时，分段不能大于keySize/8+128；
        String ciphertext = rsa.encipher(content, info.getPublicKey(),enSegmentSize);
        //Debug.printFormat("密文：{0}", ciphertext);

        int deSegmentSize=256;//等于keySize/8
//        String deTxt = rsa.decipher(ciphertext, info.getPrivateKey(),deSegmentSize);
        //Debug.printFormat("解密：{0}", deTxt);

//        assertTrue(content.equals(deTxt));
    }

}

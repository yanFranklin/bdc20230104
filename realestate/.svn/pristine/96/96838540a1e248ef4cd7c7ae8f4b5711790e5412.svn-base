package cn.gtmap.realestate.exchange.util.rsa;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;


import javax.crypto.Cipher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


/*
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0, 2019/11/28
 * @description 合肥部级接口 RSA加密工具类
 */
public class HfBjjkRSAUtils {

    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";



    public static Map<String, String> createKeys(int keySize){
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try{
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        }catch(NoSuchAlgorithmException e){
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }

    /**
     * 得到公钥
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }
    
    public static RSAPublicKey getPublicKey_bat(String file) throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {
    	FileInputStream fileInputStream=new FileInputStream(new File(file));
    	ObjectInputStream inputStream =new ObjectInputStream(fileInputStream);
		//读取公钥   
    	RSAPublicKey key=(RSAPublicKey)inputStream.readObject();
    	return key;
    }

    /**
     * 得到私钥
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }
    public static RSAPrivateKey getPrivateKey_bat(String file) throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {
    	//通过PKCS#8编码的Key指令获得私钥对象
    	FileInputStream fileInputStream2=new FileInputStream(new File(file));
		ObjectInputStream inputStream2 =new ObjectInputStream(fileInputStream2);
		RSAPrivateKey key=(RSAPrivateKey)inputStream2.readObject();
    	return key;
    }

    /**
     * 公钥加密
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
        }catch(Exception e){
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt(String data, RSAPrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), CHARSET);
        }catch(Exception e){
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥加密
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncrypt(String data, RSAPrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
        }catch(Exception e){
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), CHARSET);
        }catch(Exception e){
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize){
        int maxBlock = 0;
        if(opmode == Cipher.DECRYPT_MODE){
            maxBlock = keySize / 8;
        }else{
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try{
            while(datas.length > offSet){
                if(datas.length-offSet > maxBlock){
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                }else{
                    buff = cipher.doFinal(datas, offSet, datas.length-offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        }catch(Exception e){
            throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }
    
    /**
     * 公钥解密
     * @param str
     * @return
     * @throws Exception
     */
    public static String decodeByPublic(String str, String publicKeyStr) throws Exception{
    	//获取公钥
    	RSAPublicKey publicKey = HfBjjkRSAUtils.getPublicKey(publicKeyStr);
    	//解密报文
    	String decodedData = HfBjjkRSAUtils.publicDecrypt(str, publicKey);
    	return decodedData;
    }
    /**
     * 私钥解密
     * @param str
     * @return
     * @throws Exception
     */
    public static String decodeByprivate(String str, String privateKeyStr) throws Exception{
    	//获取公钥
    	RSAPrivateKey privateKey = HfBjjkRSAUtils.getPrivateKey(privateKeyStr);
    	//解密报文
    	String decodedData = HfBjjkRSAUtils.privateDecrypt(str, privateKey);
    	return decodedData;
    }
    
    /**
     * 私钥加密
     * @param str
     * @return
     * @throws Exception
     */
    public static String encodeByPrivate(String str, String privateKeyStr) throws Exception{
    	//获取私钥
    	RSAPrivateKey privateKey = HfBjjkRSAUtils.getPrivateKey(privateKeyStr);
    	String encodedData = HfBjjkRSAUtils.privateEncrypt(str, privateKey);
    	return encodedData;
    }
    /**
     * 公钥加密
     * @param str
     * @return
     * @throws Exception
     */
    public static String encodeByPublic(String str, String publicKeyStr) throws Exception{
    	//获取公钥
    	RSAPublicKey publicKey = HfBjjkRSAUtils.getPublicKey(publicKeyStr);
    	//公钥加密
    	String encodedData = HfBjjkRSAUtils.publicEncrypt(str, publicKey);
    	return encodedData;
    }
    
    /**
     * 生成秘钥
     * @author xunbd
     * @date 2018-4-11
     * @param num
     * @return
     */
    public static Map<String, String> getKeys(String num){
        Map<String, String> keyMap = HfBjjkRSAUtils.createKeys(Integer.parseInt(num));
        String  publicKey = keyMap.get("publicKey");
        String  privateKey = keyMap.get("privateKey");
        Map<String, String> result = new HashMap<String,String>();
        result.put("publicKey", publicKey);
        result.put("privateKey", privateKey);
        return result;
    }
    
    public static void main2(String[] args) throws Exception {
    	//公钥
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBmeVa_KHCbYL1fRczTI3eExX5uVv0-6fKxkVD0gkrMaFV5XDfbrhz4emgo8fmwWSnZlUyrtvPpZ6wg6MVlC98lIIH-6t1gyBKQlnlF8jIUEqvIgnvrP9dxWqy12elbsLAKHjGrckz6Ul9lK6CXNs7hMm2QbxZsdBWLJloxC_3aQIDAQAB";
    	
        //要加密的数据
        String json = "{\n" +
                "    \"count\": 1,\n" +
                "    \"rows\": [\n" +
                "      {\n" +
                "        \"op_date\": \"2019-09-09\",\n" +
                "        \"dept_code\": \"12\",\n" +
                "        \"name_woman\":\"123\",\n" +
                "        \"birth_woman\": \"1234\",\n" +
                "        \"cert_no\": \"12345\",\n" +
                "        \"cert_num_man\": \"123456\",\n" +
                "        \"cert_num_woman\":\"1234567\",\n" +
                "        \"birth_man\": \"12345678\",\n" +
                "        \"nation_woman\": \"123456789\",\n" +
                "        \"nation_man\": \"1234567899\",\n" +
                "        \"op_type\": \"987\",\n" +
                "        \"name_man\": \"9876中文\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }";
		//转换成json串的结果
		//调用加密方法
        String encodeByPublic=encodeByPublic(json,publicKey);
    	//响应数据公钥解密
    	/*String decodeByPublic = decodeByPublic(encodeByPrivate, publicKey);
    	System.err.println("公钥解密 : "+decodeByPublic);*/
    }

    
}
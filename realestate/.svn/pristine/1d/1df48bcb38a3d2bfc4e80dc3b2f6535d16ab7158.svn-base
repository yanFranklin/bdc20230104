package cn.gtmap.realestate.exchange.service.impl.inf.hefei;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcTsdjfxxResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.exchange.core.dto.hefei.jfpt.request.JfptTsDjfxxRequestPrePayInfoJson;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description 缴费平台相关服务
 */
@Service
public class JfptServiceImpl {

    public static final String SIGN = "sign";
    public static final String KEY = "key";

    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;

    @Autowired
    private BdcSlSfxmFeignService bdcSlSfxmFeignService;

    @Autowired
    private CommonService commonService;

    @Value("${jfpt.sysId:}")
    private String sysId;

    @Value("${jfpt.sysSecId:}")
    private String sysSecId;

    @Value("${jfpt.jianhang.aes.key:AHLifeCloud_041!}")
    private String cKey;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.String
     * @description 获取 加密后的 sysid
     */
    public String getSysSecId(){
        if(StringUtils.isNotBlank(sysSecId)){
            String date = DateUtil.formateTimeYmd(new Date());
            Map<String,Object> map = new HashMap();
            // 安全码+时间(yyyyMMdd)
            map.put("sysSecId",sysSecId + date);
            return getMD5Sign(map, new ArrayList());
        }
        return "";
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param sfxxid
     * @return cn.gtmap.realestate.exchange.core.dto.hefei.jfpt.request.JfptTsDjfxxRequestDTO
     * @description 构造请求实体  由于数据来源需要自己查，并且 存在一对多对照关系，所以直接用代码处理
     */
    public List<JfptTsDjfxxRequestPrePayInfoJson> getRequestParam(String sfxxid){
        if(StringUtils.isBlank(sfxxid)){
            return null;
        }
        JfptTsDjfxxRequestPrePayInfoJson tempInfo = new JfptTsDjfxxRequestPrePayInfoJson();
        // 1. 查询SFXX实体
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
        if(bdcSlSfxxDO == null){
            throw new AppException("收费信息为空");
        }
        if(bdcSlSfxxDO.getSfsj() != null){
            tempInfo.setCreateTime(DateUtil.formateTimeYmdhms(bdcSlSfxxDO.getSfsj()));
        }
        tempInfo.setPayor(bdcSlSfxxDO.getJfrxm());
        tempInfo.setSysId(sysId);
        tempInfo.setSysSecId(getSysSecId());

        // 2. 根据SFXX 查询 BDC_XM
        BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(bdcSlSfxxDO.getXmid());
        if(bdcXmDO == null){
            throw new AppException("无法关联到项目信息");
        }
        tempInfo.setAdminDiv(bdcXmDO.getDjjg());
        tempInfo.setItemCode(bdcXmDO.getSlbh());
        // 存放 代码 有dozer处理 字典转换
        tempInfo.setItemName(bdcXmDO.getDjxl());
        tempInfo.setPayAreaCode(bdcXmDO.getQxdm());
        // 执收单位
        tempInfo.setPayDeptCode(bdcXmDO.getQxdm());
        tempInfo.setPayDeptName(bdcXmDO.getQxdm());


        // 3. 根据BDC_XM 和 SFXX QLRLB 查询 QLRZJH
        List<BdcQlrDO> bdcQlrDOList = commonService.listBdcQlrByXmid(bdcXmDO.getXmid(),bdcSlSfxxDO.getQlrlb());
        if(CollectionUtils.isEmpty(bdcQlrDOList)){
            throw new AppException("无法关联到权利人信息");
        }
        String qlrzjhs = "";
        for(BdcQlrDO bdcQlrDO: bdcQlrDOList){
            qlrzjhs += bdcQlrDO.getZjh()+",";
        }
        if(qlrzjhs.endsWith(",")){
            qlrzjhs = qlrzjhs.substring(0,qlrzjhs.length()-1);
        }
        tempInfo.setPayorIdNum(qlrzjhs);

        List<BdcSlSfxmDO> sfxmDOList = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(sfxxid);
        if(CollectionUtils.isEmpty(sfxmDOList)){
            throw new AppException("收费明细列表为空");
        }
        List<JfptTsDjfxxRequestPrePayInfoJson> list = new ArrayList<>();
        for(BdcSlSfxmDO slSfxmDO : sfxmDOList){
            // 收费不为0 的 项目才推送
            if(slSfxmDO.getYsje()!=null && slSfxmDO.getYsje() > 0){
                JfptTsDjfxxRequestPrePayInfoJson payInfo = new JfptTsDjfxxRequestPrePayInfoJson();
                BeanUtils.copyProperties(tempInfo,payInfo);
                payInfo.setExempt(slSfxmDO.getJmje()!=null?slSfxmDO.getJmje().toString():"0");
                payInfo.setItemNum(slSfxmDO.getSl()!=null?slSfxmDO.getSl().toString():"0");
                payInfo.setMoney(slSfxmDO.getYsje().toString());
                payInfo.setBillAmount(slSfxmDO.getYsje().toString());
                payInfo.setPayItemCode(slSfxmDO.getSfxmbz());
                payInfo.setPayItemName(slSfxmDO.getSfxmbz());
                payInfo.setSourceId(slSfxmDO.getSfxmid());
                payInfo.setStdFee(slSfxmDO.getSfxmbz());
                payInfo.setOverdueFine("0");
                list.add(payInfo);
            }
        }
        return list;
    }

    private static final String JIAN_HANG_QR_CODE_SEPARATOR = "^^";

    private static final String JIAN_HANG_QR_CODE_ORDER_SEPARATOR = "#";

    private static final String JIAN_HANG_QR_CODE_ORDER_INSIDE_SEPARATOR = "|";

    @Value("${bengbu.jianhang.areacode:340300}")
    private String jhQRCodeAreacode;

    @Value("${bengbu.jianhang.deptcode:07519}")
    private String jhQRCodeDeptcode;

    @Value("${bengbu.jianhang.deptName:蚌埠市不动产登记中心}")
    private String jhQRCodeDeptName;

    @Value("${bengbu.jianhang.qrcode.url:https://www.52longka.com/zrzf/tra/payment?data=}")
    private String jhQRCodeUrl;

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param sfxxid
     * @description 构造请求实体  由于数据来源需要自己查，并且 存在一对多对照关系，所以直接用代码处理
     */
    public BdcTsdjfxxResponseDTO createQRCodeUrl(String sfxxid) throws Exception {
        if(StringUtils.isBlank(sfxxid)){
            return null;
        }
        // 1. 查询SFXX实体
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
        if(bdcSlSfxxDO == null){
            throw new AppException("收费信息为空");
        }
        // 2. 根据SFXX 查询 BDC_XM
        BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(bdcSlSfxxDO.getXmid());
        if(bdcXmDO == null){
            throw new AppException("无法关联到项目信息");
        }
//        缴款人身份证号码：当前业务的权利人证件号码，
        List<BdcQlrDO> bdcQlrDOList = commonService.listBdcQlrByXmid(bdcXmDO.getXmid(),bdcSlSfxxDO.getQlrlb());
        if(CollectionUtils.isEmpty(bdcQlrDOList)){
            throw new AppException("无法关联到权利人信息");
        }
        String qlrzjhs = "";
        for(BdcQlrDO bdcQlrDO: bdcQlrDOList){
            qlrzjhs += bdcQlrDO.getZjh()+",";
        }
        if(qlrzjhs.endsWith(",")){
            qlrzjhs = qlrzjhs.substring(0,qlrzjhs.length()-1);
        }
        StringBuilder stringBuilder = new StringBuilder();
//        区划代码：340300，
        stringBuilder.append(jhQRCodeAreacode).append(JIAN_HANG_QR_CODE_SEPARATOR)
                .append(jhQRCodeDeptcode).append(JIAN_HANG_QR_CODE_SEPARATOR)
                .append(jhQRCodeDeptName).append(JIAN_HANG_QR_CODE_SEPARATOR)
                .append(bdcSlSfxxDO.getJfrxm()).append(JIAN_HANG_QR_CODE_SEPARATOR)
                .append(qlrzjhs);
//        项目编码、项目名称：1041501	住宅类不动产登记费；1041503	不动产权属证书工本费；1041502	非住宅类不动产登记费，
        List<BdcSlSfxmDO> sfxmDOList = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(sfxxid);
        if(CollectionUtils.isEmpty(sfxmDOList)){
            throw new AppException("收费明细列表为空");
        }
        List<JfptTsDjfxxRequestPrePayInfoJson> list = new ArrayList<>();
        int count = 0;
        for(BdcSlSfxmDO slSfxmDO : sfxmDOList){
            // 收费不为0 的 项目才推送
            if(slSfxmDO.getYsje()!=null && slSfxmDO.getYsje() > 0){
                if (count == 0){
                    count++;
                    stringBuilder.append(JIAN_HANG_QR_CODE_SEPARATOR);
                }
                stringBuilder.append(slSfxmDO.getSfxmbz()).append(JIAN_HANG_QR_CODE_ORDER_INSIDE_SEPARATOR)
                        .append(slSfxmDO.getSfxmbz()).append(JIAN_HANG_QR_CODE_ORDER_INSIDE_SEPARATOR)
                        .append(slSfxmDO.getSsje()).append(JIAN_HANG_QR_CODE_ORDER_SEPARATOR);
            }
        }
        String qrCodeUrl = stringBuilder.toString();
        if (count!=0){
            qrCodeUrl = qrCodeUrl.substring(0, qrCodeUrl.length() - 1);
        }
//      渠道代码：1
        qrCodeUrl = qrCodeUrl + JIAN_HANG_QR_CODE_SEPARATOR + "1";
        String encrypt = encrypt(qrCodeUrl, cKey);
        BdcTsdjfxxResponseDTO bdcTsdjfxxResponseDTO = new BdcTsdjfxxResponseDTO();
        if (StringUtils.isNotBlank(encrypt)){
            bdcTsdjfxxResponseDTO.setJfsewmurl(jhQRCodeUrl+encrypt);
        }
        return bdcTsdjfxxResponseDTO;
    }

    /**
     * 建行提供的加密方法
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        return Base64Utils.encodeByteToBase64Str(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    /**
     * 建行提供的解密方法
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64Utils.decodeBase64StrToByte(sSrc);// 先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }



    /**
     * MD5校验算法，去除exclude key根据key排序，然后添加秘钥key校验 验证结果key在object中，key为sign
     *
     * @param params
     *            约定秘钥
     * @param excludeKeyList
     *            不参与校验的key
     *
     * */
    public static boolean checkMD5Sign(Map<String, Object> params,  List<String> excludeKeyList) {
        if (null == params || !params.containsKey(SIGN)) {
            return false;
        }
        String sign = params.get(SIGN).toString();
        String checkStr = getOrderedCheckStr(params, excludeKeyList);
        String md5EncodeStr = md5Encode(checkStr);
        boolean isCheckOk = false;
        if (md5EncodeStr.equals(sign)) {
            isCheckOk = true;
        }
        return isCheckOk;
    }

    /**
     * MD5校验算法，去除exclude key根据key排序，然后添加秘钥key校验 验证结果key在object中，key为sign
     *
     * @param params
     * @param excludeKeyList
     *            不参与校验的key
     *
     * */
    public static String getMD5Sign(Map<String, Object> params,List<String> excludeKeyList) {
        if (null == params) {
            return StringUtils.EMPTY;
        }
        String checkStr = getOrderedCheckStr(params, excludeKeyList);
        String md5EncodeStr = md5Encode(checkStr);
        return md5EncodeStr;
    }

    private static String getOrderedCheckStr(Map<String, Object> params, List<String> excludeKeyList) {
        TreeSet<String> keySet = new TreeSet<String>();
        for (String objectKey : params.keySet()) {
            keySet.add(objectKey);
        }

        Iterator<String> keyIterator = keySet.iterator();
        String keyTmp = "";
        StringBuilder checkStrBuilder = new StringBuilder();
        while (keyIterator.hasNext()) {
            keyTmp = keyIterator.next();
            Object valTmp = params.get(keyTmp);
            if (valTmp instanceof String) {
                String tmp = (String) valTmp;
                if (StringUtils.isEmpty(tmp)) {
                    continue;
                }
            }
            if (excludeKeyList.contains(keyTmp)) {
                continue;
            }
            checkStrBuilder.append(keyTmp).append("=").append(params.get(keyTmp)).append("&");
        }

        String checkStr = checkStrBuilder.toString();
        if (StringUtils.isNotBlank(checkStr)) {
            checkStr = checkStr.substring(0, checkStr.length() - 1);
        }

        return checkStr;
    }

    private final static String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };

    /**
     * 转换字节数组为16进制字串
     *
     * @param b
     *            字节数组
     * @return 16进制字串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    /**
     * md5编码，返回编码为均为大写编码字符
     *
     * @param origin
     *
     * */
    private static String md5Encode(String origin) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
        }
        return resultString.toUpperCase();
    }


}

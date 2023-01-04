package cn.gtmap.realestate.exchange.service.impl.nantong;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.fcjyhtxx.zlfhtxxByzjh.HtxxContractDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fcjyhtxx.zlfhtxxByzjh.HtxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.util.HttpClientUtils;
import cn.gtmap.realestate.exchange.util.Md5Util;
import cn.gtmap.realestate.exchange.util.des.ZsFwhtxxDesUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/12/7
 * @description 智数信息 - 南通市通州区房屋综合管理平台 相关接口处理
 */
@Service(value = "zsFwhttqServiceImpl")
public class ZsFwhttqServiceImpl {
    private static Logger LOGGER = LoggerFactory.getLogger(ZsFwhttqServiceImpl.class);

    @Autowired
    private HttpClientUtils httpClientUtils;
    /**
     * 智数信息-appkey
     */
    @Value("${zsxx.appkey:}")
    private String zsxxAppkey;

    /**
     * 大数据共享平台-appkey
     */
    @Value("${dsj.appkey:}")
    private String dsjAppkey;

    /**
     * 智数信息-appid
     */
    @Value("${zsxx.appid:}")
    private String zsxxAppid;

    /**
     * 2.2.	增量房合同提取—证件号接口地址
     */
    @Value("${zsxx.zlf.htxxbyzjhUrl:}")
    private String getIncrementContactinfoIDUrl;

    /**
     * 2.4.	增量房合同提取—证件号接口地址
     */
    @Value("${zsxx.clf.htxxbyzjhUrl:}")
    private String getStockContactinfoIDUrl;

    /**
     * 2.2.	增量房合同提取—证件号
     *
     * @param
     * @return
     * @Date 2022/12/7
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Object getIncrementContactinfoID(JSONObject paramObj) {
        if (Objects.isNull(paramObj) || StringUtils.isBlank(paramObj.getString("idno"))
                || StringUtils.isBlank(paramObj.getString("name")) || StringUtils.isBlank(getIncrementContactinfoIDUrl)) {
            throw new MissingArgumentException("参数或url地址为空！请检查参数和配置项zsxx.zlf.htxxbyzjhUrl");
        }
        LOGGER.info("2.2.增量房合同提取—证件号接口入参：{}", paramObj.toString());
       /* long timeStamp = System.currentTimeMillis(); //当前时间时间戳(单位: 毫秒)
        // MD5Utils.MD5Encode
        String signStr = "";
        try {
            signStr = Md5Util.MD5Encode(zsxxAppid + zsxxAppkey + timeStamp,"UTF-8");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Map<String, Object> header = new HashMap<>();
        header.put("appid",zsxxAppid);
        header.put("timeStamp", timeStamp);
        header.put("signStr", signStr);*/
        JSONObject data1 = new JSONObject();
        Map<String, Object> data = new HashMap<>();
        try {
            data.put("idno", ZsFwhtxxDesUtils.encrypt(paramObj.getString("idno")));
            data.put("name", ZsFwhtxxDesUtils.encrypt(paramObj.getString("name")));

        } catch (Exception e) {
            LOGGER.info("参数加密失败！");
            e.printStackTrace();
        }
        Map<String, Object> body = new JSONObject();
        body.put("body", JSONObject.toJSONString(data));
        return getHtxxResponse(new HashMap<>(), body, getIncrementContactinfoIDUrl);
    }

    /**
     * 2.4.	存量房合同提取—证件号
     *
     * @param
     * @return
     * @Date 2022/12/7
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Object getStockContactinfoID(JSONObject paramObj) {
        if (Objects.isNull(paramObj) || StringUtils.isBlank(paramObj.getString("idno"))
                || StringUtils.isBlank(paramObj.getString("name")) || StringUtils.isBlank(getStockContactinfoIDUrl)) {
            throw new MissingArgumentException("参数或url地址为空！请检查参数和配置项zsxx.clf.htxxbyzjhUrl");
        }
        LOGGER.info("2.4.存量房合同提取—证件号接口入参：{}", paramObj.toString());
       /* long timeStamp = System.currentTimeMillis(); //当前时间时间戳(单位: 毫秒)
        // MD5Utils.MD5Encode
        String signStr = "";
        try {
            signStr = Md5Util.MD5Encode(zsxxAppid + zsxxAppkey + timeStamp,"UTF-8");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Map<String, Object> header = new HashMap<>();
        header.put("appid",zsxxAppid);
        header.put("timeStamp", timeStamp);
        header.put("signStr", signStr);*/
        JSONObject data1 = new JSONObject();
        Map<String, Object> data = new JSONObject();
        try {
            data.put("idno", ZsFwhtxxDesUtils.encrypt(paramObj.getString("idno")));
            data.put("name", ZsFwhtxxDesUtils.encrypt(paramObj.getString("name")));

        } catch (Exception e) {
            LOGGER.info("参数加密失败！");
            e.printStackTrace();
        }
        Map<String, Object> body = new JSONObject();
        body.put("body", JSONObject.toJSONString(data));
        return getHtxxResponse(new HashMap<>(), body, getStockContactinfoIDUrl);
    }

    @NotNull
    public Object getHtxxResponse(Map<String, Object> header, Map<String, Object> data, String url) {
        if (Objects.isNull(header) || header.isEmpty()) {
            long timeStamp = System.currentTimeMillis(); //当前时间时间戳(单位: 毫秒)
            // MD5Utils.MD5Encode
            String signStr = "";
            try {
                signStr = Md5Util.MD5Encode(zsxxAppid + zsxxAppkey + timeStamp, "UTF-8");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            header.put("appid", zsxxAppid);
            header.put("timeStamp", timeStamp);
            header.put("signStr", signStr);
            header.put("appKey", dsjAppkey);
        }

        String response = httpClientUtils.sendPostFormHeaderRequest(url, data,
                "增量房合同提取—证件号", header);

        //name -> 89af7ce03e3bd6cf
        //idno -> 079461ca9b8bb73c9d1a0aba776a843f20f378814f11222f
//       String response = "{\"code\":200,\"data\":\"{\\\"msg\\\":\\\"成功\\\",\\\"result\\\":{\\\"contractList\\\":[{\\\"contractId\\\":\\\"7B9AF9e5A176CE06bD2ED87bf8EF9Ff2\\\",\\\"contractno\\\":\\\"63122121400130\\\",\\\"contractRecordNo\\\":null,\\\"reviewTime\\\":\\\"20221220105742\\\",\\\"commitTime\\\":\\\"20221220103716\\\",\\\"contractprice\\\":\\\"1318000\\\",\\\"contractstatus\\\":\\\"合同已备案 \\\",\\\"buyerList\\\":[{\\\"buyerName\\\":\\\"何玉娇\\\",\\\"idType\\\":\\\"居民身份证\\\",\\\"buyerNo\\\":\\\"320683199410241720\\\",\\\"national\\\":\\\"中国\\\",\\\"vendorTel\\\":\\\"18818071028\\\",\\\"address\\\":\\\"江苏省南通市通州区二甲镇坨墩十六组32号\\\",\\\"postalCode\\\":\\\"226321\\\",\\\"agentType\\\":\\\"委托代理人\\\",\\\"agentName\\\":\\\"何梦娇\\\",\\\"agentIdTpye\\\":\\\"居民身份证\\\",\\\"agentIdno\\\":\\\"320683199009121722\\\",\\\"agentTel\\\":\\\"18818071028\\\"}],\\\"vendorList\\\":[{\\\"vendorName\\\":\\\"张益\\\",\\\"idType\\\":\\\"居民身份证\\\",\\\"vendorNo\\\":\\\"32062419740523456X\\\",\\\"national\\\":\\\"中国\\\",\\\"vendorTel\\\":\\\"18068602889\\\",\\\"address\\\":\\\"江苏省南通市通州区骑岸镇东场村二十七组104号\\\",\\\"postalCode\\\":\\\"226343\\\",\\\"agentType\\\":\\\"委托代理人\\\",\\\"agentName\\\":null,\\\"agentIdTpye\\\":null,\\\"agentIdno\\\":null,\\\"agentTel\\\":null}],\\\"houseList\\\":[{\\\"houseCode\\\":null,\\\"projectName\\\":\\\"金沙镇世纪大道666号金色城品花苑\\\",\\\"buildingNo\\\":\\\"12\\\",\\\"houseNo\\\":\\\"1502\\\",\\\"position\\\":\\\"南通市通州区（县）金新街道金沙镇世纪大道666号金色城品花苑小区12栋1单元1502号（室）\\\",\\\"houseUse\\\":\\\"住宅\\\",\\\"houseUseInput\\\":null,\\\"buildingStructure\\\":\\\"混合\\\",\\\"houseOrientation\\\":\\\"南\\\",\\\"height\\\":null,\\\"floor\\\":\\\"15\\\",\\\"totalFloors\\\":\\\"19\\\",\\\"buildArea\\\":84.84,\\\"innerArea\\\":null,\\\"sharedArea\\\":null,\\\"tradeArea\\\":null,\\\"landGetWay\\\":\\\"出让\\\",\\\"landUse\\\":\\\"城镇住宅用地\\\",\\\"landUsageTerm\\\":\\\"20800414000000\\\",\\\"oldprcno\\\":null,\\\"realestateNo\\\":null,\\\"housePrice\\\":\\\"1318000\\\",\\\"unitPrice\\\":null}],\\\"fileList\\\":[{\\\"fileType\\\":\\\".jpeg\\\",\\\"fileName\\\":\\\"1671503718413_WechatIMG7064.jpeg\\\",\\\"filePath\\\":\\\"/home/file-server/zsbase/300101/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/94b452920f6d41e7aa4e59f091d6305e\\\"},{\\\"fileType\\\":\\\".jpeg\\\",\\\"fileName\\\":\\\"1671497312498_WechatIMG7055.jpeg\\\",\\\"filePath\\\":\\\"/home/file-server/zsbase/300101/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/b93532bf392f4e64bfadd85a219043e7\\\"},{\\\"fileType\\\":\\\".jpeg\\\",\\\"fileName\\\":\\\"1671497304576_WechatIMG7053.jpeg\\\",\\\"filePath\\\":\\\"/home/file-server/zsbase/300101/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/854c7346bc71493b891982491b5034f5\\\"},{\\\"fileType\\\":\\\".jpeg\\\",\\\"fileName\\\":\\\"1671503780288_WechatIMG7069.jpeg\\\",\\\"filePath\\\":\\\"/home/file-server/zsbase/300101/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/854c7346bc71493b891982491b5034f5\\\"},{\\\"fileType\\\":\\\".jpeg\\\",\\\"fileName\\\":\\\"1671497295998_WechatIMG7054.jpeg\\\",\\\"filePath\\\":\\\"/home/file-server/zsbase/300101/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/67126571596d4f599f84e1c538410660\\\"},{\\\"fileType\\\":\\\".jpeg\\\",\\\"fileName\\\":\\\"1671497312513_WechatIMG7056.jpeg\\\",\\\"filePath\\\":\\\"/home/file-server/zsbase/300101/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/b93532bf392f4e64bfadd85a219043e7\\\"},{\\\"fileType\\\":\\\".jpeg\\\",\\\"fileName\\\":\\\"1671497248670_WechatIMG6895.jpeg\\\",\\\"filePath\\\":\\\"/home/file-server/zsbase/300101/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/854c7346bc71493b891982491b5034f5\\\"},{\\\"fileType\\\":\\\".pdf\\\",\\\"fileName\\\":\\\"1671505347784_1671505340496.pdf\\\",\\\"filePath\\\":\\\"/home/file-server/dfmh/printDir/300101/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2\\\"},{\\\"fileType\\\":\\\".jpeg\\\",\\\"fileName\\\":\\\"1671497248654_WechatIMG6894.jpeg\\\",\\\"filePath\\\":\\\"/home/file-server/zsbase/300101/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/854c7346bc71493b891982491b5034f5\\\"},{\\\"fileType\\\":\\\".jpeg\\\",\\\"fileName\\\":\\\"1671503723773_WechatIMG7065.jpeg\\\",\\\"filePath\\\":\\\"/home/file-server/zsbase/300101/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/94b452920f6d41e7aa4e59f091d6305e\\\"},{\\\"fileType\\\":\\\".jpeg\\\",\\\"fileName\\\":\\\"1671497288060_WechatIMG7052.jpeg\\\",\\\"filePath\\\":\\\"/home/file-server/zsbase/300101/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/67126571596d4f599f84e1c538410660\\\"},{\\\"fileType\\\":\\\".jpeg\\\",\\\"fileName\\\":\\\"1671503775741_WechatIMG7068.jpeg\\\",\\\"filePath\\\":\\\"/home/file-server/zsbase/300101/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/854c7346bc71493b891982491b5034f5\\\"},{\\\"fileType\\\":\\\".jpeg\\\",\\\"fileName\\\":\\\"1671503728710_WechatIMG7066.jpeg\\\",\\\"filePath\\\":\\\"/home/file-server/zsbase/300101/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/94b452920f6d41e7aa4e59f091d6305e\\\"},{\\\"fileType\\\":\\\".jpeg\\\",\\\"fileName\\\":\\\"1671503785085_WechatIMG7070.jpeg\\\",\\\"filePath\\\":\\\"/home/file-server/zsbase/300101/7B9AF9e5A176CE06bD2ED87bf8EF9Ff2/854c7346bc71493b891982491b5034f5\\\"}]}]},\\\"code\\\":\\\"0000\\\"}\",\"message\":\"\"}";
        JSONObject responseData = JSONObject.parseObject(response);
//        JSONObject responseData = JSONObject.parseObject(response1);
        if (Objects.isNull(response)) {
            throw new AppException("智数接口无返回！");
        }
        if (CommonConstantUtils.RESPONSE_SUCCESS.equals(responseData.getString("code"))) {
            return responseData.getJSONObject("data");
        }
        return responseData;
       /* //解密
        JSONObject decrypt = new JSONObject();
        JSONArray resultArr = new JSONArray();
        if (CommonConstantUtils.SUCCESS_CODE_0000.equals(responseData.getString("code"))) {
            decrypt.put("code", responseData.getString("code"));
            decrypt.put("msg", responseData.getString("msg"));
            JSONArray results = JSONObject.parseArray(responseData.getString("results"));
            if (Objects.nonNull(results)) {
                for (int i = 0; i < results.size(); i++) {
                    *//**
         *  四个接口，存在返回结构不一致的情况，2.2接口多一层
         *  下面开始反射解密，判断当前字段对象类型，如果为list，则进入下一层继续循环判断
         *//*
                    if (results.getJSONObject(i).containsKey("contractList")) {
                        HtxxContractDTO htxxContractDTO = JSONObject.parseObject(JSONObject.toJSONString(results.get(i)), HtxxContractDTO.class);
                        if (Objects.nonNull(htxxContractDTO) && CollectionUtils.isNotEmpty(htxxContractDTO.getContractList())) {
                            for (HtxxDTO zlfHtxxDTO : htxxContractDTO.getContractList()) {
                                //对单个zlfHtxxDTO对象进行反射解密
                                decryptHtxx(zlfHtxxDTO);
                            }
                        }
                        LOGGER.info(htxxContractDTO.toString());
                        resultArr.add(htxxContractDTO);
                    } else {
                        HtxxDTO htxxDTO = JSONObject.parseObject(JSONObject.toJSONString(results.get(i)), HtxxDTO.class);
                        if (Objects.nonNull(htxxDTO)) {
                            decryptHtxx(htxxDTO);

                        }
                        LOGGER.info(htxxDTO.toString());
                        resultArr.add(htxxDTO);
                    }
                }
            }
            decrypt.put("results", resultArr);
        } else {
            decrypt.put("code", responseData.getString("code"));
            decrypt.put("msg", responseData.getString("msg"));
        }
        LOGGER.info("解密后数据：{}", decrypt.toString());
        return decrypt;*/
    }


    /**
     * 反射对象所有字段进行解密
     *
     * @param zlfHtxxDTO
     * @return
     * @Date 2022/12/7
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public void decryptHtxx(HtxxDTO zlfHtxxDTO) {
        //最外层字段获取
        Field[] fields = zlfHtxxDTO.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                //判断是否是list对象
                if (field.getType() == List.class) {
                    //外层对象判断，buyerList，fileList，houseList，vendorList，是不是list,如果是，则反射解密
                    for (Object o : (List) field.get(zlfHtxxDTO)) {
                        Field[] oFiles = o.getClass().getDeclaredFields();
                        for (Field os : oFiles) {
                            os.setAccessible(true);
                            if (os.getType() == List.class) {
                                //vendorList和buyerList里面的字段对象是否是list，主要针对agent，如果是，则继续反射解密
                                for (Object k : (List) os.get(o)) {
                                    Field[] kFiles = k.getClass().getDeclaredFields();
                                    for (Field ks : kFiles) {
                                        ks.setAccessible(true);
                                        ks.setAccessible(true);
                                        ks.set(k, StringUtils.isNotBlank(String.valueOf(ks.get(k))) ? ZsFwhtxxDesUtils.decrypt(ks.get(k).toString()) : "");
                                    }
                                }
                            } else {
                                os.setAccessible(true);
                                os.set(o, StringUtils.isNotBlank(String.valueOf(os.get(o))) ? ZsFwhtxxDesUtils.decrypt(os.get(o).toString()) : "");
                            }

                        }
                    }
                } else {
                    field.set(zlfHtxxDTO, StringUtils.isNotBlank(String.valueOf(field.get(zlfHtxxDTO))) ? ZsFwhtxxDesUtils.decrypt(field.get(zlfHtxxDTO).toString()) : "");
                }
            }
        } catch (Exception e) {
            LOGGER.error("反射解密失败！：{}", e);
            e.printStackTrace();
        }
    }

}

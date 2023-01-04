package cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.factory;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.config.SdqConfig;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.qi.*;
import cn.gtmap.realestate.exchange.util.ASCIIUtil;
import cn.gtmap.realestate.exchange.util.Md5Util;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
/**
 * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description //构建气相关的参数工厂
 * @Date 2022/5/26 9:30
 **/
public class QiFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(QiFactory.class);


    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //pushStyle为1, 只推送权利人，只推送户主 的QiGhxxDTO构建
     * @Date 2022/5/26 9:04
     **/
    public static SqGhxxRequest buildGhxxParam(BdcSdqghDO bdcSdqghDO,
                                    BdcXmDO bdcXmDO,
                                    BdcQlrDO selectiveQlr,
                                    Map<String, List<ClnrDTO>> material,
                                               String ywlx) {
        SqGhxxRequest req = new SqGhxxRequest();
        req.setConsno(bdcSdqghDO.getConsno());
        req.setAddress(bdcXmDO.getZl());
        req.setBdcqzh(bdcXmDO.getBdcqzh());
        req.setNewusername(selectiveQlr.getQlrmc());
        req.setNewusercard(selectiveQlr.getZjh());
        req.setNewzjzl(selectiveQlr.getZjzl().toString());
        req.setNewmobile(selectiveQlr.getDh());
        req.setNewtelephon(selectiveQlr.getDh());
        req.setDwdm(bdcSdqghDO.getRqfwbsm());
        req.setQxdm(bdcXmDO.getQxdm());
        req.setYwlx(ywlx);
        req.setJklx("gh");
        if (Objects.nonNull(material)) {
            Set<String> clmc = material.keySet();
            List<FjxxDTO> fjxxDTOList = clmc.stream().map(item -> {
                List<ClnrDTO> list = material.get(item);
                FjxxDTO fjxxDTO = new FjxxDTO();
                fjxxDTO.setClmc(item);
                fjxxDTO.setClnr(CollectionUtils.isEmpty(list) ? new ArrayList<>() : list);
                return fjxxDTO;
            }).collect(Collectors.toList());
            req.setFjxx(fjxxDTOList);
        } else {
            req.setFjxx(new ArrayList<>());
        }
        return req;
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //pushStyle为5, 海门的QiGhjcHmDto构建
     * @Date 2022/5/26 9:04
     **/
    public static QiGhjcHmDto buildGhjcHmParam(BdcSdqghDO bdcSdqghDO,
                                        SdqConfig.UnitInfo config) {
        QiGhjcHmDto req = new QiGhjcHmDto();
//        req.setTime(new Date());
        req.setUserCode(bdcSdqghDO.getConsno());
        req.setMdmCode(config.getMdmcode());
//        req.setSelfManufacture(config.getSelfManufacture());
        StringBuffer stringA = new StringBuffer();
        Map<String, Object> paramMap = JSON.parseObject(JSON.toJSONString(req), new TypeReference<Map<String, Object>>() {
        });
        paramMap = CollectionUtil.sort(paramMap, Comparator.comparing(ASCIIUtil::getASCII));
        Set<Map.Entry<String, Object>> keys = paramMap.entrySet();
        for (Map.Entry<String, Object> key : keys) {
            if (Objects.nonNull(key.getValue())) {
                stringA.append(key.getKey()).append("=").append(key.getValue().toString()).append("&");
            }
        }
        stringA.deleteCharAt(stringA.length() - 1);
        StringBuffer signSb = new StringBuffer()
                .append("stringA=")
                .append(stringA)
                .append("&key=")
//                .append(config.getKey())
                ;
        LOGGER.info("加密前的参数为{}", signSb);
        String sign = Md5Util.getMd5To32(signSb.toString(), "").toUpperCase();
//        req.setSign(sign);
        return req;
    }
    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //pushStyle为5, 海门的QiGhHmDto构建
     * @Date 2022/5/26 9:04
     **/
    public static QiGhHmDto buildGhHmParam(BdcSdqghDO bdcSdqghDO,
                                           BdcQlrDO selectiveYwr,
                                           BdcQlrDO selectiveQlr,
                                           byte[] sfgImg,
                                           byte[] bdczImg,
                                           SdqConfig.UnitInfo config){
        QiGhHmDto req = new QiGhHmDto();
        req.setUserCode(bdcSdqghDO.getConsno());
        req.setMdmCode(config.getMdmcode());
        req.setReceiveFrom(config.getReceiveFrom());
        req.setUserCode(bdcSdqghDO.getConsno());
        req.setUserName(selectiveYwr.getQlrmc());
        req.setUserPhone(selectiveYwr.getDh());
        req.setUserCertificateNum(selectiveYwr.getZjh());
        req.setNewUserName(selectiveQlr.getQlrmc());
        req.setNewUserCertificateType(selectiveQlr.getZjzl());
        req.setNewUserCertificateNum(selectiveQlr.getZjh());
        req.setNewUserPhone(selectiveQlr.getDh());
        req.setSfgImg(sfgImg);
        req.setBdczImg(bdczImg);
        req.setNewUserCertificateType(selectiveQlr.getZjzl());
//        StringBuffer stringA = new StringBuffer();
//        Map<String, Object> paramMap = JSON.parseObject(JSON.toJSONString(req), new TypeReference<Map<String, Object>>() {
//        });
//        paramMap = CollectionUtil.sort(paramMap, Comparator.comparing(ASCIIUtil::getASCII));
//        Set<Map.Entry<String, Object>> keys = paramMap.entrySet();
//        for (Map.Entry<String, Object> key : keys) {
//            if (Objects.nonNull(key.getValue())) {
//                stringA.append(key.getKey()).append("=").append(key.getValue().toString()).append("&");
//            }
//        }
//        stringA.deleteCharAt(stringA.length() - 1);
//        StringBuffer signSb = new StringBuffer()
//                .append("stringA=")
//                .append(stringA)
//                .append("&key=")
//                .append(config.getKey())
//                ;
//        LOGGER.info("加密前的参数为{}", signSb);
//        String sign = Md5Util.getMd5To32(signSb.toString(), "")
//                .toUpperCase();
//        req.setSign(sign);
        return req;
    }
    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //pushStyle为4, 权利人、义务人均推送，多个  的QiGhxxZjDTO构建
     * @Date 2022/5/26 9:04
     **/
    public static QiGhxxZjDTO buildGhxxZjParam(BdcSdqghDO bdcSdqghDO,
                                        BdcXmDO bdcXmDO,
                                        QiGhxxZjDTO.QiFileInfo fileInfo,
                                        List<BdcQlrDO> allQrl,
                                        List<BdcQlrDO> allYwl) {
        QiGhxxZjDTO req = new QiGhxxZjDTO();
        req.setConsno(bdcSdqghDO.getConsno());
        req.setAddress(bdcXmDO.getZl());
        List<QiGhxxZjDTO.QiNewUser> newUserList = allQrl.stream().map(item -> {
            QiGhxxZjDTO.QiNewUser qiNewUser = new QiGhxxZjDTO.QiNewUser();
            qiNewUser.setNewUserName(item.getQlrmc());
            qiNewUser.setNewUserCard(item.getZjh());
            qiNewUser.setNewMobile(item.getDh());
            return qiNewUser;
        }).collect(Collectors.toList());
        List<QiGhxxZjDTO.QiOriginalUser> originalUserList = allYwl.stream().map(item -> {
            QiGhxxZjDTO.QiOriginalUser originalUser = new QiGhxxZjDTO.QiOriginalUser();
            originalUser.setOriginalUserName(item.getQlrmc());
            originalUser.setOriginalUserCard(item.getZjh());
            originalUser.setOldMobile(item.getDh());
            return originalUser;
        }).collect(Collectors.toList());
        req.setNewUserList(newUserList);
        req.setOriginalUserList(originalUserList);
        List<QiGhxxZjDTO.QiFileInfo> fileInfoList = new ArrayList<>();
        fileInfoList.add(fileInfo);
        req.setData(fileInfoList);
        return req;
    }

    public static QiGhHmlsTokenDto buildGhHmlsToken(SdqConfig.UnitInfo config) {
        QiGhHmlsTokenDto authToken = new QiGhHmlsTokenDto();
        authToken.setClientId(config.getClientId());
        authToken.setSecret(config.getSecret());
        return authToken;
    }

    public static QiGhHmTokenDto buildGhHmToken(String authToken, String mobile) {
        QiGhHmTokenDto token = new QiGhHmTokenDto();
        token.setAuthToken(authToken);
        token.setMobile(mobile);
        return token;
    }
}

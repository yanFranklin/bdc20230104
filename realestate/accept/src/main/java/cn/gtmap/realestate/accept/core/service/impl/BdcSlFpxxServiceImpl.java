package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.accept.core.mapper.BdcSlFpxxMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlFpxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxxService;
import cn.gtmap.realestate.accept.service.BdcSfService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFpxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.JfsxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.fs.FsczfpxxRquestDTO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/5/11
 * @description ????????????????????????????????????
 */
@Service
public class BdcSlFpxxServiceImpl implements BdcSlFpxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlFpxxServiceImpl.class);

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcSlSfxxService bdcSlSfxxService;
    @Autowired
    BdcSfService bdcSfService;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcSlFpxxMapper bdcSlFpxxMapper;


    @Override
    public List<BdcSlFpxxDO> queryBdcSlFpxxBySfxxid(String sfxxid) {
        if(StringUtils.isBlank(sfxxid)){
            throw new AppException("????????????????????????ID");
        }
        Example example = new Example(BdcSlFpxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sfxxid", sfxxid);
        return entityMapper.selectByExample(example);
    }

    @Override
    public BdcSlFpxxDO insertBdcSlFpxx(BdcSlFpxxDO bdcSlFpxxDO) {
        if (bdcSlFpxxDO != null) {
            if (StringUtils.isBlank(bdcSlFpxxDO.getFpxxid())) {
                bdcSlFpxxDO.setFpxxid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlFpxxDO);
        }
        return bdcSlFpxxDO;
    }

    @Override
    public void deleteBdcSlFpxxBySfxxid(String sfxxid) {
        if(StringUtils.isNotBlank(sfxxid)){
            Example example = new Example(BdcSlFpxxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("sfxxid", sfxxid);
            entityMapper.deleteByExample(example);
        }
    }

    @Override
    public Integer saveOrUpdateBdcSlFpxx(BdcSlFpxxDO bdcSlFpxxDO) {
        int result;
        if (bdcSlFpxxDO != null) {
            if(StringUtils.isBlank(bdcSlFpxxDO.getFpxxid())){
                bdcSlFpxxDO.setFpxxid(UUIDGenerator.generate16());
            }
            result = entityMapper.saveOrUpdate(bdcSlFpxxDO,bdcSlFpxxDO.getFpxxid());
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;
    }

    @Override
    public void getFpxxAndUploadFpxx(String sfxxid, String gzlslid) {
        if(StringUtils.isAnyBlank(sfxxid, gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "????????????????????????ID??????????????????ID");
        }
        Map<String, String> param = new HashMap<>(2);
        param.put("serial_number", sfxxid);
        LOGGER.info("??????????????????????????????????????????,????????????beanName???downloadInvoice???param???{}",param);
        Object response = this.exchangeInterfaceFeignService.requestInterface("downloadInvoice", param);
        LOGGER.info("???????????????????????????????????????????????????{}",JSON.toJSONString(response));
        if(Objects.nonNull(response)){
            CommonResponse commonResponse = JSON.parseObject(JSON.toJSONString(response), CommonResponse.class);
            if(commonResponse.isSuccess()){
                JSONObject data = JSON.parseObject((String) commonResponse.getData());
                if(!data.isEmpty()){
                    // ??????????????????
                    this.saveFpxx(data, sfxxid);

                    // ????????????base64
                    //this.uploadFpxx(data, gzlslid);
                }
            }else{
                LOGGER.info("?????????????????????????????????{}", JSON.toJSONString(response));
                throw new AppException(ErrorCode.CUSTOM, "????????????????????????"+commonResponse.getFail().getMessage());
            }
        }
    }

    /**
     * ??????????????????
     */
    private void saveFpxx(JSONObject response, String sfxxid){
        String kprqStr = response.getString("date");
        String billName = response.getString("bill_name");
        String billBatchCode = response.getString("bill_batch_code");
        String billNo = response.getString("bill_no");
        String state = response.getString("state");
        String fpUrl = response.getString("url");

        // ????????????????????????
        this.deleteBdcSlFpxxBySfxxid(sfxxid);

        // ??????????????????
        BdcSlSfxxDO bdcSlSfxxDO = this.bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
        BdcSlFpxxDO bdcSlFpxxDO = new BdcSlFpxxDO();
        if(StringUtils.isNotBlank(kprqStr)){
            bdcSlFpxxDO.setKprq(DateUtils.formatDate(kprqStr, DateUtils.sdf));
        }
        bdcSlFpxxDO.setFpzlmc(billName);
        bdcSlFpxxDO.setFpdm(billBatchCode);
        bdcSlFpxxDO.setFph(billNo);
        bdcSlFpxxDO.setFpurl(fpUrl);
        if(StringUtils.isNotBlank(state)){
            bdcSlFpxxDO.setFpzt(Integer.parseInt(state));
        }
        if(Objects.nonNull(bdcSlSfxxDO) && StringUtils.isNotBlank(bdcSlSfxxDO.getXmid())){
            bdcSlFpxxDO.setXmid(bdcSlSfxxDO.getXmid());
        }
        bdcSlFpxxDO.setSfxxid(sfxxid);
        this.insertBdcSlFpxx(bdcSlFpxxDO);
    }

    /**
     * ????????????base64,?????????
     */
    private void uploadFpxx(JSONObject response, String gzlslid){
        String url = response.getString("url");
        String bill_no = response.getString("bill_no");
        if(StringUtils.isNotBlank(url)){
            try{
                url = URLDecoder.decode(url,"utf-8").replaceAll("&amp;","&");
                LOGGER.info("?????????????????????????????????{}", url);

                String base64Str = Base64Utils.encodeDzzzImageToBase64(new URL(url));
                // ??????base64?????????????????????????????????????????????pdf???base64?????????
                if(!base64Str.startsWith("data:")){
                    base64Str = CommonConstantUtils.BASE64_QZ_PDF + base64Str;
                }
                BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", bill_no, "????????????", CommonConstantUtils.WJHZ_PDF);
                bdcPdfDTO.setBase64str(base64Str);
                bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
            }catch(IOException e){
                throw new AppException(ErrorCode.IO_EX, "????????????????????????");
            }
        }else{
            LOGGER.info("?????????????????????????????????{}", JSON.toJSONString(response));
            throw new AppException(ErrorCode.CUSTOM, "????????????????????????,?????????????????????????????????");
        }
    }

    @Override
    public void inovice(String sfxxid, String qlrlb, String gzlslid) {
        if (StringUtils.isBlank(sfxxid)) {
            throw new AppException("????????????????????????");
        }
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("???????????????id????????????");
        }

        JfsxxRequestDTO jfsxxRequestDTO = this.bdcSfService.handlerFsTsxx(sfxxid, qlrlb, gzlslid);
        FsczfpxxRquestDTO fsczfpxxRquestDTO = new FsczfpxxRquestDTO();
        BeanUtils.copyProperties(jfsxxRequestDTO, fsczfpxxRquestDTO);
        UserDto currentUser = userManagerUtils.getCurrentUser();
        if (currentUser != null && StringUtils.isNotBlank(currentUser.getUsername())) {
            fsczfpxxRquestDTO.setUserCode(currentUser.getUsername());
        }else{
            BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
            fsczfpxxRquestDTO.setUserCode(this.getUserName("", bdcSlSfxxDO.getXmid()));
        }
        BdcSlSfxxDO bdcSlSfxxDO = this.bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
        fsczfpxxRquestDTO.setPayCode(bdcSlSfxxDO.getJfsbm());
        fsczfpxxRquestDTO.seteInvoiceNumber(bdcSlSfxxDO.getFph());
        LOGGER.info("????????????????????????????????????????????????,????????????beanName???issueInvoice,fsczfpxxRquestDTO???{}",JSON.toJSONString(fsczfpxxRquestDTO));
        Object response = this.exchangeInterfaceFeignService.requestInterface("issueInvoice", fsczfpxxRquestDTO);
        LOGGER.info("????????????????????????????????????????????????????????????response???{}",JSON.toJSONString(response));
        if(Objects.isNull(response)){
            throw new AppException(ErrorCode.CUSTOM, "?????????????????????????????????????????????");
        }
        CommonResponse commonResponse = JSON.parseObject(JSON.toJSONString(response), CommonResponse.class);
        if(commonResponse.isSuccess()){
            JSONObject jsonObject = JSON.parseObject((String) commonResponse.getData());
            if(!"0000".equals(jsonObject.getString("succ_code"))){
                throw new AppException(ErrorCode.CUSTOM, "????????????????????????,"+ jsonObject.getString("succ_msg"));
            }else{
                // ?????????????????????????????????????????????
                BdcSlSfxxDO sfxx = new BdcSlSfxxDO();
                sfxx.setSfxxid(sfxxid);
                sfxx.setSfkp(CommonConstantUtils.SF_S_DM);
                // ??????????????????
                this.bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
            }
        }else{
            throw new AppException(ErrorCode.CUSTOM, "????????????????????????,"+ commonResponse.getFail().getMessage());
        }
    }

    @Override
    public String queryDzph(String sfxxid, String userCode) {
        if(StringUtils.isBlank(sfxxid)){
            throw new AppException(ErrorCode.MISSING_ARG, "????????????????????????ID");
        }
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("userCode", userCode);
        if(StringUtils.isBlank(userCode)){
            UserDto currentUser = userManagerUtils.getCurrentUser();
            if (currentUser != null && StringUtils.isNotBlank(currentUser.getUsername())) {
                reqMap.put("userCode",currentUser.getUsername());
            }
        }
        LOGGER.info("?????????????????????????????????????????????beanName:getEticketNum,reqMap:{}",reqMap);
        Object response = this.exchangeInterfaceFeignService.requestInterface("getEticketNum", reqMap);
        LOGGER.info("?????????????????????????????????????????????:{}",JSON.toJSONString(response));
        if(Objects.nonNull(response)){
            CommonResponse commonResponse = JSON.parseObject(JSON.toJSONString(response), CommonResponse.class);
            if(commonResponse.isSuccess()){
                JSONObject jsonObject = JSON.parseObject((String) commonResponse.getData());
                String fph = (String) jsonObject.get("bill_no");
                BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
                bdcSlSfxxDO.setSfxxid(sfxxid);
                bdcSlSfxxDO.setFph(fph);
                this.bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                return fph;
            }
        }
        return "";
    }

    @Override
    public void queryJkqkAndAutoExec(String gzlslid, String currentUserName) {
        if(StringUtils.isBlank(gzlslid)){
            return;
        }
        BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
        bdcSlSfxxQO.setGzlslid(gzlslid);
        bdcSlSfxxQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        try{
            BdcSlSfxxDO bdcSlSfxxDO = this.bdcSfService.queryJkqkAndUpdate(bdcSlSfxxQO);

            // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
            if(Objects.nonNull(bdcSlSfxxDO) && Objects.equals(BdcSfztEnum.YJF.key(), bdcSlSfxxDO.getSfzt())){
                // 1??? ??????????????????
                this.queryDzfpxx(bdcSlSfxxDO, currentUserName);

                // 2?????????????????????
                this.inovice(bdcSlSfxxDO.getSfxxid(), gzlslid, CommonConstantUtils.QLRLB_QLR);

                // ?????? 5s??? ??????????????????????????????5s???????????????????????????
                Thread.sleep(5000);

                // 3???????????????????????????
                this.getFpxxAndUploadFpxx(bdcSlSfxxDO.getSfxxid(), gzlslid);
            }
        }catch(Exception e){
            LOGGER.error("?????????????????????????????????????????????????????????????????????????????????????????????????????????{}", e.getMessage());
        }
    }

    @Override
    public void queryDzfpxx(BdcSlSfxxDO bdcSlSfxxDO, String currentUserName){
        String userCode = this.getUserName(currentUserName, bdcSlSfxxDO.getXmid());
        this.queryDzph(bdcSlSfxxDO.getSfxxid(), userCode);
    }

    @Override
    public void batchDeleteBdcSlFpxxBySfxxid(List<String> sfxxidList) {
        if (CollectionUtils.isEmpty(sfxxidList)) {
            throw new AppException("????????????id??????");
        }
        bdcSlFpxxMapper.batchDeleteBdcSlFpxxBySfxxid(sfxxidList);
    }

    private String getUserName(String currentUserName, String xmid){
        String userCode = "";
        if(StringUtils.isNotBlank(currentUserName)){
            UserDto currentUser = userManagerUtils.getUserByName(currentUserName);
            userCode = currentUser.getUsername();
        }
        if(StringUtils.isBlank(userCode)){
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.isNotBlank(bdcXmDOList.get(0).getSlrid())){
                UserDto currentUser = userManagerUtils.getUserByUserid(bdcXmDOList.get(0).getSlrid());
                userCode = currentUser.getUsername();
            }
        }
        return userCode;
    }
}

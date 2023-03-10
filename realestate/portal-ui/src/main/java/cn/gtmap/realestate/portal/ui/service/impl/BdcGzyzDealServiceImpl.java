package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.gtc.feign.common.exception.GtFeignException;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.domain.common.BaseResult;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.portal.BdcZfhyzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzyzFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcYcslDjywDzbFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.portal.ui.service.BdcGzyzDealService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static cn.gtmap.realestate.common.util.BdcGzyzTsxxUtils.checkTsxx;

@Service
public class BdcGzyzDealServiceImpl implements BdcGzyzDealService {
    /**
     * ??????
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzyzDealServiceImpl.class.getName());

    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcGzZhGzFeignService bdcGzZhGzFeignService;
    @Autowired
    private TaskHandleClientMatcher taskHandleClient;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcYcslDjywDzbFeignService bdcYcslDjywDzbFeignService;
    @Autowired
    private BdcGzyzFeignService bdcAcceptGzyzFeignService;
    @Autowired
    private FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    @Override
    public List<BdcGzYzTsxxDTO> yzgz(String gzlslid, String checkMb, String processKey,UserDto userDto) {
        String yzrid = null == userDto ? null : userDto.getId();
        String yzrzh = null == userDto ? null : userDto.getUsername();

        List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = null;
        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setGzlslid(gzlslid);
        List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(xmQO);
        if (CollectionUtils.isNotEmpty(listXm)) {
            BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
            bdcGzYzQO.setZhbs(checkMb.replace("processKey", processKey));
            List<Map<String, Object>> paramList = new ArrayList<>();
            bdcGzYzQO.setParamList(paramList);
            bdcGzYzQO.setYzrid(yzrid);
            bdcGzYzQO.setYzrzh(yzrzh);
            //???????????????
            Set<String> setDyh = new HashSet<>();


            //??????????????????
            for (BdcXmDO bdcXmDTO : listXm) {
                if (!setDyh.contains(bdcXmDTO.getBdcdyh())) {
                    Map<String, Object> param = new HashMap<>(8);
                    param.put("bdcdyh", bdcXmDTO.getBdcdyh());
                    param.put("xmid", bdcXmDTO.getXmid());
                    param.put("slbh", bdcXmDTO.getSlbh());
                    param.put("gzlslid", bdcXmDTO.getGzlslid());
                    param.put("username", yzrzh);
                    paramList.add(param);
                    setDyh.add(bdcXmDTO.getBdcdyh());
                }
            }
            LOGGER.info("??????????????????: {}", JSON.toJSONString(bdcGzYzQO));
            try {
                listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
            }catch (Exception ex) {
                handlException(ex);
            }

        } else {
            //???????????????????????????
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
                if (Objects.isNull(bdcSlJbxxDO)) {
                    bdcSlJbxxDO = new BdcSlJbxxDO();
                }
                BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
                bdcGzYzQO.setZhbs(checkMb.replace("processKey", processKey));
                List<Map<String, Object>> paramList = new ArrayList<>();
                bdcGzYzQO.setParamList(paramList);
                bdcGzYzQO.setYzrid(yzrid);
                bdcGzYzQO.setYzrzh(yzrzh);
                //???????????????
                Set<String> setDyh = new HashSet<>();
                String currentUserName = userManagerUtils.getCurrentUserName();
                //??????????????????
                for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                    if (!setDyh.contains(bdcSlXmDO.getBdcdyh())) {
                        Map<String, Object> param = new HashMap<>(8);
                        param.put("bdcdyh", bdcSlXmDO.getBdcdyh());
                        param.put("xmid", bdcSlXmDO.getXmid());
                        param.put("slbh", bdcSlJbxxDO.getSlbh());
                        param.put("gzlslid", gzlslid);
                        param.put("username", currentUserName);
                        paramList.add(param);
                        setDyh.add(bdcSlXmDO.getBdcdyh());
                    }
                }
                LOGGER.info("????????????{}??????????????????: {}", gzlslid, JSON.toJSONString(bdcGzYzQO));
                try {
                    listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
                }catch (Exception ex) {
                    handlException(ex);
                }
            }
        }
        return listBdcGzYzTsxx;
    }
    /**
     * @description ????????????????????????
     * @param e
     * @return: java.lang.Object
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2023/1/5 12:05
     */
    public Object handlException(Exception e) {
        //??????????????????????????????????????????code???  ????????????????????????????????????????????????
        GtFeignException gte = null;
        if (e.getCause() instanceof GtFeignException) {
            gte = (GtFeignException) e.getCause();
            if (gte != null) {
                String responseBody = gte.getMsgBody();
                Map bodyMap = JSONObject.parseObject(responseBody, Map.class);
                if (MapUtils.getInteger(bodyMap, "code") != null && StringUtils.isNotBlank(MapUtils.getString(bodyMap, "msg"))) {
                    Integer errorCode = MapUtils.getInteger(bodyMap, "code");
                    if (errorCode == 101) {
                        return Collections.emptyList();
                    } else {
                        LOGGER.error("??????????????????{}", bodyMap.get("detail"));
                        throw new AppException("???????????????????????????????????????" + bodyMap.get("detail"));
                    }
                }
            }
        } else {
            LOGGER.error("?????????????????????", e);
            throw new AppException("???????????????????????????????????????");
        }
        return null;
    }

    @Override
    public List<BdcGzyzVO> ptyz(String taskid) {
        //??????
        BaseResult baseResult=taskHandleClient.completeVerify(taskid);
        List<BdcGzyzVO> list=Collections.EMPTY_LIST;
        //?????????????????????
        if(baseResult!=null){
            //????????????
            if(CommonConstantUtils.SF_S_DM.equals(baseResult.getCode())){
                //?????????????????????
                try{
                    BdcGzyzVO bdcGzyzVO=JSONObject.parseObject(baseResult.getMsg(),BdcGzyzVO.class);
                    if(bdcGzyzVO!=null && StringUtils.isNotBlank(bdcGzyzVO.getTsxx()) && bdcGzyzVO.getYzlx()!=null){
                        list=new ArrayList<>(1);
                        list.add(bdcGzyzVO);
                        return list;
                    }
                } catch (Exception e) {
                }
                //??????????????????
                list = JSONObject.parseArray(baseResult.getMsg(), BdcGzyzVO.class);
                //??????
            } else if (2 == baseResult.getCode()) {
                throw new AppException(baseResult.getMsg());
            }
        }
        return list;
    }

    @Override
    public List<BdcGzyzVO> zfyz(String gzlslid, String processKey, String taskid, UserDto userDto) {
        List<BdcGzyzVO> bdcGzyzVOS;
        //?????????????????????
        boolean isptyz = flowableNodeClient.isPlatformVerify(taskid);
        //????????????
        LOGGER.info("gzlslid:{}???????????????????????????: {}", gzlslid,isptyz);
        if (isptyz) {
            bdcGzyzVOS = this.ptyz(taskid);
        } else {
            List<BdcGzYzTsxxDTO> listBdcGzYzTsxx;
            //???????????????????????????,???????????????????????????????????????????????????
            if (bdcYcslDjywDzbFeignService.checkIsYcslLc(processKey)) {
                listBdcGzYzTsxx = bdcAcceptGzyzFeignService.fdjlcGzyz(gzlslid, "processKey_LCZF".replace("processKey", processKey));
                LOGGER.info("gzlslid:{}?????????????????????: {}", gzlslid,JSON.toJSONString(listBdcGzYzTsxx));
            } else {
                listBdcGzYzTsxx = this.yzgz(gzlslid, "processKey_LCZF", processKey,userDto);
                LOGGER.info("gzlslid:{}??????????????????: {}", gzlslid,JSON.toJSONString(listBdcGzYzTsxx));
            }
            bdcGzyzVOS = checkTsxx(listBdcGzYzTsxx);
        }
        return bdcGzyzVOS;
    }

    @Override
    public List<BdcGzyzVO> zfhyz(BdcZfhyzDTO bdcZfhyzDTO){
        //?????????
        UserDto yzr = userManagerUtils.getCurrentUser();
        String yzrid = null == yzr ? null : yzr.getId();
        String yzrzh = null == yzr ? null : yzr.getUsername();

        BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
        bdcGzYzQO.setZhbs(CommonConstantUtils.GZYZ_MB_ZFH.replace("processKey", bdcZfhyzDTO.getProcDefId()));
        List<Map<String, Object>> paramList = new ArrayList<>();
        Map<String, Object> param = new HashMap<>(2);
        param.put("gzlslid", bdcZfhyzDTO.getGzlslid());
        param.put("jdmc",bdcZfhyzDTO.getActivityName());
        param.put("currentUserId", yzrid);
        paramList.add(param);
        bdcGzYzQO.setParamList(paramList);
        bdcGzYzQO.setYzrid(yzrid);
        bdcGzYzQO.setYzrzh(yzrzh);

        LOGGER.info("??????????????????: {}", JSON.toJSONString(bdcGzYzQO));
        List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = null;
        try {
            listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
        } catch (Exception ex) {
            handlException(ex);
        }
        LOGGER.info("??????????????????: {}", JSON.toJSONString(listBdcGzYzTsxx));
        return checkTsxx(listBdcGzYzTsxx);
    }
}

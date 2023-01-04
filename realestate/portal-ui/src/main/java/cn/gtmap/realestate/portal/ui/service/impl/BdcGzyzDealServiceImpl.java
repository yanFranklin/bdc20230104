package cn.gtmap.realestate.portal.ui.service.impl;

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
     * 日志
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
            //单元号去重
            Set<String> setDyh = new HashSet<>();


            //循环生成条件
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
            LOGGER.info("规则验证参数: {}", JSON.toJSONString(bdcGzYzQO));
            listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
        } else {
            //查询受理项目的数据
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
                //单元号去重
                Set<String> setDyh = new HashSet<>();
                String currentUserName = userManagerUtils.getCurrentUserName();
                //循环生成条件
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
                LOGGER.info("当前流程{}规则验证参数: {}", gzlslid, JSON.toJSONString(bdcGzYzQO));
                listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
            }
        }
        return listBdcGzYzTsxx;
    }

    @Override
    public List<BdcGzyzVO> ptyz(String taskid) {
        //验证
        BaseResult baseResult=taskHandleClient.completeVerify(taskid);
        List<BdcGzyzVO> list=Collections.EMPTY_LIST;
        //验证没通过处理
        if(baseResult!=null){
            //验证问题
            if(CommonConstantUtils.SF_S_DM.equals(baseResult.getCode())){
                //先转换单个对象
                try{
                    BdcGzyzVO bdcGzyzVO=JSONObject.parseObject(baseResult.getMsg(),BdcGzyzVO.class);
                    if(bdcGzyzVO!=null && StringUtils.isNotBlank(bdcGzyzVO.getTsxx()) && bdcGzyzVO.getYzlx()!=null){
                        list=new ArrayList<>(1);
                        list.add(bdcGzyzVO);
                        return list;
                    }
                } catch (Exception e) {
                }
                //转换集合提示
                list = JSONObject.parseArray(baseResult.getMsg(), BdcGzyzVO.class);
                //异常
            } else if (2 == baseResult.getCode()) {
                throw new AppException(baseResult.getMsg());
            }
        }
        return list;
    }

    @Override
    public List<BdcGzyzVO> zfyz(String gzlslid, String processKey, String taskid, UserDto userDto) {
        List<BdcGzyzVO> bdcGzyzVOS;
        //判定是否走平台
        boolean isptyz = flowableNodeClient.isPlatformVerify(taskid);
        //平台验证
        LOGGER.info("gzlslid:{}验证是否走平台验证: {}", gzlslid,isptyz);
        if (isptyz) {
            bdcGzyzVOS = this.ptyz(taskid);
        } else {
            List<BdcGzYzTsxxDTO> listBdcGzYzTsxx;
            //判空是否为一窗流程,一窗流程调用非登记流程规则验证接口
            if (bdcYcslDjywDzbFeignService.checkIsYcslLc(processKey)) {
                listBdcGzYzTsxx = bdcAcceptGzyzFeignService.fdjlcGzyz(gzlslid, "processKey_LCZF".replace("processKey", processKey));
                LOGGER.info("gzlslid:{}验证是一窗流程: {}", gzlslid,JSON.toJSONString(listBdcGzYzTsxx));
            } else {
                listBdcGzYzTsxx = this.yzgz(gzlslid, "processKey_LCZF", processKey,userDto);
                LOGGER.info("gzlslid:{}规则验证结果: {}", gzlslid,JSON.toJSONString(listBdcGzYzTsxx));
            }
            bdcGzyzVOS = checkTsxx(listBdcGzYzTsxx);
        }
        return bdcGzyzVOS;
    }

    @Override
    public List<BdcGzyzVO> zfhyz(BdcZfhyzDTO bdcZfhyzDTO){
        //验证人
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

        LOGGER.info("规则验证参数: {}", JSON.toJSONString(bdcGzYzQO));
        List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
        LOGGER.info("规则验证结果: {}", JSON.toJSONString(listBdcGzYzTsxx));
        return checkTsxx(listBdcGzYzTsxx);
    }
}

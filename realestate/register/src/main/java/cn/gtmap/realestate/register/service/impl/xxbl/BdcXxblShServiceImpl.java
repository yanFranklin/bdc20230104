package cn.gtmap.realestate.register.service.impl.xxbl;

import cn.gtmap.gtc.feign.common.exception.GtFeignException;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.CategoryProcessDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.domain.BdcBlShDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.register.XxblDbDTO;
import cn.gtmap.realestate.common.core.enums.BdcBlShEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.ex.UserInformationAccessException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.RegisterWorkflowFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblShService;
import cn.gtmap.realestate.register.util.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.util.*;

/**
 * ??????????????????????????????????????????
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.1, 2020/03/16 18:17
 */
@Service
public class BdcXxblShServiceImpl implements BdcXxblShService {

    /**
     * ????????????
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXxblShServiceImpl.class);

    /**
     * ??????????????????
     */
    private static final String CLASS_NAME = BdcXxblShServiceImpl.class.getName();
    @Autowired
    protected MessageProvider messageProvider;
    @Autowired
    private BdcGzZhGzFeignService bdcGzZhGzFeignService;
    @Autowired
    private BdcDbxxFeignService bdcDbxxFeignService;
    @Autowired
    private ProcessTaskClient processTaskClient;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private RegisterWorkflowFeignService registerWorkflowFeignService;

    /**
     * ???????????? ???????????????????????????????????????????????????
     */
    @Value("${xxbl.zxql.updateFj:false}")
    private String updateZxqlFj;

    /**
     * ????????????????????????
     *
     * @param bdcBlShDO ???????????????????????????
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public void generateBlShxx(BdcBlShDO bdcBlShDO) {
        String blshid = UUIDGenerator.generate16();
        bdcBlShDO.setBlshid(blshid);
        bdcBlShDO.setQsjdid(blshid);
        // 1. ?????????????????????
        UserDto currentUser = getUserDto();
        bdcBlShDO.setSlrid(currentUser.getId());
        // ????????????????????????????????????????????? 1
        bdcBlShDO.setSxh(1);
        // ??????????????? 3 ??????????????????
        bdcBlShDO.setBlzt(Constants.BLZT_WZF);
        LOGGER.warn("??????????????????: {}", JSON.toJSONString(bdcBlShDO));
        entityMapper.insertSelective(bdcBlShDO);
    }

    @Override
    public void forwardBlShxx(BdcBlShDO bdcBlShDO) {
        BdcBlShDO yblshDO = entityMapper.selectByPrimaryKey(BdcBlShDO.class, bdcBlShDO.getBlshid());
        // ???????????? 1???????????????????????????????????????????????????????????????
        if (yblshDO.getSxh() == 1 && yblshDO.getShkssj() != null && Constants.BLSHLX_XJ.equals(yblshDO.getBlshlx())) {
            // ???????????????????????????
            yblshDO.setShjssj(new Date());
            // ???????????????????????????
            yblshDO.setBlzt(Constants.BLZT_END);
            entityMapper.updateByPrimaryKeySelective(yblshDO);
            // ??????????????????????????????
            bdcBlShDO.setBlshid(UUIDGenerator.generate16());
            bdcBlShDO.setQsjdid(yblshDO.getQsjdid());
            // ????????????????????????????????????????????????????????????????????????
            bdcBlShDO.setSlrid(yblshDO.getShryid());
            bdcBlShDO.setXmid(yblshDO.getXmid());
            bdcBlShDO.setGzlslid(yblshDO.getGzlslid());
            bdcBlShDO.setShkssj(new Date());
            // ?????????
            bdcBlShDO.setBlzt(Constants.BLZT_ONGOING);
            bdcBlShDO.setBllx(yblshDO.getBllx());
            bdcBlShDO.setBlshlx(yblshDO.getBlshlx());
            bdcBlShDO.setSxh(2);
            entityMapper.insertSelective(bdcBlShDO);
        } else {
            bdcBlShDO.setBlzt(Constants.BLZT_ONGOING);
            // ?????????????????????????????????????????????????????????????????? controller ?????????
            bdcBlShDO.setShkssj(new Date());
            entityMapper.updateByPrimaryKeySelective(bdcBlShDO);
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @param gzlslid
     */
    @Override
    public List<BdcGzyzVO> dbYz(String gzlslid) throws Exception {
        UserDto yzr = userManagerUtils.getCurrentUser();//????????????
        String yzrid = null == yzr ? null : yzr.getId();
        String yzrzh = null == yzr ? null : yzr.getUsername();

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS) || StringUtils.isBlank(bdcXmDOS.get(0).getGzldymc())) {
            throw new AppException("?????????????????????????????????");
        }
        List<CategoryProcessDto> categoryProcessDtos = processTaskClient.listAllCategoryProcess();
        String processKey = queryProcessDefKey(URLDecoder.decode(bdcXmDOS.get(0).getGzldymc(), "UTF-8"), categoryProcessDtos);
        if (StringUtils.isBlank(processKey)) {
            throw new AppException("???????????????????????????????????????id");
        }
        List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = null;
        if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
            BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
            // ??????????????????????????????processKey_DBYZ
            bdcGzYzQO.setZhbs(StringUtils.replace(CommonConstantUtils.processKey_DBYZ, "processKey", processKey));
            List<Map<String, Object>> paramList = new ArrayList<>();
            bdcGzYzQO.setParamList(paramList);
            bdcGzYzQO.setYzrid(yzrid);
            bdcGzYzQO.setYzrzh(yzrzh);
            //???????????????
            Set<String> setDyh = new HashSet<>();
            //??????????????????
            for (BdcXmDO bdcXmDO : bdcXmDOS) {
                if (!setDyh.contains(bdcXmDO.getBdcdyh())) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("bdcdyh", bdcXmDO.getBdcdyh());
                    param.put("xmid", bdcXmDO.getXmid());
                    param.put("slbh", bdcXmDO.getSlbh());
                    param.put("gzlslid", bdcXmDO.getGzlslid());
                    paramList.add(param);
                    setDyh.add(bdcXmDO.getBdcdyh());
                }
            }
            try {
                listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
            } catch (Exception ex) {
                if (dealGzyzException(ex)) {
                    return Collections.emptyList();
                }
            }
        }
        return BdcGzyzTsxxUtils.checkTsxx(listBdcGzYzTsxx);
    }

    /**
     * @param ex ??????????????????
     * @return {boolean} ??????????????????
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description ???????????????????????????
     */
    private boolean dealGzyzException(Exception ex) {
        //??????????????????????????????????????????code???  ????????????????????????????????????????????????
        GtFeignException gte;
        if (ex.getCause() instanceof GtFeignException) {
            gte = (GtFeignException) ex.getCause();
            if (gte != null) {
                String responseBody = gte.getMsgBody();
                Map bodyMap = JSON.parseObject(responseBody, Map.class);
                if (MapUtils.getInteger(bodyMap, "code") != null && StringUtils.isNotBlank(MapUtils.getString(bodyMap, "msg"))) {
                    Integer errorCode = MapUtils.getInteger(bodyMap, "code");
                    if (errorCode == 101) {
                        return true;
                    } else {
                        throw new AppException("????????????????????????");
                    }
                }
            }
        } else {
            throw new AppException("????????????????????????");
        }
        LOGGER.error("??????????????????????????????", ex);
        return false;
    }

    /**
     * ????????????????????????
     *
     * @param blshid
     */
    @Override
    public void endBlShxx(String blshid) {
        BdcBlShDO bdcBlShDO = entityMapper.selectByPrimaryKey(BdcBlShDO.class, blshid);
        bdcBlShDO.setBlshid(blshid);
        // ?????? ??????????????? ????????????
        bdcBlShDO.setBlzt(Constants.BLZT_END);
        bdcBlShDO.setShjssj(new Date());
        entityMapper.updateByPrimaryKeySelective(bdcBlShDO);
        // ???????????????????????????
        changeAjzt(bdcBlShDO.getGzlslid());
        // ???????????????????????????
        if(Boolean.valueOf(updateZxqlFj)) {
            registerWorkflowFeignService.updateZxqlfj(bdcBlShDO.getGzlslid());
        }
    }

    private void changeAjzt(String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            throw new AppException("?????????????????????????????????");
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        // ????????????????????????????????????
        if (!CommonConstantUtils.AJZT_YB_DM.equals(bdcXmDO.getAjzt())) {
            bdcDbxxFeignService.changeAjzt(gzlslid);
        }
        // ??????????????? 0 ???????????????
        if (CommonConstantUtils.QSZT_TEMPORY.equals(bdcXmDO.getQszt())) {
            XxblDbDTO xxblDbDTO = new XxblDbDTO();
            xxblDbDTO.setDbr(bdcXmDO.getDbr());
            xxblDbDTO.setDjjg(bdcXmDO.getDjjg());
            xxblDbDTO.setDjsj(bdcXmDO.getDjsj());
            xxblDbDTO.setGzlslid(gzlslid);
            bdcDbxxFeignService.updateBdcDbxxAndQszt(xxblDbDTO);
        }
    }

    @Override
    public boolean backBlShxx(String blshid) {
        // ???????????????????????????????????? ????????????????????????????????? ID ???????????????????????????????????????
        BdcBlShDO bdcBlShDO = entityMapper.selectByPrimaryKey(BdcBlShDO.class, blshid);
        UserDto userDto = getUserDto();
        if (StringUtils.equals(userDto.getId(), bdcBlShDO.getShryid())) {
            if (bdcBlShDO.getSxh() == 2 && StringUtils.equals(bdcBlShDO.getSlrid(), userDto.getId())) {
                // ?????????????????????????????????????????????????????????????????????????????????????????????
                entityMapper.delete(bdcBlShDO);
                bdcBlShDO = entityMapper.selectByPrimaryKey(BdcBlShDO.class, bdcBlShDO.getQsjdid());
            }
            // ????????????????????? ??????
            bdcBlShDO.setBlzt(Constants.BLZT_BACK);
            bdcBlShDO.setShkssj(null);
            bdcBlShDO.setShryid(null);
            bdcBlShDO.setShryxm(null);
            entityMapper.updateByPrimaryKeyNull(bdcBlShDO);
            return true;
        } else {
            return false;
        }

    }

    /**
     * ????????????????????????
     *
     * @param bdcBlShDO ????????????
     * @return {BdcBlShEnum.value}
     */
    @Override
    public Integer checkIsSh(BdcBlShDO bdcBlShDO) {
        if (StringUtils.isBlank(bdcBlShDO.getXmid())) {
            throw new MissingArgumentException("?????????????????? XMID");
        }
        Example example = new Example(BdcBlShDO.class);
        // ????????????????????????
        example.createCriteria().andEqualTo("xmid", bdcBlShDO.getXmid())
                .andNotEqualTo("blzt", Constants.BLZT_END);

        List<BdcBlShDO> bdcBlShDOS = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bdcBlShDOS)) {
            UserDto userDto = getUserDto();
            String currentUserId = userDto.getId();
            for (BdcBlShDO blShDO : bdcBlShDOS) {
                //1.??????????????????????????????????????????????????? ??????????????????
                if (Constants.BLZT_ONGOING.equals(blShDO.getBlzt())) {
                    return BdcBlShEnum.ONGOING.getValue();
                }
                //?????????????????????
                if (Constants.BLZT_BACK.equals(blShDO.getBlzt())) {
                    if (blShDO.getSxh().equals(1) && StringUtils.equals(blShDO.getSlrid(), currentUserId)) {
                        //????????????????????????1??????????????????????????????
                        return BdcBlShEnum.ONGOING_PERSONAL.getValue();
                    }
                    //???????????????????????????2
                    if (blShDO.getSxh().equals(2)) {
                        return BdcBlShEnum.ONGOING.getValue();
                    }
                }
                if (Constants.BLZT_WZF.equals(blShDO.getBlzt())) {
                    if (StringUtils.equals(blShDO.getSlrid(), currentUserId)) {
                        //?????????????????????????????????
                        return BdcBlShEnum.ONGOING_PERSONAL.getValue();
                    } else {
                        return BdcBlShEnum.MODIFY.getValue();
                    }
                }
            }
        } else {
            // ????????????
            return BdcBlShEnum.NOT.getValue();
        }
        // ???????????????????????????
        return BdcBlShEnum.ONGOING.getValue();
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcBlShDO]
     * @return cn.gtmap.realestate.common.core.domain.BdcBlShDO
     * @description ????????????id????????????id??????????????????????????????
     */
    @Override
    public BdcBlShDO queryBdcBlshDO(BdcBlShDO bdcBlShDO) {
        UserDto userDto = getUserDto();
        String currentUserId = userDto.getId();

        List blztList = new ArrayList<>();
        blztList.add(Constants.BLZT_WZF);
        blztList.add(Constants.BLZT_BACK);

        Example example = new Example(BdcBlShDO.class);
        example.createCriteria().andEqualTo("xmid", bdcBlShDO.getXmid())
        .andEqualTo("slrid", currentUserId)
        .andIn("blzt", blztList);

        List<BdcBlShDO> bdcBlShDOS = entityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(bdcBlShDOS)){
            LOGGER.error("????????????????????????????????????");
            throw new AppException("??????????????????????????????????????????");
        }
        if(bdcBlShDOS.size() > 1){
            throw new AppException("??????????????????????????????????????????");
        }
        return bdcBlShDOS.get(0);
    }

    /**
     * @param xmid xmid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description ???????????????????????? <br>
     * <strong>????????????????????????????????????????????????????????????</strong>
     * ?????????????????????????????????????????????????????????
     */
    @Override
    public void deleteBlShxx(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            LOGGER.info("{}: ???????????????????????????????????????????????? xmid ?????????", CLASS_NAME);
            return;
        }
        LOGGER.debug("{}: ??????????????????????????????????????? xmid: {} ", CLASS_NAME, xmid);
        // ???????????????????????????????????????
        Example example = new Example(BdcBlShDO.class);
        example.createCriteria().andEqualTo("xmid", xmid);
        entityMapper.deleteByExample(example);
        LOGGER.debug("{}: ??????????????????????????????????????? xmid: {} ", CLASS_NAME, xmid);
    }


    /**
     * @param gzlslid gzlslid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description ?????????????????????????????? <br>
     * <strong>????????????????????????????????????????????????????????????</strong>
     * ?????????????????????????????????????????????????????????
     */
    @Override
    public void deleteBlShxxs(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            LOGGER.info("{}: ???????????????????????????????????????????????? gzlslid ?????????", CLASS_NAME);
            return;
        }
        LOGGER.debug("{}: ??????????????????????????????????????? gzlslid: {} ", CLASS_NAME, gzlslid);
        // ???????????????????????????????????????
        Example example = new Example(BdcBlShDO.class);
        example.createCriteria().andEqualTo("gzlslid", gzlslid);
        int result = entityMapper.deleteByExampleNotNull(example);
        LOGGER.debug("{}: ??????????????????????????????????????? {} ???????????? gzlslid: {} ", CLASS_NAME, result, gzlslid);
    }

    /**
     * ??????????????????
     * ?????????????????????????????????????????????
     *
     * @return {UserDto} ???????????? DTO
     */
    private UserDto getUserDto() {
        UserDto currentUser = userManagerUtils.getCurrentUser();
        if (null == currentUser) {
            throw new UserInformationAccessException(messageProvider.getMessage("message.nouser"));
        }
        return currentUser;
    }

    /**
     * ?????? ??????????????????????????? ???????????????key
     *
     * @param gzldymc             ?????????????????????
     * @param categoryProcessDtos ??????
     * @return {String} ???????????????key
     */
    private String queryProcessDefKey(String gzldymc, List<CategoryProcessDto> categoryProcessDtos) {
        for (CategoryProcessDto categoryProcessDto : categoryProcessDtos) {
            List<ProcessDefData> processList = categoryProcessDto.getProcessList();
            if (CollectionUtils.isEmpty(processList) && CollectionUtils.isNotEmpty(categoryProcessDto.getChildren())) {
                String processDefKey = queryProcessDefKey(gzldymc, categoryProcessDto.getChildren());
                if (StringUtils.isNotBlank(processDefKey)) {
                    return processDefKey;
                }
            } else {
                for (ProcessDefData processDefData : processList) {
                    if (StringUtils.equals(processDefData.getName(), gzldymc)) {
                        return processDefData.getProcessDefKey();
                    }
                }
            }
        }
        return StringUtils.EMPTY;
    }


    /**
     * ????????????????????????????????????
     * @param bdcBlShDO  ????????????????????????
     */
    public int updateBlshxx(BdcBlShDO bdcBlShDO){
        if (!CheckParameter.checkAnyParameter(bdcBlShDO)){
            throw new AppException("?????????????????????");
        }
        Example example= new Example(BdcBlShDO.class,false);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("blshid",bdcBlShDO.getBlshid());
        return entityMapper.updateByExampleSelectiveNotNull(bdcBlShDO,example);
    }

    @Override
    public List<BdcBlShDO> queryBlshxxByXmid(String xmid) {
        if(StringUtils.isBlank(xmid)){
            throw new AppException("?????????????????????");
        }
        Example example= new Example(BdcBlShDO.class,false);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("xmid",xmid);
        return entityMapper.selectByExample(example);
    }

}

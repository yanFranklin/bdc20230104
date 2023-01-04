package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.init.BdcXmLsgxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcCxYwxxService;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.service.other.InitDataService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/8/25
 * @description 撤销业务信息服务
 */
@Service
public class BdcCxYwxxServiceImpl implements BdcCxYwxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcCxYwxxServiceImpl.class);

    private static final String BCFJ = "撤销登记生成新的证书记录，证号不变，注销之前旧的证号";

    @Autowired
    BdcXmLsgxService bdcXmLsgxService;

    @Autowired
    BdcXmService bdcXmService;

    @Autowired
    BdcZsServiceImpl bdcZsService;

    @Autowired
    InitDataService initDataService;

    @Autowired
    protected DozerBeanMapper initDozerMapper;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private InitBeanFactory initBeanFactory;

    @Override
    public List<InitServiceDTO> initCxYwxx(InitServiceQO initServiceQO) throws Exception {
        List<InitServiceDTO> initServiceDTOList = new ArrayList<>();
        if (initServiceQO != null && StringUtils.isNotBlank(initServiceQO.getYxmid())) {
            boolean zxql = false;
            BdcXmDO yxm = bdcXmService.queryBdcXmByPrimaryKey(initServiceQO.getYxmid());
            if (yxm != null) {
                //获取yxmid的上一手
                List<BdcXmLsgxDO> xmLsgxList = bdcXmLsgxService.queryBdcXmLsgxByXmid(initServiceQO.getYxmid(), null);
                if (CollectionUtils.isNotEmpty(xmLsgxList)) {
                    for (BdcXmLsgxDO lsgx : xmLsgxList) {
                        if (StringUtils.isNotBlank(lsgx.getYxmid())) {
                            BdcXmDO yyxm = bdcXmService.queryBdcXmByPrimaryKey(lsgx.getYxmid());
                            InitServiceDTO initServiceDTO = new InitServiceDTO();
                            if (CollectionUtils.isNotEmpty(initServiceDTOList)) {
                                String newxmid = UUIDGenerator.generate16();
                                initServiceQO.getBdcXm().setXmid(newxmid);
                                initServiceQO.setXmid(newxmid);
                                if (initServiceQO.getBdcSlXmDO() != null) {
                                    initServiceQO.getBdcSlXmDO().setXmid(newxmid);
                                }
                            }
                            LOGGER.info("gzlslid:{}slbh:{}办理撤销登记选择产权的原项目信息:{}", initServiceQO.getBdcXm().getGzlslid(), initServiceQO.getBdcXm().getSlbh(), yyxm);
                            if (yyxm == null) {
                                LOGGER.error("撤销登记产权历史关系数据存在问题,yxmid未查询到对应项目！yxmid:{}", lsgx.getYxmid());
                                throw new AppException("撤销登记产权历史关系数据存在问题,yxmid未查询到对应项目！yxmid:" + lsgx.getYxmid());
                            }
                            //权利一致,复制yxmid的上一手业务信息,转为现势权利
                            //选择的是产权,yxmid的上一手是产权或者选择非产权,权利一致,复制yxmid的上一手业务信息,转为现势权利
                            boolean cqfz = !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, yxm.getQllx()) && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, yyxm.getQllx());
                            if (cqfz || (yxm.getQllx() != null && yxm.getQllx().equals(yyxm.getQllx()))) {
                                LOGGER.info("gzlslid:{}slbh:{}办理撤销登记，走复制权利逻辑逻辑，复制xmid:{}", initServiceQO.getBdcXm().getGzlslid(), initServiceQO.getBdcXm().getSlbh(), lsgx.getYxmid());
                                InitServiceDTO yyYwxx = initDataService.queryYwsj(lsgx.getYxmid());
                                if (yyYwxx != null) {
                                    //更新原证书号为注销状态,保证不重号--办结时才处理
                                    //List<BdcZsDO> ybdcZsList = getZsList(yyYwxx);

                                    BeanUtils.copyProperties(yyYwxx, initServiceDTO);
                                    //修改流程信息
                                    changeSlYwxxToInitServiceDTO(initServiceQO, initServiceDTO);
                                    //修改xmid,gzlslid,主键数据
                                    dealCommonInitServiceDTO(initServiceDTO, initServiceQO.getBdcXm().getXmid(), initServiceQO.getBdcXm().getGzlslid());
                                    //注销时，到办结时才有bdcqzh
                                    updateZhLszt(initServiceDTO.getBdcZsList());
                                    //更新原证书号为注销状态,保证不重号--办结时才处理
                                    //updateZhzt(ybdcZsList);
                                }
                            } else if (!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, yxm.getQllx()) && ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, yyxm.getQllx())) {
                                if (CommonConstantUtils.SF_S_DM.equals(lsgx.getWlxm())) {
                                    //选择的是产权,yxmid的上一手为限制权利,如果为外联，初始化不处理,注销原权利为是则登簿时还原状态
                                    LOGGER.info("gzlslid:{}slbh:{}撤销登记，产权项目ID上一手外联历史关系为限制权利,初始化不处理:{}", initServiceQO.getBdcXm().getGzlslid(), initServiceQO.getBdcXm().getSlbh(), lsgx);
                                } else {
                                    LOGGER.error("撤销登记产权历史关系数据存在问题,产权项目ID上一手主历史关系为限制权利！yxmid:{},产权项目：{},非产权项目：{}", lsgx.getYxmid(), yxm, yyxm);
                                    throw new AppException("撤销登记产权历史关系数据存在问题,产权项目ID上一手主历史关系为限制权利！yxmid:" + lsgx.getYxmid());
                                }
                            } else if (Boolean.FALSE.equals(zxql)) {
                                LOGGER.info("gzlslid:{}slbh:{}办理撤销登记，权利不一致,走注销逻辑,yxmid:{},yyxmid:{}", initServiceQO.getBdcXm().getGzlslid(), initServiceQO.getBdcXm().getSlbh(), yxm.getXmid(), yyxm.getXmid());
                                //权利不一致,走注销逻辑,或者是没有找到上一手项目，也走注销逻辑，注销当前手不管上一手
                                //初始化服务类
                                zxql(initServiceQO, yxm, yyxm, initServiceDTO);
                                zxql = true;
                            }
                            initServiceDTOList.add(initServiceDTO);
                        }
                    }
                } else {
                    LOGGER.info("gzlslid:{}slbh:{}办理撤销登记选择产权没有上一手，直接走注销逻辑，yxmid:{}", initServiceQO.getBdcXm().getGzlslid(), initServiceQO.getBdcXm().getSlbh(), initServiceQO.getYxmid());
                    /**
                     * 没有上一手则走注销逻辑，注销当前手即可
                     * 设置xmlsgx注销原权利，当前生成的项目不成成权利和证书
                     */
                    InitServiceDTO initServiceDTO = new InitServiceDTO();
                    zxql(initServiceQO, yxm, null, initServiceDTO);
                    initServiceDTOList.add(initServiceDTO);
                }
            }

        }
        return initServiceDTOList;

    }

    /**
     * 注销权利
     *
     * @param initServiceQO
     * @param yxm            当前手
     * @param yyxm           上一手
     * @param initServiceDTO
     * @throws Exception
     */
    private void zxql(InitServiceQO initServiceQO, BdcXmDO yxm, BdcXmDO yyxm, InitServiceDTO initServiceDTO) throws Exception {
        List<Class> clzList = initBeanFactory.getInitServices();
        BdcCshFwkgSlDO bdcCshFwkgSlDO = new BdcCshFwkgSlDO();
        BeanUtils.copyProperties(initServiceQO.getBdcCshFwkgSl(), bdcCshFwkgSlDO);
        bdcCshFwkgSlDO.setQllx(yxm.getQllx());
        bdcCshFwkgSlDO.setSfscql(CommonConstantUtils.SF_F_DM);
        bdcCshFwkgSlDO.setSfsczs(CommonConstantUtils.SF_F_DM);
        /**
         * 有上一手时，将当前手置为外联，将上一手关联到即将创建的项目
         */
        if (initServiceQO.getBdcSlXmDO() != null && Objects.nonNull(yyxm)) {
            initServiceQO.getBdcSlXmDO().setQllx(yyxm.getQllx());
            if (initServiceQO.getBdcXm() != null) {
                initServiceQO.getBdcXm().setQllx(yyxm.getQllx());
            }
            if (CollectionUtils.isNotEmpty(initServiceQO.getBdcXmLsgxList())) {
                for (BdcXmLsgxDO xmLsgxDO : initServiceQO.getBdcXmLsgxList()) {
                    xmLsgxDO.setWlxm(CommonConstantUtils.SF_S_DM);
                }
                BdcXmLsgxDO bdcXmLsgxDO = new BdcXmLsgxDO();
                bdcXmLsgxDO.setXmid(initServiceQO.getBdcSlXmDO().getXmid());
                bdcXmLsgxDO.setYxmid(yyxm.getXmid());
                bdcXmLsgxDO.setZxyql(CommonConstantUtils.SF_F_DM);
                initServiceQO.setYxmid(yyxm.getXmid());
                initServiceQO.getBdcXmLsgxList().add(bdcXmLsgxDO);
            }
        }

        //初始化加载类循环
        for (Class clz : clzList) {
            List<InitService> list = initBeanFactory.getTrafficMode(bdcCshFwkgSlDO, clz);
            //对应实现循环处理
            for (InitService service : list) {
                service.init(initServiceQO, initServiceDTO);
            }
        }
    }

    @Override
    public void revertZhzt(List<BdcZsDO> zsDOList) {
        if (CollectionUtils.isNotEmpty(zsDOList)) {
            for (BdcZsDO bdcZsDO : zsDOList) {
                String zxbdcqzh = StringUtils.join(bdcZsDO.getBdcqzh(), CommonConstantUtils.BDCQZH_ZX);
                if (StringUtils.isNotBlank(zxbdcqzh)) {
                    BdcZsDO zsQO = new BdcZsDO();
                    zsQO.setBdcqzh(zxbdcqzh);
                    List<BdcZsDO> zsList = entityMapper.selectByObj(zsQO);
                    if (CollectionUtils.isNotEmpty(zsList)) {
                        BdcZsDO updateZs = new BdcZsDO();
                        updateZs.setZsid(zsList.get(0).getZsid());
                        updateZs.setBdcqzh(bdcZsDO.getBdcqzh());
                        updateZs.setFj(bdcZsDO.getFj());
                        entityMapper.updateByPrimaryKeySelective(updateZs);
                    }
                }
            }
        }
    }

    private List<BdcZsDO> getZsList(InitServiceDTO initServiceDTO) {
        List<BdcZsDO> ybdcZsList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(initServiceDTO.getBdcZsList())) {
            for (BdcZsDO bdcZsDO : initServiceDTO.getBdcZsList()) {
                BdcZsDO yBdcZs = new BdcZsDO();
                BeanUtils.copyProperties(bdcZsDO, yBdcZs);
                ybdcZsList.add(yBdcZs);
            }
        }
        return ybdcZsList;
    }

    private void changeSlYwxxToInitServiceDTO(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) {
        if (initServiceDTO != null && initServiceDTO.getBdcXm() != null) {
            //1.项目

            initDozerMapper.map(initServiceQO.getBdcXm(), initServiceDTO.getBdcXm(), "xm_cxywxx");
            //2.项目历史关系以当前历史关系为准
            if (CollectionUtils.isNotEmpty(initServiceQO.getBdcXmLsgxList())) {
                List<BdcXmLsgxDO> lsgxDOList = new ArrayList<>();
                for (BdcXmLsgxDO bdcXmLsgxDO : initServiceQO.getBdcXmLsgxList()) {
                    BdcXmLsgxDO lsgxDO = new BdcXmLsgxDO();
                    BeanUtils.copyProperties(bdcXmLsgxDO, lsgxDO);
                    lsgxDOList.add(bdcXmLsgxDO);
                }
                initServiceDTO.setBdcXmLsgxList(lsgxDOList);
            }

            //3.初始化服务开关以当前流程为准
            BdcCshFwkgSlDO bdcCshFwkgSlDO = new BdcCshFwkgSlDO();
            BeanUtils.copyProperties(initServiceQO.getBdcCshFwkgSl(), bdcCshFwkgSlDO);
            initServiceDTO.setBdcCshFwkgSlDO(bdcCshFwkgSlDO);

            //4.权利
            initServiceDTO.getBdcQl().setSlbh(initServiceQO.getBdcXm().getSlbh());
            initServiceDTO.getBdcQl().setQszt(CommonConstantUtils.QSZT_TEMPORY);
        }

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 公共处理业务信息
     */
    private void dealCommonInitServiceDTO(InitServiceDTO initServiceDTO, String xmid, String gzlslid) throws Exception {
        if (initServiceDTO.getBdcCshFwkgSlDO() != null) {
            initServiceDTO.getBdcCshFwkgSlDO().setId(xmid);
        }
        Field[] fields = InitServiceDTO.class.getDeclaredFields();
        //记录证书表yzsid和新zsid的关系
        Map<String, String> zsidMap = new HashMap<>();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                field.setAccessible(true);
                Object val = field.get(initServiceDTO);
                //判断是否是list集合
                if (val != null && field.getType() == List.class) {
                    List ywxxList = (List) val;
                    if (CollectionUtils.isNotEmpty(ywxxList)) {
                        for (int i = 0; i < ywxxList.size(); i++) {
                            setObjectValue(ywxxList.get(i), xmid, gzlslid, zsidMap);
                            //特殊处理一些外键字段
                            if ((ywxxList.get(i) instanceof BdcFdcqFdcqxmDO || ywxxList.get(i) instanceof BdcFdcq3GyxxDO) && initServiceDTO.getBdcQl() != null) {
                                ((BdcFdcqFdcqxmDO) ywxxList.get(i)).setQlid(initServiceDTO.getBdcQl().getQlid());
                            } else if (ywxxList.get(i) instanceof BdcXmZsGxDO) {
                                BdcXmZsGxDO xmZsGxDO = (BdcXmZsGxDO) ywxxList.get(i);
                                xmZsGxDO.setZsid(zsidMap.get(xmZsGxDO.getZsid()));
                            }
                        }

                    }
                } else if (val != null) {
                    setObjectValue(val, xmid, gzlslid, zsidMap);
                }
            }
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 对象修改xmid, gzlslid, 主键数据
     */
    private void setObjectValue(Object val, String xmid, String gzlslid, Map<String, String> zsidMap) throws Exception {
        Field[] ywxxFields = val.getClass().getDeclaredFields();
        for (Field f : ywxxFields) {
            f.setAccessible(true);
            if (StringUtils.equals(f.getName(), "xmid")) {
                f.set(val, xmid);
            } else if (StringUtils.equals(f.getName(), "gzlslid")) {
                f.set(val, gzlslid);
            } else if (f.isAnnotationPresent(Id.class)) {
                //获取实体主键,重新赋值
                String newId = UUIDGenerator.generate16();
                if (val instanceof BdcZsDO) {
                    zsidMap.put(((BdcZsDO) val).getZsid(), newId);
                }
                f.set(val, newId);
            }
        }
    }

    /**
     * @param bdcZsList 不动产证书记录
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新证书号为临时状态
     */
    private void updateZhLszt(List<BdcZsDO> bdcZsList) {
        if (CollectionUtils.isEmpty(bdcZsList)) {
            return;
        }
        for (BdcZsDO bdcZsDO : bdcZsList) {
            // 之前的证号已经包含注销标识说明已经获取过
            if (StringUtils.contains(bdcZsDO.getBdcqzh(), CommonConstantUtils.BDCQZH_LS)) {
                LOGGER.error("撤销登记不动产权证号已存在临时标识！bdcqzh:{}", bdcZsDO.getBdcqzh());
                throw new AppException("撤销登记不动产权证号已存在临时标识！bdcqzh:" + bdcZsDO.getBdcqzh());
            }
            bdcZsDO.setBdcqzh(StringUtils.join(bdcZsDO.getBdcqzh(), CommonConstantUtils.BDCQZH_LS));
            LOGGER.info("撤销登记生成新的证书记录，添加临时标志：{}", JSON.toJSONString(bdcZsDO));
        }
    }


    /**
     * @param yBdcZsList 原不动产证书记录
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新上一手证书号为注销状态
     */
    private void updateZhzt(List<BdcZsDO> yBdcZsList) {
        if (CollectionUtils.isEmpty(yBdcZsList)) {
            return;
        }
        for (BdcZsDO yBdcZsDO : yBdcZsList) {
            // 之前的证号已经包含注销标识说明已经获取过
            if (StringUtils.contains(yBdcZsDO.getBdcqzh(), CommonConstantUtils.BDCQZH_ZX)) {
                LOGGER.error("撤销登记不动产权证号已存在注销标识！bdcqzh:{}", yBdcZsDO.getBdcqzh());
                throw new AppException("撤销登记不动产权证号已存在注销标识！bdcqzh:" + yBdcZsDO.getBdcqzh());
            }
            BdcZsDO updateZs = new BdcZsDO();
            updateZs.setZsid(yBdcZsDO.getZsid());
            updateZs.setBdcqzh(StringUtils.join(yBdcZsDO.getBdcqzh(), CommonConstantUtils.BDCQZH_ZX));
            //补充附记
            updateZs.setFj(StringUtils.join(yBdcZsDO.getFj(), CommonConstantUtils.ZF_HH_CHAR, BCFJ));
            entityMapper.updateByPrimaryKeySelective(updateZs);
            LOGGER.info("撤销登记生成新的证书记录，证号不变，注销之前旧的证号：{}", JSON.toJSONString(updateZs));
        }
    }
}

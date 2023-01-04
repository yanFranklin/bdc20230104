package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlHsxxMapper;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.accept.BdcSwxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.swxx.SwHsztDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlHsxxQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description 受理核税信息
 */
@Service
public class BdcSlHsxxServiceImpl implements BdcSlHsxxService {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlHsxxServiceImpl.class);
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    protected MessageProvider messageProvider;
    @Autowired
    private BdcSlHsxxMxService bdcSlHsxxMxService;
    @Autowired
    BdcSlHsxxMapper bdcSlHsxxMapper;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSlJyxxService bdcSlJyxxService;
    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    BdcSlSysService bdcSlSysService;

    @Override
    public List<BdcSlHsxxDO> listBdcSlHsxx(BdcSlHsxxDO bdcSlHsxxDO) {
        if (!CheckParameter.checkAnyParameter(bdcSlHsxxDO)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.selectByObj(bdcSlHsxxDO);

    }

    @Override
    public List<BdcSlHsxxDO> listBdcSlHsxxByHsxxQo(BdcSlHsxxQO bdcSlHsxxQO) {
        BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
        BeanUtils.copyProperties(bdcSlHsxxQO, bdcSlHsxxDO);

        return listBdcSlHsxx(bdcSlHsxxDO);
    }

    @Override
    public int delBdcSlHsxx(BdcSlHsxxDO bdcSlHsxxDO) {
        if (bdcSlHsxxDO != null) {
            Example example = entityMapper.objToExample(bdcSlHsxxDO);
            if (example == null) {
                throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
            }
            return entityMapper.deleteByExampleNotNull(example);
        }
        return 0;
    }

    @Override
    public int deleteBdcSlHsxx(BdcSlHsxxQO bdcSlHsxxQO) {
        if(Objects.nonNull(bdcSlHsxxQO)){
            Example example =new Example(BdcSlHsxxDO.class);
            Example.Criteria criteria = example.createCriteria();

            if(StringUtils.isNotBlank(bdcSlHsxxQO.getHsxxid())){
                criteria.andEqualTo("hsxxid", bdcSlHsxxQO.getHsxxid());
            }
            if(StringUtils.isNotBlank(bdcSlHsxxQO.getXmid())){
                criteria.andEqualTo("xmid", bdcSlHsxxQO.getXmid());
            }
            if(StringUtils.isNotBlank(bdcSlHsxxQO.getSqrlb())){
                criteria.andEqualTo("sqrlb", bdcSlHsxxQO.getSqrlb());
            }
            if(StringUtils.isNotBlank(bdcSlHsxxQO.getHsxxlx())){
                if(StringUtils.equals("0", bdcSlHsxxQO.getHsxxlx())){
                    criteria.andEqualNvlTo("hsxxlx", bdcSlHsxxQO.getHsxxlx(), "0");
                }else{
                    criteria.andEqualTo("hsxxlx", bdcSlHsxxQO.getHsxxlx());
                }
            }
            return entityMapper.deleteByExampleNotNull(example);
        }
        return 0;
    }

    @Override
    public int updateBdcSlHsxx(BdcSlHsxxDO bdcSlHsxxDO) {
        int result;
        if (bdcSlHsxxDO != null && StringUtils.isNotBlank(bdcSlHsxxDO.getHsxxid())) {
            result = entityMapper.updateByPrimaryKeySelective(bdcSlHsxxDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;

    }

    @Override
    public List<BdcSwxxDTO> queryBdcSwxxDTO(String xmid, String sqrlb) {
        List<BdcSwxxDTO> bdcSwxxDTOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            BdcSlHsxxDO cxbdcSlHsxxDO = new BdcSlHsxxDO();
            cxbdcSlHsxxDO.setXmid(xmid);
            cxbdcSlHsxxDO.setSqrlb(sqrlb);
            List<BdcSlHsxxDO> bdcSlHsxxDOList = listBdcSlHsxx(cxbdcSlHsxxDO);
            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                    BdcSwxxDTO bdcSwxxDTO = new BdcSwxxDTO();

                    List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList = bdcSlHsxxMxService.listBdcSlHsxxMxByHsxxid(bdcSlHsxxDO.getHsxxid());
                    if (CollectionUtils.isNotEmpty(bdcSlHsxxMxDOList)) {
                        bdcSwxxDTO.setBdcSlHsxxMxDOList(bdcSlHsxxMxDOList);
                    }
                    // 当纳税人识别号、税票号码、税务机关代码为空时，获取三要素信息
                    if(StringUtils.isAnyBlank(bdcSlHsxxDO.getNsrsbh(), bdcSlHsxxDO.getSphm(), bdcSlHsxxDO.getSwjgdm())){
                        List<BdcSlSysxxDO> bdcSlSysxxDOList = getBdcSlSysxxByHsxxid(bdcSlHsxxDO.getHsxxid());
                        if(CollectionUtils.isNotEmpty(bdcSlSysxxDOList)){
                            BdcSlSysxxDO bdcSlSysxxDO = bdcSlSysxxDOList.get(0);
                            bdcSlHsxxDO.setNsrsbh(bdcSlSysxxDO.getNsrsbh());
                            bdcSlHsxxDO.setSphm(bdcSlSysxxDO.getDzsphm());
                            bdcSlHsxxDO.setSwjgdm(bdcSlSysxxDO.getSwjgdm());
                        }
                    }
                    bdcSwxxDTO.setBdcSlHsxxDO(bdcSlHsxxDO);
                    bdcSwxxDTOList.add(bdcSwxxDTO);
                }
            }
        }
        return bdcSwxxDTOList;
    }

    private List<BdcSlSysxxDO> getBdcSlSysxxByHsxxid(String hsxxid){
        if(StringUtils.isNotBlank(hsxxid)){
           return this.bdcSlSysService.listBdcSlSysxx(hsxxid, null, null);
        }
        return null;
    }

    /**
     * 根据项目ID与纳税人识别号信息更新受理核税信息
     * <p>先根据项目ID与纳税人识别号查询申请人信息表（BDC_SL_SQR）。
     * 在根据申请人类别区分为权利人还是义务人，来更新核税信息。
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: BdcSlHsxxDO 核税信息
     * @return: Integer 修改结果
     */
    @Override
    public Integer updateBdcSlHsxxByXmidAndNsrsbh(BdcSlHsxxDO bdcSlHsxxDO) {
        if (null != bdcSlHsxxDO) {
            Example example = new Example(BdcSlHsxxDO.class, false);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(bdcSlHsxxDO.getXmid())) {
                criteria.andEqualTo("xmid", bdcSlHsxxDO.getXmid());
            }
            if (StringUtils.isNotBlank(bdcSlHsxxDO.getNsrsbh())) {
                criteria.andEqualTo("nsrsbh", bdcSlHsxxDO.getNsrsbh());
            }
            if (StringUtils.isNotBlank(bdcSlHsxxDO.getHsxxid())) {
                criteria.andEqualTo("hsxxid", bdcSlHsxxDO.getHsxxid());
            }
            return entityMapper.updateByExampleSelectiveNotNull(bdcSlHsxxDO, example);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
    }

    @Override
    public void updateBatchWszt(Integer wszt, String gzlslid) {
        if (wszt != null && StringUtils.isNotBlank(gzlslid)) {
            Map map = new HashMap();
            map.put("wszt", wszt);
            map.put("gzlslid", gzlslid);
            bdcSlHsxxMapper.updateBatchWszt(map);
        }

    }

    /**
     * @param wszt
     * @param gzlslid
     * @param qlrlb
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新完税状态
     */
    @Override
    public void updateBatchWsztByQlrlb(Integer wszt, String gzlslid, String qlrlb) {
        if (wszt != null && StringUtils.isNotBlank(gzlslid)) {
            Map map = new HashMap();
            map.put("wszt", wszt);
            map.put("gzlslid", gzlslid);
            map.put("sqrlb", qlrlb);
            bdcSlHsxxMapper.updateWsztByqlrlb(map);
        }
    }

    @Override
    public List<BdcSlHsxxDO> listBdcSlHsxxBySlbh(String slbh) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("缺失受理编号");
        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxBySlbh(slbh);
        if (CollectionUtils.isEmpty(bdcXmDTOList)) {
            throw new AppException("项目信息为空");
        }
        List<BdcSlHsxxDO> bdcSlHsxxDOList = new ArrayList<>();
        for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
            BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
            bdcSlHsxxDO.setXmid(bdcXmDTO.getXmid());
            List<BdcSlHsxxDO> hsxxDOList = listBdcSlHsxx(bdcSlHsxxDO);
            if (CollectionUtils.isNotEmpty(hsxxDOList)) {
                bdcSlHsxxDOList.addAll(hsxxDOList);
            }
        }

        return bdcSlHsxxDOList;


    }

    @Override
    public void batchUpdateBdcSlHsxx(BdcSlHsxxDO bdcSlHsxxDO, String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("工作流实例ID");
        }
        Map map = new HashMap();
        if (StringUtils.isNotBlank(bdcSlHsxxDO.getJypzh())) {
            map.put("jypzh", bdcSlHsxxDO.getJypzh());
        }
        if (null != bdcSlHsxxDO.getYhjkrkzt()) {
            map.put("yhjkrkzt", bdcSlHsxxDO.getYhjkrkzt());
        }
        if (null != bdcSlHsxxDO.getYtsswzt()) {
            map.put("ytsswzt", bdcSlHsxxDO.getYtsswzt());
        }
        map.put("gzlslid", gzlslid);
        bdcSlHsxxMapper.batchUpdateBdcSlHsxx(map);
    }

    /**
     * @param bdcSlHsxxDO 不动产受理核税信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增受理核税信息
     * @date : 2020/5/19 14:08
     */
    @Override
    public Integer insertBdcSlHsxx(BdcSlHsxxDO bdcSlHsxxDO) {
        if (StringUtils.isBlank(bdcSlHsxxDO.getHsxxid())) {
            bdcSlHsxxDO.setHsxxid(UUIDGenerator.generate16());
        }
        return entityMapper.insertSelective(bdcSlHsxxDO);
    }

    /**
     * @param swHsztDTO 核税信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新核税状态
     * @date : 2020/10/28 15:05
     */
    @Override
    public Integer updateHszt(SwHsztDTO swHsztDTO) {
        if (StringUtils.isBlank(swHsztDTO.getHtbh())) {
            throw new AppException("更新核税状态缺失交易合同号");
        }
        int count = 0;
        List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByHtbh(swHsztDTO.getHtbh());
        if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
            for (BdcSlJyxxDO slJyxxDO : bdcSlJyxxDOList) {
                BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
                bdcSlHsxxQO.setXmid(slJyxxDO.getXmid());
                List<BdcSlHsxxDO> bdcSlHsxxDOList = this.listBdcSlHsxxByHsxxQo(bdcSlHsxxQO);
                if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                    for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                        bdcSlHsxxDO.setHszt(swHsztDTO.getHszt());
                        if (Objects.nonNull(swHsztDTO.getWszt())) {
                            bdcSlHsxxDO.setWszt(swHsztDTO.getWszt());

                        }
                        bdcSlHsxxDO.setThyy(swHsztDTO.getThyy());
                        if(CommonConstantUtils.WSZT_YHS.equals(String.valueOf(swHsztDTO.getHszt()))){
                            bdcSlHsxxDO.setThyy(null);
                        }
                        count += entityMapper.updateByPrimaryKey(bdcSlHsxxDO);
                    }
                } else {
                    if (null == bdcSlHsxxDOList) {
                        bdcSlHsxxDOList = new ArrayList<>();
                    }
                    BdcSlXmDO bdcSlXmDO = bdcSlXmService.queryBdcSlXmByXmid(bdcSlJyxxDOList.get(0).getXmid());
                    if (Objects.nonNull(bdcSlXmDO)) {
                        String jbxxid = bdcSlXmDO.getJbxxid();
                        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(jbxxid, "xmid");
                        for (BdcSlXmDO bdcSlXm : bdcSlXmDOList) {
                            BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                            bdcSlHsxxDO.setHsxxid(UUIDGenerator.generate16());
                            bdcSlHsxxDO.setXmid(bdcSlXm.getXmid());
                            bdcSlHsxxDO.setHszt(swHsztDTO.getHszt());
                            if (Objects.nonNull(swHsztDTO.getWszt())) {
                                bdcSlHsxxDO.setWszt(swHsztDTO.getWszt());
                            }
                            bdcSlHsxxDO.setThyy(swHsztDTO.getThyy());
                            bdcSlHsxxDOList.add(bdcSlHsxxDO);
                        }
                        count += entityMapper.insertBatchSelective(bdcSlHsxxDOList);
                    }
                }
            }
        }
        return count;
    }

    @Override
    public List<BdcSlHsxxDO> listBdcSlHsxxByGzlslid(String gzlslid,String slbh) {
        if (StringUtils.isNotBlank(gzlslid) ||StringUtils.isNotBlank(slbh)) {
            return this.bdcSlHsxxMapper.listBdcSlHsxxByGzlslid(gzlslid,slbh);
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public Integer recreateHsxx(String xmid,List<BdcSlHsxxDO> bdcSlHsxxDOList,List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList){
        int countHx = 0;
        int countMx = 0;
        if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList) &&StringUtils.isNotBlank(xmid)) {
            //删除已有的核税信息
            deleteSwxx(xmid);

            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                countHx = entityMapper.insertBatchSelective(bdcSlHsxxDOList);

            }
            if (CollectionUtils.isNotEmpty(bdcSlHsxxMxDOList)) {
                countMx =entityMapper.insertBatchSelective(bdcSlHsxxMxDOList);
            }
            LOGGER.info("xmid:{}执行重新生成操作,重新插入核税信息：{}条,核税明细：{}条",xmid,countHx,countMx);
        }
        return countHx;
    }

    @Override
    public void deleteSwxx(String xmid) {
        if(StringUtils.isNotBlank(xmid)) {
            BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
            bdcSlHsxxDO.setXmid(xmid);
            List<BdcSlHsxxDO> bdcSlHsxxDOList = listBdcSlHsxx(bdcSlHsxxDO);
            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                delBdcSlHsxx(bdcSlHsxxDO);
                for (BdcSlHsxxDO delbdcSlHsxxDO : bdcSlHsxxDOList) {
                    bdcSlHsxxMxService.delBdcSlHsxxMxByHsxxid(delbdcSlHsxxDO.getHsxxid());
                }
            }
        }
    }

    @Override
    public List<BdcSlHsxxDO> queryQlrSwxx(String xmid, String sqrlb) {
        List<BdcSlHsxxDO> list = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            BdcSlHsxxDO cxbdcSlHsxxDO = new BdcSlHsxxDO();
            cxbdcSlHsxxDO.setXmid(xmid);
            cxbdcSlHsxxDO.setSqrlb(sqrlb);
            list = listBdcSlHsxx(cxbdcSlHsxxDO);
        }
        return list;
    }

    @Override
    public List<BdcSlHsxxDO> listBdcSlHsxxByGzlslidAndSqrlb(String gzlslid, String sqrlb) {
        if (StringUtils.isNotBlank(gzlslid) || StringUtils.isNotBlank(sqrlb)) {
            return this.bdcSlHsxxMapper.listBdcSlHsxxByGzlslidAndSqrlb(gzlslid, sqrlb);
        }
        return new ArrayList<>();
    }
}

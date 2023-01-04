package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.register.BdcDjSzfgfDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFgfFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.rest.register.BdcZdRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.register.core.mapper.BdcFgfMapper;
import cn.gtmap.realestate.register.core.qo.BdcXmGxQO;
import cn.gtmap.realestate.register.service.BdcFgfService;
import cn.gtmap.realestate.register.service.BdcQlxxService;
import cn.gtmap.realestate.register.service.BdcXmxxService;
import cn.gtmap.realestate.register.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/17
 * @description 不动产房改房服务实现类
 */
@Service
public class BdcFgfServiceImpl implements BdcFgfService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcFgfServiceImpl.class);

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcFgfMapper bdcFgfMapper;

    @Autowired
    BdcQlxxService bdcQlxxService;
    @Autowired
    BdcXmxxService bdcXmxxService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcZdRestService bdcZdRestService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    BdcSlFgfFeignService bdcSlFgfFeignService;


    /**
     * @param bdcFgfDOList 批量的房改房信息
     * @return int 执行的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存房改房项目信息
     */
    @Override
    public int saveFgfxm(List<BdcFgfDO> bdcFgfDOList) {
        if (CollectionUtils.isEmpty(bdcFgfDOList)) {
            throw new MissingArgumentException("缺失数据bdcFgfDOList");
        }
        int result = 0;
        List<List> lists = ListUtils.subList(bdcFgfDOList, 500);
        for (List bdcFgfList : lists) {
            result += entityMapper.insertBatchSelective(bdcFgfList);
        }
        return result;
    }

    /**
     * @param bdcFgfDOList 批量的房改房信息
     * @return int 执行的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新房改房项目信息
     */
    @Override
    public int updateFgfxm(List<BdcFgfDO> bdcFgfDOList) {
        if (CollectionUtils.isEmpty(bdcFgfDOList)) {
            throw new MissingArgumentException("缺失数据bdcFgfDOList");
        }
        int result = 0;
        for (BdcFgfDO bdcFgfDO : bdcFgfDOList) {
            if (StringUtils.isNotBlank(bdcFgfDO.getId())) {
                result += entityMapper.updateByPrimaryKey(bdcFgfDO);
            }
        }
        return result;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return List<BdcFgfDO>  查询到的房改房信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程的房改房信息
     */
    @Override
    public List<BdcFgfDO> getLcFgfxm(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失查询参数gzlslid！");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        return bdcFgfMapper.listBdcFgf(bdcXmQO);
    }


    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 现省直房改房上市交易成功后，把不动产登记的数据再推送给房改办进行登记
     */
    @Override
    public void djSzfgb(String processInsId){
        if(StringUtils.isBlank(processInsId)){
            throw new MissingArgumentException("缺失工作流实例id");
        }
        String djyy = bdcSlFgfFeignService.getDjyy();
        List<BdcXmDO> listXm = bdcXmxxService.getListBdcXmByGzlslid(processInsId);
        if(CollectionUtils.isNotEmpty(listXm)){
            Map<String, List<Map>> zdMap = bdcZdRestService.listBdcZd();
            for(BdcXmDO bdcXmDO : listXm){
                if(djyy.indexOf(bdcXmDO.getDjyy()) == -1 ){
                    continue;
                }
                BdcDjSzfgfDTO bdcDjSzfgfDTO = new BdcDjSzfgfDTO();

                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(bdcXmDO.getXmid());
                List<BdcQlrDO> listQlr = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                String ywrDhs = "";
                if(CollectionUtils.isNotEmpty(listQlr)){
                    for(BdcQlrDO bdcQlrDO : listQlr){
                        if(bdcQlrDO.getQlrlb().equals(CommonConstantUtils.QLRLB_YWR)){
                            ywrDhs += bdcQlrDO.getDh()+",";
                        }else{
                            bdcDjSzfgfDTO.setGyqk(bdcQlrDO.getGyqk());
                        }
                    }
                }
                if(StringUtils.isNotBlank(ywrDhs)){
                    ywrDhs = ywrDhs.substring(0,ywrDhs.length()-1);
                    bdcDjSzfgfDTO.setMobile(ywrDhs);
                }

                bdcDjSzfgfDTO.setYwh(bdcXmDO.getSlbh());
                bdcDjSzfgfDTO.setXm(bdcXmDO.getYwr());
                bdcDjSzfgfDTO.setSfzhm(bdcXmDO.getYwrzjh());
                bdcDjSzfgfDTO.setQlr(bdcXmDO.getQlr());
                bdcDjSzfgfDTO.setQlrsfzhm(bdcXmDO.getQlrzjh());
                bdcDjSzfgfDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                bdcDjSzfgfDTO.setFwzl(bdcXmDO.getZl());
                if(null != bdcXmDO.getDjsj()){
                    bdcDjSzfgfDTO.setDjsj(DateUtils.formateTime(bdcXmDO.getDjsj(),DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA)));
                }else{
                    bdcDjSzfgfDTO.setDjsj(DateUtils.formateTime(new Date(),DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA)));
                }
                bdcDjSzfgfDTO.setBdcqzh(bdcXmDO.getBdcqzh());

                BdcQl bdcql = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                if(bdcql instanceof BdcFdcqDO){
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO)bdcql;
                    bdcDjSzfgfDTO.setJzmj(bdcFdcqDO.getJzmj()+"");
                    bdcDjSzfgfDTO.setTnmj(bdcFdcqDO.getZyjzmj()+"");
                    bdcDjSzfgfDTO.setGyftmj(bdcFdcqDO.getFtjzmj()+"");
                    bdcDjSzfgfDTO.setFwxz(StringToolUtils.convertBeanPropertyValueOfZd(
                            bdcFdcqDO.getFwxz(), zdMap.get(Constants.FWXZ)));
                    bdcDjSzfgfDTO.setGhyt(StringToolUtils.convertBeanPropertyValueOfZd(
                            bdcFdcqDO.getGhyt(), zdMap.get(Constants.GHYT)));

                }

                // 获取原项目信息,需求要求附记要取上一手产权的附记
                BdcXmGxQO bdcXmGxQO = new BdcXmGxQO();
                bdcXmGxQO.setXmid(bdcXmDO.getXmid());
                bdcXmGxQO.setZxyql(CommonConstantUtils.SF_S_DM);
                List<BdcXmDO> bdcYxmDOList = bdcXmxxService.getListYbdcXm(bdcXmGxQO);
                if(CollectionUtils.isNotEmpty(bdcYxmDOList)){
                    LOGGER.info("查询原项目信息数量：{}",bdcYxmDOList.size());
                    String yxmid = bdcYxmDOList.get(0).getXmid();
                    BdcQl yBdcql = bdcQllxFeignService.queryQlxx(yxmid);
                    if(yBdcql instanceof BdcFdcqDO){
                        BdcFdcqDO bdcFdcqDO = (BdcFdcqDO)yBdcql;
                        bdcDjSzfgfDTO.setFuji(bdcFdcqDO.getFj());
                    }
                }

                LOGGER.info("登记房改房调用ex省级推送入参：{}",bdcDjSzfgfDTO.toString());
                Object obj = exchangeInterfaceFeignService.requestInterface("fgf_sjts",bdcDjSzfgfDTO);
                if(null != obj){
                    Map map = (Map)obj;
                    LOGGER.info("登记房改房调用ex省级推送返参：{}",map);
                }
            }
        }
    }
}

package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.service.ReadZdtService;
import cn.gtmap.realestate.building.service.ZdtService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.common.core.domain.building.CbztsytDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-08
 * @description 宗地图相关业务逻辑服务
 */
@Service
@Validated
public class ZdtServiceImpl implements ZdtService {

    private static Logger LOGGER = LoggerFactory.getLogger(ZdtServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private ReadZdtHefeiFtpServiceImpl readZdtHefeiFtpServiceImpl;

    /**
     * @param
     * @return cn.gtmap.realestate.building.service.ReadZdtService
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 通过配置 获取  读宗地图的实现服务
     */
    private static ReadZdtService getConfigReadService() {
        String beanId = EnvironmentConfig.getEnvironment().getProperty("zdtService");
        if (StringUtils.isBlank(beanId)) {
            // 如果没有配置 默认 走合肥实现
            beanId = "readZdtHefeiFtpServiceImpl";
        }
        Object readZdtService = Container.getBean(beanId);
        return (ReadZdtService) readZdtService;
    }

    /**
     * @param djh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询宗地图(返回base64)
     */
    @Override
    public String getZdtByDjh(@NotBlank String djh) {
        ReadZdtService readZdtService = getConfigReadService();
        return readZdtService.readBase64ByDjh(djh);
    }

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询宗地图
     */
    @Override
    public String getZdtByBdcdyh(String bdcdyh,String qjgldm) {
        ReadZdtService readZdtService = getConfigReadService();
        return readZdtService.readBase64ByBdcdyh(bdcdyh,qjgldm);
    }

    /**
     * 根据合同编号查询承包土地图层信息
     *
     * @param htbh 合同编号
     * @return ZdtResponseDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 13:55 2020/8/24
     */
    @Override
    public String getCbzdtByHtbh(String htbh) {
        if (StringUtils.isNotBlank(htbh)) {
            Example example = new Example(CbztsytDO.class);
            example.createCriteria().andEqualTo("cbhtbh", htbh);
            List<CbztsytDO> zdtList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(zdtList)) {
                return BuildingUtils.blobToStr(zdtList.get(0).getCbzdsyt_img());
            }
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据BDCDYH查询宗地图-ftp
     */
    @Override
    public String queryZdtByBdcdyhFtp(String bdcdyh) {
        return readZdtHefeiFtpServiceImpl.getZdtByBdcdyhByFTP(bdcdyh);
    }

    /**
     * @param djh
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据DJH查询宗地图(返回文件中心id)

     @Override public String getZdtIDByDjh(String djh) throws IOException {
     ReadZdtService readZdtService = getConfigReadService();
     return readZdtService.getZdtIdByDjh(djh);
     }*/

}

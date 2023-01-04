package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmZsGxDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.init.core.mapper.BdcZsMapper;
import cn.gtmap.realestate.init.core.service.BdcZsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/13.
 * @description
 */
@Service
@Validated
public class BdcZsServiceImpl implements BdcZsService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcZsMapper bdcZsMapper;

    /**
     * 通过证书id获取证书信息
     *
     * @param zsid 证书id
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public BdcZsDO queryBdcZsById(String zsid) {
        if (StringUtils.isNotBlank(zsid)) {
            return entityMapper.selectByPrimaryKey(BdcZsDO.class, zsid);
        }
        return null;
    }

    /**
     * 更新证书信息
     *
     * @param bdcZs 不动产证书
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public void updateBdcZs(BdcZsDO bdcZs) {
        if (bdcZs != null && StringUtils.isNotBlank(bdcZs.getZsid())) {
            entityMapper.updateByPrimaryKeyNull(bdcZs);
        }
    }

    /**
     * 通过项目id获取证书信息
     *
     * @param xmid 项目id
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<BdcZsDO> queryBdcZsByXmid(String xmid) {
        if(StringUtils.isNotBlank(xmid)){
            BdcXmZsGxDO bdcXmZsGxDO=new BdcXmZsGxDO();
            bdcXmZsGxDO.setXmid(xmid);
            List<BdcXmZsGxDO> list=entityMapper.selectByObj(bdcXmZsGxDO);
            if(CollectionUtils.isNotEmpty(list)){
                List<BdcZsDO> listZs=new ArrayList<>();
                for(BdcXmZsGxDO bdcXmZsGx:list){
                    BdcZsDO bdcZsDO=entityMapper.selectByPrimaryKey(BdcZsDO.class,bdcXmZsGx.getZsid());
                    if(bdcZsDO!=null){
                        listZs.add(bdcZsDO);
                    }
                }
                return listZs;
            }
        }
        return Collections.emptyList();
    }

    /**
     * 删除流程证书
     *
     * @param gzlslid 工作流实例ID
     * @return
     */
    @Override
    public void deleteZs(@NotBlank(message = "工作流实例ID不能为空") String gzlslid) {
        bdcZsMapper.deleteZs(gzlslid);
    }

    /**
     * @param xmid@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid 删除证书
     * @date : 2022/12/27 10:48
     */
    @Override
    public void deleteZsByxmid(@NotBlank(message = "项目ID不能为空") String xmid) {
        bdcZsMapper.deleteZsByXmid(xmid);
    }

    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid 删除项目证书关系
     * @date : 2022/12/27 10:52
     */
    @Override
    public void deleteXmzsGx(@NotBlank(message = "项目ID不能为空") String xmid) {
        bdcZsMapper.deleteXmZsgx(xmid);
    }

    /**
     * 删除流程证书关系
     *
     * @param gzlslid 工作流实例ID
     * @return
     */
    @Override
    public void deleteZsXmGx(@NotBlank(message = "工作流实例ID不能为空") String gzlslid) {
        bdcZsMapper.deleteZsXmGx(gzlslid);
    }
}

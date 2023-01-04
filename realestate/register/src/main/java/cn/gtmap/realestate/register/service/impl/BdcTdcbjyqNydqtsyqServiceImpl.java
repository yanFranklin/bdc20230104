package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcTdcbnydsyqDO;
import cn.gtmap.realestate.common.core.dto.register.BdcDkxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDkxxQO;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.register.core.mapper.BdcTdcbnydsyqMapper;
import cn.gtmap.realestate.register.service.BdcTdcbjyqNydqtsyqService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BdcTdcbjyqNydqtsyqServiceImpl implements BdcTdcbjyqNydqtsyqService {

    @Autowired
    BdcTdcbnydsyqMapper tdcbnydsyqMapper;

    /**
     * 根据项目id查土地承包经营权信息
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 16:53 2020/8/25
     */
    @Override
    public BdcTdcbnydsyqDO queryByxmid(String xmid) {
        return tdcbnydsyqMapper.queryByxmid(xmid);
    }

    /**
     * 根据gzlslid查询地块信息
     *
     * @param gzlslid
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 10:23 2020/8/25
     */
    @Override
    public List<BdcDkxxDTO> queryDkxxBygzlslid(String gzlslid) {
        return tdcbnydsyqMapper.queryDkxxBygzlslid(gzlslid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDkxxQO 查询实体
     * @return {List} 地块信息列表
     * @description 土地承包经营权查询项目或证书关联的地块列表
     */
    @Override
    public List<BdcDkxxDTO> queryTdcbjyqDkxx(BdcDkxxQO bdcDkxxQO) {
        if (null == bdcDkxxQO || StringToolUtils.isAllNullOrEmpty(bdcDkxxQO.getGzlslid(), bdcDkxxQO.getSlbh(), bdcDkxxQO.getXmid(), bdcDkxxQO.getZsid())) {
            throw new MissingArgumentException("未定义查询参数");
        }
        return tdcbnydsyqMapper.queryTdcbjyqDkxx(bdcDkxxQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDkxxQO 查询实体
     * @return {List} 地块信息列表
     * @description 未生成证书情况下查找目标证书 土地承包经营权地块列表
     */
    @Override
    public List<BdcDkxxDTO> queryTdcbjyqDkxxBeforeZsInit(BdcDkxxQO bdcDkxxQO) {
        if (null == bdcDkxxQO || StringToolUtils.isBlank(bdcDkxxQO.getXmid())) {
            throw new MissingArgumentException("未定义查询参数");
        }
        return tdcbnydsyqMapper.queryTdcbjyqDkxxBeforeZsInit(bdcDkxxQO);
    }
}

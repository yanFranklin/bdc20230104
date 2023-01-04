package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.BdcTdcbnydsyqDO;
import cn.gtmap.realestate.common.core.dto.register.BdcDkxxDTO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDkxxQO;

import java.util.List;

public interface BdcTdcbjyqNydqtsyqService {

    BdcTdcbnydsyqDO queryByxmid(String xmid);

    /**
     * 根据gzlslid查询地块信息
     *
     * @param gzlslid
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 10:23 2020/8/25
     */
    List<BdcDkxxDTO> queryDkxxBygzlslid(String gzlslid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDkxxQO 查询实体
     * @return {List} 地块信息列表
     * @description 土地承包经营权查询项目或证书关联的地块列表
     */
    List<BdcDkxxDTO> queryTdcbjyqDkxx(BdcDkxxQO bdcDkxxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDkxxQO 查询实体
     * @return {List} 地块信息列表
     * @description 未生成证书情况下查找目标证书 土地承包经营权地块列表
     */
    List<BdcDkxxDTO> queryTdcbjyqDkxxBeforeZsInit(BdcDkxxQO bdcDkxxQO);
}

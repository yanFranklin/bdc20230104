package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlZjjgDO;
import cn.gtmap.realestate.common.core.dto.accept.YthZjjgDto;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.zjjg.ZjjgResponseDto;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlZjjgQO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/22
 * @description
 */
public interface BdcSlZjjgService {

    /**
     * @param gzlslid 工作流实例ID
     * @return 资金监管信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取资金监管信息
     */
    List<BdcSlZjjgDO> listBdcSlZjjg(String gzlslid);

    /**
     * @param xmid 受理项目ID
     * @return 资金监管信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据受理项目ID获取资金监管信息
     */
    List<BdcSlZjjgDO> listBdcSlZjjgByXmid(String xmid);

    /**
     * @param bdcSlZjjgQO 资金监管查询QO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询资金监管
     * @date : 2021/7/22 15:58
     */
    List<BdcSlZjjgDO> listBdcSlZjjg(BdcSlZjjgQO bdcSlZjjgQO);

    /**
      * @param ywlx 业务类型
      * @return 工作流定义ID集合
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取工作流定义ID集合
      */
    List<String> getFdjywlcDyidList(String ywlx);

    /**
     * 更新资金监管信息状态为已撤销
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void updateZjjgZtYcx(String gzlslid);

    /**
     * 更新项目附表是否资金监管为是
     * <p>资金监管流程办结事件调用，更新项目附表是否资金监管为：是</p>
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void updateZjjgXmfbSfzjjg(String gzlslid);

    /**
     * 更新项目附表是否资金监管为否
     * <p>资金监管撤销流程办结事件调用，更新项目附表是否资金监管为：否</p>
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void updateZjjgCxXmfbSfzjjg(String gzlslid);

    /**
     * 异步保存资金监管一体化平台的返回值
     * @param zjjgResponseDto
     */
    void saveYthptZjjg(ZjjgResponseDto zjjgResponseDto);

    /**
     * 查询平台资金监管
     * @param htbh
     * @return
     */
    YthZjjgDto listYthZjjg(String htbh);

    /**
     * 查询平台资金监管附件
     * @param htbh
     * @return
     */
    StorageDto getFileStorageUrl(String htbh);
}

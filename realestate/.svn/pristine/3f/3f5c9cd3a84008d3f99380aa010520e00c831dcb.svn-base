package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcXtLscsDO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2021/01/14
 * @description 临时参数表操作服务
 */
public interface BdcXtLscsRestService {
    /**
     * 批量添加临时参数数据
     * @param lscsDOList 参数数据
     * @return {int} 新增记录数量
     * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/lscs")
    int addValues(@RequestBody List<BdcXtLscsDO> lscsDOList);

    /**
     * 批量删除临时参数数据
     * @param lscsDOList 参数数据
     * @return {int} 删除记录数量
     * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
     */
    @DeleteMapping(value = "/realestate-inquiry/rest/v1.0/lscs")
    int deleteRecords(@RequestBody List<BdcXtLscsDO> lscsDOList);
}

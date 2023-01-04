package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhcxDyzmTjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcZhcxDyzmTjQO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/7/17
 * @description 查询子系统：综合查询打印证明统计
 */
public interface BdcZhcxTjRestService {

    /**
     * @param bdcZhcxDyzmTjQO 综合查询打印证明统计查询条件
     * @return 综合查询打印证明统计结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 综合查询打印证明统计
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zhcxtj")
    BdcZhcxDyzmTjDTO zhcxDyzmTj(@RequestBody BdcZhcxDyzmTjQO bdcZhcxDyzmTjQO);

}

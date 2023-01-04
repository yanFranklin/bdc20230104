package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.inquiry.ZipkinExportDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/07/18
 * @description
 */
public interface ZipKinRestService {
    /***
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * 新增前4小时慢服务信息
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/zfxx/zipKininfo")
    CommonResponse zipKinInfo();

    /**
     * @return
     * 导出指定数量日期的最耗时统计信息
     * @param endDate 中止日期
     * @param startDate 起始日期
     * @param num 指定导出数量
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zfxx/exportExcel")
    ZipkinExportDTO getExportExcel(@RequestParam(value = "num") Integer num,@RequestParam(value = "startDate") String startDate
            ,@RequestParam(value = "endDate") String endDate);
}

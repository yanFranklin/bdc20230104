package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.ZipKinInformationDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.ZipkinExportDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/06/30
 * @description
 */
public interface ZipKinInfromationService {


    /***
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 获取链路数据，将耗时时间、服务名插入表中
    */
    List<ZipKinInformationDTO> insertZipKinMsg();

    /**
     * 耗时统计导出到Excel
     */
    ZipkinExportDTO exportExcel(Integer num,String startDate,String endDate);
}

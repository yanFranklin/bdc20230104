package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.inquiry.ZipKinInformationDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.ZipkinExportDTO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.ZipKinRestService;
import cn.gtmap.realestate.inquiry.service.ZipKinInfromationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/07/01
 * @description
 */
@RestController
public class ZipKinInformationController implements ZipKinRestService {
    @Autowired
    private ZipKinInfromationService zipKinInfromationService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("新增前4小时慢服务信息")
    public CommonResponse zipKinInfo(){
        List<ZipKinInformationDTO> list = zipKinInfromationService.insertZipKinMsg();
        return CommonResponse.ok(list);
    }


    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("导出耗时统计信息")
    public ZipkinExportDTO getExportExcel(Integer num,String startDate,String endDate) {
        return zipKinInfromationService.exportExcel(num,startDate,endDate);
    }
}

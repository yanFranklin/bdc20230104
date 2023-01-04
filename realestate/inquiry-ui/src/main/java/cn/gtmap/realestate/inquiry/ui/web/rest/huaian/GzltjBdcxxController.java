package cn.gtmap.realestate.inquiry.ui.web.rest.huaian;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.huaian.BdcGzltjXmxxFeignService;
import cn.gtmap.realestate.inquiry.ui.core.qo.BdcXtOrgQO;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import cn.gtmap.realestate.inquiry.ui.web.rest.config.ZtreeController;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/6/27
 * @description  工作量统计前端控制层, 根据不动产项目数据统计人员办件量信息，不调用大云接口进行统计
 */
@Api(tags = "工作量统计")
@RestController
@RequestMapping("/rest/v1.0/gzltj/bdcxx")
public class GzltjBdcxxController extends BaseController {

    @Autowired
    BdcGzltjXmxxFeignService bdcGzltjXmxxFeignService;

    @Value("${cxtj.sfglbm:false}")
    private boolean sfglbm;

    @Autowired
    ZtreeController ztreeController;

    /**
     * 根据不动产项目数据，统计人员工作量信息
     * @param param 查询参数：部门、统计类型、开始时间、结束时间
     * @return 工作量内容：办件量、打印有房无房、权属证明、登记簿数量
     */
    @PostMapping(value = "/list/rygzlxx")
    @ResponseStatus(HttpStatus.OK)
    public Object listRyGzltjByBdcxx(GzltjQO param) {
        if(StringUtils.isBlank(param.getTjlx())){
            throw new AppException(ErrorCode.MISSING_ARG, "未指定统计类型");
        }
        try{
            return super.addLayUiCode(bdcGzltjXmxxFeignService.listRyGzltjByBdcxx(param));
        }catch(Exception e){
            e.printStackTrace();
            return super.addLayUiErrorCode(e.getCause().getMessage());
        }
    }

    /**
     * 根据不动产项目数据，统计周期性工作量信息
     * @param param 查询参数：部门、统计类型、开始时间、结束时间
     * @return 工作量内容：办件量、打印有房无房、权属证明、登记簿数量
     */
    @PostMapping(value = "/list/zqxgzlxx")
    @ResponseStatus(HttpStatus.OK)
    public Object listZqxGzltjByBdcxx(GzltjQO param) {
        if(sfglbm && StringUtils.isBlank(param.getBmid())){
            List<OrganizationDto> organizationDtos = ztreeController.queryGlOrgs(new BdcXtOrgQO());
            if (CollectionUtils.isNotEmpty(organizationDtos)){
                param.setBmid(organizationDtos.stream().map(OrganizationDto::getId).collect(Collectors.joining(",")));
            }
        }
        if(StringUtils.isAnyBlank(param.getTjlx(), param.getDimension())){
            throw new AppException(ErrorCode.MISSING_ARG, "未指定统计类型或统计维度");
        }
        try{
            return super.addLayUiCode(bdcGzltjXmxxFeignService.listZqxGzltjByBdcxx(param));
        }catch(Exception e){
            e.printStackTrace();
            return super.addLayUiErrorCode(e.getCause().getMessage());
        }
    }

}

package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcYbtjQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcYbtjFeignService;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.util.PageUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/12/21
 * @description月报统计接口服务
 */
@RestController
@RequestMapping("/rest/v1.0/ybtj")
public class BdcYbtjController extends BaseController {

    @Autowired
    OrganizationManagerClientMatcher organizationManagerClient;
    @Autowired
    private BdcYbtjFeignService bdcYbtjFeignService;
    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 用户信息工具
     */
    @Autowired
    protected UserManagerUtils userManagerUtils;

    /**
     *  月报统计,配置的登记机构，可以查询所有其他登记机构,
     *  否则用户只能查询自己所属的登记机构
     */
    @Value("#{'${yb.djjg:}'.split(',')}")
    private List<String> ybdjjg;

    @GetMapping("/listOrgs")
    @ApiOperation(value = "获取所有组织")
    @ResponseStatus(HttpStatus.OK)
    public Object queryAllOrgs(){
        UserDto userDto = userManagerUtils.getCurrentUser();
        if(Objects.isNull(userDto)) {
            throw new AppException("月报统计时未获取到用户信息");
        }
        if(CollectionUtils.isEmpty(userDto.getOrgRecordList())){
            return null;
        }
        if(CollectionUtils.isNotEmpty(ybdjjg)){
            for(String djjg : ybdjjg){
                for(OrganizationDto organizationDto:userDto.getOrgRecordList()){
                    if(djjg.equals(organizationDto.getCode())){
                        return this.organizationManagerClient.listOrgs(1);
                    }
                }
            }
        }
        return userDto.getOrgRecordList();
    }

    @PostMapping("/bdcywtj")
    @ApiOperation(value = "获取业务统计")
    @ResponseStatus(HttpStatus.OK)
    public Object bdcywtj(@LayuiPageable Pageable pageable, BdcYbtjQO bdcYbtjQO){
        return super.addLayUiCode(PageUtils.listToPage(bdcYbtjFeignService.bdcywtj(bdcYbtjQO),pageable));
    }

    @PostMapping("/bdcywzbtj")
    @ApiOperation(value = "获取业务统计")
    @ResponseStatus(HttpStatus.OK)
    public Object bdcywzbtj(@LayuiPageable Pageable pageable, BdcYbtjQO bdcYbtjQO){
        return super.addLayUiCode(PageUtils.listToPage(bdcYbtjFeignService.bdcyzbwtj(bdcYbtjQO),pageable));
    }

}

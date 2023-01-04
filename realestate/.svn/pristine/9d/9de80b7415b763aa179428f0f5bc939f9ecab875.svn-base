package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcYzhFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;




/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/02/20
 * @description 证书补打
 */
@RestController
@RequestMapping(value = "/rest/v1.0/zsbd")
public class BdcZsbdController extends BaseController {

    @Autowired
    BdcSlSfxxFeignService bdcSlSfxxFeignService;

    @Autowired
    BdcYzhFeignService bdcYzhFeignService;

    @Autowired
    BdcZsFeignService bdcZsFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;


    /**
     * @param gzlslid 工作流实例ID
     * @return 缴费状态
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  查询缴费状态
     */
    @GetMapping(value = "/sfzt")
    @ResponseStatus(HttpStatus.OK)
    public Object checkSfqk(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)){
            throw new AppException("工作流实例id不能为空");
        }
        return bdcSlSfxxFeignService.checkSfqk(gzlslid);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    @PostMapping(value = "/yzh/syqk")
    @ResponseStatus(HttpStatus.OK)
    public Object changeAjzt(@RequestBody BdcYzhQO bdcYzhQO) {
        if (StringUtils.isBlank(bdcYzhQO.getQzysxlh())) {
            throw new MissingArgumentException("没有权证印刷序列号！");
        }
        if (StringUtils.isBlank(bdcYzhQO.getZsid())) {
            throw new MissingArgumentException("缺失证书ID！");
        }
        //根据前台传的zsid获取区县代码和证书类型
        BdcZsDO bdcZsDO =bdcZsFeignService.queryBdcZs(bdcYzhQO.getZsid());
        if(bdcZsDO ==null){
            throw new AppException("未获取到证书信息");
        }
        bdcYzhQO.setQxdm(bdcZsDO.getQxdm());
        bdcYzhQO.setZslx(bdcZsDO.getZslx());
        bdcYzhQO.setZsid(null);
        if (StringUtils.isBlank(bdcYzhQO.getQxdm())) {
            throw new MissingArgumentException("缺失区县代码！");
        }
        return  bdcYzhFeignService.queryYzhYztsxx(bdcYzhQO);
    }






}

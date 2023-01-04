package cn.gtmap.realestate.inquiry.ui.web.rest;


import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcHmdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcHmdFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.3, 2021-10-25
 * @description 不动产单元、产权证、人员，黑名单业务类
 */
@RestController
@RequestMapping(value = "/rest/v1.0/hmd")
public class BdcHmdController extends BaseController {

    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcHmdFeignService bdcHmdFeignService;


    /**
     * 根据工作流实例ID获取原项目的不动产权证信息
     * @param gzlslid 工作流实例ID
     * @return 不动产黑名单DO
     */
    @GetMapping("/info")
    public BdcHmdDO getBdcZssdBasicInfo(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID");
        }
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        bdcZsQO.setWlxm(CommonConstantUtils.SF_F_DM);
        List<BdcZsDO> bdcZsDOList = this.bdcZsFeignService.queryYxmZs(bdcZsQO);
        if(CollectionUtils.isEmpty(bdcZsDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID");
        }
        BdcHmdDO bdcHmdDO = new BdcHmdDO();
        bdcHmdDO.setGzldymc(this.getGzldymc(gzlslid));
        bdcHmdDO.setBdcqzh(bdcZsDOList.get(0).getBdcqzh());
        bdcHmdDO.setBdcdyh(bdcZsDOList.get(0).getBdcdyh());
        bdcHmdDO.setHmdztlb(CommonConstantUtils.HMD_ZT_ZS);
        bdcHmdDO.setCjsj(new Date());
        UserDto userDto = userManagerUtils.getCurrentUser();
        if(Objects.nonNull(userDto)){
            bdcHmdDO.setCjr(userDto.getAlias());
        }
        return bdcHmdDO;
    }

    /**
     * 获取工作流定义名称
     */
    private String getGzldymc(String gzlslid){
        BdcXmQO queryParam = new BdcXmQO();
        queryParam.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(queryParam);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new MissingArgumentException("未获取到项目信息");
        }
        return bdcXmDOList.get(0).getGzldymc();
    }

    /**
     * @param bdcHmdDO 不动产黑名单信息
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 保存不动产黑名单信息
     */
    @PostMapping("/save")
    public BdcHmdDO saveBdcZssd(@RequestBody BdcHmdDO bdcHmdDO) {
        if (Objects.isNull(bdcHmdDO)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺失黑名单信息");
        }
        // 新增黑名单信息时，验证是否存在现势锁定
        if(StringUtils.isBlank(bdcHmdDO.getHmdid())){
            BdcHmdDO queryparam = new BdcHmdDO();
            if(StringUtils.isNotBlank(bdcHmdDO.getBdcqzh())){
                queryparam.setBdcqzh(bdcHmdDO.getBdcqzh());
            }
            if(StringUtils.isNotBlank(bdcHmdDO.getZtmc())){
                queryparam.setZtmc(bdcHmdDO.getZtmc());
            }
            if(StringUtils.isNotBlank(bdcHmdDO.getZtzjh())){
                queryparam.setZtzjh(bdcHmdDO.getZtzjh());
            }
            queryparam.setHmdztlb(bdcHmdDO.getHmdztlb());
            queryparam.setHmdzt(CommonConstantUtils.HMD_STATUS_VALID);
            List<BdcHmdDO> hmdxx = this.bdcHmdFeignService.queryBdcHmd(queryparam);
            if(CollectionUtils.isNotEmpty(hmdxx)){
                throw new AppException(ErrorCode.CUSTOM, "已存在现势黑名单数据，无法继续添加。");
            }
        }
        if(Objects.isNull(bdcHmdDO.getCjsj())){
            bdcHmdDO.setCjsj(new Date());
        }
        return this.bdcHmdFeignService.saveBdcHmd(bdcHmdDO);
    }

    /**
     * 批量删除黑名单信息
     * @param hmdidList 黑名单ID信息集合
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @DeleteMapping(value="/sc")
    public void saveBdcZssd(@RequestBody List<String> hmdidList) {
        if(CollectionUtils.isEmpty(hmdidList)){
            throw new MissingArgumentException("缺失黑名单id信息");
        }
        this.bdcHmdFeignService.batchDeleteBdcHmd(hmdidList);
    }

    /**
     * 批量解锁黑名单信息
     * @param bdcHmdDOList 黑名单信息集合
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/js")
    public void jsBdczs(@RequestBody List<BdcHmdDO> bdcHmdDOList) {
        if (CollectionUtils.isEmpty(bdcHmdDOList)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到黑名单信息");
        }
        this.bdcHmdFeignService.jsBljlxx(bdcHmdDOList);
    }
}

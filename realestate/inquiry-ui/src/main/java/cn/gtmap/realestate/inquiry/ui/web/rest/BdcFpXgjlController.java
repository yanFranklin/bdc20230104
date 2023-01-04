package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFpxxXgjlDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFpXgjlFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/11/19.
 * @description 不动发票修改记录查询
 */
@RestController
@RequestMapping("/rest/v1.0/fpxgjl")
public class BdcFpXgjlController extends BaseController {

    @Autowired
    BdcSlFpXgjlFeignService bdcSlFpXgjlFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * 发票修改记录分页查询
     * @param pageable 分页参数
     * @param bdcSlFpxxXgjlDO 分页查询参数
     * @return 分页数据
     */
    @GetMapping("/page")
    public Object queryFpxxXgjlByPage(@LayuiPageable Pageable pageable, BdcSlFpxxXgjlDO bdcSlFpxxXgjlDO) {
        Page<BdcSlFpxxXgjlDO> page = bdcSlFpXgjlFeignService.listBdcSlFpXgjlByPage(pageable, JSON.toJSONString(bdcSlFpxxXgjlDO));
        return super.addLayUiCode(page);
    }

    /**
     * 查询发票修改记录
     * @param bdcSlFpxxXgjlDO 查询参数
     * @return 发票修改记录
     */
    @GetMapping("/list")
    public Object queryFpxxXgjlList(BdcSlFpxxXgjlDO bdcSlFpxxXgjlDO) {
        return this.bdcSlFpXgjlFeignService.listBdcSlFpXgjl(bdcSlFpxxXgjlDO);
    }

    /**
     * 批量保存或新增不动产受理退费信息
     * @param bdcSlFpxxXgjlDO 不动产受理发票信息修改记录DO
     */
    @GetMapping("/addFpXgjl")
    public void addFpXgjl(BdcSlFpxxXgjlDO bdcSlFpxxXgjlDO){
        if(Objects.isNull(bdcSlFpxxXgjlDO) || StringUtils.isBlank(bdcSlFpxxXgjlDO.getXgqzd()) || StringUtils.isBlank(bdcSlFpxxXgjlDO.getXghzd())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产受理发票信息修改记录信息");
        }
        UserDto userDto = userManagerUtils.getCurrentUser();
        if(Objects.nonNull(userDto)){
            bdcSlFpxxXgjlDO.setXgr(userDto.getAlias());
        }
        bdcSlFpxxXgjlDO.setXgsj(new Date());
        if(StringUtils.isNotBlank(bdcSlFpxxXgjlDO.getXmid()) && StringUtils.isBlank(bdcSlFpxxXgjlDO.getSlbh())){
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(bdcSlFpxxXgjlDO.getXmid());
            List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                bdcSlFpxxXgjlDO.setSlbh(bdcXmDOList.get(0).getSlbh());
                bdcSlFpxxXgjlDO.setZl(bdcXmDOList.get(0).getZl());
            }
        }
        this.bdcSlFpXgjlFeignService.saveBdcSlFpxxXgjl(bdcSlFpxxXgjlDO);
    }

}

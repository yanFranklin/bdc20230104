package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlHsxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlHsxxFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a herf="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/1/25
 * @description 完税状态例外
 */
@RestController
@RequestMapping(value = "/rest/v1.0/wsztlw")
public class BdcWsztLwController extends BaseController{

    /**
     * 完税审核通过备注内容
     */
    private static final String HSXX_BZ = "窗口人工审核";

    @Autowired
    BdcSlHsxxFeignService bdcSlHsxxFeignService;

    @PostMapping("/wsztshtg")
    public Object wsztshtg(@RequestBody BdcSlHsxxQO bdcSlHsxxQO) {
        if(StringUtils.isBlank(bdcSlHsxxQO.getXmid())){
            throw new AppException("未获取到项目ID");
        }
        List<BdcSlHsxxDO> bdcSlHsxxDOList = this.bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
        if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
            int count = 0;
            for(BdcSlHsxxDO hsxx : bdcSlHsxxDOList){
                hsxx.setWszt(Integer.parseInt(CommonConstantUtils.WSZT_YWS));
                hsxx.setYhjkrkzt(1);
                hsxx.setBz(HSXX_BZ);
                count += this.bdcSlHsxxFeignService.updateBdcSlHsxx(hsxx);
            }
            return count;
        }else{
            BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
            bdcSlHsxxDO.setXmid(bdcSlHsxxQO.getXmid());
            bdcSlHsxxDO.setWszt(Integer.parseInt(CommonConstantUtils.WSZT_YWS));
            bdcSlHsxxDO.setYhjkrkzt(1);
            bdcSlHsxxDO.setBz("窗口人工审核");
            bdcSlHsxxDO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
            return this.bdcSlHsxxFeignService.insertBdcSlHsxxDO(bdcSlHsxxDO);
        }
    }

}

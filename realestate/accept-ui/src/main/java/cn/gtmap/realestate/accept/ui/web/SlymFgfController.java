package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcFgfDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcFgfFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 2019/1/17
 * @description 受理页面房改房
 */
@Controller
@RequestMapping("/slym/fgf")
public class SlymFgfController extends BaseController {
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcFgfFeignService bdcFgfFeignService;


    /**
     * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param: [gzlslid] 工作流程实例id
     * @return: 更新条数
     * @description 房改房推送
     */
    @ResponseBody
    @GetMapping("/tsszfgb/{gzlslid}")
    public Object tsszfgb(@PathVariable(value = "gzlslid")String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new NullPointerException("未获取到工作流实例id！");
        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            throw new AppException("未获取到项目集合");
        }

        BdcFgfDO bdcFgfDO = new BdcFgfDO();
        String xmids = StringToolUtils.resolveBeanToAppendStr(bdcXmDTOList, "getXmid", CommonConstantUtils.ZF_YW_DH);
        bdcFgfDO.setXmid(xmids);
        bdcFgfDO.setId(UUIDGenerator.generate16());
        bdcFgfDO.setSlbh(bdcXmDTOList.get(0).getSlbh());

        Map map = new HashMap();
        map.put("slbh",bdcFgfDO.getSlbh());
        map.put("xmid",bdcFgfDO.getXmid());
        LOGGER.info("房改房调用exchange接口参数：{}",map);
        Object response = exchangeInterfaceFeignService.requestInterface("fgf_tsywxx",map);
        LOGGER.info("房改房调用exchange接口返回值：{}",response);
        if (response != null) {
           Map<String,Integer> mapResult =  (Map<String,Integer>)response;
           if(mapResult.containsKey("yxcode")){
               int code = mapResult.get("yxcode");
               LOGGER.info("房改房调用exchange接口code值：{}",code);
               if(code == 200){//推送成功，调用插入接口
                    List<BdcFgfDO> listBdcFgfDO = new ArrayList<>();
                    listBdcFgfDO.add(bdcFgfDO);
                    LOGGER.info("房改房调用bdcFgfFeignService接口参数：{}",bdcFgfDO);
                    return bdcFgfFeignService.saveFgfxm(listBdcFgfDO);
               }else{
                    return 0;
               }
           }
        }
        return 0;
    }

}

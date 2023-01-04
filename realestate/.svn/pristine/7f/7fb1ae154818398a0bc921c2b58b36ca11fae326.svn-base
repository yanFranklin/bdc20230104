package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/11/19 10:21
 * @description 查询子系统 ：婚姻查询
 */
@RestController
@RequestMapping(value = "/rest/v1.0/hycx")
public class BdcHycxController extends BaseController {

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

     /**
      * 婚姻信息查询
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
      * @param qlrmc qlrmc
      * @return object
      */
    @ResponseBody
    @GetMapping("/hefei/hyxx")
    public Object getHyxx(@RequestParam(value = "qlrmc",required = false)String qlrmc,
                          @RequestParam(value = "qlrzjh",required = false)String qlrzjh,
                          @RequestParam(value = "beanName",required = false)String beanName,
    Pageable pageable){
        Map param=new HashMap();
        List hyxxList = new ArrayList(1);
        if(StringUtils.isBlank(qlrmc) ||StringUtils.isBlank(qlrzjh) ||StringUtils.isBlank(beanName)){
            throw new MissingArgumentException("查询参数缺失！");
        }
        param.put("qlrmc",qlrmc);
        param.put("qlrzjh",qlrzjh);
        Object hyxx = exchangeInterfaceFeignService.requestInterface(beanName,param);
        Object hj = JSON.toJSON(hyxx);
        hyxxList.add(hyxx);

        return super.addLayUiCode(new PageImpl(hyxxList,pageable,hyxxList.size()));

    }
}

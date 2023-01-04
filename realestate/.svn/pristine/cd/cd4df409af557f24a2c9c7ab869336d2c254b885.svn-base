package cn.gtmap.realestate.exchange.web.main;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.vo.etl.BatchSharedbVO;
import cn.gtmap.realestate.exchange.service.inf.GxtsLogService;
import cn.gtmap.realestate.exchange.web.rest.ShareDataRestController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0
 * @description 共享推送日志台账
 */
@Controller
@RequestMapping("/realestate-exchange/gxtsLog")
public class GxtsLogController {

    @Autowired
    private GxtsLogService gxtsLogService;
    @Autowired
    private ShareDataRestController shareDataRestController;

    /**
     * @param pageable 分页
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 页面展示
     */
    @ResponseBody
    @RequestMapping(value = "/getGxtsLogPagesJson")
    public Object getAccessJson(@LayuiPageable Pageable pageable,String slbhOrbdcdyh, String slbh,
                                String bdcdyh, String djlx, String czlx,String zl,String djxl,String czzt,
                                String kssj,String jssj) {
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(slbh)) {
            map.put("slbh", slbh);
        }
        if (StringUtils.isNotBlank(bdcdyh)) {
            map.put("bdcdyh", bdcdyh);
        }
        if (StringUtils.isNotBlank(slbhOrbdcdyh)) {
            map.put("slbhOrbdcdyh", slbhOrbdcdyh);
        }
        if (StringUtils.isNotBlank(djlx)) {
            map.put("djlx", djlx);
        }
        if (StringUtils.isNotBlank(czlx)) {
            map.put("czlx", czlx);
        }
        if (StringUtils.isNotBlank(zl)) {
            map.put("zl", zl);
        }
        if (StringUtils.isNotBlank(djxl)) {
            map.put("djxl", djxl);
        }
        if (StringUtils.isNotBlank(czzt)) {
            map.put("czzt", czzt);
        }
        if (StringUtils.isNotBlank(kssj)) {
            map.put("kssj", kssj);
        }
        if (StringUtils.isNotBlank(jssj)) {
            map.put("jssj", jssj);
        }
        return gxtsLogService.listGxtsLogByPages(pageable, map);
    }

    /**
     * @param xmid 项目主键
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据项目主键补偿共享当前项目
     */
    @GetMapping("/sharedb/xmid/for/compenstae")
    @ResponseBody
    CommonResponse shareXmByXmidForCompensate(@RequestParam("xmid")String xmid, @RequestParam("rzid")String rzid){
        return shareDataRestController.shareXmByXmidForCompensate(xmid,rzid);
    }

    /**
     * @param batchSharedbVO
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 批量根据项目主键补偿共享当前项目
     */
    @PostMapping("/batch/sharedb/xmid/for/compenstae")
    @ResponseBody
    CommonResponse batchShareXmByXmidForCompensate(@RequestBody BatchSharedbVO batchSharedbVO){
        return shareDataRestController.batchShareXmByXmidForCompensate(batchSharedbVO);
    }

}

package cn.gtmap.realestate.accept.ui.web.rest;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBgxxDbFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/10/21.
 * @description  变更记录对比
 */
@RestController
@RequestMapping("/rest/v1.0/bgxxdb")
@Api(tags = "变更信息对比")
public class BdcBgxxDbController extends BaseController {

    @Autowired
    BdcBgxxDbFeignService bdcBgxxDbFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    LogMessageClient logMessageClient;
    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    /**
     * 修改内容高亮显示登记小类集合
     */
    @Value("${default.xgnrglxs:}")
    private String xgnrglxs;
    /**
     * 第一次加载表单时，获取业务数据并存入es中
     * @param gzlslid 工作流实例ID
     */
    @GetMapping(value = "/es")
    public void saveYwxxToEs(@RequestParam(name="gzlslid")String gzlslid){
        if(StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(xgnrglxs)){
            List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            // 添加变更记录标记
            boolean addBgjl = false;
            for(BdcXmDTO bdcXmDTO:bdcXmDTOList){
                if(xgnrglxs.indexOf(bdcXmDTO.getDjxl())>-1){
                    addBgjl = true;
                    break;
                }
            }
            if(addBgjl){
                bdcBgxxDbFeignService.addYwxxToEs(gzlslid);
            }
        }
    }

    @GetMapping(value = "/dbxx")
    public Object queryBgDbxx(@RequestParam(name="gzlslid")String gzlslid,
                              @RequestParam(name="xmid", required = false) String xmid){
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("缺少参数工作流实例ID");
        }
        return bdcBgxxDbFeignService.compareYwxx(gzlslid);
    }

}

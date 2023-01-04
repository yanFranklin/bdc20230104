package cn.gtmap.realestate.inquiry.ui.web.rest.config;

import cn.gtmap.realestate.inquiry.ui.core.dto.BdcBzbZhcxPzDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/03/02
 * @description （标准版）综合查询配置获取服务
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcBzbZhcxPzController {
    /**
     * 查询证件号必填 配置
     */
    @Value("${standard.search.zjhsfbt:0}")
    private String cxzjhbt;
    /**
     * 证明证件号必填 配置
     */
    @Value("${standard.prove.zjhsfbt:0}")
    private String zmzjhbt;
    /**
     * 档案地址
     */
    @Value("${standard.archiveUrl:}")
    private String archiveUrl;
    /**
     * 盐城 查询目的
     */
    @Value("${yancheng.zhcx.cxmd:}")
    private String cxmd;

    /**
     * @description 盐城控制是否显示档案调用（旧）按钮
     */
    @Value("${yancheng.dadyj:}")
    private Boolean dadyj;

    /**
     * @description Excel全部导出的导出条数
     */
    @Value("${excel.qxdcts:2000}")
    private Integer dcts;


    @Value("#{${zhcx.xzzt.cxdhmap:{'cf':'cf','sd':'sd','yg':'yg','dyi':'dyi','jz':'jz'}}}")
    private Map<String, String> cxdhmap;

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取（标准版）综合查询配置项
     */
    @GetMapping("/bzb/zhcx/pz")
    public BdcBzbZhcxPzDTO getZhcxPz(){
        BdcBzbZhcxPzDTO bdcBzbZhcxPzDTO = new BdcBzbZhcxPzDTO();
        bdcBzbZhcxPzDTO.setCxzjhbt(cxzjhbt);
        bdcBzbZhcxPzDTO.setZmzjhbt(zmzjhbt);
        bdcBzbZhcxPzDTO.setArchiveUrl(archiveUrl);
        bdcBzbZhcxPzDTO.setCxmd(cxmd);
        bdcBzbZhcxPzDTO.setDadyj(dadyj);
        bdcBzbZhcxPzDTO.setDcts(dcts);
        bdcBzbZhcxPzDTO.setCxdhmap(cxdhmap);
        return bdcBzbZhcxPzDTO;
    }

}

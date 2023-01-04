package cn.gtmap.realestate.inquiry.ui.web.rest.yancheng;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcXtLscsDO;
import cn.gtmap.realestate.common.core.service.feign.building.ZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcXtLscsFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/12/31
 * @description 盐城征迁图形跳转登记页面重定向处理
 */
@Controller
@RequestMapping("/rest/v1.0/zq/url")
public class BdcZqUrlController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /** 征迁注销产权台账查询自定义查询标识 */
    private static final String ZQ_DYZX = "yczqyrcf";
    /** 征迁冻结产权台账查询自定义查询标识 */
    private static final String ZQ_DYDJ = "zqdjcqcx";
    /** 图形分析调用征迁跳转地址前缀 */
    private static final String TXZQDZ = "redirect:/view/dtcx/commonCx.html?cxdh=";

    /**
     * 图形房屋分析标识 fw
     * 图形房屋分析标识 zd
     */
    private static final String FW = "fw";

    /** 临时参数服务 */
    @Autowired
    private BdcXtLscsFeignService lscsFeignService;
    /** 权籍宗地服务 */
    @Autowired
    private ZdFeignService zdFeignService;


    /**
     * 跳转到现势产权查询页面
     * @param data 单元信息参数
     * @param general
     * @return 现势产权跳转页面
     */
    @GetMapping("/xscq")
    public String redirectToZxPageByGet(@RequestParam(value = "data", required = false) String data,
                                         @RequestParam(value = "general", required = false) String general) {
        return this.parseDataThenRedirect(data, "单元注销", ZQ_DYZX, null);
    }

    /**
     * 跳转到现势产权查询页面（指定房屋分析或宗地分析）
     * @param data 单元信息参数
     * @param general
     * @return 现势产权跳转页面
     */
    @GetMapping("/xscq/{fxlx}")
    public String redirectToZdOrFwZxPageByGet(@RequestParam(value = "data", required = false) String data,
                                          @RequestParam(value = "general", required = false) String general,
                                          @PathVariable("fxlx")String fxlx) {
        return this.parseDataThenRedirect(data, "单元注销", ZQ_DYZX, fxlx);
    }

    /**
     * 跳转到现势产权查询页面
     * @param data 单元信息参数
     * @param general
     * @return 现势产权跳转页面
     */
    @PostMapping("/xscq")
    public String redirectToZxPageByPostByPost(@RequestParam(value = "data", required = false) String data,
                                         @RequestParam(value = "general", required = false) String general) {
        return this.parseDataThenRedirect(data, "单元注销", ZQ_DYZX, null);
    }

    /**
     * 跳转到现势产权查询页面（指定房屋分析或宗地分析）
     * @param data 单元信息参数
     * @param general
     * @return 现势产权跳转页面
     */
    @PostMapping("/xscq/{fxlx}")
    public String redirectToZdOrFwZxPageByPostByPost(@RequestParam(value = "data", required = false) String data,
                                                 @RequestParam(value = "general", required = false) String general,
                                                 @PathVariable("fxlx")String fxlx) {
        return this.parseDataThenRedirect(data, "单元注销", ZQ_DYZX, fxlx);
    }


    /**
     * 跳转到单元冻结查询页面
     * @param data 单元信息参数
     * @param general
     * @return 现势产权跳转页面
     */
    @GetMapping("/dydj")
    public String redirectToDydjPageByGet(@RequestParam(value = "data", required = false) String data,
                                        @RequestParam(value = "general", required = false) String general) {
        return this.parseDataThenRedirect(data, "单元冻结", ZQ_DYDJ, null);
    }

    /**
     * 跳转到单元冻结查询页面（指定房屋分析或宗地分析）
     * @param data 单元信息参数
     * @param general
     * @return 现势产权跳转页面
     */
    @GetMapping("/dydj/{fxlx}")
    public String redirectToZdOrFwDydjPageByGet(@RequestParam(value = "data", required = false) String data,
                                            @RequestParam(value = "general", required = false) String general,
                                            @PathVariable("fxlx")String fxlx) {
        return this.parseDataThenRedirect(data, "单元冻结", ZQ_DYDJ, fxlx);
    }

    /**
     * 跳转到征迁冻结产权查询页面
     * @param data 单元信息参数
     * @param general
     * @return 现势征迁冻结产权查询页面
     */
    @PostMapping("/dydj")
    public String redirectToDydjPageByPostByPost(@RequestParam(value = "data", required = false) String data,
                                               @RequestParam(value = "general", required = false) String general) {
        return this.parseDataThenRedirect(data, "单元冻结", ZQ_DYDJ, null);
    }


    /**
     * 跳转到单元冻结查询页面（指定房屋分析或宗地分析）
     * @param data 单元信息参数
     * @param general
     * @return 现势产权跳转页面
     */
    @PostMapping("/dydj/{fxlx}")
    public String redirectToZdOrFwDydjPageByPost(@RequestParam(value = "data", required = false) String data,
                                            @RequestParam(value = "general", required = false) String general,
                                            @PathVariable("fxlx")String fxlx) {
        return this.parseDataThenRedirect(data, "单元冻结", ZQ_DYDJ, fxlx);
    }

    /**
     * 解析图形参数跳转到相应产权台账
     * @param data 图形地籍号等参数信息
     * @param type 业务类型
     * @param cxdh 对应自定义查询台账代号
     * @param fxlx 标识图形是宗地还是房屋分析
     */
    private String parseDataThenRedirect(String data, String type, String cxdh, String fxlx) {
        if(StringUtils.isBlank(data)) {
            logger.error("征迁图形跳转{}页面：未传参data或data内容为空", type);
            return TXZQDZ + cxdh;
        }

        logger.info("征迁图形跳转{}页面：data参数：{}", type, data);
        try{
            List<Map> list = JSON.parseArray(data, Map.class);
            if(CollectionUtils.isEmpty(list)) {
                return TXZQDZ + cxdh;
            }

            // 根据地籍号和自然幢号获取对应宗地、逻辑幢户室单元号
            List<String> bdcdyhList = this.getBdcdyhOfZdFw(list, fxlx);
            if(CollectionUtils.isEmpty(bdcdyhList)) {
                logger.warn("征迁图形跳转{}页面未获取到单元号数据", type);
                return TXZQDZ + cxdh;
            }

            // 将单元号保存到临时参数表
            String csmc = this.saveBdcdyh(bdcdyhList);
            return TXZQDZ + cxdh + "&param=" + csmc;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("征迁图形跳转{}页面：处理参数失败", type);
            return TXZQDZ + cxdh;
        }
    }

    /**
     * 根据隶属宗地和自然幢号查询对应宗地和房屋不动产单元号
     * @param list 图形分析数据
     * @param fxlx 标识图形是宗地还是房屋分析
     * @return {List} 不动产单元号集合
     */
    private List<String> getBdcdyhOfZdFw(List<Map> list, String fxlx) {
        List<String> bdcdyhList = new ArrayList<>();

        for(Map paramMap : list) {
            String djh = this.getDjh(paramMap);
            if(StringUtils.isBlank(djh)) {
                continue;
            }

            String zrzh = valueOf(paramMap.get("ZRZH"));
            if(FW.equals(fxlx) && StringUtils.isBlank(zrzh)) {
                // 图形房屋分析，必须要有自然幢号参数
                continue;
            }

            bdcdyhList.addAll(zdFeignService.listValidBdcdyhByDjhZrzh(djh, zrzh,""));
            bdcdyhList.add(djh + CommonConstantUtils.SUFFIX_ZD_BDCDYH);
        }
        return bdcdyhList;
    }

    /**
     * 获取地籍号
     * @param paramMap 参数信息
     * @return 地籍号
     */
    private String getDjh(Map paramMap) {
        // 宗地分析返回地籍号字段
        String djh = valueOf(paramMap.get("DJH"));
        if(StringUtils.isNotBlank(djh)) {
            return djh;
        }

        // 房屋分析返回隶属宗地字段
        return valueOf(paramMap.get("LSZD"));
    }

    /**
     * 将bdcdyh保存到临时参数表（征迁自定义查询台账会以临时表中bdcdyh为参数查询对应单元信息）
     * @param bdcdyhList 不动产单元号集合
     * @return {String} 保存的单元号在临时参数表主键参数名称
     */
    private String saveBdcdyh(List<String> bdcdyhList) {
        String csmc = CommonConstantUtils.ZDYCX_LSCS + UUIDGenerator.generate16();
        List<BdcXtLscsDO> lscsDOList = new ArrayList<>();
        for(String bdcdyh : bdcdyhList) {
            BdcXtLscsDO bdcXtLscsDO = new BdcXtLscsDO();
            bdcXtLscsDO.setCsmc(csmc);
            bdcXtLscsDO.setCsz(bdcdyh);
            bdcXtLscsDO.setSfsc(CommonConstantUtils.SF_S_DM);
            lscsDOList.add(bdcXtLscsDO);
        }
        lscsFeignService.addValues(lscsDOList);
        return csmc;
    }

    private static String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }
}

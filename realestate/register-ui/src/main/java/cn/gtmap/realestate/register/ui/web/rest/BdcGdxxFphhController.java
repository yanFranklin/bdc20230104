package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxFphhDO;
import cn.gtmap.realestate.common.core.qo.register.BdcGdxxDahQO;
import cn.gtmap.realestate.common.core.qo.register.BdcGdxxFphhQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcGdxxFphhFeignService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
 * @version 1.0, 2021/10/30
 * @description 档案分配盒号
 */
@RestController
@RequestMapping("/rest/v1.0/gdxxfphh")
public class BdcGdxxFphhController {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGdxxFphhController.class.getName());

    /**
     * 综合查询权籍筛选配置项
     * 格式为: name1:value1,value2;name2:value1;
     */
    @Value("${gdxx.fphh.xzdm:湖塘镇:100;牛塘镇:102;洛阳镇:103;滆湖:108;雪堰镇:110;太湖镇:112;前黄镇:113;礼嘉镇:114;嘉泽镇:119;湟里镇:120;经发区:400;高新区:411;}")
    private String allxzdm;


    @Autowired
    BdcGdxxFphhFeignService bdcGdxxFphhFeignService;

    /**
     * @return
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @description 页面下拉框需要展示盒号
     */
    @GetMapping(value = "/hh")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Integer> listhh(@RequestParam(required = false,name = "xzdm") String xzdm, @RequestParam(required = false,name = "nf") String nf) {
        String xzmc = "";
        if(StringUtils.isNotBlank(allxzdm)){
            List<String> xzs = Arrays.asList(allxzdm.split(";"));
            for (String xz : xzs) {
                String[] split = xz.split(":");
                if(xzdm.equals(split[1])){
                    xzmc = split[0];
                    break;
                }
            }
        }
        return bdcGdxxFphhFeignService.listhh(xzdm, xzmc, nf);
    }

    /**
     * @author <a href="mailto:wangyinghao@gtmap.cn">wyh</a>
     * @params []
     * @return java.lang.Object
     * @description 综合查询页面权籍所属筛选配置项加载
     */
    @GetMapping("/getxzdm")
    public Object getxzdm(){
        List<String> qjssSxpzsList = new ArrayList<>();
        if(StringUtils.isNotBlank(allxzdm)){
            qjssSxpzsList = Arrays.asList(allxzdm.split(";"));
        }
        return qjssSxpzsList;
    }


    /**
     * @param bdcGdxxFphhDO
     * @return
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @description 新增归档分配盒子信息
     */
    @PostMapping(value = "/addGdxxFphh")
    @ResponseStatus(HttpStatus.OK)
    public int insertBdcGdxxFphh(@RequestBody(required = true) BdcGdxxFphhDO bdcGdxxFphhDO) {
        return bdcGdxxFphhFeignService.insertBdcGdxxFphh(bdcGdxxFphhDO);
    }

    /**
     * @param bdcGdxxFphhDO
     * @return
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @description 根据xmid更新归档信息, 存在则更新不存在则插入
     */
    @PostMapping(value = "/upfphh")
    @ResponseStatus(HttpStatus.OK)
    public int updateBdcGdxxFphh(@RequestBody BdcGdxxFphhDO bdcGdxxFphhDO) {
        return bdcGdxxFphhFeignService.updateBdcGdxxFphh(bdcGdxxFphhDO);
    }

    /**
     * @param bdcGdxxFphhQO
     * @return List<BdcGdxxFphhDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 查询归档信息集合
     */
    @PostMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public List<BdcGdxxFphhDO> listBdcGdxxFphh(@RequestBody BdcGdxxFphhQO bdcGdxxFphhQO) {
        return bdcGdxxFphhFeignService.listBdcGdxxFphh(bdcGdxxFphhQO);
    }

    /**
     * @return
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @description 分配盒号，查询结果中全部未分配的，进行分配操作
     */
    @GetMapping(value = "/fphh")
    @ResponseStatus(HttpStatus.OK)
    public String fphh(BdcGdxxFphhQO bdcGdxxFphhQO) {
        return bdcGdxxFphhFeignService.fphh(bdcGdxxFphhQO);
    }

    /**
     * @return
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @description 查询是否分配盒号，数据返回不为空，则给提示语
     */
    @RequestMapping(value = "/sffphh", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<BdcGdxxFphhDO> sffphh(BdcGdxxFphhQO bdcGdxxFphhQO) {
        return bdcGdxxFphhFeignService.sffphh(bdcGdxxFphhQO);
    }


    /**
     * @param bdcGdxxFphhDOList
     * @return
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @description 页面批量更新盒号
     */
    @PostMapping(value = "/batchupfphh")
    @ResponseStatus(HttpStatus.OK)
    public void batchUpdateGdxxFphh(@RequestBody List<BdcGdxxFphhDO> bdcGdxxFphhDOList) {
        bdcGdxxFphhFeignService.batchUpdateGdxxFphh(bdcGdxxFphhDOList);
    }

    /**
     * @param bdcGdxxFphhDO
     * @param pageable
     * @return Object
     * @author <a href ="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @description 分页获取 全参数
     */
    @GetMapping(value = "/pagelistall")
    @ResponseStatus(HttpStatus.OK)
    public Object listBdcGdxxFphhPageall(@LayuiPageable Pageable pageable, BdcGdxxDahQO bdcGdxxFphhDO) {
         return bdcGdxxFphhFeignService.listBdcGdxxFphhPageAll(pageable, JSON.toJSONString(bdcGdxxFphhDO));
    }

    /**
     * @return Object
     * @author <a href ="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @description 根据id获取盒号
     */
    @GetMapping(value = "/xghh/{gdxxid}")
    @ResponseStatus(HttpStatus.OK)
    public BdcGdxxFphhDO getDaIdByXmId(@PathVariable("gdxxid") String gdxxid) {
        return bdcGdxxFphhFeignService.getDaIdById(gdxxid);
    }
}

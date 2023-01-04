package cn.gtmap.realestate.accept.ui.web;


import cn.gtmap.gtc.clients.RegionManagerClient;
import cn.gtmap.gtc.sso.domain.dto.RegionDto;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfssDdxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcYjdhSfxxGxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcYjSfxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcYjSfxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.util.PageUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/11/11
 * @description 月结收费信息Controller
 */
@RestController
@RequestMapping("/rest/v1.0/yjsfxx")
public class BdcYjSfxxController extends BaseController {

    @Autowired
    BdcXtJgFeignService bdcXtJgFeignService;
    @Autowired
    BdcYjdhSfxxGxFeignService bdcYjdhSfxxGxFeignService;
    @Autowired
    BdcYjSfDdxxFeignService bdcYjSfDdxxFeignService;
    @Autowired
    BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    BdcSlSfssDdxxFeignService bdcSlSfssDdxxFeignService;
    @Autowired
    private RegionManagerClient regionManagerClient;

    @Value("${yjsfdd.max:-1}")
    private Integer yjddMax;

    /**
     * 分页查询银行月结收费信息
     *
     * @param pageable    分页对象
     * @param bdcYjSfxxQO 查询条件
     * @return 银行月结收费信息分页数据
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/page")
    public Object listBdcYjSfxxByPage(@LayuiPageable Pageable pageable, BdcYjSfxxQO bdcYjSfxxQO) {
        List<BdcYjSfxxDTO> bdcYjSfxxDTOList = this.bdcYjdhSfxxGxFeignService.listYhyjSfxx(bdcYjSfxxQO);
        return super.addLayUiCode(PageUtils.listToPage(bdcYjSfxxDTOList, pageable));
    }

    /**
     * 获取按月结算 银行结构
     * @return 银行数据
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/queryXtjg")
    public Object getXtjgData() {
        return this.bdcXtJgFeignService.listAyjsBdcXtJg();
    }

    /**
     * 获取月结订单的最大数量
     * @return int 最大单数
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/yjdd/max")
    public int getYjddMax() {
        return yjddMax;
    }

    /**
     * 生成不动产月结单号收费信息关系数据
     * @param bdcYjdhSfxxGxDOList  收费信息关系数据
     * @return 收费信息关系数据结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/gx")
    public List<BdcYjdhSfxxGxDO> generateYjdhSfxxGx(@RequestBody List<BdcYjdhSfxxGxDO> bdcYjdhSfxxGxDOList) {
        if(CollectionUtils.isEmpty(bdcYjdhSfxxGxDOList)){
            throw new MissingArgumentException("确实月结收费信息数据");
        }
        return this.bdcYjdhSfxxGxFeignService.generateYjdhSfxxGx(bdcYjdhSfxxGxDOList);
    }


    /**
     * 检查收费信息收费下单
     * @param bdcYjdhSfxxGxDOList 收费信息关系数据
     * @return Map key: 月结单号, value: 受理编号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/sfxd")
    public Object checkSfxxSfxd(@RequestBody List<BdcYjdhSfxxGxDO> bdcYjdhSfxxGxDOList) {
        if(CollectionUtils.isEmpty(bdcYjdhSfxxGxDOList)){
            throw new MissingArgumentException("缺失月结收费信息数据");
        }
        return this.bdcYjdhSfxxGxFeignService.checkSfxxSfxd(bdcYjdhSfxxGxDOList);
    }

    /**
     * 作废月结订单信息内容
     * @param yjdhList 月结订单号集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/zfyjdh")
    public void zfYjDdxx(@RequestBody List<String> yjdhList) {
        if(CollectionUtils.isEmpty(yjdhList)){
            throw new MissingArgumentException("缺失月结单号信息");
        }
        this.bdcYjSfDdxxFeignService.zfYjddxx(yjdhList);
    }

    /**
     * 生成月结缴费订单
     * @param yjdh  月结单号
     * @param hyzfjftj 合一支付缴费途径（1表示商业银行 2 表示合一支付）
     * @param jkqd 缴款渠道（0 表示线上支付 1 表示线下支付）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/scdd")
    public List<BdcSlSfssDdxxDO> cjdd(@RequestParam(value="yjdh") String yjdh, @RequestParam(value="hyzfjftj", required = false) String hyzfjftj,
                                      @RequestParam(value="jkqd", required = false) String jkqd){
        if(StringUtils.isBlank(yjdh)){
            throw new MissingArgumentException("缺失参数月结单号。");
        }
        return this.bdcSlSfssDdxxFeignService.createYjSfOrder(yjdh, hyzfjftj, jkqd);
    }

    /**
     * 根据查询条件获取所有需要导出的数据内容
     * @param bdcYjSfxxQO
     * @return
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/getExportData")
    public Object listBdcYjSfxxExportData(@RequestBody BdcYjSfxxQO bdcYjSfxxQO) {
        return this.bdcYjdhSfxxGxFeignService.listYhyjSfxx(bdcYjSfxxQO);
    }


    /**
     * 调用大云接口获取区县代码
     * @return 县级区县代码
     */
    @GetMapping("/getQxdm")
    public Object getQxdmData() {
        List<Map> result = new ArrayList<>(20);
        List<RegionDto> regionDtolist = new ArrayList<>(20);
        // 获取县级代码
        regionDtolist.addAll(regionManagerClient.findRegionsByLevel(3));
        if(CollectionUtils.isNotEmpty(regionDtolist)){
            for(RegionDto regionDto : regionDtolist){
                Map<String, Object> region = new HashMap<>(3);
                region.put("XZDM", regionDto.getCode());
                region.put("XZMC", regionDto.getName());
                region.put("XZJB", regionDto.getLevel());
                result.add(region);
            }
        }
        return result;
    }

    /**
     * 根据收费信息id获取月结单号内容
     * @param sfxxid  收费信息ID
     * @return 月结订单信息
     */
    @GetMapping(value = "/yjdh/{sfxxid}")
    public List<String> queryYjdhBySfxxId(@PathVariable(value="sfxxid") String sfxxid){
        if(StringUtils.isBlank(sfxxid)){
            throw new MissingArgumentException("未获取到收费信息ID");
        }
        return this.bdcYjdhSfxxGxFeignService.queryYjdhxxBySfxxId(sfxxid);
    }

    /**
     * 获取合一支付缴费途径默认配置
     * @return 配置内容
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/hyzfjftj/config")
    public String getHyzfjftjConfig(){
       return this.bdcYjSfDdxxFeignService.getHyzfjftjConfig();
    }
}

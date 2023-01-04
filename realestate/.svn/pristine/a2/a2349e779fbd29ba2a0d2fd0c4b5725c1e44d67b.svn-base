package cn.gtmap.realestate.accept.ui.web;


import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYjxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYjxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlYjxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlYjxxFeginService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/12/13.
 * @description 不动产邮寄信息控制层
 */
@RestController
@RequestMapping("/slym/yjxx")
public class SlymYjxxController {

    /**
     * 需要拼接展示收件地址的流程名称集合
     */
    @Value("#{'${sjdzpj.gzldyid:}'.split(',')}")
    protected List<String> sjdzpjdyid;

    @Autowired
    private BdcSlYjxxFeginService bdcSlYjxxFeginService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    protected UserManagerUtils userManagerUtils;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    /**
     * 获取数据字典中，中国省市区县数据
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     * @return java.lang.String 树形结构的JSON数组字符串
     */
    @GetMapping("/init/zd")
    public String initzd(){
        return JSON.toJSONString(this.bdcZdFeignService.queryZdZgSsqx());
    }

    /**
     * 初始化不动产邮寄信息
     * <p> 初始化不动产邮寄信息，当收件人不为空时，通过工作流实例ID获取权利人信息，多个权利人时，默认采用第一权利人。
     *
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @return Object 不动产邮寄信息
     */
    @GetMapping("/{gzlslid}")
    public Object initBdcSlYjxx(@PathVariable(value = "gzlslid") String gzlslid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid), "未获取到参数流程实例ID。");
        return this.bdcSlYjxxFeginService.initBdcSlYjxx(gzlslid);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取常州邮寄信息
     * @date : 2021/7/14 16:13
     */

    @GetMapping("/cz/{gzlslid}")
    public Object queryBdcSlYjxx(@PathVariable(value = "gzlslid") String gzlslid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid), "未获取到参数流程实例ID。");
        List<BdcSlYjxxDTO> bdcSlYjxxDTOS = bdcSlYjxxFeginService.queryBdcSlYjxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcSlYjxxDTOS)) {
            return new BdcSlYjxxDTO();
        }
        BdcSlYjxxDTO bdcSlYjxxDTO = bdcSlYjxxDTOS.get(0);

        //常州特殊流程 收件地址拼接显示
        if (CollectionUtils.isNotEmpty(sjdzpjdyid)) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
            if (bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getGzldyid()) && sjdzpjdyid.contains(bdcSlJbxxDO.getGzldyid())) {
                StringBuilder sb = new StringBuilder();
                if (StringUtils.isNotBlank(bdcSlYjxxDTO.getSjrszp())) {
                    sb.append(bdcSlYjxxDTO.getSjrszp());
                }
                if (StringUtils.isNotBlank(bdcSlYjxxDTO.getSjrszc())) {
                    sb.append(bdcSlYjxxDTO.getSjrszc());
                }
                if (StringUtils.isNotBlank(bdcSlYjxxDTO.getSjrszx())) {
                    sb.append(bdcSlYjxxDTO.getSjrszx());
                }
                if (StringUtils.isNotBlank(bdcSlYjxxDTO.getSjrxxdz())) {
                    sb.append(bdcSlYjxxDTO.getSjrxxdz());
                }
                bdcSlYjxxDTO.setSjrxxdz(sb.toString());
            }
        }

        return bdcSlYjxxDTO;
    }

    /**
     * @param gzlslid 工作流实例id
     * @param djxl 登记小类
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 查询宣城邮寄信息
     * @date : 2022/7/13
     */
    @GetMapping("/xc")
    public Object queryXcBdcSlYjxx(@RequestParam(value = "gzlslid") String gzlslid,@RequestParam(value = "djxl") String djxl) {
        if (StringUtils.isBlank(gzlslid)&& StringUtils.isBlank(djxl)){
            throw new IllegalArgumentException("查询宣城邮寄信息，未获取到工作流实例id或登记小类");
        }
        BdcSlYjxxQO bdcSlYjxxQO = new BdcSlYjxxQO();
        bdcSlYjxxQO.setGzlslid(gzlslid);
        bdcSlYjxxQO.setDjxl(djxl);
        List<BdcSlYjxxDO> bdcSlYjxxDOS = bdcSlYjxxFeginService.listBdcSlYjxx(bdcSlYjxxQO);
        if (CollectionUtils.isEmpty(bdcSlYjxxDOS)) {
            return new BdcSlYjxxDO();
        }
        return bdcSlYjxxDOS.get(0);
    }

    /**
     * 标记当前流程需要EMS邮寄
     *
     * @param gzlslid 工作流实例ID
     * @param slbh    受理编号
     * @return String 邮寄信息ID
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/mark")
    public String makeTipsToEms(@RequestParam(value = "gzlslid") String gzlslid,
                                @RequestParam(value = "slbh") String slbh) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid),"未获取到参数流程实例ID。");
        Preconditions.checkArgument(StringUtils.isNotBlank(slbh),"未获取到参数受理编号。");
        /*
         * 将当前流程标记为由EMS邮寄时，先通过工作流实例ID查询邮寄信息，
         * 不存在邮寄信息时，进行标记。
         * 当存在当前流程的邮寄信息时，删除当前标记。
         */
        final List<BdcSlYjxxDTO> bdcSlYjxxDTOList = this.bdcSlYjxxFeginService.queryBdcSlYjxxByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcSlYjxxDTOList)){
            BdcSlYjxxDTO bdcSlYjxxDTO = new BdcSlYjxxDTO();
            bdcSlYjxxDTO.setGzlslid(gzlslid);
            bdcSlYjxxDTO.setSlbh(slbh);
            this.bdcSlYjxxFeginService.saveBdcSlYjxx(bdcSlYjxxDTO);
        }
        return "已标记由EMS进行推送，请在发证环节点击EMS推送进行邮寄。";
    }

    /**
     * 通过JSON数据更新不动产受理邮寄信息
     * <p> json中属性值为 null值的会更新为null值
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     * @param json 前台传输邮寄系信息JSON
     * @return Integer 更新结果标识
     */
    @PatchMapping("")
    public Integer updateBdcSlYjxx(@RequestBody String json){
        return this.bdcSlYjxxFeginService.updateByJsonEntity(json);
    }

    /**
     * 推送邮寄信息到EMS
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例id
     * @return String 推送信息
     */
    @GetMapping("/ems/{gzlslid}")
    public String pushYjxxToEms(@PathVariable(value = "gzlslid") String gzlslid){
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid),"未获取到参数流程实例ID。");
        return this.bdcSlYjxxFeginService.pushYjxxToEms(gzlslid,userManagerUtils.getCurrentUserName());
    }

    /**
     * 保存邮寄信息
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     * @param bdcSlYjxxDTO 不动产受理邮寄信息DTO
     * @return String 邮寄信息ID
     */
    @PostMapping("/yj")
    public String saveYjxx(@RequestBody BdcSlYjxxDTO bdcSlYjxxDTO){
        return this.bdcSlYjxxFeginService.saveBdcSlYjxx(bdcSlYjxxDTO);
    }

    /**
     * 调用EMS接口获取物流跟踪信息
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     * @param slbh 受理编号
     * @param wlydh 物流运单号
     * @return String 物流跟踪信息
     */
    @GetMapping("/trace")
    public Object getEmsTrace(@RequestParam(value = "slbh") String slbh,
                              @RequestParam(value = "wlydh") String wlydh){
        Preconditions.checkArgument(StringUtils.isNotBlank(slbh),"未获取到参数受理编号。");
        if(StringUtils.isAnyBlank(slbh,wlydh)){
            throw new AppException("未获取到参数受理编号或物流运单号");
        }
        Map<String,String> map = new HashMap<>(4);
        map.put("slbh",slbh);
        map.put("wlydh",wlydh);
        map.put("qxdm", getRegionCode(userManagerUtils.getCurrentUserName()));
        return exchangeInterfaceFeignService.requestInterface("emsddztcx", JSON.toJSONString(map));
    }

    /**
     * 获取区县代码
     */
    private String getRegionCode(String currentUserName) {
        String regionCode ="";
        if(StringUtils.isNotBlank(currentUserName)) {
            regionCode = userManagerUtils.getRegionCodeByUserName(currentUserName);
        }
        return regionCode;
    }
}

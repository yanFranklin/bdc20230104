package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlZjjgDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcZjjgDTO;
import cn.gtmap.realestate.common.core.dto.accept.YthZjjgDto;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.zjjg.ZjjgResponseDto;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlZjjgQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcZjjgFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/22
 * @description 资金监管
 */
@RestController
@RequestMapping("/slym/zjjg")
@Validated
public class BdcZjjgController {

    @Autowired
    BdcZjjgFeignService bdcZjjgFeignService;

    @Autowired
    BdcSlEntityFeignService bdcSlEntityFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 查询资金监管信息
      */
    @GetMapping("")
    public BdcZjjgDTO queryZjjg(@NotBlank(message = "查看资金监管信息工作流实例id不可为空") String gzlslid) {
        BdcZjjgDTO bdcZjjgDTO =new BdcZjjgDTO();
        List<BdcSlZjjgDO> bdcSlZjjgDOS =bdcZjjgFeignService.listBdcSlZjjg(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcSlZjjgDOS)){
           bdcZjjgDTO.setBdcSlZjjgDOS(bdcSlZjjgDOS);
           List<String> zjjgidList = bdcSlZjjgDOS.stream().map(BdcSlZjjgDO::getZjjgid).collect(Collectors.toList());
           bdcZjjgDTO.setZjjgidList(zjjgidList);

        }
        return bdcZjjgDTO;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 资金监管撤销信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 查询资金监管撤销信息
     */
    @GetMapping("/cx")
    public BdcZjjgDTO queryZjjgCxxx(@NotBlank(message = "查看资金监管撤销信息工作流实例id不可为空") String gzlslid) {
        BdcZjjgDTO bdcZjjgDTO =new BdcZjjgDTO();
        List<BdcSlZjjgDO> bdcSlZjjgDOS =new ArrayList<>();
        List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcSlXmDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到受理项目信息");
        }
        //后期调整为批量查询
        for(BdcSlXmDO bdcSlXmDO:bdcSlXmDOList){
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = this.bdcSlXmLsgxFeignService.listBdcSlXmLsgxByXmid(bdcSlXmDO.getXmid());
            if(CollectionUtils.isEmpty(bdcSlXmLsgxDOList)){
                throw new AppException(ErrorCode.MISSING_ARG, "未获取到资金监管关联信息");
            }
            List<BdcSlZjjgDO> zjjgDOS =bdcZjjgFeignService.listBdcSlZjjgByXmid(bdcSlXmLsgxDOList.get(0).getYxmid());
            if(CollectionUtils.isNotEmpty(zjjgDOS)){
                bdcSlZjjgDOS.addAll(zjjgDOS);
            }

        }
        if(CollectionUtils.isNotEmpty(bdcSlZjjgDOS)){
            bdcZjjgDTO.setBdcSlZjjgDOS(bdcSlZjjgDOS);
            List<String> zjjgidList = bdcSlZjjgDOS.stream().map(BdcSlZjjgDO::getZjjgid).collect(Collectors.toList());
            bdcZjjgDTO.setZjjgidList(zjjgidList);

        }
        return bdcZjjgDTO;
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description
      */
    @PostMapping("")
    public Object updateZjjg(@RequestBody String json) {
        if (StringUtils.isBlank(json)) {
            throw new AppException("更新资金监管缺失数据");
        }

        JSONObject jsonObject = JSON.parseObject(json);
        if(!jsonObject.containsKey("zjjgidList") ||jsonObject.get("zjjgidList") == null){
            throw new AppException("更新资金监管缺失主键");
        }
        List<String> zjjgidList =(List<String>)jsonObject.get("zjjgidList");
        int count=0;
        if(CollectionUtils.isNotEmpty(zjjgidList)) {
            for (String zjjgid : zjjgidList) {
                jsonObject.put("zjjgid", zjjgid);
                count += bdcSlEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(jsonObject), BdcSlZjjgDO.class.getName());
            }
        }
        return count;

    }

    /**
     * @param cqxmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据产权xmid 获取资金监管信息
     * @date : 2021/7/22 18:33
     */
    @GetMapping("/cqxmid")
    public Object queryZjjgByCqxmid(@NotBlank(message = "查看资金监管信息产权项目id不可为空") String cqxmid) {
        BdcSlZjjgQO bdcSlZjjgQO = new BdcSlZjjgQO();
        bdcSlZjjgQO.setCqxmid(cqxmid);
        bdcSlZjjgQO.setZt(CommonConstantUtils.ZJJG_ZT_WCX);
        List<BdcSlZjjgDO> bdcSlZjjgDOList = bdcZjjgFeignService.listBdcSlZjjg(bdcSlZjjgQO);
        if (CollectionUtils.isNotEmpty(bdcSlZjjgDOList)) {
            return bdcSlZjjgDOList.get(0);
        }
        return null;
    }

    /**
     * 查询一体化平台资金监管信息
     * @param processInsId
     * @param htbh
     * @return
     */
    @GetMapping("/yth")
    public YthZjjgDto listYthSlZjjg(@RequestParam(value = "processInsId")String processInsId,
                                    @RequestParam(value = "htbh")String htbh) {
        return bdcZjjgFeignService.listYthSlZjjg(processInsId,htbh);
    }

    /**
     * 查询一体化平台资金监管信息
     * @param processInsId
     * @param htbh
     * @return
     */
    @GetMapping("/ythfj")
    public Object getFileStorageUrl(@RequestParam(value = "htbh")String htbh) {
        return bdcZjjgFeignService.getFileStorageUrl(htbh);
    }
}

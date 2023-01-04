package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcJjdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmYjdGxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcDjxlPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcJjdFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 不动产档案交接服务
 *
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version v1.0, 2021/7/29
 */
@RestController
@RequestMapping("/rest/v1.0/dajj")
@Api(tags = "不动产档案交接服务接口")
public class BdcDajjController extends BaseController {

    @Autowired
    BdcJjdFeignService bdcJjdFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcDjxlPzFeignService bdcDjxlPzFeignService;

    /**
     *  获取原流程的工作流实例ID
     * @param gzlslidList 工作流实例ID集合
     * @return 原流程工作流实例ID
     */
    @PostMapping("/getYlcGzlslid")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量获取原流程的工作流实例ID")
    public List<String> plQueryYlcGzlslid(@RequestBody List<String> gzlslidList) {
        if(CollectionUtils.isEmpty(gzlslidList)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID");
        }
        List<BdcXmYjdGxDO> bdcXmYjdGxDOList = bdcJjdFeignService.queryJjdGxByGzlslidList(gzlslidList);
        if(CollectionUtils.isEmpty(bdcXmYjdGxDOList)){
            throw new AppException(ErrorCode.CUSTOM, "未获取到档案交接关系信息");
        }
        // 移交单中xmid存的即是gzlslid
        List<String> ylcGzlslids = bdcXmYjdGxDOList.stream().map(BdcXmYjdGxDO::getXmid).collect(Collectors.toList());
        return ylcGzlslids;
    }


    /**
     * 获取打印配置信息
     * @param gzlslid 工作流实例ID
     * @return 打印类型
     */
    @GetMapping("/dypz/dylx")
    public Map queryDylxFromDypz(@NotBlank(message = "获取打印类型工作流实例id不可为空") String gzlslid) {
        String dylx = "bdcSqSpb";
        String damlDylx = "daml";
        String dafmDylx = "dafm";
        String jtdafmdDylx = "jt-dafm";
        String lydafmdDylx = "ly-dafm";

        List<BdcXmDTO> bdcXmDOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDOList.get(0).getGzldyid(), bdcXmDOList.get(0).getDjxl());
            if (Objects.nonNull(bdcDjxlPzDO) && StringUtils.isNotBlank(bdcDjxlPzDO.getSpbdylx())) {
                dylx = bdcDjxlPzDO.getSpbdylx();
            }
            if (Objects.nonNull(bdcDjxlPzDO) && StringUtils.isNotBlank(bdcDjxlPzDO.getDamldylx())) {
                damlDylx = bdcDjxlPzDO.getDamldylx();
            }
            if (Objects.nonNull(bdcDjxlPzDO) && StringUtils.isNotBlank(bdcDjxlPzDO.getDafmdylx())) {
                dafmDylx = bdcDjxlPzDO.getDafmdylx();
            }
            if (Objects.nonNull(bdcDjxlPzDO) && StringUtils.isNotBlank(bdcDjxlPzDO.getJtdafmdylx())) {
                jtdafmdDylx = bdcDjxlPzDO.getJtdafmdylx();
            }
            if (Objects.nonNull(bdcDjxlPzDO) && StringUtils.isNotBlank(bdcDjxlPzDO.getLydafmdylx())) {
                lydafmdDylx = bdcDjxlPzDO.getLydafmdylx();
            }
        }
        // 判断当前流程类型
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        if(CommonConstantUtils.LCLX_PLZH.equals(xmlx) || CommonConstantUtils.LCLX_ZH.equals(xmlx)) {
            xmlx = CommonConstantUtils.LCLX_ZH;
        } else{
            xmlx = CommonConstantUtils.LCLX_PT;
        }
        Map result = new HashMap();
        result.put("dylx", dylx);
        result.put("lclx", xmlx);
        result.put("damldylx", damlDylx);
        result.put("dafmdylx",dafmDylx);
        result.put("jtdafmdDylx",jtdafmdDylx);
        result.put("lydafmdDylx",lydafmdDylx);
        return result;
    }

    /**
      * @param gzlslid 工作流实例ID
      * @return 汇总内容
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取档案明细汇总内容
      */
    @GetMapping("/damx/tailSummaryContent")
    public Object queryTailSummaryContent(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("工作流实例id不可为空");
        }
        BdcJjdQO bdcJjdQO = new BdcJjdQO();
        bdcJjdQO.setDajjGzlslid(gzlslid);
        List<BdcJjdDO> bdcJjdDOList = bdcJjdFeignService.listBdcJjd(bdcJjdQO);
        if(CollectionUtils.isNotEmpty(bdcJjdDOList)){
            return bdcJjdDOList.get(0);
        }
        return null;
    }
}

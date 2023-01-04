package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlLzrQzxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlZsDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzlwFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcFzjlFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 业务服务Controller
 *
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version v1.0, 2021/11/30
 */
@RestController
@RequestMapping("/rest/v1.0/yw")
@Api(tags = "不动产业务服务接口")
public class BdcYwController extends BaseController {

    /**
     * 日志服务
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYwController.class);

    @Autowired
    BdcFzjlFeignService bdcFzjlFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcGzlwFeignService bdcGzlwFeignService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @PostMapping("/fzjl/pl/qzxx")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量更新发证记录领证人签字内容")
    public void plSaveFzjlQzxx(@RequestBody BdcFzjlLzrQzxxDTO bdcFzjlLzrQzxxDTO) {
        if(CollectionUtils.isEmpty(bdcFzjlLzrQzxxDTO.getGzlslidList())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        this.bdcFzjlFeignService.plSaveFzjlLzrQzxx(bdcFzjlLzrQzxxDTO);
    }

    @PostMapping("/fzjl/pl/lzrxx")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量更新发证记录领证人信息")
    public void plSaveFzjlLzrxx(@RequestBody BdcFzjlZsDTO bdcFzjlZsDTO) {
        if(StringUtils.isBlank(bdcFzjlZsDTO.getLzr())|| StringUtils.isBlank(bdcFzjlZsDTO.getLzrzjh())
                || CollectionUtils.isEmpty(bdcFzjlZsDTO.getGzlslidList())) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到领证人或工作流实例ID信息");
        }
        this.bdcFzjlFeignService.plUpldateLzrxx(bdcFzjlZsDTO);
    }

    @GetMapping("/fzjl/getlzrxx")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取领证人信息")
    public BdcQlrDO getLzrxx(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID信息");
        }
        List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listAllBdcQlr(gzlslid, CommonConstantUtils.QLRLB_QLR, null);
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
            return bdcQlrDOList.get(0);
        }
        return null;
    }

    @PostMapping("/save/print/data")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存打印数据至redis中")
    public String savePrintDataInRedis(@RequestBody List<Map<String, Object>> paramList) {
        if(CollectionUtils.isEmpty(paramList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到打印数据。");
        }
        String redisKey = CommonConstantUtils.REDIS_PLFZQSD_PRINT + UUIDGenerator.generate16();
        return redisUtils.addStringValue(redisKey, JSON.toJSONString(paramList), 600);
    }

    @GetMapping("/queryBdcGzlwShByGzlslid")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询规则例外审核")
    public List<BdcGzlwShDO> queryBdcGzlwShByGzlslid(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID信息");
        }
        return bdcGzlwFeignService.listBdcGzlwShByGzlslid(gzlslid);
    }

    @GetMapping("/queryThhlwyyByGzlslid")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询退回互联网原因")
    public List<BdcCzrzDO> queryThhlwyyByGzlslid(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID信息");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOS)){
            BdcXmDO bdcXmDO = bdcXmDOS.get(0);
            if(StringUtils.isNotBlank(bdcXmDO.getSpxtywh())){
                BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
                bdcCzrzDO.setSpxtywh(bdcXmDO.getSpxtywh());
                bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_SC.key());
                List<BdcCzrzDO> bdcCzrzDOS = bdcCzrzFeignService.listBdcCzrz(bdcCzrzDO);
                return bdcCzrzDOS;
            }
        }
        return null;
    }
//


}

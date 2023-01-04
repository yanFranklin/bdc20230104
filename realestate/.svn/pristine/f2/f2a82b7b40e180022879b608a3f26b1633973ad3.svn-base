package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.rest.etl.HlwYzRestService;
import cn.gtmap.realestate.common.core.vo.etl.HxdjxxbVO;
import cn.gtmap.realestate.etl.core.domian.wwsq.GxWwSqxmDo;
import cn.gtmap.realestate.etl.core.service.HlwYwxxDataService;
import cn.gtmap.realestate.etl.service.HlwYwxxService;
import cn.gtmap.realestate.etl.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/5/10
 * @description 互联网验证
 */
@RestController
@Api(tags = "互联网验证服务")
public class HlwYzRestController implements HlwYzRestService {

    @Autowired
    HlwYwxxService hlwYwxxService;

    @Autowired
    HlwYwxxDataService hlwYwxxDataService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "互联网办件验证", notes = "互联网办件验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")
    })
    public Map<String, String> checkHlwBjzt(@PathVariable(name = "bdcdyh") String bdcdyh){
        return hlwYwxxService.checkHlwBjzt(bdcdyh);


    }

    /**
     * @param hxdjxxbVO
     * @return
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 回写登记信息到共享
     */
    @Override
    public void hxDjxx(@RequestBody HxdjxxbVO hxdjxxbVO) {
        if (null == hxdjxxbVO || StringUtils.isBlank(hxdjxxbVO.getGzlslid())) {
            throw new MissingArgumentException("缺少工作流实例ID参数");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(hxdjxxbVO.getGzlslid());
        List<BdcXmDO> xmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(xmList)) {
            for (BdcXmDO bdcXmDO : xmList) {
                String spxtywh = bdcXmDO.getSpxtywh();
                if (StringUtils.isNotBlank(spxtywh)) {
                    GxWwSqxmDo gxWwSqxmDo = hlwYwxxDataService.getGxWwSqxmBySqslbh(spxtywh);
                    if (null != gxWwSqxmDo) {
                        JSONObject hxObject = new JSONObject();
                        hxObject.put("xmid", gxWwSqxmDo.getXmid());
                        hxObject.put("byslyy", hxdjxxbVO.getThyy());
                        hxObject.put("shzt", Constants.SHZT_ABANDON);
                        hxObject.put("czrmc", hxdjxxbVO.getThrmc());
                        hlwYwxxService.hxDjxx(hxObject);
                    }
                    break;
                }
            }

        }

    }
}

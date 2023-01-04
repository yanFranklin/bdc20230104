package cn.gtmap.realestate.register.ui.web.rest.xxbl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXzxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXzxxQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXzxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 信息补录关于修正流程相关服务
 *
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version v1.1, 2019/12/17 11:00
 */
@RestController
@RequestMapping("/rest/v1.0/blxx/xz")
public class BdcXxblXzController extends BaseController {
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcSlXzxxFeignService bdcSlXzxxFeignService;
    @Autowired
    private BdcZsFeignService bdcZsFeignService;


    @GetMapping("")
    public Object getXzxx(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("缺少参数 processInsId");
        }
        BdcSlXzxxQO bdcSlXzxxQO = new BdcSlXzxxQO();
        bdcSlXzxxQO.setGzlslid(processInsId);
        BdcSlXzxxDO bdcSlXzxxDO = bdcSlXzxxFeignService.queryBdcSlXzxx(bdcSlXzxxQO);
        if(null != bdcSlXzxxDO && StringUtils.isNotBlank(bdcSlXzxxDO.getXzxxid())){
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(bdcSlXzxxDO));

            String xmid = bdcSlXzxxDO.getYxmid();
            if(StringUtils.isNotBlank(xmid)){
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(xmid);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                    String gzlslid = bdcXmDOList.get(0).getGzlslid();
                    if(StringUtils.isNotBlank(gzlslid)){
                        jsonObject.put("xzgzlslid",gzlslid);
                    }
                }
            }
            return jsonObject;

        }
        return null;
    }


    /**
     * 生成证书
     *
     * @param gzlslid 工作流实例 ID
     * @return {Object} 判断是否是修正流程的补录 临时状态的不能重新生成证书
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping("/zsZt")
    public Object zsZt(@RequestParam(value = "xmid") String xmid)  {
        if (StringUtils.isBlank(xmid)) {
            return true;
        }
        BdcSlXzxxQO bdcSlXzxxQO = new BdcSlXzxxQO();
        bdcSlXzxxQO.setXmid(xmid);
        BdcSlXzxxDO bdcSlXzxxDO = bdcSlXzxxFeignService.queryBdcSlXzxx(bdcSlXzxxQO);
        // 能查到修正信息
        if(null != bdcSlXzxxDO && StringUtils.isNotBlank(bdcSlXzxxDO.getXzxxid())){
            // 用xmid 查证书数据，如果没有证书数据，或者还没有获印制号，则不能编辑证书页
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setXmid(xmid);
            List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
            if(CollectionUtils.isNotEmpty(bdcZsDOList)){
                for(BdcZsDO bdcZsDO : bdcZsDOList){
                    if(StringUtils.isBlank(bdcZsDO.getQzysxlh())){
                        return false;
                    }
                }
            }
        }
        return true;
    }


}

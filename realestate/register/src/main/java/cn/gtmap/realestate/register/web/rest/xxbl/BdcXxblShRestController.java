package cn.gtmap.realestate.register.web.rest.xxbl;

import cn.gtmap.realestate.common.core.domain.BdcBlShDO;
import cn.gtmap.realestate.common.core.dto.register.BlShPageResponseDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcBlShQO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcXxblShRestService;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblShService;
import cn.gtmap.realestate.register.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/6/11 08:28
 */
@RestController
@Api(tags = "不动产信息补录审核服务接口")
public class BdcXxblShRestController extends BaseController implements BdcXxblShRestService {
    @Autowired
    private BdcXxblShService bdcXxblShService;

    @Value("${xxbl.shcx.pxgz:}")
    private String pxgz;

    /**
     * 补录审核页面后台服务
     *
     * @param pageable     分页对象
     * @param bdcBlShQoStr bdcBlShQo 的json串
     * @return Page<BlShPageResponseDTO> 分页查询对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public Page<BlShPageResponseDTO> listBlShByPageJson(Pageable pageable,
                                                        @RequestParam(name = "bdcBlShQoStr", required = false) String bdcBlShQoStr,
                                                        @RequestParam(name="version") String version) {
        BdcBlShQO bdcBlShQO = new BdcBlShQO();
        if (StringUtils.isNotBlank(bdcBlShQoStr)) {
            bdcBlShQO = JSONObject.parseObject(bdcBlShQoStr, BdcBlShQO.class);
        }
        Map map = JSONObject.parseObject(JSONObject.toJSONString(bdcBlShQO), Map.class);
        if(StringUtils.isNotBlank(version) && StringUtils.equals(version, CommonConstantUtils.SYSTEM_VERSION_BB)){
            map.put("sortParameter","x.slbh desc");
        }
        if(StringUtils.isNotBlank(pxgz)){
            map.put("sortParameter",pxgz);
        }
        try {
            return repo.selectPaging("listBlShByPage", map, pageable);
        }catch (Exception e){
            e.printStackTrace();
            //出现错误,将排序拿掉再试一次
            map.remove("sortParameter");
            return repo.selectPaging("listBlShByPage", map, pageable);
        }
    }

    /**
     * 生成补录审核信息
     *
     * @param bdcBlShDO 不动产补录审核对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public void generateBlShxx(@RequestBody BdcBlShDO bdcBlShDO) {
        bdcXxblShService.generateBlShxx(bdcBlShDO);
    }

    @Override
    public void forwardBlShxx(@RequestBody BdcBlShDO bdcBlShDO) {
        bdcXxblShService.forwardBlShxx(bdcBlShDO);
    }

    @Override
    public void plForwardBlShxx(@RequestBody List<BdcBlShDO> bdcBlShDOList) {
        if(CollectionUtils.isNotEmpty(bdcBlShDOList)){
            bdcBlShDOList.stream().filter(Objects::nonNull)
                    .forEach(bdcBlShDO -> bdcXxblShService.forwardBlShxx(bdcBlShDO));
        }
    }

    @Override
    public List<BdcGzyzVO> dbYz(String gzlslid) throws Exception {
        return bdcXxblShService.dbYz(gzlslid);
    }

    @Override
    public void endBlShxx(String blshid) {
        bdcXxblShService.endBlShxx(blshid);
    }

    @Override
    public void plEndBlShxx(@RequestBody List<BdcBlShDO> bdcBlShDOList) {
        if(CollectionUtils.isNotEmpty(bdcBlShDOList)){
            bdcBlShDOList.stream().filter(Objects::nonNull)
                    .filter(bdcBlShDO -> StringUtils.isNotBlank(bdcBlShDO.getBlshid()))
                    .forEach(bdcBlShDO ->bdcXxblShService.endBlShxx(bdcBlShDO.getBlshid()));
        }
    }

    @Override
    public boolean backBlShxx(String blshid) {
        return bdcXxblShService.backBlShxx(blshid);
    }

    @Override
    public Integer checkIsSh(@RequestBody BdcBlShDO bdcBlShDO) {
        return bdcXxblShService.checkIsSh(bdcBlShDO);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcBlShDO]
     * @return cn.gtmap.realestate.common.core.domain.BdcBlShDO
     * @description 根据项目id和受理人id查询可转发的补录记录
     */
    @Override
    public BdcBlShDO queryBdcBlshDO(@RequestBody BdcBlShDO bdcBlShDO) {
        return bdcXxblShService.queryBdcBlshDO(bdcBlShDO);
    }

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @params [bdcBlShDO]
     * @return cn.gtmap.realestate.common.core.domain.BdcBlShDO
     * @description 更新信息补录审核信息
     */
    @Override
    public Integer updateBlshxx(@RequestBody BdcBlShDO bdcBlShDO) {
        return bdcXxblShService.updateBlshxx(bdcBlShDO);
    }

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @params [bdcBlShDO]
     * @return cn.gtmap.realestate.common.core.domain.BdcBlShDO
     * @description 根据xmid查询信息补录审核信息
     */
    @Override
    public List<BdcBlShDO> queryBlshxxByXmid(@RequestParam("xmid")  String xmid) {
        return bdcXxblShService.queryBlshxxByXmid(xmid);
    }
}

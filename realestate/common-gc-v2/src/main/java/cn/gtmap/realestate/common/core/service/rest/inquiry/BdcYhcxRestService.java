package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcYyDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYhcxDyaDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/28
 * @description 银行查询
 */
public interface BdcYhcxRestService {
    /**
     * 根据证书id查询项目信息
     * @param zsid
     * @return
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcYhcx/bdcxm")
    List<BdcXmDO> listBdcXmByZsid(@RequestParam("zsid") String zsid);

    /**
     * 根据不动产单元号列表查询抵押权信息
     * @param bdcdyhList
     * @return
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcYhcx/dyaq")
    List<BdcYhcxDyaDTO> listBdcDyaqByBdcdyh(@RequestParam("bdcdyhList") List<String> bdcdyhList);

    /**
     * 根据不动产单元号列表查询抵押权信息
     * @param bdcdyhList
     * @return
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcYhcx/cf")
    List<BdcCfDO> listBdcCfByBdcdyh(@RequestParam("bdcdyhList") List<String> bdcdyhList);

    /**
     * 根据不动产单元号列表查询抵押权信息
     * @param bdcdyhList
     * @return
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcYhcx/yy")
    List<BdcYyDO> listBdcYyByBdcdyh(@RequestParam("bdcdyhList") List<String> bdcdyhList);

    /**
     * 根据不动产单元号列表查询抵押权信息
     * @param bdcdyhList
     * @return
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcYhcx/sd")
    List<String> listBdcSdxxByBdcdyh(@RequestParam("bdcdyhList") List<String> bdcdyhList);

    /**
     * @param bdcZsQO 证书查询对象
     * @return List<BdcZsDO> 不动产权证list
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取不动产权证书列表
     */
    @RequestMapping(value = "/realestate-inquiry/rest/v1.0/bdcYhcx/zs/list", method = RequestMethod.POST)
    List<BdcZsDO> listBdcZs(@RequestBody BdcZsQO bdcZsQO);

}

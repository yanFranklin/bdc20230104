package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwtcxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcFwxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.CompareFwtcxxResultDTO;
import cn.gtmap.realestate.common.core.qo.accept.CompareFwtcxxQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/12
 * @description 房屋查询rest服务
 */
public interface BdcSlFwcxRestService {

    /**
     * @param xmid 项目ID
     * @return 房屋信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID查询房屋信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/fwcx/{xmid}")
    BdcFwxxDTO listFwxxByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * @param xmid 项目ID
     * @return 房屋套次信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid和申请人类别获取房屋套次信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/fwcx/fwtcxx/{xmid}")
    List<BdcSlFwtcxxDO> listBdcSlFwtcxxByXmid(@PathVariable(value = "xmid") String xmid,@RequestParam("sqrlb") String sqrlb);

    /**
     * @param xmid  项目ID
     * @param sqrlb 申请人类别
     * @param isYz  是否验证比较房查套次与申报套次
     * @return 住房信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID和申请人住房信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/zfxx/{xmid}")
    List<BdcSlFwtcxxDO> listBdcZfxxDTO(@PathVariable(value = "xmid") String xmid, @RequestParam("sqrlb") String sqrlb, @RequestParam("isYz") Boolean isYz);

    /**
     * @param xmid  项目ID
     * @param sqrlb 申请人类别
     * @param isYz  是否验证比较房查套次与申报套次
     * @return 住房信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID和申请人住房信息(南通版本)
     */
    @PostMapping("/realestate-accept/rest/v1.0/zfxx/nt/{xmid}")
    List<BdcSlFwtcxxDO> listBdcZfxxDTONT(@PathVariable(value = "xmid") String xmid, @RequestParam("sqrlb") String
            sqrlb, @RequestParam("isYz") Boolean isYz);

    /**
     * 房屋套次信息对比
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param compareFwtcxxQO
     * @return
     */
    @PostMapping("/realestate-accept/rest/v1.0/zfxx/nantong/compareFwtcxx")
    CompareFwtcxxResultDTO compareFwtcxx(@RequestBody CompareFwtcxxQO compareFwtcxxQO);

    /**
     * @param compareFwtcxxQO 套次比对信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 导入套次比对信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/zfxx/fwtcbdxx")
    void drFwtcxx(@RequestBody CompareFwtcxxQO compareFwtcxxQO);
}

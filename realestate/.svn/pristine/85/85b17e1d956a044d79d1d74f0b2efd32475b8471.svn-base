package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.DyhGzWcqlsgxDTO;
import cn.gtmap.realestate.common.core.dto.init.DyhGzXmDTO;
import cn.gtmap.realestate.common.core.qo.init.DyhGzQO;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/9/21
 * @description 批量更新不动产单元服务
 */
public interface BdcDyhGzRestService {

    /**
     * @param dyhGzQO 单元号更正对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新不动产单元号
     */
    @PostMapping("/init/rest/v1.0/dyhgz")
    void updateBdcdyhPl(@RequestBody DyhGzQO dyhGzQO);

    /**
     * @param xmid 项目ID
     * @param xmtype 1：现势产权项目ID 2新增权利项目ID
     * @return 单元号更正项目集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取所关联的所有项目数据
     */
    @GetMapping("/init/rest/v1.0/dyhgz/xmxx/xmid")
    DyhGzXmDTO queryDyhGzXmDTO(@RequestParam("xmid") String xmid,@RequestParam("xmtype") String xmtype);

    /**
     * @param dyhGzQO 单元号更正对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证选择的数据是否存在疑似关联数据
     */
    @PostMapping("/init/rest/v1.0/dyhgz/yz/xzxx")
    List<BdcGzyzVO> yzXzxx(@RequestBody DyhGzQO dyhGzQO);

    /**
     * @param bdcXmDOList 项目集合
     * @return 单元号更正无产权历史关系数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据已选项目单元号获取无产权历史关系数据
     */
    @PostMapping("/init/rest/v1.0/dyhgz/wcqlsgx")
    DyhGzWcqlsgxDTO queryDyhGzWcqlsgxDTO(@RequestBody List<BdcXmDO> bdcXmDOList);

    /**
     * @param dyhGzQOList 单元号替换信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 替换外联产权对应的限制权利单元号
     */
    @PostMapping("/init/rest/v1.0/dyhgz/xzql")
    void updateWlcqXzql(@RequestBody List<DyhGzQO> dyhGzQOList,@RequestParam(value = "processInsId",required = false) String processInsId, @RequestParam(value = "currentUserName",required = false) String currentUserName);
}

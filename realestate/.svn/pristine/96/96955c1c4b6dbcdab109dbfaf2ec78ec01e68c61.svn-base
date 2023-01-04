package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/6/11.
 * @description
 */
public interface BdcXmFbRestService {

    /**
     * 批量更新项目附表信息（可更新空值）
     * @param bdcDjxxUpdateQO
     * jsonStr 要更新的JSON字符串
     * whereMap 更新查询条件
     * className 类名字符串
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/init/rest/v1.0/xmfb/jsonStr")
    int updateBatchBdcxmFb(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception;
    /**
     * 通过传入参数返回不动产项目附表信息
     * @param bdcXmFbQO
     * @return
     */
    @PostMapping(path = "/init/rest/v1.0/xmfb/list")
    List<BdcXmFbDO> listBdcXmFb(@RequestBody BdcXmFbQO bdcXmFbQO);

    /**
     * 通过不动产单元查询现势的sfszfgf状态
     * @param bdcdyh
     * @return
     */
    @PostMapping(path = "/init/rest/v1.0/xmfb/queryXmfbSfszfgf/{bdcdyh}")
    List<BdcXmDO> listBdcFgfXmByBdcdyh(@PathVariable(name = "bdcdyh") String bdcdyh);

    /**
     * 根据业务信息查询关联权籍管理代码
     * @param bdcQjgldmQO 登记业务数据（例如单元号、证号等）
     * @return 权籍管理代码
     */
    @PostMapping(path = "/init/rest/v1.0/xmfb/qjgldm")
    String queryQjgldm(@RequestBody BdcQjgldmQO bdcQjgldmQO);

    /**
      * @param gzlslids 工作流实例ID
      * @param zsrlzt 证书认领状态 0:未认领 1：已认领
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 更新证书认领状态
      */
    @PostMapping(path = "/init/rest/v1.0/xmfb/zsrlzt/{zsrlzt}")
    int updateZsrlzt(@RequestBody List<String> gzlslids,@PathVariable(name = "zsrlzt") Integer zsrlzt);

    @PostMapping(path = "/init/rest/v1.0/xmfb/updateXmfbByXmid")
    int updateBatchBdcxmFbByXmid(@RequestBody List<BdcXmFbDO> bdcXmFbDOList);

    @PostMapping(path = "/init/rest/v1.0/xmfb/queryDagsd")
    String queryDagsd(@RequestBody BdcXmFbQO bdcXmFbQO);
}

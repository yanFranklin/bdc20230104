package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/6/11.
 * @description
 */
public interface BdcXmFbService {

    /**
     * 批量更新项目附表信息（可更新空值）
     * @param bdcDjxxUpdateQO
     * jsonStr 要更新的JSON字符串（）
     * whereMap 更新查询条件
     * className 类名字符串
     * @return
     * @throws Exception
     */
    int updateBatchBdcXmFb(BdcDjxxUpdateQO bdcDjxxUpdateQO) throws ClassNotFoundException;

    /**
     * 通过传入参数查询不动产项目附表信息
     * @param bdcXmFbQO
     * @return
     */
    List<BdcXmFbDO> listBdcXmFb(BdcXmFbQO bdcXmFbQO);


    /**
     * 通过不动产单元查询现势的sfszfgf状态
     * @param bdcdyh
     * @return
     */
    List<BdcXmDO> listBdcFgfXmByBdcdyh(String bdcdyh);

    /**
     * 根据业务信息查询关联权籍管理代码
     * @param bdcQjgldmQO 登记业务数据（例如单元号、证号等）
     * @return 权籍管理代码
     */
    String queryQjgldm(BdcQjgldmQO bdcQjgldmQO);

    /**
     * @param gzlslids 工作流实例ID
     * @param zsrlzt 证书认领状态 0:未认领 1：已认领
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新证书认领状态
     */
    int updateZsrlzt(List<String> gzlslids,Integer zsrlzt);
}

package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcDsfdjJhxxDO;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/6/5
 * @description 第三方交互信息服务
 */
public interface BdcDsfdjJhxxService {

    /**
     * @param gzlslid 工作流实例ID
     * @return 第三方交互信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  新增写入第三方交互信息
     */
    List<BdcDsfdjJhxxDO> initDsfdjJhxx(String gzlslid);

    /**
     * @param jsonObject 需要更新的对象
     * @param bdcslbh 不动产受理编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  更新第三方交互信息
     */
    void updateDsfdjJhxxByBdcslbh(JSONObject jsonObject,String bdcslbh);

    /**
     * @param bdcDsfdjJhxxDO 查询实体
     * @return 第三方交互信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询
     */
    List<BdcDsfdjJhxxDO> listBdcDsfdjJhxx(BdcDsfdjJhxxDO bdcDsfdjJhxxDO);

    /**
     * 第三方交互信息通知更改任务状态
     * @param bdcDsfdjJhxxDO 第三方交互信息DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void dsfTzModifyTaskStatus(BdcDsfdjJhxxDO bdcDsfdjJhxxDO);

    /**
     * @param gzlslid 工作流示例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除不动产与第三方交互信息
     */
    void deleteDsfdjJhxx(String gzlslid);
}

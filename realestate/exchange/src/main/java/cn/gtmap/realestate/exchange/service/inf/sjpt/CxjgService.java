package cn.gtmap.realestate.exchange.service.inf.sjpt;

import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqq;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqqXm;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.SjptCxjgRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.SjptCxjgRequestData;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.SjptCxjgRequestDataWithEmptyString;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-29
 * @description 查询结果相关服务
 */
public interface CxjgService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return boolean
     * @description 验证上一次的任务是否结束
     */
    boolean checkLastQuarzFinished();

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param
     * @return void
     * @description 执行提交查询请求
     */
    void executeCommitCxsq(String callMethod);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return void
     * @description 执行查询请求
     */
    void executeQueryCxsq(String callMethod);

//    /**
//     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
//     * @param gxPeCxqqList 申请集合
//     * @param sfQuartz 是否是定时任务触发的
//     * @return
//     * @description 批量执行查询
//     */
//    void batchExexuteQueryCxqq(List<GxPeCxqq> gxPeCxqqList, boolean sfQuartz) throws Exception;


    /**
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @version 1.0, 2017/10/18
     * @param gxPeCxqqXm 查询请求对象
     * @return
     * @description 从数据源（共享库或登记业务库）获取到查询请求结果并保存至GXPe库
     */
    GxPeCxqqXm queryAndSaveApply(final GxPeCxqqXm gxPeCxqqXm) throws Exception;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param gxPeCxqq
     * @return cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqq
     * @description 刷新查询申请状态
     */
    GxPeCxqq flushCxqqZt(final GxPeCxqq gxPeCxqq);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param cxsqdh
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.SjptCxjgRequestData>
     * @description 获取查询结果
     */
    SjptCxjgRequestDTO getCxjgListByCxsqdh(String cxsqdh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param cxData
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.SjptCxjgRequestData>
     * @description  根据 PECXDO 查询结果
     */
    List<SjptCxjgRequestData> getCxjgListByPeCxDO(List<JSONObject> cxData) throws Exception;
}

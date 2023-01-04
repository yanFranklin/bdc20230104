package cn.gtmap.realestate.exchange.service.inf.sjpt;


import cn.gtmap.realestate.exchange.core.domain.sjpt.PeCxDO;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.PeCommitCxsqjg;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.PeCommitCxsqjgWithEmptyString;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-29
 * @description 执行查询结果相关服务
 */
public interface QueryCxjgService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param cxsqdh 查询申请单号
     * @param wsbh 文书编号
     * @return
     * @description 查询区县库权利数据
     */
    List queryCxjg(String cxsqdh, String wsbh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param cxsqdh 查询申请单号
     * @param wsbh 文书编号
     * @return
     * @description 删除区县库权利数据
     */
    void deleteCxjg(String cxsqdh, String wsbh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param peCxDO 查询参数对象
     * @param peCommitCxsqjg  查询结果对象
     * @param type 区分是实时查询还是批量查询（sscx 实时查询  plcx 批量查询）
     * @return PeCommitCxsqjg
     * @description 执行查询登记数据
     */
    PeCommitCxsqjg cxsj(PeCxDO peCxDO, PeCommitCxsqjg peCommitCxsqjg, String type) throws Exception;


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param peCommitCxsqjg 查询结果对象
     * @param cxsqdh 查询申请单号
     * @return
     * @description 保存入库
     */
    void saveCxjg(PeCommitCxsqjg peCommitCxsqjg, String cxsqdh);

    /**
     * 保存权利进 peCommitCxsqjg
     * @param peCommitCxsqjg
     * @param qlList
     */
    void setQlList(PeCommitCxsqjg peCommitCxsqjg,List qlList);

    void setQlListWithDefaultValueEmptyString(PeCommitCxsqjgWithEmptyString peCommitCxsqjg, List qlList);

    /**
     * 从peCommitCxsqjg取权利集合
     * @param peCommitCxsqjg
     * @param peCommitCxsqjg
     */
    List getQlList(PeCommitCxsqjg peCommitCxsqjg);
}

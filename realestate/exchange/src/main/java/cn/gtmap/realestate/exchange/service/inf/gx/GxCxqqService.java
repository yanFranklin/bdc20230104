package cn.gtmap.realestate.exchange.service.inf.gx;

import cn.gtmap.gtc.formclient.common.result.TreeModel;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqqXm;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeZdrzczlx;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
 * @version 1.0  2019-09-10
 * @description 查询请求
 */
public interface GxCxqqService {

    /**
     * @param pageable
     * @param map
     * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
     * @description 查询请求
     */
    Page<Object> listCxqqByPages(Pageable pageable, Map<String, String> map);

    /**
     * @param pageable
     * @param map
     * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
     * @description 查询人信息
     */
    Page<Object> getCxrByPage(Pageable pageable, Map<String, String> map);

    /**
     * @param pageable
     * @param map
     * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
     * @description 获取日志
     */
    Page<Object> getRzByPage(Pageable pageable, Map<String, String> map);

    /**
     * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
     * @description 获取日志操作类型
     */
    List<GxPeZdrzczlx> getCzlx();

    /**
     * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
     * @param cxsqdh 申请单号
     * @description 执行上报
     */
    Object executeCommit(String cxsqdh);

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @param kssj , jssj
     * @description 根据起始日期统计省厅数据的查询量
     */
    Integer getSjsjlcxCount(String kssj,String jssj);

    /**
     * @author
     * @param cxsqdhList
     * @description 执行批量上报
     */
    List<Object> executeCommitList(List<String> cxsqdhList);

    /**
     * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
     * @param cxsqdh 申请单号
     * @description 执行查询
     */
    void executeQuery(String cxsqdh);

    /**
     * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
     * @param xmid
     * @description 执行查询
     */
    String executeQueryCxqqXm(String xmid);

    List<TreeModel> initQlTreeModel(GxPeCxqqXm gxPeCxqqXm, List<TreeModel> treeModelList);

    void deleteCxjgByCxqqXm(GxPeCxqqXm gxPeCxqqXm);

}

package cn.gtmap.realestate.building.core.mapper;

import cn.gtmap.realestate.common.core.dto.exchange.wwsq.QlrlistResponse;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-23
 * @description 为受理子系统提供根据BDCLX分页查询BDCDY
 */
public interface AcceptBdcdyMapper {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return int
     * @description 查询土地定着物总数
     */
    Integer countTdDzwBdcdy(Map paramMap);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.List<java.util.Map>
     * @description 非分页查询 查询土地定着物
     */
    List<Map> listTdDzwBdcdyByPageOrder(Map paramMap);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.lang.Integer
     * @description   查询海域定着物总数
     */
    Integer countHyDzwBdcdy(Map paramMap);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.List<java.util.Map>
     * @description 非分页查询 查询海域定着物
     */
    List<Map> listHyDzwBdcdyByPageOrder(Map paramMap);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.lang.Integer
     * @description   查询构筑物不动产单元总数
     */
    Integer countGzwBdcdy(Map paramMap);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.lang.Integer
     * @description 非分页查询 查询构筑物不动产单元
     */
    List<Map> listGzwBdcdyByPageOrder(Map paramMap);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 非分页查询承包方承包宗地的不动产单元
     */
    List<Map> listCbfCbzdBdcdyByPageOrder(Map paramMap);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.Map
     * @description 根据BDCDY 查询房屋类型的流程状态
     */
    Map getFwBdcdyLcztByBdcdyh(Map<String,String> paramMap);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.Map
     * @description  查询土地的BDCDY的流程状态
     */
    Map getTdBdcdyLcztByBdcdyh(Map<String,String> paramMap);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.Map
     * @description 查询 林权、构筑物、承包宗地 BDCDY 的流程状态
     */
    Map getDcbBdcdyLcztByBdcdyh(Map<String,String> paramMap);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    String queryZlBySgbh(String sgbh);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    List<QlrlistResponse> queryQjQlr(Map<String,String> paramMap);

}

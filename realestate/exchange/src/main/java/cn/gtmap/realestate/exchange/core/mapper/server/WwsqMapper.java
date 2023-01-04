package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.exchange.core.domain.GxRygfqkDO;
import cn.gtmap.realestate.exchange.core.dto.jgpt.ywsl.JgptYwslDO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dycxcq.request.DycxcqRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dycxcq.response.DycxcqResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dyxxAndYgdyxx.BdcFdcqDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dyxxAndYgdyxx.BdcJsydsyqDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.querycfxx.response.GetWwsqCfxxResponseCqxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.rygfqk.request.RygfqkRequestDTO;
import cn.gtmap.realestate.exchange.core.qo.BdcCfxxQO;
import cn.gtmap.realestate.exchange.core.qo.jgpt.JgpTjQO;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 外网申请相关数据
 *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 *@version 1.0
 *@description
 */
public interface WwsqMapper {

    /**
     *产权证查询信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param map 参数集合
     *@return  响应结构
     *@description
     */
   List<Map> getWwsqCqzxx(Map map);

    /**
     *抵押查询项目信息
     *@author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     *@param requestDTO
     *@return  响应结构
     *@description
     */
   List<BdcXmDO> getWwsqDyXmxx(DycxcqRequestDTO requestDTO);

    /**
     *抵押查询产权信息
     *@author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     *@param bdcdyh
     *@return  响应结构
     *@description
     */
   List<DycxcqResponseDTO> getWwsqDyCqxx(@Param("bdcdyh") String bdcdyh);

   /**
    * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
    * @param map
    * @return java.util.List<java.util.Map>
    * @description 4.4.11 抵押首次获取产权证信息
    */
   List<Map> getBdczsxx(Map map);


   /**
    * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
    * @return java.util.List<cn.gtmap.realestate.exchange.core.domain.GxRygfqkDO>
    * @description 查询人员购房情况表
    */
   List<GxRygfqkDO> listRygfqk(RygfqkRequestDTO requestDTO);

    /**
     * 查询不动产证书
     *
     * @param paramMap
     * @return
     */
    List<BdcZsDO> listBdcZs(JSONObject paramMap);

    /**
     * 根据bdcdyh查询fdcq表现势数据
     * @param bdcdyh
     * @return
     * @Date 2021/3/1
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<BdcFdcqDO> fdcqList(@Param("bdcdyh") String bdcdyh);


    /**
     * 查封信息查询（解封、续封）
     *
     * @param cfxxqo
     * @return
     */
    List<GetWwsqCfxxResponseCqxx> listCfxx(BdcCfxxQO cfxxqo);

    /**
     * @param 开始时间，结束时间
     * @return 各业务统计数量
     * @Date 2022/9/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<JgptYwslDO> listYwbjsl(JgpTjQO jgpTjQO);


    /**
     * @param 开始时间，结束时间
     * @return 各业务统计数量
     * @Date 2022/9/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<JgptYwslDO> listCyywbjsl(JgpTjQO jgpTjQO);

    /**
     * 查询指定单元房地产权信息
     * @param subList 不动产单元号集合
     * @return {List} 房地产权信息
     */
    List<BdcFdcqDTO> listBdcFdcqxx(@Param(value = "bdcdyhList") List subList);

    /**
     * 查询指定单元建设用地使用权信息
     * @param subList 不动产单元号集合
     * @return {List} 建设用地使用权信息
     */
    List<BdcJsydsyqDTO> listBdcJsydsyqxx(@Param(value = "bdcdyhList") List subList);
}

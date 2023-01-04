package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.exchange.core.domain.*;
import cn.gtmap.realestate.exchange.core.dto.AccessLogDTO;
import cn.gtmap.realestate.exchange.core.dto.BdcXmForZxAccessDTO;
import cn.gtmap.realestate.exchange.core.qo.BdcXmQO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 不动产项目映射层
 */
@Repository
public interface BdcXmMapper {

    /**
     * 根据项目ID  查找项目信息
     *
     * @param proid
     * @return
     */
    BdcXmDO queryBdcXm(String proid);


    /**
     * 根据项目工作流  查找项目信息
     *
     * @param gzlslid
     * @return
     */
    List<BdcXmDO> queryBdcXmList(String gzlslid);

    /**
     * 根据审批系统业务号  查找项目信息
     *
     * @param spxtywh
     * @return
     */
    List<BdcXmDO> queryBdcXmListBySpxtywh(String spxtywh);

    /**
     * 获取不动产登记项目：登记类型、权力类型、房屋类型、不动产类型
     *
     * @param proid
     * @return
     */
    Map<String, Object> queryBdcXmLx(String proid);

    /**
     * 通过业务号获取接入业务服务参数
     *
     * @return
     */
    Map<String, Object> getBdcSubmitZdSqlx(Map<String, Object> bdcXmLxMap);

    /**
     * @param paramMap
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 通过业务号获取接入业务服务参数
     */
    List<Map<String, Object>> getExchangeZd(Map<String, String> paramMap);

    /**
     * 获取BizId
     *
     * @return
     */
    Integer getBizMsgID();

    /**
     * @param xmid
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 通过xmid查询权利人名称和不动产权证号
     */
    List<Map<String, String>> getQlrAndBdcqzhByXmid(String xmid);

    /**
     * @param paramMap
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 通过权利人名称和权利人证件号查xm，fdcq等房改房产权信息
     */
    List<Map<String, Object>> listFgfCqxx(Map paramMap);

    List<BdcXmFbDO> listBdcXmFbDo(Map paramMap);

    /**
     * 通过xmid获取关联的上一手项目id
     *
     * @param paramMap
     * @return
     */
    List<BdcXmLsgxDO> queryYxmidByXmidForZh(Map paramMap);

    /**
     * @param paramMap
     * @return
     */
    List<BdcXmLsgxDO> queryBdcXmLsgxByyxmidAndZxyqlIsZeroOrNvl(Map paramMap);


    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据bdcdyh和bdcqzh获取现势xm
     */
    List<BdcXmDO> getXsXmByBdcdyhAndBdcqzh(@Param("bdcdyh") String bdcdyh, @Param("bdcqzh") String bdcqzh);

    /**
     * @param
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据xmid获取zslx
     */
    Integer getBdcZslxByXmid(@Param("xmid") String xmid);

    /**
     * 根据时间段推送
     *
     * @param
     * @return
     * @Date 2021/4/20
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<String> getTsGzlslidList(Map paramMap);

    /**
     * 查询当前项目列表外联的注销项目
     *
     * @param xmids
     * @return
     */
    List<BdcXmForZxAccessDTO> listBdcXmForZxAccessDTO(@Param("xmids") List<String> xmids);

    /**
     * 公告外部系统定时任务调用查询xmxx过滤bdc_gg表的数据
     *
     * @return
     */
    List<BdcXmDO> getXmxxByDjsjAndSplyAndGzldyIdWithoutBdcGg(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("sply") Integer sply, @Param("gzldyId") String gzldyId);

    List<BdcXmDO> getXmBySlbhAndCqzh(@Param("bdcqzh") String bdcqzh, @Param("slbh") String slbh);

    /**
     * 根据slbh和产权证号查项目
     *
     * @param bdcqzh
     * @return
     * @Date 2021/8/31
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<BdcXmDO> queryXmBySlbhAndCqzh(@Param("bdcqzh") String bdcqzh, @Param("slbh") String slbh);


    /**
     * 语句查询权利人，zjh多个
     *
     * @param
     * @return
     * @Date 2021/9/17
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<BdcQlrDO> queryQlrByZjh(@Param("qlrmc") String qlrmc, @Param("qlrzjh") String qlrzjh, @Param("bdcqzh") String bdcqzh, @Param("qlrlb") String qlrlb);

    /**
     * 语句查询权利人，zjh多个,qzdm
     *
     * @param
     * @return
     * @Date 2021/9/17
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<BdcQlrDO> queryQlrByZjhWitjQjgldm(@Param("qlrmc") String qlrmc, @Param("qlrzjh") String qlrzjh, @Param("qlrlb") String qlrlb, @Param("qjgldm") String qjgldm);

    /**
     * bdcdyh集合查询不动产项目
     *
     * @param
     * @return
     * @Date 2021/10/12
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<BdcXmDO> queryBdcxmListByBdcdyhList(@Param("bdcdyhList") List<String> bdcdyhList, @Param("qszt") Integer qszt);

    /**
     * 根据合同号查询交易信息_舒城
     *
     * @param htbh 合同编号
     * @return map map
     * @Date 2022/1/11
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Map queryFcjyView(@Param("htbh") String htbh);

    /**
     * 根据合同号查询交易信息_舒城
     *
     * @param docId
     * @return map map
     * @Date 2022/1/11
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<Map> queryFcjyGyrView(@Param("docId") String docId);

    /**
     * 根据不动产单元号查现势fdcq
     *
     * @param bdcdyh
     * @return
     * @Date 2022/1/25
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    BdcFdcqDO quertXsFdcqByBdcdyh(@Param("bdcdyh") String bdcdyh);

    /**
     * 查询不动产项目信息
     *
     * @param bdcXmQO
     * @return
     */
    List<BdcXmDO> listBdcXm(BdcXmQO bdcXmQO);

    /**
     * 查询省份上报信息
     *
     * @param paramMap
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<AccessLogDTO> listAccessLogByPageOrderForBatchAccess(Map paramMap);


    /**
     * 根据xmid查未上报的数据
     *
     * @param xmid
     * @return
     * @Date 2022/1/25
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Integer listSbqkCount(@Param("xmid") String xmid);
}

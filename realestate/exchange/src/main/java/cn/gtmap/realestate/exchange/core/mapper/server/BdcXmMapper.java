package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.exchange.KttZhjbxxDO;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.moke.MokeZzdzjPlszDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx.CourtYwxxcxRequestDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.exchange.core.dto.common.BdcXmForZxAccessDTO;
import cn.gtmap.realestate.exchange.core.dto.ggxypt.qy.QyCqDTO;
import cn.gtmap.realestate.exchange.core.dto.ggxypt.qy.QyDyaDTO;
import cn.gtmap.realestate.exchange.core.dto.ggxypt.zrr.ZrrCqDTO;
import cn.gtmap.realestate.exchange.core.dto.ggxypt.zrr.ZrrDyaDTO;
import cn.gtmap.realestate.exchange.core.dto.shucheng.fgj.ShuchengFgjClfHyxxDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.fcjy.rcsd.BdcRcsdDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.fcjy.rcsd.BdcRcsdFyxxZtDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getYgxx.response.DvYdy;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequestBody;
import cn.gtmap.realestate.exchange.core.qo.fcjy.BdcRcsdQO;
import cn.gtmap.realestate.exchange.core.qo.ggxypt.qy.GgxyptQyBdccqQO;
import cn.gtmap.realestate.exchange.core.qo.ggxypt.qy.GgxyptQyDyQO;
import cn.gtmap.realestate.exchange.core.qo.ggxypt.zrr.ZrrBdcCqQO;
import cn.gtmap.realestate.exchange.core.qo.ggxypt.zrr.ZrrBdcDyaQO;
import org.apache.ibatis.annotations.Param;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 不动产项目映射层
 */
@Component
public interface BdcXmMapper {

    /**
     * 根据项目ID  查找项目信息
     *
     * @param proid
     * @return
     */
    BdcXmDO queryBdcXm(String proid);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据项目历史关系的关系id查询
     * @date : 2022/12/6 10:56
     */
    BdcXmDO queryBdcxmByLsgxGxid(String gxid);


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

    List<BdcXmDO> listCourtXscqWithZjhOrQlrmc(CourtYwxxcxRequestDTO courtYwxxcxRequestDTO);

    List<BdcXmDO> listCourtXscqWithZjhAndQlrmc(CourtYwxxcxRequestDTO courtYwxxcxRequestDTO);


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
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询外联项目没有成功接入的数据
     * @date : 2022/12/6 14:01
     */
    Set<BdcXmForZxAccessDTO> listZxWlxmNotAccess(@Param("xmids") List<String> xmids);

    /**
     * 公告外部系统定时任务调用查询xmxx过滤bdc_gg表的数据
     *
     * @return
     */
    List<BdcXmDO> getXmxxByDjsjAndSplyAndGzldyIdWithoutBdcGg(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("sply") Integer sply, @Param("gzldyId") String gzldyId);

    List<DvYdy> queryYdyChangzhou(GrdacxRequestBody requestBody);

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
     * 查询权利人（和上面queryQlrByZjh逻辑一致，主要优化原有逻辑，因为是为了判断权利人是否存在，并且避免大批量权利人查询出来浪费性能，因此改为查询数量）
     * @param qlrmc 权利人名称
     * @param qlrzjh 权利人证件号
     * @param bdcqzh 不动产权证号
     * @param qlrlb 权利人类别
     * @return {Integer} 权利人数量
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    Integer countQlrByZjh(@Param("qlrmc") String qlrmc, @Param("qlrzjh") String qlrzjh, @Param("bdcqzh") String bdcqzh, @Param("qlrlb") String qlrlb);

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
     * @param htbh 合同编号
     * @return map map
     * @Date 2022/1/11
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<Map> queryFcjyGyrView(@Param("docId") String docId);

    /**
     * 根据工作流实例查询打证信息
     *
     * @param gzlslid
     * @return
     * @Date 2022/1/13
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<MokeZzdzjPlszDTO> queryMokeZsxx(@Param("gzlslid") String gzlslid, @Param("lzfsList") List<Integer> lzfsList);

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
     * 给房管局提供查询接口-舒城县存量房交易备案接口
     *
     * @param ShuchengFgjClfHyxxDTO clfHyxxDTO
     * @return
     * @Date 2022/4/6
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<Map> queryFgjClfxx(ShuchengFgjClfHyxxDTO clfHyxxDTO);


    /**
     * 给房管局提供查询接口-舒城县一手房接口
     *
     * @param ShuchengFgjClfHyxxDTO clfHyxxDTO
     * @return
     * @Date 2022/4/6
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<Map> queryFgjYsfxx(@Param("bdcDyhList") List<String> bdcDyhList , @Param("fwbm") String fwbm);


    /**
     * 给房管局提供查询接口-舒城县一手房抵押或预告信息接口
     *
     * @param ShuchengFgjClfHyxxDTO clfHyxxDTO
     * @return
     * @Date 2022/4/6
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<Map> queryFgjYsfDyYgxx(@Param("bdcDyhList") List<String> bdcDyhList, @Param("fwbm") String fwbm);

    /**
     * @param gzlslid
     * @return
     */
    List<BdcYgDO> queryYgxxByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * @param bdcXmQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询项目信息
     * @date : 2022/7/28 11:30
     */
    List<BdcXmDO> listBdcXm(BdcXmQO bdcXmQO);


    /**
     * @param qyBdccqQO
     * @author <a href="mailto:gaolining@gtmap.cn">huangjian</a>
     * @description 查询企业产权信息
     * @date : 2022/7/28 11:30
     */
    List<QyCqDTO> queryQycqList(GgxyptQyBdccqQO qyBdccqQO);

    /**
     * @param qyDyQO
     * @author <a href="mailto:gaolining@gtmap.cn">huangjian</a>
     * @description 查询企业抵押权信息
     * @date : 2022/7/28 11:30
     */
    List<QyDyaDTO> queryQyDyaList(GgxyptQyDyQO qyDyQO);

    /**
     * @param qyBdccqQO
     * @author <a href="mailto:gaolining@gtmap.cn">huangjian</a>
     * @description 查询企业产权信息
     * @date : 2022/7/28 11:30
     */
    List<ZrrCqDTO> queryZrrCqList(ZrrBdcCqQO zrrBdcCqQO);

    /**
     * @param qyDyQO
     * @author <a href="mailto:gaolining@gtmap.cn">huangjian</a>
     * @description 查询个人抵押权信息
     * @date : 2022/7/28 11:30
     */
    List<ZrrDyaDTO> queryZrrDyaList(ZrrBdcDyaQO zrrBdcDyaQO);

    /**
     * 人才锁定房源信息查询
     *
     * @param bdcRcsdQO bdcRcsdQO
     * @return List<BdcRcsdDTO>
     * @Date 2022/10/13
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<BdcRcsdDTO> queryRcsdList(BdcRcsdQO bdcRcsdQO);

    /**
     * 根据bdcqzh查不动产单元号
     *
     * @param bdcRcsdQO bdcRcsdQO
     * @return List<BdcRcsdDTO>
     * @Date 2022/10/13
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<BdcRcsdFyxxZtDTO> queryBdcxmByBdcqzh(BdcRcsdQO bdcRcsdQO);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据zddm查询bdcdjb_zdjbxx
     * @date : 2022/11/21 10:34
     */
    List<BdcBdcdjbZdjbxxDO> listBdcDjbZdjbxx(@Param("zddm") String zddm);

    /**
     * @param bdcQlrQO
     * @return String
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 根据工作流实例ID 查询抵押和预抵押的权利人名称
     */
    List<String> listDyaAndYdyQlrmc(BdcQlrQO bdcQlrQO);


    List<BdcXmDO> getBdcXmByQlrAndZjh(@Param("qlrmc") String qlrmc, @Param("zjh") List<String> zjh);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid查询预抵押数据
     * @date : 2022/12/15 16:18
     */
    BdcYgDO queryBdcYdya(@NotBlank @Param("xmid") String xmid);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询宗海基本信息
     * @date : 2022/12/20 14:05
     */
    List<KttZhjbxxDO> listZhjbxx(@NotBlank @Param("xmid") String xmid);
}

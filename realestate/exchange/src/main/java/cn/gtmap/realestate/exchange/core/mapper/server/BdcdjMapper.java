package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.bo.accessnewlog.Access;
import cn.gtmap.realestate.common.core.bo.accessnewlog.Register;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.exchange.BdcJrXsdjyDO;
import cn.gtmap.realestate.common.core.domain.exchange.BdcYhUrlCsDO;
import cn.gtmap.realestate.common.core.domain.exchange.ZttGyQlrDO;
import cn.gtmap.realestate.common.core.domain.exchange.sjpt.BdcExchangeZddz;
import cn.gtmap.realestate.common.core.domain.exchange.yth.QltQlXzxzDO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.sea.*;
import cn.gtmap.realestate.common.core.dto.init.BdcCfDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcDyaqDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcYdyDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcQszmQO;
import cn.gtmap.realestate.common.core.dto.register.*;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.exchange.core.domain.sjpt.PeCxDO;
import cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.qsdjxx.response.BdcXscqxxDTO;
import cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.DjxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.common.BdcAccessCountResultDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.gjj.request.GjjRequestData;
import cn.gtmap.realestate.exchange.core.dto.nantong.gjj.response.GjjCzxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.nantong.gjj.response.GjjFwxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.nantong.hlwtj.response.BlhjtjResponseData;
import cn.gtmap.realestate.exchange.core.dto.nantong.hlwtj.response.DjlxtjResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequestBody;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthQueryDzzzxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yctb.YctbGetHouseInfoResponse;
import cn.gtmap.realestate.exchange.core.dto.yctb.YctbOldBusinessReqmap;
import cn.gtmap.realestate.exchange.core.dto.yctb.YctbZxcdRequest;
import cn.gtmap.realestate.exchange.core.dto.zfxxcx.BdcZsResponseDTO;
import cn.gtmap.realestate.exchange.core.qo.BdcDjbQO;
import cn.gtmap.realestate.exchange.core.qo.DzzzXzQO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zdd on 2015/11/24.
 * 需要单独查询登记业务的方法   后面可以用server的不动产业务服务替代
 */
public interface BdcdjMapper {

    /**
     * zdd 根据项目ID  查找项目信息
     *
     * @param proid
     * @return
     */
    BdcXmDO queryBdcXm(String proid);


    /**
     * zdd 根据项目ID  查找项目信息
     *
     * @param proid
     * @return
     */
    List<BdcXmDO> listBdcXmBySlbh(String slbh);


    List<BdcXmDO> queryZsXmByZsid(String zsid);

    /**
     * 根据项目ID  查找bdcdyh
     *
     * @param xmid
     * @return
     */
    Map<String, Object> getBdcdyhByXmid(@Param(value = "xmid") String xmid);

    /**
     * zdd 关联项目关系表 查询上次不动产登记信息
     *
     * @param proid
     * @return
     */
    List<HashMap<String, String>> queryPreInfo(String proid);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询虚拟单元号匹配关系表，查询关系数据
     * @date : 2022/12/2 14:03
     */
    List<BdcXnbdcdyhGxDO> queryXndyhPpgx(Map map);

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 序列获取标识码
     */
    Integer getBsm();

    /**
     * @param map
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取证书数量
     */
    Integer getZsNum(Map map);

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取所有字典类型
     */
    List<String> getzdlx();

    /**
     * @param map
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 登记字典代码和国标字典代码对照
     */
    BdcExchangeZddz getBdcExchangeZddz(Map map);

    /**
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 登记字典代码和国标字典list全查询
     */
    List<BdcExchangeZddz> getBdcExchangeZddzList();


    /*  *//**
     * @param bdcdyid 不动产单元Id
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取不动产单元号
     *//*
    Map getBdcdyh(String bdcdyid);*/


    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取当天各业务的登簿数量
     */
    List<Map<String, String>> getDjsl(Map map);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 找平模式获取登簿量
     * @date : 2022/12/14 13:53
     */
    List<Map<String, String>> getCheatDbsj(Map map);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询登记类型根据接入业务代码前三位
     * @date : 2022/7/21 17:18
     */
    List<Map<String, String>> getJrYwdm(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取当天各业务的登簿数量 部分地区存在非省份的自定义的qxdm，并且需要查看登簿日志量，通过xm表的qxdm统计登簿量和接入量
     */
    List<Map<String, String>> getXnDjsl(Map map);

    /**
     * 获取登簿数据详情
     *
     * @param map
     * @return
     * @Date 2022/4/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<Register> getRegisterDetails(Map map);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 找平模式下的查询登簿量详情 cgbs=0，1，2
     * @date : 2022/12/14 14:13
     */
    List<Register> getCheatRegisterDetails(Map map);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询当天外联注销项目数据
     * @date : 2022/11/29 16:21
     */
    List<Map<String, String>> listWlzxXm(Map map);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据接入表查询详单
     * @date : 2022/11/29 18:17
     */
    List<Register> getJrRegisterDetails(Map map);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询外联项目详单
     * @date : 2022/11/29 18:23
     */
    List<Register> listWlxmRegsiterDetails(Map map);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid 查询证书信息
     * @date : 2022/11/30 14:29
     */
    List<BdcZsDO> listBdczsByXmid(@Param(value = "xmid") String xmid);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 获取当天各业务的登簿数量去除xmly为2的数据
     */
    List<Map<String, String>> getDjslWithOutHistoryData(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 获取当天各业务的登簿数量去除xmly为2的数据 部分地区存在非省份的自定义的qxdm，并且需要查看登簿日志量，通过xm表的qxdm统计登簿量和接入量
     */
    List<Map<String, String>> getXnDjslWithOutHistoryData(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取当天各业务的接入成功的数量
     */
    List<Map<String, String>> getAccessSl(Map map);


    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据接入表ywdm前三位截取bdclx
     * @date : 2022/7/21 17:27
     */
    List<Map<String, String>> getAccessJrYwdm(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 获取当天各业务的接入成功的数量 部分地区存在非省份的自定义的qxdm，并且需要查看登簿日志量，通过xm表的qxdm统计登簿量和接入量
     */
    List<Map<String, String>> getXnAccessSl(Map map);


    /**
     * 上报接入数量详情
     *
     * @param map
     * @return
     * @Date 2022/4/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<Access> getAccessDetails(Map map);

    /**
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取所有的行政区代码
     */
    List<Map<String, String>> queryBdcZdQx();


    /**
     * @param map
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询FDCQ 的权利人
     */
    List<BdcQlrDO> queryFdcqBdcQlr(Map map);


    /**
     * @param paramMap
     * @return java.util.List<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询是否有正在办理的业务
     * 产权类 AJZT不为2
     * 限制权利 AJZT不为2 且未登簿（QSZT == 0）
     */
    List<Map> queryBdcdyhSfzb(Map<String, String> paramMap);


    /**
     * @param grdacxRequestBody
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcFdcqDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询房地产权实体
     */
    List<BdcFdcqDO> queryBdcFdcq(GrdacxRequestBody grdacxRequestBody);

    /**
     * @param grdacxRequestBody
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcYgDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询预告实体
     */
    List<BdcYgDO> queryBdcYg(GrdacxRequestBody grdacxRequestBody);

    /**
     * 查询土地所有权实体
     *
     * @param grdacxRequestBody
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcTdsyqDO>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 查询土地所有权实体
     */
    List<BdcTdsyqDO> queryBdcTdsyq(GrdacxRequestBody grdacxRequestBody);

    /**
     * 查询建设用地使用权实体
     *
     * @param grdacxRequestBody
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 查询建设用地使用权实体
     */
    List<BdcJsydsyqDO> queryBdcJsydsyq(GrdacxRequestBody grdacxRequestBody);

    /**
     * 根据xmid获取证书附件相关内容
     *
     * @param map
     * @return
     */
    List<BdcZsDO> getZsbsxxByXmid(Map map);

    /**
     * 根据xmid获取是否发证
     *
     * @param map
     * @return
     */
    BdcCshFwkgSlDO getSffzflagByXmid(Map map);

    /**
     * @return 修改数量
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 批量更新上报日志BDC_JR_SJJL的数据
     */
    int batchUpdateAccessLogCgbs(Map<String, Object> map);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 市级更新
     * @date : 2022/11/10 15:55
     */
    int batchUpdateAccessLogCgbsCity(Map<String, Object> map);

    /**
     * @return 修改数量
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 批量更新上报日志BDC_GJ_SJJL的数据
     */
    int batchUpdateAccessLogCgbsNational(Map<String, Object> map);

    /**
     * 公积金查询fwxx
     *
     * @param requestData 请求参数
     * @return
     * @Date 2021/5/19
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<GjjFwxxResponseData> queryFwxx(GjjRequestData requestData);

    /**
     * 公积金查询czxx
     *
     * @param requestData 请求参数
     * @return
     * @Date 2021/5/19
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<GjjCzxxResponseData> queryCzxx(GjjRequestData requestData);

    /**
     * 查询电子证照下载
     *
     * @param dzzzXzQO
     * @return
     * @Date 2021/5/20
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<BdcZsDO> queryBdczsZzbs(DzzzXzQO dzzzXzQO);

    /**
     * @param paramMap 查询参数
     * @return 证书信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询不动产证书信息
     */
    List<BdcZsResponseDTO> listBdcZsxx(Map<String, Object> paramMap);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    List<BdcQlrDO> listBdcQlrByZsid(String zsid);

    /**
     * 插入BDC_JR_DBRZJL表数据，（用于添加xnjrbw字段，正常的插入请使用do对象插入）
     *
     * @param map
     * @return
     */
    int insertBdcJrDbrzjlWithXnjrbw(Map map);

    /**
     * 查询虚拟报文
     *
     * @param id
     * @return
     */
    String queryBdcJrDbrzjlXnjrbwWithXnjrbwById(String id);

    /**
     * 根据参数，查询权利人信息
     *
     * @param
     * @return
     * @Date 2021/6/10
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<BdcQlrDO> queryQlrList(Map paramMap);

    /**
     * 根据权利人证件号和权利人名称查询zzbs
     *
     * @param
     * @return
     * @Date 2021/6/10
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    List<BdcZsDO> queryZzbsByQlrmcAndZjh(YthQueryDzzzxxDTO paramMap);

    /**
     * 根据参数qlrxx,zl,bdcdyh,bdcqzh查询BdcQszmQO信息
     *
     * @param
     * @return
     * @Date 2021/6/25
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    List<BdcQszmQO> queryXmxxListByQlrAndZlAndBdcdyh(YctbZxcdRequest paramMap);

    /**
     * 根据参数bdcdyh,bdcqzh查询BdcXmDO信息
     *
     * @param
     * @return
     * @Date 2021/6/25
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    List<BdcXmDO> queryXmxxByBdcqzhAndBdcDyh(BdcQszmQO paramMap);

    /**
     * 根据参数bdcdyh,bdcqzh查询BdcXmDO信息
     *
     * @param
     * @return
     * @Date 2021/6/25
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    List<BdcXmDO> queryXmxxByBdcqzhAndBdcDyh_map(HashMap paramMap);

    /**
     * 根据参数bdcdyhList查询QltQlXzxzDO (bdc_zssd和bdc_dysd)信息
     *
     * @param
     * @return
     * @Date 2021/6/25
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    List<QltQlXzxzDO> queryXzxxForYthQltQlXzxzByBdcdyhList(List<String> bdcdyh);

    /**
     * 根据参数bdcdyh查询QltQlXzxzDO (bdc_zssd和bdc_dysd)信息
     *
     * @param
     * @return
     * @Date 2021/6/25
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    List<QltQlXzxzDO> queryXzxxForYthQltQlXzxzByBdcdyh(String bdcdyh);

    List<BdcFdcqDO> queryFdcqxx(Map map);

    List<BdcYgDO> queryYgQlxxForSjpt(PeCxDO map);

    /**
     * @param xmid
     * @return
     */
    List<YctbOldBusinessReqmap> queryReqmapForYctb(String xmid);

    /**
     * @param xmid
     * @return
     */
    List<YctbOldBusinessReqmap> queryReqmapForYctb_bf(String xmid);

    /**
     * @param xmid
     * @return
     */
    List<Map> queryQdjgForYctb(String xmid);

    /**
     * 查询单元信息-一窗通办
     *
     * @param
     * @return
     * @Date 2021/6/25
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    List<YctbGetHouseInfoResponse> queryBdcdyhxxForYctb(BdcQszmQO paramMap);

    String queryQjgldm(String xmid);

    /**
     * 统计jr表cgbs各个状态数量
     *
     * @param map
     * @return
     */
    List<BdcAccessCountResultDTO> countErrorJrxx(Map map);

    /**
     * 查询宗海基本信息 by gzlslid
     *
     * @param gzlslid
     * @return
     */
    List<ZhJbxxDTO> queryZhjbxxByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * queryZhYhzkByGzlslid by gzlslid
     *
     * @param gzlslid
     * @return
     */
    List<ZhYhzkDTO> queryZhYhzkByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * queryZhYhzbByGzlslid by gzlslid
     *
     * @param gzlslid
     * @return
     */
    List<ZhYhzbDTO> queryZhYhzbByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * queryZhZhbhqkByGzlslid by gzlslid
     *
     * @param gzlslid
     * @return
     */
    List<ZhZhbhqkDTO> queryZhZhbhqkByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * queryZhHysyqByGzlslid by gzlslid
     *
     * @param gzlslid
     * @return
     */
    List<ZhHysyqxxDTO> queryZhHysyqByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * queryZhQlrByGzlslid by gzlslid
     *
     * @param gzlslid
     * @return
     */
    List<ZhQlrDTO> queryZhQlrByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * queryZhDydjxxByGzlslid by gzlslid
     *
     * @param gzlslid
     * @return
     */
    List<ZhDydjxxDTO> queryZhDydjxxByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * queryZhCfdjxxByGzlslid by gzlslid
     *
     * @param gzlslid
     * @return
     */
    List<ZhCfdjxxDTO> queryZhCfdjxxByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * queryZhYydjxxByGzlslid by gzlslid
     *
     * @param gzlslid
     * @return
     */
    List<ZhYydjxxDTO> queryZhYydjxxByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * queryZhZxdjxxByGzlslid by gzlslid
     *
     * @param gzlslid
     * @return
     */
    List<ZhZxdjxxDTO> queryZhZxdjxxByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * queryZhsmjByGzlslid by gzlslid
     *
     * @param gzlslid
     * @return
     */
    List<ZhSmjxxDTO> queryZhsmjByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * 不动产银行地址参数表
     *
     * @param gzlslid
     * @return
     */
    List<BdcYhUrlCsDO> queryYhdz(@Param("yhmc") String yhmc, @Param("handlerType") String handlerType);

    /**
     * 根据条件查询抵押权
     *
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    List<BdcDyaqDTO> queryDyaqByBdcdyh(@Param("processInsId") String processInsId,
                                       @Param("qlrmc") List<String> qlrmc,
                                       @Param("qlrlb") Integer qlrlb,
                                       @Param("bdcdyh") List<String> bdcdyh,
                                       @Param("qszt") Integer qszt,
                                       @Param("ajzt") Integer ajzt);

    List<BdcFdcqDO> queryFdcqByBdcdys(@Param("bdcdyh") List<String> bdcdyh, @Param("qszt") Integer qszt);


    /**
     * 根据条件查询查封数据
     *
     * @param processInsId
     * @param qlrmc
     * @param qlrlb
     * @param bdcdyh
     * @param qszt
     * @param ajzt
     * @return
     */
    List<BdcCfDTO> queryCfByBdcdyh(@Param("processInsId") String processInsId,
                                   @Param("qlrmc") List<String> qlrmc,
                                   @Param("qlrlb") Integer qlrlb,
                                   @Param("bdcdyh") List<String> bdcdyh,
                                   @Param("qszt") Integer qszt,
                                   @Param("ajzt") Integer ajzt);

    /**
     * 根据条件查询预抵押权
     *
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    List<BdcYdyDTO> queryYdyByBdcdyh(@Param("qlrmc") List<String> qlrmc,
                                     @Param("qlrlb") Integer qlrlb,
                                     @Param("bdcdyh") List<String> bdcdyh);

    /**
     * @param bdcDjbQO 请求参数
     * @return list 证书
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 根据权利人的信息查询证书
     */
    List<BdcZsDO> getZsByQlr(BdcDjbQO bdcDjbQO);

    /**
     * @param xmidList xmid集合
     * @return List 登记簿信息--自然状况中的房屋
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 常州自助查询机查询登记簿信息--查询自然状况中的房屋
     */
    List<BdcDjbFwxxDTO> getFwxxByXmid(@Param("xmidList") List<String> xmidList);

    /**
     * @param zsid 证书id
     * @return List 登记簿的权利人
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 常州自助查询机查询登记簿信息--权属状态中的权利人
     */
    List<BdcDjbQlrxxDTO> getQlrByZsid(@Param("zsid") String zsid);

    /**
     * @param bdcdyhList 不动产单元号集合
     * @return List 登记簿信息--抵押权信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 常州自助查询机查询登记簿信息--抵押权信息
     */
    List<BdcDjbDyxxDTO> queryDyaqByDyh(@Param("bdcdyhList") List<String> bdcdyhList);

    /**
     * @param bdcdyhList 不动产单元号集合
     * @return List 登记簿信息--居住权信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 常州自助查询机查询登记簿信息--居住权信息
     */
    List<BdcDjbJzxxDTO> queryBdcJzqByDyh(@Param("bdcdyhList") List<String> bdcdyhList);

    /**
     * @param bdcdyhList 不动产单元号集合
     * @return List 登记簿信息--限制权信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 常州自助查询机查询登记簿信息--限制权信息
     */
    List<BdcDjbXzxxDTO> queryBdcCf(@Param("bdcdyhList") List<String> bdcdyhList);

    /**
     * @param bdcdyhList 不动产单元号集合
     * @param zsid       证书id
     * @return List 登记簿信息--征收冻结信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 常州自助查询机查询登记簿信息--征收冻结信息
     */
    List<BdcDjbZsdjxxDTO> queryBdcZsdj(@Param("zsid") String zsid, @Param("bdcdyhList") List<String> bdcdyhList);

    /**
     * @param xmid 项目id
     * @return String 附记
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 常州自助查询机查询登记簿信息--查询项目信息
     */
    String queryFjByXmid(@Param("xmid") String xmid);

    /**
     * @param xmidList 项目id集合
     * @return String 附记
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 常州自助查询机查询登记簿信息--查询房屋土地信息
     */
    List<BdcDjbTdxxDTO> getFwTdxxByXmid(@Param("xmidList") List<String> xmidList);

    /**
     * @param xmidList 项目id集合
     * @return String 附记
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 常州自助查询机查询登记簿信息--查询土地信息
     */
    List<BdcDjbTdxxDTO> getTdxxByXmid(@Param("xmidList") List<String> xmidList);


    /**
     * 登记类型统计
     *
     * @param slksrq
     * @param sljsrq
     * @param slly
     * @param xzqdm
     * @return
     */
    List<DjlxtjResponseData> countDjlxtj(
            @Param("slksrq") String slksrq,
            @Param("sljsrq") String sljsrq,
            @Param("slly") String slly,
            @Param("xzqdm") String xzqdm
    );

    /**
     * 办理环节统计
     *
     * @param slksrq
     * @param sljsrq
     * @param slly
     * @param xzqdm
     * @return
     */
    List<BlhjtjResponseData> countBlhjtj(
            @Param("slksrq") String slksrq,
            @Param("sljsrq") String sljsrq,
            @Param("slly") String slly,
            @Param("xzqdm") String xzqdm
    );


    /**
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @param xmid 项目id
     * @return {List} 建设用地使用权、宅基地使用权
     * @description 根据XMID查询BdcJsydsyqDO
     */
    List<BdcJsydsyqDO> listJsydsyqDOByXmid(String xmid);


    /**
     * 根据登记小类查询申报登记反馈
     *
     * @param djxl 登记小类
     * @param qjgldm 权籍管理代码
     * @return 申报登记反馈
     */
    List<BdcDjxlSbdjfkCsgxDO> querySbdjfkList(@Param("djxl") String djxl,@Param("qjgldm") String qjgldm);

    /**
     * 根据登记时间查询项目表
     *
     * @return 项目表集合
     */
    List<BdcXmDO> listBdcXmByslsj(BdcXmQO xmQO);

    /**
     * @param map
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 登记字典代码和国标字典代码对照
     */
    List<BdcExchangeZddz> queryBdcExchangeZddzList(Map map);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入xsd校验的对照表数据
     * @date : 2022/11/23 15:15
     */
    List<BdcJrXsdjyDO> listXsdJyDz();


    /**
     * 获取权利人业务信息
     *
     * @param map
     * @return
     */
    List<ZttGyQlrDO> queryAllZttGyQlrList(Map map);


    /**
     * @description 获取房屋房源信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/12/22 18:26
     * @param djxxRequestDTO
     * @return List<BdcFwfyxxDTO>
     */
    List<BdcXscqxxDTO> listFwfyxx(DjxxRequestDTO djxxRequestDTO);

}

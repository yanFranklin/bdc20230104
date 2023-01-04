package cn.gtmap.realestate.exchange.service.inf;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.PageDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.exchange.core.dto.common.*;
import cn.gtmap.realestate.exchange.core.dto.ycsl.queryxzqlbybdcdyh.response.YcslQueryXzqlByBdcdyhSdxx;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-08
 * @description  比较通用的一些服务
 */
public interface CommonService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ysfwbm
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @description
     */
    BdcdyResponseDTO queryHsBdcdyByYsfwbm(String ysfwbm);

    /**
     * 根据XMID 权利人类别 查询权利人列表
     * @param xmid
     * @param qlrlb
     * @return
     */
    List<BdcQlrDO> listBdcQlrByXmid(String xmid,String qlrlb);

    /**
     * 根据权利人名称、证件号、权利人类别 查询权利人列表
     * @param qlrmc
     * @param qlrzjh
     * @param qlrlb
     * @return
     */
    List<BdcQlrDO> listBdcQlrByQlrAndZjh(String qlrmc, String qlrzjh, String qlrlb);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param qlrmc
     * @param qlrzjhs
     * @param qlrlb
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQlrDO>
     * @description 根据权利人名称、证件号(多个)、权利人类别 查询权利人列表
     */
    List<BdcQlrDO> listBdcQlrByQlrAndZjhArr(String qlrmc,List<String> qlrzjhs,String qlrlb);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xmid
     * @return cn.gtmap.realestate.common.core.domain.BdcFdcqDO
     * @description 根据XMID查询 房地产权 现势权利
     */
    BdcFdcqDO getFdcqByXmid(String xmid);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xmid
     * @return cn.gtmap.realestate.common.core.domain.BdcFdcqDO
     * @description 根据XMID查询 房地产权 所有权属状态
     */
    BdcFdcqDO getFdcqByXmidWithNoQszt(String xmid);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xmid
     * @return cn.gtmap.realestate.common.core.domain.BdcXmDO
     * @description 根据XMID 查询 项目
     */
    BdcXmDO getBdcXmByXmid(String xmid);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param slbh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @description 根据SLBH 查询项目列表
     */
    List<BdcXmDO> listBdcXmBySlbh(String slbh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param spxtywh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @description 根据审批系统业务号查询项目列表
     */
    List<BdcXmDO> listBdcXmBySpxtywh(String spxtywh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQl>
     * @description 根据BDCDYH查询现势状态的权利
     */
    List<BdcQl> listXsQlByBdcdyh(String bdcdyh,String qllx);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQl>
     * @description 根据BDCDYH查询现势状态的权利
     */
    List<BdcQl> listQlByBdcdyh(String bdcdyh,String qllx,List<Integer> qsztList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyhList 批量单元号
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQl>
     * @description 根据批量BDCDYH查询现势权利（主要针对大数据量分批查询）
     */
    <T> List<T> listQlByBdcdyhs(List<String> bdcdyhList, T qlxx);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.FdcqQlWithXmQlrDTO>
     * @description   根据BDCDYH查询 FDCQ 和项目
     */
    List<FdcqQlWithXmQlrDTO> listFdcqByBdcdyh(QueryQlRequestDTO requestDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.DyQlWithXmQlrDTO>
     * @description 根据BDCDYH查询抵押权 和项目
     */
    List<DyQlWithXmQlrDTO> listDyaqByBdcdyh(QueryQlRequestDTO requestDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.CfQlWithXmQlrDTO>
     * @description
     */
    List<CfQlWithXmQlrDTO> listCfByBdcdyh(QueryQlRequestDTO requestDTO);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.YyQlWithXmQlrDTO>
     * @description
     */
    List<YyQlWithXmQlrDTO> listYyByBdcdyh(QueryQlRequestDTO requestDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.YgQlWithXmQlrDTO>
     * @description 根据xmid查询预告权利
     */
    List<YgQlWithXmQlrDTO> listYgByBdcdyh(QueryQlRequestDTO requestDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.YgQlWithXmQlrDTO>
     * @description 根据xmid查询预告权利
     */
    List<JzqQlWithXmQlrDTO> listJzqByBdcdyh(QueryQlRequestDTO requestDTO);

    /**
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.YgQlWithXmQlrDTO>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据xmid查询预抵押权利
     */
    List<YgQlWithXmQlrDTO> listYgDyaqByBdcdyh(QueryQlRequestDTO requestDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcXmDO
     * @return java.lang.String
     * @description 根据XM实体 工作流节点名称
     */
    String queryJdmcByBdcXm(BdcXmDO bdcXmDO);

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @return java.lang.String
     * @description
     */
    JSONObject queryProcessStatusInfo(JSONObject jsonObject);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param bdcXmDO
     * @return java.lang.String
     * @description 根据XM实体 办件状态
     */
    String queryBjztByBdcXm(BdcXmDO bdcXmDO);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param beanName   token类型
     * @param requestObject   请求参数
     * @return java.lang.String
     * @description 获取token
     */
    String getToken(String beanName, Object requestObject);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageDTO
     * @return Pageable
     * @description
     */
    Pageable getPageable(PageDTO pageDTO);

    /**
     * 获取不动产单元锁定数据
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param bdcdyh
     * @return java.util.List<BdcDysdDO>
     * @description
     */
    List<BdcDysdDO> listDysdByBdcdyh(@NotBlank(message = "不动产单元号不能为空") String bdcdyh,Integer sdzt);

    /**
     * 获取不动产单元锁定数据(不包含解锁数据；包含单元锁定、证书锁定数据)
     *
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @return java.util.List 锁定数据
     */
    List<YcslQueryXzqlByBdcdyhSdxx> listSdxxByBdcdyh(@NotBlank(message = "不动产单元号不能为空") String bdcdyh);

    /**
     * 获取证书锁定数据
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param xmid
     * @return java.util.List<BdcZssdDO>
     * @description
     */
    List<BdcZssdDO> listZssdByXmid(@NotBlank(message = "项目ID不能为空") String xmid,Integer sdzt);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param gzlslid
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @description 根据工作流实例ID 查询项目列表
     */
    List<BdcXmDO> listBdcXmByGzlslid(String gzlslid);

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param gzlslid
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @description 根据工作流实例ID 查询项目列表供南通通知交易系统调用，过滤了重复的qlxx
     */
    List<BdcXmDO> listBdcXmByGzlslidForNtTofcjy(String gzlslid);

    /**
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcQlrQO
     * @return String
     * @description 根据工作流实例ID 查询抵押和预抵押的权利人名称
     */
    List<String> listDyaAndYdyQlrmc(BdcQlrQO bdcQlrQO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zdkey
     * @param dm
     * @return java.lang.String
     * @description 根据 字典代码获取字典名称
     */
    String getBdcZdMcFromDm(String zdkey,String dm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zdkey
     * @param dm
     * @return java.lang.String
     * @description 根据 字典代码获取字典名称
     */
    String getBdcSlZdMcFromDm(String zdkey,String dm);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xmid
     * @param qlrlb
     * @return cn.gtmap.realestate.common.core.domain.BdcQlrDO
     * @description 根据XMID查询权利人信息 多个权利人 用逗号隔开拼接成一个QLR实体返回
     */
    BdcQlrDO wmcatQlrxx(String xmid,String qlrlb);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xmid
     * @param sqrlb
     * @return cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO
     * @description 根据XMID查询申请人信息 多个申请人 用逗号隔开拼接成一个SQR实体返回
     */
    BdcSlSqrDO wmcatSqrxx(String xmid,String sqrlb);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zdTable
     * @param dsfFlag
     * @param dsfzd
     * @return java.lang.String
     * @description 第三方字典 转成 BDC 代码
     */
    String dsfZdToBdcDm(String zdTable,String dsfFlag,String dsfzd);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zdTable
     * @param dsfFlag
     * @param bdcdm
     * @return java.lang.String
     * @description 转成 第三方字典 代码
     */
    String bdcDmToDsfZd(String zdTable, String dsfFlag, String bdcdm);


    /**
     * @param zdTable
     * @param dsfFlag
     * @param bdcdm
     * @return java.lang.String
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 使用第三方字典对照表对照，有值返回，无值返回原值
     */
    String bdcDmToDsfZdNvl(String zdTable, String dsfFlag, String bdcdm);

    /**
     * @param xmid
     * @return cn.gtmap.realestate.common.core.domain.BdcZsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据XMID 查询 原证书信息
     */
    BdcZsDO queryYzsByXmid(String xmid);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return cn.gtmap.realestate.common.core.domain.BdcFdcqDO
     * @description
     */
    BdcFdcqDO queryYFdcqByXmid(String xmid);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param
     * @return BdcQl
     * @description
     */
    BdcQl queryYBdcqlByXmid(String xmid);

    /**
     * @param xmid
     * @return ist<String>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 查询注销原权利的上一手项目idlist
     */
    List<String> queryYZxxqlXmidListByXmid(String xmid);

    /**
     * @param xmid
     * @param zxyql
     * @param wlxm
     * @return List<String>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 查询原权利的上一手项目idlist 如果传wlxm和zxyql则进行过滤
     */
    List<String> queryYxmidListByXmidAndWlxmAndZxyql(String xmid, Integer zxyql, Integer wlxm);

    /**
     * @param xmids
     * @return List<String>
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 查询项目关联的项目信息并根据xmid bdcdyh qllx去重
     */
    Set<BdcXmForZxAccessDTO> queryBdcXmForZxAccessListByXmidsAndWlxmAndZxyql(List<String> xmids);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param
     * @return BdcQl
     * @description
     */
    BdcQl queryYBdcqlByXmid_BdcSlXm(String xmid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    List<BdcXmDO> listBdcXmByZfxzspbh(String zfxzspbh);

    /**
     * @param bdcxm
     * @return
     * @Date 2021/7/21
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    String queryZslxByxmid(BdcXmDO xmDO);

    /**
     * xmid查证书附记
     *
     * @param bdcxm
     * @return
     * @Date 2021/7/21
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    String queryZsfjByxmid(BdcXmDO xmDO);

    /**
     * @param xmidList 批量单元号
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQl>
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据批量xmid查询项目（主要针对大数据量分批查询）
     */
    List<BdcXmDO> listBdcXmByXmids(List<String> xmidList);

    /**
     * 住址核验接口
     *
     * @param cqzh
     * @param qlrmc
     * @return
     */
    BdcXmCqyzDTO listQlrByCqzhAndQlr(String cqzh, String qlrmc);

    /**
     * 处理外网申请返回的token结构
     *
     * @param 外网申请token
     * @return
     * @Date 2022/9/1
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    String wwsqToken(Object object);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param beanName   token类型
     * @param requestObject   请求参数
     * @return java.lang.String
     * @description 获取当前token
     */
    String getCurrentToken(String beanName, Object requestObject);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">caolu</a>
     * @param jsonObject   请求参数
     * @return JSONObject
     * @description 根据slbh查询办件状态
     */
    JSONObject getWwsqztBySlbh(JSONObject jsonObject);
}



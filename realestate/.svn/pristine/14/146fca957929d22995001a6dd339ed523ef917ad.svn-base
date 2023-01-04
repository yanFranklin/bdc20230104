package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.HefeiDzzzZzDataDTO;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.moke.MokeZzdzjPlszExtendDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZsTjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZsxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZszmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.hefei.moke.MokeHxyzhRequestDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDzzzZzQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcZsTjQO;
import cn.gtmap.realestate.inquiry.core.dto.BdcFdcqGhytDTO;
import cn.gtmap.realestate.inquiry.core.dto.BdcQlfjDTO;
import cn.gtmap.realestate.inquiry.core.entity.YhLqrDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/05/16
 * @description 不动产证书证明查询Mapper
 */
public interface BdcZszmMapper {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZszmDTOList 选中的记录数据
     * @return {List} 不动产单元号集合
     * @description 查询要打印抵押查封证明的产权信息对应的不动产单元号
     */
    Set<String> listBdcdyhOfDyacfzm(List<BdcZszmDTO> bdcZszmDTOList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmidList 项目ID集合参数
     * @return {List} 房地产权信息
     * @description 根据XMID查询指定权利信息
     */
    List<BdcZhcxDTO> listQlxxDTO(List<String> xmidList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmidList 项目ID集合参数
     * @return {List} 权利人信息
     * @description 根据XMID查询指定权利人信息
     */
    List<BdcZhcxDTO> listQlrDTO(List<String> xmidList);

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param xmidList 项目ID集合参数
     * @return {List} xm
     * @description 根据XMID查询指定字段的项目信息
     */
    List<BdcXmDO> listBfBdcxm(List<String> xmidList);

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @param xmidList 项目ID集合参数
     * @return {List} xm
     * @description 根据XMID查询指定土地使用权面积
     */
    List<BdcJsydsyqDO> listSyqmjBdcxm(List<String> xmidList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zsid 证书ID
     * @return {List} 不动产单元号集合
     * @description 获取证书证明关联的不动产单元号集合
     */
    List<String> listBdcZszmBdcdyh(@Param("zsid") String zsid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmidList 项目ID
     * @return {List} 证书信息集合
     * @description 获取项目关联的证书信息
     */
    List<BdcZsxxDTO> listBdcZsDO(List<String> xmidList);

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @return {List} 证书信息集合
     * @description 获取项目关联的证书信息
     */
    List<BdcZsxxDTO> listBdcZsDOByZsids(List<String> zsids);

    /**
     * 判断fdcq表是否有值，以此区别是一证多房还是在建工程抵押
     *
     * @param gzlslid
     * @return num
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Integer getFdcqCount(String gzlslid);

    /**
     * 判断抵押权表是否值》1
     *
     * @param gzlslid
     * @return num
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Integer getDyaqCount(String gzlslid);

    /**
     * 获得证书证明统计用于Excel导出的数据
     *
     * @param bdcZsTjQO
     * @return List<BdcZsTjDTO>
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    List<BdcZsTjDTO> getZszmCountExcel(BdcZsTjQO bdcZsTjQO);

     /**
      * 根据登记机构统计证书证明数量
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
      * @param zszmCountJson
      * @return list
      */
    List<BdcZsTjDTO> getZszmCountList(BdcZsTjQO zszmCountJson);

    /**
     * 根据权利人名称查询权利人证件号为空或证件号不为18位与15位的数量
     *
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param qlrmc 权利人名称数据
     * @return 数量
     */
    Integer getQlrZjhNullCount(List<String> qlrmc);

    /**
     * 【31556】【海门】多个物一个单元号的规划用途展示需求
     * 根据slbh查询项目内多幢规划用途
     *
     * @param slbh 受理编号
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    List<String> getFdcqXmGhyt(String slbh);

    List<HefeiDzzzZzDataDTO> listWzzZsDataByPage(BdcDzzzZzQO bdcDzzzZzQO);

    /**
     * @param hxyzhRequestDTO @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 合肥摩科查询打证信息接口
     */
    List<MokeZzdzjPlszExtendDTO> queryMokeDzxx(MokeHxyzhRequestDTO mokeHxyzhRequestDTO);

    /**
     * @param yhLqrDO
     * @return YhLqrDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据领取人姓名和证件号查银行
     */
    YhLqrDO queryYhByLqr(YhLqrDO yhLqrDO);

    /**
     * 查询项目对应的项目内多幢规划用途
     * @param xmidList 项目信息
     * @return 项目对应的规划用途信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcFdcqGhytDTO> listFdcqXmGhyt(List<String> xmidList);

    /**
     * 查询项目对应的权利附记信息
     * @param xmidList 项目信息
     * @return 权利附记信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcQlfjDTO> listBdcQlfj(List<String> xmidList);
}

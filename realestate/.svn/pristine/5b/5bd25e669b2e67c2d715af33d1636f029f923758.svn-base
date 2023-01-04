package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSzgzlTjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZsTjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZszmDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZszmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcSzgzlTjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcZsTjQO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZsTjVO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZsmxTjVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/5/15
 * @description 查询子系统：不动产证书证明查询逻辑接口定义
 */
public interface BdcZszmCxService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable 分页对象
     * @param bdcZszmQO 查询条件
     * @return {Page} 证书证明查询分页参数
     * @description 证书证明分页查询
     */
    Page<BdcZszmDTO> listBdcZszm(Pageable pageable, BdcZszmQO bdcZszmQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmidList 项目ID集合参数
     * @return {List} 综合查询附加展示信息
     * @description 根据XMID查询综合查询附加显示信息
     */
    List<BdcZhcxDTO> listZhcxFjxx(List<String> xmidList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable      分页对象
     * @param bdcZszmQO 查询条件
     * @return {Page} 项目信息分页数据
     * @description 根据证书ID获取关联的项目信息
     */
    Page<BdcXmDO> listBdcZszmXmxx(Pageable pageable, BdcZszmQO bdcZszmQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zsid 证书ID
     * @return {List} 不动产单元号集合
     * @description 获取证书证明关联的不动产单元号集合
     */
    List<String> listBdcZszmBdcdyh(String zsid);

    /**
     * @param bdcZsTjQO
     * @return {List} 证书统计结果
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    List<BdcZsTjVO> listBdcZsTj(BdcZsTjQO bdcZsTjQO);

    /**
     * @param bdcZsTjQO
     * @return {List} 证书明细统计
     * @author <a href ="mailto:wutao2@gtmap.cn">wutao</a>
     * @description
     */
    List<BdcZsmxTjVO> listBdcZsmxTj(Pageable pageable, BdcZsTjQO bdcZsTjQO);

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [bdcGzYzQO] 验证查询参数
     * @return: List<Map<String,Object>> 验证结果信息   ccx 2019-10-03 验证结果增加查看项目
     * @description 综合出具房产证明与房产档案时，验证该不动产单元号是否正在办理其他登记。
     */
    List<Map<String,Object>> checkBdcdyhGz(BdcGzYzQO bdcGzYzQO);

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
     * 根据登记机构统计证书证明数量
     *
     * @param pageable
     * @param parseObject zszmCountJson
     * @return BdcZsTjDTO BdcZsTjDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<BdcZsTjDTO> getZszmCount(Pageable pageable, BdcZsTjQO parseObject);

    /**
     * 根据登记机构统计证书证明数量
     *
     * @param parseObject zszmCountJson
     * @return BdcZsTjDTO BdcZsTjDTO
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    List<BdcZsTjDTO> getZszmCountExcel(BdcZsTjQO parseObject);

    /**
     * 根据登记机构统计证书证明数量
     *
     * @param zszmCountJson zszmCountJson
     * @return BdcZsTjDTO BdcZsTjDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<BdcZsTjDTO> getZszmCountList(BdcZsTjQO zszmCountJson);

    /**
     * 根据权利人名称查询权利人证件号为空或证件号不为18位与15位的数量
     *
     * @param  qlrmc 权利人名称
     * @return int   数量
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Integer getQlrZjhNullCount(List<String> qlrmc);

    /**
     * 获取缮证工作量统计
     *
     * @param  bdcSzgzlTjQO 缮证工作量统计查询QO
     * @return List<BdcSzgzlTjDTO> 缮证工作量统计list
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    List<BdcSzgzlTjDTO> getSzgzl(BdcSzgzlTjQO bdcSzgzlTjQO);

    /**
     * 验证权利人证件号
     * @param qlrmc
     * @return
     */
    Boolean checkQlczjh(String qlrmc);
}

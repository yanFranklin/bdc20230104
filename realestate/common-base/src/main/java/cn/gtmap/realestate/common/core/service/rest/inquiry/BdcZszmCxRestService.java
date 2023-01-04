package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSzgzlTjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZsTjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZszmDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcSzgzlTjQO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZsTjVO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZsmxTjVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/5/15
 * @description 查询子系统：不动产证书证明查询服务接口定义
 */
public interface BdcZszmCxRestService {
    /**
     * @param pageable      分页对象
     * @param zszmParamJson 查询条件
     * @return {Page} 证书证明查询分页数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 证书证明查询
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zszm")
    Page<BdcZszmDTO> listBdcZszm(Pageable pageable,
                                 @RequestParam(name = "zszmParamJson", required = false) String zszmParamJson);

    /**
     * @param xmidList 项目ID集合参数
     * @return {List} 综合查询附加展示信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据XMID查询综合查询附加显示信息
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zszm/fjxx")
    List<BdcZhcxDTO> listZhcxFjxx(@RequestBody List<String> xmidList);

    /**
     * @param zsid 证书ID
     * @return {List} 不动产单元号集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取证书证明关联的不动产单元号集合
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zszm/{zsid}/bdcdyh")
    List<String> listBdcZszmBdcdyh(@PathVariable("zsid") String zsid);

    /**
     * @param pageable      分页对象
     * @param zszmParamJson 查询条件
     * @return {Page} 项目信息分页数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据证书ID获取关联的项目信息
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zszm/xmxx")
    Page<BdcXmDO> listBdcZszmXmxx(Pageable pageable,
                                  @RequestParam(name = "zszmParamJson", required = false) String zszmParamJson);

    /**
     * @param zstjParamJson
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 证书证明统计
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zs/tj")
    List<BdcZsTjVO> listBdcZsTj(@RequestParam(name = "zstjParamJson", required = false) String zstjParamJson);

    /**
     * @param zstjParamJson
     * @return
     * @author <a href ="mailto:wutao2@gtmap.cn">wutao</a>
     * @description 证书打印明细统计
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zs/mx")
    List<BdcZsmxTjVO> listBdcZsmxTj(Pageable pageable, @RequestParam(name = "zstjParamJson", required = false) String zstjParamJson);

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [bdcGzYzQO] 规则验证查询参数
     * @return: List<String> 验证结果
     * @description
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zhcx/gzyz")
    List<Map<String, Object>> checkBdcdyhGz(@RequestBody BdcGzYzQO bdcGzYzQO);

    /**
     * 判断fdcq表是否有值，以此区别是一证多房还是在建工程抵押
     *
     * @param gzlslid
     * @return num
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zfxx/getfdcqcount")
    Integer getFdcqCount(@RequestBody String gzlslid);

    /**
     * 判断抵押权表是否有值，以此区别是一证多房还是在建工程抵押
     *
     * @param gzlslid
     * @return num
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zfxx/getDyaqcount")
    Integer getDyaqCount(@RequestBody String gzlslid);

    /**
     * 根据登记机构统计证书证明数量 (可用getZszmCountList方法，直接返回list)
     *
     * @param zszmCountJson zszmCountJson
     * @return BdcZsTjDTO BdcZsTjDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zszmCount")
    @Deprecated
    Page<BdcZsTjDTO> getZszmCount(Pageable pageable,
                                  @RequestParam(name = "zszmCountJson", required = false) String zszmCountJson);

    /**
     * 根据登记机构统计证书证明数量
     *
     * @param zszmCountJson zszmCountJson
     * @return BdcZsTjDTO BdcZsTjDTO
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zszmCount/excel")
    List<BdcZsTjDTO> getZszmCountExcel(@RequestParam(name = "zszmCountJson") String zszmCountJson);

    /**
     * 根据登记机构统计证书证明数量
     *
     * @param zszmCountJson zszmCountJson
     * @return BdcZsTjDTO BdcZsTjDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/realestate-inquiry/rest/v1.1/zszmCountList")
    List<BdcZsTjDTO> getZszmCountList(@RequestParam(name = "zszmCountJson", required = false) String zszmCountJson);

    /**
     * 根据权利人名称查询权利人证件号为空或证件号不为18位与15位的数量
     *
     * @param  qlrmc 权利人名称
     * @return int   数量
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zszm/qlrzjh/count")
    Integer getQlrZjhNullCount(@RequestBody  List<String> qlrmc);

    /**
    * @description 获取缮证工作量统计
    * @param
    * @return List<BdcSzgzlTjDTO> 缮证工作量统计list
    * @author  <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
    **/
    @PostMapping("/realestate-inquiry/rest/v1.0/zszm/getSzgzl")
    List<BdcSzgzlTjDTO> getSzgzl(@RequestBody BdcSzgzlTjQO bdcSzgzlTjQO);
}

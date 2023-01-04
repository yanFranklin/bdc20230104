package cn.gtmap.realestate.common.core.service.rest.certificate;

import cn.gtmap.realestate.common.core.domain.BdcJjdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmYjdGxDO;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcJjdXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcAjxxDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdXmQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 交接单服务
 *
 * @author <a href="mailto:lixin1@gtmap.cn>lixin</a>"
 * @version 1.0, 2019/8/27
 */
public interface BdcJjdRestService {

    /**
     * 生成并保存移交单信息
     *
     * @param bdcJjdQO 交接单查询QO
     * @return {BdcJjdDO}
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/jjd/jjdxx")
    BdcJjdDO generateAndSaveJjdxx(@RequestBody BdcJjdQO bdcJjdQO);

    /**
     * 生成移交单编号
     *
     * @param bdcJjdQO 交接单查询对象
     * @return {BdcJjdDO} 移交单编号
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/jjd/jjdh")
    BdcJjdDO generateJjdBh(@RequestBody BdcJjdQO bdcJjdQO);

    /**
     * 获取移交单的打印xml
     *
     * @param jjdid     移交单ID
     * @param bdcUrlDTO 地址对象
     * @return 打印xml字符串
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/jjd/print/{jjdid}/xml")
    String jjdPrintXml(@PathVariable(name = "jjdid") String jjdid, @RequestBody BdcUrlDTO bdcUrlDTO);

    /**
     * 分页查询全部交接单内容
     *
     * @param pageable 分页对象
     * @param bdcJjdQO 移交单查询对象字符串
     * @return {Page} 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/jjd/page/all")
    Page<BdcJjdDO> listAllBdcJjdByPage(Pageable pageable, @RequestParam(name = "bdcJjdQO", required = false) String bdcJjdQO);

    /**
     * 分页查询交接单内容
     *
     * @param pageable 分页对象
     * @param bdcJjdQO 移交单查询对象字符串
     * @return {Page} 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/jjd/page")
    Page<BdcJjdDO> listBdcJjdByPage(Pageable pageable, @RequestParam(name = "bdcJjdQO", required = false) String bdcJjdQO);

    /**
     * 分页查询案卷内容
     *
     * @param pageable 分页对象
     * @param bdcJjdQO 移交单查询对象字符串
     * @return {Page} 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/jjd/ajxx")
    Page<BdcAjxxDTO> listBdcAjxxByPage (Pageable pageable, @RequestParam(name = "bdcJjdQO") String bdcJjdQO);

    /**
     * 查询案卷内容
     *
     * @param bdcJjdQO 移交单查询对象字符串
     * @return {List} 查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/jjd/ajxx/all")
    List<BdcAjxxDTO> listBdcAjxx(@RequestParam(name = "bdcJjdQO") String bdcJjdQO);
    /**
     * 分页查询交接单项目信息
     *
     * @param pageable 分页对象
     * @param jjdid    交接单号
     * @return {Page} 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/jjd/xm")
    Page<BdcJjdXmDTO> listBdcJjdxmByPage(Pageable pageable, @RequestParam(name = "jjdid") String jjdid);

    /**
     * 删除交接单项目关系
     *
     * @param gzlslid gzlslid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @DeleteMapping(value = "/realestate-certificate/rest/v1.0/jjd/xm")
    void delJjdXmGx(@RequestParam(name = "gzlslid") String gzlslid);

    /**
     * 删除交接单
     *
     * @param jjdid 交接单ID
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @DeleteMapping(value = "/realestate-certificate/rest/v1.0/jjd")
    void delJjd(@RequestParam(name = "jjdid") String jjdid);

    /**
     * 转发交接单
     *
     * @param bdcJjdDO 交接单对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/jjd/forward")
    BdcJjdDO forwardJjd(@RequestBody BdcJjdDO bdcJjdDO);

    /**
     * 确认接收交接单
     *
     * @param jjdid 交接单 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/jjd/accept")
    Boolean acceptJjd(@RequestParam("jjdid") String jjdid);

    /**
     * 退回交接单
     *
     * @param jjdid 交接单id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/jjd/back")
    Boolean backJjd(@RequestParam("jjdid") String jjdid);

    /**
     * 查询在一段时间内指定受理人的特定流程
     *
     * @param bdcJjdXmQO 交接单项目查询对象
     * @return {BdcJjdDTO} 不动产交接单 DTO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/jjd/jjdxm")
    List<BdcXmDO> queryJjdXm(@RequestBody BdcJjdXmQO bdcJjdXmQO);

    @GetMapping(value = "/realestate-certificate/rest/v1.0/jjd/jjdgx/{jjdid}")
    List<BdcXmYjdGxDO> queryJjdGx(@PathVariable(value = "jjdid") String jjdid);

    @PostMapping(value = "/realestate-certificate/rest/v1.0/jjd")
    List<BdcJjdDO> listBdcJjd(@RequestBody BdcJjdQO bdcJjdQO);

    /**
     * 判断是否交接单可以创建
     *
     * @param gzlslid gzlslid
     * @return {Integer} 0 表示可以创建， 1 不能创建 当前用户已经用此gzlslid创建过交接单，不能再创建
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/jjd/iscreate")
    Integer checkIsCreat(@RequestParam("gzlslid") String gzlslid);

    /**
     * 批量转发交接单
     * @param bdcJjdDOList 交接单对象集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/realestate-certificate/rest/v1.0/jjd/forward/all")
    void forwardJjd(@RequestBody List<BdcJjdDO> bdcJjdDOList);

    /**
     * 生成南通交接单批量打印xml
     * @param key 打印参数缓存Redis Key
     * @return {String} 移交单打印xml
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/jjd/batch/{key}/xml")
    String jjdBatchPrintXml(@PathVariable(value = "key")String key);

    /**
     * 根据工作流实例ID 批量查询交接单关系信息
     * <p>BDC_JJD 与 BDC_XM_YJD_GX 关联查询， gzlslid为BDC_JJD.GZLSLID</p>
     * @param gzlslidList 工作流实例ID集合
     * @return 交接单关系集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/jjd/gx/list")
    List<BdcXmYjdGxDO> queryJjdGxByGzlslidList(@RequestBody List<String> gzlslidList);
}

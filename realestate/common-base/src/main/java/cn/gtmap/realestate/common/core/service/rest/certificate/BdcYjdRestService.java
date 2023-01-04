package cn.gtmap.realestate.common.core.service.rest.certificate;

import cn.gtmap.realestate.common.core.domain.BdcXmYjdGxDO;
import cn.gtmap.realestate.common.core.domain.BdcYjdDO;
import cn.gtmap.realestate.common.core.domain.BdcYjdTaskGxDO;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcHaimenYhYjdDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXmYjdDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYjdDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYjdGdxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.yjd.BdcYjdXmFilesDTO;
import cn.gtmap.realestate.common.core.dto.certificate.yjd.BdcYjdXmxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.yjd.ResultCode;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYjdQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/1
 * @description 移交单服务
 */
public interface BdcYjdRestService {
    /**
     * @return 移交单编号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成移交单编号
     * @param bdcYjdQO
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yjd/yjdbh", method = RequestMethod.POST)
    BdcYjdDO generateYjdBh(@RequestBody BdcYjdQO bdcYjdQO);

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return BdcYjdDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成并保存移交单信息
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yjd/yjdxx", method = RequestMethod.POST)
    BdcYjdDO generateAndSaveYjdxx(@RequestBody BdcYjdQO bdcYjdQO);

    /**
     * @param bdcYjdDO 移交单DO
     * @return 更新结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新移交单信息
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yjd", method = RequestMethod.PUT)
    int updateBdcYjd(@RequestBody BdcYjdDO bdcYjdDO);
    /**
     * @param bdcXmYjdGxDOList 项目移交单关系列表
     * @return 更新结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新项目移交单关系
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yjd/xmYjdGx", method = RequestMethod.PUT)
    int updateXmYjdGx(List<BdcXmYjdGxDO> bdcXmYjdGxDOList);

    /**
     * @param pageable 分页对象
     * @param bdcYjdQO 移交单查询对象
     * @return 分页查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询移交单移交移交单内容
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yjd/page", method = RequestMethod.POST)
    Page<BdcYjdDTO> listBdcYjdByPage(Pageable pageable, @RequestParam(name = "bdcYjdQO", required = false) String bdcYjdQO);

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return 查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 移交单包含项目信息查询
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yjd/list", method = RequestMethod.POST)
    List<BdcYjdDTO> listYjdAndXm(@RequestBody BdcYjdQO bdcYjdQO);

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return 查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询拥有移交单的项目
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yjd/xmYjdList", method = RequestMethod.POST)
    List<BdcXmYjdDTO> listXmOwnYjd(@RequestBody BdcYjdQO bdcYjdQO);

    /**
     * @param yjdid 移交单ID
     * @param dylx
     * @param bdcUrlDTO 地址对象
     * @return 打印xml字符串
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取移交单的打印xml
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yjd/print/{yjdid}/{dylx}/xml", method = RequestMethod.POST)
    String yjdPrintXml(@PathVariable(name = "yjdid") String yjdid, @PathVariable(name = "dylx") String dylx, @RequestBody BdcUrlDTO bdcUrlDTO);


    /**
     * @param pageable 分页对象
     * @return 分页查询结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 分页查询移交单核验归档信息
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yjd/checkYjd/yjdid", method = RequestMethod.POST)
    Page<BdcYjdGdxxDTO> listBdcYjdGdxx(Pageable pageable,@RequestParam(name = "bdcYjdQO", required = false) String bdcYjdQO);

    /**
     * @param gxid 关系id
     * @return 删除归档信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除归档信息
     */
    @DeleteMapping(value = "/realestate-certificate/rest/v1.0/yjd/delGdxx/gxid")
    void delGdxx(@RequestParam(name = "gxid", required = false) String gxid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable 分页信息
     * @param bdcYjdQO 移交单项目信息查询参数
     * @description  查询流程项目信息（用于海门新增移交单场景，根据流程移交）
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/yjd/xmxx")
    Page<BdcYjdXmxxDTO> listBdcYjdXmxx(Pageable pageable, @RequestParam(name = "bdcYjdQO", required = false) String bdcYjdQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYjdXmxxDTO 项目信息
     * @return wjy : 没有移交过； yyj : 已经移交过
     * @description  核查当前项目是否已经移交过
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/yjd/check")
    String checkState(@RequestBody BdcYjdXmxxDTO bdcYjdXmxxDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbhList 受理编号集合
     * @return 移交单信息
     * @description  （海门）生成移交单
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/yjd/new")
    BdcYjdDO generateBdcYjd(@RequestBody List<String> slbhList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYjdDOList 移交单集合
     * @return  操作返回信息
     * @description （海门）进行移交单移交到档案
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/yjd/yj")
    String executeYj(@RequestBody List<BdcYjdDO> bdcYjdDOList);

    /**
     * @param slbhList 受理编号集合
     * @return 受理编号相关文件信息
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description （海门）提供受理编号相关文件信息给档案
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/yjd/files/all")
    List<BdcYjdXmFilesDTO> getFilesBySlbhList(@RequestBody List<String> slbhList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param yjxx 移交信息
     * @return {ResultCode} 返回状态信息实体
     * @description （海门）提供给档案接口，档案系统接收档案后回调该接口进行更新接收人等信息
     */
    @PutMapping(value = "/realestate-certificate/rest/v1.0/yjd/yjxx")
    ResultCode updateYjxx(@RequestBody List<BdcYjdDO> yjxx);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbh 当前项目受理编号
     * @return {List} 上一手受理编号
     * @description （海门提供给档案接口）根据受理编号获取上一手项目受理编号
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/yjd/pre/slbh")
    List<String> listPreSlbh(@RequestParam("slbh")String slbh);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbh 当前项目受理编号
     * @return {List} 下一手受理编号
     * @description （海门提供给档案接口）根据受理编号获取下一手项目受理编号
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/yjd/next/slbh")
    List<String> listNextSlbh(@RequestParam("slbh")String slbh);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcHaimenYhYjdDTO
     * @description 海门银行系统提供移交单接口
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/yjd/haimen/yh/save")
    void saveHaimenYhYjd(@RequestBody BdcHaimenYhYjdDTO bdcHaimenYhYjdDTO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcYjdTaskGxDOList]
     * @return int
     * @description 保存移交单和任务关系 蚌埠
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/yjd/yjdTaskGx/save")
    int saveBdcYjdTaskGx(@RequestBody List<BdcYjdTaskGxDO> bdcYjdTaskGxDOList);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [taskids]
     * @return int
     * @description 更新移交单打印状态 蚌埠
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/yjd/yjdTaskGx/dyzt")
    int updateYjdDyztBytaskid(@RequestBody List<String> taskids);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [paramMap]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcYjdTaskGxDO>
     * @description 查询移交单任务关系
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/yjd/yjdTaskGx")
    List<BdcYjdTaskGxDO> getYjdTaskGx(@RequestBody Map paramMap);
}

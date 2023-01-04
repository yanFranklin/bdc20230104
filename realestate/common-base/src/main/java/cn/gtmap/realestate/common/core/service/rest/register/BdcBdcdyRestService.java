package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.BdcXmXmfbDTO;
import cn.gtmap.realestate.common.core.dto.register.*;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDyaQo;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDysdQO;
import cn.gtmap.realestate.common.core.qo.register.BdcBdcdyQO;
import cn.gtmap.realestate.common.core.qo.register.BdcCqQO;
import cn.gtmap.realestate.common.core.qo.register.BdcLsgxQO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcBdcdyVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDyawqdVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcJfVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/2/21
 * @description 不动产单元相关服务定义
 */
public interface BdcBdcdyRestService {
    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   pageable  分页参数
     * @param   bdcLsgxParamJson  查询参数
     * @return {Page} 项目列表
     * @description  查询不动产单元号对应的项目登记历史关系
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/bdcdy")
    Page<BdcLsgxXmDTO> listBdcdyLsgx(Pageable pageable,
                                     @RequestParam(name = "bdcLsgxParamJson", required = true)String bdcLsgxParamJson);

    /**
     * @param page 页码
     * @param size 每页的数据量
     * @param sort 排序
     * @return Page<BdcXmDO> 分页查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询项目信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcXmPage", method = RequestMethod.POST)
    Page<BdcXmDO> listXmByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcXmDO bdcXmDO);

    /**
     * @param page 页码
     * @param size 每页的数据量
     * @param sort 排序
     * @return Page<BdcXmDO> 分页查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询不动产单元信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcdyPage", method = RequestMethod.POST)
    Page<BdcBdcdyVO> listBdcdyByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcBdcdyQO bdcBdcdyQO);

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: pageable 分页参数
     * @param: bdcBdcdyQOJson 不动产单元查询实体类JSON数据
     * @return: Page<BdcBdcdyVO> 分页的不动产单元视图实体类
     * @description 分页查询不动产单元信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcdyPage/json", method = RequestMethod.POST)
    Page<BdcBdcdyVO> listBdcdyByPage(Pageable pageable, @RequestParam(name = "bdcBdcdyQOJson", required = false) String bdcBdcdyQOJson);

    /**
     * @param page 页码
     * @param size 每页的数据量
     * @param sort 排序
     * @return Page<BdcXmDO> 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 分页查询流程中的不动产单元号和坐落（去重）
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/distinctBdcdyhPage", method = RequestMethod.POST)
    Page<BdcBdcdyVO> listDistinctBdcdyByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcXmDO bdcXmDO);

    /**
     * @param bdcBdcdyQO 不动产单元查询实体
     * @return List<BdcBdcdyVO> 不动产单元信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元信息(和分页查询不动产单元信息是同一个查询)
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcdy/list", method = RequestMethod.POST)
    List<BdcBdcdyVO> queryBdcdyList(@RequestBody BdcBdcdyQO bdcBdcdyQO);
    /**
     * @param page 页码
     * @param size 每页的数据量
     * @param sort 排序
     * @return Page<BdcXmDO> 分页查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询抵押物清单
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dyawqdPage", method = RequestMethod.POST)
    Page<BdcDyawqdVO> listDyawqdByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcXmDO bdcXmDO);

    /**
     * @param pageable 每页的数据量
     * @return Page<BdcJfVO> 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 分页查询解封清单
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/jfqdPage", method = RequestMethod.GET)
    Page<BdcJfVO> listJfqdByPage(Pageable pageable, @RequestParam(name = "gzlslid") String gzlslid);

    /**
     * @param page 页码
     * @param size 每页的数据量
     * @param sort 排序
     * @param dyabg
     * @return Page<BdcXmDO> 分页查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询原抵押物清单（用于注销流程的展示查询）
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/ydyawqdPage", method = RequestMethod.POST)
    Page<BdcDyawqdVO> listYxmdyawqdByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcXmDO bdcXmDO, @RequestParam(name = "dyabg", required = false) Integer dyabg);

    /**
     * @param page 页码
     * @param size 每页的数据量
     * @param sort 排序
     * @return Page<BdcXmDO> 分页查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询抵押注销证明信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dyaZxzmPage", method = RequestMethod.POST)
    Page<BdcXmDO> listDyaZxByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcXmDO bdcXmDO);
    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @param bdcXmQO 查询参数
     * @return List<BdcDyaqDO> 抵押信息
     * @description  查询当前流程的原抵押信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/ydyaxx", method = RequestMethod.POST)
    List<BdcDyaqDO> queryYdyaxx(@RequestBody BdcXmQO bdcXmQO);
    /**
     * @param bdcXmDO 项目信息
     * @return List<BdcDyawqdVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询抵押物清单
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dyawqd", method = RequestMethod.POST)
    List<BdcDyawqdVO> queryDyawqd(@RequestBody BdcXmDO bdcXmDO);

    /**
     * @param bdcXmDO 项目信息
     * @return List<BdcDyawqdVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询原抵押物清单信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/ydyawqd", method = RequestMethod.POST)
    List<BdcDyawqdVO> queryYxmDyawqd(@RequestBody BdcXmDO bdcXmDO);

    /**
     * @param bdcXmDO 项目信息
     * @return List<BdcDyawqdVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 抵押变更类型1时，查询抵押物清单
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/ydyawqd/bg", method = RequestMethod.POST)
    List<BdcDyawqdVO> queryBgYxmDyawqd(@RequestBody BdcXmDO bdcXmDO);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcdyh  不动产单元号
     * @return {Boolean} 存在查封：true，不存在查封：false
     * @description  判断不动产单元号对应的权利人是否存在查封
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/bdcdy/qlr/cf/{bdcdyh}")
    Boolean isBdcdyhQlrCf(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcdyh  不动产单元号
     * @return {Boolean} 存在异议：true，不存在异议：false
     * @description  判断不动产单元号对应的权利人是否存在异议
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/bdcdy/qlr/yy/{bdcdyh}")
    Boolean isBdcdyhQlrYy(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @return {List} 锁定则返回提示信息；未锁定则返回空
     * @description 验证所在宗地不动产单元是否锁定
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/bdcdy/{bdcdyh}/zdsd")
    List<String> isZdBdcdySd(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @return {Boolean} 验证通过：true ；不通过：false
     * @description 验证当前项目权利人权利比例之和是否为1
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/bdcdy/xmxx/qlr/{xmid}")
    Boolean checkQlrQlbl(@PathVariable("xmid")String xmid);

    /**
     * @param gzlslid   工作流实例ID
     * @param dylx      打印类型
     * @param bdcUrlDTO url请求对象
     * @return String  获取打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 打印xml
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/print/bdcdy/xml", method = RequestMethod.POST)
    String bdcdyPrintXml(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "dylx") String dylx, @RequestBody BdcUrlDTO bdcUrlDTO);

    /**
     * @param bdcPrintDTO 打印参数
     * @return String  获取打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 打印xml
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/print/bdcdy/printDTO/xml", method = RequestMethod.POST)
    String bdcdyPrintXml(@RequestBody BdcPrintDTO bdcPrintDTO);

    /**
     * @param bdcPrintDTO 打印参数
     * @return String  获取打印xml批量
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 打印xml
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/print/bdcdy/printDTO/xml/pl", method = RequestMethod.POST)
    String bdcdyPrintXmlPl(@RequestBody BdcPrintDTO bdcPrintDTO);
    /**
     * 获取项目来源信息
     * @param xmid
     * @return
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/bdcdy/tellTdFromBdc")
    Map queryXmly(@RequestParam(name = "xmid") String xmid);

    /**
     * 获取产权大证信息
     * @param xmid
     * @return
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/bdcdy/cqdz")
    List<BdcLsgxXmDTO> cqdz(@RequestParam(name = "xmid") String xmid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 面积和
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 计算房地产权面积之和
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/bdcdy/mj/{gzlslid}")
    BdcdySumDTO calculatedFdcqMj(@PathVariable("gzlslid") String gzlslid,@RequestParam(name = "sfyql") Boolean sfyql,@RequestParam(name = "djxl", required = false) String djxl);

    /**
     * @param gzlslid 工作流实例ID
     * @return 面积和
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 计算抵押权面积之和
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/bdcdy/dyaq/mj/{gzlslid}")
    BdcdySumDTO calculatedDyaqMj(@PathVariable("gzlslid") String gzlslid, @RequestParam(name = "sfyql") Boolean sfyql, @RequestBody List<String> xmidList);

    /**
     * @param gzlslid 工作流实例ID
     * @return 面积和
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 计算抵押权面积之和
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/bdcdy/dyaq/mjgybdclx/{gzlslid}")
    List<BdcdySumDTO> calculatedDyaqMjGyBdclx(@PathVariable("gzlslid") String gzlslid,@RequestParam(name = "sfscql")
            Boolean sfscql);

    /**
     * @param bdcdyh
     * @param sdzt
     * @return 锁定数据
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取单元号的锁定数据
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/bdcdy/dysd/list")
    List<BdcDysdDO> queryBdcDysd(@RequestParam(value = "bdcdyh") String bdcdyh, @RequestParam(value = "sdzt",required = false)Integer sdzt);

    /**
     * @param gzlslid   工作流实例ID
     * @param bdcDyaqDO 抵押信息
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新原抵押信息的抵押注销申请人
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/ydyaxx/dyzxsqr/{gzlslid}", method = RequestMethod.POST)
    int saveYdyaxxZxsqrPl(@PathVariable(value = "gzlslid") String gzlslid, @RequestBody BdcDyaqDO bdcDyaqDO);

    /**
     * 查询当前项目上一手的产权
     *
     * @param bdcLsgxQO 历史关系查询对象
     * @return 不动产单元列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcdy/lsgx/last/cq", method = RequestMethod.POST)
    List<BdcLsgxXmDTO> listBdcdyLsgxLastCq(@RequestBody BdcLsgxQO bdcLsgxQO);

    /**
     * 通过上一手的证书信息查询现不动产项目信息
     * <p>不动产项目表，不动产历史关系表，不动产项目证书关系表三表关联查询项目信息</p>
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [gzlslid, zsid, qllx] 工作流实例ID， 证书ID（上一手项目的证书ID） 权利类型
     * @return: List<BdcXmDO> 不动产项目信息集合
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcdy/lsgx/bdcxm/list", method = RequestMethod.POST)
    List<BdcXmDO> queryBdcXmByGzlslidAndZsid(@RequestParam(value = "gzlslid")String gzlslid,
                                             @RequestParam(value = "zsid", required = false) String zsid,
                                             @RequestParam(value = "qllx", required = false) Integer qllx);

    /**
     * @param bdcXmQO 项目查询对象
     * @return Page<BdcXmDO> 分页查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前项目和原项目的单元信息，并去重
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/xmOrYxm", method = RequestMethod.POST)
    Page<BdcXmXmfbDTO> getXmOrYxmDistinctByDyhPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcXmQO bdcXmQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXmQO 抵押项目
     * @return  抵押土地面积
     * @description  查询抵押土地面积：如果是产权，取所有不动产单元 产权的 fttdmj + dytdmj 之和
     */
    @PostMapping("/realestate-register/rest/v1.0/bdcdy/dya/tdmj")
    Double getDyaTdmj(@RequestBody BdcXmQO bdcXmQO);

    /**
     * @param bdcBdcdyQO 单元信息查询对象
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据房屋用途统计面积
     */
    @PostMapping("/realestate-register/rest/v1.0/bdcdy/sumMjbyYt")
    List<Map> getSumMjByGhyt(@RequestBody BdcBdcdyQO bdcBdcdyQO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询预告
     */
    @GetMapping("/realestate-register/rest/v1.0/bdcyg/search/{xmid}")
    List listBdcYgByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * @param bdcXmQO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询当前不动产单元号是否办理过抵押
     * @date : 2020/8/3 11:24
     */
    @PostMapping("/realestate-register/rest/v1.0/bdcdya/search")
    List<BdcDyaqDO> listExistBdcDyaq(@RequestBody BdcXmQO bdcXmQO);

    /**
     * @param pageable         分页参数
     * @param hbBdcdyParamJson 查询参数
     * @return {Page} 产权信息列表
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description （海门）查询待合并不动产单元信息
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/bdcdy/hb/bdcdy")
    Page<BdcHbBdcdyDTO> queryDhbBdcdyXx(Pageable pageable, @RequestParam(name = "hbBdcdyParamJson") String hbBdcdyParamJson);

    /**
     * @param bdcdyDTOList 待合并不动产单元信息
     * @param ppBdcdyh     匹配到的新记录待使用不动产单元号
     * @return {BdcHbBdcdyDTO} 新合并生成的不动产单元记录
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 执行不动产单元合并操作
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/bdcdy/hb/bdcdy/new/{ppBdcdyh}")
    BdcHbBdcdyDTO hbBdcdy(@RequestBody List<BdcHbBdcdyDTO> bdcdyDTOList, @PathVariable(value = "ppBdcdyh") String ppBdcdyh);

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/7
     * @description 根据不动产单元号查询预告
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/bdcdy/ygByBdcdy")
    List listYgByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcdyhList 不动产单元号集合
     * @return {List} 房屋性质
     * @description  查询抵押物清单列表单元的房屋信息
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/bdcdy/dyawqd/fwxx")
    List<BdcDyawqdVO> listBdcDyawqdFwxx(@RequestBody List<String> bdcdyhList);

    /**
     * 获取落款单位登记机构配置内容
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 落款单位登记机构配置
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/bdcdy/config/lkdw")
    String getLkdwConfig();

    /**
     * @param gzlslid 工作流实例ID
     * @return 面积和
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 计算建设用地使用权面积之和
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/bdcdy/jsydsyq/mj/{gzlslid}")
    BdcdySumDTO calculatedJsydsyqMj(@PathVariable("gzlslid") String gzlslid, @RequestParam(name = "sfyql") Boolean sfyql, @RequestParam(name = "djxl", required = false) String djxl);

    /**
     * @param bdcCqQO 产权查询对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询房地产权项目信息
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/bdcdy/fdcq/xmxx")
    List<Map> listBdcFdcqXmxx(@RequestBody BdcCqQO bdcCqQO);

    /**
     * @param bdcCqQO 产权查询对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询建设用地使用权项目信息
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/bdcdy/jsydsyq/xmxx")
    List<Map> listBdcJsydsyqXmxx(@RequestBody BdcCqQO bdcCqQO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询单元锁定信息
     * @date : 2021/8/20 15:21
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/bdcdy/cjsd")
    List<BdcDysdDO> listBdcDysd(@RequestBody BdcDysdQO bdcDysdQO);

    /**
     * @param bdcDyaqQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人信息查询抵押信息
     * @date : 2021/9/14 14:53
     */
    @PostMapping("/realestate-register/rest/v1.0/bdcdy/dyaq/qlrxx")
    List<BdcDyaqQlrDTO> listBdcDyaqByQlrmc(@RequestBody BdcDyaQo bdcDyaqQO);

    /**
     * @param bdcLsgxParamJson
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/9/14 14:53
     */
    @PostMapping("/realestate-register/rest/v1.0/bdcdy/cfdjls")
    List<BdcLsgxXmDTO> listBdccfdjls(@RequestParam(name = "bdcLsgxParamJson", required = true) String bdcLsgxParamJson);

    /**
     * @param qxdm
     * @param zdtzm
     * @param dzwtzm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 生成虚拟单元号
     * @date : 2021/12/30 15:39
     */
    @PostMapping("/realestate-register/rest/v1.0/bdcdy/xndyh/{qxdm}/{zdtzm}/{dzwtzm}")
    String createXndyh(@PathVariable(value = "qxdm") String qxdm, @PathVariable(value = "zdtzm") String zdtzm, @PathVariable(value = "dzwtzm") String dzwtzm);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询房地产权使用权结束时间去重后的数据
     * @date : 2022/9/7 19:13
     */
    @GetMapping("/realestate-register/rest/v1.0/bdcdy/fdcq/tdsyjssj")
    TdSyqJssjDTO listDistinctTdsyjssj(@RequestParam("gzlslid") String gzlslid);

    /**
     * @param bdcPrintDTO
     * @return pdf base64文件
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成抵押物清单pdf
     */
    @PostMapping("/realestate-register/rest/v1.0/bdcdy/pdf/dywqd")
    String getDywqdPdf(@RequestBody BdcPrintDTO bdcPrintDTO);

}


package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshFwkgDataDTO;
import cn.gtmap.realestate.common.core.dto.etl.BzjdDTO;
import cn.gtmap.realestate.common.core.dto.etl.XscqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx.CourtYwxxcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.init.*;
import cn.gtmap.realestate.common.core.qo.init.*;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcXmFwzkfbVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/8
 * @description 查询不动产信息接口
 */
public interface BdcXmRestService {
    /**
     * 通过传入参数返回不动产项目信息
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcXmQO 不动产项目
     * @return  {List} 不动产项目信息
     * @description 通过传入参数返回不动产项目信息
     */
    @PostMapping(path = "/init/rest/v1.0/xmxx/list")
    List<BdcXmDO> listBdcXm(@RequestBody BdcXmQO bdcXmQO);

    /**
     * 通过传入bdcdyh返回对象的现势产权项目信息
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcdyhList 不动产单元号集合
     * @return  {List} 不动产项目信息
     * @description 通过传入bdcdyh返回对象的现势产权项目信息
     */
    @PostMapping(path = "/init/rest/v1.0/xmxx/xscq/list")
    List<BdcXmDO> listBdcXmXscq(@RequestBody List<String> bdcdyhList);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcXmQO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @description 根据办结日期 查询XM的BDCDYH
     */
    @PostMapping(path = "/init/rest/v1.0/xmxx/listbybjrq")
    List<BdcXmDO> listBdcXmByBjrq(@RequestBody BdcXmQO bdcXmQO);

    /**
     * 更新不动产项目信息bdcQlPageByPageJson
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcXmDO 不动产项目
     *@description 更新不动产项目信息
     */
    @PutMapping(path = "/init/rest/v1.0/xmxx")
    int updateBdcXm(@RequestBody BdcXmDO bdcXmDO);

    /**
     * 选择不动产权利页面后台服务
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @param pageable
     * @param bdcQlQoStr  bdcQlQo的json串
     *@return Page<BdcQlPageResponseDTO> 分页查询对象
     *@description 选择不动产权利页面后台服务
     */
    @PostMapping(path = "/init/rest/v1.0/xmxx/page")
    Page<BdcQlPageResponseDTO> bdcQlPageByPageJson(Pageable pageable,
                                                   @RequestParam(name = "bdcQlQoStr",required = false) String bdcQlQoStr);


    /**
     * 选择不动产权利页面后台服务(非分页)
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param bdcQlQO  bdcQlQo
     *@return List<BdcQlPageResponseDTO> 查询对象集合
     *@description 选择不动产权利页面后台服务
     */
    @PostMapping(path = "/init/rest/v1.0/xmxx/bdcql/list")
    List<BdcQlPageResponseDTO> bdcQlList(@RequestBody(required = false) BdcQlQO bdcQlQO);

    /**
     * 选择查封信息页面后台服务(不分页)
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param bdcCfQO
     *@return List<BdcCfxxPageResponseDTO> 查询对象集合
     *@description 选择查封信息页面后台服务
     */
    @PostMapping(path = "/init/rest/v1.0/cfxx/list")
    List<BdcCfxxPageResponseDTO> bdcCfxxList(@RequestBody(required = false) BdcCfQO bdcCfQO);


    /**
     * 选择查封信息页面后台服务
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param pageable
     * @param bdcCfQoStr
     *@return Page<BdcCfxxPageResponseDTO> 分页查询对象
     *@description 选择查封信息页面后台服务
     */
    @PostMapping(path = "/init/rest/v1.0/cfxx/page")
    Page<BdcCfxxPageResponseDTO> bdcCfxxPageByPageJson(Pageable pageable,
                                                       @RequestParam(name = "bdcCfQoStr", required = false) String bdcCfQoStr);

    /**
     * @param bdcCfQoStr
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 选择查封以及查封的续封信息页面后台服务
     * @date : 2021/9/10 10:34
     */
    @PostMapping(path = "/init/rest/v1.0/cfxfxx/page")
    Page<BdcCfxxPageResponseDTO> bdcCfXfxxPageByPageJson(Pageable pageable,
                                                         @RequestParam(name = "bdcCfQoStr", required = false) String bdcCfQoStr);

    /**
     * 选择证书锁定信息页面后台服务(不分页)
     *
     * @param bdcZssdQO 证书锁定DO对象
     * @return List<BdcZssdPageResponseDTO> 查询对象集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 选择证书锁定信息页面后台服务(不分页)
     */
    @PostMapping(path = "/init/rest/v1.0/zssd/list")
    List<BdcZssdPageResponseDTO> bdcZssdList(@RequestBody(required = false) BdcZssdQO bdcZssdQO);

    /**
     * 选择单元锁定信息页面后台服务(不分页)
     *
     * @param bdcDysdQO 单元锁定DO对象
     * @return List<BdcZssdPageResponseDTO> 查询对象集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 选择单元锁定信息页面后台服务(不分页)
     */
    @PostMapping(path = "/init/rest/v1.0/dysd/list")
    List<BdcDysdPageResponseDTO> bdcDysdList(@RequestBody(required = false) BdcDysdQO bdcDysdQO);

    /**
     * 选择证书锁定信息页面后台服务
     * @param pageable     页面分页参数
     * @param bdcZssdQOStr 证书锁定查询参数
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 不动产证书锁定信息分页对象
     */
    @PostMapping(path = "/init/rest/v1.0/zssd/page")
    Page<BdcZssdPageResponseDTO> bdcZssdPageByPageJson(Pageable pageable,
                                                       @RequestParam(name = "bdcZssdQOStr",required = false) String bdcZssdQOStr);

    /**
     * 选择单元锁定信息页面后台服务
     * @param pageable     页面分页参数
     * @param bdcDysdQOStr 单元锁定查询参数
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 不动产单元锁定信息分页对象
     */
    @PostMapping(path = "/init/rest/v1.0/dysd/page")
    Page<BdcDysdPageResponseDTO> bdcDysdPageByPageJson(Pageable pageable,
                                                       @RequestParam(name = "bdcDysdQOStr",required = false) String bdcDysdQOStr);

   /**
    *根据项目id获取开关实例数据
    *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
    *@param xmid 项目id
    *@return BdcCshFwkgSlDO 实例信息
    *@description
    */
    @GetMapping(path = "/init/rest/v1.0/{xmid}/fwkgsl")
    BdcCshFwkgSlDO queryFwkgsl(@PathVariable("xmid") String xmid);

    /**
     * 根据项目id更新受理开关服务信息
     * @param bdcCshFwkgSlDO 不动产受理房屋开关服务信息
     * @return int           更新状态
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PutMapping(path = "/init/rest/v1.0/fwkgsl")
    int updateCshFwkgSl(@RequestBody BdcCshFwkgSlDO bdcCshFwkgSlDO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [xmids, sfhz]
     * @return int
     * @description 批量更新是否换证
     */
    @PutMapping(path = "/init/rest/v1.0/fwkgsl/batchSfhz")
    int batchUpdateCshFwkgSlSfhz(@RequestBody List<String> xmids, @RequestParam(name = "sfhz") String sfhz);

    /**
     * 通过工作流实例ID查询不动产初始化开关服务信息
     * @param gzlslid 工作流实例ID
     * @param bdcdyh  不动产单元号
     * @return
     */
    @PostMapping(path = "/init/rest/v1.0/fwkgsl/gzlslid")
    List<BdcSlCshFwkgDataDTO> queryFwkgslByGzlslid(@RequestParam(name="gzlslid") String gzlslid,
                                                   @RequestParam(name="bdcdyh", required = false) String bdcdyh);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcXmLsgxDOList
     *@return
     *@description 外联证书接口
     */
    @PutMapping(path = "/init/rest/v1.0/bdcxmlsgx/batchInsert")
    void batchInsertBdcXmLsgx(@RequestBody List<BdcXmLsgxDO> bdcXmLsgxDOList);


    /**
     * 获得组合的所有项目信息
     * @param xmid  项目ID
     * @return List<BdcXmZhxxDTO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获得组合的所有项目信息
     */
    @GetMapping("/init/rest/v1.0/zhxmxx/{xmid}/list")
    List<BdcXmZhxxDTO> queryBdcXmZhxx(@PathVariable("xmid") String xmid);


    /**
     * 根据工作流实例ID获取挂接附件的项目  空的话用gzlslid去获取
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param gzlslid  工作流实例ID
     *@return List<BdcXmDO>
     *@description 根据工作流实例ID获取挂接附件的项目  空的话用gzlslid去获取
     */
    @GetMapping(path = "/init/rest/v1.0/fileXm/{gzlslid}/list")
    List<BdcXmDO> queryFileXmList(@PathVariable("gzlslid") String gzlslid);

    /**
     * 根据受理编号和证件号获取项目信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param slbh
     *@param zjh
     *@return List<Map>
     *@description 根据受理编号和证件号获取项目信息
     */
    @GetMapping(path = "/init/rest/v1.0/xmxx/{slbh}/{zjh}/list")
    List<Map> queryBjjdXx(@PathVariable("slbh") String slbh, @PathVariable("zjh") String zjh);

    /**
     * 根据产权证号获取项目信息
     *
     * @param bdcqzh 不动产权证号
     * @param qszt   权属状态
     * @return List<Map>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(path = "/init/rest/v1.0/xmxx/bdcqz/qszt/xmid")
    List<Map> queryBdcxmByCqzh(@RequestParam("bdcqzh") String bdcqzh, @RequestParam("qszt") String qszt,@RequestParam("bdcdyh") String bdcdyh);

    /**
     * @param bdcDjxlDjyyQO 登记小类登记原因QO
     * @return 登记小类登记原因集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询登记原因登记小类关系,djxl必须匹配,其余参数可匹配或配置为空
     */
    @PostMapping(path = "/init/rest/v1.0/djyy")
    List<BdcDjxlDjyyGxDO> listBdcDjxlDjyyGxWithParam(@RequestBody BdcDjxlDjyyQO bdcDjxlDjyyQO);

    /**
     * 根据 gzlslid 确定项目类型
     * @param gzlslid 工作流实例 id
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @return 1:普通  2：组合  3：批量  4:批量组合
     */
    @GetMapping(path = "/init/rest/v1.0/xmxx/{gzlslid}/xmlx")
    int makeSureBdcXmLx(@PathVariable("gzlslid") String gzlslid);

    /**
     * 根据 gzlslid 确定项目类型
     * @param list 不动产项目DO集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @return 1:普通  2：组合  3：批量  4:批量组合
     */
    @PostMapping(path = "/init/rest/v1.0/xmxx/xmlx")
    int makeSureBdcXmLx(@RequestBody List<BdcXmDO> list);

    /**
     * 通过传入参数返回不动产项目部分信息
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   gzlslid 工作流实例id
     * @return  {List} 不动产项目部分信息
     * @description 通过传入参数返回不动产项目部分信息
     */
    @GetMapping(path = "/init/rest/v1.0/bfxmxx/{gzlslid}/gzlslid/list")
    List<BdcXmDTO> listBdcXmBfxxByGzlslid(@PathVariable("gzlslid")String gzlslid);

    /**
     * 通过传入参数返回不动产项目部分信息
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   slbh   受理编号
     * @return  {List} 不动产项目部分信息
     * @description 通过传入参数返回不动产项目部分信息
     */
    @GetMapping(path = "/init/rest/v1.0/bfxmxx/{slbh}/slbh/list")
    List<BdcXmDTO> listBdcXmBfxxBySlbh(@PathVariable("slbh")String slbh);

    /**
     * 通过传入工作流实例id返回受理编号
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   gzlslid 工作流实例id
     * @return  受理编号
     * @description 通过传入工作流实例id返回受理编号
     */
    @GetMapping(path = "/init/rest/v1.0/lc/slbh/{gzlslid}")
    String querySlbh(@PathVariable("gzlslid")String gzlslid);

    /**
     * 通过传入受理编号返回工作流实例id
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   slbh 受理编号
     * @return  工作流实例id
     * @description 通过传入受理编号返回工作流实例id
     */
    @GetMapping(path = "/init/rest/v1.0/lc/gzlslid/{slbh}")
    String queryGzlslid(@PathVariable("slbh")String slbh);

    /**
     * 通过传入工作流实例ID和权利类型获取登记原因
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   gzlslid 工作流实例ID
     * @param   qllx 权利类型
     * @return  登记原因
     * @description 通过传入工作流实例ID和权利类型获取登记原因
     */
    @GetMapping(path = "/init/rest/v1.0/lc/djyy/{gzlslid}")
    String queryDjyy(@PathVariable("gzlslid")String gzlslid,@RequestParam(value = "qllx",required = false)Integer qllx);

    /**
      * @param gzlslid 工作流实例ID
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 通过工作流实例ID获取预告预抵押登记原因
      */
    @GetMapping(path = "/init/rest/v1.0/lc/ygdjyy/{gzlslid}")
    String queryZxYgYdyDjyy(@PathVariable("gzlslid")String gzlslid,@RequestParam(value = "ygdjzl",required = false)List<Integer> ygdjzl);

    /**
     *根据工作流实例ID获取开关实例数据
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param gzlslid 工作流实例ID
     *@return List<BdcCshFwkgSlDO> 实例集合
     *@description
     */
    @GetMapping(path = "/init/rest/v1.0/{gzlslid}/fwkgsl/list")
    List<BdcCshFwkgSlDO> queryFwkgslByGzlslid(@PathVariable("gzlslid") String gzlslid);

    /**
     * @param bdcDjxxUpdateQO 更新对象
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  批量更新项目
     */
    @PostMapping(path = "/init/rest/v1.0/xmxx/jsonStr")
    int updateBatchBdcXm(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO);


    /**
     * @param bdcQlQoStr 查询对象
     * @return 分页信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description  分页查询原权利信息
     */
    @PostMapping(path = "/init/rest/v1.0/xmxx/bdcyql/list/page")
    Page<BdcQlPageResponseDTO> listBdcYqlByPage(Pageable pageable,
                                                @RequestParam(name = "bdcQlQoStr",required = false) String bdcQlQoStr);

    /**
     * @param bdcXmDO
     * @return List<BdcXmFwzkfbVO> 查询结果
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjiaqiang</a>
     * @description 查询抵押物清单
     */
    @RequestMapping(value = "/init/rest/v1.0/fwzkbPage", method = RequestMethod.POST)
    List<BdcXmFwzkfbVO> listFwzkbByPage(@RequestBody BdcXmDO bdcXmDO);

    /**
     * @param bdcXmDO
     * @return List<BdcXmFwzkfbVO> 查询结果
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjiaqiang</a>
     * @description 查询原抵押物清单
     */
    @RequestMapping(value = "/init/rest/v1.0/yxmfwzkbPage", method = RequestMethod.POST)
    List<BdcXmFwzkfbVO> listYxmFwzkbByPage(@RequestBody BdcXmDO bdcXmDO);


    /**
     * @param xmid
     * @return List<String> 项目集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询当前证书项目的涉及的项目集合
     */
    @GetMapping(value = "/init/rest/v1.0/{xmid}/zsxm/list")
    List<String> queryZsxmList(@PathVariable("xmid") String xmid);

    /**
     * @param bdcXmDO 项目信息
     * @return int 更新数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新证书相关的项目的部分权利其他状况
     */
    @PutMapping(value = "/init/rest/v1.0/xmxx/bfqlqtzk")
    int updateBdcZsXmBfqlqtzk(@RequestBody BdcXmDO bdcXmDO);

    /**
     * @return List<String> 用途集合
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询要默认主房的用途集合
     */
    @RequestMapping(value = "/init/rest/v1.0/xmxx/getZfYt", method = RequestMethod.GET)
    List<String> getZfYt(@RequestParam(name = "zfpz") String zfpz);

   /**
    * @param gzlslid 工作流实例ID
    * @return List<BdcZdFwytDO>
    * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
    * @description 根据工作流实例ID获取房屋用途字典项
    */
    @GetMapping(path = "/init/rest/v1.0/zdfwyt/{gzlslid}")
    List<BdcZdFwytDO> queryZdFwytByGzlslid(@PathVariable("gzlslid") String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据当前工作流实例ID查询原项目
     */
    @PostMapping(value = "/init/rest/v1.0/xmxx/listYxm")
    List<BdcXmDO> listYxmByGzlslid(@RequestBody BdcXmLsgxDO bdcXmLsgxDO,@RequestParam(name = "gzlslid") String gzlslid,@RequestParam(name = "djxl",required = false) String djxl);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据查询对象查询原项目
     */
    @PostMapping(value = "/init/rest/v1.0/xmxx/listYxm/qo")
    List<BdcXmDO> listYxmByYxmQO(@RequestBody BdcYxmQO bdcYxmQO);

    /**
     * @param xmid 当前项目ID
     * @param getOneXm 是否获取一条
     * @return 产权项目列表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 嵌套获取上手产权项目
     */
    @PostMapping(value = "/init/rest/v1.0/xmxx/prevCq")
    List<BdcXmDO> listPrevCqXm(@RequestParam("xmid") String xmid,@RequestParam(value = "getOneXm") boolean getOneXm);

    /**
     * @param xmidList 当前项目ID集合
     * @param getOneXm 每个项目是否获取一条
     * @return 产权项目列表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 嵌套批量获取上手产权项目
     */
    @PostMapping(value = "/init/rest/v1.0/xmxx/prevCqpl")
    List<BdcXmDO> listPrevCqXmPl(@RequestBody List<String> xmidList,@RequestParam(value = "getOneXm") boolean getOneXm);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据查询对象查询原项目
     */
    @PostMapping(value = "/init/rest/v1.0/xmxx/list/xmandfb")
    List<BdcXmAndFbDTO> listBdcXmAndFb(@RequestBody BdcXmQO bdcXmQO);

    /**
     *
     * @param xm 权利人名称
     * @param zjhm 权利人证件号
     * @param cqzh 不动产权证号
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据条件查询产权
     * @return
     */
    @GetMapping(value = "/init/rest/v1.0/xmxx/xscq")
    List<XscqDTO> listXscq(@RequestParam(value = "xm") String xm, @RequestParam(value = "zjhm") String zjhm, @RequestParam(value = "cqzh") String cqzh);

    /**
     *
     * @param xm 权利人名称
     * @param zjhm 权利人证件号
     * @param htbh 合同编号
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据条件查询办证进度
     * @return
     */
    @GetMapping(value = "/init/rest/v1.0/xmxx/bzjd")
    List<BzjdDTO> listBzjd(@RequestParam(value = "xm") String xm, @RequestParam(value = "zjhm") String zjhm, @RequestParam(value = "htbh") String htbh);


    /**
     * @param bdcQlrQO 权利人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人名称加证件号找到项目表的经营权信息
     * @date : 2020/11/5 9:19
     */
    @PostMapping(value = "/init/rest/v1.0/xmxx/cbjyq")
    List<BdcXmDTO> listCbjyqXm(@RequestBody BdcQlrQO bdcQlrQO);

    /**
     * 根据xmids批量查询项目信息
     * @param xmids 项目ID集合
     * @return 项目数据集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/init/rest/v1.0/xmxx/list/xmids")
    List<BdcXmDO> listBdcXmByXmids(@RequestBody List<String> xmids);


    /**
     *根据工作流实例ID获取开关实例数据
     *@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     *@param gzlslid 工作流实例ID
     *@return List<BdcCshFwkgSlDO>  是否主房实例集合
     *@description
     */
    @GetMapping(path = "/init/rest/v1.0/{gzlslid}/fwkgsl/sfzf/list")
    List<BdcCshFwkgSlDO> querySfzfKgslByGzlslid(@PathVariable("gzlslid") String gzlslid,@RequestParam(name = "sfzf") Integer sfzf);

    /**
     * 法院查询现势产权
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param courtYwxxcxRequestDTO
     * @return
     */
    @PostMapping(value = "/init/rest/v1.0/court/xscq/list")
    List<BdcXmDO> listCourtXscq(@RequestBody CourtYwxxcxRequestDTO courtYwxxcxRequestDTO);

    /**
      * @param bdcXmDOList  包含不动产单元号（土地或房屋）和不动产类型
      * @return 产权项目
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据不动产单元查询现势产权项目
      */
    @PostMapping(path = "/init/rest/v1.0/xmxx/xscq/bdcdyh")
    List<BdcXmDO> listXscqXm(@RequestBody List<BdcXmDO> bdcXmDOList);

    /**
     * 查询登记系统存在的所有权籍管理代码
     *
     * @return {List} 权籍管理代码集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping(path = "/init/rest/v1.0/xmxx/qjgldm")
    List<String> listQjgldm();


    /**
     * @param bdcCshFwkgSlDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增初始化开关实例数据
     * @date : 2021/9/30 14:18
     */
    @PostMapping("/init/rest/v1.0/fwkgsl")
    int insertBdcCshFwkgSl(@RequestBody BdcCshFwkgSlDO bdcCshFwkgSlDO);

    /**
     * 根据xmid获取历史关系
     *
     * @param xmid 不动产权证号
     * @return List<Map>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(path = "/init/rest/v1.0/xmxx/lsgx")
    BdcXmLsgxDTO queryBdcxmLsgxByXmid(@RequestParam("xmid") String xmid);

    /**
     * 根据不动产权证号查询项目信息
     * @param bdcqzh 不动产权证号
     * @return BdcXmDO 不动产项目信息
     */
    @GetMapping(path = "/init/rest/v1.0/xmxx/zsbdcqzh")
    List<BdcXmDO> queryXmByZsBdcqzh(@RequestParam("bdcqzh") String bdcqzh);

    /**
     * 根据工作流实例ID获取同审批系统业务号的其他项目信息
     * @param gzlslid 工作流实例ID
     * @return BdcXmDO 不动产项目信息
     */
    @GetMapping(path = "/init/rest/v1.0/xmxx/spxtywh")
    List<BdcXmDO> listOtherXmWithSameSpxtywh(@RequestParam("gzlslid") String gzlslid);

    /**
     * @param xmid
     * @return daywh
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据xmid取daywh值（盐城BDC_XM表独有字段）
     */
    @GetMapping(path = "/init/rest/v1.0/xmxx/yancheng/daywh")
    String queryDaywh(@RequestParam("xmid") String xmid);


    /**
     * 模糊查询项目数据（限定数量、最新数据）
     * @param bdcXmQO 查询参数
     * @return {List} 项目数据
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping(path = "/init/rest/v1.0/xmxx/data")
    List<BdcXmDO> listBdcXmData(@RequestBody  BdcXmQO bdcXmQO);

    /**
     * @description 【常州】提示查看清册和附表--查询项目信息中同一工作流实例ID不动产单元号数量
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/6/21 21:04
     * @param gzlslid 工作流实例ID
     * @return int 同一工作流实例ID不动产单元号数量
     */
    @GetMapping(path = "/init/rest/v1.0/xmxx/{gzlslid}/bdcqzh/count")
    int countBdcByGzlslidGroupBdcdyh(@PathVariable("gzlslid") String gzlslid);

    /**
     * 根据受理编号查询一条项目信息（权利人、坐落）
     * @param slbh 受理编号
     * @return 项目数据
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(path = "/init/rest/v1.0/xmxx/{slbh}/one")
    BdcXmDO queryOneBdcXmDOBySlbh(@PathVariable(value = "slbh") String slbh);

    /**
     * 根据不动产项目ID查询项目信息
     * @param xmid 项目ID
     * @return 项目数据
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(path = "/init/rest/v1.0/xmxx/{xmid}")
    BdcXmDO queryBdcXmByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     *@author <a href="mailto:wangfangfang@gtmap.cn">wangfangfang</a>
     *@param xmid 项目ID
     *@param qszt 权属状态过滤
     *@return 项目ID集合
     *@description 根据不动产项目id获取相同证书的全部项目ID集合
     */
    @PostMapping(path = "/init/rest/v1.0/xmxx/zsxm/count")
    List<String> listYbzXmByXmid(@RequestParam(value = "xmid") String xmid,@RequestParam(value = "qszt",required = false) Integer qszt);
}

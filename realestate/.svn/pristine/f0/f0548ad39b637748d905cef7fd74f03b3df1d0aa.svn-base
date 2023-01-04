package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXmGdxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcGwcDeleteQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcUpdateDagsdQo;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlXmVO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/12
 * @description 不动产受理项目rest服务
 */
public interface BdcSlXmRestService {
    /**
     * @param xmid 项目ID
     * @return 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID获取不动产受理项目
     */
    @GetMapping("/realestate-accept/rest/v1.0/xm/{xmid}")
    BdcSlXmDO queryBdcSlXmByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * @param jbxxid 基本信息ID
     * @return 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID获取不动产受理项目
     */
    @GetMapping("/realestate-accept/rest/v1.0/xm/list/{jbxxid}")
    List<BdcSlXmDO> listBdcSlXmByJbxxid(@PathVariable(value = "jbxxid") String jbxxid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理项目
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取不动产受理项目
     */
    @GetMapping("/realestate-accept/rest/v1.0/xm/list/gzlslid/{gzlslid}")
    List<BdcSlXmDO> listBdcSlXmByGzlslid(@PathVariable(value = "gzlslid") String gzlslid);

    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 根据工作流实例id更新权属状态
    */
    @GetMapping("/realestate-accept/rest/v1.0/xm/qsztxz/gzlslid")
    void bdcSlGxqsztByGzlslid(@RequestParam(name = "processInsId") String processInsId) ;
    /**
     * @param xmidList 项目ID集合
     * @return 不动产受理项目
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID集合获取不动产受理项目
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/list/xmid")
    List<BdcSlXmDO> listBdcSlXmByXmids(@RequestBody List<String> xmidList);

    /**
     * @param bdcSlXmDO 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理项目
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/")
    BdcSlXmDO insertBdcSlXm(@RequestBody BdcSlXmDO bdcSlXmDO);

    /**
     * @param bdcSlXmDO 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理项目
     */
    @PutMapping("/realestate-accept/rest/v1.0/xm/")
    Integer updateBdcSlXm(@RequestBody BdcSlXmDO bdcSlXmDO);

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID删除不动产受理项目
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/xm/{xmid}")
    Integer deleteBdcSlXmByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * @param jbxxid 基本信息id
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID删除不动产受理项目
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/xm/list/{jbxxid}")
    Integer deleteBdcSlXmByJbxxid(@PathVariable(value = "jbxxid") String jbxxid);

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 分页已选业务信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/yx/page")
    Page<BdcSlYwxxDTO> listBdcSlXmByPageJson(Pageable pageable,
                                             @RequestParam(name = "bdcSlXmQO", required = false) String bdcSlXmQO);

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页已选业务信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/yx/list")
    List<BdcSlYwxxDTO> listBdcSlYwxxDTO(@RequestParam(name = "bdcSlXmQO", required = false) String bdcSlXmQO);

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 分页已选业务信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/yxbdcdy/page")
    YxBdcdyDTO queryYxBdcdyDTOByPage(Pageable pageable,
                               @RequestParam(name = "slXmQO", required = false) String slXmQO,
                               @RequestParam(name = "gzldyid", required = false) String gzldyid,
                               @RequestParam(name = "jbxxid", required = false) String jbxxid);

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 已选业务信息(不分页)
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/yx/")
    YxBdcdyDTO queryYxBdcdyDTO(
                               @RequestParam(name = "slXmQO", required = false) String slXmQO,
                               @RequestParam(name = "gzldyid", required = false) String gzldyid,
                               @RequestParam(name = "jbxxid", required = false) String jbxxid);

    /**
     * @param jbxxid 基本信息ID
     * @return 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息获取不动产受理项目集合
     */
    @GetMapping("/realestate-accept/rest/v1.0/xm/yx/ywxx/list/{jbxxid}")
    List<BdcSlYwxxDTO> listBdcSlYwxxByJbxxid(@PathVariable(value = "jbxxid") String jbxxid);

    /**
     * @param bdcCshSlxmDTO 不动产初始化受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 初始化受理项目信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/csh/{czrid}")
    void cshYxxm(@PathVariable(value = "czrid") String czrid, @RequestBody BdcCshSlxmDTO bdcCshSlxmDTO);

    /**
     * @param bdcCshSlxmDTO 不动产初始化受理项目
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 外联证书生成数据
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/wlzs")
    void wlZs(@RequestBody BdcCshSlxmDTO bdcCshSlxmDTO);

    /**
     * @param bdcCshSlxmDTO 不动产初始化受理项目
     * @author <a href="mailto:gailining@gtmap.cn">gaolining</a>
     * @description 外联证书生成数据,带入抵押信息生成历史关系
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/wlzs/dyaxx")
    void wlZsDyaqxx(@RequestBody BdcCshSlxmDTO bdcCshSlxmDTO, @RequestParam(name = "xmids", required = false) String xmids);

    /**
     * @param json 受理项目集合
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新受理项目
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/list/")
    Integer updateBdcSlXm(@RequestBody String json);

    /**
     * @param fwhsIndexs 户室主键集合，多个用英文逗号隔开
     * @param jbxxid 基本信息id
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据户室主键和基本信息id删除已选项目信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/list/deleteyxfwhs")
    Integer deleteYxFwhs(@RequestBody String fwhsIndexs,@RequestParam(name = "jbxxid") String jbxxid);

    /**
     * @param bdcSlXmQO 不动产受理项目查询
     * @return 受理项目集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据查询条件获取受理项目集合
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/list/bdcSlXm")
    List<BdcSlXmDO> listBdcSlXm(@RequestBody BdcSlXmQO bdcSlXmQO);

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据登记小类分组查询已选业务信息(不分页)
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/yx/groupbydjxl")
    List<YxBdcdyDTO> queryYxBdcdyDTOGroupByDjxl(
            @RequestParam(name = "slXmQO", required = false) String slXmQO,
            @RequestParam(name = "gzldyid", required = false) String gzldyid,
            @RequestParam(name = "jbxxid", required = false) String jbxxid,
            @RequestParam(name = "sfazfz", required = false) String sfazfz);


    /**
     * @param zsxh 证书序号
     * @param jbxxid 基本信息ID
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新证书序号
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/list/batchUpdateZsxh")
    Integer batchUpdateBdcSlXmZsxh(@RequestParam(name = "zsxh", required = false) String zsxh, @RequestParam(value = "jbxxid") String jbxxid,@RequestBody List<String> xmidList);


    /**
     * @return 已选数据
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询购物车中所有的数据,需要包含原项目id
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/yx/gwc/list")
    List<BdcSlYwxxDTO> listGwcData(@RequestParam(name = "bdcSlXmQO", required = false) String bdcSlXmQO);

    /**
     * 获取不动产单元号是否重复配置
     * 选择不动产单元页面
     *
     * @param xmid xmid
     * @return 是否去除重复数据
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/bdcdyh/repeat")
    boolean bdcdyhIsRepeat(@RequestParam(name = "xmid", required = false) String xmid);

    /**
     * @param bdcSlXmQO 不动产受理项目查询
     * @return 受理项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据原xmid和jbxxid查询到购物车中的对应xmid
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/xmlsgx/bdcSlXm")
    List<BdcSlXmDO> queryBdcSlXmByLsgx(@RequestBody BdcSlXmQO bdcSlXmQO);

    /**
     * @param bdcXmDO 承诺期限
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取领证日期
     * @date : 2020/1/15 9:57
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/lzrq")
    Date getLzrq(@RequestBody BdcXmDO bdcXmDO) throws Exception;

    /**
     * @param jbxxid 基本信息ID
     * @return 项目ID集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据基本信息ID获取项目ID集合
     */
    @GetMapping("/realestate-accept/rest/v1.0/xm/xmid/list/{jbxxid}")
    List<String> getXmidListByJbxxid(@PathVariable(name = "jbxxid") String jbxxid);

    /**
     * @param bdcSlXmQO 受理项目查询参数
     * @return 购物车已选项目数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询购物车已选项目数据(包含历史关系)
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/listWithlsgx")
    List<BdcSlXmVO> listGwcSelectDataWithLsgx(@RequestBody BdcSlXmQO bdcSlXmQO);

    /**
     * 通过基本信息ID查询受理发证状态信息
     * @param jbxxid 基本信息ID
     * @param
     * @return 发证状态信息
     */
    @PostMapping(path = "/realestate-accept/rest/v1.0/xm/fzzt")
    List<BdcSlCshFwkgDataDTO> querySlFzztByJbxxid(@RequestParam(name="jbxxid") String jbxxid);

    /**
     * 更新受理项目中的 证书类型与是否发证
     * @param jbxxid 基本信息ID
     * @param bdcSlXmDOList  不动产受理项目数据集合
     * @return 更新发证状态的单元号列表
     */
    @PatchMapping(path = "/realestate-accept/rest/v1.0/xm/fzzt/{jbxxid}")
    List<String> updateSlxmFzztPl(@PathVariable(name = "jbxxid") String jbxxid, @RequestBody List<BdcSlXmDO> bdcSlXmDOList, @RequestParam(name = "gzldyid") String gzldyid);

    /**
     * @param bdcGwcDeleteQO 购物车删除QO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 购物车页面删除已选项目
     */
    @DeleteMapping(path = "/realestate-accept/rest/v1.0/xm/yxxm")
    void deleteYxxm(@RequestBody BdcGwcDeleteQO bdcGwcDeleteQO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询项目房屋信息
     * @date : 2022/8/18 16:14
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/fwxx/page")
    Page<BdcSlFwxxDTO> listBdcSlFwxxByPageJson(Pageable pageable,
                                               @RequestParam(name = "bdcdyQO", required = false) String bdcdyQO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询已选单元信息的规划用途
     * @date : 2022/9/13 11:04
     */
    @GetMapping("/realestate-accept/rest/v1.0/xm/ghyt")
    List<String> listGwcGhytByJbxxid(@RequestParam(name = "jbxxid", required = true) String jbxxid);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 计算购物车的总面积
     * @date : 2022/9/13 11:09
     */
    @GetMapping("/realestate-accept/rest/v1.0/xm/jzmj")
    String queryGwcJzmjByJbxxid(@RequestParam(name = "jbxxid", required = true) String jbxxid, @RequestParam(name = "djxl", required = false) String djxl);

    @PostMapping("/realestate-accept/rest/v1.0/xm/gdxxhf")
    List<BdcXmGdxxDTO> listBdcGdxxHf(@RequestBody HashMap<String, Object> param);


    /**
     * 批量更新档案归属地信息
     * @param param
     * @return
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/list/batchUpdateDagsd")
    Integer batchUpdateBdcSlXmDagsd(@RequestBody BdcUpdateDagsdQo param);

    /**
     * @param bdcDjxxUpdateQO 更新对象
     * @return 更新数量
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description  批量更新项目
     */
    @PostMapping(path = "/realestate-accept/rest/v1.0/xm/list/jsonStr")
    Integer updateBatchBdcSlXm(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO);



}

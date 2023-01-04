package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmPzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxmPlxgDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxmSlDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxmsDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxmQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/12
 * @description 不动产受理收费项目rest服务
 */
public interface BdcSlSfxmRestService {
    /**
     * @param sfxmid 收费项目ID
     * @return 不动产受理收费项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费项目ID获取不动产受理收费项目
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxm/{sfxmid}")
    BdcSlSfxmDO queryBdcSlSfxmBySfxmid(@PathVariable(value = "sfxmid") String sfxmid);

    /**
     * @param sfxxid 收费信息ID
     * @return 不动产受理收费项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费信息ID获取不动产受理收费项目
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxm/list/{sfxxid}")
    List<BdcSlSfxmDO> listBdcSlSfxmBySfxxid(@PathVariable(value = "sfxxid") String sfxxid);

    /**
     * @param bdcSlSfxmPzDOList 不动产受理收费项目配置
     * @return 不动产受理收费项目
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 初始化不动产受理收费项目
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxm/csh/{sfxxid}")
    List<BdcSlSfxmDO> listCshBdcSlSfxm(@RequestBody List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList,@PathVariable(value = "sfxxid") String sfxxid);

    /**
     * @param bdcSlSfxmDO 不动产受理收费项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理收费项目
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxm/")
    BdcSlSfxmDO insertBdcSlSfxm(@RequestBody BdcSlSfxmDO bdcSlSfxmDO);

    /**
     * @param bdcSlSfxmDO 不动产受理收费项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理收费项目
     */
    @PutMapping("/realestate-accept/rest/v1.0/sfxm/")
    Integer updateBdcSlSfxm(@RequestBody BdcSlSfxmDO bdcSlSfxmDO);

    /**
     * @param bdcSlSfxmDO 不动产受理收费项目
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产受理收费项目
     */
    @PutMapping("/realestate-accept/rest/v1.0/sfxm/save")
    Integer saveOrUpdateBdcSlSfxm(@RequestBody BdcSlSfxmDO bdcSlSfxmDO);

    /**
     * @param sfxmid 收费项目ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费项目ID删除不动产受理收费项目
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sfxm/{sfxmid}")
    Integer deleteBdcSlSfxmBySfxmid(@PathVariable(value = "sfxmid") String sfxmid);

    /**
     * @param sfxxid 收费信息ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费信息ID删除不动产受理收费项目
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sfxm/list/{sfxxid}")
    Integer deleteBdcSlSfxmBySfxxid(@PathVariable(value = "sfxxid") String sfxxid);

    /**
     * @param processInsId 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 转发自动保存收费数据
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxm/save/zf")
    void saveSfDataForZf(@RequestParam("processInsId") String processInsId) throws Exception;

    /**
     * @param bdcSlSfxmsDTO 收费项目信息值
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 计算登记费
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxm/count/djf")
    BdcSlSfxmDO countDjf(@RequestBody BdcSlSfxmsDTO bdcSlSfxmsDTO);

    /**
     * @param bdcXmDOList 不动产项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取登记费数量（区分住宅和非住宅）
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxm/count/djf/sl")
    BdcSlSfxmSlDTO getSfxmDjfSl(@RequestBody List<BdcXmDO> bdcXmDOList);


    /**
     * @param bdcSlSfxmQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/11/30 11:31
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxm/fpcx")
    List<BdcSlSfxmDO> listFpcxSfxm(@RequestBody BdcSlSfxmQO bdcSlSfxmQO);

    /**
     * @param bdcXmDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 盐城请求查询登记费信息
     * @date : 2021/6/23 16:42
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxm/yydj/yc")
    BdcSlSfxmSlDTO queryYcDjfSl(@RequestBody List<BdcXmDO> bdcXmDOList);

    /**
     * 批量修改收费项目内容
     * <p>批量修改多个流程的收费项目信息</p>
     * @param bdcSlSfxmPlxgDTO 不动产收费项目批量修改信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxm/plxg")
    void plxgSfxm(@RequestBody BdcSlSfxmPlxgDTO bdcSlSfxmPlxgDTO);

    /**
     * 根据登记原因、sfxxid获取不动产受理收费项目
     * @param sfxxid
     * @param djyy
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxm/listSfxmBySfxxidAndDjyy")
    List<BdcSlSfxmDO> listBdcSlSfxmBySfxxidAndDjyy(@RequestParam(value = "sfxxid") String sfxxid, @RequestParam(value = "djyy") String djyy);

    /**
     * 批量修改收费项目信息
     * @param bdcSlSfxmDOList 收费项目集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxm/pl/sfxmxx")
    void batchUpdateBdcSlSfxm(@RequestBody List<BdcSlSfxmDO> bdcSlSfxmDOList);

    /**
     * 计算土地交易服务费信息
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxm/td/jyfwf")
    void countTdjyfwf(@RequestParam(value = "sfxmid") String sfxmid, @RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "xmid") String xmid);
}

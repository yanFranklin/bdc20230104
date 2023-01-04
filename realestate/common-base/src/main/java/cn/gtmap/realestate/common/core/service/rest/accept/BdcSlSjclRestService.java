package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcDsfSjclDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSjclDTO;
import cn.gtmap.realestate.common.core.dto.accept.SjclUploadDTO;
import cn.gtmap.realestate.common.core.dto.accept.SlymDchyDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSjclQO;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/12
 * @description 不动产受理收件材料rest服务
 */
public interface BdcSlSjclRestService {

    /**
     * @param sjclid 收件材料ID
     * @return 不动产受理收件材料
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收件材料ID获取不动产受理收件材料
     */
    @GetMapping("/realestate-accept/rest/v1.0/sjcl/{sjclid}")
    BdcSlSjclDO queryBdcSlSjclBySjclid(@PathVariable(value = "sjclid") String sjclid);


    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理收件材料
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 工作流实例ID获取不动产受理收件材料
     */
    @GetMapping("/realestate-accept/rest/v1.0/sjcl/list/pl/{gzlslid}")
    List<BdcSlSjclDO> listBdcSlSjclByGzlslid(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param clmc 材料名称
     * @param gzlslid 工作流实例ID
     * @return 不动产受理收件材料
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据材料名称获取不动产受理收件材料
     */
    @GetMapping("/realestate-accept/rest/v1.0/sjcl/list/clmc")
    List<BdcSlSjclDO> listBdcSlSjclByClmc(@RequestParam(value = "gzlslid",required = false) String gzlslid,@RequestParam(value = "clmc") String clmc);

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid    项目ID
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 更新不动产受理收件材料页数
     */
    @PostMapping("/realestate-accept/rest/v1.0/sjcl/ys")
    Integer updateSjclYs(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "xmid",required = false) String xmid);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新组合流程收件材料页数
     * @date : 2021/12/20 13:47
     */
    @PutMapping("/realestate-accept/rest/v1.0/sjcl/ys/zhlc")
    Integer updateZhlcSjclYs(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param bdcSlSjclDO 不动产受理收件材料
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理收件材料
     */
    @PostMapping("/realestate-accept/rest/v1.0/sjcl/")
    BdcSlSjclDO insertBdcSlSjcl(@RequestBody BdcSlSjclDO bdcSlSjclDO);

    /**
     * @param bdcSlSjclDO 不动产受理收件材料
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理收件材料
     */
    @PutMapping("/realestate-accept/rest/v1.0/sjcl/")
    Integer updateBdcSlSjcl(@RequestBody BdcSlSjclDO bdcSlSjclDO);

    /**
     * @param sjclid 收件材料ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收件材料ID删除不动产受理收件材料
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sjcl/{sjclid}")
    void deleteBdcSlSjclBySjclid(@PathVariable(value = "sjclid") String sjclid);

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id删除不动产受理收件材料
     */
    @GetMapping("/realestate-accept/rest/v1.0/sjcl/recreate/{gzlslid}")
    List<BdcSlSjclDO> reCreateSjcl(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param json
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新收件材料
     */
    @PostMapping("/realestate-accept/rest/v1.0/sjcl/list/")
    Integer updateBdcSlSjcl(@RequestBody String json);

    /**
     * @param sjclid，czlx
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 改变顺序号
     */
    @GetMapping("/realestate-accept/rest/v1.0/sjcl/sxh/{sjclid}/{czlx}")
    Integer changeSjclSxh(@PathVariable(value = "sjclid") String sjclid,@PathVariable(value = "czlx")  String czlx);

    /**
     * @param gzlslid
     * @param sjclIdsStr
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 获取收件材料名称
     */
    @GetMapping("/realestate-accept/rest/v1.0/sjcl/sjclmc")
    List<String> listSjclMc(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "sjclIdsStr", required = false) String sjclIdsStr);

    /**
     * @param gzlslid 工作流实例ID
     * @return 是否验证通过
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证收件材料份数页数是否为空
     */
    @GetMapping("/realestate-accept/rest/v1.0/sjcl/gzyz/ysfs/{gzlslid}")
    Boolean checkSjclYsFs(@PathVariable(value = "gzlslid")  String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @param bdcDsfSjclDTOList 第三方收件材料
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  保存第三方收件材料
     */
    @PostMapping("/realestate-accept/rest/v1.0/sjcl/dsf/{gzlslid}")
    void saveDsfSjcl(@PathVariable(value = "gzlslid")  String gzlslid,@RequestBody List<BdcDsfSjclDTO> bdcDsfSjclDTOList) throws IOException;

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id删除流程附件，包括大云附件
     * @date : 2020/5/12 9:49
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sjcl/all/{gzlslid}")
    void deleteAllSjcl(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param slbh 受理编号
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据受理编号查询不动产受理收件材料
     */
    @GetMapping("/realestate-accept/rest/v1.0/sjcl/listSjclBySlbh")
    List<BdcSlSjclDTO> listSjclBySlbh(@RequestParam(value = "slbh") String slbh);

    /**
     * @param bdcSlSjclQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询收件材料-查询条件包含等级小类
     * @date : 2021/3/25 14:47
     */
    @PostMapping("/realestate-accept/rest/v1.0/sjcl/djxl")
    List<BdcSlSjclDO> listBdcSlSjcl(@RequestBody BdcSlSjclQO bdcSlSjclQO);

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新收件材料
     */
    @PostMapping("/realestate-accept/rest/v1.0/sjcl/zhsjcl/")
    Integer updateZhSlSjcl(@RequestBody String json);


    /**
     * @param sjclUploadDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 上传附件材料，受理没有文件夹则新增
     * @date : 2021/7/5 10:16
     */
    @PostMapping("/realestate-accept/rest/v1.0/sjcl/xzsc")
    void createAndUploadFile(@RequestBody SjclUploadDTO sjclUploadDTO) throws IOException;

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 复制收件材料信息
     * @date : 2021/7/12 14:22
     */
    @PostMapping("/realestate-accept/rest/v1.0/sjcl/copy")
    void copySjcl(@RequestBody String sjclJson);

    /**
     * @param
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description 复制收件材料信息
     * @date : 2021/11/16 14:22
     */
    @PostMapping("/realestate-accept/rest/v1.0/sjcl/copyzh")
    void copyzhSjcl(@RequestBody String sjclJson);

    /**
     * @param gzlslid
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 更新是否批注
     * @return
     */
    @RequestMapping(value = "/realestate-accept/rest/v1.0/sjcl/updateSjclSfpz", method = RequestMethod.POST)
    Integer updateSjclSfpz(@RequestParam(value = "processInsId") String gzlslid);

    /**
     * @param  json 上传附件信息
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 上传一体化平台审批附件
     */
    @PostMapping("/realestate-accept/rest/v1.0/sjcl/ythspfj")
    void uploadYthSpfj(@RequestBody String json,@RequestParam(value = "processInsId") String gzlslid);

    /**
     * 推送附件信息进行云签
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @return 云签结果
     */
    @PostMapping("/realestate-accept/rest/v1.0/sjcl/tsyq")
    Object tsYqDzclxx(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例id
     * @return object
     * @throws IOException
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 从第三方获取获取委托信息文件保存到附件材料
     * @date : 2022/11/25
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/sjcl/download")
    Boolean downloadWtcl(@RequestParam("gzlslid") String gzlslid) throws IOException;

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取多测合一附件
     * @date : 2022/12/20 8:45
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/sjcl/dchy")
    void downDchyFj(@RequestBody SlymDchyDTO slymDchyDTO);
}

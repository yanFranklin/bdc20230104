package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcFileBase64DTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSqrDTO;
import cn.gtmap.realestate.common.core.dto.accept.CompareHyxxResultDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJtcyQO;
import cn.gtmap.realestate.common.core.qo.accept.CompareHyxxQO;
import cn.gtmap.realestate.common.core.qo.accept.GetJtcyxxQO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcJtcyCxYmxxVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 不动产受理家庭成员服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-06-26 16:59
 **/
public interface BdcSlJtcyRestService {


    /**
     * @param bdcSlJtcyDO 不动产受理家庭成员
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存不动产受理家庭成员
     */
    @PutMapping("/realestate-accept/rest/v1.0/jtcy/")
    Integer updateBdcSlJtcy(@RequestBody BdcSlJtcyDO bdcSlJtcyDO);

    /**
     * @param sqrid 申请人id
     * @return 不动产受理家庭成员集合
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据申请人id获取不动产受理家庭成员信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/jtcy/list/{sqrid}")
    List<BdcSlJtcyDO> listBdcSlJtcyBySqrid(@PathVariable(value = "sqrid") String sqrid);

    /**
     * @param jtcyid 家庭成员id
     * @return 不动产受理家庭成员
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据家庭成员id获取不动产受理家庭成员
     */
    @GetMapping("/realestate-accept/rest/v1.0/jtcy/{jtcyid}")
    BdcSlJtcyDO queryBdcSlJtcyByJtcyid(@PathVariable(value = "jtcyid") String jtcyid);

    /**
     * @param bdcSlJtcyDO 不动产受理家庭成员
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理家庭成员
     */
    @PostMapping("/realestate-accept/rest/v1.0/jtcy/")
    BdcSlJtcyDO insertBdcSlJtcy(@RequestBody BdcSlJtcyDO bdcSlJtcyDO);

    /**
     * @param jtcyid 家庭成员id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理家庭成员
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/jtcy/{jtcyid}")
    Integer deleteBdcSlJtcyByJtcyid(@PathVariable(value = "jtcyid") String jtcyid);

    /**
     * @param sqrid 申请人id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理家庭成员
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/jtcy/xm/{sqrid}")
    Integer deleteBdcSlJtcyBySqrid(@PathVariable(value = "sqrid") String sqrid);

    /**
     * @param zjh 证件号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据申请人ID集合和证件号批量删除不动产受理家庭成员
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/jtcy/jtcys")
    void deleteBatchBdcSlJtcy(@RequestBody List<String> sqridList,@RequestParam(value = "zjh") String zjh);

    /**
     * @param  getJtcyxxQO 接口入参
     * @return 家庭成员信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 婚姻/公安接口调用
     */
    @PostMapping("/realestate-accept/rest/v1.0/jtcy/getHygaxx")
    Object getHygaxx(@RequestBody GetJtcyxxQO getJtcyxxQO);

    /**
     * @param getJtcyxxQO 获取家庭成员接口入参
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 调用接口获取家庭成员信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jtcy/getJtcyxx")
    List<BdcSlJtcyDO> getJtcyxx(@RequestBody GetJtcyxxQO getJtcyxxQO);


    /**
     * @param sqrmc 申请人名称
     * @param zjh 证件号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 调用接口获取家庭成员信息（南通版本）
     * <p>南通版本区别：南通获取家庭成员信息接口，需先调用查询申请接口。调用成功后等待1分钟之后，在调用申请反馈接口进行查询。</p>
     */
    @GetMapping("/realestate-accept/rest/v1.0/jtcy/nt/getJtcyxx/{sqrmc}/{zjh}/{sqrid}")
    void getJtcyxxNt(@PathVariable(value = "sqrmc")String sqrmc,@PathVariable(value = "zjh")String zjh,@PathVariable(value = "sqrid")String sqrid);

    /**
     * @param xmid 项目ID
     * @param qlrlb 权利人类别
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 调用接口获取当前项目下所有申请人的家庭成员信息（南通版本）
     * <p>批量获取申请人家庭信息，通过XMID获取当前表单的所有申请人，
     * 在通过南通获取家庭成员信息接口，需先调用查询申请接口。调用成功后等待1分钟之后，在调用申请反馈接口进行查询。</p>
     */
    @GetMapping("/realestate-accept/rest/v1.0/jtcy/nt/getJtcyxxByXmid/{xmid}/{qlrlb}")
    void getJtcyxxByXmidNt(@PathVariable(value = "xmid")String xmid,@PathVariable(value = "qlrlb")String qlrlb);

    /**
     * @param bdcSlSqrDTO 不动产受理申请人DTO
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 同步家庭成员至其他户
     */
    @PostMapping("/realestate-accept/rest/v1.0/jtcy/syncJtcyxx")
    void syncJtcyxx(@RequestBody BdcSlSqrDTO bdcSlSqrDTO, @RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param bdcSlJtcyQO
     * @return 家庭成员信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询家庭成员信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jtcy/list")
    List<BdcSlJtcyDO> listBdcSlJtcy(@RequestBody  BdcSlJtcyQO bdcSlJtcyQO);

    /**
     * @param getJtcyxxQO 家庭成员查询参数
     * @return 提示信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 民政婚姻信息比对
     */
    @PostMapping("/realestate-accept/rest/v1.0/jtcy/compareHyxx")
    Map<String,String> compareHyxx(@RequestBody GetJtcyxxQO getJtcyxxQO);

    /**
     * @param compareHyxxQO
     * @return 提示信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 南通一体化民政婚姻子女信息比对
     */
    @PostMapping("/realestate-accept/rest/v1.0/jtcy/nantong/compareHyxx")
    CompareHyxxResultDTO compareHyznxx(@RequestBody CompareHyxxQO compareHyxxQO);

    /**
     * @param resultDTO 婚姻查询信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 导入婚姻比对信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jtcy/hybdxx")
    void drhybdxx(@RequestBody CompareHyxxResultDTO resultDTO);

    /**
     * 南通查询婚姻信息，并生成查询文件PDF
     * @param gzlslid 工作流实例ID
     * @param isFirstHand 是否一手房
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/jtcy/nantong/hyxx/{gzlslid}")
    void queryHyxxAndGenerateFile(@PathVariable(value = "gzlslid") String gzlslid,@RequestParam(value = "isFirstHand", required = false) boolean isFirstHand);

    /**
     * 南通查询户籍信息，并生成查询文件PDF
     * @param gzlslid 工作流实例ID
     * @param isFirstHand 是否一手房
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/jtcy/nantong/hjxx/{gzlslid}")
    void queryHjxxAndGenerateFile(@PathVariable(value = "gzlslid") String gzlslid,@RequestParam(value = "isFirstHand", required = false) boolean isFirstHand);

    /**
     * 上传家庭成员页面截图图片
     * @param bdcFileBase64DTO 文件base64DTO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/jtcy/nantong/upload/screenshot")
    void uploadScreenShot(@RequestBody BdcFileBase64DTO bdcFileBase64DTO);

    /**
     * 根据工作流实例ID，获取家庭成员查询页面信息
     * @param gzlslid 工作流实例ID
     * @return 不动产家庭成员查询页面信息VO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/jtcy/nantong/ymxx/{gzlslid}")
    BdcJtcyCxYmxxVO queryJtcyYmxx(@PathVariable(value="gzlslid") String gzlslid);

}

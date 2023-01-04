package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response.SdqHyResponseDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztResponseDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztUpdateHyDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqxxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/8
 * @description 查询不动产信息接口
 */
public interface BdcSdqghRestService {

    /**
     *@author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *@param bdcSdqghDO
     *@description 插入水电气过户实体
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/insertSdqgh")
    Integer insertSdqghDO(@RequestBody BdcSdqghDO bdcSdqghDO);

    /**
     * 通过传入参数返回不动产项目水电气业务实体集合
     * @author  <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param   bdcSdqywQO 水电气业务查询qo
     * @return  {List} 水电气业务实体集合
     * @description 通过传入参数返回水电气业务实体集合(有序)
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqghxx/list/order")
    List<BdcSdqghDO> querySdqywOrder(@RequestBody BdcSdqywQO bdcSdqywQO);

    /**
     * 通过传入参数返回不动产项目水电气业务实体集合
     * @author  <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param   bdcSdqywQO 水电气业务查询qo
     * @return  {List} 水电气业务实体集合
     * @description 通过传入参数返回水电气业务实体集合(无序)
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqghxx/list")
    List<BdcSdqghDO> querySdqyw(@RequestBody BdcSdqywQO bdcSdqywQO);


    /**
     * 水过户
     * @author  <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param   processInsId
     * @description 登簿成功后把水的业务过户
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/shuigh/{processInsId}/{isOneWebSource}")
    boolean shuigh(@PathVariable("processInsId") String processInsId,
                   @PathVariable("isOneWebSource") String isOneWebSource);

    /**
     * 电过户
     * @author  <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param   processInsId
     * @description 登簿成功后把电的业务过户
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/diangh/{processInsId}/{isOneWebSource}")
    boolean diangh(@PathVariable("processInsId") String processInsId,
                   @PathVariable("isOneWebSource") String isOneWebSource);

    /**
     * 气过户
     * @author  <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param   processInsId
     * @description 登簿成功后把气的业务过户
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/qigh/{processInsId}/{isOneWebSource}")
    boolean qigh(@PathVariable("processInsId") String processInsId,
                 @PathVariable("isOneWebSource") String isOneWebSource);

    /**
     * 气 查询是否满足过户条件
     * @return
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/qi")
    public Object getQiZt(@RequestBody BdcSdqywQO bdcSdqywQO);
    /**
     * 录办理的户号，和3.0件关联上
     * @return
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/saveData")
    Object saveData(@RequestBody BdcSdqywQO bdcSdqywQO);

    /**
     * 获取一窗打印的登记库的 水电气业务数据
     * @param processInsId
     * @return
     */
    @GetMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/SdqSqbYwDy/{processInsId}")
    List<Map> getSdqSqbYwDyData(@PathVariable("processInsId") String processInsId);


    /**
     *@author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *@param bdcSdqBlztRequestDTO
     *@description 更新水电气过户业务状态
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/updateSdqgh")
    BdcSdqBlztResponseDTO updateSdqBlzt(@RequestBody BdcSdqBlztRequestDTO bdcSdqBlztRequestDTO);

    /**
     *@author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     *@param bdcSdqBlztUpdateHyDTO
     *@description 更新水电气过户核验信息
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/updateSdqghhy")
    void updateSdqghhy(@RequestBody BdcSdqBlztUpdateHyDTO bdcSdqBlztUpdateHyDTO);

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 电过户核验，过户前查询是否可以过户
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/dianhy/{processInsId}")
    CommonResponse commonDianghhy(@PathVariable("processInsId") String processInsId);

    /**
      * @param processInsId 工作流实例ID
      * @param qlrxxsjly 权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人
      * @param appendSign 权利人拼接字符
      * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
      * @return 过户结果
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 电过户请求
      */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/diangh/{processInsId}")
    CommonResponse commonDiangh(@PathVariable("processInsId") String processInsId,
                                @RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,
                                @RequestParam(name = "appendSign", required = false) String appendSign,
                                @RequestParam(name = "fjxxsjly", required = false) Integer fjxxsjly);

    /**
     * @param processInsId 工作流实例ID
     * @param qlrxxsjly 权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人
     * @param appendSign 权利人拼接字符
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @return 过户结果
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 电过户请求,先查询后过户
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/diancxgh/{processInsId}")
    CommonResponse commonDianCxgh(@PathVariable("processInsId") String processInsId,
                                @RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,
                                @RequestParam(name = "appendSign", required = false) String appendSign,
                                @RequestParam(name = "fjxxsjly", required = false) Integer fjxxsjly);

    /**
     * @param processInsId 工作流实例ID
     * @param qlrxxsjly 权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人
     * @param appendSign 权利人拼接字符
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @return 过户结果
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 水过户请求,先查询后过户
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/shuicxgh/{processInsId}")
    CommonResponse commonShuiCxgh(@PathVariable("processInsId") String processInsId,
                                  @RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,
                                  @RequestParam(name = "appendSign", required = false) String appendSign,
                                  @RequestParam(name = "fjxxsjly", required = false) Integer fjxxsjly);

    /**
     * @param processInsId 工作流实例ID
     * @param qlrxxsjly 权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人
     * @param appendSign 权利人拼接字符
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @return 过户结果
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 气过户请求,先查询后过户
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/qicxgh/{processInsId}")
    CommonResponse commonQiCxgh(@PathVariable("processInsId") String processInsId,
                                  @RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,
                                  @RequestParam(name = "appendSign", required = false) String appendSign,
                                  @RequestParam(name = "fjxxsjly", required = false) Integer fjxxsjly);

    /**
     * @param processInsId 工作流实例ID
     * @param qlrxxsjly 权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人
     * @param appendSign 权利人拼接字符
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @return 过户结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 气过户请求
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/qigh/{processInsId}")
    CommonResponse commonQigh(@PathVariable("processInsId") String processInsId,
                              @RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,
                              @RequestParam(name = "appendSign", required = false) String appendSign,
                              @RequestParam(name = "fjxxsjly", required = false) Integer fjxxsjly);

    /**
     * @param processInsId 工作流实例ID
     * @return 过户结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 电过户附件推送
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/diangh/fj/{processInsId}")
    CommonResponse commonDianghFjcl(@PathVariable("processInsId") String processInsId);


    /**
     * @param processInsId 工作流实例ID
     * @return 过户结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 电过户请求(工作流事件,业务信息+附件推送)
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/diangh/workflow/{processInsId}")
    CommonResponse commonDianghWorkflow(@PathVariable("processInsId") String processInsId,@RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,@RequestParam(name = "appendSign", required = false) String appendSign);

    /**
      * @param gzlslid 工作流实例ID
      * @param needHz 是否需要户主，默认需要
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 保存水电气过户信息
      */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/save/{processInsId}")
    void saveSdq(@PathVariable("processInsId") String gzlslid,
                 @RequestBody List<BdcSdqghDO> bdcSdqghDOS,
                 @RequestParam(value = "needHz", required = false) Boolean needHz);

    /**
      * @param gzlslid 工作流实例ID
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 查询水电气信息
      */
    @GetMapping(path = "/realestate-inquiry/rest/v1.0/sdqxx/{processInsId}")
    BdcSdqxxDTO querySdqxx(@PathVariable("processInsId") String gzlslid);

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 电过户请求撤销
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/diangh/cancel/{processInsId}")
    CommonResponse commonDianghCancel(@PathVariable("processInsId") String processInsId);

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 气过户请求撤销
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/qigh/cancel/{processInsId}")
    CommonResponse commonQighCancel(@PathVariable("processInsId") String processInsId);

    /**
      * @param processInsId 工作流实例ID
      * @param consNo 户号
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 过户申请前查询状态，是否欠费
      */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/dianzt/{processInsId}/{consNo}")
    CommonResponse commonDianZt(@PathVariable("processInsId") String processInsId,@PathVariable("consNo") String consNo);

    /**
     * @param processInsId 工作流实例ID
     * @param consNo 户号
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 过户办理状态查询
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/dian/ghblzt/{processInsId}/{consNo}")
    CommonResponse commonDianGhblzt(@PathVariable("processInsId") String processInsId,@PathVariable("consNo") String consNo);


    /**
     * @param processInsId 工作流实例ID
     * @param qlrxxsjly 权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人
     * @param appendSign 权利人拼接字符
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @return 过户结果
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 水过户请求
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/shuigh/{processInsId}")
    CommonResponse commonShuigh(@PathVariable("processInsId") String processInsId,
                                @RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,
                                @RequestParam(name = "appendSign", required = false) String appendSign,
                                @RequestParam(name = "fjxxsjly", required = false) Integer fjxxsjly);

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 水过户请求撤销
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/sdqgh/shuigh/cancel/{processInsId}")
    CommonResponse commonShuighCancel(@PathVariable("processInsId") String processInsId);

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //舒城水过户
     * @Date 2022/5/26 18:38
     **/
    @PostMapping(path = "/realestate-inquiry/sdqgh/shui/tsgh/shucheng/{processInsId}")
    CommonResponse shuchengShuigh(@PathVariable("processInsId") String processInsId);


    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //舒城水过户登记簿数据
     * @Date 2022/5/26 18:38
     **/
    @PostMapping(path = "/realestate-inquiry/sdqgh/shui/query/shucheng/djbData/{processInsId}")
    CommonResponse<String> shuchengShuiDjbData(@PathVariable(name = "processInsId") String processInsId);

}

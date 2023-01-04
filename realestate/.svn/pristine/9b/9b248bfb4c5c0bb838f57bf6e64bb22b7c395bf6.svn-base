package cn.gtmap.realestate.exchange.service.inf.yzw;

import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.exchange.sjpt.BdcExchangeZddz;
import cn.gtmap.realestate.common.core.domain.exchange.yzw.*;
import cn.gtmap.realestate.exchange.core.dto.yzw.YzwCheckAndTsResultDTO;
import cn.gtmap.realestate.exchange.core.qo.GxYzwQO;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019-09-05
 * @description 一张网相关服务
 */
public interface YzwService {

    /**
     * 共享一张网数据
     * @param gzlslid  工作流实例id
     * @param isbj  是否办结
     */
    void shareYzwData(String  gzlslid,boolean isbj);


    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param xmid
     * @return
     * @description 获取InfApply表
     */
    InfApplyDO getInfApplyData(ProcessInstanceData processInstance,@NotBlank(message = "参数不能为空") String xmid,Map<String, List<BdcExchangeZddz>> map);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param infApplyDO
     * @param gzlslid
     * @param bh
     * @return
     * @description 获取流程过程数据插入到InfApplyProcess表
     */
    List<InfApplyProcessDO> getInfApplyProcessData(List<TaskData> list, String gzlslid, String bh, InfApplyDO infApplyDO);


    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param xmid
     * @param gzlslid
     * @param bh
     * @param no
     * @return
     * @description  获取收件数据插入到InfApplyMaterial表
     */
    List<InfApplyMaterialDO> getInfApplyMaterialData(String xmid, String gzlslid, String bh,String no,String djjgdm);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param gzlslid
     * @param bh
     * @param infApplyDO
     * @return
     * @description  获取流程结果数据插入到InfApplyResult表
     */
    List<InfApplyResultDO> getInfApplyResultData(List<TaskData> listLstTask,ProcessInstanceData processInstance, String gzlslid, String bh, InfApplyDO infApplyDO);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param xmid
     * @param gzlslid
     * @param bh
     *  @param infApplyDO
     * @return
     * @description 获取结果证照信息
     */
    List<InfApplyJgzzDO> getInfApplyJgzzData(String xmid, String gzlslid, String bh,InfApplyDO infApplyDO,Map<String, List<BdcExchangeZddz>> map);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param xmid
     * @param gzlslid
     * @param bh
     * @return
     * @description 获取EMS物流信息
     */
    List<InfApplyWlxxDO> getInfApplyWlxxData(String xmid, String gzlslid, String bh);

    /**
     * @param yzwbh 一张网编号
     * @param yzwbhname 一张网字段名称
     * @return 一张网信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据一张网编号获取共享一张网信息
     */
    <T> List<T> listGxYzwDataByYzwbh(String yzwbh,Class clazz,String yzwbhname);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询一张网推送信息
     */
    Page<Map> listYzwTsxxByPageJson(Pageable pageable, GxYzwQO gxYzwQO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询一张网推送信息
     */
    List<Map> listYzwTsxx( GxYzwQO gxYzwQO);

    /**
     *
     * @param gxYzwQO
     * @return 统计信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     */
    List<Map> tjTszt(GxYzwQO gxYzwQO);

    /**
     * @param isDelete 是否删除原有记录
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存一张网验证推送结果数据
     */
    void saveYzwCheckAndTsResultDTO(List<YzwCheckAndTsResultDTO> yzwCheckAndTsResultDTOS,boolean isDelete);

    /**
     * @param tsxxid 推送信息ID
     * @return 验证信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询一张网验证明细信息
     */
    List<Map> listGxYzwYzmxxxByTsxxid(String tsxxid);

    /**
     * 删除时，推送inf_apply_result表
     * @param gzlslid
     * @Date 2021/2/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    void deleteTsResult(String processInsId);
}

package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQlrXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxPlDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcCfjgQO;
import cn.gtmap.realestate.common.core.qo.register.BdcFdcq3QO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/12
 * @description 不动产冗余字段处理服务接口定义
 */
public interface BdcRyzdRestService {
    /**
     * @param xmid 项目ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据项目ID关联处理不动产项目中存在的冗余字段
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/ryzd/{xmid}", method = RequestMethod.PUT)
    void updateRyzd(@PathVariable(name = "xmid") String xmid);

    /**
     * @param processInsId 流程实例ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 处理流程实例ID关联的不动产项目中的冗余字段
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/ryzd", method = RequestMethod.PUT)
    void updateRyzdWithProcessInstId(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param bdcqzhMap 各项目生成的证书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新流程对应项目、权利人证号冗余字段
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/ryzd/bdcqzh", method = RequestMethod.PUT)
    void updateRyzdBdcqzh(@RequestBody Map<String, List<BdcBdcqzhDTO>> bdcqzhMap);

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 更新权利人冗余字段
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/ryzd/qlr", method = RequestMethod.PUT)
    void updateRyzdQlrWithProcessInstId(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新项目的共有情况
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/ryzd/gyqk", method = RequestMethod.PUT)
    void updateGyqk(@RequestParam(name = "xmid") String xmid);

    @RequestMapping(value = "/realestate-register/rest/v1.0/ryzd/gyqk/{gzlslid}", method = RequestMethod.PUT)
    void updateGyqkWithGzlslid(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid    可以依据更新的xmid
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 首次登记，批量更新唯一权利人等值
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/ryzd/plwy", method = RequestMethod.GET)
    void updateRyzdPl(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid") String xmid);

    /**
     * @param bdcQlrXmDTOList 权利人项目DTO对象List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/ryzd/qlrXm", method = RequestMethod.POST)
    void updateRyzdQlrXm(@RequestBody List<BdcQlrXmDTO> bdcQlrXmDTOList);
    /**
     * @param bdcCfjgQO 查封机关QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新查封机关或解封机关
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/cfjg", method = RequestMethod.POST)
    void updateCfjgOrJfjg(@RequestBody BdcCfjgQO bdcCfjgQO);

    /**
     * @param bdcFdcq3QO 建筑物所有权及业主共有信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 建筑物所有权及业主共有信息权利人字段
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/ryzd/fdcq3gyxx")
    void updateBdcFdcq3Qlr(@RequestBody BdcFdcq3QO bdcFdcq3QO);

    /**
      * @param xmid 项目ID
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 更新项目的使用期限
      */
    @PutMapping(value = "/realestate-register/rest/v1.0/ryzd/syqx")
    void updateSyqx(@RequestParam(name = "xmid") String xmid);

    /**
     * @param bdcSyqxPlDTO 使用期限更新实体
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新项目的使用期限(批量)
     */
    @PutMapping(value = "/realestate-register/rest/v1.0/ryzd/syqx/pl")
    void updateSyqxPl(@RequestBody BdcSyqxPlDTO bdcSyqxPlDTO) throws Exception;
}

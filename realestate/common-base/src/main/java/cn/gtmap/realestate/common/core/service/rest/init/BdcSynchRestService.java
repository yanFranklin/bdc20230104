package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.dto.init.BdcQjtbxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcSjdzDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/5/6.
 * @description 业务数据同步接口
 */
public interface BdcSynchRestService {

    /**
     * 同步更新业务数据的原证号字段
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param gzlslid 工作流实例ID
     *@return
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/synch/yzh/{gzlslid}")
    void synchYzh(@PathVariable(name = "gzlslid") String gzlslid) throws Exception;


    /**
     * 流程同步权籍数据
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param gzlslid 工作流实例ID
     *@return
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/synch/lpb/data/{gzlslid}")
    void synchLpbDataToLc(@PathVariable(name = "gzlslid") String gzlslid) throws Exception;

    /**
     * 项目同步权籍数据
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param xmid 项目ID
     *@return
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/synch/lpb/data/toxm/{xmid}")
    void synchLpbDataToXm(@PathVariable(name = "xmid") String xmid) throws Exception;

    /**
     * 项目同步权籍数据后的数据
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param xmid 项目ID
     *@return
     *@description
     */
    @GetMapping(value = "/init/rest/v1.0/query/synch/lpb/data/{xmid}")
    BdcYwxxDTO querySynchLpbData(@PathVariable(name = "xmid") String xmid) throws Exception;

    /**
     * 项目同步权籍数据对照信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param xmid 项目ID
     *@return
     *@description
     */
    @GetMapping(value = "/init/rest/v1.0/query/synch/lpb/dzxx/data/{xmid}")
    List<BdcQjtbxxDTO> queryLpbDataDzxx(@PathVariable(name = "xmid") String xmid) throws Exception;
    /**
     * 不动产、权籍、原项目数据对照信息
     *@author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param xmid 项目ID
     *@return
     *@description
     */
    @GetMapping(value = "/init/rest/v1.0/query/synch/lpbyxm/dzxx/data/{xmid}")
    List<BdcSjdzDTO> queryLpbAndYxmDataDzxx(@PathVariable(name = "xmid") String xmid) throws Exception;

    /**
     * 根据前台传递的对照信息进行同步
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param xmid 项目ID
     * @param list 权籍同步对照信息
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/synch/lpb/data/dzxx/toxm/{xmid}")
    void synchLpbDataToXm(@RequestBody List<BdcQjtbxxDTO> list, @PathVariable(name = "xmid") String xmid) throws Exception;


    /**
     * 同步部分解押和解封的受理编号到附记
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param gzlslid 工作流实例ID
     *@return
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/synch/bfjfjy/slbh/tofj/{gzlslid}")
    void synchBfjfjySlbhToFj(@PathVariable(name = "gzlslid") String gzlslid) throws Exception;
}

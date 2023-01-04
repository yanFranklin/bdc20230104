package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description 打印数据
 */
public interface BdcPrintDataRestService {

    /**
     * 通过传入项目id集合以及工作流实例id去查询对应打印对象
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param 通过传入项目id集合以及工作流实例id去查询对应打印对象
     *@return
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/query/fdcqDzFwqd/dysj/{gzlslid}")
    List<BdcDysjDTO> queryFdcqDzFwqdDysj(@RequestBody(required = false) List<String> xmids,@PathVariable("gzlslid") String gzlslid,@RequestParam(value = "zsid",required = false)String zsid,
                                         @RequestParam(value = "hfwqd", required = false) boolean hfwqd) throws Exception;

}

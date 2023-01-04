package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcFsPzDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a herf="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0, 2022/08/03
 * @description 南通非税配置
 */
public interface BdcFsPzRestService {

    /**
     * 更新非税配置
     *
     * @param bdcFsPzDO
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/fspz/update")
    int updateFspz(@RequestBody BdcFsPzDO bdcFsPzDO);

    /**
     * 获取非税配置
     *
     * @param fspzid 主键
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/fspz/query")
    BdcFsPzDO queryFspz(@RequestParam(name = "fspzid", required = false) String fspzid, @RequestParam(name = "userid", required = false) String userid);

    /**
     * 删除非税配置
     *
     * @param fspzidList id集合
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     */
    @DeleteMapping(value = "/realestate-inquiry/rest/v1.0/fspz/delete")
    void deleteFspz(@RequestParam(name = "fspzidList") List<String> fspzidList);


    /**
     * @param fsPzDOList
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 批量保存非税配置
     * @date : 2022/8/4
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/fspz/pl")
    int saveFspzPl(@RequestBody List<BdcFsPzDO> fsPzDOList);


}

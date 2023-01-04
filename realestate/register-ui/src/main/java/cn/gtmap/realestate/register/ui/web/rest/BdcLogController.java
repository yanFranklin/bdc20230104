package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.config.logaop.DbLogUtils;
import cn.gtmap.realestate.common.core.dto.register.BdcZszmPrintDTO;
import cn.gtmap.realestate.common.core.dto.register.DbLogDTO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcLogFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/9/28
 * @description register 日志记录Controller
 */
@RestController
@RequestMapping("/rest/v1.0/log")
public class BdcLogController {
    @Autowired
    DbLogUtils dbLogUtils;

    @Autowired
    BdcLogFeignService bdcLogFeignService;

    /**
     * 保存打印数据源
     *
     * @return
     */
    @PostMapping(value = "/dbxx")
    public void saveDbLog(DbLogDTO dbLogDTO) {
        dbLogUtils.saveDbLog(dbLogDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZszmPrintDTO 打印信息
     * @description  保存打印的证书、证明、证明单数量信息
     */
    @PostMapping("/zszm/print")
    public boolean saveBdcZszmPrintInfo(@RequestBody BdcZszmPrintDTO bdcZszmPrintDTO){
        if(null == bdcZszmPrintDTO || null == bdcZszmPrintDTO.getZslx()){
            return false;
        }
        return bdcLogFeignService.saveBdcZszmPrintInfo(bdcZszmPrintDTO);
    }

    /**
     * 同步Redis中的证书证明打印数据量信息至数据库中
     * <p>临时接口，手动触发接口，后续可删除</p>
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/zszm/print/sync")
    public void syncZszmPrintCountToDB(){
        this.bdcLogFeignService.syncZszmPrintCountToDB();
    }
}

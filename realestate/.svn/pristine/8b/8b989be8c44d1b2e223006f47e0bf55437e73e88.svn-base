package cn.gtmap.realestate.register.ui.web.rest.xxbl;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.enums.LogEventEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXxblLogFeignService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbDetailVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcXxblLogVO;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 信息补录日志相关控制器
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 9:38 下午 2020/3/18
 */
@RestController
@RequestMapping("/rest/v1.0/blxx/log")
public class BdcBlLogController extends BaseController {

    /**
     * 信息补录日志服务
     */
    @Autowired
    private BdcXxblLogFeignService bdcXxblLogFeignService;

    /**
     * 获取当前用户的操作日志信息
     *
     * @param pageable 分页数据
     * @param xmid     项目id
     * @return 返回用户操作日志信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping
    public Object listUserLog(@LayuiPageable Pageable pageable, @RequestParam(value = "xmid") String xmid) {
        Page<BdcXxblLogVO> logList = bdcXxblLogFeignService.listLog(pageable, xmid, LogEventEnum.XXBL);
        return super.addLayUiCode(logList);
    }

    /**
     * 选择 ID 进行回退操作
     *
     * @param id 回退选择对应的日志 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/{id}")
    public void backLog(@PathVariable(value = "id") String id) throws Exception {
        if (StringUtils.isBlank(id)) {
            throw new MissingArgumentException("回溯日志缺少参数日志ID");
        }
        bdcXxblLogFeignService.backLog(id);
    }

    /**
     * 信息对比
     * 根据当前项目的 processInsId 获取日志中存储的初始化是的备份信息
     *
     * @param processInsId 修改流程的 processInsId
     */
    @GetMapping("/xxdb")
    public List<BdcDbVO> xxdb(@RequestParam("processInsId") String processInsId) throws Exception {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("对比数据缺少 processInsId");
        }
        return bdcXxblLogFeignService.xxdb(processInsId);
    }


    /**
     * 信息对比
     * 根据当前项目的 processInsId 获取日志中存储的初始化是的备份信息
     * 查询页面高亮显示内容
     *
     * @param processInsId 修改流程的 processInsId
     */
    @GetMapping("/dbgl")
    public List<BdcDbDetailVO> queryGl(@RequestParam("processInsId") String processInsId) throws Exception {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("对比数据缺少 processInsId");
        }
        return bdcXxblLogFeignService.queryGlxx(processInsId);
    }

    /**
     * 获取信息补录修改记录对比
     *
     * @param processInsId 工作流定义ID
     */
    @GetMapping("/getSjdb")
    public Object getSjdb(@RequestParam("processInsId") String processInsId,@RequestParam(value = "xmid",required = false) String xmid){
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("缺失参数processInsId");
        }
        return bdcXxblLogFeignService.getSjdb(processInsId,xmid);
    }
}

package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcDlrDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcDlrQO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcDlrRestService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcDlrVO;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.init.core.service.BdcDlrService;
import cn.gtmap.realestate.init.web.BaseController;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: realestate
 * @description: 代理人restControlller
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-21 11:17
 **/
@RestController
@Api(tags = "不动产代理人信息服务接口")
public class BdcDlrRestController extends BaseController implements BdcDlrRestService {

    @Autowired
    BdcDlrService bdcDlrService;

    /**
     * @param bdcDlrQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询代理人服务
     * @date : 2022/3/21 11:20
     */
    @Override
    public List<BdcDlrDO> listBdcDlr(@RequestBody BdcDlrQO bdcDlrQO) {
        if (StringToolUtils.isAllNullOrEmpty(bdcDlrQO.getDlrid(), bdcDlrQO.getXmid(), bdcDlrQO.getQlrid())) {
            throw new AppException("查询代理人信息缺失必要参数");
        }
        return bdcDlrService.listBdcDlr(bdcDlrQO);
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增保存代理人服务
     * @date : 2022/3/21 11:22
     */
    @Override
    public List<BdcDlrDO> insertBatchDlr(@RequestBody String json, @RequestParam("gzlslid") String gzlslid,
                                         @RequestParam("djxl") String djxl) {
        return bdcDlrService.updateBatchDlr(json, gzlslid, djxl);
    }

    /**
     * @param bdcDlrDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新代理人
     * @date : 2022/3/21 16:20
     */
    @Override
    public List<BdcDlrDO> updateBatchDlr(@RequestBody String json, @RequestParam("gzlslid") String gzlslid,
                                         @RequestParam("djxl") String djxl) {
        return null;
    }

    /**
     * @param gzlslid
     * @param dlrmc
     * @param dlrzjh
     * @param djxl
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据代理人信息删除代理人
     * @date : 2022/3/21 11:24
     */
    @Override
    public int deleteDlr(@RequestBody String json) {
        if (StringUtils.isNotBlank(json)) {
            BdcDlrVO bdcDlrVO = JSON.parseObject(json, BdcDlrVO.class);
            return bdcDlrService.deleteBatchDlr(bdcDlrVO.getDlrmc(), bdcDlrVO.getDlrzjh(), bdcDlrVO.getGzlslid(), bdcDlrVO.getDjxl(), bdcDlrVO.getQlridList());
        }
        return 0;
    }

    /**
     * @param dlrid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据代理人id删除代理人
     * @date : 2022/3/22 15:58
     */
    @Override
    public int deleteDlrById(@PathVariable(value = "dlrid") String dlrid) {
        return bdcDlrService.deleteBdcDlrByDlrid(dlrid);
    }
}

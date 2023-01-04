package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlShxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYwlzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwlzDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlShxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 受理业务流转ui控制层
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-22 16:50
 **/
@RestController
@RequestMapping("/slym/ywlz")
public class BdcSlYwlzController extends BaseController {

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlYwlzFeignService bdcSlYwlzFeignService;
    @Autowired
    BdcSlEntityFeignService bdcSlEntityFeignService;
    @Autowired
    BdcSlShxxFeignService bdcSlShxxFeignService;

    @Autowired
    WorkFlowUtils workFlowUtils;
    /**
     * 签名图片地址
     */
    @Value("${url.sign.image:}")
    protected String signImageUrl;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 页面展现数据
     * @date : 2021/9/22 16:51
     */
    @GetMapping
    public Object queryYwlzxx(String gzlslid) {
        BdcSlYwlzDTO bdcSlYwlzDTO = new BdcSlYwlzDTO();
        if (StringUtils.isNotBlank(gzlslid)) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
            bdcSlYwlzDTO.setBdcSlJbxxDO(bdcSlJbxxDO);
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                bdcSlYwlzDTO.setBdcSlXmDO(bdcSlXmDOList.get(0));
            }
            BdcSlYwlzDO bdcSlYwlzQO = new BdcSlYwlzDO();
            bdcSlYwlzQO.setGzlslid(gzlslid);
            List<BdcSlYwlzDO> bdcSlYwlzDOList = bdcSlYwlzFeignService.listBdcSlYwlz(bdcSlYwlzQO);
            if (CollectionUtils.isNotEmpty(bdcSlYwlzDOList)) {
                bdcSlYwlzDTO.setBdcSlYwlzDO(bdcSlYwlzDOList.get(0));
            }
            BdcSlShxxQO bdcShxxQO = new BdcSlShxxQO();
            bdcShxxQO.setGzlslid(gzlslid);
            bdcShxxQO.setJdmc("受理");
            bdcSlYwlzDTO.setSlShxxVO(queryShxxVO(bdcShxxQO));
            bdcShxxQO.setJdmc("公证");
            bdcSlYwlzDTO.setGzShxxVO(queryShxxVO(bdcShxxQO));
        }
        return bdcSlYwlzDTO;
    }


    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存业务流转信息
     * @date : 2021/9/22 17:01
     */
    @PostMapping
    public void saveOrUpdateYwlz(@RequestBody String json) {
        if (StringUtils.isNotBlank(json)) {
            bdcSlEntityFeignService.updateByJsonEntity(json, BdcSlYwlzDO.class.getName());
        }
    }

    private BdcShxxVO queryShxxVO(BdcSlShxxQO bdcShxxQO) {
        List<BdcSlShxxDO> bdcSlShxxDOList = bdcSlShxxFeignService.queryBdcShxx(bdcShxxQO);
        if (CollectionUtils.isNotEmpty(bdcSlShxxDOList)) {
            BdcShxxVO bdcShxxVO = new BdcShxxVO();
            bdcShxxVO.setQmtpdz(signImageUrl + bdcSlShxxDOList.get(0).getQmid());
            bdcShxxVO.setQmsj(DateUtils.format(bdcSlShxxDOList.get(0).getQmsj(), "yyyy年MM月dd日"));
            return bdcShxxVO;
        }
        return new BdcShxxVO();
    }


    @GetMapping("/jdmc")
    public Object queryTaskData(String taskid) {
        if (StringUtils.isNotBlank(taskid)) {
            return workFlowUtils.getTaskById(taskid);
        }
        return null;
    }
}

package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcDlrDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcDlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcDlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcDlrVO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: realestate
 * @description: 受理页面代理人相关查询信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-21 15:59
 **/
@RestController
@RequestMapping("/slym/dlr")
public class SlymDlrController extends BaseController {
    @Autowired
    BdcDlrFeignService bdcDlrFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量保存代理人
     * @date : 2022/3/21 16:09
     */
    @PostMapping()
    public Object saveBatchDlr(@RequestBody String json, String gzlslid, String djxl) {
        if (StringUtils.isBlank(json)) {
            throw new AppException("更新代理人缺失必要参数");
        }
        return bdcDlrFeignService.insertBatchDlr(json, gzlslid, djxl);
    }

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除领证人
     * @date : 2022/3/22 13:51
     */

    @DeleteMapping("/pllc")
    public int delBatchDlr(String dlrid, String gzlslid, String djxl, String qlrlb) {
        if (StringUtils.isNotBlank(dlrid)) {
            BdcDlrQO bdcDlrQO = new BdcDlrQO();
            bdcDlrQO.setDlrid(dlrid);
            List<BdcDlrDO> bdcDlrDOList = bdcDlrFeignService.listBdcDlr(bdcDlrQO);
            if (CollectionUtils.isNotEmpty(bdcDlrDOList) && StringUtils.isNotBlank(gzlslid)) {
                //有代理人后根据代理人的qlrid 找到所有相同的权利人的信息
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setQlrid(bdcDlrDOList.get(0).getQlrid());
                List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    //查询当前流程所有的权利人
                    List<BdcQlrDO> bdcQlrDOS = bdcQlrFeignService.listAllBdcQlr(gzlslid, qlrlb, djxl);
                    List<String> qlridList = new ArrayList<>(1);
                    if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
                        for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                            if (StringUtils.equals(bdcQlrDO.getQlrmc(), bdcQlrDOList.get(0).getQlrmc()) && StringUtils.equals(bdcQlrDO.getZjh(), bdcQlrDOList.get(0).getZjh())) {
                                qlridList.add(bdcQlrDO.getQlrid());
                            }
                        }
                    }
                    BdcDlrVO bdcDlrVO = new BdcDlrVO();
                    bdcDlrVO.setDjxl(djxl);
                    bdcDlrVO.setDlrmc(bdcDlrDOList.get(0).getDlrmc());
                    bdcDlrVO.setDlrzjh(bdcDlrDOList.get(0).getZjh());
                    bdcDlrVO.setGzlslid(gzlslid);
                    bdcDlrVO.setQlridList(qlridList);
                    return bdcDlrFeignService.deleteDlr(JSON.toJSONString(bdcDlrVO));
                }
            }
        }
        return 0;
    }

    /**
     * @param dlrid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 单个/组合流程删除代理人
     * @date : 2022/3/23 13:37
     */
    @DeleteMapping("/zhlc")
    public int deleteBdcDlrById(String dlrid) {
        if (StringUtils.isBlank(dlrid)) {
            return 0;
        }
        return bdcDlrFeignService.deleteDlrById(dlrid);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除权利人时删除代理人
     * @date : 2022/3/23 13:35
     */

    @DeleteMapping("/delqlr")
    public void deleteDlrByQlr(String gzlslid, String djxl, @RequestBody(required = false) List<String> qlridList) {
        if (StringUtils.isNotBlank(gzlslid) && CollectionUtils.isNotEmpty(qlridList)) {
            BdcDlrVO bdcDlrVO = new BdcDlrVO();
            bdcDlrVO.setGzlslid(gzlslid);
            bdcDlrVO.setQlridList(qlridList);
            bdcDlrVO.setDjxl(djxl);
            bdcDlrFeignService.deleteDlr(JSON.toJSONString(bdcDlrVO));
        }
    }
}

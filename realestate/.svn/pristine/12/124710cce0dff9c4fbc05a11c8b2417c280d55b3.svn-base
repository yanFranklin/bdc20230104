package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcWtsjDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcWtsjFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/11
 * @description 问题数据
 */
@RestController
@RequestMapping(value = "/rest/v1.0/wtsj")
public class BdcWtsjController {

    @Autowired
    BdcWtsjFeignService bdcWtsjFeignService;

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 标记问题数据
      */
    @PostMapping("/addWtsj")
    public void sdBdcdy(@RequestBody List<BdcWtsjDO> bdcWtsjDOList) {
        if (CollectionUtils.isEmpty(bdcWtsjDOList)) {
            throw new AppException("添加问题时未获取到问题数据");
        }
        bdcWtsjFeignService.addWtBdcdy(bdcWtsjDOList);
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 查询问题数据状态
      */
    @PostMapping("/wtsjzt")
    public Object queryBdcdySdzt(@RequestBody BdcWtsjDO bdcWtsjDO) {
        if (bdcWtsjDO == null || StringUtils.isBlank(bdcWtsjDO.getWtsj())) {
            throw new MissingArgumentException("wtsj");
        }
        List<BdcWtsjDO> bdcWtsjDOList = bdcWtsjFeignService.queryBdcWtsj(bdcWtsjDO);
        if (CollectionUtils.isNotEmpty(bdcWtsjDOList)) {
            return bdcWtsjDOList.get(0);
        }
        return new BdcWtsjDO();
    }


    /**
      * @param bdcWtsjDOList 问题数据
      * @param jjfa 解决方案
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 解决问题数据
      */
    @PostMapping("/jj")
    public void jjWtsj(@RequestBody List<BdcWtsjDO> bdcWtsjDOList,
                        String jjfa) {
        if (CollectionUtils.isEmpty(bdcWtsjDOList)) {
            throw new AppException("解决问题时未获取到问题数据");
        }
        if (StringUtils.isBlank(jjfa)){
            throw new AppException("解决方案不能为空");
        }
        List<BdcWtsjDO> wtsjDOList = new ArrayList<>();
        for (BdcWtsjDO bdcWtsjDO : bdcWtsjDOList){
            if(StringUtils.isBlank(bdcWtsjDO.getWtsj()) ||StringUtils.isBlank(bdcWtsjDO.getWtsjid())){
                throw new AppException("问题数据或者问题数据主键不能为空");
            }
            BdcWtsjDO queryWtsj = new BdcWtsjDO();
            queryWtsj.setWtsjzt(CommonConstantUtils.WTSJZT_CZWT);
            queryWtsj.setWtsj(bdcWtsjDO.getWtsj());
            List<BdcWtsjDO> queryBdcWtsj = bdcWtsjFeignService.queryBdcWtsj(queryWtsj);
            if (CollectionUtils.isNotEmpty(queryBdcWtsj)){
                wtsjDOList.add(bdcWtsjDO);
            }
        }
        // 当存在问题数据时 解决问题数据
        if (CollectionUtils.isNotEmpty(wtsjDOList)) {
            bdcWtsjFeignService.jjBdcWtsj(wtsjDOList, jjfa);
        }
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 保存问题数据
      */
    @PostMapping("/save")
    public void jjWtsj(@RequestBody BdcWtsjDO bdcWtsjDO){
        if(StringUtils.isBlank(bdcWtsjDO.getWtsjid())){
            throw new AppException("保存问题数据缺失主键");
        }
        bdcWtsjFeignService.updateWtsj(bdcWtsjDO);

    }
}

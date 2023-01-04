package cn.gtmap.realestate.supervise.web;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhDTO;
import cn.gtmap.realestate.supervise.core.qo.LfYzhQO;
import cn.gtmap.realestate.supervise.core.dto.LfYzhtjDTO;
import cn.gtmap.realestate.supervise.service.LfZssyjgService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/25
 * @description 廉防5：证书使用监管服务类
 */
@RestController
@RequestMapping("/rest/v1.0/zssyjg")
public class LfZssyjgRestController extends BaseController{
    @Autowired
    private LfZssyjgService zssyjgService;


    /**
     * 查询证书印制号信息
     * @param pageable 分页对象
     * @param lfYzhQO 查询条件
     * @return 证书印制号信息
     */
    @GetMapping("/yzh")
    public Object listBdcZsyzh(@LayuiPageable Pageable pageable, LfYzhQO lfYzhQO){
        Page<BdcYzhDO> bdcYzhDOPage = zssyjgService.listBdcZsyzh(pageable, lfYzhQO);
        return super.addLayUiCode(bdcYzhDOPage);
    }

    /**
     * 查询证书印制号信息
     * @param yzhid 印制号ID
     * @return 证书印制号信息
     */
    @GetMapping("/yzh/{yzhid}/symx")
    public List<BdcYzhsymxDO> listBdcZsyzhSymx(@PathVariable(value = "yzhid") String yzhid) {
        BdcYzhDTO bdcYzhDTO = zssyjgService.queryBdcYzh(yzhid);
        if (null == bdcYzhDTO) {
            return Collections.emptyList();
        }

        List<BdcYzhsymxDO> bdcYzhsymxDOList = bdcYzhDTO.getBdcYzhsymxDOList();
        if (CollectionUtils.isNotEmpty(bdcYzhsymxDOList)) {
            bdcYzhsymxDOList.sort(Comparator.comparing(BdcYzhsymxDO::getSysj));
            Collections.reverse(bdcYzhsymxDOList);
        }
        return bdcYzhsymxDOList;
    }

    /**
     * 废弃印制号统计（统计哪些人废弃了多少印制号）
     * @param lfYzhQO 印制号查询条件
     * @return {List} 统计信息
     */
    @PostMapping("/fqyzhtj")
    public List<LfYzhtjDTO> fqyzhtj(@RequestBody LfYzhQO lfYzhQO) {
        return zssyjgService.fqyzhtj(lfYzhQO);
    }
}

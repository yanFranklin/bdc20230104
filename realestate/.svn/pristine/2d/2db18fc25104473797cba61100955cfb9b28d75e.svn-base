package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.dto.init.DyhGzWcqlsgxDTO;
import cn.gtmap.realestate.common.core.dto.init.DyhGzXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.DyhGzQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDysdQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcDyhGzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/9/21
 * @description 批量更新不动产单元功能
 */
@RestController
@RequestMapping(value = "/rest/v1.0/dyhgz")
public class DyhGzController {

    @Autowired
    BdcDyhGzFeignService bdcDyhGzFeignService;

    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;

    /**
     * @param dyhGzQO 单元号更正对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一键更新不动产单元
     */
    @PostMapping("")
    public void updateBdcdyhPl(@RequestBody DyhGzQO dyhGzQO) {
        if(CollectionUtils.isEmpty(dyhGzQO.getBdcXmDOList()) ||StringUtils.isBlank(dyhGzQO.getBdcdyh())){
            throw new AppException("需要更新的项目集合为空或更新的不动产单元号为空");
        }
        bdcDyhGzFeignService.updateBdcdyhPl(dyhGzQO);
    }

    /**
     * @param xmid 项目ID
     * @param xmtype 1：现势产权项目ID 2新增权利项目ID
     * @return 单元号更正项目集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取所关联的所有项目数据
     */
    @GetMapping("/xmxx")
    public DyhGzXmDTO queryDyhGzXmDTO(@RequestParam("xmid") String xmid, @RequestParam("xmtype") String xmtype) {
        if(StringUtils.isBlank(xmid) ||StringUtils.isBlank(xmtype)){
            throw new AppException("选择的数据为空");
        }
        return bdcDyhGzFeignService.queryDyhGzXmDTO(xmid,xmtype);
    }

    /**
     * @param dysdid 单元锁定主键
     * @return 单元锁定数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据单元锁定主键获取单元锁定数据
     */
    @GetMapping("/dysdxx")
    public List<BdcDysdDO> queryDysd(@RequestParam("dysdid") String dysdid) {
        if(StringUtils.isBlank(dysdid)){
            throw new AppException("选择的数据为空");
        }
        BdcDysdQO bdcDysdQO = new BdcDysdQO();
        bdcDysdQO.setDysdid(dysdid);
        bdcDysdQO.setSdlx(null);
        return  bdcBdcdyFeignService.listBdcDysd(bdcDysdQO);

    }

    /**
     * @param dyhGzQO 单元号更正对象
     * @return 验证提示信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证选择的数据是否存在疑似关联数据
     */
    @PostMapping("/xzxx")
    public List<BdcGzyzVO> yzXzxx(@RequestBody DyhGzQO dyhGzQO) {
        if(CollectionUtils.isEmpty(dyhGzQO.getBdcXmDOList()) ||StringUtils.isBlank(dyhGzQO.getBdcdyh())){
            throw new AppException("需要更新的项目集合为空或更新的不动产单元号为空");
        }
        return bdcDyhGzFeignService.yzXzxx(dyhGzQO);
    }

    /**
     * @param xmid 项目ID
     * @return 项目附表信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取项目附表数据
     */
    @ResponseBody
    @GetMapping("/xmfb")
    public Object queryBdcXmfb(String xmid) {
        if(StringUtils.isNotBlank(xmid)) {
            BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
            bdcXmFbQO.setXmid(xmid);
            List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
            if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
                return bdcXmFbDOList.get(0);
            }
        }
        return new BdcXmFbDO();
    }

    /**
     * @param bdcXmDOList 已选项目集合
     * @return 单元号更正无产权历史关系数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据已选项目单元号获取无产权历史关系数据
     */
    @PostMapping("/wcqlsgx")
    public DyhGzWcqlsgxDTO queryDyhGzWcqlsgxDTO(@RequestBody List<BdcXmDO> bdcXmDOList) {
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException("已选项目集合为空");
        }
        return bdcDyhGzFeignService.queryDyhGzWcqlsgxDTO(bdcXmDOList);
    }
}

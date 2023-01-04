package cn.gtmap.realestate.inquiry.ui.web.rest.changzhou;

import cn.gtmap.gtc.clients.RegionManagerClient;
import cn.gtmap.gtc.sso.domain.dto.RegionDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.define.v2.ProcessModelClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.define.ProcessModel;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjdDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcPlZjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcPlZjxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZjXmxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZjQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZjFeignService;
import cn.gtmap.realestate.common.matcher.ProcessDefinitionClientMatcher;
import cn.gtmap.realestate.common.matcher.ProcessModelClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.util.Constants;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/3/30
 * @description 质检核查功能相关方法
 */
@RestController
@RequestMapping(value = "/rest/changzhou/zjhc")
public class BdcZjHcCzController extends BaseController {

    @Autowired
    private BdcZjFeignService bdcZjFeignService;

    @Autowired
    private BdcBhFeignService bdcBhFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    ProcessModelClientMatcher processModelClient;

    @Autowired
    private RegionManagerClient regionManagerClient;

    @Autowired
    private ProcessDefinitionClientMatcher processDefinitionClient;

    /**
     * 质检数据查询
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcZjQO 查询条件
     * @return 质检项目信息数据
     */
    @PostMapping(value ="/list/zjxmxx")
    public Object listBdcZjxx(@RequestBody BdcZjQO bdcZjQO) {
        if(StringUtils.isBlank(bdcZjQO.getDjlx()) && StringUtils.isBlank(bdcZjQO.getYwfl())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数登记类型或业务分类。");
        }
        if(StringUtils.isNotBlank(bdcZjQO.getYwfl()) && CollectionUtils.isEmpty(bdcZjQO.getGzldyids())){
            // 获取业务分类下所有流程
//            List<ProcessModel>  processModelList = this.processModelClient.queryProcessModelsByCondition(null, bdcZjQO.getYwfl(), null,  null, null);
            List<ProcessDefData> processModelList = this.processDefinitionClient.getProcessDefDataForCategory(bdcZjQO.getYwfl());
            List<String> gzldyids = processModelList.stream().map(ProcessDefData::getProcessDefKey).collect(Collectors.toList());
            bdcZjQO.setGzldyids(gzldyids);
        }

        return this.bdcZjFeignService.randomBdcZjXmxx(bdcZjQO);
    }

    /**
     * 生成质检信息
     * @param bdcXmDO 不动产项目
     * @return 质检信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/sc/zjqk")
    public BdcZjDO generateZj(@RequestBody BdcXmDO bdcXmDO){
        BdcZjDO bdcZjDO  = new BdcZjDO();
        BeanUtils.copyProperties(bdcXmDO, bdcZjDO);
        bdcZjDO.setLcmc(bdcXmDO.getGzldymc());
        String zjbh = bdcBhFeignService.queryCommonBh(Constants.BDC_ZJ_BH, CommonConstantUtils.ZZSJFW_DAY, 6, "");
        bdcZjDO.setZjbh(zjbh);
        UserDto userDto = this.userManagerUtils.getCurrentUser();
        if(Objects.nonNull(userDto)){
            bdcZjDO.setHdr(userDto.getAlias());
        }
        bdcZjDO.setZjsj(new Date());
        return this.bdcZjFeignService.saveBdcZjDO(bdcZjDO);
    }

    /**
     * 获取质检情况
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param zjid 质检ID
     * @return 质检情况信息
     *
     */
    @GetMapping(value = "/zjqk/{zjid}")
    public Object getBdcZjxx(@PathVariable(value = "zjid") String zjid){
        if(StringUtils.isBlank(zjid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到质检ID信息。");
        }
        BdcZjDO bdcZjDO = new BdcZjDO();
        bdcZjDO.setZjid(zjid);
        List<BdcZjDO> bdcZjDOList = this.bdcZjFeignService.queryBdcZjxx(bdcZjDO);
        return bdcZjDOList;
    }


    /**
     * 查询不动产质检汇总数据
     * @param bdcZjQO 不动产质检QO
     * @return 质检结果汇总数据
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/zjjg/hz")
    public List<BdcZjXmxxDTO> queryBdcZjjgHzxx(@RequestBody BdcZjQO bdcZjQO){
        if(StringUtils.isBlank(bdcZjQO.getSxfs())){
            throw new AppException(ErrorCode.MISSING_ARG, "质检结果汇总查询时，缺少筛选方式。");
        }
        if(bdcZjQO.getSxfs().equals("personal")){
            UserDto userDto = this.userManagerUtils.getCurrentUser();
            bdcZjQO.setHdr(userDto.getAlias());
        }
        return this.bdcZjFeignService.queryBdcZjjgHzxx(bdcZjQO);
    }

    /**
     * 批量生成质检信息
     * @param bdcPlZjDTO 不动产批量质检DTO
     * @return 质检信息
     * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
     */
    @PostMapping(value = "/sc/plzj")
    public BdcZjdDO generatePlZj(@RequestBody BdcPlZjDTO bdcPlZjDTO){
        return bdcZjFeignService.plCreateBdcZj(bdcPlZjDTO);
    }

    /**
     * 批量质检质检单详情
     * @param zjdbh 质检单编号
     * @return 质检信息
     * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
     */
    @PostMapping(value = "/plzj/info")
    public BdcPlZjxxDTO plZjInfo(@RequestParam(value = "zjdbh") String zjdbh){
        return bdcZjFeignService.plZjInfo(zjdbh);
    }

    /**
     * 调用大云接口获取区县代码
     * @return 县级区县代码
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/getQxdm")
    public Object getQxdmData() {
        List<Map> result = new ArrayList<>(20);
        List<RegionDto> regionDtolist = new ArrayList<>(20);
        // 获取县级代码
        regionDtolist.addAll(regionManagerClient.findRegionsByLevel(3));
        if(CollectionUtils.isNotEmpty(regionDtolist)){
            for(RegionDto regionDto : regionDtolist){
                Map<String, Object> region = new HashMap<>(3);
                region.put("XZDM", regionDto.getCode());
                region.put("XZMC", regionDto.getName());
                region.put("XZJB", regionDto.getLevel());
                result.add(region);
            }
        }
        return result;
    }

}

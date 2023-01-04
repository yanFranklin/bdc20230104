package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.util.Constants;
import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzyzFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.common.core.service.rest.building.FwHsRestService;
import cn.gtmap.realestate.common.core.service.rest.building.FwXmxxRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019/3/4
 * @description
 */
@Controller
@Validated
@RequestMapping("check")
public class CheckBgController extends BaseController {

    @Autowired
    private FwHsRestService fwHsRestService;
    @Autowired
    private FwXmxxRestService fwXmxxRestService;
    @Autowired
    BdcGzyzFeignService bdcGzyzFeignService;
    @Autowired
    FwHsFeignService fwHsFeignService;
    @Autowired
    FwYcHsFeignService fwYcHsFeignService;

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息变更，验证不动产单元号
     */
    @ResponseBody
    @RequestMapping("/xmxx/bdcdyh")
    public Map checkXmxxBgByBdcdyh(@NotEmpty(message = "项目数据不能为空")
                                   @RequestParam(name = "indexList", required = false) List<String> indexList) {
        if(CollectionUtils.isNotEmpty(indexList)){
            for(String fwXmxxIndex:indexList){
                if(StringUtils.isNotBlank(fwXmxxIndex)){
                    FwXmxxDO fwXmxxDO=fwXmxxRestService.queryXmxxByFwXmxxIndex(fwXmxxIndex,"");
                    if(fwXmxxDO!=null&&(StringUtils.isBlank(fwXmxxDO.getBdcdyh())||StringUtils.equals(Constants.BDCZT_BKY,fwXmxxDO.getBdczt()))){
                        return returnHtmlResult(false, "不动产单元号异常");
                    }
                }
            }
        }
        return returnHtmlResult(true, "");
    }
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return java.lang.String
     * @description 房屋户室变更，验证不动产单元号
     */
    @ResponseBody
    @RequestMapping("/fwhs/bdcdyh")
    public Map checkFwhsBgByBdcdyh(@NotEmpty(message = "户室数据不能为空")
                                       @RequestParam(name = "indexList", required = false) List<String> indexList){
        if(CollectionUtils.isNotEmpty(indexList)){
            for(String fwHsIndex:indexList){
                if (StringUtils.isNotBlank(fwHsIndex)) {
                    FwHsDO fwHsDO = fwHsRestService.queryFwHsByFwHsIndex(fwHsIndex,"");
                    if (fwHsDO != null && (StringUtils.isBlank(fwHsDO.getBdcdyh()) || StringUtils.equals(Constants.BDCZT_BKY, fwHsDO.getBdczt()))) {
                        return returnHtmlResult(false, "不动产单元号异常");
                    }
                }
            }
        }
        return returnHtmlResult(true, "");
    }

    @ResponseBody
    @GetMapping("/hshb")
    public List<Map<String, Object>> gzyzHbdyh(@NotEmpty(message = "合并数据数据不能为空") @RequestParam(name = "indexList", required = false) List<String> indexList) {
        LOGGER.info("合并单元号入参{}", indexList);
        List<Map<String, Object>> gzYzsjDTOList = new ArrayList<>(CollectionUtils.size(indexList));
        List<String> bdcdyhList = fwHsFeignService.listValidBdcdyhByFwHsIndexList(indexList,"");
        //如果实测查不到再查预测的
        if (CollectionUtils.isEmpty(bdcdyhList)) {
            for (String hsIndex : indexList) {
                FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByFwHsIndex(hsIndex,"");
                if (Objects.nonNull(fwYchsDO) && StringUtils.isNotBlank(fwYchsDO.getBdcdyh())) {
                    bdcdyhList.add(fwYchsDO.getBdcdyh());
                }
            }
        }
        for (String bdcdyh : bdcdyhList) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("bdcdyh", bdcdyh);
            gzYzsjDTOList.add(paramMap);
        }
        BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
        bdcGzYzQO.setZhbs("HSHB");
        bdcGzYzQO.setParamList(gzYzsjDTOList);
        LOGGER.info("合并单元号规则验证数据{}", bdcGzYzQO);
        return bdcGzyzFeignService.yzBdcgz(bdcGzYzQO);
    }

    @ResponseBody
    @PostMapping("/log")
    public void removeLog(@RequestBody String data) {
        addLog(data, CommonConstantUtils.HLLX_HSHB);
    }


    /**
     * @param fwHsIndexLsit
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 户室合并验证后台逻辑
     * @date : 2021/9/1 15:27
     */
    @PostMapping("/hbCheck")
    @ResponseBody
    public Map hbCheck(@RequestBody List<String> fwHsIndexLsit) {
        if (CollectionUtils.isEmpty(fwHsIndexLsit)) {
            throw new AppException("验证户室能否合并缺失户室主键");
        }
        HashMap<String, Object> resultMap = new HashMap<>(1);
        boolean result = true;
        List<FwHsDO> fwHsDOList = new ArrayList<>(CollectionUtils.size(fwHsIndexLsit));
        for (String fwHsIndex : fwHsIndexLsit) {
            FwHsDO fwHsDO = fwHsFeignService.queryFwHsByFwHsIndex(fwHsIndex, "");
            if (Objects.isNull(fwHsDO)) {
                fwHsDO = new FwHsDO();
                FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByFwHsIndex(fwHsIndex, "");
                if (Objects.nonNull(fwYchsDO)) {
                    BeanUtils.copyProperties(fwYchsDO, fwHsDO);
                    fwHsDOList.add(fwHsDO);
                }
            } else {
                fwHsDOList.add(fwHsDO);
            }
        }
        //先判断是否是同层的数据，根据物理层数判断
        LOGGER.info("勾选需要合并的数据{}", fwHsIndexLsit);
        if (CollectionUtils.isEmpty(fwHsDOList)) {
            throw new AppException("未查询到户室信息");
        }
        boolean sameWlcs = checkSameWlcs(fwHsDOList);
        //相同层数判断是否相邻
        if (sameWlcs) {
            LOGGER.info("当前合并数据属于同一层户室");
            result = checkSfzyxl(fwHsDOList);
            resultMap.put("hbfx", "1");
            if (!result) {
                resultMap.put("msg", "当前选择的户室数据不属于左右相邻户室");
            }
        } else {
            //不同层数判断两个户室的室序号是否相等，不相等说明不在上下层关系
            LOGGER.info("当前合并数据不在同一层");
            result = checkSfSxxl(fwHsDOList);
            resultMap.put("hbfx", "3");
            if (!result) {
                resultMap.put("msg", "当前选择的数据不属于上下相邻户室");
            }
        }
        resultMap.put("result", result);
        return resultMap;
    }

    private boolean checkSameWlcs(List<FwHsDO> fwHsDOList) {
        boolean result = true;
        if (CollectionUtils.isNotEmpty(fwHsDOList) && fwHsDOList.size() > 1) {
            Integer wlcs = fwHsDOList.get(0).getWlcs();
            for (FwHsDO fwHsDO : fwHsDOList) {
                if (Objects.nonNull(wlcs) && !Objects.equals(wlcs, fwHsDO.getWlcs())) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * @param fwHsDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 是否左右相邻
     * @date : 2021/9/1 16:09
     */
    private boolean checkSfzyxl(List<FwHsDO> fwHsDOList) {
        boolean result = true;
        //根据室序号排序
        fwHsDOList.sort(Comparator.comparing(FwHsDO::getSxh));
        for (int i = 0; i < fwHsDOList.size() - 1; i++) {
            Integer thisFwhsSxh = fwHsDOList.get(i).getSxh();
            Integer nextFwhsSxh = fwHsDOList.get(i + 1).getSxh();
            Integer subtractSxh = nextFwhsSxh - thisFwhsSxh;
            if (!Objects.equals(1, subtractSxh)) {
                //不等于1 说明不是左右相邻的室序号
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * @param fwHsDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证是否上下相邻
     * @date : 2021/9/1 16:11
     */
    private boolean checkSfSxxl(List<FwHsDO> fwHsDOList) {
        boolean result = true;
        //根据室序号排序
        fwHsDOList.sort(Comparator.comparing(FwHsDO::getSxh));
        //室序号相同且物理层数相邻
        //物理层数是否相邻
        boolean sfxlwlc = checkCloseWlcs(fwHsDOList);
        if (!sfxlwlc) {
            result = false;
        } else {
            for (int i = 0; i < fwHsDOList.size() - 1; i++) {
                Integer thisFwhsSxh = fwHsDOList.get(i).getSxh();
                Integer nextFwhsSxh = fwHsDOList.get(i + 1).getSxh();
                Integer subtractSxh = nextFwhsSxh - thisFwhsSxh;
                if (!Objects.equals(0, subtractSxh)) {
                    //不等于0 说明不是上下相邻的室序号
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * @param fwHsDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 物理层数是否相邻
     * @date : 2021/9/2 8:28
     */
    private boolean checkCloseWlcs(List<FwHsDO> fwHsDOList) {
        boolean result = true;
        //根据物理层数排序
        fwHsDOList.sort(Comparator.comparing(FwHsDO::getWlcs));
        for (int i = 0; i < fwHsDOList.size() - 1; i++) {
            Integer thisFwhsWlc = fwHsDOList.get(i).getWlcs();
            Integer nextFwhsWlc = fwHsDOList.get(i + 1).getWlcs();
            Integer subtractWlc = nextFwhsWlc - thisFwhsWlc;
            if (!Objects.equals(1, subtractWlc)) {
                //不等于1 说明不是上下相邻的物理层数
                result = false;
                break;
            }
        }
        return result;
    }
}

package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcTsywPzDO;
import cn.gtmap.realestate.common.core.domain.BdcZdSsjGxDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcLcSsjGxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.config.BdcTsywPzQO;
import cn.gtmap.realestate.common.core.service.feign.config.BdcTsywPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcZdSsjgxFeignService;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.spire.ms.System.Collections.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/5/31
 * @description 共享接口配置
 */
@RestController
@RequestMapping("/rest/v1.0/gxjkpz")
public class BdcGxjkPzController extends BaseController {

    @Autowired
    BdcZdSsjgxFeignService bdcZdSsjgxFeignService;

    @Autowired
    BdcTsywPzFeignService bdcTsywPzFeignService;

    @Autowired
    WorkFlowUtils workFlowUtils;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询数据
     */
    @GetMapping("/page")
    public Object listBdcZdSsjGxByPage(@LayuiPageable Pageable pageable, BdcZdSsjGxDO bdcZdSsjGxDO) {
        String queryJson = JSON.toJSONString(bdcZdSsjGxDO);
        return super.addLayUiCode(bdcZdSsjgxFeignService.listBdcZdSsjGxByPage(pageable, queryJson));
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除省市级共享字典数据
     */
    @DeleteMapping("")
    public void deleteBdcZdSsjGx(@RequestBody List<BdcZdSsjGxDO> bdcZdSsjGxDOList){
        if(CollectionUtils.isEmpty(bdcZdSsjGxDOList)){
            throw new AppException("未获取到省市级共享字典数据");
        }
        for(BdcZdSsjGxDO bdcZdSsjGxDO:bdcZdSsjGxDOList){
            if(StringUtils.isBlank(bdcZdSsjGxDO.getZmldm())){
                throw new AppException("删除省市级共享字典数据缺失子目录代码");
            }
            bdcZdSsjgxFeignService.deleteBdcZdSsjGxByZmldm(bdcZdSsjGxDO.getZmldm());

        }
        //同步字典项
        bdcTsywPzFeignService.syncGxjkmlZdyzdx();
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存省市级共享字典数据
     */
    @PatchMapping("/save")
    public void saveBdcZdSsjGx(@RequestBody BdcZdSsjGxDO bdcZdSsjGxDO){
        if(bdcZdSsjGxDO ==null){
            throw new AppException("未获取到省市级共享字典数据");
        }
        bdcZdSsjgxFeignService.saveBdcZdSsjgx(bdcZdSsjGxDO);
        //同步字典项
        bdcTsywPzFeignService.syncGxjkmlZdyzdx();


    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询省市级共享字典数据
     */
    @GetMapping("")
    public Object queryBdcZdSsjGx(@RequestParam(name = "id") String id){
        if(StringUtils.isNotBlank(id)){
            return bdcZdSsjgxFeignService.queryBdcZdSsjGx(id);

        }
        return null;


    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询共享流程配置分页
     */
    @GetMapping("/lcpz/page")
    public Object listBdcTsywPzByPage(@LayuiPageable Pageable pageable, BdcTsywPzQO bdcTsywPzQO) {
        String cxgzldyid ="";
        if(StringUtils.isNotBlank(bdcTsywPzQO.getPzmc())) {
            cxgzldyid = bdcTsywPzQO.getPzmc().replace("gxjkml.","").replace("gxjkml","");
        }
        String bdcTsywPzQOStr = JSON.toJSONString(bdcTsywPzQO);
        List<BdcTsywPzDO> bdcTsywPzDOList = bdcTsywPzFeignService.bdcTsywPzList(bdcTsywPzQOStr);
        //获取共享接口配置流程与配置值的关系
        Map<String,BdcTsywPzDO> fmldmMap =new HashMap<>();
        if(CollectionUtils.isNotEmpty(bdcTsywPzDOList)){
            for(BdcTsywPzDO bdcTsywPzDO:bdcTsywPzDOList){
                fmldmMap.put(bdcTsywPzDO.getPzmc().replace("gxjkml.",""),bdcTsywPzDO);
            }
        }
        List<BdcLcSsjGxDTO> bdcLcSsjGxDTOS =new ArrayList();
        //查询出所有流程
        List<ProcessDefData> processDefDataList=workFlowUtils.getAllProcessDefData();
        processDefDataList=processDefDataList.stream().filter(processDefData ->
                processDefData.getSuspensionState()==1
        ).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(processDefDataList)){
            for(ProcessDefData processDefData:processDefDataList){
                if(StringUtils.isBlank(cxgzldyid) ||StringUtils.equals(cxgzldyid,processDefData.getProcessDefKey())) {
                    BdcLcSsjGxDTO bdcLcSsjGxDTO = new BdcLcSsjGxDTO();
                    bdcLcSsjGxDTO.setGzldyid(processDefData.getProcessDefKey());
                    bdcLcSsjGxDTO.setGzldymc(processDefData.getName());
                    if (fmldmMap.containsKey(processDefData.getProcessDefKey())) {
                        bdcLcSsjGxDTO.setFmldm(fmldmMap.get(processDefData.getProcessDefKey()).getPzz());
                        bdcLcSsjGxDTO.setPzid(fmldmMap.get(processDefData.getProcessDefKey()).getPzid());
                    }
                    bdcLcSsjGxDTOS.add(bdcLcSsjGxDTO);
                }
            }
        }
        return super.addLayUiCode(PageUtils.listToPage(bdcLcSsjGxDTOS,pageable));
    }


}

package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhExcelDTO;
import cn.gtmap.realestate.common.core.dto.config.BdcReturnData;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.*;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcYzhQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcYzhFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtZsyzhFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcYzhVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/30
 * @description 业务配置系统：证书印制号模板配置请求处理控制器
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcXtZsyzhController extends BaseController {
    /**
     * 证书印制号模板服务
     */
    @Autowired
    private BdcXtZsyzhFeignService bdcXtZsyzhFeignService;
    /**
     * 字典服务
     */
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    /**
     * 用户信息
     */
    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private BdcYzhFeignService bdcYzhFeignService;
    /**
     * 印制号人员过滤角色配置
     */
    @Value("#{'${zsyzh.roles:}'.split(',')}")
    private List<String> rolesList;

    /**
     * 标准版印制号显示按钮（默认显示正常版；配置hauian显示淮安版本的）
     */
    @Value("${yzhpz.version:standard}")
    private String version;

    /***
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description  获取印制号配置（用于标准版显示标准版按钮还是淮安版按钮）
    */
    @GetMapping("/zsyzh/version")
    public BdcReturnData getVersion(){
        BdcReturnData returnData = new BdcReturnData();
        returnData.setRes(version);
        return returnData;
    }
    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  pageable 分页对象
     * @param  bdcYzhQO 查询条件
     * @return 证书印制号模板
     * @description  查询证书印制号模板数据列表
     */
    @GetMapping("/zsyzh")
    public Object listBdcZsyzh(@LayuiPageable Pageable pageable, BdcYzhQO bdcYzhQO){
        // 印制号查询根据当前用户区县代码进行过滤  此段逻辑放在这里处理如果不合适后期可以更改一下 ccx 2019-10-06
        UserDto user = userManagerUtils.getCurrentUser();
        // 是否有管理员角色
        boolean isAdmin = false;
        // 返回信息
        Map map = null;
        if(user != null && StringUtils.isNotBlank(user.getId())){
            isAdmin = userManagerUtils.isAdminByUserId(user.getId());
        }else{
            map = new HashMap(1);
            map.put("msg","未获取到当前用户信息！");
        }
        if(!isAdmin){
            String regionCode = userManagerUtils.getRegionCodeByUserName(user.getUsername());
            if(StringUtils.isNotBlank(regionCode)){
                bdcYzhQO.setQxdm(regionCode);
            }else{
                map = new HashMap(1);
                map.put("msg","未获取到当前用户区县代码！");
            }
        }
        if(map != null){
            return map;
        }
        //由于sql中会将印制号会将区县代码部分去除，故参数也要做此处理
        int lengthLimit = 11;
        String yzhq = bdcYzhQO.getYzhq();
        String yzhz = bdcYzhQO.getYzhz();
        if (StringUtils.isNotBlank(yzhq) && yzhq.length()>=lengthLimit){
            if (yzhq.startsWith(CommonConstantUtils.TSYZH_NT)){
                bdcYzhQO.setYzhq(yzhq.substring(3));
            }else {
                bdcYzhQO.setYzhq(yzhq.substring(2));
            }
        }
        if (StringUtils.isNotBlank(yzhz) && yzhz.length()>=lengthLimit){
            if (yzhz.startsWith(CommonConstantUtils.TSYZH_NT)){
                bdcYzhQO.setYzhz(yzhz.substring(3));
            }else {
                bdcYzhQO.setYzhz(yzhz.substring(2));
            }
        }
        Page<BdcYzhDO> bdcYzhDOPage = bdcXtZsyzhFeignService.listBdcZsyzh(pageable, JSON.toJSONString(bdcYzhQO));
        return super.addLayUiCode(bdcYzhDOPage);
    }

    /**
     * @param yzhid 印制号ID
     * @return List<BdcYzhsymxDO> 印制号使用明细
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取印制号使用明细
     */
    @GetMapping("/zsyzh/{yzhid}/symx")
    List<BdcYzhsymxDO> listBdcYzhSymx(@PathVariable(value = "yzhid") String yzhid) {
        BdcYzhDTO bdcYzhDTO = bdcYzhFeignService.queryBdcYzh(yzhid);
        if (null != bdcYzhDTO) {
            List<BdcYzhsymxDO> bdcYzhsymxDOList = bdcYzhDTO.getBdcYzhsymxDOList();
            if (CollectionUtils.isNotEmpty(bdcYzhsymxDOList)) {
                bdcYzhsymxDOList.sort(Comparator.comparing(BdcYzhsymxDO::getSysj));
                Collections.reverse(bdcYzhsymxDOList);
            }
            return bdcYzhsymxDOList;
        }
        return null;
    }


    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcYzhVO 证书印制号模板
     * @return {int} 操作数据记录数
     * @description  生成证书印制号
     */
    @PostMapping("/zsyzh")
    public int generateBdcZsyzh(@RequestBody BdcYzhVO bdcYzhVO){
        return bdcXtZsyzhFeignService.generateBdcZsyzh(bdcYzhVO);
    }

    @PostMapping("/extractData")
    public BdcReturnData extractData(@RequestBody BdcYzhVO bdcYzhVO){
        return bdcXtZsyzhFeignService.extractData(bdcYzhVO);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcYzhDO 证书印制号模板实体
     * @return {int} 操作数据记录数
     * @description  保存证书印制号模板配置
     */
    @PutMapping("/zsyzh")
    public int saveBdcZsyzh(@RequestBody BdcYzhDO bdcYzhDO){
        return bdcXtZsyzhFeignService.saveBdcZsyzh(bdcYzhDO);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcYzhDOList 证书印制号模板实体
     * @return {int} 操作数据记录数
     * @description  删除证书印制号模板配置
     */
    @DeleteMapping("/zsyzh")
    public int deleteBdcZsyzh(@RequestBody List<BdcYzhDO> bdcYzhDOList){
        return bdcXtZsyzhFeignService.deleteBdcZsyzh(bdcYzhDOList);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcYzhsymxDO 证书印制号使用明细
     * @return {int} 操作数据记录数
     * @description  作废证书印制号
     */
    @DeleteMapping("/zsyzh/zf")
    public void deleteBdcZsyzh(@RequestBody BdcYzhsymxDO bdcYzhsymxDO){
        bdcXtZsyzhFeignService.deleteBdcZsyzh(bdcYzhsymxDO);
    }

    /**
     * @author  <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param  bdcYzhsymxDO 证书印制号使用明细
     * @return {int} 操作数据记录数
     * @description  还原证书印制号
     */
    @PostMapping("/zsyzh/hy")
    public void revertBdcZsyzh(@RequestBody BdcYzhsymxDO bdcYzhsymxDO){
        bdcXtZsyzhFeignService.revertBdcZsyzh(bdcYzhsymxDO);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return  {Map} 字典集合
     * @description  获取权利用到的字典
     */
    @GetMapping("/zsyzh/zd")
    public Map<String, List<Map>> listZd(){
        return bdcZdFeignService.listBdcZd();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 领取方式
     * @description 获取区县代码对应的印制号领取方式所有配置项
     */
    @GetMapping("/zsyzh/lqfs")
    public Map<String, String> getAllZsyzhLqfs() {
        return bdcXtZsyzhFeignService.getAllZsyzhLqfs();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 省份代码
     * @description 获取省份代码
     */
    @GetMapping("/zsyzh/sfdm")
    public String getSfdm(){
        List<OrganizationDto> orgList = userManagerUtils.getCurrentUser().getOrgRecordList();
        if(CollectionUtils.isEmpty(orgList)){
            return null;
        }

        String regionCode = orgList.get(0).getRegionCode();
        if(StringUtils.isBlank(regionCode)){
            return null;
        }
        return regionCode.substring(0, 2);
    }

    /**
     * @param yzhidList 印制号ID列表
     * @return List<BdcYzhExcelDTO> 不动产印制号及明细
     * @author <a href="mailto:hejian@gtmap.cn">hejain</a>
     * @description 批量获取印制号及使用明细
     */
    @PostMapping("/zsyzh/listSymx")
    @ResponseStatus(HttpStatus.OK)
    List<BdcYzhExcelDTO> listBdcYzhSymxSet(@RequestBody List<String> yzhidList) {
        if(CollectionUtils.isEmpty(yzhidList)) {
            throw new MissingArgumentException("印制号ID列表参数为空");
        }

        List<BdcYzhExcelDTO> BdcYzhSymxList = bdcYzhFeignService.queryBdcYzhSymx(yzhidList);
        return BdcYzhSymxList;
    }
}

package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZjxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZjQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZjFeignService;
import cn.gtmap.realestate.common.core.vo.inquiry.ResultDataVO;
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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/3/30
 * @description 质检核查功能相关方法
 */
@RestController
@RequestMapping(value = "/rest/v1.0/zjhc")
public class BdcZjHcController  extends BaseController {

    @Autowired
    private BdcZjFeignService bdcZjFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    StorageClientMatcher storageClient;
    /**
     * 签名图片地址
     */
    @Value("${url.storage}")
    private String storageUrl;

    /**
     * 质检数据查询
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param pageable  分页对象
     * @param bdcZjQO 查询条件
     * @return {Page} 质检数据查询分页结果
     */
    @GetMapping(value ="/zjxxcx")
    public Object listBdcZjxx(@LayuiPageable Pageable pageable, BdcZjQO bdcZjQO) {
        Page<BdcXmDO> zjxxListPage = this.bdcZjFeignService.listBdcZjxx(pageable, JSON.toJSONString(bdcZjQO));
        return super.addLayUiCode(zjxxListPage);
    }

    /**
     * 随机筛选质检信息，创建质检单生成质检单编号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcZjQO 不动产质检查询DO
     * @return 质检单ID
     */
    @GetMapping(value ="/random/cjzjxx")
    public String randomCjzjxx(BdcZjQO bdcZjQO) {
        if(Objects.isNull(bdcZjQO)){
            throw new AppException("未获取到质检查询信息。");
        }
        return this.bdcZjFeignService.generateRandomZjd(bdcZjQO);
    }

    /**
     * 手动筛选质检信息，创建质检单生成质检单编号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcXmDOList 不动产项目信息集合
     * @return 质检单ID
     */
    @PostMapping(value = "/manual/cjzjxx")
    public String manualCjzjxx(@RequestBody List<BdcXmDO> bdcXmDOList) {
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException("未获取到手动筛选的质检信息");
        }
        return this.bdcZjFeignService.generateManualZjd(bdcXmDOList);
    }

    /**
     * 根据质检单ID获取质检单与关联的质检信息
     * @param zjdid 质检单ID
     * @return BdcZjdDTO 不动产质检单DTO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/zjxx/{zjdid}")
    public Object getBdcZjdAndZjxx(@PathVariable(value = "zjdid") String zjdid){
        if(StringUtils.isBlank(zjdid)){
            throw new AppException("未获取到质检单ID");
        }
        return bdcZjFeignService.getBdcZjxxByZjdId(zjdid);
    }

    /**
     * 更新质检单质检状态
     * @param zjdid 质检单ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/zjd/zjzt/{zjdid}")
    public ResultDataVO modifyZjzt(@PathVariable(value = "zjdid") String zjdid){
        if(StringUtils.isBlank(zjdid)){
            throw new AppException("缺失参数，质检单ID。");
        }
        // 获取质检信息
        BdcZjDO bdcZjDO = new BdcZjDO();
        bdcZjDO.setZjdid(zjdid);
        List<BdcZjDO> bdcZjDOList = this.bdcZjFeignService.queryBdcZjxx(bdcZjDO);
        if(CollectionUtils.isEmpty(bdcZjDOList)){
            throw new AppException("未获取到质检信息。");
        }
        boolean checked = true;
        for(BdcZjDO zjxx : bdcZjDOList){
            if(CommonConstantUtils.ZJZT_WZJ.equals(zjxx.getZjzt())){
                checked = false;
                break;
            }
        }
        if(checked){
            this.bdcZjFeignService.modifyZjdzt(zjdid);
            return new ResultDataVO(true, "");
        }
        return new ResultDataVO(false, "未质检完成，无法办结。");
    }

    /**
     * 根据质检ID删除质检信息
     * @param zjid 质检ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @DeleteMapping(value = "/zjxx/{zjid}")
    public void deleteZjxx(@PathVariable(value = "zjid") String zjid){
        if (StringUtils.isBlank(zjid)) {
            throw new AppException("缺失参数：质检信息ID。");
        }
        this.bdcZjFeignService.deleteZjxx(zjid);
    }

    /**
     * 保存质检信息和质检明细信息
     * @param bdcZjxxDTO 不动产质检信息DTO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PutMapping(value = "/zjxx/mx")
    public void saveZjxxAndMx(@RequestBody BdcZjxxDTO bdcZjxxDTO){
        bdcZjxxDTO.setZjsj(new Date());
        this.bdcZjFeignService.saveZjxxAndZjmx(bdcZjxxDTO);
    }

    /**
     * 根据质检ID获取质检信息和情况明细
     * @param zjid 质检ID
     * @return 质检信息和情况明细
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/zjmx/{zjid}")
    public BdcZjxxDTO getBdcZjxx(@PathVariable(value = "zjid") String zjid){
        BdcZjxxDTO bdcZjxxDTO = this.bdcZjFeignService.getBdcZjxxAndMx(zjid);
        if(Objects.isNull(bdcZjxxDTO.getZjsj())){
            bdcZjxxDTO.setZjsj(new Date());
        }
        if(StringUtils.isNotBlank(bdcZjxxDTO.getQmxxid())){
            bdcZjxxDTO.setQmtpdz(storageUrl + "/rest/files/download/" + bdcZjxxDTO.getQmxxid());
        }
        return bdcZjxxDTO;
    }

    /**
     * 签名方法
     * @return 签名信息ID 与 签名图片地址
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/zjxx/qm")
    public BdcZjxxDTO qmxx(){
        UserDto userDto = userManagerUtils.getCurrentUser();
        if(userDto ==null){
            throw new AppException("用户信息获取失败");
        }
        String signId = storageClient.userSign(userDto.getUsername());
        if (StringUtils.isEmpty(signId)) {
            throw new AppException("获取签名id失败");
        }
        BdcZjxxDTO bdcZjxxDTO = new BdcZjxxDTO();
        bdcZjxxDTO.setQmxxid(signId);
        bdcZjxxDTO.setQmtpdz(storageUrl + "/rest/files/download/" + signId);
        return bdcZjxxDTO;
    }

    /**
     * 根据质检单ID删除质检单信息
     * @param zjdid 质检单ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @DeleteMapping(value = "/zjd/{zjdid}")
    public void deleteZjdxx(@PathVariable(value = "zjdid") String zjdid){
        if (StringUtils.isBlank(zjdid)) {
            throw new AppException("缺失参数：质检单ID。");
        }
        this.bdcZjFeignService.deleteZjd(zjdid);
    }
}

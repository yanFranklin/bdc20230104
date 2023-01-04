package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlTfxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlTfxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlTfxxFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/11/19.
 * @description 不动产退费信息查询
 */
@RestController
@RequestMapping("/rest/v1.0/tfxx")
public class BdcTfxxController extends BaseController {

    @Autowired
    BdcSlTfxxFeignService bdcSlTfxxFeignService;

    /**
     * 退费信息分页查询
     * @param pageable 分页参数
     * @param bdcSlTfxxQO 分页查询参数
     * @return 分页数据
     */
    @GetMapping("/page")
    public Object queryTfxxByPage(@LayuiPageable Pageable pageable, BdcSlTfxxQO bdcSlTfxxQO) {
        Page<BdcSlTfxxDO> page = bdcSlTfxxFeignService.listBdcSlTfxxByPage(pageable, JSON.toJSONString(bdcSlTfxxQO));
        return super.addLayUiCode(page);
    }

    /**
     * 查询退费信息
     * @param bdcSlTfxxQO 查询参数
     * @return 退费信息
     */
    @GetMapping("/list")
    public Object queryTfxxList(BdcSlTfxxQO bdcSlTfxxQO) {
        return bdcSlTfxxFeignService.listbdcSlTfxx(bdcSlTfxxQO);
    }

    /**
     * 根据受理编号获取收费信息，生成退费信息
     * @param slbh 受理编号
     * @return 不动产受理退费信息集合
     */
    @GetMapping("/generate")
    public List<BdcSlTfxxDO> generateTfxx(@RequestParam(value="slbh", required = false) String slbh){
        if(StringUtils.isBlank(slbh)){
            return new ArrayList<>(1);
        }
        return this.bdcSlTfxxFeignService.generateTfxx(slbh);
    }

    /**
     * 批量保存或新增不动产受理退费信息
     * @param bdcSlTfxxDOList 不动产受理退费信息DO
     */
    @PostMapping("/saveTfxx")
    public void saveBdcSlTfxx(@RequestBody List<BdcSlTfxxDO> bdcSlTfxxDOList){
        if(CollectionUtils.isEmpty(bdcSlTfxxDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到退费信息");
        }
        this.bdcSlTfxxFeignService.plSaveBdcSlTfxx(bdcSlTfxxDOList);
    }

    /**
     * 批量删除退费信息
     * @param tfxxidList 退费信息id集合
     */
    @DeleteMapping("/list")
    public void removeTfxx(@RequestBody List<String> tfxxidList){
        if(CollectionUtils.isEmpty(tfxxidList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取退费信息ID");
        }
        this.bdcSlTfxxFeignService.plRemoveBdcSlTfxx(tfxxidList);
    }

}

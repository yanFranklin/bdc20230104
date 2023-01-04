package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcFphDO;
import cn.gtmap.realestate.common.core.domain.BdcFphSymxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcFphSymxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.config.BdcFphQO;
import cn.gtmap.realestate.common.core.service.rest.config.BdcXtFphRestService;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcFphVO;
import cn.gtmap.realestate.config.config.PropsConfig;
import cn.gtmap.realestate.config.service.BdcXtFphService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-09
 * @description
 */
@RestController
@Api(tags = "不动产系统打印数据源配置服务接口")
public class BdcXtFphRestController implements BdcXtFphRestService {
    @Autowired
    BdcXtFphService bdcXtFphService;
    @Autowired
    private PropsConfig propsConfig;
    
    /**
     * @param pageable     分页对象
     * @param fphParamJson 查询条件
     * @return {Page} 发票号配置分页数据
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询发票号配置数据列表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询默认意见配置列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "fphParamJson", value = "发票号查询参数JSON", required = false, paramType = "query")})
    public Page<BdcFphVO> listBdcFph(Pageable pageable, @RequestParam(value = "fphParamJson", required = false) String fphParamJson) {
        return bdcXtFphService.listBdcFph(pageable, fphParamJson);
    }
    
    /**
     * @param bdcFphQO 发票号模板
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 生成发票号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("生成发票号")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcFphQO", value = "不动产系统发票号", required = true, paramType = "body")})
    public int generateBdcFph(@RequestBody BdcFphQO bdcFphQO) {
        return bdcXtFphService.generateBdcFph(bdcFphQO);
    }
    
    /**
     * @param bdcFphDO 发票号配置实体
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存发票号配置配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存发票号配置信息")
    @ApiImplicitParam(name = "bdcFphDO", value = "不动产系统发票号配置", required = true, paramType = "body")
    public int saveBdcFph(@RequestBody BdcFphDO bdcFphDO) {
        return bdcXtFphService.saveBdcFph(bdcFphDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("修改发票号配置信息")
    @ApiImplicitParam(name = "bdcFphDO", value = "不动产系统发票号信息", required = true, paramType = "body")
    public void updateBdcFphxx(@RequestBody BdcFphDO bdcFphDO) {
        bdcXtFphService.updateBdcFphxx(bdcFphDO);
    }

    /**
     * @param bdcFphDOList 发票号配置集合
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除发票号配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除发票号配置信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcFphDOList", value = "不动产系统发票号配置", required = true, paramType = "body")})
    public int deleteBdcFph(@RequestBody List<BdcFphDO> bdcFphDOList) {
        return bdcXtFphService.deleteBdcFph(bdcFphDOList);
    }
    
    /**
     * @param bdcFphSymxDO 发票号使用明细
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 作废发票号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("作废发票号配置信息")
    @ApiImplicitParam(name = "bdcFphSymxDO", value = "不动产系统发票号使用明细", required = true, paramType = "body")
    public void deleteBdcFph(@RequestBody BdcFphSymxDO bdcFphSymxDO) {
        bdcXtFphService.deleteBdcFph(bdcFphSymxDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("作废发票号")
    @ApiImplicitParam(name = "fph", value = "发票号", required = true, paramType = "path")
    public void zfBdcFph(@PathVariable(value="fph") String fph) {
        this.bdcXtFphService.zfBdcFph(fph);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("取消发票号")
    @ApiImplicitParam(name = "fph", value = "发票号", required = true, paramType = "path")
    public void qxBdcFph(@PathVariable(value="fph") String fph) {
        this.bdcXtFphService.qxBdcFph(fph);
    }

    /**
     * @param qxdm 区县代码
     * @return {String} 领取方式
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取指定区县代码对应的发票号领取方式
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取指定区县代码对应的发票号领取方式")
    @ApiImplicitParam(name = "qxdm", value = "区县代码", required = true, paramType = "String")
    public String getFphLqfs(@RequestParam("qxdm") String qxdm) {
        return propsConfig.getFphlqfs(qxdm);
    }
    
    /**
     * @return {String} 领取方式
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取区县代码对应的发票号领取方式所有配置项
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取区县代码对应的发票号领取方式所有配置项")
    public Map<String, String> getAllFphLqfs() {
        return propsConfig.getFphlqfs();
    }
    
    /**
     * @param bdcSlSfxxDOList slbh
     * @param slbh
     * @return {List<BdcFphDO>}
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取可用发票号，并且更新使用状态和使用明细
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取可用发票号，并且更新使用状态和使用明细")
    @ApiImplicitParams({@ApiImplicitParam(name = "BdcSlSfxxDO", value = "收费信息", required = true, paramType = "body"), @ApiImplicitParam(name = "slbh", value = "受理编号", required = true, paramType = "query")})
    public List<BdcSlSfxxDO> getBdcFph(@RequestBody List<BdcSlSfxxDO> bdcSlSfxxDOList, @RequestParam(name = "slbh") String slbh) {
        List<BdcFphDO> bdcFphDOList = bdcXtFphService.queryBdcFphDOList(bdcSlSfxxDOList);
        return bdcXtFphService.getBdcFph(bdcSlSfxxDOList, slbh, bdcFphDOList);
    }

    /**
     * 获取可用发票号，根据指定发票类别更新对应字段的发票号 「jspzfph 和 fssrfph」并且更新使用状态和使用明细
     *
     * @param bdcSlSfxxDOList
     * @param slbh
     * @param fplb
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO>
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @Override
    public List<BdcSlSfxxDO> getBdcFph(@RequestBody List<BdcSlSfxxDO> bdcSlSfxxDOList, @RequestParam(name = "slbh") String slbh,
                                       @RequestParam(name = "fplb") String fplb) {
        List<BdcFphDO> bdcFphDOList = bdcXtFphService.queryBdcFphDOList(bdcSlSfxxDOList);
        return bdcXtFphService.getBdcFph(bdcSlSfxxDOList, slbh, fplb, bdcFphDOList);
    }

    @Override
    public List<BdcFphDO> getBdcFphxx(@RequestParam(value="count") int count) {
        return bdcXtFphService.getBdcFphDOList(count);
    }

    @Override
    public Integer getSyqk(String fph) {
        return bdcXtFphService.getBdcFphSyzt(fph);
    }

    @Override
    public Integer syqkEdit(@RequestBody List<BdcFphDO> bdcFphDOList) {
        return bdcXtFphService.syqkEdit(bdcFphDOList);
    }

    @Override
    public Boolean checkYyFph(String fph) {
        BdcFphQO bdcFphQO = new BdcFphQO();
        bdcFphQO.setFph(fph);
        return bdcXtFphService.checkYyFph(bdcFphQO);
    }
    
    @Override
    public Boolean saveSfxxFph(@RequestBody BdcSlSfxxDO bdcSlSfxxDO, String slbh, String fph) {
        if (StringUtils.isAnyBlank(slbh, fph)) {
            throw new AppException("参数缺失！");
        }
        BdcFphDO bdcFphDO = bdcXtFphService.getBdcFphDO(fph);

        if(Objects.isNull(bdcFphDO)){
            return false;
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>();
        List<BdcFphDO> bdcFphDOList = new ArrayList<>();
        bdcSlSfxxDOList.add(bdcSlSfxxDO);
        bdcFphDOList.add(bdcFphDO);
        return CollectionUtils.isNotEmpty(bdcXtFphService.getBdcFph(bdcSlSfxxDOList, slbh, bdcFphDOList));
    }

    /**
     * @param fphid 发票号id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取发票号使用明细
     * @date : 2020/11/26 18:29
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取发票号使用明细")
    @ApiImplicitParam(name = "fphid", value = "发票号id", required = true, paramType = "path")
    public List<BdcFphSymxDTO> listFphSymx(@PathVariable(value = "fphid") String fphid) {
        return bdcXtFphService.listFphSymx(fphid);
    }

    /**
     * @param fph
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据发票号查询发票信息
     * @date : 2021/9/16 9:06
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据发票号获取发票号信息")
    @ApiImplicitParam(name = "fphid", value = "发票号id", required = true, paramType = "path")
    public BdcFphDO listBdcFphxx(@PathVariable(value = "fph") String fph) {
        return bdcXtFphService.getBdcFphDO(fph);
    }
}

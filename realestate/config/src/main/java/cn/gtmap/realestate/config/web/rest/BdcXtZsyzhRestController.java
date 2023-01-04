package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcReturnData;
import cn.gtmap.realestate.common.core.qo.config.BdcYzhQO;
import cn.gtmap.realestate.common.core.service.rest.config.BdcXtZsyzhRestService;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcYzhVO;
import cn.gtmap.realestate.config.config.PropsConfig;
import cn.gtmap.realestate.config.service.BdcXtZsyzhService;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/30
 * @description 业务配置系统：证书印印制号配置请求服务处理
 */
@RestController
@Api(tags = "不动产证书印制号印制号配置服务接口")
public class BdcXtZsyzhRestController implements BdcXtZsyzhRestService {
    @Autowired
    private BdcXtZsyzhService bdcXtZsyzhService;

    @Autowired
    private PropsConfig propsConfig;

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable 分页对象
     * @param zsyzhParamJson 查询条件
     * @return {Page} 证书印制号配置分页数据
     * @description 查询证书印制号配置数据列表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询证书印制号数据列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "zsyzhParamJson", value = "证书印制号查询参数JSON", required = false, paramType = "query")})
    public Page<BdcYzhDO> listBdcZsyzh(Pageable pageable,
                                       @RequestParam(name = "zsyzhParamJson", required = false) String zsyzhParamJson) {

        return bdcXtZsyzhService.listBdcXtZsyzh(pageable, JSON.parseObject(zsyzhParamJson, BdcYzhQO.class));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYzhVO 证书印制号模板
     * @return {int} 操作数据记录数
     * @description 生成证书印制号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("生成证书印制号")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhVO", value = "证书印制号配置模板", required = true, paramType = "body")})
    public int generateBdcZsyzh(@RequestBody BdcYzhVO bdcYzhVO) {
        if(null == bdcYzhVO){
            throw new NullPointerException("证书印制号配置模板为空！");
        }

        return bdcXtZsyzhService.generateBdcZsyzh(bdcYzhVO);
    }

    /**
    * @return 提取领取与撤回印制号
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description
    */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("提取领取或撤销印制号")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhVO", value = "证书印制号配置模板", required = true, paramType = "body")})
    public BdcReturnData extractData(@RequestBody BdcYzhVO bdcYzhVO) {
        if(null == bdcYzhVO){
            throw new NullPointerException("证书印制号配置模板为空！");
        }
        BdcReturnData bdcReturnData = new BdcReturnData();
        bdcReturnData = bdcXtZsyzhService.extractData(bdcYzhVO);

        return bdcReturnData;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYzhDO 证书印制号配置实体
     * @return {int} 操作数据记录数
     * @description 保存证书印制号配置配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存证书印制号配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhDO", value = "证书印制号配置实体", required = true, paramType = "body")})
    public int saveBdcZsyzh(@RequestBody BdcYzhDO bdcYzhDO) {
        if(null == bdcYzhDO || StringUtils.isEmpty(bdcYzhDO.getYzhid())){
            throw new NullPointerException("证书印制号配置为空！");
        }

        return bdcXtZsyzhService.saveBdcZsyzh(bdcYzhDO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYzhDOList 证书印制号配置实体集合
     * @return {int} 操作数据记录数
     * @description 删除证书印制号配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除证书印制号")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhDOList", value = "证书印制号配置集合", required = true, paramType = "body")})
    public int deleteBdcZsyzh(@RequestBody List<BdcYzhDO> bdcYzhDOList) {
        return bdcXtZsyzhService.deleteBdcZsyzh(bdcYzhDOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYzhsymxDO 证书印制号使用明细
     * @return {int} 操作数据记录数
     * @description 作废证书印制号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("作废证书印制号")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhsymxDO", value = "证书印制号使用明细", required = true, paramType = "body")})
    public void deleteBdcZsyzh(@RequestBody BdcYzhsymxDO bdcYzhsymxDO) {
        if(null == bdcYzhsymxDO){
            throw new NullPointerException("证书印制号使用明细为空！");
        }

        bdcXtZsyzhService.deleteBdcZsyzh(bdcYzhsymxDO);
    }

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param bdcYzhsymxDO 证书印制号使用明细
     * @return {int} 操作数据记录数
     * @description 还原证书印制号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("还原证书印制号")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhsymxDO", value = "证书印制号使用明细", required = true, paramType = "body")})
    public void revertBdcZsyzh(@RequestBody BdcYzhsymxDO bdcYzhsymxDO) {
        if(null == bdcYzhsymxDO){
            throw new NullPointerException("证书印制号使用明细为空！");
        }

        bdcXtZsyzhService.revertBdcZsyzh(bdcYzhsymxDO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  qxdm 区县代码
     * @return {String} 领取方式
     * @description 获取指定区县代码对应的印制号领取方式
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取指定区县代码对应的印制号领取方式")
    @ApiImplicitParams({@ApiImplicitParam(name = "qxdm", value = "区县代码", required = true, paramType = "path")})
    public String getZsyzhLqfs(@PathVariable(value = "qxdm", required = true) String qxdm){
        return propsConfig.getZsyzhLqfs(qxdm);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 领取方式
     * @description 获取区县代码对应的印制号领取方式所有配置项
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取区县代码对应的印制号领取方式所有配置项")
    public Map<String, String> getAllZsyzhLqfs() {
        return propsConfig.getYzhlqfs();
    }

}

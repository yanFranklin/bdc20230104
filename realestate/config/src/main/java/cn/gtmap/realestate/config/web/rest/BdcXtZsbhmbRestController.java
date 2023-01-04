package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXtZsbhmbDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.config.BdcXtZsbhmbQO;
import cn.gtmap.realestate.common.core.service.rest.config.BdcXtZsbhmbRestService;
import cn.gtmap.realestate.config.service.BdcXtZsbhmbService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/11
 * @description 业务配置系统：证书编号模板配置请求服务处理
 */
@RestController
@Api(tags = "不动产证书编号模板配置服务接口")
public class BdcXtZsbhmbRestController implements BdcXtZsbhmbRestService {
    @Autowired
    private BdcXtZsbhmbService bdcXtZsbhmbService;


    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  pageable 分页对象
     * @param  zsbhmbParamJson 查询条件
     * @return {Page} 证书编号模板分页数据
     * @description  查询证书编号模板数据列表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询证书编号模板数据列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "zsbhmbParamJson", value = "证书编号模板查询参数JSON", required = false, paramType = "query")})
    public Page<BdcXtZsbhmbDO> queryBdcXtZsbhmb(Pageable pageable,
                                             @RequestParam(name = "zsbhmbParamJson", required = false) String zsbhmbParamJson) {

        return bdcXtZsbhmbService.queryBdcXtZsbhmb(pageable, JSON.parseObject(zsbhmbParamJson, BdcXtZsbhmbQO.class));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXtZsbhmbDO 证书编号模板实体
     * @return {int} 操作数据记录数
     * @description 保存证书编号模板配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存证书编号模板配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXtZsbhmbDO", value = "证书编号模板配置", required = true, paramType = "body")})
    public int saveBdcXtZsbhmb(@RequestBody BdcXtZsbhmbDO bdcXtZsbhmbDO) {
        if(null == bdcXtZsbhmbDO || StringUtils.isBlank(bdcXtZsbhmbDO.getNf()) || StringUtils.isBlank(bdcXtZsbhmbDO.getQxdm())){
            throw new NullPointerException("配置系统-保存证号模板：参数‘证书编号模板配置’为空！");
        }

        return bdcXtZsbhmbService.saveBdcXtZsbhmb(bdcXtZsbhmbDO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXtZsbhmbDOList 证书编号模板集合
     * @return {int} 操作数据记录数
     * @description 删除证书编号模板
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除证书编号模板")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXtZsbhmbDOList", value = "证书编号模板配置集合", required = true, paramType = "body")})
    public int deleteBdcXtZsbhmb(@RequestBody List<BdcXtZsbhmbDO> bdcXtZsbhmbDOList) {
        return bdcXtZsbhmbService.deleteBdcXtZsbhmb(bdcXtZsbhmbDOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 省区代码
     * @description 从证书模板配置中获取省区代码（正常来说所有模板配置的是一致的）
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取省区代码")
    public String querySqdm() {
        return bdcXtZsbhmbService.querySqdm();
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param zsbhmbid 证书编号模板ID
     * @return｛String｝复制后新的证书编号模板ID
     * @description 复制当前证书编号模板的信息，并创建一个新的证书编号模板
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("复制证书编号模板信息")
    @ApiImplicitParam(name = "zsbhmbid", value = "证书编号模板ID", required = true, paramType = "path")
    public String copyBdcXtZsbhmb(@PathVariable("zsbhmbid") String zsbhmbid) {
        if(StringUtils.isBlank(zsbhmbid)){
            throw new AppException("配置系统-复制证号模板：参数‘证书编号模板ID’为空！");
        }
        return bdcXtZsbhmbService.copyBdcXtZsbhmb(zsbhmbid);
    }
}

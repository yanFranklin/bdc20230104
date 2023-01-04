package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.service.rest.config.BdcXtMryjPzRestService;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcXtMryjPzVO;
import cn.gtmap.realestate.config.service.BdcXtMryjPzService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/1/29
 * @description 不动产系统默认意见配置服务接口
 */
@RestController
@Api(tags = "不动产系统默认意见配置服务接口")
public class BdcXtMryjPzRestController implements BdcXtMryjPzRestService {
    @Autowired
    BdcXtMryjPzService bdcXtMryjPzService;

    /**
     * @param pageable
     * @param mryjParamJson
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取默认意见
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询默认意见配置列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "mryjParamJson", value = "默认意见查询参数JSON", required = false, paramType = "query")})
    public Page<BdcXtMryjPzVO> listBdcXtMryj(Pageable pageable, @RequestParam(name = "mryjParamJson",required = false) String mryjParamJson) {
        return bdcXtMryjPzService.listBdcXtMryjByPage(pageable, JSON.parseObject(mryjParamJson, BdcXtMryjDO.class));
    }

    /**
     * @param bdcXtMryjDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存默认意见
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存系统默认意见配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXtMryjDO", value = "不动产系统默认意见配置", required = true, paramType = "body")})
    public int saveBdcXtMryj(@RequestBody BdcXtMryjDO bdcXtMryjDO) {
        return bdcXtMryjPzService.saveBdcXtMryj(bdcXtMryjDO);
    }

    /**
     * @param bdcXtMryjDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改默认意见
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("修改系统默认意见配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXtMryjDO", value = "不动产系统默认意见配置", required = true, paramType = "body")})
    public int updateBdcXtMryj(@RequestBody BdcXtMryjDO bdcXtMryjDO) {
        return bdcXtMryjPzService.updateBdcXtMryj(bdcXtMryjDO);
    }

    /**
     * @param bdcXtMryjDOList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除默认意见
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除默认意见配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXtMryjDOList", value = "默认意见配置集合", required = true, paramType = "body")})
    public int deleteBdcXtMryj(@RequestBody List<BdcXtMryjDO> bdcXtMryjDOList) {
        return bdcXtMryjPzService.deleteBdcXtMryj(bdcXtMryjDOList);
    }

    /**
     * @param bdcXtMryjDO
     * @return List<BdcXtMryjDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 默认意见配置
     */
    @Override
    public List<BdcXtMryjDO> listMryjpz(BdcXtMryjDO bdcXtMryjDO) {
        return null;
    }
}

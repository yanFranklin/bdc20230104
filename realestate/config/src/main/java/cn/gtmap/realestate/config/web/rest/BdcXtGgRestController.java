package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcGgDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtGgDO;
import cn.gtmap.realestate.common.core.service.rest.config.BdcXtGgRestService;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcXtGgVO;
import cn.gtmap.realestate.config.core.service.BdcGgService;
import cn.gtmap.realestate.config.core.service.BdcXtGgService;
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
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/19
 * @description 不动产公告配置服务接口
 */
@RestController
@Api(tags = "不动产公告配置服务接口")
public class BdcXtGgRestController implements BdcXtGgRestService {

    @Autowired
    private BdcXtGgService bdcXtGgService;

    @Autowired
    private BdcGgService bdcGgService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取公告配置分页数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "bdcXtGgJson", value = "公告配置查询参数JSON", required = false, paramType = "query")})
    public Page<BdcXtGgVO> listBdcXtGgByPage(Pageable pageable, @RequestParam(name = "bdcXtGgJson", required = false) String bdcXtGgJson){
        return bdcXtGgService.listBdcXtGgByPage(pageable,JSON.parseObject(bdcXtGgJson, BdcXtGgDO.class));

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存更新公告配置信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXtGgDO", value = "公告配置信息", required = true, paramType = "body")})
    public int saveOrUpdateBdcXtGg(@RequestBody BdcXtGgDO bdcXtGgDO){
        return bdcXtGgService.saveOrUpdateBdcXtGg(bdcXtGgDO);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除公告配置信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xtggidList", value = "需要删除的公告配置集合", required = true, paramType = "body")})
    public int deleteBdcXtGg(@RequestBody List<String> xtggidList){
        return bdcXtGgService.deleteBdcXtGg(xtggidList);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据配置的sql生成公告信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "bdcXtGgDO", value = "公告配置信息", required = true, paramType = "query")})
    public BdcGgDO generateXtggBySql(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid") String xmid, @RequestBody BdcXtGgDO bdcXtGgDO){
        return bdcXtGgService.generateXtggBySql(gzlslid, xmid, bdcXtGgDO);


    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量根据项目获取配置的sql生成公告信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmDOS", value = "项目集合", required = true, paramType = "query"),
            @ApiImplicitParam(name = "sply", value = "审批来源", required = true, paramType = "query"),
            @ApiImplicitParam(name = "gglx", value = "公告类型", required = true, paramType = "query")})
    public List<BdcGgDO> generateXtggBySqlPl(@RequestBody List<BdcXmDO> xmDOS, @RequestParam(name = "sply") Integer sply, @RequestParam(name = "gglx") Integer gglx){
        return bdcXtGgService.generateXtggBySqlPl(xmDOS,sply,gglx);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量新增不动产公告信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmDOS", value = "项目集合", required = true, paramType = "query")})
    public int insertBatchBdcGg(@RequestBody List<BdcGgDO> bdcGgDOS){
        return bdcGgService.insertBatchBdcGg(bdcGgDOS);

    }
}

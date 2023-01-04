package cn.gtmap.realestate.natural.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcJgLzrGxDO;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.dto.init.BdcJgLzrGxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXtjgLzrQO;
import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyXtJgRestService;
import cn.gtmap.realestate.natural.service.ZrzyXtJgService;
import cn.gtmap.realestate.natural.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 不动产系统银行配置查询接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/05/02
 */
@RestController
@Api(tags = "不动产银行配置服务接口")
public class ZrzyXtJgRestController extends BaseController implements ZrzyXtJgRestService {

    @Autowired
    private ZrzyXtJgService zrzyXtJgService;

    /**
     * 查询机构配置信息
     *
     * @return List<BdcXtJgDO>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ApiOperation(value = "查询银行配置信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "jglb", value = "机构类型", dataType = "Integer", paramType = "query")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcXtJgDO> listBdcXtJg(@RequestParam("jglb") Integer jglb) {
        return zrzyXtJgService.listBdcXtJg(jglb);
    }

    /**
     * 根据 jgmc 查询出机构名称信息
     *
     * @param jgmc 机构名称
     * @return BdcXtJgDO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ApiOperation(value = "根据 jgmc 查询出机构信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "jgmc", value = "机构名称", dataType = "String", paramType = "query")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcXtJgDO queryJgByJgmc(@RequestParam(value = "jgmc") String jgmc, @RequestParam(value = "jglb", required = false) String jglb) {
        return zrzyXtJgService.queryJgByJgMc(jgmc, jglb);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据系统机构查询参数，获取不动产系统机构信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcXtJgDO", value = "不动产系统机构DO", dataType = "Page", paramType = "body")})
    public List<BdcXtJgDO> queryBdcXtJg(@RequestBody BdcXtJgDO bdcXtJgDO) {
        return zrzyXtJgService.listBdcXtJg(bdcXtJgDO);
    }

    /**
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取按月结算 银行结构
     */
    @Override
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcXtJgDO> listAyjsBdcXtJg() {
        return zrzyXtJgService.listAyjsBdcXtJg(null);
    }

    /**
     * @return
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取按月结算 银行结构
     */
    @Override
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcXtJgDO> listAyjsBdcXtJgYhmc(@RequestParam("yhmc") String yhmc) {
        return zrzyXtJgService.listAyjsBdcXtJg(yhmc);
    }

    @Override
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcXtJgDO> listAyjsBdcXtJgYhmcLike(@RequestParam("yhmc") String yhmc) {
        return zrzyXtJgService.listAyjsBdcXtJgLike(yhmc);
    }

    /**
     * 根据机构id获取配置的lzr
     *
     * @param jgid
     * @return list
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @ApiOperation(value = "根据 jgid 查询配置的领证人")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "jgid", value = "机构id", dataType = "String", paramType = "query")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcJgLzrGxDO> queryJgLzrByJgid(@RequestParam(value = "jgid") String jgid) {
        return zrzyXtJgService.queryJgLzrByJgid(jgid);
    }

    /**
     * 分页查询机构领证人
     *
     * @param pageable
     * @param bdcXtjgLzrQOJSON
     * @return BdcXtJgDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询机构领证人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcXtjgLzrQOJSON", value = "参数JSON",
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageable", value = "分页参数",
                    dataType = "Page", paramType = "body")})
    public Page<BdcJgLzrGxDTO> queryJgLzrByPage(Pageable pageable, String bdcXtjgLzrQOJSON) {
        BdcXtjgLzrQO bdcXtjgLzrQO = new BdcXtjgLzrQO();
        if (StringUtils.isNotBlank(bdcXtjgLzrQOJSON)) {
            bdcXtjgLzrQO = JSONObject.parseObject(bdcXtjgLzrQOJSON, bdcXtjgLzrQO.getClass());
        }
        return zrzyXtJgService.queryJgLzrByPage(pageable, bdcXtjgLzrQO);
    }

    /**
     * @param gxid 关系id
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据关系id删除机构领证人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据关系id删除机构领证人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "关系id", value = "gxid",
                    dataType = "String", paramType = "query")})
    public void deleteJglzr(@PathVariable("gxid") String gxid) {
        zrzyXtJgService.deleteJglzr(gxid);

    }

    /**
     * @param bdcJgLzrGxDO 机构领证人信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 新增机构领证人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "新增机构领证人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "机构领证人", value = "bdcJgLzrGxDO",
                    dataType = "String", paramType = "body")})
    public BdcJgLzrGxDO insertBdcJgLzr(@RequestBody BdcJgLzrGxDO bdcJgLzrGxDO) {
        zrzyXtJgService.insertBdcLzr(bdcJgLzrGxDO);
        return bdcJgLzrGxDO;
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量修改系统机构是否可用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "银行名称集合", value = "yhmcList", dataType = "string", paramType = "body"),
            @ApiImplicitParam(name = "是否可用", value = "sfky", dataType = "string", paramType = "path")
    })
    public void batchModifyXtJgSfky(@RequestBody List<String> yhmcList, @PathVariable(name = "sfky") int sfky) {
        zrzyXtJgService.batchModifyXtJgSfky(yhmcList, sfky);
    }

    /**
     * @param bdcXtJgDO 机构信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 新增机构
     */
    @Override
    public BdcXtJgDO insertBdcJg(@RequestBody BdcXtJgDO bdcXtJgDO) {
        zrzyXtJgService.insertBdcXtJg(bdcXtJgDO);
        return bdcXtJgDO;
    }

    /**
     * @param jgmclist 机构名称集合
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description
     */
    @Override
    public List<BdcXtJgDO> queryBatchBdcXtJg(@RequestBody List<String> jgmclist) {
        List<BdcXtJgDO> bdcXtJgDOList = zrzyXtJgService.queryBatchBdcXtJg(jgmclist);
        return bdcXtJgDOList;
    }
}

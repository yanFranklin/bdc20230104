package cn.gtmap.realestate.register.web.rest.xxbl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.register.BdcBlxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.service.rest.register.BdcXxblRestService;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblService;
import cn.gtmap.realestate.register.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 不动产信息补录服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.1, 2020/03/16 21:02
 */
@RestController
@Api(tags = "不动产信息补录服务接口")
public class BdcXxblRestController extends BaseController implements BdcXxblRestService {
    /**
     * 补录数据服务
     */
    @Autowired
    private BdcXxblService bdcXxblService;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private BdcDbxxFeignService bdcDbxxFeignService;

    /**
     * 信息补录 注销权力时更新权利附记，默认不更新
     */
    @Value("${xxbl.zxql.updateFj:false}")
    private String updateZxqlFj;

    /**
     * 是否不需要审核
     */
    @Value("${xxbl.bxysh:false}")
    private boolean BLSHLX_NOSH;
    /**
     * 初始化补录数据
     *
     * @param bdcBlxxDTO 补录传输对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "初始化补录数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcBlxxDTO", value = "补录信息实体", dataType = "BdcBlxxDTO")})
    public List<BdcXmDO> cshBlxx(@RequestBody BdcBlxxDTO bdcBlxxDTO) throws Exception {
        return bdcXxblService.cshBlxx(bdcBlxxDTO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "初始化补录流程数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcBlxxDTO", value = "补录信息实体", dataType = "BdcBlxxDTO")})
    public Object cshLcxx(@RequestBody BdcBlxxDTO bdcBlxxDTO) throws Exception {
        return bdcXxblService.cshLcxx(bdcBlxxDTO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "初始化批量补录流程数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcBlxxDTO", value = "补录信息实体", dataType = "BdcBlxxDTO")})
    public Object cshPlxxblLcxx(@RequestBody List<BdcBlxxDTO> bdcBlxxDTOS) throws Exception {
        return bdcXxblService.cshPlxxblLcxx(bdcBlxxDTOS);
    }

    @Override
    public void endLc(@PathVariable(value = "processInsId") String processInsId, @PathVariable(value = "currentUserName") String currentUserName) {
        bdcXxblService.endLc(processInsId, currentUserName);
    }

    /**
     * @param processInsId
     * @param currentUserName
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 信息修改结束后判断上一手数据是否存在匹配关系或者被匹配关系，如果存在同样需要更新关联关系
     * @date : 2022/9/20 16:33
     */
    @Override
    public void endSjxgPpgx(@PathVariable(value = "processInsId") String processInsId, @PathVariable(value = "currentUserName") String currentUserName) throws Exception {
        bdcXxblService.endSjxgPpgx(processInsId, currentUserName);
    }

    @Override
    public void endModify(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "jsyy", required = false) String jsyy) {
        bdcXxblService.endModify(gzlslid, jsyy);
    }

    /**
     * @param processInsId 修改流程的工作流实例 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 删除补录修改流程数据 <br>
     * 1. 删除证书锁定数据 <br>
     * 2. 同步权籍证书锁定状态 <br>
     * 3. 还原补录修改流程数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除补录修改流程数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流定义 ID", dataType = "String", paramType = "path")})
    public void deleteBllcModify(@PathVariable("processInsId") String processInsId) throws Exception {
        bdcXxblService.deleteBllcModify(processInsId);
    }

    @Override
    public Object cshModify(@RequestBody BdcBlxxDTO bdcBlxxDTO) {
        return bdcXxblService.cshModify(bdcBlxxDTO);
    }

    /**
     * @param bdcBlxxDTO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 信息补录删除流程接口，只初始化受理项目，基本信息和受理项目历史关系
     * @date : 2022/8/30 15:00
     */
    @Override
    public Object cshDeletelc(@RequestBody BdcBlxxDTO bdcBlxxDTO) {
        return bdcXxblService.cshDeleteOrDelete(bdcBlxxDTO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "复制补录数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String"),
            @ApiImplicitParam(name = "gzldyid", value = "工作流定义 ID", dataType = "String")})
    public List<BdcXmDO> copyBlxx(String yxmid, String bdcdyh) throws Exception {
        return bdcXxblService.copyBlxx(yxmid, bdcdyh);
    }

    @Override
    public void glcq(String gzlslid, String xmid,String blxmid) {
        bdcXxblService.glcq(gzlslid, xmid,blxmid);
    }

    @Override
    public String glsjyz(String gzlslid, String yxmid) {
        return bdcXxblService.glsjyz(gzlslid, yxmid);
    }

    /**
     * 挂接主房信息
     *
     * @param yxmid  被挂接的项目 id
     * @param bdcdyh 需要挂接的不动产单元
     * @return {BdcXmDO} 挂接后生成的项目信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 将传入的不动产单元号挂接到 yxmid 对应的项目 <br>
     * 场景：车库挂接主房
     * 1. 生成项目、fdcq、权利人和证书关系并且插入 cshfwkgsl <strong> zlcsh = 1 作为挂接项目的标识<strong/>
     * 2. 和 bdcdy 相关信息取权籍，其余取自原项目数据
     * 3. 受理人相关信息获取当前用户，受理时间取系统时间
     */
    @Override
    public BdcXmDO gjZfxx(String yxmid, String bdcdyh, @RequestParam(value = "qjgldm",required = false) String qjgldm) throws Exception {
        return bdcXxblService.gjZfxx(yxmid, bdcdyh,qjgldm);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询是否生成证书")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目 id", dataType = "String"),
            @ApiImplicitParam(name = "djxl", value = "登记小类", dataType = "String")})
    public int querySfsczs(String xmid, String djxl) {
        return bdcXxblService.querySfsczs(xmid, djxl);
    }

    /**
     * @param xmid xmid 用于判断删除的是挂接项目还是主项目
     * @param all  是否删除全部，true：删除全部，false：只删除当前项目「用于删除挂接项目」
     * @return {boolean} 是否删除成功
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 删除补录数据，补录台账删除数据 <br>
     * 删除业务数据和补录审核数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除补录信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "String"),
            @ApiImplicitParam(name = "all", value = "是否删除全部", dataType = "boolean")})
    public boolean deleteBlxx(String xmid, boolean all) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }
        // 删除补录数据
        return bdcXxblService.deleteBlxx(xmid, all);
    }

    /**
     * @param xmid xmid
     * @return {BdcXmDTO} 项目对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 判断项目是否生成权利 <br>
     * 生成返回当前项目的 gzlslid 和 xmid <br>
     * 不生成返回上一手项目的 gzlslid 和 xmid <br>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "判断项目是否生成权利")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "String")})
    public BdcXmDO querySfscQl(String xmid) {
        return bdcXxblService.querySfscQl(xmid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据xmid列表更新注销权利附记")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmidsList", value = "项目ID列表", dataType = "String")})
    public void updateZxqlFj(@RequestBody List<String> xmidList) {
        if(CollectionUtils.isNotEmpty(xmidList)) {
            if(Boolean.valueOf(updateZxqlFj)) {
                for(String xmid : xmidList) {
                    BdcQl bdcZxQl = bdcQllxFeignService.queryQlxx(xmid);
                    if(bdcZxQl != null) {
                        bdcDbxxFeignService.generateAndUpdateZxqlFj(bdcZxQl);
                    }
                }
            }
        }
    }

    /**
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询是否不需要是审核")
    public boolean bxysh() {
        return BLSHLX_NOSH;
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "信息补录选择产权补录(仅限抵押权)")
    @ApiImplicitParams({@ApiImplicitParam(name = "yxmid", value = "yxmid", dataType = "String"),
            @ApiImplicitParam(name = "bdcdyhList", value = "xmid集合", dataType = "String")})
    public List<BdcXmDO> plbl(@RequestParam("yxmid") String yxmid, @RequestParam("xmidList") String xmidListStr) throws Exception {
        return bdcXxblService.plbl(yxmid,xmidListStr);
    }


    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "信息补录选择不动产单元批量补录")
    @ApiImplicitParams({@ApiImplicitParam(name = "yxmid", value = "yxmid", dataType = "String"),
            @ApiImplicitParam(name = "bdcdyhList", value = "不动产单元号集合", dataType = "String")})
    public List<BdcXmDO> selectBdcdyhPlbl(@RequestParam("yxmid") String yxmid, @RequestParam("bdcdyhList") String bdcdyhListStr) throws Exception {
        return bdcXxblService.selectBdcdyhPlbl(yxmid,bdcdyhListStr);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "初始化删除信息补录流程")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcBlxxDTO", value = "补录信息实体", dataType = "BdcBlxxDTO")})
    public Object cshDeleteXxblLc(@RequestBody BdcBlxxDTO bdcBlxxDTO) throws Exception {
        return bdcXxblService.cshDeleteXxblLc(bdcBlxxDTO);
    }

}

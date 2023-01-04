package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.service.BdcFzjlService;
import cn.gtmap.realestate.certificate.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlLzrQzxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlZsDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcFzjlPdfDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.register.BdcQzxxQO;
import cn.gtmap.realestate.common.core.service.rest.certificate.BdcFzjlRestService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcSzxxVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/27
 * @description 发证记录服务接口
 */
@RestController
@Api(tags = "发证记录服务接口")
public class BdcFzjlRestController extends BaseController implements BdcFzjlRestService {
    @Autowired
    BdcFzjlService bdcFzjlService;
    @Autowired
    BdcZsService bdcZsService;

    /**
     * @param xmid       项目主ID
     * @param bdcFzjlDTO 发证记录DTO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新发证记录信息（领证人和备注信息）
     */

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存发证记录领证人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "sfhb", value = "是否合并批量更新", dataType = "boolean", paramType = "path", required = true),
            @ApiImplicitParam(name = "bdcFzjlDTO", value = "发证记录信息", dataType = "BdcFzjlDTO", paramType = "body", required = true)})
    @Override
    public int updateFzjl(@PathVariable(name = "xmid") String xmid, @PathVariable(name = "sfhb") boolean sfhb, @RequestBody BdcFzjlDTO bdcFzjlDTO) {
        int result = bdcFzjlService.updateFzjlLzr(bdcFzjlDTO.getBdcFzjlZsDTOList());
        result += bdcFzjlService.updateFzjlBz(xmid, sfhb, bdcFzjlDTO.getBz());
        if(StringUtils.isNotBlank(bdcFzjlDTO.getFzyj())){
            result += bdcFzjlService.updateFzjlFzyj(xmid, sfhb, bdcFzjlDTO.getFzyj());
        }
        return result;
    }

    /**
     * @param zsid 证书ID
     * @return BdcFzjlDTO 发证记录信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前证书的发证记录
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询当前证书的发证记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "证书ID", dataType = "string", paramType = "path")})
    @Override
    public BdcFzjlDTO queryBdcZsFzjl(@PathVariable(name = "zsid") String zsid) {
        return bdcFzjlService.queryBdcZsFzjl(zsid);
    }

    /**
     * @param xmid 项目ID
     * @return BdcFzjlDTO 查询的发证记录
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询发证记录
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询当前项目发证记录信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "string", paramType = "path")})
    @Override
    public BdcFzjlDTO queryFzjl(@PathVariable(name = "xmid") String xmid) {
        return bdcFzjlService.queryFzjl(xmid, null);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param bdcXmDOList
     * @return 流程的发证记录
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 合并显示流程的发证记录
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("合并显示流程的发证记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")})
    @Override
    public BdcFzjlDTO queryHbFzjl(@PathVariable(name = "gzlslid") String gzlslid, @RequestBody List<BdcXmDO> bdcXmDOList) {
        return bdcFzjlService.queryHbFzjl(gzlslid, bdcXmDOList);
    }

    /**
     * @param gzlslid     工作流实例ID
     * @param bdcXmDOList 流程所有的项目信息
     * @return 发证记录信息（一个发证记录）
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 流程发一个发证记录（基本信息取一个项目，证书信息获取流程所有的证书信息）
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("流程发一个发证记录（基本信息取一个项目，证书信息获取流程所有的证书信息）")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")})
    @Override
    public BdcFzjlDTO queryLcOneFzjl(@PathVariable(name = "gzlslid") String gzlslid, @RequestBody List<BdcXmDO> bdcXmDOList) {
        return bdcFzjlService.queryLcOneFzjl(gzlslid, bdcXmDOList);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param sfhb    是否合并显示
     * @return List<BdcFzjlDTO> 发证记录list (流程所有项目的fzjl)
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询流程所有项目发证记录信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "sfhb", value = "是否合并显示", dataType = "boolean", paramType = "path")})
    @Override
    public List<BdcFzjlDTO> listBdcFzjl(@PathVariable(name = "gzlslid") String gzlslid, @PathVariable(name = "sfhb") boolean sfhb) {
        return bdcFzjlService.queryListBdcFzjl(gzlslid, sfhb);
    }

    /**
     * @param gzlslid  工作流实例ID
     * @param userName 当前用户名/账户名
     * @return int 更新记录的数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存发证人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
            , @ApiImplicitParam(name = "userName", value = "用户名/账户名", dataType = "string", paramType = "query")})
    @Override
    public int updateFzr(@PathVariable(name = "gzlslid") String gzlslid, @RequestParam(value = "userName") String userName) {
        LOGGER.info("更新发证人获取当前用户名为：{},工作流实例ID：{}", userName, gzlslid);
        UserDto user = super.getUser(userName, "更新发证人");
        return bdcFzjlService.updateFzr(gzlslid, user, false);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param userDto 用户信息
     * @return int 更新记录的数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新流程的发证人
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存发证人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
            , @ApiImplicitParam(name = "userDto", value = "用户信息对象", dataType = "UserDto", paramType = "body")})

    @Override
    public int updateFzr(@PathVariable(name = "gzlslid") String gzlslid, @RequestBody UserDto userDto, @RequestParam(name = "isNullUpdate", required = false) Boolean isNullUpdate) {
        LOGGER.info("更新发证人获取当前用户信息为：{},工作流实例ID：{}", userDto.toString(), gzlslid);
        return bdcFzjlService.updateFzr(gzlslid, userDto, isNullUpdate);
    }

    /**
     * @param gzlslid  工作流实例ID
     * @param userName 当前用户名/账户名
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存缮证人，缮证时间
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存缮证信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
            , @ApiImplicitParam(name = "userName", value = "用户名/账户名", dataType = "string", paramType = "query")})
    @Override
    public BdcSzxxVO updateSzr(@PathVariable(name = "gzlslid") String gzlslid, @RequestParam(value = "userName") String userName) {
        LOGGER.info("更新缮证人获取当前用户名为：{},工作流实例ID：{}", userName, gzlslid);
        UserDto user = super.getUser(userName, "更新缮证人");
        return bdcFzjlService.updateSzr(gzlslid, user);
    }

    /**
     * @param gzlslid  工作流实例ID
     * @param userName 当前用户名/账户名
     * @return int 更新数据量
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 保存缮证人，缮证时间(不管证书表中是否存在缮证人信息都更新)
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存缮证信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
            , @ApiImplicitParam(name = "userName", value = "用户名/账户名", dataType = "string", paramType = "query")})
    @Override
    public BdcSzxxVO updateSzrByGzlslid(@PathVariable(name = "gzlslid") String gzlslid, @RequestParam(value = "userName") String userName) {
        LOGGER.info("更新缮证人获取当前用户名为：{},工作流实例ID：{}", userName, gzlslid);
        UserDto user = super.getUser(userName, "更新缮证人");
        return bdcFzjlService.updateSzrByGzlslid(gzlslid, user);
    }

    /**
     * @param bdcZsQO 证书信息
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新指定证书的缮证人信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新指定证书的缮证人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZsQO", value = "证书信息查询QO", dataType = "BdcZsQO", paramType = "body")})
    @Override
    public BdcSzxxVO updateSzr(@RequestBody BdcZsQO bdcZsQO) {
        return bdcZsService.updateSzr(bdcZsQO.getZsidList(), bdcZsQO.getUserDto());
    }

    /**
     * @param bdcPrintDTO 发证记录打印QO
     * @return String 发证记录打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取发证记录的打印xml
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取发证记录的打印XML")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dylx", value = "发证记录的打印类型", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "query")})

    public String fzjlPrintXml(@RequestBody BdcPrintDTO bdcPrintDTO) {
        return bdcFzjlService.fzjlPrintXml(bdcPrintDTO);
    }

    /**
     * @param bdcPrintDTO 打印对象
     * @return String 发证记录打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 按流程生成发证记录，获取发证记录的打印xml
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取发证记录的打印XML")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dylx", value = "发证记录的打印类型", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "query")})
    public String fzjlOnePrintXml(@RequestBody BdcPrintDTO bdcPrintDTO) {
        return bdcFzjlService.fzjlOnePrintXml(bdcPrintDTO);
    }

    /**
     * @param bdcFzjlZsDTO 发证记录证书信息
     * @return int 执行数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新领证人信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新指定证书的领证人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcFzjlZsDTO", value = "发证记录证书信息", dataType = "BdcZsQO", paramType = "body")})
    @Override
    public int updateLzrxx(@RequestBody BdcFzjlZsDTO bdcFzjlZsDTO) {
        List<BdcFzjlZsDTO> bdcFzjlZsDTOList = new ArrayList(2);
        bdcFzjlZsDTOList.add(bdcFzjlZsDTO);
        return bdcFzjlService.updateFzjlLzr(bdcFzjlZsDTOList);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据工作流实例ID集合更新领证人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcFzjlZsDTO", value = "发证记录证书信息", dataType = "BdcFzjlZsDTO", paramType = "body")})
    @Override
    public void plUpldateLzrxx(@RequestBody BdcFzjlZsDTO bdcFzjlZsDTO) {
        bdcFzjlService.plUpdateLzrxx(bdcFzjlZsDTO);
    }

    /**
     * 检查领证人信息（南通）
     * @param xmid       项目ID
     * @param bdcFzjlDTO 发证记录对象
     * @return String 提示信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("检查领证人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcFzjlZsDTO", value = "发证记录证书信息", dataType = "BdcZsQO", paramType = "body"),
            @ApiImplicitParam(name = "xmid", value = "项目 ID", dataType = "string", paramType = "query")})
    @Override
    public String checkLzr(@RequestParam(value = "xmid") String xmid, @RequestBody BdcFzjlDTO bdcFzjlDTO) {
        return bdcFzjlService.checkLzr(xmid, bdcFzjlDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFzjlLzrQzxxDTO 领证人签字信息
     * @return {BdcQzxxDO} 入库签字信息
     * @description  保存领证人签字图片信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存领证人签字图片信息")
    @ApiImplicitParam(name = "bdcFzjlLzrQzxxDTO", value = "领证人签字信息", dataType = "BdcFzjlLzrQzxxDTO", paramType = "body")
    public BdcQzxxDO saveFzjlLzrQzxx(@RequestBody BdcFzjlLzrQzxxDTO bdcFzjlLzrQzxxDTO) {
        return bdcFzjlService.saveFzjlLzrQzxx(bdcFzjlLzrQzxxDTO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存领证人签字图片信息")
    @ApiImplicitParam(name = "bdcFzjlLzrQzxxDTO", value = "领证人签字信息", dataType = "BdcFzjlLzrQzxxDTO", paramType = "body")
    public List<BdcQzxxDO> plSaveFzjlLzrQzxx(@RequestBody BdcFzjlLzrQzxxDTO bdcFzjlLzrQzxxDTO) {
        return bdcFzjlService.plSaveFzjlLzrQzxx(bdcFzjlLzrQzxxDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @return {String} 领证人签字图片Base64字符串
     * @description  获取领证人签字图片信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取领证人签字图片信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "zsid", value = "证书ID", dataType = "String", paramType = "param")
    })
    public String getFzjlLzrQzxx(@RequestParam("xmid") String xmid, @RequestParam(value="zsid", required = false)String zsid) {
        return bdcFzjlService.getFzjlLzrQzxx(xmid, zsid);
    }

    /**
     * 获取领证人签字图片信息（如果指定顺序号则查询项目指定顺序号签字信息，否则查询项目所有签字信息）
     * @param bdcQzxxQO 签字信息查询参数
     * @return {List} 发证记录领证人签字信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取领证人签字图片信息")
    @ApiImplicitParam(name = "bdcQzxxQO", value = "签字信息查询参数", dataType = "BdcQzxxQO", paramType = "body")
    public List<BdcQzxxDO> getFzjlLzrQzxxs(@RequestBody BdcQzxxQO bdcQzxxQO) {
        return bdcFzjlService.getFzjlLzrQzxxs(bdcQzxxQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFzjlPdfDTO PDF及项目信息
     * @return {String} 文件存储ID
     * @description  保存发证记录文件到大云并关联到当前项目作为附件
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存发证记录文件到大云并关联到当前项目作为附件")
    @ApiImplicitParam(name = "bdcFzjlPdfDTO", value = "PDF及项目信息", dataType = "BdcFzjlPdfDTO", paramType = "body")
    public String saveFzjlPdfFile(@RequestBody BdcFzjlPdfDTO bdcFzjlPdfDTO) {
        return bdcFzjlService.saveFzjlPdfFile(bdcFzjlPdfDTO);
    }
}

package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.register.*;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.register.BdcDjbqlQO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcDjbxxRestService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDjbQlMlVO;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.register.core.service.BdcFdcq3GyxxService;
import cn.gtmap.realestate.register.core.service.BdcFdcq3Service;
import cn.gtmap.realestate.register.core.service.BdcFdcqFdcqXmService;
import cn.gtmap.realestate.register.service.BdcDjbxxService;
import cn.gtmap.realestate.register.service.BdcQlxxService;
import cn.gtmap.realestate.register.service.BdcXmxxService;
import cn.gtmap.realestate.register.util.Constants;
import cn.gtmap.realestate.register.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/8
 * @description 不动产登记簿信息业务Controller
 */
@RestController
@Api(tags = "不动产登记簿信息服务接口")
public class BdcDjbxxRestController extends BaseController implements BdcDjbxxRestService {
    @Autowired
    BdcDjbxxService bdcDjbxxService;
    @Autowired
    BdcQlxxService bdcQlxxService;
    @Autowired
    BdcFdcqFdcqXmService bdcFdcqFdcqXmService;
    @Autowired
    BdcFdcq3GyxxService bdcFdcq3GyxxService;
    @Autowired
    BdcFdcq3Service bdcFdcq3Service;
    @Autowired
    BdcXmxxService bdcXmxxService;
    /**
     * 注销电子证照服务版本
     */
    @Value("${zxdzzz.version:}")
    private String zxdzzzFwVersion;

    /**
     * @param zdzhh 宗地宗海号
     * @return List<BdcQlDjMlDTO> 不动产权利登记目录
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取某宗地宗海的不动产登记权利
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询不动产权利目录信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdzhh", value = "宗地宗海号", required = true, dataType = "string", paramType = "path")})
    @Override
    public List<BdcQlDjMlDTO> listBdcQlDjMl(@PathVariable(value = "zdzhh") String zdzhh) {
        return bdcDjbxxService.listBdcQlDjMl(zdzhh);
    }

    /**
     * @param zdzhh 宗地宗海号
     * @return bdcdyh 不动产单元号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元在登记簿的权利信息，包括返回在登记簿的排序
     */
    @Override
    public BdcQlDjMlDTO indexBdcdyhQlDjMl(@PathVariable(value = "zdzhh") String zdzhh, @PathVariable(value = "bdcdyh") String bdcdyh) {
        return bdcDjbxxService.indexBdcdyhQlDjMl(zdzhh, bdcdyh);
    }

    /**
     * @param bdcDjbqlQO 登记簿查询QO
     * @return Page<BdcQlDjMlDTO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询登记簿权利目录
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询不动产权利目录信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjbqlQO", value = "登记簿查询QO", required = true, dataType = "BdcDjbqlQO", paramType = "body")})
    @Override
    public Page<BdcQlDjMlDTO> bdcQlDjMlByPageJson(@RequestBody BdcDjbqlQO bdcDjbqlQO) {
        if (null == bdcDjbqlQO || StringUtils.isBlank(bdcDjbqlQO.getZdzhh())) {
            throw new MissingArgumentException(Constants.MESSAGE_NOPARAMETER);
        }
        Pageable pageable = new PageRequest(bdcDjbqlQO.getPage(), bdcDjbqlQO.getSize(), bdcDjbqlQO.getSort());
        String json = JSONObject.toJSONString(bdcDjbqlQO);
        Map<String, Object> map = pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));

        map.put("isxn", BdcdyhToolUtils.checkXnbdcdyh(bdcDjbqlQO.getBdcdyh()));
        return repo.selectPaging("listBdcQlDjMlByPage", map, pageable);
    }

    /**
     * @param zdzhh 宗地宗海号
     * @return BdcBdcdjbDO 不动产登记簿
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产登记簿信息
     */

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询不动产登记簿信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdzhh", value = "宗地宗海号", required = true, dataType = "string", paramType = "path")})
    @Override
    public BdcBdcdjbDO queryBdcBdcdjb(@PathVariable(value = "zdzhh") String zdzhh) {
        return bdcDjbxxService.queryBdcBdcdjb(zdzhh);
    }

    /**
     * @param bdcdyh 宗地不动产单元号
     * @return BdcBdcdjbZdjbxxDO 不动产宗地基本信息
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产宗地基本信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询不动产宗地基本信息", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "宗地不动产单元号", required = true, dataType = "string", paramType = "path")})
    @Override
    public BdcBdcdjbZdjbxxDO queryBdcBdcdjbZdjbxx(@PathVariable(value = "bdcdyh") String bdcdyh) {
        return bdcDjbxxService.queryBdcBdcdjbZdjbxx(bdcdyh);
    }

    /**
     * @param zdzhh 宗地宗海号
     * @return BdcBdcdjbZdjbxxDO 不动产宗地基本信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据宗地宗海号查询宗地宗海基本信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据宗地宗海号查询宗地宗海基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdzhh", value = "宗地宗海号", required = true, dataType = "string", paramType = "query")})
    @Override
    public BdcBdcdjbZdjbxxDO queryBdcBdcdjbZdjbxxByZdzhh(@RequestParam(value = "zdzhh") String zdzhh) {
        return bdcDjbxxService.queryBdcBdcdjbZdjbxxByZdzhh(zdzhh);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return List<BdcBdcdjbZdjbxxZdbhqkDO> 不动产宗地变化情况列表
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 查询不动产宗地变化情况
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询不动产宗地变化情况")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "宗地不动产单元号", required = true, dataType = "string", paramType = "path")})
    @Override
    public List<BdcBdcdjbZdjbxxZdbhqkDO> listBdcBdcdjbZdjbxxZdbhqk(@PathVariable(value = "bdcdyh") String bdcdyh) {
        BdcBdcdjbZdjbxxDO bdcBdcdjbZdjbxxDO = queryBdcBdcdjbZdjbxx(bdcdyh);
        if (null != bdcBdcdjbZdjbxxDO && StringUtils.isNotBlank(bdcBdcdjbZdjbxxDO.getZddm())) {
            return bdcDjbxxService.listBdcBdcdjbZdjbxxZdbhqk(bdcBdcdjbZdjbxxDO.getZddm());
        }
        return new ArrayList();
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {BdcBdcdjbZhjbxxDO} 宗海基本信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询不动产登记簿宗海基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    @Override
    public BdcBdcdjbZhjbxxDO queryBdcBdcdjbZhjbxx(@PathVariable(value = "bdcdyh") String bdcdyh) {
        return bdcDjbxxService.queryBdcBdcdjbZhjbxx(bdcdyh);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {List} 宗海基本信息用海状况
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息中的用海状况
     */

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询不动产登记簿宗海基本信息中的用海状况")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    @Override
    public List<BdcBdcdjbZhjbxxYhzkDO> listBdcBdcdjbZhjbxxYhzk(@PathVariable(value = "bdcdyh") String bdcdyh) {
        return bdcDjbxxService.listBdcBdcdjbZhjbxxYhzk(bdcdyh);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {List} 宗海基本信息用海用岛坐标
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息中的用海用岛坐标
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询不动产登记簿宗海基本信息中的用海用岛坐标")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    @Override
    public List<BdcBdcdjbZhjbxxYhydzbDO> listBdcBdcdjbZhjbxxYhydzb(@PathVariable(value = "bdcdyh") String bdcdyh) {
        return bdcDjbxxService.listBdcBdcdjbZhjbxxYhydzb(bdcdyh);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {List} 宗海基本信息宗海变化情况
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息中的宗海变化情况
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询不动产登记簿宗海基本信息中的宗海变化情况")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    @Override
    public List<BdcBdcdjbZhjbxxZhbhqkDO> listBdcBdcdjbZhjbxxZhbhqk(@PathVariable(value = "bdcdyh") String bdcdyh) {
        return bdcDjbxxService.listBdcBdcdjbZhjbxxZhbhqk(bdcdyh);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @param qllx   权利类型
     * @return list
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元的指定类型的权利信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询不动产单元的指定类型的权利信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")
            , @ApiImplicitParam(name = "qllx", value = "权利类型（bdc_zd_qllx表中的dm值，仅用于指定查询的数据库表）", required = true, dataType = "String", paramType = "path")})
    @Override
    public List<BdcQl> listBdcQlxx(@PathVariable(value = "bdcdyh") String bdcdyh, @PathVariable(name = "qllx") String qllx, @RequestBody List<Integer> qsztList) {
        return bdcQlxxService.listBdcQlxx(bdcdyh, qllx, qsztList);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @param qllx   权利类型
     * @return list 注销权利信息
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 查询不动产单元的指定类型的注销权利信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询不动产单元的指定类型的注销权利信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")
            , @ApiImplicitParam(name = "qllx", value = "权利类型（bdc_zd_qllx表中的dm值，仅用于指定查询的数据库表）", required = true, dataType = "String", paramType = "path")})
    @Override
    public List<BdcQl> listBdcZxQlxx(@PathVariable(value = "bdcdyh") String bdcdyh, @PathVariable(name = "qllx") String qllx) {
        return bdcQlxxService.listBdcZxQlxx(bdcdyh, qllx);
    }

    /**
     * @param bdcDjbqlQO 登记簿权利查询QO
     * @return Page<String>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询登记簿权利信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询登记簿权利信息(返回Json字符串)")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjbqlQO", value = "登记簿权利查询QO", required = true, dataType = "BdcDjbqlQO", paramType = "body")})
    @ApiIgnore
    @Override
    public String bdcQlJsonByPage(@RequestBody BdcDjbqlQO bdcDjbqlQO) {
        return JSONObject.toJSONString(bdcQlByPageJson(bdcDjbqlQO));
    }

    /**
     * @param bdcDjbqlQO 登记簿权利查询QO
     * @return Page<BdcQl>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询登记簿权利信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询登记簿权利信息(返回BdcQl对象)")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjbqlQO", value = "登记簿权利查询QO", required = true, dataType = "BdcDjbqlQO", paramType = "body")})
    @Override
    public Page<BdcQl> bdcQlByPageJson(@RequestBody BdcDjbqlQO bdcDjbqlQO) {
        //查询参数必须包含不动产单元号或者受理编号
        if (null == bdcDjbqlQO || (StringUtils.isBlank(bdcDjbqlQO.getBdcdyh()) && StringUtils.isBlank(bdcDjbqlQO.getGzlslid())) || StringUtils.isBlank(bdcDjbqlQO.getQllx())) {
            throw new MissingArgumentException(Constants.MESSAGE_NOPARAMETER);
        }
        Pageable pageable = new PageRequest(bdcDjbqlQO.getPage(), bdcDjbqlQO.getSize(), bdcDjbqlQO.getSort());
        String json = JSONObject.toJSONString(bdcDjbqlQO);
        Map<String, Object> map = pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
        return bdcQlxxService.bdcQlxxByPageJson(bdcDjbqlQO.getQllx(), map, pageable);
    }

    /**
     * @param qlid 房地产权的权利ID
     * @return List<BdcFdcqFdcqxmDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前权利的房地产权项目信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取当前权利的房地产权项目信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "qlid", value = "产权权利ID", required = true, dataType = "String", paramType = "path")})
    @Override
    public List<BdcFdcqFdcqxmDO> listBdcFdcqxm(@PathVariable(value = "qlid") String qlid) {
        return bdcFdcqFdcqXmService.listFdcqxm(qlid);
    }

    /**
     * @param xmid 项目ID
     * @return List<BdcFdcq3DO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取业主共有部分的主信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取业主共有部分的主信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")})
    @Override
    public List<BdcFdcq3DO> listBdcFdcq3(@PathVariable(value = "xmid") String xmid) {
        return bdcFdcq3Service.queryListBdcFdcq3(xmid);
    }


    /**
     * @param bdcDjbqlQO 登记簿查询QO
     * @return Page<BdcFdcqFdcqxmDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询房地产权项目信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询房地产权项目信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjbqlQO", value = "登记簿权利查询QO", required = true, dataType = "BdcDjbqlQO", paramType = "body")})
    @Override
    public Page<BdcFdcqFdcqxmDO> bdcFdcqxmByPageJson(@RequestBody BdcDjbqlQO bdcDjbqlQO) {
        if (null == bdcDjbqlQO || StringUtils.isBlank(bdcDjbqlQO.getQlid())) {
            throw new MissingArgumentException(Constants.MESSAGE_NOPARAMETER);
        }
        Pageable pageable = new PageRequest(bdcDjbqlQO.getPage(), bdcDjbqlQO.getSize(), bdcDjbqlQO.getSort());
        String json = JSONObject.toJSONString(bdcDjbqlQO);
        Map<String, Object> map = pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
        return repo.selectPaging("listBdcFdcqFdcqxmByPage", map, pageable);
    }

    /**
     * @param bdcDjbqlQO 登记簿权利查询QO
     * @return Page<BdcFdcq3GyxxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询业主共有部分信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询业主共有部分信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjbqlQO", value = "登记簿权利查询QO", required = true, dataType = "BdcDjbqlQO", paramType = "body")})
    @Override
    public Page<BdcFdcq3GyxxDTO> bdcFdcq3GyxxDOByPageJson(@RequestBody BdcDjbqlQO bdcDjbqlQO) {
        if (null == bdcDjbqlQO || StringUtils.isBlank(bdcDjbqlQO.getBdcdyh())) {
            throw new MissingArgumentException(Constants.MESSAGE_NOPARAMETER);
        }
        Pageable pageable = new PageRequest(bdcDjbqlQO.getPage(), bdcDjbqlQO.getSize(), bdcDjbqlQO.getSort());
        String json = JSONObject.toJSONString(bdcDjbqlQO);
        Map<String, Object> map = pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
        String bdcdyh = StringUtils.deleteWhitespace(bdcDjbqlQO.getBdcdyh());
        map.put("djh", StringUtils.substring(bdcdyh, 0, 19));

        // 仅查询现势状态的信息
        List<Integer> qsztList = new ArrayList();
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        map.put("qsztList", qsztList);
        return repo.selectPaging("listFdcq3GyxxByPage", map, pageable);
    }

    /**
     * @param bdcdyh   不动产单元号
     * @param qsztList
     * @return BdcQlQtsxDTO 不动产权利及其他事项DTO对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询不动产单元权利及其他事项")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    @Override
    public BdcQlQtsxDTO queryBdcQlQtSx(@PathVariable(value = "bdcdyh") String bdcdyh, @RequestBody List<Integer> qsztList) {
        return bdcDjbxxService.queryBdcQlQtSx(bdcdyh, qsztList);
    }


    /**
     * @param bdcdyh 不动产单元号
     * @return BdcDjbQlDTO 登记簿权利信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元的登记簿权利信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询不动产单元的权利信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    @Override
    public BdcDjbQlDTO queryBdcDjbQl(@PathVariable(value = "bdcdyh") String bdcdyh, @RequestBody List<Integer> qsztList) {
        return bdcDjbxxService.queryBdcDjbQl(bdcdyh, qsztList);
    }


    /**
     * @param bdcdyh 不动产单元号
     * @return String 格式化后的单元号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 格式化单元号
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("格式化不动产单元号")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    @Override
    public String formatBdcdyh(@PathVariable(value = "bdcdyh") String bdcdyh) {
        return BdcdyhToolUtils.formatBdcdyh(bdcdyh);
    }

    /**
     * @param zdzhh    宗地宗海号
     * @param bdcdyh   不动产单元号
     * @param onlyQlfm
     * @return List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成登记簿目录树（不动产单元号不为空时，只返回当前宗地的这个单元号信息）
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("生成登记簿目录树（不动产单元号不为空时，只返回当前宗地的这个单元号信息）")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdzhh", value = "宗地宗海号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "query")})
    @Override
    public List generateDjbMls(@RequestParam(name = "zdzhh") String zdzhh, @RequestParam(name = "bdcdyh", required = false) String bdcdyh, @RequestParam(name = "onlyQlfm") Boolean onlyQlfm) {
        return bdcDjbxxService.generateDjbMls(zdzhh, bdcdyh, onlyQlfm);
    }

    /**
     * @param bdcdyh   不动产单元号
     * @param onlyQlfm 是否只查询权利封面，不查询具体权利
     * @return BdcDjbQlMlVO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 每个单元号在登记簿中的权利目录树
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("每个单元号在登记簿中的权利目录树")
    @Override
    public BdcDjbQlMlVO generateDyhQlMl(@RequestParam(name = "bdcdyh") String bdcdyh, @RequestParam(name = "onlyQlfm") Boolean onlyQlfm) {
        return bdcDjbxxService.generateDyhQlMl(bdcdyh, onlyQlfm);
    }

    /**
     * @param fwBdcXmDO 房屋不动产项目
     * @param tdqllx    需要查询的土地的qllx
     * @return BdcXmDO 查询到的土地的不动产权证
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 已知单元号的房屋产权信息，查询同单元号的土地项目信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("已知单元号的房屋产权信息，查询同单元号的土地项目信息")
    @Override
    public BdcXmDO queryFwTdXm(@RequestBody BdcXmDO fwBdcXmDO, @RequestParam(name = "tdqllx") Integer tdqllx) {
        return bdcXmxxService.queryFwTdXm(fwBdcXmDO, tdqllx);
    }

    /**
     * @return 电子证照版本
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 电子证照版本
     */
    @Override
    public String getDzzzVersion() {
        return zxdzzzFwVersion;
    }


    /**
     * @param bdcBdcdjbZhjbxxDO 不动产登记簿宗海基本信息
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 更新宗海不动产登记簿宗海基本信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新不动产登记簿宗海基本信息")
    @Override
    public int updateBdcBdcdjbZdjbxx(@RequestBody BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxxDO) {
        return bdcDjbxxService.updateBdcBdcdjbZdjbxx(bdcBdcdjbZhjbxxDO);
    }

    /**
     * @param bdcBdcdjbZhjbxxYhzkDO 宗海基本信息用海状况
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 更新宗海基本信息用海状况
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新宗海基本信息用海状况")
    @Override
    public int updateBdcBdcdjbZhjbxxYhzk(@RequestBody BdcBdcdjbZhjbxxYhzkDO bdcBdcdjbZhjbxxYhzkDO) {
        return bdcDjbxxService.updateBdcBdcdjbZhjbxxYhzk(bdcBdcdjbZhjbxxYhzkDO);
    }
}

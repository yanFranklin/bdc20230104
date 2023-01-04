package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.service.BdcJjdService;
import cn.gtmap.realestate.certificate.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcJjdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmYjdGxDO;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcJjdXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcAjxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdXmQO;
import cn.gtmap.realestate.common.core.service.rest.certificate.BdcJjdRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 交接单 rest 服务
 * 南通
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/8/27 18:16
 */
@RestController
@Api(tags = "交接单服务接口")
public class BdcJjdRestController extends BaseController implements BdcJjdRestService {
    @Autowired
    BdcJjdService bdcJjdService;

    @Autowired
    EntityMapper entityMapper;

    /**
     * 生成并保存交接单信息
     *
     * @param bdcJjdQO 交接单查询QO
     * @return {BdcJjdDO}
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "生成并保存交接单信息")
    @Override
    public BdcJjdDO generateAndSaveJjdxx(@RequestBody BdcJjdQO bdcJjdQO) {
        return bdcJjdService.generateAndSaveJjdxx(bdcJjdQO);
    }

    /**
     * 生成交接单编号
     *
     * @param bdcJjdQO 交接单查询对象
     * @return {BdcJjdDo} 交接单对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "生成交接单编号")
    @Override
    public BdcJjdDO generateJjdBh(@RequestBody BdcJjdQO bdcJjdQO) {
        return bdcJjdService.generateJjdBh(bdcJjdQO);
    }

    /**
     * 获取交接单的打印xml
     *
     * @param jjdid     交接单id
     * @param bdcUrlDTO 地址对象
     * @return 打印xml字符串
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取交接单的打印xml")
    @Override
    public String jjdPrintXml(@PathVariable(name = "jjdid") String jjdid, @RequestBody BdcUrlDTO bdcUrlDTO) {
        return bdcJjdService.jjdPrintXml(jjdid, bdcUrlDTO);
    }

    /**
     * 分页查询交接单内容
     *
     * @param pageable 分页对象
     * @param bdcJjdQO 移交单查询对象字符串
     * @return {Page} 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询移交单内容")
    @Override
    public Page<BdcJjdDO> listBdcJjdByPage(Pageable pageable, @RequestParam(name = "bdcJjdQO", required = false) String bdcJjdQO) {
        if (StringUtils.isBlank(bdcJjdQO)) {
            throw new AppException("查询参数不能为空！");
        }
        Map<String, Object> map = pageableSort(pageable, JSONObject.parseObject(bdcJjdQO, HashMap.class));
        map = getSmqsrParam(map, "jsks");
        return repo.selectPaging("listBdcJjdByPage", map, pageable);
    }

    /**
     * 分页查询全部交接单内容
     *
     * @param pageable 分页对象
     * @param bdcJjdQO 移交单查询对象字符串
     * @return {Page} 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询全部移交单内容")
    @Override
    public Page<BdcJjdDO> listAllBdcJjdByPage(Pageable pageable, @RequestParam(name = "bdcJjdQO", required = false) String bdcJjdQO) {
        if (StringUtils.isBlank(bdcJjdQO)) {
            throw new AppException("查询参数不能为空！");
        }
        Map<String, Object> map = pageableSort(pageable, JSONObject.parseObject(bdcJjdQO, HashMap.class));
        return repo.selectPaging("listAllBdcJjdByPage", map, pageable);
    }

    /**
     * 分页查询案卷内容
     *
     * @param pageable 分页对象
     * @param bdcJjdQO 移交单查询对象字符串
     * @return {Page} 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询案卷内容")
    @Override
    public Page<BdcAjxxDTO> listBdcAjxxByPage(Pageable pageable, @RequestParam(name = "bdcJjdQO", required = false) String bdcJjdQO) {
        if (StringUtils.isBlank(bdcJjdQO)) {
            throw new AppException("查询参数不能为空！");
        }
        Map<String, Object> map = pageableSort(pageable, JSONObject.parseObject(bdcJjdQO, HashMap.class));
        // 处理扫描枪参数
        map = getSmqsrParam(map,"smqsr");
        return repo.selectPaging("listBdcAjxxByPage", map, pageable);
    }

    /**
     * 分页查询案卷内容
     *
     * @param bdcJjdQO 移交单查询对象字符串
     * @return {Page} 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询案卷内容")
    @Override
    public List<BdcAjxxDTO> listBdcAjxx(@RequestParam(name = "bdcJjdQO", required = false) String bdcJjdQO) {
        if (StringUtils.isBlank(bdcJjdQO)) {
            throw new AppException("查询参数不能为空！");
        }
        Map<String, Object> map = JSONObject.parseObject(bdcJjdQO, HashMap.class);
        // 处理扫描枪参数
        map = getSmqsrParam(map,"smqsr");
        return bdcJjdService.listBdcAjxx(map);
    }

    /**
     * 分页查询交接单项目信息
     *
     * @param pageable 分页对象
     * @param jjdid    交接单id
     * @return {Page} 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询移交单项目信息")
    @Override
    public Page<BdcJjdXmDTO> listBdcJjdxmByPage(Pageable pageable, @RequestParam(name = "jjdid") String jjdid) {
        if (StringUtils.isBlank(jjdid)) {
            throw new AppException("查询 jjdid 不能为空！");
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("jjdid", jjdid);
        return repo.selectPaging("listBdcJjdxmByPage", map, pageable);
    }

    /**
     * 删除交接单项目关系
     *
     * @param gzlslid gzlslid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除交接单项目关系")
    @Override
    public void delJjdXmGx(@RequestParam(name = "gzlslid") String gzlslid) {
        bdcJjdService.delJjdXmGx(gzlslid);
    }

    /**
     * 删除交接单
     *
     * @param jjdid 交接单ID
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public void delJjd(@RequestParam(name = "jjdid") String jjdid) {
        bdcJjdService.delJjd(jjdid);
    }

    /**
     * 转发交接单
     *
     * @param bdcJjdDO 交接单对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "转发交接单")
    @Override
    public BdcJjdDO forwardJjd(@RequestBody BdcJjdDO bdcJjdDO) {
        return bdcJjdService.forwardJjd(bdcJjdDO);
    }


    /**
     * 确认接收交接单
     *
     * @param jjdid 交接单 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "确认接收交接单")
    @Override
    public Boolean acceptJjd(@RequestParam("jjdid") String jjdid) {
        return bdcJjdService.acceptJjd(jjdid);
    }


    /**
     * 退回交接单
     *
     * @param jjdid 交接单id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "退回交接单")
    @Override
    public Boolean backJjd(@RequestParam("jjdid") String jjdid) {
        return bdcJjdService.backJjd(jjdid);
    }

    /**
     * 查询在一段时间内指定受理人的流程
     *
     * @param bdcJjdXmQO 交接单项目查询对象
     * @return BdcJjdDTO 不动产交接单 DTO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public List<BdcXmDO> queryJjdXm(@RequestBody BdcJjdXmQO bdcJjdXmQO) {
        return bdcJjdService.queryJjdXm(bdcJjdXmQO);
    }

    @Override
    public List<BdcXmYjdGxDO> queryJjdGx(@PathVariable String jjdid) {
        return bdcJjdService.queryJjdGx(jjdid);
    }

    @Override
    public List<BdcJjdDO> listBdcJjd(@RequestBody BdcJjdQO bdcJjdQO) {
        return bdcJjdService.listBdcJjd(bdcJjdQO);
    }

    /**
     * 判断是否交接单可以创建
     *
     * @param gzlslid gzlslid
     * @return {Integer} 0 表示可以创建， 1 不能创建 当前用户已经用此gzlslid创建过交接单，不能再创建
     */
    @Override
    public Integer checkIsCreat(String gzlslid) {
        return bdcJjdService.checkIsCreat(gzlslid);
    }

    /**
     * 批量转发交接单
     * @param bdcJjdDOList 交接单对象集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量转发交接单")
    @ApiImplicitParam(name = "bdcJjdDOList", value = "交接单对象集合", required = true, paramType = "body")
    public void forwardJjd(@RequestBody List<BdcJjdDO> bdcJjdDOList) {
        bdcJjdService.forwardJjd(bdcJjdDOList);
    }

    /**
     * 生成南通交接单批量打印xml
     * @param key 打印参数缓存Redis Key
     * @return {String} 移交单打印xml
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("生成南通交接单批量打印xml")
    @ApiImplicitParam(name = "key", value = "打印参数缓存Redis Key", required = true, paramType = "path")
    public String jjdBatchPrintXml(@PathVariable(value = "key")String key) {
        return bdcJjdService.jjdBatchPrintXml(key);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据工作流实例ID 查询交接单关系信息")
    @ApiImplicitParam(name = "gzlslidList", value = "工作流实例ID集合", required = true, paramType = "body")
    public List<BdcXmYjdGxDO> queryJjdGxByGzlslidList(@RequestBody List<String> gzlslidList) {
        return bdcJjdService.queryJjdGxByGzlslidList(gzlslidList);
    }
}
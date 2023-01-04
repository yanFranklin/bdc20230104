package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskCustomExtendClient;
import cn.gtmap.gtc.workflow.domain.common.RequestCondition;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjdDO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZjQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcZjRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.core.mapper.BdcZjMapper;
import cn.gtmap.realestate.inquiry.service.BdcZjService;
import cn.gtmap.realestate.inquiry.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/4/10
 * @description
 */
@RestController
@Api(tags = "质检查询")
public class BdcZjRestController extends BaseController implements BdcZjRestService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcZjMapper bdcZjMapper;
    @Autowired
    private ProcessTaskCustomExtendClient processTaskCustomExtendClient;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcZjService bdcZjService;

    // 抽查频率
    @Value("${zjcx.ccpl:20}")
    private Integer ccpl;

    @Override
    public Object queryZjzt(String kssj, String jssj) {
        Map map = new HashMap();
        map.put("kssj", kssj);
        map.put("jssj", jssj);
        return bdcZjMapper.queryZjxx(map);
    }

    @Override
    public Integer cjzjxx(String kssj, String jssj) {
        if (ccpl <= 0) {
            throw new AppException("抽查频率异常！");
        }
        // 获取可以用于质检的受理编号
        Map map = new HashMap();
        map.put("kssj", kssj);
        map.put("jssj", jssj);
        List<String> gzlslidList = bdcZjMapper.listZjGzlslid(map);

        if (CollectionUtils.isEmpty(gzlslidList)) {
            return 0;
        }
        // 根据当前可抽查数据和抽查频率 确定抽查数据个数
        Integer count = (int) Math.ceil((double) gzlslidList.size() / (double) ccpl);
        // 组织抽取的工作流实例id
        Set<String> gzlslidSet = new HashSet<>();
        while (gzlslidSet.size() < count) {
            gzlslidSet.add(gzlslidList.get((int) (Math.random() * (gzlslidList.size() - 1))));
        }

        List<Map<String, Object>> processInsCustomExtendList = new ArrayList<>();
        // 根据工作流实例id获取项目信息 并存入数据库
        for (String gzlslid : gzlslidSet) {
            List<RequestCondition> requestConditionList = new ArrayList<>();
            RequestCondition requestCondition = new RequestCondition();
            requestCondition.setRequestKey("procInsId");
            requestCondition.setRequestJudge("eq");
            requestCondition.setRequestValue(gzlslid);
            requestConditionList.add(requestCondition);

            // 查询对应工作流实例id的数据
            Page<Map<String, Object>> processInsCustomExtend = processTaskCustomExtendClient.queryProcessInsWithProject(requestConditionList,0,10,0);
            // 组织查询出的数据
            if (CollectionUtils.isNotEmpty(processInsCustomExtend.getContent())) {
                processInsCustomExtendList.add(processInsCustomExtend.getContent().get(0));
            }
        }
        saveZjxx(processInsCustomExtendList);
        return processInsCustomExtendList.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void zjztgx(@RequestBody List<BdcZjDO> bdcZjDOList) {
        if (CollectionUtils.isEmpty(bdcZjDOList)) {
            throw new AppException("质检信息不能为空！");
        }
        // 更新质检状态信息
        for (BdcZjDO bdcZjDO : bdcZjDOList) {
            entityMapper.updateByPrimaryKeySelective(bdcZjDO);
        }
    }

    /**
     * 保存质检信息
     *
     * @param processInsCustomExtendList
     */
    private void saveZjxx(List<Map<String, Object>> processInsCustomExtendList) {
        if (CollectionUtils.isEmpty(processInsCustomExtendList)) {
            return;
        }
        List<BdcZjDO> bdcZjDOList = new ArrayList<>();
        for (Map<String, Object> processInsCustomExtend : processInsCustomExtendList) {
            BdcZjDO bdcZjDO = new BdcZjDO();
            bdcZjDO.setZjid(UUIDGenerator.generate16());
            bdcZjDO.setGzlslid(MapUtils.getString(processInsCustomExtend, "processInstanceId"));
            bdcZjDO.setSlbh(MapUtils.getString(processInsCustomExtend, "slbh"));
            bdcZjDO.setBdcdyh(MapUtils.getString(processInsCustomExtend, "bdcdyh"));
            bdcZjDO.setLcmc(MapUtils.getString(processInsCustomExtend, "procDefName"));
            bdcZjDO.setQlr(MapUtils.getString(processInsCustomExtend, "qlr"));
            bdcZjDO.setYwr(MapUtils.getString(processInsCustomExtend, "ywr"));
            bdcZjDO.setZl(MapUtils.getString(processInsCustomExtend, "zl"));
            bdcZjDO.setZjzt(0);

            if (StringUtils.isNotEmpty(MapUtils.getString(processInsCustomExtend, "PROC_INS_ID"))) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(MapUtils.getString(processInsCustomExtend, "PROC_INS_ID"));
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    bdcZjDO.setDjsj(bdcXmDOList.get(0).getDjsj());
                }
            }

            bdcZjDOList.add(bdcZjDO);
        }
        // 批量保存质检信息
        entityMapper.insertBatchSelective(bdcZjDOList);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("质检数据分页查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "zjParamJson", value = "质检查询分页参数JSON", required = false, paramType = "query")})
    public Page<BdcXmDO> listBdcZjxx(Pageable pageable,
                                    @RequestParam(name = "zjParamJson", required = false) String zjParamJson) {
        return this.bdcZjService.listBdcZjxx(pageable, JSON.parseObject(zjParamJson, BdcZjQO.class));
    }


    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据条件查询质检信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZjDO", value = "质检信息DO", required = true, paramType = "body")})
    public List<BdcZjDO> queryBdcZjxx(@RequestBody BdcZjDO bdcZjDO) {
        return this.bdcZjService.queryBdcZjxx(bdcZjDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("随机筛选质检信息，创建质检单生成质检单编号")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZjQO", value = "不动产质检QO", required = true, paramType = "body")})
    public String generateRandomZjd(@RequestBody BdcZjQO bdcZjQO) {
        return this.bdcZjService.generateRandomZjd(bdcZjQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("手动筛选质检信息，创建质检单生成质检单编号")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXmDOList", value = "不动产项目信息集合", required = true, paramType = "body")})
    public String generateManualZjd(@RequestBody List<BdcXmDO> bdcXmDOList) {
        return this.bdcZjService.generateManualZjd(bdcXmDOList);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("随机筛选出 100 条质检信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZjQO", value = "不动产质检QO", required = true, paramType = "body")})
    public List<BdcZjXmxxDTO> randomBdcZjXmxx(@RequestBody BdcZjQO bdcZjQO) {
        return this.bdcZjService.randomBdcZjXmxx(bdcZjQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据质检单ID获取质检信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zjdid", value = "质检信息ID", required = true, paramType = "path")})
    public List<BdcZjDO> getBdcZjxxByZjdId(@PathVariable(value = "zjdid") String zjdid) {
        return this.bdcZjService.queryBdcZjxxByZjdId(zjdid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据质检ID删除质检信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zjid", value = "质检信息ID", required = true, paramType = "path")})
    public void deleteZjxx(@PathVariable(value = "zjid") String zjid) {
        this.bdcZjService.deleteZjxx(zjid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取质检信息以及明细内容")
    @ApiImplicitParams({@ApiImplicitParam(name = "zjid", value = "质检信息ID", required = true, paramType = "path")})
    public BdcZjxxDTO getBdcZjxxAndMx(@PathVariable(value = "zjid") String zjid) {
        return this.bdcZjService.queryBdcZjxxAndMx(zjid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新质检单质检状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "zjdid", value = "质检单ID", required = true, paramType = "path")})
    public void modifyZjdzt(@PathVariable(value = "zjdid") String zjdid) {
        this.bdcZjService.modifyZjdZt(zjdid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据质检单ID获取质检单与关联的质检信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zjdid", value = "质检单ID", required = true, paramType = "path")})
    public BdcZjdDTO getBdcZjdAndZjxx(@PathVariable(value = "zjdid") String zjdid) {
        return this.bdcZjService.queryBdcZjdAndZjxx(zjdid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存质检信息和质检明细信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZjxxDTO", value = "不动产质检信息DTO", required = true, paramType = "body")})
    public void saveZjxxAndZjmx(@RequestBody BdcZjxxDTO bdcZjxxDTO) {
        this.bdcZjService.saveZjxxAndZjmx(bdcZjxxDTO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据质检单ID删除质检单信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zjdid", value = "质检单ID", required = true, paramType = "path")})
    public void deleteZjd(@PathVariable(value = "zjdid") String zjdid) {
        this.bdcZjService.deleteZjd(zjdid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存不动产质检DO信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZjDO", value = "不动产质检信息", required = true, paramType = "body")})
    public BdcZjDO saveBdcZjDO(@RequestBody BdcZjDO bdcZjDO) {
        return this.bdcZjService.saveBdcZjDO(bdcZjDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询不动产质检汇总数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZjQO", value = "不动产质检QO", required = true, paramType = "body")})
    public List<BdcZjXmxxDTO> queryBdcZjjgHzxx(@RequestBody BdcZjQO bdcZjQO) {
        return this.bdcZjService.queryBdcZjjgHzxx(bdcZjQO);
    }

    /**
     * 批量生成质检
     *
     * @param bdcPlZjDTO@author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量生成质检")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcPlZjDTO", value = "批量质检", required = true, paramType = "body")})
    public BdcZjdDO plCreateBdcZj(@RequestBody BdcPlZjDTO bdcPlZjDTO) {
        return this.bdcZjService.plCreateBdcZj(bdcPlZjDTO);
    }

    /**
     * 批量质检质检单详情
     *
     * @param zjdbh 质检单编号
     * @return 质检信息
     * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据质检单ID删除质检单信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zjdid", value = "质检单ID", required = true, paramType = "path")})
    public BdcPlZjxxDTO plZjInfo(@RequestParam(value = "zjdbh") String zjdbh) {
        return this.bdcZjService.plZjInfo(zjdbh);
    }
}

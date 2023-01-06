package cn.gtmap.realestate.register.web.rest.workflow;

import cn.gtmap.realestate.common.core.domain.register.BdcGzlJkDO;
import cn.gtmap.realestate.common.core.domain.register.BdcGzlSjDO;
import cn.gtmap.realestate.common.core.domain.register.BdcGzlsjJkGxDO;
import cn.gtmap.realestate.common.core.dto.register.BdcGzlsjFzDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcGzlsjPlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcWorkFlowDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcGzlQO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcGzlsjRestService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.register.service.workflow.BdcWorkFlowService;
import cn.gtmap.realestate.register.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 工作流事件rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-30 13:45
 **/
@RestController
@Api(tags = "审核登簿子系统工作流服务")
public class BdcGzlsjRestController extends BaseController implements BdcGzlsjRestService {

    @Autowired
    BdcWorkFlowService bdcWorkFlowService;
    @Autowired
    Repo repo;

    /**
     * @param gzlsjlx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 工作流事件统一接口
     * @date : 2022/3/23 17:21
     */
    @Override
    @ApiOperation("工作流事件统一封装配置接口，外部统一调用，内部分别调用")
    public void workFlowService(@PathVariable(value = "gzlsjlx") Integer gzlsjlx, BdcWorkFlowDTO workFlowDTO) {
        LOGGER.warn("当前流程实例id{},接口数据{}", workFlowDTO.getProcessInsId(), JSON.toJSONString(workFlowDTO, SerializerFeature.WriteNullStringAsEmpty));
        bdcWorkFlowService.executWorkFlowEvent(gzlsjlx, workFlowDTO);
    }

    /**
     * @param pageable
     * @param bdcGzlsjJson
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询工作流事件
     * @date : 2022/3/30 13:47
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询工作流事件", notes = "分页查询工作流事件")
    public Page<BdcGzlSjDO> listGzlsjByPage(Pageable pageable, @RequestParam(name = "bdcGzlsjJson", required = false) String bdcGzlsjJson) {
        BdcGzlQO bdcGzlQO = new BdcGzlQO();
        if (StringUtils.isNotBlank(bdcGzlsjJson)) {
            bdcGzlQO = JSON.parseObject(bdcGzlsjJson, BdcGzlQO.class);
        }
        return repo.selectPaging("listBdcGzlsjByPage", bdcGzlQO, pageable);
    }

    /**
     * @param bdcGzlSjDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增工作流事件
     * @date : 2022/3/30 13:48
     */
    @Override
    public BdcGzlSjDO insertGzlsj(@RequestBody BdcGzlSjDO bdcGzlSjDO) {
        return bdcWorkFlowService.insertBdcGzlSj(bdcGzlSjDO);
    }

    /**
     * @param sjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流事件
     * @date : 2022/3/30 13:49
     */
    @Override
    public void deleteGzlsj(@PathVariable(value = "sjid") String sjid) {
        bdcWorkFlowService.deleteBdcGzlsj(sjid);
    }

    /**
     * @param pageable
     * @param bdcGzljkJson
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询工作流接口
     * @date : 2022/3/30 13:48
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询工作流接口", notes = "分页查询工作流接口")
    public Page<BdcGzlJkDO> listBdcGzlJkByPage(Pageable pageable, @RequestParam(value = "bdcGzljkJson") String bdcGzljkJson) {
        BdcGzlQO bdcGzlQO = new BdcGzlQO();
        if (StringUtils.isNotBlank(bdcGzljkJson)) {
            bdcGzlQO = JSON.parseObject(bdcGzljkJson, BdcGzlQO.class);
        }
        return repo.selectPaging("listBdcGzljkByPage", bdcGzlQO, pageable);
    }

    /**
     * @param bdcGzlJkDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增工作流接口
     * @date : 2022/3/30 16:58
     */
    @Override
    public int insertBdcGzlJk(@RequestBody BdcGzlJkDO bdcGzlJkDO) {
        return bdcWorkFlowService.insertBdcGzlJk(bdcGzlJkDO);
    }

    /**
     * @param jkid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流接口
     * @date : 2022/3/30 17:00
     */
    @Override
    public void deleteBdcGzlJk(@PathVariable(value = "jkid") String jkid) {
        bdcWorkFlowService.deleteBdcGzlJk(jkid);
    }

    /**
     * @param bdcGzlsjJkGxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增工作流事件接口关系
     * @date : 2022/3/30 17:18
     */
    @Override
    public int insertBdcGzlSjjkGx(@RequestBody BdcGzlsjJkGxDO bdcGzlsjJkGxDO) {
        return bdcWorkFlowService.insertBdcGzlJkGx(bdcGzlsjJkGxDO);
    }

    /**
     * @param bdcGzlQO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接口信息
     * @date : 2022/4/7 16:36
     */
    @Override
    public List<BdcGzlJkDO> listBdcGzlJk(@RequestBody BdcGzlQO bdcGzlQO) {
        return bdcWorkFlowService.listBdcGzljk(bdcGzlQO);
    }

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询工作流事件
     * @date : 2022/4/11 14:40
     */
    @Override
    public List<BdcGzlSjDO> listBdcGzlSj(@RequestBody BdcGzlQO bdcGzlQO) {
        return bdcWorkFlowService.listBdcGzlsj(bdcGzlQO);
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询工作流事件和接口关系
     * @date : 2022/4/11 15:15
     */
    @Override
    public List<BdcGzlsjJkGxDO> listBdcGzlsjJkGx(@RequestBody BdcGzlQO bdcGzlQO) {
        return bdcWorkFlowService.listBdcGzlSjJkGx(bdcGzlQO);
    }

    /**
     * @param sjid
     * @param jkid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存工作流事件的关联关系
     * @date : 2022/4/12 17:01
     */
    @Override
    public void saveBdcgzlsjGx(@RequestParam(value = "sjid") String sjid, @RequestParam(value = "jkid") String jkid) {
        bdcWorkFlowService.saveGzlsjGx(sjid, jkid);
    }

    /**
     * @param sjid
     * @param jkid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流事件的关联关系
     * @date : 2022/4/12 17:02
     */
    @Override
    public void deleteBdcGzlSjGx(@RequestParam(value = "sjid") String sjid, @RequestParam(value = "jkid") String jkid) {
        bdcWorkFlowService.deleteGzlsjGx(sjid, jkid);
    }

    /**
     * @param sjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据事件id 删除工作流事件关系
     * @date : 2022/4/13 16:38
     */
    @Override
    public void deleteBdcGzlSjGx(@RequestParam(value = "sjid") String sjid) {
        bdcWorkFlowService.deleteGzlsjGx(sjid);
    }

    /**
     * @param bdcGzlsjJkGxDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新关联关系
     * @date : 2022/4/14 15:18
     */
    @Override
    public void batchUpdateGzlsjGx(@RequestBody List<BdcGzlsjJkGxDO> bdcGzlsjJkGxDOList) {
        bdcWorkFlowService.batchUpdateGzlsjGx(bdcGzlsjJkGxDOList);
    }

    /**
     * @param bdcGzlsjPlDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新工作流事件（勾选多个流程或者多个节点）
     * @date : 2022/4/21 15:17
     */
    @Override
    public void batchSaveGzlsj(@RequestBody BdcGzlsjPlDTO bdcGzlsjPlDTO) {
        bdcWorkFlowService.batchSaveGzlsj(bdcGzlsjPlDTO);
    }

    /**
     * @param bdcGzlsjFzDTO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 复制工作流事件
     * @date : 2023/1/4 11:11
     */
    @Override
    public void copyGzlsj(@RequestBody BdcGzlsjFzDTO bdcGzlsjFzDTO) {
        bdcWorkFlowService.copyGzlsj(bdcGzlsjFzDTO);
    }


}

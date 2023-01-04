package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.bo.ZlsxConfigBO;
import cn.gtmap.realestate.building.core.service.ZlsxService;
import cn.gtmap.realestate.building.util.ManageConfigUtil;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.building.ZlsxDTO;
import cn.gtmap.realestate.common.core.service.rest.building.ZlsxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/27
 * @description 坐落刷新相关服务
 */
@RestController
@Api(tags = "坐落刷新服务接口")
public class ZlsxRestController extends BaseController implements ZlsxRestService {

    @Autowired
    private ZlsxService zlsxService;

    /**
     * @param zlsxDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 坐落刷新
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "坐落刷新")
    public void zlsxByRule(@RequestBody ZlsxDTO zlsxDTO) {
        zlsxService.zlsxByRule(zlsxDTO);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zlsxDTO
     * @return void
     * @description 使用默认配置 做 坐落刷新
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "使用默认配置刷新坐落")
    public void zlsxByConfig(@RequestBody ZlsxDTO zlsxDTO) {
        zlsxService.zlsxByConfig(zlsxDTO);
    }

    /**
     * @return cn.gtmap.realestate.common.core.dto.building.ZlsxDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询默认配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取默认配置")
    public ZlsxDTO getConfig() {
        ZlsxDTO zlsxDTO = new ZlsxDTO();
        ZlsxConfigBO zlsxConfigBO = ManageConfigUtil.getZlsxConfig();
        if(zlsxConfigBO != null){
            zlsxDTO.setSxgz(zlsxConfigBO.getSxgz());
            zlsxDTO.setNullOnly(zlsxConfigBO.isNullOnly());
        }
        return zlsxDTO;
    }

    /**
     * @param zlsxDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据dto查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据dto查询有效的不动产单元号")
    public List<String> listValidBdcdyhByDTO(@RequestBody ZlsxDTO zlsxDTO) {
        return zlsxService.listValidBdcdyhByDTO(zlsxDTO);
    }
}
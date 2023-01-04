package cn.gtmap.realestate.natural.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtZsbhmbDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyZsDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyZsQO;
import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyZsRestService;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyYzhVO;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyZsVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsVO;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.natural.core.service.ZrzyXmService;
import cn.gtmap.realestate.natural.core.service.ZrzyXtZsbhmbService;
import cn.gtmap.realestate.natural.core.service.impl.ZrzyDjcqzhServiceImpl;
import cn.gtmap.realestate.natural.service.ZrzyZsService;
import cn.gtmap.realestate.natural.web.main.BaseController;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangyinghao@gtmap.cn">wyh</a>"
 * @version 1.0, 2021/11/5
 * @description 证书服务
 */
@RestController
@Api(tags = "自然资源证书服务接口")
public class ZrzyZsRestController extends BaseController implements ZrzyZsRestService {
    @Autowired
    ZrzyZsService zrzyZsService;

    @Autowired
    ZrzyDjcqzhServiceImpl zrzyDjcqzhService;

    @Autowired
    ZrzyXmService zrzyXmService;

    @Autowired
    ZrzyXtZsbhmbService zrzyXtZsbhmbService;


    /**
     * @param zrzyZsQO 证书查询对象
     * @return List<BdcZsDO> 不动产权证list
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书列表
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据封装的证书查询对象查询证书信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZsQO", value = "证书查询对象", dataType = "BdcZsQO", paramType = "body")})
    @Override
    public List<ZrzyZsDO> listZrzyZs(@RequestBody ZrzyZsQO zrzyZsQO) {
        return zrzyZsService.listZryzZs(zrzyZsQO);
    }

    /**
     * 初始化不动产权证
     *
     * @param processInsId
     * @throws Exception
     */
    @ApiOperation(value = "生成证书")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "生成成功"), @ApiResponse(code = 500, message = "生成失败")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "zsyl", value = "证书预览", required = false, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ZrzyZsDO initZrzyqzs(@PathVariable("processInsId") String processInsId, @RequestParam(value = "zsyl", required = false) boolean zsyl) throws Exception {
        return zrzyZsService.initZrzyqzs(processInsId, zsyl, false, null, false);
    }

    /**
     * 生成产权证号
     *
     * @param processInsId
     * @return 集合
     * @throws Exception
     */
    @ApiOperation(value = "生成产权证号")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "生成成功"), @ApiResponse(code = 500, message = "生成失败")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public void generateCqzh(@PathVariable("processInsId") String processInsId) throws Exception {
        zrzyDjcqzhService.generateCqzhOfPro(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("生成证书印制号")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhVO", value = "证书印制号配置模板", required = true, paramType = "body")})
    public int generateBdcZsyzh(ZrzyYzhVO zrzyYzhVO) {
        return zrzyZsService.generateBdcZsyzh(zrzyYzhVO);
    }

    /**
     * @param zsid 证书ID
     * @return {BdcZsDo} 不动产证书
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取不动产权证书
     */
    @Override
    public ZrzyZsVO queryZrzyZs(@PathVariable(value = "zsid") String zsid) {
        ZrzyZsVO zrzyZsVO = new ZrzyZsVO();
        ZrzyZsQO zrzyZsQO = new ZrzyZsQO();
        zrzyZsQO.setZsid(zsid);
        List<ZrzyZsDO> zrzyZsDOS = zrzyZsService.listZryzZsByZsid(zrzyZsQO);
        if(CollectionUtils.isNotEmpty(zrzyZsDOS)){
            ZrzyZsDO zrzyZsDO = zrzyZsDOS.get(0);
            ZrzyXmDO zrzyXmDO = zrzyXmService.queryZrzyXmByXmid(zrzyZsDO.getXmid());
            BeanUtils.copyProperties(zrzyZsDO,zrzyZsVO);
            ZrzyXtZsbhmbDO zrzyXtZsbhmbDO = zrzyXtZsbhmbService.queryZrzyXtZsbhmb(zrzyXmDO);
            BeanUtils.copyProperties(zrzyXtZsbhmbDO,zrzyZsVO);
            Date bfrq = zrzyXmDO.getSlsj();
            zrzyZsVO.setSzrq(bfrq);
            zrzyZsVO.setSzrqDay(bfrq);
            zrzyZsVO.setSzrqMonth(bfrq);
            zrzyZsVO.setSzrqYear(bfrq);
        }
        return zrzyZsVO;
    }

    /**
     * @param zsid 证书ID
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前证书相关的项目信息
     */
    @Override
    public List<ZrzyXmDO> queryZsXmByZsid(String zsid) {
        return null;
    }

    /**
     * 更新证书
     *
     * @param zrzyZsDO
     * @return
     */
    @Override
    public int updateZrzyZs(@RequestBody ZrzyZsDO zrzyZsDO) {
        return zrzyZsService.updateZrzyZs(zrzyZsDO);
    }

    /**
     * @param page     分页查询页数
     * @param size     分页查询行数
     * @param sort     分页查询排序
     * @param zrzyZsQO 证书查询对象
     * @return Page<BdcZsDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书分页
     */
    @Override
    public Page<ZrzyZsVO> zrzyZsByPageJson(int page, int size, Sort sort, ZrzyZsQO zrzyZsQO) {
        return null;
    }

    /**
     * @param gzlslid 工作流实例
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @return　 List<String>　zsid列表
     * @description 查询当前流程所有的zsid
     */
    @Override
    public List<String> queryGzlZsid(String gzlslid) {
        return null;
    }
}

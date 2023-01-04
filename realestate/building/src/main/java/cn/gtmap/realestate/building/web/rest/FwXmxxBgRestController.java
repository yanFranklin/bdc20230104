package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.FwXmxxService;
import cn.gtmap.realestate.building.service.bg.FwBgService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.dto.building.XmxxBgRequestDTO;
import cn.gtmap.realestate.common.core.ex.EntityException;
import cn.gtmap.realestate.common.core.ex.IllegalArgumentException;
import cn.gtmap.realestate.common.core.service.rest.building.FwXmxxBgRestService;
import cn.gtmap.realestate.common.core.vo.building.FwXmxxBgVO;
import cn.gtmap.realestate.common.core.vo.building.FwXmxxHbVO;
import cn.gtmap.realestate.common.util.InterfaceCodeBeanFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/26
 * @description
 */
@RestController
@Api(tags = "项目信息变更服务")
public class FwXmxxBgRestController extends InterfaceCodeBeanFactory implements FwXmxxBgRestService {

    @Autowired
    private FwXmxxService fwXmxxService;

    @Resource(name = "fwXmxxJbxxBgServiceImpl")
    private FwBgService fwXmxxJbxxBgService;

    @Resource(name = "fwXmxxHbServiceImpl")
    private FwBgService fwXmxxHbService;

    @Resource(name = "fwXmxxMsServiceImpl")
    private FwBgService fwXmxxMsService;


    /**
     * @param xmxxBgRequestDTO
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息变更
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "项目信息变更")
    public FwXmxxDO xmxxBg(@RequestBody XmxxBgRequestDTO xmxxBgRequestDTO) {
        if(CollectionUtils.isEmpty(xmxxBgRequestDTO.getyFwXmxxIndexList())){
            throw new IllegalArgumentException("xmxxBgRequestDTO");
        }
        String yIndex = xmxxBgRequestDTO.getyFwXmxxIndexList().get(0);
        FwXmxxDO yFwXmxxDO = fwXmxxService.queryXmxxByPk(yIndex);
        if(yFwXmxxDO == null){
            throw new EntityException("主键为"+yIndex+"的项目不存在");
        }
        Object result = fwXmxxJbxxBgService.jbxxBg(xmxxBgRequestDTO.getBgbh(),Constants.BGLX_BG,yFwXmxxDO,xmxxBgRequestDTO.getFwXmxxDO());
        if(result != null){
            return (FwXmxxDO)result;
        }
        return null;
    }

    /**
     * @param xmxxBgRequestDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息灭失
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "项目信息灭失")
    public void xmxxMs(@RequestBody XmxxBgRequestDTO xmxxBgRequestDTO) {
        if(CollectionUtils.isEmpty(xmxxBgRequestDTO.getyFwXmxxIndexList())){
            throw new IllegalArgumentException("xmxxBgRequestDTO");
        }
        String yIndex = xmxxBgRequestDTO.getyFwXmxxIndexList().get(0);
        FwXmxxDO yFwXmxxDO = fwXmxxService.queryXmxxByPk(yIndex);
        if(yFwXmxxDO == null){
            throw new EntityException("主键为"+yIndex+"的项目不存在");
        }
        fwXmxxMsService.msBg(xmxxBgRequestDTO.getBgbh(),Constants.BGLX_MS,yFwXmxxDO);
    }

    /**
     * @param xmxxBgRequestDTO
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息合并
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "项目信息合并")
    public FwXmxxDO xmxxHb(@RequestBody XmxxBgRequestDTO xmxxBgRequestDTO) {
        if(CollectionUtils.isEmpty(xmxxBgRequestDTO.getyFwXmxxIndexList())){
            throw new IllegalArgumentException("xmxxBgRequestDTO");
        }
        List<FwXmxxDO> yList = new ArrayList<>();
        double dymj=0;
        for(String yIndex : xmxxBgRequestDTO.getyFwXmxxIndexList()){
            FwXmxxDO xmxxDO = fwXmxxService.queryXmxxByPk(yIndex);
            if(xmxxDO != null){
                yList.add(xmxxDO);
                if(xmxxDO.getDytdmj()!=null&&xmxxDO.getDytdmj()!=0){
                    dymj=dymj+xmxxDO.getDytdmj();
                }
            }
        }
        Object result = fwXmxxHbService.hbBg(xmxxBgRequestDTO.getBgbh(),yList,xmxxBgRequestDTO.getFwXmxxDO());
        if(result != null){
            FwXmxxDO fwXmxxDO=(FwXmxxDO)result;
            if(dymj!=0){
                fwXmxxDO.setDytdmj(dymj);
                fwXmxxService.updateFwXmxx(fwXmxxDO,false);
            }
            return fwXmxxDO;
        }
        return null;
    }
    /**
     * @param fwXmxxBgVO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据FwXmxxBgVO查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据FwXmxxBgVO查询有效的不动产单元号")
    public List<String> listValidBdcdyhByFwXmxxBgVO(@RequestBody FwXmxxBgVO fwXmxxBgVO) {
        return fwXmxxService.listValidBdcdyhByFwXmxxBgVO(fwXmxxBgVO);
    }

    /**
     * @param fwXmxxHbVO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据FwXmxxHbVO查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据FwXmxxHbVO查询有效的不动产单元号")
    public List<String> listValidBdcdyhByFwXmxxHbVO(@RequestBody FwXmxxHbVO fwXmxxHbVO) {
        return fwXmxxService.listValidBdcdyhByFwXmxxHbVO(fwXmxxHbVO);
    }


}
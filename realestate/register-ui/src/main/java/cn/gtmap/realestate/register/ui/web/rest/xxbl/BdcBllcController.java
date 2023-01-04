package cn.gtmap.realestate.register.ui.web.rest.xxbl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.register.BdcBlxxDTO;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXxblFeignService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 不动产信息补录流程服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/12/17 11:00
 */
@RestController
@RequestMapping("/rest/v1.0/blxx/bllc")
public class BdcBllcController {

    @Autowired
    private BdcXxblFeignService bdcXxblFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcZsFeignService bdcZsFeignService;
    /*
     * 信息补录修改时是否需要证书锁定
     * */
    @Value("${xxbl.modify.zssd:true}")
    private boolean xxblZssd;


    @Value("#{'${xxbl.modify.gzldyid:}'.split(',')}")
    private List<String> xxblXgGzldyidList;


    /*
     * 删除
     * */
    @Value("#{'${xxbl.delete.gzldyid:}'.split(',')}")
    private List<String> xxblScGzldyid;

    /*
     * 恢复
     * */
    @Value("#{'${xxbl.rollback.gzldyid:}'.split(',')}")
    private List<String> xxblHfGzldyid;

    @Value("${xxbl.modifyKey: }")
    private String modifyKey;

    /**
     * 初始化流程
     *
     * @param bdcBlxxDTO bdcBlxxDTO
     * @return TaskData
     * @throws Exception
     */
    @PostMapping("/csh")
    public Object cshLcxx(@RequestBody BdcBlxxDTO bdcBlxxDTO) throws Exception {
        if (StringUtils.isAnyBlank(bdcBlxxDTO.getGzldyid(), bdcBlxxDTO.getLcmc(), bdcBlxxDTO.getPtgzldyid())) {
            throw new MissingArgumentException("补录数据必须传入参数缺失！");
        }
        return bdcXxblFeignService.cshLcxx(bdcBlxxDTO);
    }

    /**
     * 初始化修改流程
     * @param bdcBlxxDTO bdcBlxxDTO
     * @return TaskData
     * @throws Exception
     */
    @PostMapping("/modify")
    public Object cshModify(@RequestBody BdcBlxxDTO bdcBlxxDTO) {
        if (StringUtils.isAnyBlank(bdcBlxxDTO.getPtgzldyid())) {
            throw new MissingArgumentException("数据维护初始化必须传入平台定义 ID！");
        }
        //根据不通的流程判断走哪个接口创建信息补录数据
        if (CollectionUtils.isNotEmpty(xxblXgGzldyidList) && xxblXgGzldyidList.contains(bdcBlxxDTO.getPtgzldyid()) || StringUtils.equals(modifyKey, bdcBlxxDTO.getPtgzldyid())) {
            return bdcXxblFeignService.cshModify(bdcBlxxDTO);
        } else {
            return bdcXxblFeignService.cshDeletelc(bdcBlxxDTO);
        }
    }

    /**
     * 判断是否需要填写证书锁定原因
     *
     * @return {String}
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 判断当前项目是否生成证书
     */
    @GetMapping("/sdyy")
    @ApiOperation(value = "判断是否需要填写证书锁定原因")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public boolean querySdyy(@RequestParam String gzlslid) {
        if (!xxblZssd) {
            return false;
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            throw new EntityNotFoundException("判断是否需要填写证书锁定原因没有获取到对应项目信息！");
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        String yxmid = bdcXmDO.getXmid();
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.queryBdcZsByXmid(yxmid);
        return CollectionUtils.isNotEmpty(bdcZsDOS);
    }


    /**
     *
     *
     * @return {String}
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 判断当前项目是否生成证书
     */
    @GetMapping("/zs")
    @ApiOperation(value = "判断是否需要填写证书锁定原因")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public void endModify(@RequestParam String gzlslid, @RequestParam String jsyy) {
        bdcXxblFeignService.endModify(gzlslid, jsyy);
    }

    /**
     * 初始化删除信息补录流程
     * @param bdcBlxxDTO bdcBlxxDTO
     * @return TaskData
     * @throws Exception
     */
    @PostMapping("/cshDeleteXxblLc")
    public Object cshDeleteXxblLc(@RequestBody BdcBlxxDTO bdcBlxxDTO) throws Exception {
        if (StringUtils.isAnyBlank(bdcBlxxDTO.getPtgzldyid())) {
            throw new MissingArgumentException("数据维护初始化必须传入平台定义 ID！");
        }
        return bdcXxblFeignService.cshDeleteXxblLc(bdcBlxxDTO);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断是否信息补录修改流程
     * @date : 2022/8/30 12:57
     */
    @GetMapping("/blxglc")
    public boolean blxglc(String gzldyid) {
        if ((CollectionUtils.isNotEmpty(xxblXgGzldyidList) && xxblXgGzldyidList.contains(gzldyid)) || StringUtils.equals(modifyKey, gzldyid)) {
            return true;
        }
        return false;
    }

    /**
     * 初始化信息补录流程支持批量
     *
     * @param bdcBlxxDTOS bdcBlxxDTOS
     * @return TaskData
     * @throws Exception
     */
    @PostMapping("/plXxblcsh")
    public Object cshPlxxblLcxx(@RequestBody List<BdcBlxxDTO>  bdcBlxxDTOS) throws Exception {
        if(CollectionUtils.isEmpty(bdcBlxxDTOS)){
            throw new MissingArgumentException("补录数据必须传入参数缺失！");
        }
        return bdcXxblFeignService.cshPlxxblLcxx(bdcBlxxDTOS);
    }
}

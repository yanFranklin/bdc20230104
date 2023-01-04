package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcZdSsjGxDO;
import cn.gtmap.realestate.common.core.qo.config.BdcZdSsjGxQO;
import cn.gtmap.realestate.common.core.service.rest.config.BdcZdSsjgxRestService;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcZdSsjGxVO;
import cn.gtmap.realestate.common.util.StringUtil;
import cn.gtmap.realestate.config.service.BdcZdSsjgxService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0, 2021/08/25
 * @description 业务配置系统：省市级共享配置接口信息获取
 */
@RestController
@Api(tags = "省市级共享配置接口信息获取")
public class BdcZdSsjgxRestController implements BdcZdSsjgxRestService {

    @Value("#{'${sjgx.jkml:}'.split(',')}")
    private List<String> sortFmldmList;

    @Autowired
    private BdcZdSsjgxService bdcZdSsjgxService;

    /**
     * 查询BdcZdSsjGxDO，bdcZdSsjGxQO为空默认查全部激活状态的数据
     *
     * @param bdcZdSsjGxQO
     * @return
     */
    @Override
    @PostMapping("/realestate-config/rest/v1.0/zd/ssjgx/list")
    public List<BdcZdSsjGxDO> listSsjgx(@RequestBody BdcZdSsjGxQO bdcZdSsjGxQO) {
        return bdcZdSsjgxService.listSsjgx(bdcZdSsjGxQO);
    }

    /**
     * 通过子目录代码获取BdcZdSsjGxDO
     *
     * @param zmldm
     * @return
     */
    @Override
    @GetMapping("/realestate-config/rest/v1.0/zd/ssjgx/get/by/zmldm")
    public BdcZdSsjGxDO getSsjgxByZmldm(@RequestParam(value = "zmldm") String zmldm) {
        if (StringUtil.isNotBlank(zmldm)){
            return bdcZdSsjgxService.getSsjgxByZmldm(zmldm);
        }
        return null;
    }

    /**
     * 通过父目录代码获取BdcZdSsjGxDO
     *
     * @param fmldms @return
     */
    @Override
    public List<BdcZdSsjGxVO> getSsjgxByFmldms(String fmldms) {
        if (StringUtils.isNotBlank(fmldms)){
            List<String> fmldmList =new ArrayList<>();
            List<BdcZdSsjGxDO> ssjGxDOS = bdcZdSsjgxService.getSsjgxByFmldms(fmldms);
            if (CollectionUtils.isNotEmpty(ssjGxDOS)){
                Map<String,List<BdcZdSsjGxDO>> resultMap = new HashMap<>();
                for (BdcZdSsjGxDO ssjGxDO : ssjGxDOS) {
                    if (resultMap.containsKey(ssjGxDO.getFmldm())){
                        resultMap.get(ssjGxDO.getFmldm()).add(ssjGxDO);
                    }else {
                        List<BdcZdSsjGxDO> childList = new ArrayList<>(2);
                        childList.add(ssjGxDO);
                        resultMap.put(ssjGxDO.getFmldm(),childList);
                        fmldmList.add(ssjGxDO.getFmldm());
                    }
                }
                List<BdcZdSsjGxVO> result = new ArrayList<>(resultMap.size());
                if (CollectionUtils.isNotEmpty(sortFmldmList)){
                    for (String sortFmldm : sortFmldmList) {
                        if (resultMap.containsKey(sortFmldm)){
                            List<BdcZdSsjGxDO> zdSsjGxDOS = resultMap.remove(sortFmldm);
                            initLcmlList(result, zdSsjGxDOS);
                        }
                    }
                }
                if (MapUtils.isNotEmpty(resultMap)){
                    for (String fmldm : fmldmList) {
                        if (resultMap.containsKey(fmldm)){
                            List<BdcZdSsjGxDO> zdSsjGxDOS = resultMap.remove(fmldm);
                            initLcmlList(result, zdSsjGxDOS);
                        }
                    }
                }
                return result;
            }
//            return bdcZdSsjgxService.getSsjgxByFmldms(fmldms);
        }
        return null;
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询共享接口字典")
    @ApiImplicitParam(name = "json", value = "不动产角色区县代码查询对象", required = false, paramType = "String")
    public Page<BdcZdSsjGxDO> listBdcZdSsjGxByPage(Pageable pageable, @RequestParam(name = "json", required = false) String json){
        BdcZdSsjGxDO bdcZdSsjGxDO = new BdcZdSsjGxDO();
        if(StringUtils.isNotBlank(json)){
            bdcZdSsjGxDO = JSON.parseObject(json, BdcZdSsjGxDO.class);
        }
        return bdcZdSsjgxService.listBdcZdSsjGxByPage(pageable, bdcZdSsjGxDO);
    }

    @Override
    public void deleteBdcZdSsjGxByZmldm(@PathVariable("zmldm") String zmldm){
        bdcZdSsjgxService.deleteBdcZdSsjGxByZmldm(zmldm);

    }

    @Override
    public void saveBdcZdSsjgx(@RequestBody BdcZdSsjGxDO bdcZdSsjGxDO){
        bdcZdSsjgxService.saveBdcZdSsjgx(bdcZdSsjGxDO);

    }

    @Override
    public BdcZdSsjGxDO queryBdcZdSsjGx(@RequestParam(value = "id") String id){
        return bdcZdSsjgxService.queryBdcZdSsjGx(id);

    }

    private void initLcmlList(List<BdcZdSsjGxVO> result, List<BdcZdSsjGxDO> zdSsjGxDOS) {
        BdcZdSsjGxVO build = BdcZdSsjGxVO.BdcZdSsjGxVOBuilder.aBdcZdSsjGxVO().withName(zdSsjGxDOS.get(0).getFmldm()).withMc(zdSsjGxDOS.get(0).getFmlmc()).build();
        build.setChildren(new ArrayList<>(zdSsjGxDOS.size()));
        for (BdcZdSsjGxDO child : zdSsjGxDOS) {
            build.getChildren().add(BdcZdSsjGxVO.BdcZdSsjGxVOBuilder.aBdcZdSsjGxVO().withName(child.getZmldm()).withMc(child.getZmlmc()).withUrl(child.getYmdz()).build());
        }
        result.add(build);
    }
}

package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcTddysfDyhDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.BdcZdQjgldmDO;
import cn.gtmap.realestate.common.core.dto.register.BdcTddysfDyhDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcTddysfDyhShDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.register.BdcTddysfFwQO;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcTddysfDyhFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/10/12
 * @description 土地抵押释放
 */
@RestController
@RequestMapping("/rest/v1.0/tddysf")
public class BdcTddysfController extends BaseController {

    @Autowired
    BdcTddysfDyhFeignService bdcTddysfDyhFeignService;

    @Autowired
    FwHsFeignService fwHsFeignService;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;

    @Autowired
    BdcZdFeignService bdcZdFeignService;


    /**
     * @param bdcTddysfFwQO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询房屋户室
     */
    @GetMapping(value = "/fwdyh/page")
    @ResponseStatus(HttpStatus.OK)
    public Object listTddysfFwxx(@LayuiPageable Pageable pageable, BdcTddysfFwQO bdcTddysfFwQO){
        if(StringUtils.isNotBlank(bdcTddysfFwQO.getGzlslid())){
            List<BdcTddysfDyhDO> bdcTddysfDyhDOList =bdcTddysfDyhFeignService.listTddysfBdcdyhByGzlslid(bdcTddysfFwQO.getGzlslid());
            if(CollectionUtils.isNotEmpty(bdcTddysfDyhDOList)){
                bdcTddysfFwQO.setBdcdyhList(bdcTddysfDyhDOList.stream().map(BdcTddysfDyhDO::getBdcdyh).collect(Collectors.toList()));
            }else{
                return addLayUiCode(new PageImpl(new ArrayList(), pageable, 0));
            }
        }
        return addLayUiCode(fwHsFeignService.listFwHsByPageJson(pageable, JSONObject.toJSONString(bdcTddysfFwQO)));
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 关联房屋单元号
     */
    @PostMapping("/glfwdyh")
    public void glFwdyh(@RequestBody BdcTddysfDyhDTO bdcTddysfDyhDTO){
        bdcTddysfDyhFeignService.initBdcTddysfDyh(bdcTddysfDyhDTO);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 取消关联房屋单元号
     */
    @DeleteMapping("/qxglfwdyh")
    public void qxglFwdyh(@RequestBody BdcTddysfDyhDTO bdcTddysfDyhDTO){
        bdcTddysfDyhFeignService.deleteBdcTddysfDyh(bdcTddysfDyhDTO);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 例外审核
     */
    @PostMapping("/lwsh")
    public void lwsh(@RequestBody BdcTddysfDyhShDTO bdcTddysfDyhShDTO){
        bdcTddysfDyhFeignService.updateBdcTddysfDyhShxx(bdcTddysfDyhShDTO);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取地区下拉
     */
    @GetMapping("/qjgldm/list")
    public List<BdcZdQjgldmDO> listQjgldm(){
        List<BdcZdQjgldmDO> zdqjgldmList =new ArrayList<>();
        List<String> qjgldmList = Container.getBean(BdcConfigUtils.class).qjgldmFilter("selectbdcdyh");
        List<Map> qjgldmZdMap = bdcZdFeignService.queryBdcZd("qjgldm");
        for(Map qjgldmZd:qjgldmZdMap){
            String dm =qjgldmZd.get("DM").toString();
            if(CollectionUtils.isEmpty(qjgldmList) ||qjgldmList.contains(dm)){
                BdcZdQjgldmDO bdcZdQjgldmDO =new BdcZdQjgldmDO();
                bdcZdQjgldmDO.setDm(dm);
                bdcZdQjgldmDO.setMc(qjgldmZd.get("MC").toString());
                zdqjgldmList.add(bdcZdQjgldmDO);
            }
        }
        if (CollectionUtils.isNotEmpty(qjgldmList) && CollectionUtils.isEmpty(zdqjgldmList)){
            // 配置权籍管理代码与字典不匹配
            throw new AppException("权籍管理代码过滤与字典表不对应，请检查配置，"+ JSONObject.toJSONString(qjgldmList));
        }
        return zdqjgldmList;
    }


}

package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlShxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXqxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXqxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlShxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXqxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 2021/2/25
 * @description 需求流转信息
 */
@Controller
@RequestMapping("/xqxx")
public class SlymXqxxController extends BaseController {

    @Autowired
    BdcSlXqxxFeignService bdcSlXqxxFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    @Autowired
    BdcSlShxxFeignService bdcSlShxxFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcSlxxHxFeignService bdcSlxxHxFeignService;

    /**
     * 签名图片地址
     */
    @Value("${url.sign.image:}")
    protected String signImageUrl;


    /**
     * 根据gzlslid查询需求流转信息
     * @param gzlslid
     * @return 需求流转信息
     */
    @ResponseBody
    @GetMapping("/getXqxx/{gzlslid}")
    public Object getXqxx(@PathVariable(value="gzlslid")String gzlslid){
        if(StringUtils.isNotBlank(gzlslid)){
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if(CollectionUtils.isEmpty(bdcSlXmDOList)){
                throw new AppException("未查询到受理项目数据,实例id：" + gzlslid);
            }

            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
            if(null == bdcSlJbxxDO){
                throw new AppException("未查询到受理基本信息数据,实例id：" + gzlslid);
            }

            BdcSlXqxxDTO bdcSlXqxxDTO = new BdcSlXqxxDTO();

            bdcSlXqxxDTO.setBdcSlXmDO(bdcSlXmDOList.get(0));
            bdcSlXqxxDTO.setBdcSlJbxxDO(bdcSlJbxxDO);

            BdcSlXqxxQO bdcSlXqxxQO = new BdcSlXqxxQO();
            bdcSlXqxxQO.setXmid(bdcSlXmDOList.get(0).getXmid());
            BdcSlXqxxDO bdcSlXqxxDO = bdcSlXqxxFeignService.queryBdcSlXqxx(bdcSlXqxxQO);
            bdcSlXqxxDTO.setBdcSlXqxxDO(bdcSlXqxxDO);

            BdcSlShxxQO bdcSlShxxQO = new BdcSlShxxQO();
            bdcSlShxxQO.setGzlslid(gzlslid);
            List<BdcSlShxxDO> listSlShxx = bdcSlShxxFeignService.queryBdcShxx(bdcSlShxxQO);
            bdcSlXqxxDTO.setBdcSlShxxDOList(listSlShxx);

            bdcSlXqxxDTO.setSignImageUrl(signImageUrl);

            bdcSlXqxxDTO.setUserDto(userManagerUtils.getCurrentUser());

            return bdcSlXqxxDTO;

        }else{
            throw new MissingArgumentException("查询需求流转信息缺失gzlslid");
        }
    }

    /**
     * @param json
     * @return 新的实体bdcSlXqxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新(插入)需求流转信息
     */
    @ResponseBody
    @PatchMapping("/saveBdcSlxqxx")
    public Object updateBdcSlxqxx(@RequestBody String json, String gzlslid) throws Exception {
        BdcSlXqxxDO bdcSlXqxxDO = JSONObject.parseObject(json, BdcSlXqxxDO.class);

        // 同步需求名称至 BDC_SL_XM 表中的坐落,并回写需求名称至大云。用户可以通过坐落搜索对应的需求名称
        if(!StringUtils.isAnyBlank(gzlslid, bdcSlXqxxDO.getXqmc())){
            BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
            if(Objects.nonNull(bdcSlJbxxDO)){
                bdcSlJbxxDO.setZl(bdcSlXqxxDO.getXqmc());
                this.bdcSlJbxxFeignService.updateBdcSlJbxx(bdcSlJbxxDO);
                this.bdcSlxxHxFeignService.hxBdcSlxx(gzlslid);
            }
        }
        return bdcSlXqxxFeignService.saveBdcSlXqxx(bdcSlXqxxDO);
    }

}

package cn.gtmap.realestate.accept.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcJtcyDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcJtcySaveDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcJtcyQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcJtcyFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/8/3
 * @description 不动产家庭成员(BDC_JTCY)
 */
@RestController
@RequestMapping("/rest/v1.0/jtcy")
@Api(tags = "不动产家庭成员")
public class BdcJtcyController {

    @Autowired
    BdcJtcyFeignService bdcJtcyFeignService;

    @Autowired
    BdcSlQlrFeignService bdcSlQlrFeignService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcJtcyController.class);


    /**
     * @param qlrid 权利人ID
     * @return 家庭成员信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据权利人ID获取家庭成员信息
     */
    @GetMapping(value = "qlrid/{qlrid}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "qlrid", value = "权利人ID", dataType = "string", paramType = "path", required = true)})
    public List<BdcJtcyDO> listJtcyxx(@PathVariable(name = "qlrid") String qlrid) {
        if(StringUtils.isNotBlank(qlrid)) {
            BdcQlrQO bdcQlrQO =new BdcQlrQO();
            bdcQlrQO.setQlrid(qlrid);
            return bdcJtcyFeignService.listBdcJtcyByQlr(bdcQlrQO);
        }
        return Collections.emptyList();
    }

    /**
     * 根据户主的证件号信息查询家庭成员信息
     * @param zjh  户主证件号信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 家庭成员集合
     */
    @GetMapping(value = "/queryJtcyxx/{zjh}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcJtcyDO", value = "家庭成员DO", dataType = "BdcJtcyDO", paramType = "body", required = true)})
    public List<BdcJtcyDO> queryJtcyxxList(@PathVariable(value = "zjh") String zjh) {
        return this.bdcJtcyFeignService.queryJtcyxxByHzzjh(zjh);
    }

    /**
     * @param jtcyid 家庭成员ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据家庭成员ID获取家庭成员
     */
    @GetMapping(value = "jtcyid/{jtcyid}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "jtcyid", value = "家庭成员ID", dataType = "string", paramType = "path", required = true)})
    public BdcJtcyDO queryJtcyxx(@PathVariable(name = "jtcyid") String jtcyid) {
        BdcJtcyQO bdcJtcyQO =new BdcJtcyQO();
        bdcJtcyQO.setJtcyid(jtcyid);
        List<BdcJtcyDO> bdcJtcyDOList =bdcJtcyFeignService.listBdcJtcy(bdcJtcyQO);
        if(CollectionUtils.isNotEmpty(bdcJtcyDOList)){
            return bdcJtcyDOList.get(0);
        }
        return new BdcJtcyDO();
    }

    /**
     * @param json 数组结构
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新权利人与家庭成员
     */
    @PatchMapping(value = "qlrAndjtcy/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBdcQlrAndJtcyxx(@RequestBody String json,@PathVariable("gzlslid") String gzlslid) throws Exception{
        if(StringUtils.isBlank(json) ||StringUtils.isBlank(gzlslid)){
            throw new AppException("更新权利人家庭成员信息工作流实例ID或者更新内容为空");
        }
        LOGGER.info("保存权利和家庭成员参数：{}",json);
        JSONArray jsonArray =JSONObject.parseArray(json);
        if(CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                if(obj.get("qlr") != null){
                    BdcQlrDO bdcQlrDO =JSONObject.parseObject(JSON.toJSONString(obj.get("qlr")),BdcQlrDO.class);
                    if(bdcQlrDO != null) {
                        String djxl ="";
                        if(StringUtils.isNotBlank(bdcQlrDO.getXmid())){
                            BdcXmQO bdcXmQO =new BdcXmQO();
                            bdcXmQO.setXmid(bdcQlrDO.getXmid());
                            List<BdcXmDO> bdcXmDOList=bdcXmFeignService.listBdcXm(bdcXmQO);
                            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                                djxl =bdcXmDOList.get(0).getDjxl();
                            }

                        }
                        String qlrid ="";
                        //权利人存在进行更新,不存在进行新增
                        if(StringUtils.isNotBlank(bdcQlrDO.getQlrid())){
                            qlrid =bdcQlrDO.getQlrid();
                            bdcSlQlrFeignService.updatePlzhBdcQlr(JSON.toJSONString(obj.get("qlr")), gzlslid, bdcQlrDO.getXmid(), djxl);
                        }else{
                            BdcQlrDO qlrDO =bdcSlQlrFeignService.insertPlzhBdcQlr(JSON.toJSONString(obj.get("qlr")), gzlslid, djxl,false);
                            if(qlrDO != null) {
                                qlrid = qlrDO.getQlrid();
                            }
                        }
                        //保存家庭成员
                        if (obj.get("jtcyList") != null) {
                            List<BdcJtcyDO> bdcJtcyDOList = JSONArray.parseArray(JSON.toJSONString(obj.get("jtcyList")), BdcJtcyDO.class);
                            if (CollectionUtils.isNotEmpty(bdcJtcyDOList)) {
                                BdcJtcySaveDTO bdcJtcySaveDTO =new BdcJtcySaveDTO();
                                bdcJtcySaveDTO.setBdcJtcyDOList(bdcJtcyDOList);
                                bdcJtcySaveDTO.setGzlslid(gzlslid);
                                bdcJtcySaveDTO.setQlrid(qlrid);
                                bdcJtcyFeignService.saveBatchJtcyxx(bdcJtcySaveDTO);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param json
     * @param gzlslid
     * @description  更新家庭成员信息
     */
    @PostMapping(value = "/updateJtcyxx/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public void updateJtcyxx(@RequestBody String json,@PathVariable("gzlslid") String gzlslid){
        if(StringUtils.isBlank(json)){
            throw new AppException("更新家庭成员信息更新内容为空");
        }
        LOGGER.info("保存权利和家庭成员参数：{}",json);
        JSONArray jsonArray =JSONObject.parseArray(json);
        if(CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                List<BdcJtcyDO> bdcJtcyDOList = JSONArray.parseArray(JSON.toJSONString(obj.get("jtcyList")), BdcJtcyDO.class);
                List<String> qlridList = JSONArray.parseArray(JSON.toJSONString(obj.get("qlrid")), String.class);
                if(CollectionUtils.isNotEmpty(bdcJtcyDOList) && CollectionUtils.isNotEmpty(qlridList)){
                    BdcJtcySaveDTO bdcJtcySaveDTO =new BdcJtcySaveDTO();
                    bdcJtcySaveDTO.setBdcJtcyDOList(bdcJtcyDOList);
                    bdcJtcySaveDTO.setGzlslid(gzlslid);
                    bdcJtcySaveDTO.setQlrid(qlridList.get(0));
                    bdcJtcyFeignService.saveBatchJtcyxx(bdcJtcySaveDTO);
                }
            }
        }
    }
    /**
     * @param jtcyid 家庭成员ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  删除家庭成员
     */
    @DeleteMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBdcJtcy(@RequestParam(name = "jtcyid" , required = false) String jtcyid,@RequestParam(name = "gzlslid", required = false) String gzlslid){
        if(StringUtils.isBlank(jtcyid) || StringUtils.isBlank(gzlslid) ){
            throw new AppException("删除家庭成员信息内容为空");
        }
        if(StringUtils.isNotBlank(jtcyid)){
            bdcJtcyFeignService.deleteBdcJtcyByGzlslid(jtcyid,gzlslid);
        }
    }

    /**
     * @param qlrid 权利人ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  删除权利人与家庭成员
     */
    @DeleteMapping(value = "/qlrAndjtcy")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBdcQlrAndJtcyxx(@RequestParam("qlrid") String qlrid,@RequestParam("gzlslid") String gzlslid) throws Exception{
        //批量删除当前流程家庭成员
        bdcJtcyFeignService.deleteBatchJtcyByGzlslid(qlrid, gzlslid);
        //批量删除权利人
        bdcSlQlrFeignService.deletePlzhBdcQlr(qlrid,gzlslid);
    }



}

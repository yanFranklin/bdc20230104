package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.etl.core.domian.nantong.BdcDsfRlsbDO;
import cn.gtmap.realestate.etl.core.dto.CheckFaceResultDTO;
import cn.gtmap.realestate.etl.service.FaceCheckInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "人脸识别")
@RequestMapping("/realestate-etl/rest/v1.0/face/check")
public class ThirdPartyFaceCheckRestController {

    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private FaceCheckInterface faceCheckInterface;

    @Autowired
    @Qualifier("entityMapper")
    private EntityMapper entityMapper;


    /**
     * @param qlrid 权利人ID
     * @return 权利人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据权利人ID 获取权利人（用于权利人详细）
     */
    @ResponseBody
    @GetMapping("/query/qlr")
    public Object queryQlr(@RequestParam String qlrid) {
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        if (StringUtils.isNotBlank(qlrid)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrid(qlrid);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                bdcQlrDO = bdcQlrDOList.get(0);
            }
        }
        if (bdcQlrDO!=null){
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(bdcQlrDO));
            //获取当前相关人员信息
            Example queryBdcDsfRlsbParam = new Example(BdcDsfRlsbDO.class);
            queryBdcDsfRlsbParam.createCriteria().andEqualTo("ywnum", bdcQlrDO.getXmid()).andEqualTo("idcode",bdcQlrDO.getZjh());
            List<BdcDsfRlsbDO> personInfo = entityMapper.selectByExample(queryBdcDsfRlsbParam);
            if (CollectionUtils.isNotEmpty(personInfo)) {
                jsonObject.put("rlsbppd",personInfo.get(0).getSimilar());
                return jsonObject;
            }
        }
        return bdcQlrDO;
    }

    /**
     * @param xmid
     * @return 权利人
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据项目id获取人脸识别业务数据
     */
    @ResponseBody
    @GetMapping("/query/rlsbInfo")
    public String queryRlsbInfo(@RequestParam String xmid) {
        try {
            return faceCheckInterface.checkResultInfo(xmid);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * @param checkFaceResultDTO
     * @return 权利人
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 保存人脸识别返回参数
     */
    @ResponseBody
    @PostMapping("/save/rlsb/result")
    public String saveCheckFaceResult(@RequestBody CheckFaceResultDTO checkFaceResultDTO) {
        try {
            return faceCheckInterface.checkResultInfo(JSON.toJSONString(checkFaceResultDTO));
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}

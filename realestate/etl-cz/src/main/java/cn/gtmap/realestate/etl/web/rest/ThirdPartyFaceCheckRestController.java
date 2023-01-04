package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.etl.core.domian.BdcDsfRlsbDO;
import cn.gtmap.realestate.etl.core.dto.CheckFacePictureDTO;
import cn.gtmap.realestate.etl.core.dto.CheckFaceResultDTO;
import cn.gtmap.realestate.etl.service.FaceCheckInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "人脸识别")
@RequestMapping("/realestate-etl/rest/v1.0/face/check")
public class ThirdPartyFaceCheckRestController {

    private final Logger logger = LoggerFactory.getLogger(ThirdPartyFaceCheckRestController.class);

    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private FaceCheckInterface faceCheckInterface;

    @Autowired
    @Qualifier("bdcEntityMapper")
    private EntityMapper bdcEntityMapper;


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
            List<BdcDsfRlsbDO> personInfo = bdcEntityMapper.selectByExample(queryBdcDsfRlsbParam);
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
        logger.info("人脸识别查询业务数据接口入参:{}",xmid);
        try {
            String result= faceCheckInterface.checkResultInfo(xmid);
            logger.info("人脸识别查询业务数据接口返回结果:{},{}",xmid,result);
            return result;
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
        logger.info("人脸识别保存业务数据接口入参:{}",checkFaceResultDTO != null?JSON.toJSONString(checkFaceResultDTO):"");
        try {
            String result = faceCheckInterface.checkResultInfo(JSON.toJSONString(checkFaceResultDTO));
            logger.info("人脸识别保存业务数据接口返回结果:{},{}",checkFaceResultDTO != null?JSON.toJSONString(checkFaceResultDTO):"",result);
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
      *  保存人脸识别接口上传的图片，更新文件id到项目表
      * @param
      * @return
      * @Date 2021/11/5
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
         */
    @ResponseBody
    @PostMapping("/save/rlsb/picture")
    public CommonResponse saveCheckFacePicture(@RequestBody CheckFacePictureDTO checkFacePictureDTO) {
        logger.info("人脸识别保存图片接口入参:{}",checkFacePictureDTO != null?JSON.toJSONString(checkFacePictureDTO):"");
        if(null!= checkFacePictureDTO && StringUtils.isNotBlank(checkFacePictureDTO.getXczpimg())
        && StringUtils.isNotBlank(checkFacePictureDTO.getYwnum())){
            try {
                CommonResponse commonResponse = faceCheckInterface.saveCheckFacePicture(JSON.toJSONString(checkFacePictureDTO));
                logger.info("人脸识别保存图片接口返回结果:{},{}",JSON.toJSONString(checkFacePictureDTO),commonResponse);
                return commonResponse;

            }catch (Exception e){
                return CommonResponse.fail(e.getMessage());
            }

        }
        return CommonResponse.fail("参数不能为空！");

    }

    /**
     *  保存人脸识别接口上传的图片，更新文件id到项目表
     * @param
     * @return
     * @Date 2021/11/5
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @ResponseBody
    @GetMapping("/query/rlsb/picture")
    public String queryCheckFacePicture(@RequestParam String ywnum) {
        logger.info("人脸识别查询图片接口入参:{}",ywnum);
        if(StringUtils.isNotBlank(ywnum)){

            try {
                String result= faceCheckInterface.queryCheckFacePicture(ywnum);
                logger.info("人脸识别查询图片接口返回结果:{},{}",ywnum,result);
                return result;

            }catch (Exception e){
                return e.getMessage();
            }

        }
        return "参数不能为空！";

    }
}

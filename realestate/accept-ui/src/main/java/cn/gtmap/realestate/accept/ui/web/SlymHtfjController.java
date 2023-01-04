package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.accept.BdcHtfjDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcHtfjmxDTO;
import cn.gtmap.realestate.common.core.dto.accept.HtfjResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcHtfjQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.PageUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;


/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 2022/8/26
 * @description 受理页面合同附件
 */
@Controller
@RequestMapping("/htfj")
public class SlymHtfjController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(SlymHtfjController.class);

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    StorageClientMatcher storageClient;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    /**
     * @param bdcHtfjQO
     * @return 新的实体bdcSlXqxxDO
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 获取合同附件信息
     */
    @ResponseBody
    @PostMapping("/getHtfj")
    public Object getHtfj(@LayuiPageable Pageable pageable, @RequestBody BdcHtfjQO bdcHtfjQO) throws Exception {
        if(bdcHtfjQO == null){
            throw new MissingArgumentException("查询参数不能为空");
        }
        if(StringUtils.isBlank(bdcHtfjQO.getHtbh())&&StringUtils.isBlank(bdcHtfjQO.getDzbah())
                &&StringUtils.isBlank(bdcHtfjQO.getXmmc())&&StringUtils.isBlank(bdcHtfjQO.getZdbh())){
            throw new MissingArgumentException("查询参数不能为空");
        }
        Map htfjParams = new HashMap();
        htfjParams.put("htbh",bdcHtfjQO.getHtbh());
        htfjParams.put("dzbah",bdcHtfjQO.getDzbah());
        htfjParams.put("xmmc",bdcHtfjQO.getXmmc());
        htfjParams.put("zdbh",bdcHtfjQO.getZdbh());
        logger.info("合同附记查询接口组装参数：{}", htfjParams.toString());
        Object response = exchangeInterfaceFeignService.requestInterface("yzt_htfj", htfjParams);
        List<HtfjResponseDTO> qxpfList = gethtfjList(response);
        return super.addLayUiCode(PageUtils.listToPage(qxpfList, pageable));
    }

    /**
     * @param bdcHtfjDTO
     * @return 新的实体bdcSlXqxxDO
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 上传合同附件
     */
    @ResponseBody
    @PostMapping("/schtfj")
    public void schtfj(@RequestBody BdcHtfjDTO bdcHtfjDTO) throws Exception {
        if(bdcHtfjDTO == null || StringUtils.isBlank(bdcHtfjDTO.getGzlslid())){
            throw new MissingArgumentException("参数gzlslid不能为空");
        }
        // 不动产登记申请表及询问笔录
        if(CollectionUtils.isNotEmpty(bdcHtfjDTO.getBdcsqb_xwbl_fj())){
            uploadHtfj(bdcHtfjDTO.getBdcsqb_xwbl_fj(),bdcHtfjDTO.getGzlslid());
        }
        // 申请人身份证明复印件
        if(CollectionUtils.isNotEmpty(bdcHtfjDTO.getSqr_sfzm_fj())){
            uploadHtfj(bdcHtfjDTO.getSqr_sfzm_fj(),bdcHtfjDTO.getGzlslid());
        }
        // 出让合同
        if(CollectionUtils.isNotEmpty(bdcHtfjDTO.getCrht_fj())){
            uploadHtfj(bdcHtfjDTO.getCrht_fj(),bdcHtfjDTO.getGzlslid());
        }
        // 土地交接单
        if(CollectionUtils.isNotEmpty(bdcHtfjDTO.getTdjjd_fj())){
            uploadHtfj(bdcHtfjDTO.getTdjjd_fj(),bdcHtfjDTO.getGzlslid());
        }
        // 不动产权籍调查资料
        if(CollectionUtils.isNotEmpty(bdcHtfjDTO.getBdcqj_dczl_fj())){
            uploadHtfj(bdcHtfjDTO.getBdcqj_dczl_fj(),bdcHtfjDTO.getGzlslid());
        }
    }

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @date 2022/5/13 10:49
     * @param
     * @return
     * @description
     **/
    private void uploadHtfj(List<BdcHtfjmxDTO> list ,String gzlslid) throws IOException {
        if(CollectionUtils.isNotEmpty(list)){
            for(BdcHtfjmxDTO bdcHtfjmxDTO:list){
                // 判断文件信息是否都存在
                if(StringUtils.isNotBlank(bdcHtfjmxDTO.getUrl()) && StringUtils.isNotBlank(bdcHtfjmxDTO.getFloderName()) && StringUtils.isNotBlank(bdcHtfjmxDTO.getName())){
                    logger.info("上传合同附件查询接口组装参数：{},工作流实例ID：{}", bdcHtfjmxDTO.toString(), gzlslid);
                    String[] split = bdcHtfjmxDTO.getName().split("\\.");
                    // 附件后缀
                    String fileSuffix="";
                    if(split.length == 2){
                        fileSuffix= "." + split[1];
                        byte[] data = Base64Utils.getFile(bdcHtfjmxDTO.getUrl());
                        String base64Str = "data:application/octet-stream;base64," + Base64Utils.encodeByteToBase64Str(data);
                        bdcUploadFileUtils.uploadBase64File(base64Str, gzlslid, bdcHtfjmxDTO.getFloderName(), split[0], fileSuffix);
                    }
                }
            }
        }
    }

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @date 2022/5/13 10:49
     * @param
     * @return
     * @description
     **/
    private List<HtfjResponseDTO> gethtfjList(Object response){
        List<HtfjResponseDTO> list = new ArrayList<>();
        JSONArray data = null;
        if (response != null) {
            logger.info("获取合同附件接口调用成功，响应内容：{}", JSONObject.toJSONString(response));
            JSONObject content = JSONObject.parseObject(JSONObject.toJSONString(response));
            if(!"true".equals(content.getString("result"))){
                throw new AppException(content.getString("msg"));
            }
            if (content.getJSONArray("data") != null) {
                data = content.getJSONArray("data");
                if(data!= null){
                    List<HtfjResponseDTO> htfjResponseDTOS = JSONArray.parseArray(data.toJSONString(), HtfjResponseDTO.class);
                    return htfjResponseDTOS;
                }
            }
        }
        return list;
    }

}

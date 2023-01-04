package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcEcertificateConfigDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.HefeiDzzzZzDataDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDzzzCxDTO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.enums.ECertificateErrorMsgEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDzzzQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDzzzZzQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzAppearCityFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzGxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcDzzzCzRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.service.BdcDzzzCzService;
import cn.gtmap.realestate.inquiry.util.Constants;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2020/2/24
 * @description 电子证照操作服务接口
 */
@RestController
public class BdcDzzzCzRestController implements BdcDzzzCzRestService {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDzzzCzRestController.class);

    /**
     * 电子证照操作服务接口
     */
    @Autowired
    BdcDzzzCzService bdcDzzzCzService;

    /**
     * 电子证照共享接口
     */
    @Autowired
    BdcDzzzGxFeignService bdcDzzzGxFeignService;

    /**
     * 电子证照市级中转接口
     */
    @Autowired
    BdcDzzzAppearCityFeignService bdcDzzzAppearCityFeignService;

    /**
     * 电子证照接口无token
     */
    @Autowired
    BdcDzzzFeignService bdcDzzzFeignService;

    @Autowired
    ECertificateFeignService eCertificateFeignService;

    /**
     * 获取用户信息
     */
    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * 查询项目信息服务
     */
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    /**
     * 字典服务
     */
    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    EntityMapper entityMapper;

    /**
     * 证书服务
     */
    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    /**
     * 查询不动产登记库中的证书、证明，可通过受理编号、坐落、产权证号精确查询和模糊查询。
     *
     * @param pageable      分页
     * @param bdcDzzzQOJson 台账查询参数
     * @return 分页证书证明
     * @Date 2020/2/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询证书证明，用于电子证照操作")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bdcDzzzQOJson", value = "证书证明查询信息参数JSON", required = false, paramType = "query")})
    public Page<BdcDzzzCxDTO> listBdcDzzzByPage(Pageable pageable, String bdcDzzzQOJson) {
        return bdcDzzzCzService.listBdcDzzzByPage(pageable, JSONObject.parseObject(bdcDzzzQOJson, BdcDzzzQO.class));
    }

    /**
     * 获取访问电子证照token
     *
     * @return token
     * @Date 2020/2/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取访问电子证照token")
    public String getToken() {
        //获取配置
        BdcEcertificateConfigDTO bdcEcertificateConfigDTO = eCertificateFeignService.queryConfig();
        Map yymcMap = new HashMap(1);
        if (StringUtils.isEmpty(bdcEcertificateConfigDTO.getTokenYymc())) {
            bdcEcertificateConfigDTO.setTokenYymc("bdcdj");
        }
        yymcMap.put("yymc", bdcEcertificateConfigDTO.getTokenYymc());

        Map<String, Object> dataMap = new HashMap(1);
        dataMap.put("data", yymcMap);
        String jsonString = JSONObject.toJSONString(dataMap);
        DzzzResponseModel dzzzResponseModel = bdcDzzzGxFeignService.creatToken(jsonString);
        if (ECertificateErrorMsgEnum.MSG1.getCode().equals(dzzzResponseModel.getHead().getStatus())) {
            LOGGER.warn("获取token失败!  message:{}, TokenJson:{}", dzzzResponseModel.getHead().getMessage(), JSONObject.toJSONString(dataMap));
            return "";
        }
        String dzzzData = JSONObject.toJSONString(dzzzResponseModel.getData());
        JSONObject dataObject = JSONObject.parseObject(dzzzData);
        if (dataObject.containsKey("token")) {
            return dataObject.getString("token");
        }
        return "";
    }


    /**
     * 创建电子证照
     *
     * @param xmids 证书ids
     * @return 数量
     * @Date 2020/2/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("生成电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmids", value = "xmids集合", required = false, paramType = "body")})
    public Integer createDzzz(@RequestBody List<String> xmids) {
        if (CollectionUtils.isEmpty(xmids)) {
            throw new MissingArgumentException("缺失查询参数xmids");
        }
        String currentUserName = userManagerUtils.getCurrentUserName();
        //记录要生成的电子证照数量
        Integer zsidsCount = 0;
        //统计生成成功的电子证照数量
        Integer count = 0;
        //前端返回标识：1-全部生成成功；2-部分生成成功；3-全部生成失败
        Integer returnFlag = 0;
        StringBuilder tsxx = new StringBuilder();

        for (String xmid : xmids) {
            //获取项目信息
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isEmpty(bdcXmDOS)) {
                throw new MissingArgumentException("无项目信息！");
            }
            BdcXmDO bdcXmDO = bdcXmDOS.get(0);

            // 生成项目的电子证照信息
            List<DzzzResponseModel<Map>> dzzzResponseModelList = eCertificateFeignService.createXmECertificate(bdcXmDO, currentUserName);
            if (CollectionUtils.isNotEmpty(dzzzResponseModelList)) {
                zsidsCount += CollectionUtils.size(dzzzResponseModelList);

                // 处理请求结果
                for (DzzzResponseModel<Map> dzzzResponseModel : dzzzResponseModelList) {
                    if (StringUtils.equals(Constants.STATUS_SUCCESS, dzzzResponseModel.getHead().getStatus())) {
                        count++;
                    } else {
                        //对照错误代码
                        String errorMessage = errorMessage(dzzzResponseModel.getHead().getMessage());
                        tsxx.append(bdcXmDO.getSlbh() + " 生成失败：" + errorMessage + "--->" + dzzzResponseModel.getData() + "<br/> ");
                    }
                }
            }

            // 注销上一手电子证照(此处是按照项目注销的，所以只注销当前项目的需要注销的原项目的证书)
            eCertificateFeignService.cancelYxmECertificate(bdcXmDO, currentUserName);
        }

        if (StringUtils.isNotBlank(tsxx)) {
            throw new AppException(tsxx.toString());
        }
        //判断是全部生成成功还是部分成功
        if (zsidsCount > count && count != 0) {
            returnFlag = 2;
            LOGGER.warn("批量生成电子证照，部分失败！未成功生成的数量为{}", zsidsCount - count);
            return returnFlag;
        }
        if (zsidsCount.equals(count)) {
            returnFlag = 1;
            LOGGER.warn("生成电子证照全部成功，全部成功的数量为{}", zsidsCount);
            return returnFlag;
        }
        if (0 == count) {
            returnFlag = 3;
            LOGGER.warn("批量生成电子证照全部失败！数量为{}", zsidsCount);
            return returnFlag;
        }
        return returnFlag;
    }

    /**
     * @param xmids
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/2/29
     * @description 注销电子证照
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("注销电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmids", value = "xmids集合", required = false, paramType = "body")})
    public Integer cancelDzzz(@RequestBody List<String> xmids) {
        if (CollectionUtils.isEmpty(xmids)) {
            throw new MissingArgumentException("缺失参数xmids");
        }
        Integer count = 0;

        for (String xmid : xmids) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            //查询出来项目信息，准备组织参数
            bdcXmQO.setXmid(xmid);
            //获取项目信息
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isEmpty(bdcXmDOS)) {
                throw new MissingArgumentException("无项目信息！");
            }
            BdcXmDO bdcXmDO = bdcXmDOS.get(0);

            // 注销当前项目的电子证照信息
            List<DzzzResponseModel> dzzzResponseModelList = eCertificateFeignService.cancelXmECertificate(bdcXmDO, userManagerUtils.getCurrentUserName());

            if (CollectionUtils.isNotEmpty(dzzzResponseModelList)) {
                for (DzzzResponseModel dzzzResponseModel : dzzzResponseModelList) {
                    if (StringUtils.equals(CommonConstantUtils.STATUS_SUCCESS, dzzzResponseModel.getHead().getStatus())) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * @param xmids
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/2/29
     * @description 作废电子证照
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("作废电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmids", value = "xmids集合", required = false, paramType = "body")})
    public Integer zfDzzz(@RequestBody List<String> xmids) {
        LOGGER.warn("作废电子证照，操作用户{}", userManagerUtils.getCurrentUserName());
        if (CollectionUtils.isEmpty(xmids)) {
            throw new MissingArgumentException("缺失参数xmids");
        }
        Integer count = 0;
        StringBuilder tsxx = new StringBuilder();

        for (String xmid : xmids) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isEmpty(bdcXmDOS)) {
                throw new MissingArgumentException("无项目信息！");
            }
            BdcXmDO bdcXmDO = bdcXmDOS.get(0);

            // 作废当前项目的电子证照
            List<DzzzResponseModel> dzzzResponseModelList = eCertificateFeignService.zfXmECertificate(bdcXmDO, userManagerUtils.getCurrentUserName());
            if (CollectionUtils.isNotEmpty(dzzzResponseModelList)) {
                for (DzzzResponseModel dzzzResponseModel : dzzzResponseModelList) {
                    if (StringUtils.equals(CommonConstantUtils.STATUS_SUCCESS, dzzzResponseModel.getHead().getStatus())) {
                        count++;
                    } else {
                        //对照错误代码
                        String errorMessage = errorMessage(dzzzResponseModel.getHead().getMessage());
                        tsxx.append(bdcXmDO.getSlbh() + " 作废失败：" + errorMessage + "--->" + dzzzResponseModel.getData() + "<br/> ");
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(tsxx)) {
            throw new AppException(tsxx.toString());
        }
        return count;
    }
    /**
     * 判断电子证照是否是历史状态
     *
     * @return flag 1是现势，2为历史
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("判断电子证照是否是历史状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "zsids", value = "zsid", paramType = "body")})

    public Integer sfYzx(@RequestBody List<String> zsids) {
        //返回判断标识
        Integer flag = 1;
        StringBuilder tsxx = new StringBuilder();

        if (CollectionUtils.isEmpty(zsids)) {
            throw new MissingArgumentException("缺少参数！");
        }
        List<DzzzResponseModel<List<Map>>> dzzzResponseModelList = eCertificateFeignService.checkZzzt(zsids);
        if (CollectionUtils.isNotEmpty(dzzzResponseModelList)) {
            for (DzzzResponseModel<List<Map>> dzzzResponseModel : dzzzResponseModelList) {
                if (StringUtils.equals(Constants.STATUS_SUCCESS, dzzzResponseModel.getHead().getStatus())) {
                    if (CollectionUtils.isNotEmpty(dzzzResponseModel.getData())) {
                        // 取创建时间最晚的一条查看证照状态
                        Map mapTemp = dzzzResponseModel.getData().get(0);
                        if (MapUtils.isNotEmpty(mapTemp) && mapTemp.containsKey("zzzt")) {
                            //标识查询成功
                            String zzzt = MapUtils.getString(mapTemp, "zzzt");
                            if ("2".equals(zzzt)) {
                                //证照为历史状态
                                flag = 2;
                                return flag;
                            }
                        }
                    }
                } else {
                    //对照错误代码
                    String errorMessage = errorMessage(dzzzResponseModel.getHead().getMessage());
                    tsxx.append(" 验证状态失败：" + errorMessage + "--->" + dzzzResponseModel.getData() + "<br/> ");
                }
            }
        }
        if (StringUtils.isNotBlank(tsxx)) {
            throw new AppException(tsxx.toString());
        }
        return flag;
    }

    /**
     * 下载电子证照并上传到登记
     *
     * @param xmid xmid
     * @return 创建个数
     * @date 2020/7/7
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("下载电子证照并上传到登记")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "xmid", required = true, paramType = "query")})
    public void reUploadDzzz(@RequestParam String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }
        String currentUserName = userManagerUtils.getCurrentUserName();
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
        if (null == bdcXmDO) {
            throw new AppException("下载电子证照未查询到登记中项目信息");
        }
        eCertificateFeignService.reUploadECertificate(bdcXmDO, currentUserName);
    }

    /**
     * 更新电子证照的 storageid 到证书表
     *
     * @param stroageid stroageid
     * @param zsid      zsid
     * @date 2020/7/14
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public void updateZsStroageId(@RequestParam(value = "stroageid") String stroageid, @RequestParam(value = "zsid") String zsid) {
        if (StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("zsid");
        }
        if (StringUtils.isBlank(stroageid)) {
            throw new MissingArgumentException("stroageid");
        }
        BdcZsDO bdcZsDO = new BdcZsDO();
        bdcZsDO.setStorageid(stroageid);
        bdcZsDO.setZsid(zsid);
        bdcZsFeignService.updateBdcZs(bdcZsDO);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcDzzzZzQO]
     * @return java.lang.Object
     * @description 合肥 查询电子证照需要制证的台账 （权属状态为现势、流程状态已办结但没有生成电子证照的数据）
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询电子证照需要制证的台账")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDzzzZzQO", value = "bdcDzzzZzQO", required = true, paramType = "body")})
    public Page<HefeiDzzzZzDataDTO> queryDzzzZz(@RequestBody BdcDzzzZzQO bdcDzzzZzQO) {
        return bdcDzzzCzService.queryDzzzZz(bdcDzzzZzQO);
    }

    /**
     * @param code 错误代码
     * @return String 对应错误值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 错误代码对照表
     */
    private String errorMessage(String code) {
        ECertificateErrorMsgEnum tempEnum = ECertificateErrorMsgEnum.getType(code);
        if (null != tempEnum) {
            return tempEnum.getMsg();
        }
        return "";
    }
}

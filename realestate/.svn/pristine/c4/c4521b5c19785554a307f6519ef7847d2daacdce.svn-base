package cn.gtmap.realestate.inquiry.web.rest.changzhou;

import cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseData;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.enums.ECertificateErrorMsgEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.rest.inquiry.changzhou.BdcZzqzCzRestService;
import cn.gtmap.realestate.inquiry.util.Constants;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 电子签章操作服务
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 下午8:09 2021/1/11
 */
@RestController
public class BdcZzqzCzRestController implements BdcZzqzCzRestService {

    /**
     * 正常办理登记业务注销
     */
    private static final Integer ZX = 1;

    /**
     * 电子证照签发错误注销
     */
    private static final Integer ZF = 2;

    @Autowired
    private ECertificateFeignService eCertificateFeignService;

    /**
     * 签章生成
     *
     * @param xmids 项目 id 集合
     * @return 1 全部创建成功； 2 部分创建成功； 3 全部创建失败
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("签章生成")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmids", value = "xmids集合", required = true, paramType = "body")})
    public Integer createDzzz(@RequestBody List<String> xmids) {
        if (CollectionUtils.isEmpty(xmids)) {
            throw new MissingArgumentException("缺失查询参数xmids");
        }
        Integer count = 0;
        List<DzzzResponseData> responseDatas = Lists.newArrayList();
        for (String xmid : xmids) {
            responseDatas.addAll(eCertificateFeignService.createZzqz(xmid));
        }

        if (CollectionUtils.isNotEmpty(responseDatas)) {
            for (DzzzResponseData responseData : responseDatas) {
                if (Boolean.TRUE.equals(responseData.isStatus())) {
                    count++;
                }
            }
        }
        return generateCode(xmids, count);
    }

    /**
     * 注销/作废 证照签章
     *
     * @param xmids 项目 id 集合
     * @return java.lang.Integer 电子证照数目
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("注销/作废电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmids", value = "xmids集合", required = true, paramType = "body")})
    public Integer cancelDzzz(@RequestBody List<String> xmids) {
        if (CollectionUtils.isEmpty(xmids)) {
            throw new MissingArgumentException("缺失参数xmids");
        }
        return this.zfzxZzqz(xmids, ZX);
    }

    /**
     * 作废 电子签章
     *
     * @return java.lang.Integer 电子证照数目
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("作废电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmids", value = "xmids集合", required = false, paramType = "body")})
    public Integer zfDzzz(@RequestBody List<String> xmids) {
        return this.zfzxZzqz(xmids, ZF);
    }

    /**
     * 作废/注销 电子签章
     *
     * @param xmids  xmids
     * @param status status
     * @return java.lang.Integer
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    private Integer zfzxZzqz(List<String> xmids, Integer status) {
        Integer count = 0;

        for (String xmid : xmids) {
            List<DzzzResponseData> responseDatas = eCertificateFeignService.cancelZzqz(xmid, status);

            if (CollectionUtils.isNotEmpty(responseDatas)) {
                for (DzzzResponseData responseData : responseDatas) {
                    if (Boolean.TRUE.equals(responseData.isStatus())) {
                        count++;
                    }
                }
            }
        }

        return generateCode(xmids, count);
    }

    /**
     * 判断是否是历史状态
     *
     * @param zsids 证书ids
     * @return java.lang.Integer 1是现势，2为历史
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
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
                        for (Map mapTemp : dzzzResponseModel.getData()) {
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
     * 处理返回值
     *
     * @param xmids 传入参数
     * @param count 操作失败数目
     * @return 1 全部创建成功； 2 部分创建成功； 3 全部创建失败
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    private Integer generateCode(List<String> xmids, Integer count) {
        if (count == xmids.size()) {
            return 3;
        } else if (count == 0) {
            return 1;
        } else {
            return 2;
        }
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

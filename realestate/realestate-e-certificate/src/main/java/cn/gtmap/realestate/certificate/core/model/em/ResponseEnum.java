package cn.gtmap.realestate.certificate.core.model.em;

import cn.gtmap.realestate.certificate.util.Constants;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/5/31
 */
public enum ResponseEnum {

    SUCCESS("0", "success"), FALSE("1", "false")
    , DATABASE_INSERT("-1", "数据库插入数据失败", Constants.RESPONSE)
    , DATABASE_UPDATE("-2", "数据库更新数据失败", Constants.RESPONSE)
    , DATABASE_DELETE("-3", "数据库删除数据失败", Constants.RESPONSE)

    // 结果返回消息 开始
    // 未获得授权
    , RESPONSE_UNAUTHORIZED_ERROR("1", "未获得授权", Constants.RESPONSE)
    // 服务超时
    , RESPONSE_SERVICE_TIMEOUT_ERROR("2", "服务超时", Constants.RESPONSE)
    // 未检索到记录
    , RESPONSE_NO_RECORD_ACQUIRED_ERROR("3", "未检索到记录", Constants.RESPONSE)
    // 参数错误
    , RESPONSE_PARAMETER_ERROR("4", "参数错误", Constants.RESPONSE)

    // 验证项返回消息 开始
    // 电子证照信息必填项验证
    , CHECK_DZZZ_REQUIRED("5", "缺失必填字段", Constants.RESPONSE)
    // 验证是否已生成过电子证照PDF
    , CHECK_DZZZ_CREATE("6", "已生成过电子证照PDF", Constants.RESPONSE)
    // 电子证照信息已入库
    , CHECK_DZZZ_INSERT("7", "电子证照信息已入库", Constants.RESPONSE)
    // 上传附件中心失败
    , RESPONSE_UPLOAD_FILE_CENTER_ERROR("8", "上传附件中心失败", Constants.RESPONSE)
    // 创建电子证照PDF失败
    , RESPONSE_CREATE_PDF_ERROR("9", "生成电子证照PDF失败", Constants.RESPONSE)
    // 电子证照签章失败
    , RESPONSE_SIGNATURE_PDF_ERROR("10", "电子证照签章失败", Constants.RESPONSE)
    // 电子证照已注销
    , RESPONSE_CERTIFICATE_CANCELLED_ERROR("11", "电子证照已注销", Constants.RESPONSE)
    // 生成zzbs失败
    , RESPONSE_CREATE_ZZBS_ERROR("12", "生成zzbs失败", Constants.RESPONSE)
    // 验证数字签名失败
    , RESPONSE_SIGNTURE_ERROR("13", "验证数字签名失败", Constants.RESPONSE)
    // 水印添加失败
    , RESPONSE_WATERMARK_ERROR("14", "水印添加失败", Constants.RESPONSE)
    // 未找到模板
    , RESPONSE_NO_TEMPLATE_ACQUIRED_ERROR("15", "未找到模板", Constants.RESPONSE)
    // 接口访问数量已达到峰值
    , RESPONSE_NO_REQUEST_LIMIT_ERROR("16", "接口访问数量已达到峰值", Constants.RESPONSE)
    // 服务访问限制
    , RESPONSE_NO_REQUEST_EXECUTE_ERROR("17", "服务访问限制", Constants.RESPONSE)


    // 文件验证通过
    , VERIFY_FILE_WJYZTG("0", "文件验证通过", Constants.VERIFY_FILE_RESPONSE)
    // 文件签章校验失败
    , VERIFY_FILE_WJQZJYSB("1", "文件签章校验失败", Constants.VERIFY_FILE_RESPONSE)
    // 文件校验失败
    , VERIFY_FILE_WJJYSB("2", "文件校验失败", Constants.VERIFY_FILE_RESPONSE)

    // 信息正确且有效
    , VERIFY_INFO_ZQYX("0", "信息正确且有效", Constants.VERIFY_INFO_RESPONSE)
    // 信息正确且未激活
    , VERIFY_INFO_ZQWJH("1", "信息正确且未激活", Constants.VERIFY_INFO_RESPONSE)
    // 信息正确且证照暂时失效
    , VERIFY_INFO_ZQZSSX("2", "信息正确且证照暂时失效", Constants.VERIFY_INFO_RESPONSE)
    // 信息正确且证照已失效
    , VERIFY_INFO_ZQYSX("3", "信息正确且证照已失效", Constants.VERIFY_INFO_RESPONSE)
    // 信息不全，无法验证
    , VERIFY_INFO_XXBQ("4", "信息不全，无法验证", Constants.VERIFY_INFO_RESPONSE)
    // 查无此信息
    , VERIFY_INFO_CWCXX("5", "查无此信息", Constants.VERIFY_INFO_RESPONSE)
    // 信息错误，参数不一致
    , VERIFY_INFO_CSBYZ("6", "信息错误，参数不一致", Constants.VERIFY_INFO_RESPONSE)


    // base64码转文件失败
    , BASE64_CONVERT_FILE_ERROR("22", "base64码转文件失败", Constants.RESPONSE);

    private String code;
    private String msg;
    private String type;

    private ResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResponseEnum(String code, String msg, String type) {
        this.code = code;
        this.msg = msg;
        this.type = type;
    }

    public static Map<String,String> getResponseEnumMap(){
        Map<String,String> responseMap = new HashedMap(3);
        ResponseEnum[] responseEnums = ResponseEnum.values();
        if (null != responseEnums && responseEnums.length > 0) {
            for (ResponseEnum responseEnum : responseEnums) {
                if (StringUtils.equals(Constants.RESPONSE, responseEnum.getType())) {
                    responseMap.put(responseEnum.getCode(),responseEnum.getMsg());
                }
            }
        }

        return responseMap;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getType() {
        return type;
    }
}

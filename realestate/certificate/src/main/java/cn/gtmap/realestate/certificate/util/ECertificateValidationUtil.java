package cn.gtmap.realestate.certificate.util;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.DzzzClDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EQlrxxDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.validator.certificate.DzzzZm;
import cn.gtmap.realestate.common.util.validator.certificate.DzzzZs;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;
import java.util.StringJoiner;

import static cn.gtmap.realestate.common.util.ValidatorUtils.sjValidate;

/**
 * 电子证照特殊规则参数校验
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 */
@Validated
@Component
public class ECertificateValidationUtil {

    /**
     * 存量电子证照签发签章参数校验
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public static String clDzzzValidation(DzzzClDTO dzzzClDTO, BdcZsDO bdcZsDO, BdcXmDO bdcXmDO) {
        Integer zslx = bdcZsDO.getZslx();
        StringJoiner sj = new StringJoiner(";");
        // 校验参数
        sj.merge(sjValidate(dzzzClDTO, CommonConstantUtils.ZSLX_ZS.equals(zslx) ? DzzzZs.class : DzzzZm.class));
        // 特殊逻辑手动循环校验
        if (CollectionUtils.isNotEmpty(dzzzClDTO.getQlrxx())) {
            for (EQlrxxDTO eQlrxxDTO : dzzzClDTO.getQlrxx()) {
                if (CommonConstantUtils.GYFS_AFGY.equals(bdcXmDO.getGyfs()) && CollectionUtils.isNotEmpty(dzzzClDTO.getQlrxx())) {
                    appendBlank(sj, eQlrxxDTO.getCzztgybl(), "权利人共有比例");
                }
            }
        }
        return StringUtils.isNotBlank(sj.toString()) ? "zsid: " + bdcZsDO.getZsid() + "—— 参数：" + sj.toString() + "为空" : "";
    }


    private static void appendBlank(StringJoiner sj, Object value, String name) {
        // Obj 对象为空，或者 "" 字符串
        if (Objects.isNull(value) || (value instanceof String && StringUtils.isBlank((String) value))) {
            sj.add(name);
        }
    }
}

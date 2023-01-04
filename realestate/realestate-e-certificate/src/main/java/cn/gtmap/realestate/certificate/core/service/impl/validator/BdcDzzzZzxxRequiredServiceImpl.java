package cn.gtmap.realestate.certificate.core.service.impl.validator;

import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxCzztDo;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.DzzzValidate;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzValidateService;
import cn.gtmap.realestate.certificate.core.service.sign.BdcDzqzValidateService;
import cn.gtmap.realestate.certificate.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/6
 */
public class BdcDzzzZzxxRequiredServiceImpl implements BdcDzzzValidateService, BdcDzqzValidateService {

    /**
     * <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     *
     * @param dzzzValidate
     * @return
     * @description 电子证照信息必填项验证
     */
    @Override
    public Map<String, Object> validate(DzzzValidate dzzzValidate) {
        Map<String, Object> resultMap = new HashMap<>();
        BdcDzzzZzxx bdcDzzzZzxx = null;
        if (null != dzzzValidate) {
            bdcDzzzZzxx = dzzzValidate.getBdcDzzzZzxx();
        }
        if (null != bdcDzzzZzxx) {
            if (StringUtils.isBlank(bdcDzzzZzxx.getZzlxdm())) {
                resultMap.put("zzlxdm", "zzlxdm is empty");
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getZzbfjg())) {
                resultMap.put("zzbfjg", "zzbfjg is empty");
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getZzbfjgdm())) {
                resultMap.put("zzbfjgdm", "zzbfjgdm is empty");
            }
            if (null == bdcDzzzZzxx.getZzbfrq()) {
                resultMap.put("zzbfrq", "zzbfrq is null");
            }

            if (StringUtils.isBlank(bdcDzzzZzxx.getCzzt())) {
                resultMap.put("czzt", "czzt is empty");
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getCzztdm())) {
                resultMap.put("czztdm", "czztdm is empty");
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getCzztdmlx())) {
                resultMap.put("czztdmlx", "czztdmlx is empty");
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getCzztdmlxdm())) {
                resultMap.put("czztdmlxdm", "czztdmlxdm is empty");
            }

            if (CollectionUtils.isNotEmpty(bdcDzzzZzxx.getQlrxx())) {
                for (BdcDzzzZzxxCzztDo bdcDzzzZzxxCzztDo : bdcDzzzZzxx.getQlrxx()) {
                    if (StringUtils.isBlank(bdcDzzzZzxxCzztDo.getCzzt())) {
                        resultMap.put("czzt", "qlrxx czzt is empty");
                    }
                    if (StringUtils.isBlank(bdcDzzzZzxxCzztDo.getCzztdm())) {
                        resultMap.put("czztdm", "qlrxx czztdm is empty");
                    }
                    if (StringUtils.isBlank(bdcDzzzZzxxCzztDo.getCzztdmlx())) {
                        resultMap.put("czztdmlx", "qlrxx czztdmlx is empty");
                    }
                    if (StringUtils.isBlank(bdcDzzzZzxxCzztDo.getCzztdmlxdm())) {
                        resultMap.put("czztdmlxdm", "qlrxx czztdmlxdm is empty");
                    }
                }
            } else {
                if (StringUtils.isEmpty(bdcDzzzZzxx.getCzzt())) {
                    resultMap.put("czzt", "czzt is empty");
                }
                if (StringUtils.isBlank(bdcDzzzZzxx.getCzztdm())) {
                    resultMap.put("czztdm", "czztdm is empty");
                }
                if (StringUtils.isBlank(bdcDzzzZzxx.getCzztdmlx())) {
                    resultMap.put("czztdmlx", "czztdmlx is empty");
                }
                if (StringUtils.isBlank(bdcDzzzZzxx.getCzztdmlxdm())) {
                    resultMap.put("czztdmlxdm", "czztdmlxdm is empty");
                }
            }

            if (null == bdcDzzzZzxx.getFzrq()) {
                resultMap.put("fzrq", "fzrq is empty");
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getYwh())) {
                resultMap.put("ywh", "ywh is empty");
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getBdcqzh())) {
                resultMap.put("bdcqzh", "bdcqzh is empty");
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getZhlsh())) {
                resultMap.put("zhlsh", "zhlsh is empty");
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getDwdm())) {
                resultMap.put("dwdm", "dwdm is empty");
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getSqsjc())) {
                resultMap.put("sqsjc", "sqsjc is empty");
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getSzsxqc())) {
                resultMap.put("szsxqc", "szsxqc is empty");
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getBdcdyh())) {
                resultMap.put("bdcdyh", "bdcdyh is empty");
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getZl())) {
                resultMap.put("zl", "zl is empty");
            }
            if (StringUtils.equals(Constants.BDC_ZSLX_MC_ZMS, bdcDzzzZzxx.getZstype())) {
                if (StringUtils.isBlank(bdcDzzzZzxx.getZmqlsx())) {
                    resultMap.put("zmqlsx", "zmqlsx is empty");
                }

                //验证义务人是否为空
                if (StringUtils.isEmpty(bdcDzzzZzxx.getYwr())) {
                    resultMap.put("ywr", "ywr is empty");
                }
            } else if (StringUtils.equals(Constants.BDC_ZSLX_MC_ZS, bdcDzzzZzxx.getZstype())
                    || StringUtils.equals(Constants.BDC_ZSLX_MC_SCDJZ, bdcDzzzZzxx.getZstype())) {
                if (StringUtils.isBlank(bdcDzzzZzxx.getGyqk())) {
                    resultMap.put("gyqk", "gyqk is empty");
                }
                if (StringUtils.isBlank(bdcDzzzZzxx.getQlxz())) {
                    resultMap.put("qlxz", "qlxz is empty");
                }
                if (StringUtils.isBlank(bdcDzzzZzxx.getYt())) {
                    resultMap.put("yt", "yt is empty");
                }
                if (StringUtils.isBlank(bdcDzzzZzxx.getMj())) {
                    resultMap.put("mj", "mj is empty");
                }
                /*if (StringUtils.isBlank(bdcDzzzZzxx.getSyqx())) {
                    resultMap.put("syqx", "syqx is empty");
                }*/
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getQllx())) {
                resultMap.put("qllx", "qllx is empty");
            }
            //验证权利人是否为空
            if (StringUtils.isEmpty(bdcDzzzZzxx.getQlr())) {
                resultMap.put("qlr", "qlr is empty");
            }
            if (StringUtils.isBlank(bdcDzzzZzxx.getNf())) {
                resultMap.put("nf", "nf is empty");
            }

        }
        return resultMap;
    }

    @Override
    public String getCheckCode() {
        return ResponseEnum.CHECK_DZZZ_REQUIRED.getCode();
    }

    @Override
    public String getDescription() {
        return ResponseEnum.CHECK_DZZZ_REQUIRED.getMsg();
    }

}

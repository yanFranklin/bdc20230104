package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.service.BdcQllxService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.dto.accept.QllxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;



/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/2/21
 * @description 权利类型服务
 */
@Service
public class BdcQllxServiceImpl implements BdcQllxService {
    /**
     * 权属类型代码，单元号第14位
     */
    private static final String QSLXDM_A = "A";
    private static final String QSLXDM_BSXWY = "BSXWY";
    private static final String QSLXDM_C = "C";
    private static final String QSLXDM_D = "D";
    private static final String QSLXDM_E = "E";
    private static final String QSLXDM_F = "F";
    private static final String QSLXDM_G = "G";
    private static final String QSLXDM_H = "H";
    private static final String QSLXDM_L = "L";
    private static final String QSLXDM_N = "N";
    private static final String QSLXDM_W = "W";


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 权利性质代码, 单元号第13位
     */
    private static final String QLXZDM_GJZ = "GJZ";
    private static final String QLXZDM_G = "G";
    private static final String QLXZDM_J = "J";
    private static final String QLXZDM_Z = "Z";





    /**
     * @param bdcdyh  不动产单元号
     * @param dyhcxlx 单元号查询类型
     * @return 权利类型
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据不动产单元号获取权利类型
     */
    @Override
    public Integer getQllxByBdcdyh(String bdcdyh, String dyhcxlx) {
        if(StringUtils.isBlank(dyhcxlx)){
            dyhcxlx =Constants.DYHCXLX_TDDZW;
        }
        Integer qllx = null;
        if (StringUtils.isNotBlank(bdcdyh) && StringUtils.length(bdcdyh) == CommonConstantUtils.BDCDYH_LENGTH) {
            QllxDTO qllxDTO = new QllxDTO(StringUtils.substring(bdcdyh, 12, 13), StringUtils.substring(bdcdyh, 13, 14), StringUtils.substring(bdcdyh, 19, 20), dyhcxlx);
            if (StringUtils.equals(CommonConstantUtils.BDCLX_TZM_W, qllxDTO.getDzwtzm())) {
                qllx = getQllxFromTdBdcdy(qllxDTO);
            } else if (StringUtils.equals(CommonConstantUtils.BDCLX_TZM_F, qllxDTO.getDzwtzm())) {
                qllx = getQllxFromFwBdcdy(qllxDTO, dyhcxlx);
            } else if (StringUtils.equals(CommonConstantUtils.BDCLX_TZM_L, qllxDTO.getDzwtzm())) {
                if (StringUtils.contains(QLXZDM_GJZ, qllxDTO.getQlxzdm())) {
                    if (StringUtils.equals(QSLXDM_E, qllxDTO.getQslxdm())) {
                        qllx = 35;
                    } else if (StringUtils.equals(QSLXDM_L, qllxDTO.getQslxdm())) {
                        qllx = 12;
                    }
                }
            } else if (StringUtils.equals(CommonConstantUtils.BDCLX_TZM_Q, qllxDTO.getDzwtzm())) {
                if (StringUtils.equals(QSLXDM_E, qllxDTO.getQslxdm()) && StringUtils.contains(QLXZDM_GJZ, qllxDTO.getQlxzdm())) {
                    qllx = 9;
                }
            }
        } else {
            throw new AppException("不动产单元号:" + bdcdyh + "位数不正确，不允许创建");
        }
        if (qllx == null) {
            throw new AppException("不动产单元号：" + bdcdyh + "无法找到对应的权利类型,单元号查询类型:"+dyhcxlx);
        }

        return qllx;
    }

    /**
     * @param qllxDTO 判断对象
     * @return 权利类型
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据土地不动产单元获取权利类型
     */
    private Integer getQllxFromTdBdcdy(QllxDTO qllxDTO) {
        Integer qllx = null;
        if (StringUtils.equals(QLXZDM_G, qllxDTO.getQlxzdm())) {
            if (StringUtils.equals(QSLXDM_A, qllxDTO.getQslxdm())) {
                qllx = 2;
            } else if (StringUtils.contains(QSLXDM_BSXWY, qllxDTO.getQslxdm())) {
                qllx = 3;
            } else if (StringUtils.equals(QSLXDM_C, qllxDTO.getQslxdm())) {
                qllx = 5;
            } else if (StringUtils.equals(QSLXDM_L, qllxDTO.getQslxdm())) {
                qllx = 11;
            } else if (StringUtils.equals(QSLXDM_D, qllxDTO.getQslxdm()) || StringUtils.equals(QSLXDM_E, qllxDTO.getQslxdm()) || StringUtils.equals(QSLXDM_F, qllxDTO.getQslxdm())) {
                qllx = 9;
            } else if (StringUtils.equals(QSLXDM_N, qllxDTO.getQslxdm())) {
                qllx = 23;
            } else if (StringUtils.equals(QSLXDM_H, qllxDTO.getQslxdm()) || StringUtils.equals(QSLXDM_W, qllxDTO.getQslxdm())) {
                qllx = 15;
            } else if (StringUtils.contains(QSLXDM_G, qllxDTO.getQslxdm())) {
                qllx = 17;
            }
        } else if (StringUtils.equals(QLXZDM_J, qllxDTO.getQlxzdm()) || StringUtils.equals(QLXZDM_Z, qllxDTO.getQlxzdm())) {
            if (StringUtils.equals(QSLXDM_A, qllxDTO.getQslxdm())) {
                qllx = 1;
            } else if (StringUtils.contains(QSLXDM_BSXWY, qllxDTO.getQslxdm())) {
                qllx = 7;
            } else if (StringUtils.equals(QSLXDM_C, qllxDTO.getQslxdm())) {
                qllx = 5;
            } else if (StringUtils.equals(QSLXDM_D, qllxDTO.getQslxdm()) || StringUtils.equals(QSLXDM_F, qllxDTO.getQslxdm())) {
                qllx = 9;
            } else if (StringUtils.equals(QLXZDM_J, qllxDTO.getQlxzdm())) {
                if (StringUtils.equals(QSLXDM_E, qllxDTO.getQslxdm())) {
                    qllx = 9;
                } else if (StringUtils.equals(QSLXDM_L, qllxDTO.getQslxdm())) {
                    qllx = 11;
                } else if (StringUtils.equals(QSLXDM_N, qllxDTO.getQslxdm())) {
                    qllx = 13;
                }
            }
        }
        return qllx;
    }

    /**
     * @param qllxDTO 判断对象
     * @param dyhcxlx 单元号查询类型
     * @return 权利类型
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据房屋不动产单元获取权利类型
     */
    private Integer getQllxFromFwBdcdy(QllxDTO qllxDTO, String dyhcxlx) {
        Integer qllx = null;
        if (StringUtils.contains(QSLXDM_BSXWY, qllxDTO.getQslxdm())) {
            if (StringUtils.equals(QLXZDM_G, qllxDTO.getQlxzdm())) {
                if (StringUtils.equals(Constants.DYHCXLX_TDDZW, dyhcxlx) ||StringUtils.equals(Constants.DYHCXLX_CBZD, dyhcxlx)) {
                    qllx = 4;
                } else if (StringUtils.equals(Constants.DYHCXLX_GZW, dyhcxlx)) {
                    qllx = 24;
                }
            } else if (StringUtils.equals(QLXZDM_J, qllxDTO.getQlxzdm())) {
                if (StringUtils.equals(Constants.DYHCXLX_TDDZW, dyhcxlx) ||StringUtils.equals(Constants.DYHCXLX_CBZD, dyhcxlx)) {
                    qllx = 8;
                } else if (StringUtils.equals(Constants.DYHCXLX_GZW, dyhcxlx)) {
                    qllx = 26;
                }
            }
        } else if (StringUtils.equals(QSLXDM_C, qllxDTO.getQslxdm())) {
            if ((StringUtils.equals(Constants.DYHCXLX_TDDZW, dyhcxlx) ||StringUtils.equals(Constants.DYHCXLX_CBZD, dyhcxlx)) && StringUtils.contains(QLXZDM_GJZ, qllxDTO.getQlxzdm())) {
                qllx = 6;
            } else if (StringUtils.equals(Constants.DYHCXLX_GZW, dyhcxlx) && (StringUtils.equals(QLXZDM_G, qllxDTO.getQlxzdm()) || StringUtils.equals(QLXZDM_J, qllxDTO.getQlxzdm()))) {
                qllx = 25;
            }
        } else if (StringUtils.equals(QSLXDM_G, qllxDTO.getQslxdm())) {
            if (StringUtils.equals(QLXZDM_G, qllxDTO.getQlxzdm()) && StringUtils.equals(Constants.DYHCXLX_GZW, dyhcxlx)) {
                qllx = 18;
            }
        } else if ((StringUtils.equals(QSLXDM_H, qllxDTO.getQslxdm()) || StringUtils.equals(QSLXDM_W, qllxDTO.getQslxdm())) &&
                StringUtils.equals(QLXZDM_G, qllxDTO.getQlxzdm())) {
            qllx = 16;
        }
        return qllx;

    }

}

package cn.gtmap.realestate.register.ui.util;

import cn.gtmap.realestate.common.core.enums.BdclxEnum;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlBdcdyhQO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/1/30
 */
public class TzmUtils {

    public static void getCxByBdclx(BdcSlBdcdyhQO bdcSlBdcdyhQO) {
        BdclxEnum tzmEnum = BdclxEnum.valueOf(getBdclxEnum(bdcSlBdcdyhQO.getBdclx()));
        if (StringUtils.isNotBlank(tzmEnum.getZdtzm())) {
            bdcSlBdcdyhQO.setZdtzm(tzmEnum.getZdtzm());
        }
        if (StringUtils.isNotBlank(tzmEnum.getDzwtzm())) {
            bdcSlBdcdyhQO.setDzwtzm(tzmEnum.getDzwtzm());
        }
        if (StringUtils.isNotBlank(tzmEnum.getFwlx())) {
            bdcSlBdcdyhQO.setBdcdyfwlx(tzmEnum.getFwlx());
        }
        if (tzmEnum.getDyhcxlx() != null) {
            bdcSlBdcdyhQO.setDyhcxlx(tzmEnum.getDyhcxlx());
        }
    }

    public static void getQlxzAndZdtzm(BdcSlBdcdyhQO bdcSlBdcdyhQO) {
        if (StringUtils.isNotBlank(bdcSlBdcdyhQO.getZdtzm())) {
            String[] zdtzmArr = bdcSlBdcdyhQO.getZdtzm().split(CommonConstantUtils.ZF_YW_DH);
            String zdtzm = zdtzmArr[0];
            if (StringUtils.isNotBlank(zdtzm) && zdtzm.length() == 2) {
                bdcSlBdcdyhQO.setQlxzAndZdtzm(bdcSlBdcdyhQO.getZdtzm());
                bdcSlBdcdyhQO.setZdtzm("");

            } else {
                bdcSlBdcdyhQO.setQlxzAndZdtzm("");
            }
        }
    }

    private static String getBdclxEnum(String bdclx) {
        String bdclxEnum = "";
        switch (bdclx) {
            case "1":
                bdclxEnum = "TD";
                break;
            case "2":
                bdclxEnum = "TDFW";
                break;
            case "3":
                bdclxEnum = "LDLM";
                break;
            case "4":
                bdclxEnum = "ZJJZW";
                break;
            case "5":
                bdclxEnum = "HY";
                break;
            case "6":
                bdclxEnum = "HYGZW";
                break;
            case "7":
                bdclxEnum = "QT";
                break;
            case "8":
                bdclxEnum = "TDGZW";
                break;
            case "9":
                bdclxEnum = "TDQT";
                break;
            case "10":
                bdclxEnum = "HYFW";
                break;
            case "11":
                bdclxEnum = "HYSLLM";
                break;
            case "12":
                bdclxEnum = "HYQT";
                break;
            case "13":
                bdclxEnum = "WJMHD";
                break;
            default:
                break;
        }
        return bdclxEnum;
    }

}

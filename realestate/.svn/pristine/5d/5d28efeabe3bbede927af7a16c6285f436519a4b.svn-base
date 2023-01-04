package cn.gtmap.realestate.accept.ui.utils;

import cn.gtmap.realestate.common.core.enums.BdclxEnum;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlBdcdyhQO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/1/30
 * @description 工具类
 */
public class TzmUtils {

    public static boolean getCxByBdclx(BdcSlBdcdyhQO bdcSlBdcdyhQO) {
        //查询条件出现冲突,返回false
        BdclxEnum tzmEnum = BdclxEnum.valueOf(getBdclxEnum(bdcSlBdcdyhQO.getBdclx()));
        if (tzmEnum != null) {
            if(StringUtils.isNotBlank(tzmEnum.getZdtzm())) {
                StringBuilder zdtzmStringBuilder =new StringBuilder();
                if(StringUtils.isBlank(bdcSlBdcdyhQO.getZdtzm())) {
                    bdcSlBdcdyhQO.setZdtzm(tzmEnum.getZdtzm());
                }else{
                    //如果配置的和根据不动产类型获取的宗地特征码均不为空，找出两者共有的部分
                    String[] zdtzmArr = bdcSlBdcdyhQO.getZdtzm().split(CommonConstantUtils.ZF_YW_DH);
                    for(String zdtzm:zdtzmArr){
                        if(zdtzm.length() >1 &&StringUtils.contains(tzmEnum.getZdtzm(),zdtzm.substring(1,2))){
                            if(StringUtils.isBlank(zdtzmStringBuilder)) {
                                zdtzmStringBuilder.append(zdtzm);
                            }else{
                                zdtzmStringBuilder.append(CommonConstantUtils.ZF_YW_DH).append(zdtzm);
                            }
                        }

                    }
                    if(StringUtils.isNotBlank(zdtzmStringBuilder)){
                        bdcSlBdcdyhQO.setZdtzm(zdtzmStringBuilder.toString());

                    }else{
                        return false;
                    }

                }
            }
            if(StringUtils.isNotBlank(tzmEnum.getDzwtzm())) {
                if(StringUtils.isNotBlank(bdcSlBdcdyhQO.getDzwtzm())) {
                    String[] dzwtzm = bdcSlBdcdyhQO.getDzwtzm().split(CommonConstantUtils.ZF_YW_DH);
                    if (!ArrayUtils.contains(dzwtzm, tzmEnum.getDzwtzm())){
                        return false;
                    }
                }
                bdcSlBdcdyhQO.setDzwtzm(tzmEnum.getDzwtzm());
            }
            if(StringUtils.isNotBlank(tzmEnum.getFwlx())) {
                bdcSlBdcdyhQO.setBdcdyfwlx(tzmEnum.getFwlx());
            }
            if(tzmEnum.getDyhcxlx() != null) {
                if(bdcSlBdcdyhQO.getDyhcxlx() != null){
                    if(tzmEnum.getDyhcxlx() ==1 &&bdcSlBdcdyhQO.getDyhcxlx() != 1 &&bdcSlBdcdyhQO.getDyhcxlx() != 4) {
                        return false;
                    }
                    if(tzmEnum.getDyhcxlx() !=1 &&!tzmEnum.getDyhcxlx().equals(bdcSlBdcdyhQO.getDyhcxlx())){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void getQlxzAndZdtzm(BdcSlBdcdyhQO bdcSlBdcdyhQO){
        if(StringUtils.isNotBlank(bdcSlBdcdyhQO.getZdtzm())){
            String[] zdtzmArr =bdcSlBdcdyhQO.getZdtzm().split(CommonConstantUtils.ZF_YW_DH);
            String zdtzm =zdtzmArr[0];
            if(StringUtils.isNotBlank(zdtzm) &&zdtzm.length() ==2){
                bdcSlBdcdyhQO.setQlxzAndZdtzm(bdcSlBdcdyhQO.getZdtzm());
                bdcSlBdcdyhQO.setZdtzm("");

            }else{
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

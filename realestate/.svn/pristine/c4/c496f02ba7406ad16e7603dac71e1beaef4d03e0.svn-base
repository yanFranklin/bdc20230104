package cn.gtmap.realestate.building.util;

import cn.gtmap.realestate.building.core.bo.FwbmBO;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-21
 * @description 房屋编码工具类
 */
public class FwbmUtils {


    // 使用权类型 枚举值
    private final static Map<String,String> SYQLX_MAP = new HashMap<>();

    // 宗地特征码 枚举值
    private final static Map<String,String> ZDTZM_MAP = new HashMap<>();

    static {
        // 使用权类型
        SYQLX_MAP.put("G","0");
        SYQLX_MAP.put("J","1");
        SYQLX_MAP.put("Z","2");
        // 宗地特征码
        ZDTZM_MAP.put("A","0");
        ZDTZM_MAP.put("B","1");
        ZDTZM_MAP.put("C","2");
        ZDTZM_MAP.put("D","3");
        ZDTZM_MAP.put("E","4");
        ZDTZM_MAP.put("F","5");
        ZDTZM_MAP.put("S","6");
        ZDTZM_MAP.put("X","7");
        ZDTZM_MAP.put("W","8");
        ZDTZM_MAP.put("Y","9");
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwbmBO
     * @return void
     * @description  拆分 BDCDYH
     */
    public static void split(FwbmBO fwbmBO){
        String bdcdyh = fwbmBO.getBdcdyh();
        if(StringUtils.isNotBlank(bdcdyh)){
            char[] chars = bdcdyh.toCharArray();
            String prefixOne = "";
            StringBuilder prefixTwe = new StringBuilder("");
            String syqlx = "";
            String zdtzm = "";
            StringBuilder middleFive = new StringBuilder("");
            StringBuilder zrzh = new StringBuilder("");
            StringBuilder fxlh = new StringBuilder("");
            for(int i = 0 ; i < chars.length ; i++){
                if(i == 0){
                    prefixOne = chars[i]+"";
                }
                // prefixTwe
                if(i < 12){
                    prefixTwe.append(chars[i]);
                }
                // syqlx
                if(i == 12){
                    syqlx = chars[i]+"";
                }
                // zdtzm
                if(i == 13){
                    zdtzm = chars[i]+"";
                }
                if( i >= 14 && i < 19){
                    middleFive.append(chars[i]);
                }
                // zrzh
                if(i >= 21 && i < 24){
                    zrzh.append(chars[i]);
                }
                // fxlh
                if(i >= 25){
                    fxlh.append(chars[i]);
                }
            }
            fwbmBO.setPrefixOne(prefixOne);
            fwbmBO.setPrefixTwe(prefixTwe.toString());
            fwbmBO.setSyqlx(syqlx);
            fwbmBO.setZdtzm(zdtzm);
            fwbmBO.setMiddleFive(middleFive.toString());
            fwbmBO.setZrzh(zrzh.toString());
            fwbmBO.setFxlh(fxlh.toString());
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bo
     * @return void
     * @description  替换 TZM
     */
    public static void replaceTzm(FwbmBO bo){
        // 替换使用权类型
        bo.setSyqlxCode(SYQLX_MAP.get(bo.getSyqlx()));
        // 替换 特征码类型
        bo.setZdtzmCode(ZDTZM_MAP.get(bo.getZdtzm()));
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bo
     * @return void
     * @description 获取 F列
     */
    public static void setFCode(FwbmBO bo){
        bo.setfCode(bo.getPrefixTwe()
                + bo.getSyqlxCode() + bo.getZdtzmCode()
                + bo.getMiddleFive()
                + bo.getZrzh() + bo.getFxlh());
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bo
     * @return void
     * @description 获取 校验码
     */
    public static void setJym(FwbmBO bo){
        char[] chars = bo.getfCode().toCharArray();
        // 第一次循环计算 传10 计算G列
        int res = 10;
        for(int i = 0 ; i < chars.length ; i++){
            res = getAE(Integer.parseInt(chars[i]+""),res);
        }
        int jym = res > 1 ? 11 - res : 1 - res;
        bo.setJym(jym + "");
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param code
     * @param lastRes
     * @return void
     * @description 获取 AE列
     */
    public static int getAE(int code,int lastRes){

        // 截取后的编码 + 上次计算后的值
        int res = code + lastRes;
        // 除以 10 取余
        int temMol = res % 10;
        // 乘以 2
        int mulTwo = temMol * 2;

        // 除以 11 取余
        int eleMol = mulTwo % 11;

        return eleMol;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bo
     * @return void
     * @description 获取 AE列
     */
    public static void setFwbm(FwbmBO bo){
        bo.setFwbm(bo.getfCode() + bo.getJym());
    }

}

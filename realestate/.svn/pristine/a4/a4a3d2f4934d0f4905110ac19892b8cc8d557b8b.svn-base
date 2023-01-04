package cn.gtmap.realestate.exchange.util.sjrpt;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SmUtil;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022-09-28
 * @description 合肥省金融平台加解密工具类
 */
public class Sm3Util {

    /**
     * 使用SM3算法对数据进行摘要
     *
     * @param data 原始数据
     * @return 摘要，HEX编码
     */
    public static String digest(String data) {
        return HexUtil.encodeHexStr(SmUtil.sm3().digest(data));
    }

}

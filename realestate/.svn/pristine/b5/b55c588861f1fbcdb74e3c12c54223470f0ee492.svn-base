package cn.gtmap.realestate.accept.service;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0, 2022/8/21
 * @description
 */
public interface PosService {
    /**
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 获取消费交易信息
     */
    Object getXfjyxx(String gzlslid, String qlrlb);

    /**
     * @param
     * @param gzlslid
     * @param qlrlb
     * @return java.lang.Object
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/22 15:28
     * @description 当日撤销交易
     **/
    Object drcxjy(String gzlslid, String qlrlb);

    /**
     * @param
     * @param gzlslid
     * @param qlrlb
     * @return java.lang.Object
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/22 15:37
     * @description 退货交易
     **/
    Object thjy(String gzlslid, String qlrlb);

    /**
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description  保存交易信息
     */
    void saveJyxx(String output, String gzlslid, String qlrlb);

    /**
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 重打印交易
     */
    Object rePrintJy(String proid, String qlrlb);

    /**
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 广开聚合支付被扫交易
     */
    Object gkjhZfJy(String gzlslid, String qlrlb, String fkm);

    /**
     * @param
     * @param gzlslid
     * @param qlrlb
     * @return java.lang.Object
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/22 15:43
     * @description 广开聚合退货交易
     **/
    Object gkjhthjy(String gzlslid, String qlrlb);
}

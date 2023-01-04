package cn.gtmap.realestate.etl.service;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0  2020/05/12
 * @description 房产交易数据转换服务
 */
public interface FcjyDataConvertService {
    /**
     * @description 转换房产交易合同备案信息到不动产登记
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/28 9:43
     * @return void
     */
    void convertFcjyHtbaxxAndImoprtBdcDj();

}

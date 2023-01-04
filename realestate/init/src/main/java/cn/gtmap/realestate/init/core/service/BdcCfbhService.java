package cn.gtmap.realestate.init.core.service;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/30
 * @description 查封编号服务
 */
public interface BdcCfbhService {

    /**
      * @param qlid 查封权利ID
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 生成查封编号
      */
    String generateCfbh(String qlid);
}

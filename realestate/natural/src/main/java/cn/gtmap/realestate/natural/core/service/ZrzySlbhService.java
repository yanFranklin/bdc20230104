package cn.gtmap.realestate.natural.core.service;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/11/1
 * @description 受理编号服务
 */
public interface ZrzySlbhService {

    /**
      * @param ywlx 业务类型
      * @param zzsjfw 自增时间范围
      * @param zzxlh 自增序列号
      * @param prebh 编号前缀
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 生成受理编号
      */
    String generateSlbh(String ywlx, String zzsjfw,Integer zzxlh,String prebh);
}

package cn.gtmap.realestate.building.service.lpb;

/**
 * @program: realestate
 * @description: 地籍调查表打印服务
 * @author: <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @create: 2022-10-13
 **/
public interface BdcPrintDjdcbService {
    /**
     * @param bdcdyh
     * @return xml
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 地籍调查表打印服务
     */
    String printDjdcb(String dylx, String qjgldm, String bdcdyh);

}

package cn.gtmap.realestate.building.service.bg;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-31
 * @description 房屋类变更 服务  BGBH 变更编号不传则不记录变更记录表
 */
public interface FwBgService<T> {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgbh
     * @param yDO
     * @param bgDo
     * @return T
     * @description 基本信息变更操作
     */
    T jbxxBg(String bgbh,String bglx,T yDO,T bgDo);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgbh
     * @param yDOList
     * @param bgDO
     * @return T
     * @description 合并变更
     */
    T hbBg(String bgbh, List<T> yDOList, T bgDO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgbh
     * @param yDO
     * @param bgDOList
     * @return List<T>
     * @description 拆分变更
     */
    List<T> cfBg(String bgbh,T yDO, List<T> bgDOList);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgbh
     * @param yDO
     * @param bglx
     * @return void
     * @description 灭失变更
     */
    void msBg(String bgbh,String bglx,T yDO);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgbh
     * @param bglx
     * @param yDO
     * @param newDO
     * @return void
     * @description 单条变更记录保存
     */
    void saveSingleHsbgjlb(String bgbh,String bglx,T yDO,T newDO);
}

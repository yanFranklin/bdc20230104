package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxFphhDO;
import cn.gtmap.realestate.common.core.qo.register.BdcGdxxFphhQO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
 * @version 1.0, 2021/10/30
 * @description 档案分配盒号
 */
public interface BdcGdxxFphhMapper {
    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @param map
     * @return List<BdcGdxxDO>
     * @description 根据map查询归档信息集合
     */
    List<BdcGdxxFphhDO> listBdcGdxx(HashMap map);

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @param map
     * @return List<BdcGdxxDO>
     * @description 根据档案号查询是否已经分配查询出已经分配的
     */
   List<BdcGdxxFphhDO> listBdcGdxxFphhsffp(HashMap map);

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @return List<Stirng>
     * @description 查询所有盒号
     */
    List<String> listhh();

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @return List<Stirng>
     * @description 查询乡镇所有盒号
     */
    List<Integer> listXzhh(@Param("xzdm") String xzdm, @Param("nf") String nf);

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGdxxFphhDO 档案号信息
     * @return String 档案信息记录主键ID
     * @description 获取指定档案号范围对应乡镇、年份已有最大盒号（目录号） [这里先不考虑并发重号]
     */
    Integer getCurrentMlhMaxSxh(BdcGdxxFphhDO bdcGdxxFphhDO);

    List<BdcGdxxFphhDO> getBdcGdxxFphhDOList(BdcGdxxFphhQO bdcGdxxFphhQO);

    String getMaxLsh(BdcGdxxFphhQO bdcGdxxFphhQO);
}

package cn.gtmap.realestate.certificate.core.mapper;


/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description  不动产电子证照加注件信息
 */

import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzJzjxxDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;


@Mapper
public interface BdcDzzzJzjxxMapper {


    /**
     * @param zzid
     * @return
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 通过zsid查询证照加注件信息
     */
    List<BdcDzzzJzjxxDO> queryJzjxxByZzid(String zzid);

    BdcDzzzJzjxxDO queryJzjxxByJzjId(String jzjid);


    /**
     * @param bdcDzzzJzjxxDO
     * @return
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 插入加注件信息
     */
    int insertBdcDzzzJzjxx(BdcDzzzJzjxxDO bdcDzzzJzjxxDO) throws DataAccessException;

    /**
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 根据zsid删除证照加注件息
     */
    int deleteBdcDzzzJzjxxByZzid(String zzid);

}

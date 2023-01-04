package cn.gtmap.realestate.etl.core.mapper.exchange;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxDO;
import cn.gtmap.realestate.common.core.dto.etl.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.com">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/10/19 16:51
 */
@Mapper
public interface BdcXmMapper {

    /**
     * @param xm   权利人名称
     * @param zjhm 权利人证件号
     * @param cqzh 不动产权证号
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description
     */
    List<XscqDTO> listXscq(@Param("xm") String xm, @Param("zjhm") String zjhm, @Param("cqzh") String cqzh);

    /**
     * @param xm   权利人名称
     * @param zjhm 权利人证件号
     * @param cqzh 不动产权证号
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description
     */
    List<XscqDTO> listXscqWithTd(@Param("xm") String xm, @Param("zjhm") String zjhm, @Param("cqzh") String cqzh);

    /**
     * @param htbh 合同编号
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description
     */
    List<BzjdDTO> listBzjd(@Param("htbh") String htbh);

    /**
     * @param htbh 合同编号匹配土地
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @version 2021/07/21,1.0
     * @description
     */
    List<BzjdDTO> listBzjdWithTd(@Param("htbh") String htbh);

    BdcGdxxDO getBdcGdxxByGzlslid(Map map);

    /**
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @version 2020/06/23,1.0
     * @description
     */
    List<CfxxDTO> listCfxx(@Param("cqdjbh") String cqdjbh, @Param("bdcqzh") String bdcqzh);

    /**
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @version 2020/06/23,1.0
     * @description
     */
    List<DyxxDTO> listDyxx(@Param("cqdjbh") String cqdjbh, @Param("bdcqzh") String bdcqzh);

    /**
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @version 2020/06/23,1.0
     * @description
     */
    List<CfmjDTO> queryCfmj(Map<String, Object> param);

    /**
     * 查询是否存在遗失公告
     *
     * @param ajzt,qszt,bdcdyh,djxl
     * @return
     * @Date 2021/10/21
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<BdcXmDO> ggxm(@Param("qszt") Integer qszt, @Param("ajzt") Integer ajzt,
                       @Param("bdcdyh") String bdcdyh, @Param("djxlList") List<String> djxlList);

}

package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.qo.inquiry.yancheng.BdcYthYrcfQO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.ZttGyQlrDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/12/9 23:45
 * @description
 */
@Component
public interface BdcYthyrcfMapper {

    List<BdcXmDO> getBdcXmByBdcYthYrcfQO(BdcYthYrcfQO bdcYthYrcfQO);

    List<String> filterBdcdyh(@Param(value = "bdcdyhList") List<String> bdcdyhList);

    List<String> filterTdBdcdyh(@Param(value = "bdcdyhList") List<String> bdcdyhList);

    List<BdcQlrDO> getBdcQlrByBdcdyh(String bdcdyh);

    List<BdcQlrDO> getBdcQlrByBdcdyhAndXmid(@Param(value = "bdcdyh") String bdcdyh, @Param(value = "xmid") String xmid);

    List<BdcQlrDO> getBdcQlrByBdcdyhList(@Param(value = "bdcdyhList") List<String> bdcdyhList);

    List<ZttGyQlrDTO> queryZttGyQlrListForYcYth(Map map);
    /**
      *     根据xmid查询第三权利人信息
      * @param xmid
      * @return BdcQlrDO
      * @Date 2021/9/24
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
         */
    List<BdcQlrDO> getBdcDsQlrByXmid(@Param(value = "xmid") String xmid);

}

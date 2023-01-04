package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcGgDO;
import cn.gtmap.realestate.common.core.domain.BdcGggxDO;
import cn.gtmap.realestate.common.core.domain.BdcGgywsjDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcGgDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @program: realestate
 * @description: 不动产公告Service
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 14:52
 **/
public interface BdcGgService {

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询不动产公告
     * @date : 2021/7/21 14:52
     */
    List<BdcGgDO> listBdcGg(String gzlslid);

    /**
     * @param ggid
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @description 查询不动产公告关系
     * @date : 2022/4/27 14:52
     */
    List<BdcGggxDO> listBdcGggxByGgid(String ggid);

    /**
     * @param xmid
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @description 查询不动产公告关系
     * @date : 2022/4/27 14:52
     */
    List<BdcGggxDO> listBdcGggxByXmid(String xmid);

    /**
     * @param ggid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据公告id查公告信息
     * @date : 2022/3/3 9:05
     */
    BdcGgDO queryBdcGg(String ggid);

    /**
     * @param ywsjid
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @description 根据项目id查公告业务数据信息
     * @date : 2022/3/3 9:05
     */
    List<BdcGgywsjDO> queryBdcGgywsj(String ywsjid);

    /**
     * @param ggid
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 根据公告id查公告业务数据信息
     * @date : 2022/3/3 9:05
     */
    List<BdcGgywsjDO> queryBdcGgywsjByGgid(String ggid);

    /**
     * @param bdcGgDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产公告
     * @date : 2021/7/21 14:54
     */
    BdcGgDO insertBdcGg(BdcGgDO bdcGgDO);

    /**
     * @param xmidList,ggid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产公告
     * @date : 2021/7/21 14:54
     */
    int insertBdcGggx(List<String> xmidList,String ggid);

    /**
     * @param bdcGgDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产公告
     * @date : 2021/7/21 14:54
     */
    Object insertBdcGgYwsj(@RequestBody BdcGgDTO bdcGgDTO,boolean sfgl);

    /**
     * @param ggids 公告Id
     * @param ggzt 公告zhuangtai
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 批量更新公告状态
     * @date : 2021/7/22 14:54
     */
    int batchUpdatebdcggzt(List<String> ggids, String ggzt);

    /**
     * 根据公告ID查询公告关联的流程信息
     * @param ggid 公告ID
     * @return 流程相关信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcXmDO> queryBdcGgGlBdcXmxx(String ggid);

    /**
     * 根据公告ID查询公告关联的流程信息
     * @param ggids 公告ID
     * @return 流程相关信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    int deleteBdcGgxx(List<String> ggids);
}

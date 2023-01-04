package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.register.BdcBdcdyHbjlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcLsgxXmDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.register.BdcCqQO;
import cn.gtmap.realestate.common.core.qo.register.BdcLsgxQO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcBdcdyVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDyawqdVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/2/21
 * @description 不动产单元Mapper接口定义
 */
public interface BdcBdcdyMapper {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询出已被查封的义务人信息
     */
    int listBdcCfYwr(List<BdcQlrDO> list);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询出已被异议的义务人信息
     */
    int listBdcYyYwr(List<BdcQlrDO> list);

    /**
     * @param map 查询参数
     * @return 不动产单元列表
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 和分页查询同一个逻辑，用于查询出所有的数据
     */
    List<BdcBdcdyVO> listBdcdyByPage(Map<String, Object> map);

    /**
     * 查询当前项目上一手的产权
     *
     * @param bdcLsgxQO 历史关系查询对象
     * @return 不动产单元列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcLsgxXmDTO> listBdcdyLsgxLastCq(BdcLsgxQO bdcLsgxQO);

    /**
     * @param map 查询参数
     * @return 不动产单元列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 分页查询当前流程的不动产单元（去重），用于查询出所有的不动产单元号和坐落
     */
    List<BdcBdcdyVO> listDistinctBdcdyByPage(Map<String, Object> map);

    /**
     * @param map 查询参数
     * @return 不动产单元列表
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 和分页查询同一个逻辑，用于查询出所有的数据
     */
    List<BdcDyawqdVO> listDyawqdByPage(Map<String, Object> map);

    /**
     * @param map 查询参数
     * @return 不动产单元列表
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 和分页查询同一个逻辑，用于查询出所有的数据
     */
    List<BdcDyawqdVO> listYgDyawqdByPage(Map<String, Object> map);

    /**
     * @param map 查询参数
     * @return 不动产单元列表
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 和分页查询同一个逻辑，用于查询出所有的数据
     */
    List<BdcDyawqdVO> listYxmDyawqdByPage(Map<String, Object> map);

    /**
     * @param map 查询参数
     * @return 抵押物清单
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 和分页查询同一个逻辑，用于查询部分注销，抵押变更原项目的抵押物清单
     */
    List<BdcDyawqdVO> queryBgYxmDyawqdByPage(Map<String, Object> map);


    /**
     * @param map 查询参数
     * @return 不动产单元列表
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 和分页查询同一个逻辑，用于查询出所有的数据
     */
    List<BdcDyawqdVO> listYgYdyawqdByPage(Map<String, Object> map);

    /**
     * 项目来源
     * @param map
     * @return
     */
    List<Map> queryXmly(Map<String, Object> map);

    /**
     * 产权大证
     * @param map
     * @return
     */
    List<BdcLsgxXmDTO> queryCqdz(Map<String, Object> map);

    /**
     * @param bdcXmQO 抵押项目
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取抵押权对应上一手项目的房地产权信息
     */
    List<BdcFdcqDO> listBdcFdcqByDya(BdcXmQO bdcXmQO);

    /**
     * @param param 参数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询指定房地产权项目是否有合并成的下一手新记录
     */
    List<BdcBdcdyHbjlDTO> queryBdcdyHbjl(Map<String, List> param);

    /**
     * @param bdcdyh
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/7
     * @description 根据不动产单元号查询预告
     */
    List listBdcYgBybdcdyh(@Param("bdcdyh") String bdcdyh);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  list 不动产单元号集合
     * @return {List} 房屋性质
     * @description  查询抵押物清单列表单元的房屋信息
     */
    List<BdcDyawqdVO> listBdcDyawqdFwxx(List<String> list);

    /**
     * @param bdcCqQO 产权查询
     * @return 房地产权项目信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询房地产权项目信息
     */
    List<Map> listBdcFdcqXmxx(BdcCqQO bdcCqQO);

    /**
     * @param bdcCqQO 产权查询
     * @return 房地产权项目信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询建设用地使用权项目信息
     */
    List<Map> listBdcJsydsyqXmxx(BdcCqQO bdcCqQO);

    /**
     *
     * @param bdcLsgxQO
     * @return 登记历史关系
     * @description 查询不动产查封登记历史
     */
    List<BdcLsgxXmDTO> listBdccfdjls(BdcLsgxQO bdcLsgxQO);
}

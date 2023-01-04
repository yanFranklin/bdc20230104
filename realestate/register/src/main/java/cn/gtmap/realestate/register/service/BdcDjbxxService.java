package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.register.BdcDjbQlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDjbxxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQlDjMlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQlQtsxDTO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDjbQlMlVO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/8
 * @description 不动产登记簿信息业务服务接口
 */
public interface BdcDjbxxService {
    /**
     * @param zdzhh 宗地宗海号
     * @return List<BdcQlDJMlDTO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产登记目录
     */
    List<BdcQlDjMlDTO> listBdcQlDjMl(String zdzhh);

    /**
     * @param zdzhh 宗地宗海号
     * @return bdcdyh 不动产单元号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元在登记簿的权利信息，包括返回在登记簿的排序
     */
    BdcQlDjMlDTO indexBdcdyhQlDjMl(String zdzhh, String bdcdyh);

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param zdzhh 宗地宗海号
     * @return BdcBdcdjbDO 不动产登记簿
     * @description 获取不动产登记簿信息
     */
    BdcBdcdjbDO queryBdcBdcdjb(String zdzhh);

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param bdcdyh 宗地不动产单元号
     * @return BdcBdcdjbZdjbxxDO 不动产宗地基本信息
     * @description 获取不动产宗地基本信息
     */
    BdcBdcdjbZdjbxxDO queryBdcBdcdjbZdjbxx(String bdcdyh);

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param zddm 宗地代码
     * @return List<BdcBdcdjbZdjbxxZdbhqkDO> 不动产宗地变化情况列表
     * @description 查询不动产宗地变化情况
     */
    List<BdcBdcdjbZdjbxxZdbhqkDO> listBdcBdcdjbZdjbxxZdbhqk(String zddm);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcdyh 不动产单元号
     * @return  {BdcBdcdjbZhjbxxDO} 宗海基本信息
     * @description 查询不动产登记簿宗海基本信息
     */
    BdcBdcdjbZhjbxxDO queryBdcBdcdjbZhjbxx(String bdcdyh);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcdyh 不动产单元号
     * @return  {List} 宗海基本信息用海状况
     * @description 查询宗海基本信息用海状况
     */
    List<BdcBdcdjbZhjbxxYhzkDO> listBdcBdcdjbZhjbxxYhzk(String bdcdyh);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcdyh 不动产单元号
     * @return  {List} 宗海基本信息用海用岛坐标
     * @description 查询不动产登记簿宗海基本信息中的用海用岛坐标
     */
    List<BdcBdcdjbZhjbxxYhydzbDO> listBdcBdcdjbZhjbxxYhydzb(String bdcdyh);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcdyh 不动产单元号
     * @return  {List} 宗海基本信息宗海变化情况
     * @description 查询不动产登记簿宗海基本信息中的宗海变化情况
     */
    List<BdcBdcdjbZhjbxxZhbhqkDO> listBdcBdcdjbZhjbxxZhbhqk(String bdcdyh);

    /**
     * @param bdcdyh 不动产单元号
     * @param qsztList
     * @return BdcQlQtsxDTO 不动产权利及其他事项DTO对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产权利及其他事项信息
     */
    BdcQlQtsxDTO queryBdcQlQtSx(String bdcdyh, List<Integer> qsztList);

    /**
     * @param bdcdyh 不动产单元号
     * @param qsztList
     * @return BdcDjbQlDTO 登记簿权利信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元的登记簿权利信息
     */
    BdcDjbQlDTO queryBdcDjbQl(String bdcdyh, List<Integer> qsztList);

    /**
     * @param zdzhh 宗地宗海号
     * @param bdcdyh
     * @param onlyQlfm 是否只生成权利封面url
     * @return List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成登记簿目录树
     */
    List generateDjbMls(String zdzhh, String bdcdyh, Boolean onlyQlfm);

    /**
     * @param bdcdyh 不动产单元号
     * @param onlyQlfm
     * @return 每个单元号在登记簿中的权利目录树
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 每个单元号在登记簿中的权利目录树
     */
    BdcDjbQlMlVO generateDyhQlMl(String bdcdyh, Boolean onlyQlfm);

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 判断并组织不动产单元的权利目录对象
     */
    List<Map<String, String>> getBdcDjbQlVO(List<Map<String, String>> bdcQlList, BdcDjbQlDTO bdcDjbQlDTO, String bdcdyh);

    /**
     * @param dbxxQO 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description (宗地宗海等修改了单元的信息需要更新)更新登记簿信息
     */
    void updateDjbxx(DbxxQO dbxxQO);

    /**
     * @param zdzhh 宗地宗海号
     * @return BdcBdcdjbZdjbxxDO 不动产宗地基本信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据宗地宗海号查询宗地宗海基本信息
     */
    BdcBdcdjbZdjbxxDO queryBdcBdcdjbZdjbxxByZdzhh(String zdzhh);


    /**
     * @param bdcBdcdjbZhjbxxDO 不动产登记簿宗海基本信息
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 更新宗海不动产登记簿宗海基本信息
     */
    int updateBdcBdcdjbZdjbxx(BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxxDO);

    /**
     * @param bdcBdcdjbZhjbxxYhzkDO 宗海基本信息用海状况
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 更新宗海基本信息用海状况
     */
    int updateBdcBdcdjbZhjbxxYhzk(BdcBdcdjbZhjbxxYhzkDO bdcBdcdjbZhjbxxYhzkDO);
}

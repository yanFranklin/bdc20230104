package cn.gtmap.realestate.init.core.mapper;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.etl.BzjdDTO;
import cn.gtmap.realestate.common.core.dto.etl.XscqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx.CourtYwxxcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmAndFbDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYxmQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/14
 * @description 不动产项目mapper
 */
public interface BdcXmMapper {


    /**
     * 删除流程实例关系表
     * @param gzlslid 工作流实例ID
     * @return
     */
    void deleteCshFwkgSl(String gzlslid);


    /**
     * 查询不动产项目部分信息
     * @param gzlslid 工作流实例ID
     * @param slbh 受理编号
     * @param onlyOne 只查询一条
     * @return
     */
    List<BdcXmDTO>  listXmBfxx(@Param("gzlslid") String gzlslid, @Param("slbh") String slbh,@Param("onlyOne")boolean onlyOne);


    /**
     * 查询不动产项目信息
     * @param bdcXmQO
     * @return
     */
    List<BdcXmDO>  listBdcXm(BdcXmQO bdcXmQO);

    /**
     * 根据办结日期 查询 BDCXM（目前只查了BDCDYH）
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.List<java.lang.String>
     * @description  根据办结日期 查询 BDCXM（目前只查了BDCDYH）
     */
    List<BdcXmDO> listBdcdyhByBjrq(Map<String,Object> paramMap);

    /**
     * 查询不动产项目初始化实例信息
     * @param gzlslid 工作流实例ID
     * @return
     */
    List<BdcCshFwkgSlDO> listBdCshSl(String gzlslid);


    /**
     * 根据工作流实例Id和权利类型获取登记原因
     * @param gzlslid 工作流实例ID
     * @param qllx 权利类型
     * @return
     */
    String  queryDjyy(@Param("gzlslid") String gzlslid, @Param("qllx") Integer qllx);

    /**
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 通过工作流实例ID获取预告预抵押登记原因
     */
    String queryZxYgYdyDjyy(@Param("gzlslid")  String gzlslid,@Param("ygdjzl") List<Integer> ygdjzl);

    /**
     * 批量更新项目
     * @param map
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新项目
     */
    int updateBatchBdcXm(Map map);


    /**
     *  批量更新项目备注
     * @param map  bz和gzlslid或者xmid必填
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 批量更新项目备注
     */
    int updateBatchXmBz(Map map);

    /**
     * 根据工作流实例ID获取房屋用途字典项
     * @param gzlslid 工作流实例ID
     * @return List<BdcZdFwytDO>
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取房屋用途字典项
     */
    List<BdcZdFwytDO> queryZdFwytByGzlslid(String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据当前工作流实例ID查询原项目
     */
    List<BdcXmDO> listYxmByGzlslid(Map map);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    List<BdcXmDO> listYxmByYxmQO(BdcYxmQO bdcYxmQO);

    /**
     * @param bdcXmQO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/9/8 15:45
     */

    List<BdcXmAndFbDTO> listXmAndFb(BdcXmQO bdcXmQO);

    /**
     *
     * @param xm 权利人名称
     * @param zjhm 权利人证件号
     * @param cqzh 不动产权证号
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description
     * @return
     */
    List<XscqDTO> listXscq(@Param("xm") String xm, @Param("zjhm") String zjhm, @Param("cqzh") String cqzh);

    /**
     *
     * @param xm 权利人名称
     * @param zjhm 权利人证件号
     * @param htbh 合同编号
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description
     * @return
     */
    List<BzjdDTO> listBzjd(@Param("xm") String xm, @Param("zjhm") String zjhm, @Param("htbh") String htbh);

    /**
     * @param bdcQlrQO 权利人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人名称加证件号找到项目表的经营权信息
     * @date : 2020/11/5 9:26
     */
    List<BdcXmDTO> listCbjyqXm(BdcQlrQO bdcQlrQO);

    /**
     * 根据项目ID集合获取项目数据
     * @param xmids  项目ID集合
     * @return 不动产项目数据集合
     */
    List<BdcXmDO> listBdcXmByXmids(@Param("xmids") List<String> xmids);


    /**
     * 查询不动产项目初始化实例信息
     * @param gzlslid 工作流实例ID
     * @return
     */
    List<BdcCshFwkgSlDO> listBdCshSlSfzf(@Param("gzlslid") String gzlslid,@Param("sfzf") Integer sfzf);

    /**
     * 更新zfxzspbh字段值
     * @param xmList
     */
    void updateZfxzspbh(@Param("list") List<BdcXmDO> xmList);

    List<BdcXmDO> listCourtXscq(CourtYwxxcxRequestDTO courtYwxxcxRequestDTO);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询现势产权
     */
    List<BdcXmDO> listXscqXm(@Param("bdcdyhList") List<String> bdcdyhList, @Param("bdclx") Integer bdclx);

    /**
     * 查询登记系统存在的所有权籍管理代码
     *
     * @return {List} 权籍管理代码集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<String> listQjgldm();

    /**
     * 查询不动产项目初始化实例信息
     *
     * @param gzlslid 工作流实例ID
     * @return
     */
    List<BdcCshFwkgSlDO> listBdCshSlById(@Param("gzlslid") String gzlslid, @Param("xmid") String xmid);


    /**
     * 根据不动产权证号查询项目信息
     * @param bdcqzh 不动产权证号
     * @return BdcXmDO 不动产项目信息
     */
    List<BdcXmDO> queryXmByZsBdcqzh(@Param("bdcqzh") String bdcqzh);

    /**
     * 根据工作流实例ID获取同审批系统业务号的其他项目信息
     * @param gzlslid 工作流实例ID
     * @return BdcXmDO 不动产项目信息
     */
    List<BdcXmDO> listOtherXmWithSameSpxtywh(@Param("gzlslid") String gzlslid);

    /**
     * @param xmid
     * @return daywh
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据xmid取daywh值（盐城BDC_XM表独有字段）
     */
    String queryDaywh(@Param("xmid") String xmid);

    /**
     * 模糊查询项目数据
     * @param bdcXmQO 查询参数
     * @return {List} 项目数据
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcXmDO> listBdcXmSlbhs(BdcXmQO bdcXmQO);

    /**
     * @description 查询项目信息中同一工作流实例ID不动产单元号数量（【常州】提示查看清册和附表）
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/6/21 20:21
     * @param gzlslid 工作流实例ID
     * @return int 同一工作流实例ID不动产单元号数量
     */
    int countBdcByGzlslidGroupBdcdyh(@Param("gzlslid") String gzlslid);

    /**
     * 根据受理编号查询一条项目信息（权利人、坐落）
     * @param slbh 受理编号
     * @return 项目数据
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    BdcXmDO queryOneBdcXmDOBySlbh(@Param("slbh")String slbh);
}

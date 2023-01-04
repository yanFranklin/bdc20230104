package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshFwkgDataDTO;
import cn.gtmap.realestate.common.core.dto.etl.BzjdDTO;
import cn.gtmap.realestate.common.core.dto.etl.XscqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx.CourtYwxxcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmAndFbDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmLsgxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmZhxxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYxmQO;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/10/31
 * @description 不动产项目
 */
public interface BdcXmService {

    /**
     * 根据日期 查询 当天办结的 BDCDYH
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcXmQO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @description 根据日期 查询 当天办结的 BDCDYH
     */
    List<BdcXmDO> listBdcXmByRq(BdcXmQO bdcXmQO);

    /**
     * 通过传入的不动产项目参数查询不动产项目信息
     * @param bdcXmDO 不动产项目信息
     * @return
     */
    List<BdcXmDO> listBdcXm(BdcXmDO bdcXmDO);

    /**
     * 通过传入的不动产项目参数查询不动产项目信息
     * @param bdcXmQO 项目表查询参数对象
     * @return
     */
    List<BdcXmDO> listBdcXm(BdcXmQO bdcXmQO);

    /**
     * 查询不动产项目部分信息
     * @param gzlslid 工作流实例ID
     * @param slbh 工作流实例ID
     * @return
     */
    List<BdcXmDTO>  listXmBfxx(String gzlslid, String slbh);

    /**
     * 查询一条不动产项目部分信息
     * @param gzlslid 工作流实例ID
     * @param slbh 工作流实例ID
     * @return
     */
    BdcXmDTO getXmBfxxOnlyOne(String gzlslid, String slbh);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcXmDO 不动产项目信息
     *@description 更新不动产项目信息
     * @return 更新数量
     */
    int updateBdcXm(BdcXmDO bdcXmDO);

    /**
     * 通过项目id获得不动产项目信息
     *
     * @param xmid 不动产项目id
     * @return
     */
    BdcXmDO queryBdcXmByPrimaryKey(String xmid);

    /**
     *通过项目id获得不动产服务开关实例信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param xmid 不动产项目id
     *@return BdcCshFwkgSlDO
     *@description
     */
    BdcCshFwkgSlDO queryCshFwkgSl(String xmid);

    /**
     *  通过项目ID更新不动产服务开关实例信息
     * @param bdcCshFwkgSlDO
     * @return 更新状态 1为更新成功，0为更新失败
     */
    int updateCshFwkgSl(BdcCshFwkgSlDO bdcCshFwkgSlDO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [xmids, sfhz]
     * @return int
     * @description 批量保存是否换证
     */
    int batchUpdateCshFwkgSlSfhz(List<String> xmids, String sfhz);

    /**
     * 通过工作流实例ID查询不动产初始化开关服务信息
     * @param gzlslid 工作流实例ID
     * @param bdcdyh  不动产单元号
     * @return
     */
    List<BdcSlCshFwkgDataDTO> queryCshFwkgSlByGzlslid(String gzlslid, String bdcdyh);

    /**
     * 查询不动产项目初始化流实例数据
     * @param gzlslid 工作流实例ID
     * @return
     */
    List<BdcCshFwkgSlDO>  listBdCshSl(@NotBlank(message = "工作流实例ID不能为空")String gzlslid);


    /**
     * 通过项目id获得与其组合的所有项目信息
     * @param xmid 不动产项目id
     * @param sftlc 是否同流程
     * @return List<BdcXmZhxxDTO>
     */
    List<BdcXmZhxxDTO> queryBdcXmZhxx(String xmid,boolean sftlc);



    /**
     * 通过传入的工作流实例ID参数查询不动产项目信息
     *
     * @param gzlslid 工作流实例ID
     * @return
     */
    List<BdcXmDO> listBdcXm(String gzlslid);


    /**
     *通过项目ID获得原项目ID
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param xmid 不动产项目ID
     *@return 原项目ID
     *@description
     */
    String queryYxmid(String xmid);


    /**
     * 判定该流程的类型
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcXmList 不动产项目集合
     *@description 判定该流程的类型
     * @return 1:普通  2：组合  3：批量  4:批量组合
     */
    int bdcXmLx(@NotEmpty(message ="项目集合不能为空" ) List<BdcXmDTO> bdcXmList);

    /**
     * 判定该流程的类型
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcXmList 不动产项目集合
     *@description 判定该流程的类型
     * @return 1:普通  2：组合  3：批量  4:批量组合
     */
    int bdcXmLxByAllXm(@NotEmpty(message ="项目集合不能为空" ) List<BdcXmDO> bdcXmList);

    /**
     * 判定该流程的类型
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param gzlslid 工作流实例ID
     *@description 判定该流程的类型
     * @return 1:普通  2：组合  3：批量  4:批量组合
     */
    int bdcXmLx(@NotBlank(message = "工作流实例ID不能为空")String gzlslid);

    /**
     * 删除流程实例关系表
     * @param gzlslid 工作流实例ID
     * @return
     */
    void deleteCshFwkgSl(@NotBlank(message = "工作流实例ID不能为空")String gzlslid);

    /**
     * 根据工作流实例Id和权利类型获取登记原因
     * @param gzlslid 工作流实例ID
     * @param qllx 权利类型
     * @return
     */
    String  queryDjyy(@NotBlank(message = "工作流实例ID不能为空")String gzlslid,Integer qllx);

    /**
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 通过工作流实例ID获取预告预抵押登记原因
     */
    String queryZxYgYdyDjyy(String gzlslid,List<Integer> ygdjzl);

    /**
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新项目
     */
    int updateBatchBdcXm(BdcDjxxUpdateQO bdcDjxxUpdateQO);

    /**
     * @param bz
     * @param djxl
     * @param gzlslid
     * @param xmid
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 批量更新项目备注
     */
    int updateBatchXmBz(@NotBlank(message = "备注不能为空") String bz,String djxl,String gzlslid,String xmid);


    /**
     * 更新证书相关的项目的部分权利其他状况
     * @param bdcXmDO 项目信息
     * @return int 更新数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新证书相关的项目的部分权利其他状况
     */
    int updateBdcZsXmBfqlqtzk(BdcXmDO bdcXmDO,Boolean plgx);

    /**
     * 根据json内容更新证书项目的权利其他状况
     * @param json 更新内容json
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据json内容更新证书项目的权利其他状况
     */
    int updateBdcZsXmBfqlqtzk(String json, Boolean plgx);

    /**
     * 查询当前证书项目的涉及的项目集合
     * @param xmid
     * @return List<String> 项目集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询当前证书项目的涉及的项目集合
     */
    List<String> queryZsxmList(String xmid);

    /**
     * 根据工作流实例ID获取房屋用途字典项
     * @param gzlslid 工作流实例ID
     * @return List<BdcZdFwytDO>
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取房屋用途字典项
     */
    List<BdcZdFwytDO> queryZdFwytByGzlslid(String gzlslid);

    /**
     * 生成项目关于证书数量的备注
     * @param gzlslid 项目ID信息
     * @return 备注信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成项目关于证书数量的备注
     */
    String generateXmBzYzhNum(String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据当前工作流实例ID查询原项目
     */
    List<BdcXmDO> listYxmByGzlslid(BdcXmLsgxDO bdcXmLsgxDO, String gzlslid,String djxl);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据查询对象查询原项目
     */
    List<BdcXmDO> listYxmByYxmQO(BdcYxmQO bdcYxmQO);

    /**
     * @param xmid 当前项目ID
     * @param bdcXmDOList 产权项目列表
     * @param getOneXm 是否获取一条
     * @return 产权项目列表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 嵌套获取上手产权项目
     */
    List<BdcXmDO> listPrevCqXm(String xmid,List<BdcXmDO> bdcXmDOList,boolean getOneXm);

    /**
     * @param xmidList 当前项目ID集合
     * @param bdcXmDOList 产权项目列表
     * @param getOneXm 每个项目是否获取一条
     * @return 产权项目列表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 嵌套获取上手产权项目
     */
    List<BdcXmDO> listPrevCqXmPl(List<String> xmidList,List<BdcXmDO> bdcXmDOList,boolean getOneXm);
    /**
     * 获取所有需要更新的不动产单元对象信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param list
     * @param bdcdyh
     * @param bhdysd 是否包含(处理)单元锁定 0 不包含，其余均包含
     *@return InitResultDTO
     *@description
     */
    InitResultDTO updateAllBdcdyXx(List<BdcXmDO> list, String bdcdyh,String qjgldm,Boolean gxbdcdyfwlx,Integer bhdysd);

    /**
     * @param bdcXmQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询项目和附表信息
     * @date : 2020/9/8 15:44
     */
    List<BdcXmAndFbDTO> listXmAndFb(BdcXmQO bdcXmQO);

    /**
     *
     * @param xm 权利人名称
     * @param zjhm 权利人证件号
     * @param cqzh 不动产权证号
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据条件查询产权
     * @return
     */
    List<XscqDTO> listXscq(String xm, String zjhm, String cqzh);

    /**
     *
     * @param xm 权利人名称
     * @param zjhm 权利人证件号
     * @param htbh 合同编号
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据条件查询办证进度
     * @return
     */
    List<BzjdDTO> listBzjd(String xm, String zjhm, String htbh);

    /**
     * @param bdcQlrQO 权利人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人名称加证件号找到项目表的经营权信息
     * @date : 2020/11/5 9:26
     */
    List<BdcXmDTO> listCbjyqXm(BdcQlrQO bdcQlrQO);

    /**
     * 根据xmids批量查询项目信息
     * @param xmids 项目ID集合
     * @return 项目数据集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcXmDO> listBdcXmByXmids(List<String> xmids);


    /**
     * 查询不动产项目初始化流实例数据
     * @param gzlslid 工作流实例ID
     * @return
     */
    List<BdcCshFwkgSlDO>  listBdCshSlSfzf(@NotBlank(message = "工作流实例ID不能为空")String gzlslid, Integer sfzf);

    /**
     * 根据产权证号查询现势产权
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param courtYwxxcxRequestDTO
     * @return
     */
    List<BdcXmDO> listCourtXscq(CourtYwxxcxRequestDTO courtYwxxcxRequestDTO);

    /**
     * @param bdcXmDOList  包含不动产单元号（土地或房屋）和不动产类型
     * @return 产权项目
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据不动产单元查询现势产权项目
     */
    List<BdcXmDO> listXscqXm(List<BdcXmDO> bdcXmDOList);

    /**
     * 查询登记系统存在的所有权籍管理代码
     *
     * @return {List} 权籍管理代码集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<String> listQjgldm();

    /**
     * 查询不动产项目初始化流实例数据
     *
     * @param gzlslid 工作流实例ID
     * @return
     */
    List<BdcCshFwkgSlDO> listBdCshSlById(@NotBlank(message = "工作流实例ID不能为空") String gzlslid, String xmid);

    /**
     * @param bdcCshFwkgSlDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增初始化房屋开关实例表
     * @date : 2021/9/30 14:22
     */
    int insertBdcCshFwkgSl(BdcCshFwkgSlDO bdcCshFwkgSlDO);


    /**
     * 根据xmid获取历史关系
     *
     * @param xmid 不动产权证号
     * @return List<Map>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public BdcXmLsgxDTO queryBdcxmLsgxByXmid(String xmid);

    /**
     * 根据不动产权证号查询项目信息
     * @param bdcqzh 不动产权证号
     * @return BdcXmDO 不动产项目信息
     */
    List<BdcXmDO> queryXmByZsBdcqzh(String bdcqzh);

    /**
     * 根据工作流实例ID获取同审批系统业务号的其他项目信息
     * @param gzlslid 工作流实例ID
     * @return BdcXmDO 不动产项目信息
     */
    List<BdcXmDO> listOtherXmWithSameSpxtywh(String gzlslid);

    /**
     * @param xmid
     * @return daywh
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据xmid取daywh值（盐城BDC_XM表独有字段）
     */
    String queryDaywh(String xmid);

    /**
     * 模糊查询项目受理编号数据
     * @param bdcXmQO 查询参数
     * @return {List} 项目数据
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcXmDO> listBdcXmSlbhs(BdcXmQO bdcXmQO);

    /**
     * @description 查询项目信息中同一工作流实例ID不动产单元号数量（【常州】提示查看清册和附表）
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/6/21 20:28
     * @param gzlslid 工作流实例ID
     * @return int 同一工作流实例ID不动产单元号数量
     */
    int countBdcByGzlslidGroupBdcdyh(String gzlslid);

    /**
     * 根据受理编号查询一条项目信息（权利人、坐落）
     * @param slbh 受理编号
     * @return 项目数据
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    BdcXmDO queryOneBdcXmDOBySlbh(String slbh);

    /**
     *@author <a href="mailto:wangfangfang@gtmap.cn">wangfangfang</a>
     * @param xmid 项目ID
     * @param qszt 权属状态过滤
     *@return 项目ID集合
     *@description 根据不动产项目id获取相同证书的全部项目ID集合
     */
    List<String> listYbzXmByXmid(String xmid,Integer qszt);
}

package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDyaqQlrDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcLsgxXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcdySumDTO;
import cn.gtmap.realestate.common.core.dto.register.TdSyqJssjDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDyaQo;
import cn.gtmap.realestate.common.core.qo.register.BdcBdcdyQO;
import cn.gtmap.realestate.common.core.qo.register.BdcCqQO;
import cn.gtmap.realestate.common.core.qo.register.BdcLsgxQO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcBdcdyVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDyawqdVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/2/21
 * @description 不动产单元服务处理Service接口定义
 */
public interface BdcBdcdyService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable  分页参数
     * @param bdcLsgxQO 查询参数
     * @return {Page} 项目列表
     * @description  查询不动产单元号对应的项目登记历史关系
     */
    Page<BdcLsgxXmDTO> listBdcdyLsgx(Pageable pageable, BdcLsgxQO bdcLsgxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @return {Boolean} 存在查封：true，不存在查封：false
     * @description 判断不动产单元号对应的权利人是否存在查封
     */
    Boolean isBdcdyhQlrCf(String bdcdyh,String qjgldm);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @return {Boolean} 存在异议：true，不存在异议：false
     * @description 判断不动产单元号对应的权利人是否存在异议
     */
    Boolean isBdcdyhQlrYy(String bdcdyh,String qjgldm);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @return {List} 锁定则返回提示信息；未锁定则返回空
     * @description 验证所在宗地不动产单元是否锁定
     */
    List<String> isZdBdcdySd(String bdcdyh,String qjgldm);

    /**
     * @param bdcPrintDTO 打印对象
     * @return String 打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取打印xml
     */
    String bdcdyPrintXml(BdcPrintDTO bdcPrintDTO);

    /**
     * @param bdcPrintDTO 打印对象
     * @return String 打印xml批量
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 获取打印xml
     */
    String bdcdyPrintXmlPl(BdcPrintDTO bdcPrintDTO);

    /**
     * @param map 查询参数
     * @return 不动产单元列表
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 和分页查询同一个逻辑，用于查询出所有的数据
     */
    List<BdcBdcdyVO> queryBdcdyList(Map<String, Object> map);

    /**
     * @param map 查询参数
     * @return 抵押物清单
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 和分页查询同一个逻辑，用于查询原项目的抵押物清单
     */
    List<BdcDyawqdVO> queryYxmDyawqd(Map<String, Object> map);

    /**
     * @param map 查询参数
     * @return 抵押物清单
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 和分页查询同一个逻辑，用于查询部分注销，抵押变更原项目的抵押物清单
     */
    List<BdcDyawqdVO> queryBgYxmDyawqd(Map<String, Object> map);

    /**
     * @param map 查询参数
     * @return 抵押物清单
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 和分页查询同一个逻辑，用于查询抵押物清单
     */
    List<BdcDyawqdVO> queryDyawqd(Map<String, Object> map);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @return {Boolean} 验证通过：true ；不通过：false
     * @description 验证当前项目权利人权利比例之和是否为1
     */
    Boolean checkQlrQlbl(String xmid);

    /**
     * 查询项目来源
     * @param xmid
     * @return
     */
    List queryXmly(String xmid);

    /**
     * 查询产权大证
     * @param xmid
     * @return
     */
    List<BdcLsgxXmDTO> queryCqdz(String xmid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 面积和
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 计算房地产权面积之和
     */
    BdcdySumDTO calculatedFdcqMj(String gzlslid,Boolean sfyql,String djxl);

    /**
     * @param gzlslid 工作流实例ID
     * @return 面积和
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 计算抵押权面积之和
     */
    BdcdySumDTO calculatedDyaqMj(String gzlslid, Boolean sfyql, List<String> xmidList);

    /**
     * @param gzlslid 工作流实例ID
     * @return 面积和
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 计算抵押权面积之和
     */
    List<BdcdySumDTO> calculatedDyaqMjGyBdclx(String gzlslid,Boolean sfscql);

    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @param bdcXmQO 查询参数
     * @return List<BdcDyaqDO> 抵押信息
     * @description  查询当前流程的原抵押信息
     */
    List<BdcDyaqDO> queryYdyaxx(BdcXmQO bdcXmQO);

    /**
     * @param gzlslid   工作流实例ID
     * @param bdcDyaqDO 抵押信息
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新原抵押信息的抵押注销申请人
     */
    int saveYdyaxxZxsqr(String gzlslid, BdcDyaqDO bdcDyaqDO);

    /**
     * 查询当前项目上一手的产权
     *
     * @param bdcLsgxQO 历史关系查询对象
     * @return 不动产单元列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcLsgxXmDTO> listBdcdyLsgxLastCq(BdcLsgxQO bdcLsgxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXmQO 抵押项目
     * @return  抵押土地面积
     * @description  查询抵押土地面积：如果是产权，取所有不动产单元 产权的 fttdmj + dytdmj 之和
     */
    Double getDyaTdmj(BdcXmQO bdcXmQO);

    /**
     * @param bdcBdcdyQO 单元信息查询对象
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据房屋用途统计面积
     */
    List<Map> getSumMjByGhyt(BdcBdcdyQO bdcBdcdyQO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询预告
     */
    List listBdcYgByXmid(String xmid);

    /**
     * @param bdcXmQO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据单元号查询是否办理过抵押权
     * @date : 2020/8/3 14:03
     */
    List<BdcDyaqDO> listExistDyaq(BdcXmQO bdcXmQO);

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/7
     * @description 根据不动产单元号查询预告
     */
    List listBdcYgByBdcdyh(String bdcdyh);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcdyhList 不动产单元号集合
     * @return {List} 房屋性质
     * @description  查询抵押物清单列表单元的房屋信息
     */
    List<BdcDyawqdVO> listBdcDyawqdFwxx(List<String> bdcdyhList);

    /**
     * @param gzlslid 工作流实例ID
     * @return 面积和
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 计算建设用地使用权面积之和
     */
    BdcdySumDTO calculatedJsydsyqMj(String gzlslid, Boolean sfyql, String djxl);

    /**
      * @param bdcCqQO 产权查询
      * @return 房地产权项目信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 查询房地产权项目信息,同一个bdcdyh 按登记时间倒序排序，取最新一条
     */
    List<Map> listBdcFdcqXmxx(BdcCqQO bdcCqQO);

    /**
     * @param bdcCqQO 产权查询
     * @return 房地产权项目信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询建设用地使用权项目信息,同一个bdcdyh 按登记时间倒序排序，取最新一条
     */
    List<Map> listBdcJsydsyqXmxx(BdcCqQO bdcCqQO);

    /**
     * @param bdcDyaQo
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人信息查询抵押权数据
     * @date : 2021/9/14 9:06
     */
    List<BdcDyaqQlrDTO> listBdcDyqByQlrxx(BdcDyaQo bdcDyaQo);

    List<BdcLsgxXmDTO> listBdccfdjls(BdcLsgxQO parseObject);

    /**
     * @param qxdm,zdtzm,dzwtzm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 生成虚拟单元号
     * @date : 2021/12/30 15:43
     */
    String createXndyh(String qxdm, String zdtzm, String dzwtzm);

    /**
     * @param djh 地籍号
     * @return 面积和
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据宗地地籍号获取宗地上所有户室，计算房地产权面积之和
     */
    BdcdySumDTO computeFdcqMjByDjh(String djh);

    /**
     * @param gzlslid
     * @param zdmc    字段名称
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询房地产权使用权结束时间去重后的数据
     * @date : 2022/9/7 19:18
     */
    TdSyqJssjDTO listDistinctTdsyjssj(String gzlslid);

    /**
     * @param bdcPrintDTO
     * @return pdf base64文件
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成抵押物清单pdf
     */
    String getDywqdPdf(BdcPrintDTO bdcPrintDTO);
}

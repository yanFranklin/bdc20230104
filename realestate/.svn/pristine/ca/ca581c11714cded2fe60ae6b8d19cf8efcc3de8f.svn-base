package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXmGdxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcGwcDeleteQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.register.BdcBdcdyQO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlXmVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理项目数据服务
 */
public interface BdcSlXmService {
    /**
     * @param xmid 项目ID
     * @return 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID获取不动产受理项目
     */
    BdcSlXmDO queryBdcSlXmByXmid(String xmid);

    /**
     * @param jbxxid 基本信息ID
     * @return 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID获取不动产受理项目
     */
    List<BdcSlXmDO> listBdcSlXmByJbxxid(String jbxxid,String orderBy);

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理项目
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据工作流实例ID获取所有不动产受理项目
     */
    List<BdcSlXmDO> listBdcSlXmByGzlslid(String gzlslid);

    /**
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * @description 根据工作流实例ID更新权属状态
     */
    void updateQszt(String gzlslId) throws Exception;

    /**
     * @param xmidList 项目ID集合
     * @return 不动产受理项目
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据工作流实例ID获取所有不动产受理项目
     */
    List<BdcSlXmDO> listBdcSlXmByXmids(List<String> xmidList);

    /**
     * @param bdcSlXmDO 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理项目
     */
    BdcSlXmDO insertBdcSlXm(BdcSlXmDO bdcSlXmDO);

    /**
     * @param bdcSlXmDO 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理项目
     */
    Integer updateBdcSlXm(BdcSlXmDO bdcSlXmDO);

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID删除不动产受理项目
     */
    Integer deleteBdcSlXmByXmid(String xmid);

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID删除不动产受理项目
     */
    Integer deleteBdcSlXmByJbxxid(String jbxxid);

    /**
     * @param jbxxid 基本信息ID
     * @return 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息获取不动产受理项目集合
     */
    List<BdcSlYwxxDTO> listBdcSLXmDTOByJbxxid(String jbxxid);

    /**
     * @param map 查询参数
     * @return 不动产受理项目
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取不动产受理项目集合
     */
    List<BdcSlYwxxDTO> listBdcSLXmDTO(Map map);

    /**
     * @param pageable 分页对象
     * @param map      查询条件
     * @return 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息获取不动产受理项目集合
     */
    Page<BdcSlYwxxDTO> listBdcSLXmDTOByPage(Pageable pageable, Map map);

    /**
     * @param json 受理项目集合
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新受理项目
     */
    Integer updateBdcSlXmList(String json);

    /**
     * @param fwhsIndexs 户室主键集合，多个用英文逗号隔开
     * @param jbxxid 基本信息id
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据户室主键和基本信息id删除已选项目信息
     */
    Integer deleteYxFwhs(String fwhsIndexs,String jbxxid);

    /**
     * @param fwhsIndexList 户室主键集合
     * @param jbxxid 基本信息ID
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据户室主键和基本信息id删除已选项目信息
     */
    Integer deleteYxFwhs(List<String> fwhsIndexList,String jbxxid);

    /**
     * @param bdcSlXmQO 不动产受理项目查询
     * @return 受理项目集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据查询条件获取受理项目集合
     */
    List<BdcSlXmDO> listBdcSlXm(BdcSlXmQO bdcSlXmQO);

    /**
     * @param zsxh 证书序号
     * @param jbxxid 基本信息ID
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新证书序号
     */
    Integer batchUpdateBdcSlXmZsxh(String zsxh, String jbxxid,List<String> xmidList);

    /**
     * @param map 查询参数
     * @return 不动产受理项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取购物车所有数据包含yxmid
     */
    List<BdcSlYwxxDTO> listGwcData(Map map);

    /**
     * 获取不动产单元号是否重复配置
     * 选择不动产单元页面
     *
     * @param xmid xmid
     * @return 是否去除重复数据
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    boolean bdcdyhIsRepeat(String xmid);


    /**
     * 根据历史关系找到项目表数据
     *
     * @param bdcSlXmQO
     * @return 受理项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    List<BdcSlXmDO> queryBdcSlxmByLsgx(BdcSlXmQO bdcSlXmQO);

    /**
     * @param xmid     项目ID
     * @return 受理信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取受理信息
     */
    BdcSlXmDTO queryBdcSlxx(String xmid);

    /**
     * @param cnqx,slsj
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取领证日期
     * @date : 2020/1/15 10:02
     */
    Date getLzrq(Integer cnqx,Date slsj) throws Exception;

    /**
     * @param sfzlcsh 是否为增量初始化
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理需要初始化的数据---判断交易信息和受理项目信息是否相同条数，相同带入权利人，不相同不带入权利人
     */
    List<BdcSlXmDTO> dealCshData(List<BdcSlXmDTO> bdcSlXmDTOList, BdcSlJbxxDO bdcSlJbxxDO,boolean sfzlcsh);

    /**
     * @param xmidList 项目ID集合
     * @param type 1:购物车删除 2.受理全部删除 其余默认全部删除
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除受理信息
     */
    void deleteBdcSlxx(List<String> xmidList,String type);

    /**
     * @param jbxxid 基本信息ID
     * @return 项目ID集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据基本信息ID获取项目ID集合
     */
    List<String> getXmidListByJbxxid(String jbxxid);

    /**
     * @param bdcSlXmQO 受理项目查询参数
     * @return 购物车已选项目数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询购物车已选项目数据(包含历史关系)
     */
    List<BdcSlXmVO> listGwcSelectDataWithLsgx(BdcSlXmQO bdcSlXmQO);

    /**
     * @param jbxxid 基本信息ID
     * @return 发证状态信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 通过基本信息ID查询受理发证状态信息
     */
    List<BdcSlCshFwkgDataDTO> querySlFzztByJbxxid(String jbxxid);

    /**
     * 更新受理项目中的 证书类型与是否发证
     * @param jbxxid 基本信息ID
     * @param bdcSlXmDOList  不动产受理项目数据集合
     * @return 更新发证状态的单元号列表
     */
    List<String> updateSlxmFzztPl(String jbxxid, List<BdcSlXmDO> bdcSlXmDOList, String gzldyid);

    /**
     * @param bdcGwcDeleteQO 购物车删除QO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 购物车页面删除已选项目
     */
    void deleteYxxm(BdcGwcDeleteQO bdcGwcDeleteQO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询当前流程房屋信息
     * @date : 2022/8/18 16:28
     */
    Page<BdcSlFwxxDTO> listBdcFwxxByPage(Pageable pageable, BdcBdcdyQO bdcBdcdyQO);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询购物车数据库的所有规划用途
     * @date : 2022/9/13 11:17
     */
    List<String> listGwcGhyt(String jbxxid);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询购物车所有面积
     * @date : 2022/9/13 11:19
     */
    String queryGwcDzwmj(String jbxxid, String djxl);

    /**
     * @param bdcSlXmQO 查询参数
     * @return 不动产受理项目
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 根据受理编号获取所有不动产受理项目
     */
    List<BdcSlXmDO> queryBdcSlXmDOList(BdcSlXmQO bdcSlXmQO);

    List<BdcXmGdxxDTO> listBdcGdxxHf(HashMap<String, Object> param);

    /**
     * 批量更新档案归属地信息
     * @param dagsd
     * @param jbxxidList
     * @param xmidList
     * @return
     */
    Integer batchUpdateBdcSlXmDagsd(String dagsd,List<String> jbxxidList,List<String> xmidList);

    /**
     * @param bdcSlxxInitDTOList 受理信息
     * @param czlx insert:插入 update：更新
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存受理信息
     */
    void saveBdcSlxx(List<BdcSlxxInitDTO> bdcSlxxInitDTOList, String czlx) throws IllegalAccessException;

    /**
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return 更新数量
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 批量更新受理项目
     */
    Integer updateBatchBdcSlXm(BdcDjxxUpdateQO bdcDjxxUpdateQO);


}

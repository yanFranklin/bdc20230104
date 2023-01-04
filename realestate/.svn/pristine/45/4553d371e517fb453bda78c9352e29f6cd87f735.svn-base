package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxFphhDO;
import cn.gtmap.realestate.common.core.qo.register.BdcGdxxDahQO;
import cn.gtmap.realestate.common.core.qo.register.BdcGdxxFphhQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
 * @version 1.0, 2021/10/30
 * @description 档案分配盒号
 */
public interface BdcGdxxFphhService {


    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @return
     * @description 分配盒号，查询结果中全部未分配的，进行分配操作
     */
    String fphhMethod(BdcGdxxFphhQO bdcGdxxFphhQO);

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @return
     * @description 查询是否分配盒号，数据返回不为空，则给提示语
     */
    List<BdcGdxxFphhDO> sffphh(BdcGdxxFphhQO bdcGdxxFphhQO);

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @return
     * @description 页面下拉框需要展示盒号
     */
    List<Integer> listhh(String xzdm,String xzmc, String nf);

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @param bdcGdxxFphhDO
     * @return
     * @description 新增归档分配盒子信息
     */
    int insertBdcGdxxFphh(BdcGdxxFphhDO bdcGdxxFphhDO);

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @param bdcGdxxFphhDO
     * @return
     * @description 根据xmid更新归档信息,存在则更新不存在则插入
     */
    int updateBdcGdxxFphh(BdcGdxxFphhDO bdcGdxxFphhDO);

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @param bdcGdxxFphhDOList
     * @return
     * @description 页面批量更新盒号
     */
    void batchUpdateGdxxFphh(List<BdcGdxxFphhDO> bdcGdxxFphhDOList);

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @param gdxxid
     * @return daId
     * @description 根据xmid查询信息
     */
    BdcGdxxFphhDO getDaIdById(String gdxxid);

    /**
     * @author <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @param bdcGdxxFphhQO
     * @return List<BdcGdxxFphhDO>
     * @description 查询归档信息集合
     */
    List<BdcGdxxFphhDO> listBdcGdxxFphh(BdcGdxxFphhQO bdcGdxxFphhQO);



    /**
     * @author <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @param gdxxid
     * @return BdcGdxxFphhDO
     * @description 根据主键查询归档信息
     */
    BdcGdxxFphhDO queryBdcGdxxFphh(String gdxxid);

    /**
     * @author <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @param pageable
     * @return BdcGdxxFphhDO
     * @description 分页获取归档分盒信息 全参数
     */
    Page<BdcGdxxFphhDO> listGdxxTable(Pageable pageable, BdcGdxxDahQO bdcGdxxDahQO);

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGdxxFphhDO 档案信息
     * @return String 档案信息记录主键ID
     * @description 保存档案号
     */
    String saveDah(BdcGdxxFphhDO bdcGdxxFphhDO);

    /**
     * @param bdcGdxxFphhQO
     * @return
     * @author <a href ="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description
     */
    List<BdcGdxxFphhDO> getBdcGdxxFphhDOList(BdcGdxxFphhQO bdcGdxxFphhQO);

    /**
     *
     * @param bdcGdxxFphhQO
     * @return
     * @description 获取当日最大流水号
     */
    String getMaxLsh(BdcGdxxFphhQO bdcGdxxFphhQO);
}

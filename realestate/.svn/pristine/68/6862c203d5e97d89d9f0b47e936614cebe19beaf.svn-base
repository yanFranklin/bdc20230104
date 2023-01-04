package cn.gtmap.realestate.natural.service;


import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyZsDO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyZsQO;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyYzhVO;
import cn.gtmap.realestate.natural.core.bo.ZrzyZscCqzhBO;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author <a href ="mailto:wangyinghao@gtmap.cn">wangyh</a>
 * @version 1.3, 2021/11/5
 * @description 证书业务类
 */
public interface ZrzyZsService {

    /**
     *
     * @param zrzyZsQO
     * @return
     */
    public List<ZrzyZsDO> listZryzZsByZsid(ZrzyZsQO zrzyZsQO);

    /**
     * @param zrzyZsQO 证书查询对象
     * @return List<BdcZsDO> 不动产权证list
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书列表
     */
    List<ZrzyZsDO> listZryzZs(ZrzyZsQO zrzyZsQO);

    /**
     * 初始化不动产证书
     *
     * @param cshFwkgSlList 每个项目的初始化实例
     * @param zsyl          是否证书预览
     * @param zssl          证书数量
     * @param plybz         是否是批量一本证逻辑，按项目处理的要传,gzlslid的会自己判断
     * @param sjbl          数据补录 （不生成权利其他状况和附记）
     * @return List<BdcZsDO>
     */
    ZrzyZsDO initZrzyqzs(String gzlslid,
                               boolean zsyl,
                               boolean zssl,
                               Boolean plybz,
                               boolean sjbl) throws Exception;

    /**
     * @param bdcYzhVO 证书印制号模板
     * @return {int} 操作数据记录数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成证书印制号
     */
    int generateBdcZsyzh(ZrzyYzhVO bdcYzhVO);

    /**
     * @param zrzyZscCqzhBO 证号查询实体
     * @return {Integer} 库中当前最大顺序号
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取数据库中当前最大顺序号
     */
    int queryMaxSxh(ZrzyZscCqzhBO zrzyZscCqzhBO);

    /**
     * @param xmid 项目id
     * @return List<ZrzyZsDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书列表
     */
    List<ZrzyZsDO> queryZrzyZsByXmid(String xmid);

    /**
     * @param ZrzyZsDO
     * @return
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 修改证书信息
     */
    int updateZrzyZs(ZrzyZsDO zrzyZsDO);

    /**
     * @param zrzyZscCqzhBO 证号查询实体
     * @return {Integer}  证号数量
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询指定初始顺序号到最大顺序号之间顺序号
     */
    LinkedHashSet<Integer> querySxh(ZrzyZscCqzhBO zrzyZscCqzhBO);

    /**
     * 删除证书
     * @param deleteList
     */
    public void deleteZryzZs(List<ZrzyZsDO> deleteList);

    /**
     * 初始化证书数据
     * @param xmid
     * @param zrzyZsDO
     */
    void initZs(String gzlslid,ZrzyZsDO zrzyZsDO);
}

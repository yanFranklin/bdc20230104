package cn.gtmap.realestate.config.service;

import cn.gtmap.realestate.common.core.domain.BdcZdSsjGxDO;
import cn.gtmap.realestate.common.core.qo.config.BdcZdSsjGxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BdcZdSsjgxService {

    /**
     * 查询BdcZdSsjGxDO，bdcZdSsjGxQO为空默认查全部激活状态的数据
     * @param bdcZdSsjGxQO
     * @return
     */
    List<BdcZdSsjGxDO> listSsjgx(BdcZdSsjGxQO bdcZdSsjGxQO);

    /**
     * 通过子目录代码获取BdcZdSsjGxDO
     * @param zmldm
     * @return
     */
    BdcZdSsjGxDO getSsjgxByZmldm(String zmldm);

    /**
     * 通过父目录代码获取BdcZdSsjGxDO
     * @param fmldms
     * @return
     */
    List<BdcZdSsjGxDO> getSsjgxByFmldms(String fmldms);

    /**
     * @param bdcZdSsjGxDO 查询条件
     * @return 分页结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询共享接口字典
     */
    Page<BdcZdSsjGxDO> listBdcZdSsjGxByPage(Pageable pageable,BdcZdSsjGxDO bdcZdSsjGxDO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据子目录代码删除共享接口字典
     */
    void deleteBdcZdSsjGxByZmldm(String zmldm);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存共享接口字典
     */
    void saveBdcZdSsjgx(BdcZdSsjGxDO bdcZdSsjGxDO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询省市级共享字典数据
     */
    BdcZdSsjGxDO queryBdcZdSsjGx(String id);

}

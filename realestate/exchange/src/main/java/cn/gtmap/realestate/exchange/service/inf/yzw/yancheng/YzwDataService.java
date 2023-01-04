package cn.gtmap.realestate.exchange.service.inf.yzw.yancheng;

import cn.gtmap.realestate.exchange.core.domain.yzw.yancheng.TBmCasebaseinfoDO;
import cn.gtmap.realestate.exchange.core.dto.yzw.YzwJgxxDTO;
import cn.gtmap.realestate.exchange.core.vo.YzwYzsbCshxxVO;
import cn.gtmap.realestate.exchange.core.vo.YzwYzsbQsgcxxVO;
import cn.gtmap.realestate.exchange.core.vo.YzwYzsbSjcsVO;
import cn.gtmap.realestate.exchange.core.vo.YzwYzsbTjwtsjgVO;
import cn.gtmap.realestate.exchange.core.vo.YzwYzsbXmcqwbjVO;

import java.util.Date;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/23
 * @description
 */
public interface YzwDataService {

    /**
     * @param yzwbh 一张网编号
     * @return 办件基本信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据一张网编号获取办件基本信息
     */
    TBmCasebaseinfoDO queryTBmCasebaseinfo(String yzwbh);

    /**
     * @param yzwbh 一张网编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 修改受理时间
     */
    void editSlsj(String yzwbh,Date slsj);

    /**
     * @param yzwbh 一张网编号
     * @return 受理时间
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取受理时间
     */
    YzwYzsbSjcsVO getSlsj(String yzwbh);

    /**
     * @param yzwbh 一张网编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 修改办结时间
     */
    void editBjsj(String yzwbh,Date bjsj);

    /**
     * @param yzwbh 一张网编号
     * @return 办结信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化生成办结信息
     */
    YzwYzsbXmcqwbjVO initBjxx(String yzwbh);

    /**
     * @param yzwJgxxDTO 结果信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存结果信息
     */
     void saveYzwJgxxDTO(YzwJgxxDTO yzwJgxxDTO);

    /**
     * @param yzwbh 一张网编号
     * @return 退件结果信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化生成退件结果信息
     */
    YzwYzsbTjwtsjgVO initTjjgxx(String yzwbh);

    /**
     * @param yzwbh 一张网编号
     * @return 过程信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化生成过程信息
     */
    YzwYzsbQsgcxxVO initGcxx(String yzwbh);

    /**
     * @param yzwbh 一张网编号
     * @param zjh 证件号
     * @param zjzl 证件种类
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证保存申请人证件信息
     */
    void checkAndSaveSqrZjxx(String yzwbh,String zjzl,String zjh);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取一张网验证失败推送信息
     */
    YzwYzsbCshxxVO getYzwYzsbTsxx(String yzwbh,String tsxxid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  保存一张网验证失败推送修改信息
     */
    void checkAndSaveYzwYzsbTsxgxx(YzwYzsbCshxxVO yzwYzsbCshxxVO);


}

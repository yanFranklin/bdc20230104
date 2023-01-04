package cn.gtmap.realestate.exchange.service.inf.qj;


import cn.gtmap.realestate.exchange.core.domain.exchange.*;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-02-24
 * @description 权籍数据服务
 */
public interface BuildingService {

    /**
     * 上报查询户室层信息
     */
    List<KttFwCDO> queryKttFwCList(String bdcdyh, String qjgldm);

    /**
     * 上报查询自然幢信息
     */
    List<Map> queryKttFwZrzList(String bdcdyh, String qjgldm);

    /**
     * 上报查询户室信息
     */
    List<KttFwHDO> queryKttFwHList(String bdcdyh, boolean sfdz,  String qjgldm);

    /**
     * 上报查询宗地宗海信息
     */
    List<KttGyJzdDO> queryKttGyJzdList(String bdcdyh,  String qjgldm);

    /**
     * 上报查询界址线信息
     */
    List<KttGyJzxDO> queryKttGyJzxList(String bdcdyh,  String qjgldm);

    /**
     * 上报查询逻辑幢信息
     */
    List<KttFwLjzDO> queryKttFwLjzList(String bdcdyh,  String qjgldm);

    /**
     * 上报查询宗海空间信息
     */
    List<ZhKDO> queryZhkList(String bdcdyh, String qjgldm);


}

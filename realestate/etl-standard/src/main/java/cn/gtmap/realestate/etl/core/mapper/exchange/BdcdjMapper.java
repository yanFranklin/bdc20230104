package cn.gtmap.realestate.etl.core.mapper.exchange;

import cn.gtmap.realestate.etl.core.domian.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 需要单独查询登记业务的方法
 *
 * @author sly
 */
@Component
public interface BdcdjMapper {


    /**
     * sly 查询登记库证书信息，组织成YttBdcqzsDo格式
     *
     * @param xmid
     * @return
     */
    List<YttBdcqzsDo> queryYttBdcqzs(String xmid);

    /**
     * sly 查询登记库证名信息，GtBdcqzmDo
     *
     * @param xmid
     * @return
     */
    List<GtBdcqzmDo> queryGtBdcqzms(String xmid);

    /**
     * sly 查询产权信息，GtCqxxDo
     *
     * @param xmid
     * @return
     */
    List<GtCqxxDo> queryGtCqxxs(String xmid);

    /**
     * sly 查询抵押信息，GtDyaqFwDo
     *
     * @param xmid
     * @return
     */
    List<GtDyaqFwDo> queryGtDyaqFws(String xmid);

    /**
     * sly 查询抵押信息，queryGtDyaqTds
     *
     * @param xmid
     * @return
     */
    List<GtDyaqTdDo> queryGtDyaqTds(String xmid);

    /**
     * sly 查询查封信息，GtCfFwDo
     *
     * @param xmid
     * @return
     */
    List<GtCfFwDo> queryGtCfFws(String xmid);

    /**
     * sly 查询查封信息，GtCfFwDo
     *
     * @param xmid
     * @return
     */
    List<GtCfTdDo> queryGtCfTds(String xmid);

    /**
     * 获取 getDsjjCfFwId
     *
     * @return
     */
    Integer getDsjjCfFwId();
}


package cn.gtmap.realestate.natural.resource.core.mapper;

import cn.gtmap.realestate.common.core.domain.naturalresource.*;
import cn.gtmap.realestate.common.core.dto.naturalresource.JbzkDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface JbzkMapper {
    /**
     * 查询详细信息
     *
     * @param zrzydjdyh
     * @return
     */
    public JbzkDTO queryJbzkByZrzydjdyh(@Param("zrzydjdyh") String zrzydjdyh);

    /**
     * 查询公共管制关联信息属性结构描
     *
     * @param zrzydjdyh
     * @return
     */
    public List<GggzglxxDO> gggzglxxQuery(@Param("zrzydjdyh") String zrzydjdyh);

    /**
     * 查询不动产权利关联信息属性结构
     *
     * @param zrzydjdyh
     * @return
     */
    public List<BdcqlglxxDO> bdcqlglxxQuery(@Param("zrzydjdyh") String zrzydjdyh);

    /**
     * 查询矿业权关联信息属性结构
     *
     * @param zrzydjdyh
     * @return
     */
    public List<KyqglxxDO> kyqglxxQuery(@Param("zrzydjdyh") String zrzydjdyh);

    /**
     * 查询取水权关联信息属性结构
     *
     * @param zrzydjdyh
     * @return
     */
    public List<QsqglxxDO> qsqglxxQuery(@Param("zrzydjdyh") String zrzydjdyh);

    /**
     * 查询排污权关联信息属性结构
     *
     * @param zrzydjdyh
     * @return
     */
    public List<PwqglxxDO> pwqglxxQuery(@Param("zrzydjdyh") String zrzydjdyh);

    /**
     * 查询水流状况信息表属性结构
     *
     * @param zrzydjdyh
     * @return
     */
    public List<SzyzkxxDO> szyzkxxQuery(@Param("zrzydjdyh") String zrzydjdyh);

    /**
     * 查询湿地状况
     *
     * @param zrzydjdyh
     * @return
     */
    public List<SdzkxxDO> sdzkxxQuery(@Param("zrzydjdyh") String zrzydjdyh);

    /**
     * 查询森林状况
     *
     * @param zrzydjdyh
     * @return
     */
    public List<SlzkxxDO> slzkxxQuery(@Param("zrzydjdyh") String zrzydjdyh);

    /**
     * 查询草原状况
     *
     * @param zrzydjdyh
     * @return
     */
    public List<CyzkxxDO> cyzkxxQuery(@Param("zrzydjdyh") String zrzydjdyh);

    /**
     * 查询荒地状况
     *
     * @param zrzydjdyh
     * @return
     */
    public List<HdzkxxDO> hdzkxxQuery(@Param("zrzydjdyh") String zrzydjdyh);

    /**
     * 查询无居民海岛状况
     *
     * @param zrzydjdyh
     * @return
     */
    public List<WjmhdzkxxDO> wjmhdzkxxQuery(@Param("zrzydjdyh") String zrzydjdyh);

    /**
     * 查询探明储量矿产资源状况
     *
     * @param zrzydjdyh
     * @return
     */
    public List<TmclkczyzkxxDO> tmclkczyzkxxQuery(@Param("zrzydjdyh") String zrzydjdyh);

    /**
     * 查询森林状况
     *
     * @param zrzydjdyh
     * @return
     */
    public List<HyzkxxDO> hyzkxxQuery(@Param("zrzydjdyh") String zrzydjdyh);


}
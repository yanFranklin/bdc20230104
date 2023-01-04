package cn.gtmap.realestate.certificate.core.mapper;


import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzYwxxDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0, chenyongqiang
 * @description  业务信息
 */
@Mapper
public interface BdcDzzzYwxxMapper {


    /**
     *
     * @param ywid
     * @return
     * @description  根据主键查询
     */
    BdcDzzzYwxxDo queryYwxxByYwid(String ywid);

    /**
     *
     * @param zzid
     * @return
     * @description  根据zzid查询
     */
    List<BdcDzzzYwxxDo> queryYwxxByZzid(String zzid);

    /**
     *
     * @param bdcDzzzYwxxDo
     * @return
     * @description  插入
     */
    int insertYwxx(BdcDzzzYwxxDo bdcDzzzYwxxDo);

    /**
     *
     * @param ywid
     * @return
     * description  删除
     */
    int deleteYwxxByYwid(String ywid);

    /**
     *
     * @param zzid
     * @return
     * description  删除 通过zzid
     */
    int deleteYwxxByZzid(String zzid);

    /**
     *
     * @param bdcDzzzYwxxDo
     * @return
     * description  更新 通过zzid
     */
    int updateYwxxByZzid(BdcDzzzYwxxDo bdcDzzzYwxxDo);

}

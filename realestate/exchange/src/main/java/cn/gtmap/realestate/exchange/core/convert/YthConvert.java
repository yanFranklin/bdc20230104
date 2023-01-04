package cn.gtmap.realestate.exchange.core.convert;

import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.ZdQlrDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.KttFwHDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.KttFwZrzDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.ZttGyQlrDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttFwCOldDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttFwLjzOldDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttZdjbxxOldDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 一体化相关实体转换类
 */
@Mapper(componentModel = "spring")
public interface YthConvert {

    // zddm bsm
    @Mappings({
            @Mapping(source = "djh", target = "zddm"),
            @Mapping(source = "qslx", target = "zdtzm"),
            @Mapping(source = "tdzl", target = "zl"),
            @Mapping(source = "tdyt", target = "yt"),
            @Mapping(source = "bdcdyhzt", target = "zt"),
            @Mapping(source = "qdjg", target = "jg"),
            @Mapping(source = "qsxz", target = "qllx"),
            @Mapping(source = "syqlx", target = "qlxz"),
            @Mapping(source = "jzrjl", target = "rjl"),
            @Mapping(source = "mjdw", target = "mjdw", defaultValue = "1"),
            @Mapping(source = "sztfh", target = "tfh")
    })
    KttZdjbxxOldDO getKttZdjbxxDOByZdDjdcbDO(ZdDjdcbDO zdDjdcbDO);

    @Mappings({
            @Mapping(source = "xh", target = "sxh"),
            @Mapping(source = "qlrzjlx", target = "zjzl"),
            @Mapping(source = "qlrzjh", target = "zjh"),
            @Mapping(source = "qlrlx", target = "qlrlb"),
            @Mapping(source = "qlrzjlx", target = "qlrlx")
    })
    ZttGyQlrDTO getZttGyQlrDTOByZdQlrDO(ZdQlrDO zdQlrDO);

    List<ZttGyQlrDTO> getZttGyQlrListDTOByZdQlrDOList(List<ZdQlrDO> zdQlrDOList);

    @Mappings({
            @Mapping(source = "fwcs", target = "zcs"),
            @Mapping(source = "dh", target = "ljzh"),
            @Mapping(source = "fwyt", target = "fwyt1")
    })
    KttFwLjzOldDO getKttFwLjzDOByFwLjzDO(FwLjzDO fwLjzDO);

    @Mappings({
            @Mapping(source = "lszd", target = "zddm"),
            @Mapping(source = "fwmc", target = "jzwmc"),
            @Mapping(source = "fwmc", target = "xmmc"),
            @Mapping(source = "bdczt", target = "zt"),
            @Mapping(source = "fwyt", target = "ghyt"),
            @Mapping(source = "fwcs", target = "zcs")
    })
    KttFwZrzDTO getKttFwZrzDOByFwLjzDO(FwLjzDO fwLjzDO);

    @Mappings({
            @Mapping(source = "bdczt", target = "zt"),
            @Mapping(source = "ghyt", target = "fwyt1"),
            @Mapping(source = "ghyt2", target = "fwyt2"),
            @Mapping(source = "ghyt3", target = "fwyt3"),
            @Mapping(source = "wlcs", target = "sjcs"),
            @Mapping(source = "fjh", target = "shbw"),
            @Mapping(source = "sxh", target = "hh"),
            @Mapping(source = "fwhx", target = "hx"),
            @Mapping(source = "wlcs", target = "ch")
    })
    KttFwHDTO getKttFwHDOByFwHsDO(FwYchsDO fwHsDO);

    List<KttFwHDTO> getKttFwHDOListByFwHsDOList(List<FwYchsDO> fwHsDOList);

    @Mappings({
            @Mapping(source = "dycs", target = "myc"),
            @Mapping(source = "wlcs", target = "ch"),
            @Mapping(source = "wlcs", target = "sjc")
    })
    KttFwCOldDO getKttFwCDOByFwHsDO(FwYchsDO fwHsDO);

    List<KttFwCOldDO> getKttFwCDOListByFwHsDOList(List<FwYchsDO> fwHsDO);

//    KttFwHDO getKttFwHDOByFwHsDO(FwHsDO fwHsDO);
}

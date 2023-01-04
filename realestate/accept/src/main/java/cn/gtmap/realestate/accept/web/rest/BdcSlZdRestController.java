package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.dto.config.BdcZdChangeDTO;
import cn.gtmap.realestate.common.core.enums.BdcSlZdEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.BdcZdGlService;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlZdRestService;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.3, 2019/1/14
 * @description 不动产受理字典项服务
 */
@RestController
@Api(tags = "不动产受理字典项服务")
public class BdcSlZdRestController extends BaseController implements BdcSlZdRestService {

    /**
     * 字典转换工具类
     */
    @Autowired
    EntityZdConvertUtils entityZdConvertUtils;
    /**
     * 字典缓存
     */
    @Autowired
    private BdcZdCache bdcZdCache;

    @Autowired
    BdcZdGlService bdcZdGlService;

    /**
     * 全部字典项
     */
    public static final Map<String, List<Map>> zdMap = new ConcurrentHashMap<>(128);

    /**
     * @param
     * @return Map<String ,List<Map>>
     * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 获取所有字典项
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取所有字典项")
    public Map<String, List<Map>> listBdcSlzd() {
        return zdMap;
    }

    /**listBdcZd
     * @param zdmc
     * @return
     * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据字段名称获取字典项
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取所有字典项")
    public List<Map> queryBdcSlzd(@PathVariable(name = "zdmc") String zdmc) {
        if (StringUtils.isBlank(zdmc)) {
            throw new MissingArgumentException("zdmc");
        }
        return zdMap.get(zdmc);
    }

    /**
     * @param entity
     * @return
     * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 字典项代码转名称
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "字典项代码转名称")
    public Object convertEntityToMc(@RequestBody Object entity) {
        entityZdConvertUtils.convertEntityToMc(entity);
        return entity;
    }

    /**
     * @param entity
     * @return
     * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 字典项名称转代码
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "字典项名称转代码")
    public Object convertEntityToDm(@RequestBody Object entity) {
        entityZdConvertUtils.convertEntityToDm(entity);
        return entity;
    }

    /**
     * @param convertMap
     * @return
     * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 转换map中的字典项代码为名称
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "转换map中的字典项代码为名称")
    public Map convertMapToMc(Map convertMap) {
        entityZdConvertUtils.convertMapToMc(convertMap);
        return convertMap;
    }

    /**
     * @param convertMap
     * @return
     * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 转换map中的字典项名称为代码
     */
    @Override
    public Map convertMapToDm(Map convertMap) {
        entityZdConvertUtils.convertMapToDm(convertMap);
        return convertMap;
    }

    /**
     * 刷新不动产受理字典对照缓存
     * 
     * @param zdmc 字典名称
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("刷新不动产受理字典对照缓存")
    public void refreshBdcSlZd(@PathVariable(name = "zdmc", required = false) String zdmc) {
        if (StringUtils.isNotBlank(zdmc)) {
            BdcSlZdEnum bdcSlZdEnum = BdcSlZdEnum.getBdcSlZdEnumByTableName(zdmc);
            if (bdcSlZdEnum != null) {
                bdcZdCache.refreshZdCacheMap(zdmc, bdcSlZdEnum.getTableClass());
                putZdMapByBdcSlZdEnum(bdcSlZdEnum);
            }
        }
    }

    /**
     * @author <a href ="mailto:caolu@gtmap.cn">caolu</a>
     * @description 初始化所有字典项
     */
    @PostConstruct
    public void initBdcZd(){
        for (BdcSlZdEnum bdcSlZdEnum : BdcSlZdEnum.values()) {
            putZdMapByBdcSlZdEnum(bdcSlZdEnum);
        }
    }


    /**
     * 新增字典项
     *
     * @param bdcZdChangeDTO
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("新增字典项")
    public void addZdItem(@RequestBody BdcZdChangeDTO bdcZdChangeDTO) {
        String zdmc = bdcZdChangeDTO.getZdmc();
        if (StringUtils.isNotBlank(zdmc)) {
            BdcSlZdEnum bdcZdEnum = BdcSlZdEnum.getBdcSlZdEnumByName(zdmc);
            if (bdcZdEnum != null) {
                bdcZdGlService.addZdTableData(bdcZdEnum.getTableClass(),bdcZdChangeDTO);
            }
        }
    }

    /**
     * 编辑字典项
     *
     * @param bdcZdChangeDTO
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("编辑字典项")
    public void editZdItem(@RequestBody BdcZdChangeDTO bdcZdChangeDTO) {
        String zdmc = bdcZdChangeDTO.getZdmc();
        if (StringUtils.isNotBlank(zdmc)) {
            BdcSlZdEnum bdcZdEnum = BdcSlZdEnum.getBdcSlZdEnumByName(zdmc);
            if (bdcZdEnum != null) {
                bdcZdGlService.editZdTableData(bdcZdEnum.getTableClass(),bdcZdChangeDTO);
            }
        }
    }

    /**
     * 删除字典项
     *
     * @param bdcZdChangeDTO
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除字典项")
    public void delZdItem(@RequestBody BdcZdChangeDTO bdcZdChangeDTO) {
        String zdmc = bdcZdChangeDTO.getZdmc();
        if (StringUtils.isNotBlank(zdmc)) {
            BdcSlZdEnum bdcZdEnum = BdcSlZdEnum.getBdcSlZdEnumByName(zdmc);
            if (bdcZdEnum != null) {
                bdcZdGlService.delZdTableData(bdcZdEnum.getTableClass(),bdcZdChangeDTO);
            }
        }
    }

    /**
     * 根据枚举把字典表更新或插入进去
     *
     * @param bdcSlZdEnum
     */
    private void putZdMapByBdcSlZdEnum(BdcSlZdEnum bdcSlZdEnum) {
        zdMap.put(StringUtils.lowerCase(bdcSlZdEnum.name()), bdcZdCache.getZdTableList(bdcSlZdEnum.getTableName(), bdcSlZdEnum.getTableClass()));
    }
}

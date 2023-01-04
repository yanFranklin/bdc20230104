package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.config.BdcZdChangeDTO;
import cn.gtmap.realestate.common.core.dto.config.BdcZdDsfzdgxDTO;
import cn.gtmap.realestate.common.core.enums.BdcZdEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.Impl.BdcZdGlServiceImpl;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.service.rest.register.BdcZdRestService;
import cn.gtmap.realestate.common.core.vo.accept.ui.TreeNodeVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import cn.gtmap.realestate.register.service.BdcXmxxService;
import cn.gtmap.realestate.register.service.BdcZdDsfzdgxService;
import cn.gtmap.realestate.register.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/12/29
 * @description 不动产字典项服务
 */
@RestController
@Api(tags = "不动产字典项服务")
public class BdcZdRestController extends BaseController implements BdcZdRestService {

    /**
     * 字典转换工具类
     */
    @Autowired
    EntityZdConvertUtils entityZdConvertUtils;
    @Autowired
    BdcXmxxService bdcXmxxService;

    @Autowired
    BdcZdDsfzdgxService bdcZdDsfzdgxService;
    /**
     * 字典缓存
     */
    @Autowired
    private BdcZdCache bdcZdCache;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    BdcZdGlServiceImpl bdcZdGlService;

    @Autowired
    BdcSlZdFeignService bdcSlZdFeignService;
    /**
     * 全部字典项
     */
    public static final Map<String, List<Map>> zdMap = new ConcurrentHashMap<>(128);

    /**
     * @param
     * @return Map<String   , List < Map>>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取所有字典项
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取所有字典项")
    public Map<String, List<Map>> listBdcZd() {
        return zdMap;
    }

    /**
     * @param zdmc
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据字段名称获取字典项
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取所有字典项")
    public List<Map> queryBdcZd(@PathVariable(name = "zdmc") String zdmc) {
        if (StringUtils.isEmpty(zdmc)) {
            throw new MissingArgumentException("zdmc");
        }
        return zdMap.get(zdmc);
    }

    /**
     * @param entity
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
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
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
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
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
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
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 转换map中的字典项名称为代码
     */
    @Override
    public Map convertMapToDm(Map convertMap) {
        entityZdConvertUtils.convertMapToDm(convertMap);
        return convertMap;
    }

    /**
     * @param xmid 项目ID
     * @return BdcDldmSyqxGxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前项目土地的使用期限
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询当前项目土地的使用期限")
    @Override
    public BdcDldmSyqxGxDO queryDldmSyqxGx(@PathVariable(name = "xmid") String xmid) {
        return bdcXmxxService.queryDldmSyqxGx(xmid);
    }

    /**
     * @param json
     * @param className
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存字典表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存字典表")
    public int saveBdcZdxx(@RequestBody String json,
                           @RequestParam(name = "className") String className, @RequestParam(name = "action") String action) {
        int result = 0;
        if (StringUtils.equals(action, "update")) {
            result = bdcEntityFeignService.updateByJsonEntity(json, className);
        } else {
            result = bdcEntityFeignService.insertByJsonEntity(json, className);
        }
        return result;
    }

    /**
     * 查询第三方系统与登记系统字典项值对照关系
     *
     * @param bdcZdDsfzdgxDO bdcZdDsfzdgxDO
     * @return BdcZdDsfzdgxDO BdcZdDsfzdgxDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询第三方系统与登记系统字典项值对照关系")
    public BdcZdDsfzdgxDO dsfZdgx(@RequestBody BdcZdDsfzdgxDO bdcZdDsfzdgxDO) {
        if (StringUtils.isBlank(bdcZdDsfzdgxDO.getZdbs()) || StringUtils.isBlank(bdcZdDsfzdgxDO.getDsfxtbs())) {
            throw new MissingArgumentException("缺少参数：字典表名称,第三方系统标识");
        }
        return bdcZdDsfzdgxService.queryZdgx(bdcZdDsfzdgxDO);
    }

    /**
     * 查询数据字典的中国省市区县的树形结构数据
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [] 无参数
     * @return: List<TreeNodeVO> 树形结构的List<TreeNode>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询数据字典的中国省市区县的树形结构数据")
    public List<TreeNodeVO> queryZdZgSsqx() {
        return bdcZdDsfzdgxService.queryZdZgSsqx();
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询第三方字典关系")
    public Map<String,List<Map>> queryDsfZdgx() {
        return bdcZdDsfzdgxService.queryAllDsfZdgx();
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询第三方字典项数据")
    @ApiImplicitParam(name = "bdcZdDsfzdgxDO", value = "不动产YML配置查询QO", required = false, paramType = "String")
    public Page<BdcZdDsfzdgxDO> listBbcDsfZdxGxByPage(Pageable pageable, @RequestParam(name = "json", required = false) String json) {
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        if(StringUtils.isNotBlank(json)){
            bdcZdDsfzdgxDO = JSON.parseObject(json, BdcZdDsfzdgxDO.class);
        }
        return this.bdcZdDsfzdgxService.listDsfZdxByPage(pageable, bdcZdDsfzdgxDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量保存第三方字典项数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZdDsfzdgxDTO", value = "第三方字典项数据DTO", dataType = "List", paramType = "body")})
    public List<BdcZdDsfzdgxDO> saveBdcZdDsfzdgx(@RequestBody BdcZdDsfzdgxDTO bdcZdDsfzdgxDTO) {
        return this.bdcZdDsfzdgxService.saveBdcZdDsfzdgx(bdcZdDsfzdgxDTO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据主键删除第三方字典项数据")
    @ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "第三方字典项数据ID", required = true, dataType = "String", paramType = "query")})
    public void deleteDsfZdxById(@RequestParam(name="id", required = true) String id) {
        this.bdcZdDsfzdgxService.deleteDsfZdxById(id);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据字典标识与第三方字典标识删除第三方字典项数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZdDsfzdgxDO", value = "第三方字典项数据", dataType = "BdcZdDsfzdgxDO", paramType = "body")})
    public void deleteDsfZdxBybs(@RequestBody BdcZdDsfzdgxDO bdcZdDsfzdgxDO) {
        this.bdcZdDsfzdgxService.deleteDsfZdxBybs(bdcZdDsfzdgxDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据字典标识与第三方字典标识获取第三方字典项数据")
    @ApiImplicitParam(name = "bdcZdDsfzdgxDO", value = "第三方字典项数据", dataType = "BdcZdDsfzdgxDO", paramType = "body")
    public List<BdcZdDsfzdgxDO> queryDsfZdxBybs(@RequestBody BdcZdDsfzdgxDO bdcZdDsfzdgxDO) {
        return this.bdcZdDsfzdgxService.queryDsfZdxBybs(bdcZdDsfzdgxDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("刷新不动产第三方字典对照缓存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zdbs", value = "字典标识", dataType = "zdbs", paramType = "query"),
            @ApiImplicitParam(name = "dsfxtbs", value = "第三方系统标识", dataType = "dsfxtbs", paramType = "query")
    })
    public void refreshBdcZdDsfzdgx(@RequestParam(name = "zdbs") String zdbs, @RequestParam(name = "dsfxtbs") String dsfxtbs) {
        if(StringUtils.isNotBlank(zdbs) && StringUtils.isNotBlank(dsfxtbs)){
            Map<String, List<Map>> map = this.bdcZdDsfzdgxService.queryDsfZdgxBybs(zdbs, dsfxtbs);
            // 删除需要刷新的对照缓存数据
            String bsKey = zdbs+ CommonConstantUtils.ZF_YW_DH + dsfxtbs;
            zdMap.remove(bsKey);
            // 添加对照数据至缓存中
            if(MapUtils.isNotEmpty(map)){
                zdMap.putAll(map);
            }
        }
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("刷新不动产字典对照缓存")
    public void refreshBdcZd(@PathVariable(name = "zdmc", required = false) String zdmc) {
        if (StringUtils.isNotBlank(zdmc)) {
            BdcZdEnum bdcZdEnum = BdcZdEnum.getBdcZdEnumByTableName(zdmc);
            if (bdcZdEnum != null) {
                bdcZdCache.refreshZdCacheMap(zdmc, bdcZdEnum.getTableClass());
                putZdMapByBdcZdEnum(bdcZdEnum);
            }
        }
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("通过字典标识和第三方系统标识查询第三方字典项关系数据")
    public List<Map> queryDsfZd(@PathVariable(name = "zdbs") String zdbs, @PathVariable(name = "dsfxtbs")String dsfxtbs){
        return bdcZdDsfzdgxService.queryDsfZd(zdbs, dsfxtbs);

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
            BdcZdEnum bdcZdEnum = BdcZdEnum.getBdcZdEnumByName(zdmc);
            if (bdcZdEnum != null) {
                bdcZdGlService.addZdTableData(bdcZdEnum.getTableClass(),bdcZdChangeDTO);
            }else {
                bdcSlZdFeignService.addZdItem(bdcZdChangeDTO);
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
            BdcZdEnum bdcZdEnum = BdcZdEnum.getBdcZdEnumByName(zdmc);
            if (bdcZdEnum != null) {
                bdcZdGlService.editZdTableData(bdcZdEnum.getTableClass(),bdcZdChangeDTO);
            }else {
                bdcSlZdFeignService.editZdItem(bdcZdChangeDTO);
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
            BdcZdEnum bdcZdEnum = BdcZdEnum.getBdcZdEnumByName(zdmc);
            if (bdcZdEnum != null) {
                bdcZdGlService.delZdTableData(bdcZdEnum.getTableClass(),bdcZdChangeDTO);
            }else {
                bdcSlZdFeignService.delZdItem(bdcZdChangeDTO);
            }
        }
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 初始化所有字典项
     */
    @PostConstruct
    public void initBdcZd() {
        //2020-7-31 sly 把所有字典改为枚举，循环加入，方便重用
        for (BdcZdEnum bdcZdEnum : BdcZdEnum.values()) {
            putZdMapByBdcZdEnum(bdcZdEnum);
        }
        /**
         * 添加第三方字典对照信息，采用zbbs,dsfxtbs作为key，设置内存缓存内容
         */
        zdMap.putAll(this.bdcZdDsfzdgxService.queryAllDsfZdgx());
    }

    /**
     * 根据枚举吧字典表更新或插入进去
     *
     * @param bdcZdEnum
     */
    private void putZdMapByBdcZdEnum(BdcZdEnum bdcZdEnum) {
        zdMap.put(StringUtils.lowerCase(bdcZdEnum.name()), bdcZdCache.getZdTableList(bdcZdEnum.getTableName(), bdcZdEnum.getTableClass()));
    }


}

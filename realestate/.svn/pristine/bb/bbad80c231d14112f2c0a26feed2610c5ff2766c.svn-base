package cn.gtmap.realestate.register.service.impl.xxbl;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.enums.BdcDbTypeEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.support.mybatis.utils.AnnotationsUtils;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbDetailVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbJbVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbVO;
import cn.gtmap.realestate.common.util.DateUtils;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static cn.gtmap.realestate.common.util.LogCompareUtils.getClassName;
import static cn.gtmap.realestate.common.util.LogCompareUtils.getFieldName;

/**
 * 对比信息处理类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 8:31 上午 2020/4/27
 */
@Component
public class CompareHandler {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CompareHandler.class.getName());

    /**
     * 字典服务
     */
    @Autowired
    private BdcZdCache bdcZdCache;

    /**
     * 返回 补录修改流程 BdcYwxxDTO 前后变化的情况，返回结果为 BdcDbVO 集合<br/>
     * <p>
     * 用于补录修改流程中 dbym.html 展示变化数据<br/>
     * 通过反射遍历 BdcYwxxDTO 数据和补录修改流程初始化时发生的变化。
     *
     * @param ywxxBefore 业务信息修改前
     * @param ywxxAfter  业务信息修改后
     * @return {List<BdcDbVO>} 两个对象之间变化
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public List<BdcDbVO> compareBdcYwxx(BdcYwxxDTO ywxxBefore, BdcYwxxDTO ywxxAfter) throws Exception {
        Field[] fields = BdcYwxxDTO.class.getDeclaredFields();
        List<BdcDbVO> bdcDbVOS = new ArrayList<>();
        for (Field field : fields) {
            // 取消域的访问权限
            field.setAccessible(true);
            // 返回指定对象上此 Field 表示的字段的值。
            Object objBefore = ywxxBefore == null ? null : field.get(ywxxBefore);
            Object objAfter = ywxxAfter == null ? null : field.get(ywxxAfter);
            if (objAfter != null || objBefore != null) {
                // 判断是否是 list 类型的数据
                if (field.getType() != List.class) {
                    BdcDbJbVO bdcDbJbVO = compareNull(objBefore, objAfter);
                    bdcDbVOS.add(BdcDbVO.buildSingle(bdcDbJbVO));
                } else {
                    // 处理集合类型的对象
                    List beforelist = (List) objBefore;
                    List afterlist = (List) objAfter;
                    if (CollectionUtils.isEmpty(beforelist) && CollectionUtils.isEmpty(afterlist)) {
                        continue;
                    }
                    if (CollectionUtils.isNotEmpty(beforelist) && CollectionUtils.isNotEmpty(afterlist) && beforelist.size() == 1 && afterlist.size() == 1) {
                        BdcDbJbVO bdcDbJbVO = compareNull(beforelist.get(0), afterlist.get(0));
                        bdcDbVOS.add(BdcDbVO.buildSingle(bdcDbJbVO));
                        continue;
                    }
                    Method keyMethod;
                    String objName = "";
                    HashMap<String, Object> beforeMap = Maps.newHashMap();
                    // 获取主键的方法 （if 中已经判断了 afterlist 和 before 均不为空）
                    if (CollectionUtils.isNotEmpty(afterlist)) {
                        keyMethod = AnnotationsUtils.getAnnotationsName(afterlist.get(0));
                    } else {
                        keyMethod = AnnotationsUtils.getAnnotationsName(beforelist.get(0));
                    }

                    if (CollectionUtils.isNotEmpty(beforelist)) {
                        // 将 before 数据存到 map 中用于对比分析
                        for (Object o : beforelist) {
                            // 从对象中获取对象名
                            if (StringUtils.isBlank(objName)) {
                                objName = getClassName(o);
                            }
                            String id = keyMethod.invoke(o).toString();
                            if (StringUtils.isBlank(id)) {
                                throw new AppException(objName + "主键丢失异常");
                            } else {
                                beforeMap.put(id, o);
                            }
                        }
                    }

                    List<BdcDbJbVO> bdcDbJbVOS = Lists.newArrayList();
                    if (CollectionUtils.isNotEmpty(afterlist)) {
                        // 遍历 after 集合对比数据，判断数据是否是新增或删除
                        for (Object o : afterlist) {
                            // 从对象中获取对象名
                            if (StringUtils.isBlank(objName)) {
                                objName = getClassName(o);
                            }
                            String afterid = keyMethod.invoke(o).toString();
                            Object temp = beforeMap.get(afterid);
                            if (temp == null) {
                                // beforeMap 中不存在对应 id 表示属于新增的 id
                                BdcDbJbVO bdcDbJbVO = compareNull(null, o);
                                bdcDbJbVOS.add(bdcDbJbVO);
                            } else {
                                // 存在则对于两个对象进行对比，然后删除 beforeMap 中对应的 key
                                BdcDbJbVO bdcDbJbVO = compareNull(beforeMap.get(afterid), o);
                                bdcDbJbVOS.add(bdcDbJbVO);
                                beforeMap.remove(afterid);
                            }
                        }
                    }

                    // beforeMap 中剩下的键值对表示被删除了的数据
                    if (beforeMap.size() > 0) {
                        // 获取表名
                        for (Map.Entry<String, Object> entry : beforeMap.entrySet()) {
                            LOGGER.debug(" 新增 id 为: {},的 {} 对象", entry.getKey(), objName);
                            // beforeMap 中不存在对应 id 表示属于新增的 id
                            BdcDbJbVO bdcDbJbVO = compareNull(entry.getValue(), null);
                            bdcDbJbVOS.add(bdcDbJbVO);
                        }
                    }
                    BdcDbVO bdcDbVO = new BdcDbVO();
                    bdcDbVO.setData(bdcDbJbVOS);
                    bdcDbVO.setTitle(objName);
                    String title = checkQlrTitle(beforelist, afterlist);
                    if (StringUtils.isNotBlank(title)) {
                        bdcDbVO.setTitle(title);
                    }
                    bdcDbVOS.add(bdcDbVO);
                }
            }
        }
        return bdcDbVOS;
    }

    /**
     * 返回 补录修改流程 BdcYwxxDTO 前后变化的情况，返回结果为 BdcDbDetailVO 集合<br/>
     * <p>
     * 用于补录修改内容页面修改数据高亮显示<br/>
     * 调用 compareBdcYwxx 方法，对其返回结果进行重新组合
     *
     * @param ywxxBefore 业务信息修改前
     * @param ywxxAfter  业务信息修改后
     * @return {List<BdcDbDetailVO>} 两个对象之间变化
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public List<BdcDbDetailVO> compareYwxxDetail(BdcYwxxDTO ywxxBefore, BdcYwxxDTO ywxxAfter) throws Exception {
        // 高亮字段集合
        List<BdcDbDetailVO> glDetails = Lists.newArrayList();
        // 对比数据
        List<BdcDbVO> change = compareBdcYwxx(ywxxBefore, ywxxAfter);
        // 转换结果集
        for (BdcDbVO bdcDbVO : change) {
            List<BdcDbJbVO> JbVOs = bdcDbVO.getData();
            for (BdcDbJbVO jbVO : JbVOs) {
                List<BdcDbDetailVO> detailVOS = jbVO.getData();
                glDetails.addAll(detailVOS);
            }
        }
        return glDetails;
    }

    /**
     * 比较对象前后信息的区别（对于空对象进行处理）<br/>
     * <p>
     * 补录修改数据对比<br>
     * 针对 before 和 after 为空的情况做特殊处理，两者均不为空则调用 compare() 比较数据
     *
     * @param before 修改前对象
     * @param after  修改后对象
     * @return {BdcDbJbVO} 对比基本信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public BdcDbJbVO compareNull(Object before, Object after) throws Exception {
        BdcDbJbVO bdcDbJbVO = new BdcDbJbVO();
        String title;
        if (before == null) {
            // 新增数据
            bdcDbJbVO.setType(BdcDbTypeEnum.NEW);
            bdcDbJbVO.setData(parse(after, BdcDbTypeEnum.NEW));
            title = getClassName(after);
        } else if (after == null) {
            // 删除数据
            bdcDbJbVO.setType(BdcDbTypeEnum.DELETE);
            bdcDbJbVO.setData(parse(before, BdcDbTypeEnum.DELETE));
            title = getClassName(before);
        } else {
            bdcDbJbVO.setType(BdcDbTypeEnum.MODIFY);
            bdcDbJbVO.setData(compare(before, after));
            title = getClassName(before);
        }
        bdcDbJbVO.setTitle(title);
        return bdcDbJbVO;
    }

    /**
     * 比较对象前后变化的情况<br>
     * <p>
     * 比较补录修改流程数据和初始化是变化情况<br>
     * 此方法用于比较前后均不为空的情况，并且只会解析当前类一层的属性<br>
     * 例：String Date 类型可以被直接比较返回结果
     * 反例：BdcYwxxDTO 则会将属性 bdcXm 解析为字符串比较，不会遍历 bdcXm 的属性
     *
     * @param before 修改前的对象
     * @param after  修改后的对象
     * @return {List<BdcDbDetailVO>} 两个对象变化信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public List<BdcDbDetailVO> compare(Object before, Object after) throws Exception {
        List<BdcDbDetailVO> details = new ArrayList<>();
        Object csxx;
        Object xgxx;
        Class<?> clazz = before.getClass();
        // 判断是否是权利人，权利人的特殊处理，需要在 页面名称字段拼接上 qlrid
        String qlrid = "";
        if (clazz == BdcQlrDO.class) {
            qlrid = ((BdcQlrDO) before).getQlrid();
        }
        // 遍历属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            csxx = field.get(before);
            xgxx = field.get(after);
            if (csxx == null && xgxx == null) {
                continue;
            }
            // 数据发生变化，处理数据
            if (!Objects.equals(xgxx, csxx)) {
                details.add(buildDbDetailVO(field, csxx, xgxx, qlrid));
            }
        }
        return details;
    }

    /**
     * 解析对象信息<br>
     * <p>
     * 修改信息对比时，修改前或修改后的数据有一个为 null，说明属于新增或者删除类型，直接解析不为空的对象返回到页面
     *
     * @param object 被解析的对象
     * @return List<BdcDbDetailVO>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 解析对象信息
     */
    public List<BdcDbDetailVO> parse(Object object, BdcDbTypeEnum bdcDbTypeEnum) throws Exception {
        List<BdcDbDetailVO> details = new ArrayList<>();
        Object obj;
        Class<?> clazz = object.getClass();
        String qlrid = "";
        // 判断是否是权利人，权利人的 页面对应的 name 特殊处理
        if (clazz == BdcQlrDO.class) {
            qlrid = ((BdcQlrDO) object).getQlrid();
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            obj = field.get(object);
            // 数据为空就跳过循环
            if (obj == null) {
                continue;
            }
            if (BdcDbTypeEnum.NEW.equals(bdcDbTypeEnum)) {
                details.add(buildDbDetailVO(field, null, obj, qlrid));
            } else {
                details.add(buildDbDetailVO(field, obj, null, qlrid));
            }
        }
        return details;
    }

    private BdcDbDetailVO buildDbDetailVO(Field field, Object before, Object after, String qlrid) {
        // 获取字段名称
        String fieldName = getFieldName(field);
        // 权利人的页面名称特殊处理
        String ymname = field.getName() + qlrid;

        String beforeStr = before == null ? StringUtils.EMPTY : before.toString();
        String afterStr = after == null ? StringUtils.EMPTY : after.toString();
        // 处理日期格式
        if (before instanceof Date) {
            beforeStr = DateUtils.formateYmdhms((Date) before);
        }
        if (after instanceof Date) {
            afterStr = DateUtils.formateYmdhms((Date) after);
        }
        // 字典项处理
        Zd zd = field.getAnnotation(Zd.class);
        if (zd != null) {
            // 针对字典项不匹配的老数据直接显示字典项内容
            String beforeZd = bdcZdCache.getFeildValue(zd.tableClass().getSimpleName(), before, zd.tableClass());
            beforeStr = StringUtils.isBlank(beforeZd) ? beforeStr : beforeZd;
            String afterZd = bdcZdCache.getFeildValue(zd.tableClass().getSimpleName(), after, zd.tableClass());
            afterStr = StringUtils.isBlank(afterZd) ? afterStr : afterZd;
        }
        return new BdcDbDetailVO(fieldName, beforeStr, afterStr, ymname);
    }

    /**
     * list 类型的 title 特殊处理<br>
     * <p>
     * 集合为权利人类型，返回 "权利人"，否则返回 ""
     *
     * @param beforelist 修改前集合
     * @param afterlist  修改后集合
     * @return 集合的 title
     */
    private String checkQlrTitle(List beforelist, List afterlist) {
        if (CollectionUtils.isNotEmpty(beforelist)) {
            if (beforelist.get(0) instanceof BdcQlrDO) {
                return "权利人";
            }
        } else if (CollectionUtils.isNotEmpty(afterlist)) {
            if (afterlist.get(0) instanceof BdcQlrDO) {
                return "权利人";
            }
        }
        return StringUtils.EMPTY;
    }
}

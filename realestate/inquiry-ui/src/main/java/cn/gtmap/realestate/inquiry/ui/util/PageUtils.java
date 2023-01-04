package cn.gtmap.realestate.inquiry.ui.util;

import cn.gtmap.gtc.starter.gcas.config.GTAutoConfiguration;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: <a href="@mailto:hynkoala@163.com">hanyaning</a>
 * @version: V1.0, 2019.03.13
 * @description: 分页相关工具
 */
public class PageUtils {
    /**
     * list数据集转为分页对象
     *
     * @param inList   数据集list
     * @param pageable 分页参数对象
     * @return org.springframework.data.domain.Page
     * @date 2019.03.13 16:15
     * @author hanyaning
     */
    public static Page listToPage(List inList, Pageable pageable) {
        if (CollectionUtils.isNotEmpty(inList)) {
            int pageNumber = pageable.getPageNumber();
            int size = pageable.getPageSize();
            int total = inList.size();
            if (total > (pageNumber + 1) * size) {
                inList = inList.subList(size * pageNumber, (pageNumber + 1) * size);
            } else {
                inList = inList.subList(size * pageNumber, total);
            }
            return new GTAutoConfiguration.DefaultPageImpl(inList, pageNumber, size, total);
        } else {
            return new PageImpl(new ArrayList(), pageable, 0);
        }

    }
}
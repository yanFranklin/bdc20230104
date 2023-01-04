package cn.gtmap.realestate.common.core.vo.register.ui;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 不动产信息对比 VO 对象
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 2:19 下午 2020/4/9
 */
public class BdcDbVO {
    /**
     * 实体名称
     */
    private String title;
    /**
     * 基本数据， dbJbVOS
     */
    private List<BdcDbJbVO> data;

    /**
     * @param bdcDbJbVO 基本信息
     * @return {BdcDbVO} bdcDbVO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 根据基本信息构建一个对比信息对象
     */
    public static BdcDbVO buildSingle(BdcDbJbVO bdcDbJbVO) {
        BdcDbVO bdcDbVO = new BdcDbVO();
        bdcDbVO.setData(Collections.singletonList(bdcDbJbVO));
        bdcDbVO.setTitle(bdcDbJbVO.getTitle());
        return bdcDbVO;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<BdcDbJbVO> getData() {
        return data;
    }

    public void setData(List<BdcDbJbVO> data) {
        this.data = data;
    }

    /**
     * @param dbJbVOS
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 添加基本信息
     */
    public void addDbJbVOS(List<BdcDbJbVO> dbJbVOS) {
        if (CollectionUtils.isNotEmpty(dbJbVOS)) {
            if (data == null) {
                data = new ArrayList<>();
            }
            data.addAll(dbJbVOS);
        }
    }
}

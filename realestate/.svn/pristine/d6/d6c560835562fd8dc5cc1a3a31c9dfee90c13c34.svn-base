package cn.gtmap.realestate.check.service.impl;


import cn.gtmap.realestate.check.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.check.service.GzxxService;
import cn.gtmap.realestate.check.utils.ExcelUtil;
import jxl.write.WriteException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ccx 2019/1/14
 * @description
 */
@Service
public class GzxxServiceImpl implements GzxxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GzxxServiceImpl.class);
    private static final String EXPORT_FILENAME_PRE = "质检情况汇总";
    /**
     * 导出列标题与实体成员变量对应Map
     */
    private static final Map<String, Object> EXPORT_COLUMN = new LinkedHashMap<String,Object>();
    @Autowired
    BdcXmMapper bdcXmMapper;
    @Override
    public List<Map<String, Object>> queryGzxxList(Map<String, Object> map) {
        return bdcXmMapper.getGzxxGroupByPage(map);
    }

    @Override
    public void export(List<Map<String, Object>> list, HttpServletResponse response) {
        String fileName = EXPORT_FILENAME_PRE + System.currentTimeMillis() + ".xls";
        initColumn();
        try {
            ExcelUtil.exportExcel(fileName,EXPORT_COLUMN,list,response);
        } catch (WriteException e) {
            LOGGER.error("导出excel报错",e);
        } catch (IOException e) {
            LOGGER.error("导出excel报错",e);
        }
    }

    @Override
    public void updateBatchCheckGzxxSfhl(Integer sfhl,List<String> gzidList){
        if(CollectionUtils.isNotEmpty(gzidList) &&sfhl !=null) {
            bdcXmMapper.updateBatchCheckGzxxSfhl(sfhl, gzidList);
        }

    }

    /**
     * 初始化导出列
     */
    private static void initColumn(){
        if(EXPORT_COLUMN == null || EXPORT_COLUMN.size()==0){
            EXPORT_COLUMN.put("检查项","GROUP_MC");
            EXPORT_COLUMN.put("检查描述","GZMS");
            EXPORT_COLUMN.put("检查规则","TSXX");
            EXPORT_COLUMN.put("规则code","GZCODE");
            EXPORT_COLUMN.put("规则范围","GZFW");
            EXPORT_COLUMN.put("错误数","SL");
        }
    }
}

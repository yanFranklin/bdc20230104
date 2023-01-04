package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcJgLzrGxDO;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxjgDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxtjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxDO;
import cn.gtmap.realestate.common.core.dto.init.BdcJgLzrGxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXtjgLzrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcXtJgpzFeignService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/27
 * @description 机构配置
 */
@RestController
@RequestMapping(value = "/rest/v1.0/jgpz")
public class BdcXtJgpzController extends BaseController{
    @Autowired
    private BdcXtJgpzFeignService bdcXtJgpzFeignService;

    @Autowired
    private BdcXtJgFeignService bdcXtJgFeignService;

    /**
     * 保存机构配置信息
     * @param bdcXtJgDO
     */
    @PostMapping("/save")
    public BdcXtJgDO saveJgpz(@RequestBody BdcXtJgDO bdcXtJgDO) {
        return bdcXtJgpzFeignService.saveJgpz(bdcXtJgDO);
    }

    /**
     * 获取机构配置信息
     * @param jgid
     */
    @GetMapping("/query")
    public BdcXtJgDO saveJgpz(String jgid) {
        return bdcXtJgpzFeignService.queryJgpz(jgid);
    }

    /**
     * 删除机构配置
     * @param jgidList
     */
    @GetMapping("/delete")
    public void deleteJgpz(@RequestParam("jgidList") List<String> jgidList){
        bdcXtJgpzFeignService.deleteJgpz(jgidList);
    }



    @PostMapping("/import/excel")
    public Object importXtjgToExcel(MultipartHttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            return null;
        }
        int count = 0;
        InputStream fileIn = null;
        try {
            Iterator<String> iterator = httpServletRequest.getFileNames();
            // 遍历所有上传文件
            while (iterator.hasNext()) {
                String fileName = iterator.next();
                MultipartFile multipartFile = httpServletRequest.getFile(fileName);
                fileIn = multipartFile.getInputStream();
                // 根据指定的文件输入流导入Excel从而产生Workbook对象
                Workbook workbook = Workbook.getWorkbook(fileIn);
                List<BdcXtJgDO> bdcXtJgDOList = importToObject(BdcXtJgDO.class, 0, workbook);
                if(CollectionUtils.isNotEmpty(bdcXtJgDOList)) {
                    bdcXtJgDOList.forEach(bdcXtJg -> {
                        if (StringUtils.isBlank(bdcXtJg.getJgid())) {
                            bdcXtJg.setJgid(UUIDGenerator.generate16());
                    }
                    });
                    count+=bdcXtJgpzFeignService.saveJgpzPl(bdcXtJgDOList);
                }
            }
        } catch (IOException | BiffException | IllegalAccessException | InstantiationException | ParseException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new AppException("导入失败");
        } finally {
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
            }
        }

        return count;
    }

    /**
     * 查询银行机构
     * @return
     */
    @GetMapping("/query/allYgJg")
    public List<BdcXtJgDO> queryAllJg() {
        return bdcXtJgFeignService.listBdcXtJg(1);
    }

    /**
     * 分页查询领证人
     * @param pageable
     * @param bdcXtjgLzrQO
     * @return
     */
    @GetMapping("/queryLzrByPage")
    public Object queryLzrByPage(@LayuiPageable Pageable pageable, BdcXtjgLzrQO bdcXtjgLzrQO) {
        return addLayUiCode(bdcXtJgFeignService.queryJgLzrByPage(pageable,JSON.toJSONString(bdcXtjgLzrQO)));
    }

    /**
     * 删除机构默认领证人
     * @param gxid
     */
    @DeleteMapping("/delete/{gxid}")
    public void deleteLzr(@PathVariable("gxid") String gxid) {
         bdcXtJgFeignService.deleteJglzr(gxid);
    }

    /**
     * 新增机构默认领证人
     * @param bdcJgLzrGxDO
     */
    @PostMapping("/lzr/save")
    public void deleteLzr(@RequestBody BdcJgLzrGxDO bdcJgLzrGxDO) {
        bdcXtJgFeignService.insertBdcJgLzr(bdcJgLzrGxDO);
    }
}

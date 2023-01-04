package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmSfbzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2019/1/3
 * @description 不动产受理收费项目配置rest服务
 */
public interface BdcSlSfxmPzRestService {
    /**
     * @param djxl 登记小类
     * @return 不动产受理收费项目配置
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据登记小类获取不动产受理收费项目配置
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxmpz/list/{djxl}")
    List<BdcSlSfxmPzDO> listBdcSlSfxmPzByDjxl(@PathVariable(value = "djxl") String djxl);

    /**
     * @param sfxmdm 收费项目代码
     * @return 不动产收费项目收费标准
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费项目代码获取不动产收费项目收费标准
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxmbz/list/{sfxmdm}")
    List<BdcSlSfxmSfbzDO> listBdcSlSfxmSfbzDO(@PathVariable(value = "sfxmdm") String sfxmdm);

    /**
     * @return 不动产收费项目收费标准
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 获取不动产收费项目收费标准
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxmbz/list")
    List<BdcSlSfxmSfbzDO> listBdcSlSfxmSfbz();


    /**
     * @param bdcSlSfxmPzDO 不动产受理收费项目配置
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产受理收费项目配置
     */
    @PutMapping("/realestate-accept/rest/v1.0/sfxmpz")
    int saveBdcSlSfxmPzDO(@RequestBody BdcSlSfxmPzDO bdcSlSfxmPzDO);

    /**
     * @param bdcSlSfxmPzDOList 收费项目配置集合
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除不动产受理收费项目配置
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sfxmpz")
    int deleteBdcSlSfxmPzDO(@RequestBody List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList);


    /**
     * @param bdcSlSfxmPzJson  收费项目配置
     * @return 收费项目配置分页
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 收费项目配置分页
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxmpz/page")
    Page<BdcSlSfxmPzDO> listBdcSlSfxmPzByPage(Pageable pageable, @RequestParam(name = "bdcSlSfxmPzJson" ,required = false) String bdcSlSfxmPzJson);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取收费项目配置 最大序号
     */
    @PostMapping("/realestate-accept/rest/v1.0/djsfxmpzxlpz/maxsxh")
    Integer querySfxmPzMaxSxh(@RequestBody BdcSlSfxmPzDO bdcSlSfxmPzDO);

    /**
     * @return 收费项目配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取所有收费项目配置
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxmpz/all")
    List<BdcSlSfxmDTO> listAllBdcSlSfxmDTO();

    /**
     * 根据登记小类获取收费项目配置
     * @param djxl 登记小类
     * @return 收费项目配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxmpz/{djxl}")
    List<BdcSlSfxmDTO> listBdcSfxmDTOByDjxl(@PathVariable(value = "djxl") String djxl);
}

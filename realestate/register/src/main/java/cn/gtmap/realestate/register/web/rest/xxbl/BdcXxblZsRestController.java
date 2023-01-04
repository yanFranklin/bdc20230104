package cn.gtmap.realestate.register.web.rest.xxbl;

import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcXxblZsRestService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcXzZsVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsVO;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblZsService;
import cn.gtmap.realestate.register.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 补录证书服务
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2020/01/08 08:28
 */
@RestController
@Api(tags = "不动产信息补录证书服务接口")
public class BdcXxblZsRestController extends BaseController implements BdcXxblZsRestService {
    /**
     * 补录证书服务
     */
    @Autowired
    private BdcXxblZsService bdcXxblZsService;

    /**
     * @param xmid   xmid
     * @param bdcqzh bdcqzh 录入的不动产权证号，分别持证的逗号隔开，英文中文均可
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 信息补录更新不动产权证号 </br>
     * 1. 保存证书表中产权证号 </br>
     * 2. 更新相关表中冗余字段 </br>
     * 3. 更新权利其他状况和附记 </br>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "补录更新不动产权证号")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bdcqzh", value = "不动产权证号", dataType = "String", paramType = "query")})
    public void updateBlBdcqzh(String xmid, String bdcqzh) {
        if (StringUtils.isBlank(bdcqzh)) {
            throw new MissingArgumentException("bdcqzh");
        }
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }
        // 处理英文逗号
        bdcqzh = StringToolUtils.replaceBracket(bdcqzh);
        bdcXxblZsService.updateBlBdcqzh(xmid, bdcqzh);
    }

    /**
     * @param zsid   证书 id
     * @param xmid   项目 id
     * @param qlqtzk 权利其他状况
     * @param bdcqzh 不动产权证号
     * @param fj     附记
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 更新部分证书信息，同时处理冗余字段 </br>
     * 权利其他状况、附记以及不动产权证号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新部分证书信息，同时处理冗余字段")
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "证书 id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目 id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlqtzk", value = "权利其他状况", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bdcqzh", value = "不动产权证号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fj", value = "附记", dataType = "String", paramType = "query")})
    public void updateBfZsxx(String zsid, String xmid, String qlqtzk, String bdcqzh, String fj) {
        if (StringUtils.isAnyBlank(zsid, xmid)) {
            throw new MissingArgumentException("xmid,zsid");
        }
        bdcXxblZsService.updateBfZsxx(zsid, xmid, qlqtzk, bdcqzh, fj);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [zsid, xmid, bdcqzh]
     * @return void
     * @description 更新不动产权证号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新不动产权证号")
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "证书 id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目 id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bdcqzh", value = "不动产权证号", dataType = "String", paramType = "query")})
    public void updateBdcqzh(String zsid, String xmid, String bdcqzh) {
        if (StringUtils.isAnyBlank(zsid, xmid)) {
            throw new MissingArgumentException("xmid,zsid");
        }
        bdcXxblZsService.updateBdcqzh(zsid, xmid, bdcqzh);
    }

    /**
     * 更新证书锁定 <br>
     * <p>
     * 由于重新生成证书，所以先对于将原证书锁定信息关联到新生成的证书上 <br/>
     *
     * @param xmid       xmid
     * @param bdcZssdDOS 证书锁定
     * @return 更新数据条数
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新证书锁定")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "xmid", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bdcZssdDOS", value = "证书锁定集合", dataType = "BdcZssdDO", paramType = "body")})
    @Override
    public int updateZssd(@RequestParam String xmid, @RequestBody List<BdcZssdDO> bdcZssdDOS) {
        if (StringUtils.isBlank(xmid) && CollectionUtils.isEmpty(bdcZssdDOS)) {
            throw new MissingArgumentException("xmid, bdcZssdDOS");
        }
        return bdcXxblZsService.updateZssd(xmid, bdcZssdDOS);
    }

    /**
     * 查询证书锁定信息 <br>
     *
     * @param xmid xmid
     * @param sdzt 锁定状态
     * @return 证书锁定集合
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询证书锁定信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "xmid", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sdzt", value = "锁定状态", dataType = "Integer", paramType = "query")})
    public List<BdcZssdDO> listZssdByXmid(String xmid, Integer sdzt) {
        if (StringUtils.isBlank(xmid) || sdzt == null) {
            throw new MissingArgumentException("xmid,sdzt");
        }
        return bdcXxblZsService.listZssdByXmid(xmid, sdzt);
    }

    /**
     * 修正更新证书信息
     *
     * @param bdcXzZsVO
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新证书锁定")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXzZsVO", value = "证书锁定集合", dataType = "BdcXzZsVO", paramType = "body")})
    public void updateXzBdczs(@RequestBody BdcXzZsVO bdcXzZsVO) {
        bdcXxblZsService.updateXzBdcZs(bdcXzZsVO);
    }

    /**
     * 添加项目和已有证书的关联关系
     * @param bdcZsQO
     * @author <a href="mailto:liaoxiang@gtmap.cn">wangyinghao</a>
     * @description 添加项目和已有证书的关联关系
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "添加项目和已有证书的关联关系")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZsQO", value = "证书信息", dataType = "BdcZsQO", paramType = "body")})
    public void addXmZsConnection(@RequestBody BdcZsQO bdcZsQO){
        bdcXxblZsService.addXmZsConnection(bdcZsQO);
    }
    /**
     * 查询信息补录证书
     * @param bdcZsQO
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 查询信息补录证书
     */
    @Override
    public Page<BdcZsVO> bdcZsByPageJson(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcZsQO bdcZsQO) {
        Map<String, Object> map = new HashMap(2);
        sort = new Sort("qlr");
        Pageable pageable = new PageRequest(page, size, sort);
        if (bdcZsQO != null) {
            String json = JSONObject.toJSONString(bdcZsQO);
            map = pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
        }
        return repo.selectPaging("listBdcXxblZsByPage", map, pageable);
    }
}

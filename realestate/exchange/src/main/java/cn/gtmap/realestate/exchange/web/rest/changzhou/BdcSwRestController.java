package cn.gtmap.realestate.exchange.web.rest.changzhou;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.exchange.service.impl.inf.changzhou.ChangzhouSwServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/05/09
 * @description 常州 登记提供给税务接口
 */
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/sw")
@Api(tags = "常州登记提供给税务接口")
public class BdcSwRestController {
    @Autowired
    private ChangzhouSwServiceImpl swService;


    /**
     * 家庭房产信息查询
     * @param request 权利人信息
     * @return {String} 房产信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/jtfcxx")
    @ApiOperation(value = "家庭房产信息查询")
    @ApiImplicitParam(name = "taxbizmlRequest", value = "权利人信息", dataType = "TaxbizmlRequestDTO",paramType = "body")
    public void queryFcxx(HttpServletRequest request, HttpServletResponse response) {
        try (InputStream inputStream = request.getInputStream();
                OutputStream outputStream = response.getOutputStream()) {
            String xml = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            String responseXml = swService.queryFcxx(xml);
            IOUtils.write(responseXml, outputStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("房产信息查询异常，请核查查询参数");
        }
    }

}

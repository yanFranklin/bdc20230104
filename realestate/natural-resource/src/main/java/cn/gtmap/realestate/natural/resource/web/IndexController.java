package cn.gtmap.realestate.natural.resource.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiOperation;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/10/30
 * @description
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/lskTest")
    @ApiOperation(value="lskTest")
    public String lskTest(@RequestParam String uid){
        return uid;
    }

}

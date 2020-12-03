package com.hbsoft.test;

import com.hb.annotation.LoginNotRequired;
import com.hb.bean.CallResult;
import com.hb.util.GsonUtil;
import com.hbsoft.test.bean.TestBena;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class TestVController {

    @LoginNotRequired
    @RequestMapping("testV")
    public  String testV(@Validated TestBena bena){
        CallResult<String> result = new CallResult<>();
        result.setFailResult("45555");
        return  GsonUtil.gson.toJson(result);
    }
}

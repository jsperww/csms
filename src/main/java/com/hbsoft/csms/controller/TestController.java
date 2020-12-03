package com.hbsoft.csms.controller;

import com.greenpineyu.fel.Expression;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.interpreter.Interpreter;
import com.greenpineyu.fel.optimizer.Interpreters;
import com.greenpineyu.fel.parser.FelNode;
import com.hbsoft.test.dao.service.TestBenaDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class TestController {

    @Autowired
    private TestBenaDaoService testBenaDaoService;

    @RequestMapping("test")
    public String test() {
        return "SUCCESS";
    }

    @RequestMapping("testDB")
    public void testDB(){
        try {
            testBenaDaoService.getAll(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public static void main(String[] args) {
        FelEngine fel = new FelEngineImpl();
        Interpreters interpreters = new Interpreters();
        interpreters.add("+", new Interpreter() {
            @Override
            public Object interpret(FelContext felContext, FelNode felNode) {
                List<FelNode> args = felNode.getChildren();
                Object objectA = args.get(0).eval(felContext);
                Object objectB = args.get(1).eval(felContext);
                BigDecimal a = new BigDecimal(String.valueOf(objectA));
                BigDecimal b = new BigDecimal(String.valueOf(objectB));
                return a.add(b);
            }
        });


        FelContext ctx = fel.getContext();
        ctx.set("a", 1.3);
        ctx.set("b", 2.3);
        String exp = "a+b";

        // 使用自定义解释器作为编译选项进行进行编译
        Expression expObj = fel.compile(exp, null, interpreters);
        Object eval = expObj.eval(ctx);
        System.out.println("eval:" + eval);

        ctx.set("price",4);
       Object result = fel.eval("1<price < 10");
       System.out.println(result);

       System.out.println(1.3+2.3);



   }


}

package com.hbsoft.csms.controller;

import com.greenpineyu.fel.Expression;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.interpreter.Interpreter;
import com.greenpineyu.fel.optimizer.Interpreters;
import com.greenpineyu.fel.parser.FelNode;
import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hbsoft.csms.dao.LoanReportDao;
import com.hbsoft.csms.vo.LoanReportVo;
import com.hbsoft.test.dao.service.TestBenaDaoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class LoanReportController extends ABaseController {

    @Autowired
    LoanReportDao loanReportDao;

    @RequestMapping("loanInfoReport")
    public String loanInfoReport(){
        Map<String, Object> map = new HashMap<>();
        try {
            List<Map<String,Object>> list = loanReportDao.loanInfoReportByTeam(null);
            Map<String,Object> distributeMap = loanReportDao.loanInfoReportTotal();

            if(ObjectUtils.isNotEmpty(distributeMap)){
                map.put("total",distributeMap.get("total"));
                map.put("distribute",distributeMap.get("distribute"));
                map.put("noDistribute",distributeMap.get("noDistribute"));
            }
            if(ObjectUtils.isNotEmpty(list)){
                Map<String,Object> item = new HashMap<>();
                if(ObjectUtils.isNotEmpty(distributeMap)) {
                    item.put("deptName", "合计");
                    item.put("cusNum",distributeMap.get("total"));
                    list.add(item);
                }
                map.put("list",list);
                map.put("code",0);
                map.put("msg","查询成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",400);
            map.put("msg","获取失败");
        }
        return gson.toJson(map);
    }

    @RequestMapping("loanRepayReport")
    public String loanRepayReport(LoanReportVo vo){
        Map<String, Object> map = new HashMap<>();
        try {
            List<Map<String,Object>> list = loanReportDao.loanRepayReportByTeam(vo);
            Map<String,Object> totalMap = loanReportDao.loanRepayReportTotal(vo);
            if(ObjectUtils.isNotEmpty(list)){
                list.add(totalMap);
                map.put("list",list);
                map.put("code",0);
                map.put("msg","查询成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",400);
            map.put("msg","获取失败");
        }
        return gson.toJson(map);
    }

    @RequestMapping("loanVisitReport")
    public String loanVisitReport(LoanReportVo vo){
        Map<String, Object> map = new HashMap<>();
        try {
            List<Map<String,Object>> list = loanReportDao.loanVisitReportByTeam(vo);
            List<Map<String,Object>> loanInfoNumList = loanReportDao.loanInfoReportByTeam(null);
            for(Map<String,Object> visitMap : list){
                for(Map<String,Object> loanInfoMap : loanInfoNumList){
                    if(ObjectUtils.isNotEmpty(visitMap.get("deptName"))){
                        if(visitMap.get("deptName").equals(loanInfoMap.get("deptName"))){
                            visitMap.put("cusNum",loanInfoMap.get("cusNum"));
                        }
                    }
                }
            }
            Map<String,Object> visitTotal = loanReportDao.loanVisitReportTotal(vo);
            Map<String,Object> cusNumTotal = loanReportDao.loanInfoReportTotal();
            if(ObjectUtils.isNotEmpty(list)){
                Map<String,Object> item = new HashMap<>();
                item.put("deptName","合计");
                if(ObjectUtils.isNotEmpty(visitTotal)){
                    item.put("visitNum",visitTotal.get("visitNum"));
                }
                if(ObjectUtils.isNotEmpty(cusNumTotal)){
                    item.put("cusNum",cusNumTotal.get("distribute"));
                }
                list.add(item);
                map.put("list",list);
                map.put("code",0);
                map.put("msg","查询成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",400);
            map.put("msg","获取失败");
        }
        return gson.toJson(map);
    }


    @RequestMapping("loanUpdateReport")
    public String loanUpdateReport(LoanReportVo vo){
        Map<String, Object> map = new HashMap<>();
        try {
            List<Map<String,Object>> list = loanReportDao.loanUpdateReportByTeam(vo);
            List<Map<String,Object>> loanInfoNumList = loanReportDao.loanInfoReportByTeam(null);
            for(Map<String,Object> visitMap : list){
                for(Map<String,Object> loanInfoMap : loanInfoNumList){
                    if(ObjectUtils.isNotEmpty(visitMap.get("deptName"))){
                        if(visitMap.get("deptName").equals(loanInfoMap.get("deptName"))){
                            visitMap.put("cusNum",loanInfoMap.get("cusNum"));
                        }
                    }
                }
            }
            Map<String,Object> updateMap = loanReportDao.loanUpdateReportTotal();
            Map<String,Object> cusNumTotal = loanReportDao.loanInfoReportTotal();

            if(ObjectUtils.isNotEmpty(list)){
                Map<String,Object> item = new HashMap<>();
                item.put("deptName","合计");
                if(ObjectUtils.isNotEmpty(updateMap)){
                    item.put("updateNum",updateMap.get("total"));
                }
                if(ObjectUtils.isNotEmpty(cusNumTotal)){
                    item.put("cusNum",cusNumTotal.get("distribute"));
                }
                list.add(item);
                map.put("list",list);
                map.put("code",0);
                map.put("msg","查询成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",400);
            map.put("msg","获取失败");
        }
        return gson.toJson(map);
    }

}

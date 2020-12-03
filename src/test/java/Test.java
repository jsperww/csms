import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.hb.bean.CallResult;
import com.hb.util.DateUtil;
import com.hbsoft.Application;
import com.hbsoft.csms.bean.IndictmentInfo;
import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.bean.LoanInfoUpdateContent;
import com.hbsoft.csms.bean.LoanRepayBean;
import com.hbsoft.csms.controller.*;
import com.hbsoft.csms.dao.service.IndictmentInfoDaoService;
import com.hbsoft.csms.dao.service.LoanInfoBeanDaoService;
import com.hbsoft.csms.dao.service.LoanRepayBeanDaoService;
import com.hbsoft.csms.dao.service.ProcessBusinessOperatorBeanDaoService;
import com.hbsoft.csms.service.*;
import com.hbsoft.csms.vo.*;
import com.hbsoft.dingding.util.DingDingUtil;
import com.hbsoft.test.dao.service.TestBenaDaoService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class Test {

    @Autowired
    TestBenaDaoService testBenaDaoService;

    @Autowired
    LoanInfoBeanDaoService loanInfoBeanDaoService;


    @Autowired
    LoanRepayService loanRepayService;

    @Autowired
    LoanRepayBeanDaoService loanRepayBeanDaoService;

    @Autowired
    MesageInfoController mesageInfoController;

    @Autowired
    LoanInfoController loanInfoController;

    @Autowired
    LoanInfoService loanInfoService;

    @Autowired
    RepayController repayController;

    @Autowired
    ProcessHandleService processHandleService;

    @Autowired
    ProcessService processService;

    @Autowired
    ProcessBusinessOperatorBeanDaoService processBusinessOperatorBeanDaoService;

    @Autowired
    IndictmentInfoDaoService indictmentInfoDaoService;

    @Autowired
    ProcessBusinessController processBusinessController;

    @Autowired
    LoanWarnController loanWarnController;

    @Autowired
    DingDingUtil dingDingUtil;

    @Autowired
    LoanWarnService loanWarnService;



    @org.junit.Test
    public void test3() throws  Exception{
        RepayVo vo = new RepayVo();
        vo.setLoanContractNum("702566309241552896");
        vo.setRepayCapitalAmount(new BigDecimal("1"));
        vo.setRepayAmount(new BigDecimal("2"));
        vo.setRepayInterestAmount(new BigDecimal("1"));
        loanRepayService.reCountRepay(vo);
    }

    @org.junit.Test
    public void test1() throws  Exception{

        LoanRepayBean loanRepayBean = new LoanRepayBean();
        loanRepayBean.setId_prikey(33);
        loanRepayBean.setLoanContractNum("704433905389797376");
        loanRepayBean.setUserId("cs6");
        loanRepayService.revokeRepay(loanRepayBean);
    }


    @org.junit.Test
    public void test2() throws  Exception{
        RepayLoanSecondVo vo = new RepayLoanSecondVo();
        //vo.setLoanContractNum();
        loanRepayService.rePayLoanSecond(vo);
    }

    @org.junit.Test
    public void test4(){
       /* MesageInfoBean m = new MesageInfoBean();
        String str = mesageInfoController.getMessageCount();
        System.out.println(str);*/
    }

    @org.junit.Test
    public void test5() throws Exception {
        loanInfoService.importLoanInfo("D:\\test\\22.xlsx","1234");
    }

    @org.junit.Test
    public void test6(){
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = fel.getContext();
        ctx.set("结欠本金", 5000);
        ctx.set("还款本金", 12);
        Object capitalObject = fel.eval("结欠本金 + 还款本金");
        System.out.println(capitalObject);
    }

    @org.junit.Test
    public void test7() throws Exception{
        ProcessVo vo = new ProcessVo();
        vo.setNodeId(40);
        vo.setBusinessId("6");
        vo.setNodeSatus("2");
        vo.setProcessAttribute("repay_revoke");
        vo.setUserId("234");
        vo.setBusinessCode("708691220431372288");
        processHandleService.processHandle(vo);
        System.out.println("s");
    }

    @org.junit.Test
    public void tset8() throws  Exception{

        //loanRepayBeanDaoService.getPagingData(new LoanRepayBean());
        LoanInfoVo vo = new LoanInfoVo();
        vo.setOperator("cs6");
        processService.findMyProcessBusinessPagingData(vo);
    }


    @org.junit.Test
    public void tset9() throws  Exception{

        LoanRepayAndProcessInfoVo vo = new LoanRepayAndProcessInfoVo();
       vo.setProcessAttribute("repay_revoke");
       vo.setId_prikey(3);
        String str =  repayController.loanRepayAndProcessInfo(vo);
        System.out.println(str);
    }


    @org.junit.Test
    public void tset10() throws  Exception{


      /*  loanContractNum: 704433889229144064
        repayAmount: 200
        repayCapitalAmount: 100
        repayInterestAmount: 100
        org: 454121
        repayWay: 1
        addRepayDate: 2020-05-13
        remark: 测试*/
        RepayVo vo = new RepayVo();
        vo.setLoanContractNum("708691222075539456");
        vo.setRepayAmount(new BigDecimal(2));
        vo.setRepayCapitalAmount(new BigDecimal(1));
        vo.setRepayInterestAmount(new BigDecimal(1));
        //vo.setRepayHeXiaoLeftInterestAmount(new BigDecimal(100));
        vo.setOrg("454121");
        vo.setRepayWay(1);
        vo.setAddRepayDate(DateUtil.getDate("2020-05-20"));
        vo.setRemark("测试");
        loanRepayService.addRepay(vo,"234");

    }

    @org.junit.Test
    public  void test11() throws  Exception{
        LoanRepayBean loanRepayBean = new LoanRepayBean();
        loanRepayBean.setId_prikey(27);
        loanRepayBean.setLoanContractNum("704433941439840256");
        loanRepayBean.setNodeId(40);
        loanRepayBean.setOperateType(1);
        loanRepayBean.setUserId("122");
        loanRepayService.examRevokeRepay(loanRepayBean);
    }

    @org.junit.Test
    public  void  test12() {
        RepayVo vo = new RepayVo();
        vo.setRepayInterestAmount(new BigDecimal(1000));
        vo.setRepayCapitalAmount(new BigDecimal(1000));
        vo.setOrg("454122");
        vo.setRepayWay(1);
        vo.setAddRepayDate(DateUtil.getDate("2020-05-22"));
        vo.setLoanContractNum("708691235811885056");
        repayController.reCountAddRepay(vo);
    }

    @org.junit.Test
    public void test13() throws  Exception{
        RepayAddVo vo = new RepayAddVo();
        vo.setProcessStatus("2");
        vo.setNodeId("70");
        vo.setId_prikey("14");
        loanRepayService.addRepayCheck(vo,"sdf");
    }
    @org.junit.Test
    public void test14() throws  Exception{
        LoanInfoUpdateContent liuc = new LoanInfoUpdateContent();
        liuc.setId_prikey(3);
        liuc.setLoanContractNum("708691223975559168");
        liuc.setNodeId(10);
        liuc.setStatus("2");


        loanInfoService.loanUpdateAudit2(liuc,"ss");
    }

    @org.junit.Test
    public void test15() throws Exception{
        loanInfoBeanDaoService.getPagingData(new LoanInfoBean());
    }

    @org.junit.Test
    public void test16() throws  Exception{
        IndictmentInfo indictmentParam = new IndictmentInfo();
        indictmentParam.setLoanContractNum("ssssss");
        List<IndictmentInfo> indictmentList =  indictmentInfoDaoService.getAll(indictmentParam);
        System.out.println("sdfds");
    }

    @org.junit.Test
    public void test17() {
        /*ProcessHandleVo vo = new ProcessHandleVo();
        vo.setId_prikey("80");
        vo.setNodeId("120");
        vo.setProcessStatus(ProcessNodeStatusEnum.PROCESS_NODE_STATUS_YES.getCode());
        String str = processBusinessController.claimProcessHandle(vo,null);
        System.out.println(str);*/
    }

    @org.junit.Test
    public void test18() throws Exception {
        CallResult callResult = loanInfoService.updateLoanInfoShare(1258, "123");
        System.out.println(callResult.getMsg());
    }

   @org.junit.Test
    public void test19() throws Exception{
       loanWarnService.sendWarnMessage();
    }
}

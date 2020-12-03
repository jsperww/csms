package com.hbsoft.csms.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hb.bean.CallResult;
import com.hb.exception.SqlNotChangeException;
import com.hb.util.*;
import com.hbsoft.common.constant.Constant;
import com.hbsoft.common.enumtype.*;
import com.hbsoft.config.SysConfigInfo;
import com.hbsoft.csms.bean.*;
import com.hbsoft.csms.dao.CommonDao;
import com.hbsoft.csms.dao.DictBeanDao;
import com.hbsoft.csms.dao.HbcmUserDao;
import com.hbsoft.csms.dao.service.*;
import com.hbsoft.csms.vo.ProcessVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: yj
 * @Date: 2020/3/28 10:39
 */
@Service
public class LoanInfoService<resul> {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    LoanInfoBeanDaoService loanInfoBeanDaoService;
    @Autowired
    LoanDistributeBeanDaoService loanDistributeBeanDaoService;
    @Autowired
    LoanDistributeOrgVillvgeBeanDaoService loanDistributeOrgVillvgeBeanDaoService;
    @Autowired
    LoanGuaranteeDaoService loanGuaranteeDaoService;
    @Autowired
    LoanMortgageDaoService loanMortgageDaoService;
    @Autowired
    UpdateLogBeanDaoService updateLogBeanDaoService;
    @Autowired
    DictBeanDao dictBeanDao;
    @Autowired
    LoanRepayBeanDaoService loanRepayBeanDaoService;
    @Autowired
    LoanVisitBeanDaoService loanVisitBeanDaoService;
    @Autowired
    LoanDistributeLogBeanDaoService loanDistributeLogBeanDaoService;
    @Autowired
    LoanInfoImportErrorDaoService loanInfoImportErrorDaoService;
    @Autowired
    LoanDistributeOrgBankBeanDaoService loanDistributeOrgBankBeanDaoService;
    @Autowired
    LoanInfoUpdateContentDaoService loanInfoUpdateContentDaoService;
    @Autowired
    LoanFileInfoBeanDaoService loanFileInfoBeanDaoService;
    @Autowired
    ProcessHandleService processHandleService;
    @Autowired
    IndictmentInfoDaoService indictmentInfoDaoService;
    @Autowired
    LoanDistributeSourcesBeanDaoService loanDistributeSourcesBeanDaoService;
    @Autowired
    CommonDao commonDao;
    @Autowired
    LoanInfoFileService loanInfoFileService;
    @Autowired
    SysConfigInfo sysConfigInfo;
    @Autowired
    HbcmUserDao hbcmUserDao;

    @Transactional
    public CallResult<String> addLoanInfoAndSource(LoanInfoBean loanInfoBean, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();
        loanInfoBean.setLoanContractNum(String.valueOf(SWId.getInstance().nextId()));
        loanInfoBean.setIdNum(V1Pass.pass_encode(loanInfoBean.getIdNum()));
        loanInfoBean.setMobile(V1Pass.pass_encode(loanInfoBean.getMobile()));
        loanInfoBean.setName(V1Pass.pass_encode(loanInfoBean.getName()));
        loanInfoBean.setCreateOn(new Date());
        loanInfoBean.setCreateBy(userId);
        loanInfoBean.setUpdateOn(new Date());
        loanInfoBean.setUpdateBy(userId);
        loanInfoBean.setImportDate(new Date());

        loanInfoBeanDaoService.addPrikey(loanInfoBean);
        if(StringUtils.isNotBlank(loanInfoBean.getHomeAddrImg())){
            loanInfoFileService.handleLoanFile(loanInfoBean.getHomeAddrImg(),loanInfoBean.getId_prikey(),userId,LoanFileTypeEnum.LOAN_FILE_TYPE_ADDR_HOME.getCode());
        }
        if(StringUtils.isNotBlank(loanInfoBean.getCompanyAddrImg())){
            loanInfoFileService.handleLoanFile(loanInfoBean.getHomeAddrImg(),loanInfoBean.getId_prikey(),userId,LoanFileTypeEnum.LOAN_FILE_TYPE_ADDR_COMPANY.getCode());
        }

        if(StringUtils.isNotBlank(loanInfoBean.getOtherAddrImg())){
            loanInfoFileService.handleLoanFile(loanInfoBean.getOtherAddrImg(),loanInfoBean.getId_prikey(),userId,LoanFileTypeEnum.LOAN_FILE_TYPE_ADDR_OTHER.getCode());
        }
        loanInfoBeanDaoService.addSourceLoan(loanInfoBean);

        if (LoanStatusEnum.LOAN_STATUS_PROSECUTION.getCode().equals(String.valueOf(loanInfoBean.getLoanStatus()))) {
            IndictmentInfo indictmentInfo = new IndictmentInfo();
            indictmentInfo.setLoanContractNum(loanInfoBean.getLoanContractNum());
            indictmentInfo.setCreateBy(userId);
            indictmentInfo.setCreateOn(new Date());
            indictmentInfoDaoService.add(indictmentInfo);
        }
        List<LoanGuarantee> mateList = new Gson().fromJson(loanInfoBean.getMates(), new TypeToken<List<LoanGuarantee>>() {
        }.getType());
        for (LoanGuarantee mate : mateList) {
            mate.setRelType(LoanGuaranteeRelEnum.LOAN_GUARANTEE_REL_MATE.getCode());
            mate.setCreateBy(userId);
            mate.setCreateOn(new Date());
            mate.setUpdateBy(userId);
            mate.setUpdateOn(new Date());
            mate.setLoanContractNum(loanInfoBean.getLoanContractNum());
            loanGuaranteeDaoService.add(mate);
        }

        List<LoanGuarantee> guaranteeList = new Gson().fromJson(loanInfoBean.getGuarantees(), new TypeToken<List<LoanGuarantee>>() {
        }.getType());
        for (LoanGuarantee loanGuarantee : guaranteeList) {
            loanGuarantee.setRelType(LoanGuaranteeRelEnum.LOAN_GUARANTEE_REL_LOANGUARANTEE.getCode());
            loanGuarantee.setCreateBy(userId);
            loanGuarantee.setCreateOn(new Date());
            loanGuarantee.setUpdateBy(userId);
            loanGuarantee.setUpdateOn(new Date());
            loanGuarantee.setLoanContractNum(loanInfoBean.getLoanContractNum());
            loanGuaranteeDaoService.add(loanGuarantee);
        }

        List<LoanMortgage> mortgageList = new Gson().fromJson(loanInfoBean.getMortgages(), new TypeToken<List<LoanMortgage>>() {
        }.getType());
        for (LoanMortgage mortgage : mortgageList) {
            mortgage.setCreateBy(userId);
            mortgage.setCreateOn(new Date());
            mortgage.setUpdateBy(userId);
            mortgage.setUpdateOn(new Date());
            mortgage.setLoanContractNum(loanInfoBean.getLoanContractNum());
            loanMortgageDaoService.add(mortgage);
        }

        result.setSuccessResult();
        return result;
    }

    @Transactional
    public CallResult<String> updateLoanInfo(LoanInfoBean loanInfoBean, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();
        Gson gson = new Gson();
        LoanInfoBean loanInfoBeanSource = loanInfoBeanDaoService.getById(loanInfoBean.getId_prikey());
        if (ObjectUtils.isEmpty(loanInfoBeanSource)) {
            result.setFailResult("主键有误");
            return result;
        }
        String loanContractNum = loanInfoBeanSource.getLoanContractNum();
        loanInfoBean.setIdNum(V1Pass.pass_encode(loanInfoBean.getIdNum()));
        loanInfoBean.setMobile(V1Pass.pass_encode(loanInfoBean.getMobile()));
        loanInfoBean.setName(V1Pass.pass_encode(loanInfoBean.getName()));

        UpdateLogBean log = new UpdateLogBean();
        CallResult<LoanInfoBean> selectLoanInfo = selectLoanInfo(loanContractNum);
        if (selectLoanInfo.getCode() != 0) {
            result.setFailResult(selectLoanInfo.getMsg());
            return result;
        }

        log.setCreateBy(userId);
        log.setCreateOn(new Date());
        log.setOldContent(gson.toJson(selectLoanInfo.getData()));
        log.setNewContent(gson.toJson(loanInfoBean));
        updateLogBeanDaoService.add(log);

        if(StringUtils.isNotBlank(loanInfoBean.getHomeAddrImg())){
            loanInfoFileService.handleLoanFile(loanInfoBean.getHomeAddrImg(),loanInfoBean.getId_prikey(),userId,LoanFileTypeEnum.LOAN_FILE_TYPE_ADDR_HOME.getCode());
        }
        if(StringUtils.isNotBlank(loanInfoBean.getCompanyAddrImg())){
            loanInfoFileService.handleLoanFile(loanInfoBean.getCompanyAddrImg(),loanInfoBean.getId_prikey(),userId,LoanFileTypeEnum.LOAN_FILE_TYPE_ADDR_COMPANY.getCode());
        }

        if(StringUtils.isNotBlank(loanInfoBean.getOtherAddrImg())){
            loanInfoFileService.handleLoanFile(loanInfoBean.getOtherAddrImg(),loanInfoBean.getId_prikey(),userId,LoanFileTypeEnum.LOAN_FILE_TYPE_ADDR_OTHER.getCode());
        }
        loanInfoBeanDaoService.set(loanInfoBean);

        if (LoanStatusEnum.LOAN_STATUS_PROSECUTION.getCode().equals(String.valueOf(loanInfoBean.getLoanStatus()))) {
            IndictmentInfo indictmentParam = new IndictmentInfo();
            indictmentParam.setLoanContractNum(loanContractNum);
            List<IndictmentInfo> indictmentList = indictmentInfoDaoService.getAll(indictmentParam);
            if (indictmentList != null && indictmentList.size() == 0) {
                IndictmentInfo indictmentInfo = new IndictmentInfo();
                indictmentInfo.setLoanContractNum(loanContractNum);
                indictmentInfo.setCreateBy(userId);
                indictmentInfo.setCreateOn(new Date());
                indictmentInfoDaoService.add(indictmentInfo);
            }
        }

        List<LoanGuarantee> mateList = new Gson().fromJson(loanInfoBean.getMates(),
                new TypeToken<List<LoanGuarantee>>() {
                }.getType());
        if (null != mateList && mateList.size() != 0) {
            for (LoanGuarantee mate : mateList) {
                if (mate.getId_prikey() != null && mate.getDelFlag() == Constant.DELFLAG) {
                    loanGuaranteeDaoService.removeOne(mate.getId_prikey());
                } else if (mate.getId_prikey() != null && mate.getDelFlag() != Constant.DELFLAG) {
                    mate.setUpdateOn(new Date());
                    mate.setUpdateBy(userId);
                    loanGuaranteeDaoService.set(mate);
                } else if (mate.getId_prikey() == null) {
                    mate.setRelType(LoanGuaranteeRelEnum.LOAN_GUARANTEE_REL_MATE.getCode());
                    mate.setCreateBy(userId);
                    mate.setCreateOn(new Date());
                    mate.setUpdateBy(userId);
                    mate.setUpdateOn(new Date());
                    mate.setLoanContractNum(loanInfoBeanSource.getLoanContractNum());
                    loanGuaranteeDaoService.add(mate);
                }
            }
        }

        List<LoanGuarantee> guaranteeList = new Gson().fromJson(loanInfoBean.getGuarantees(),
                new TypeToken<List<LoanGuarantee>>() {
                }.getType());
        if (null != guaranteeList && guaranteeList.size() != 0) {
            for (LoanGuarantee loanGuarantee : guaranteeList) {
                if (loanGuarantee.getId_prikey() != null && loanGuarantee.getDelFlag() == Constant.DELFLAG) {
                    loanGuaranteeDaoService.removeOne(loanGuarantee.getId_prikey());
                } else if (loanGuarantee.getId_prikey() != null && loanGuarantee.getDelFlag() != Constant.DELFLAG) {
                    loanGuarantee.setUpdateBy(userId);
                    loanGuarantee.setUpdateOn(new Date());
                    loanGuaranteeDaoService.set(loanGuarantee);
                } else if (loanGuarantee.getId_prikey() == null) {
                    loanGuarantee.setRelType(LoanGuaranteeRelEnum.LOAN_GUARANTEE_REL_LOANGUARANTEE.getCode());
                    loanGuarantee.setCreateBy(userId);
                    loanGuarantee.setCreateOn(new Date());
                    loanGuarantee.setUpdateBy(userId);
                    loanGuarantee.setUpdateOn(new Date());
                    loanGuarantee.setLoanContractNum(loanInfoBeanSource.getLoanContractNum());
                    loanGuaranteeDaoService.add(loanGuarantee);
                }
            }
        }

        List<LoanMortgage> mortgageList = new Gson().fromJson(loanInfoBean.getMortgages(),
                new TypeToken<List<LoanMortgage>>() {
                }.getType());
        if (null != mortgageList && mortgageList.size() != 0) {
            for (LoanMortgage mortgage : mortgageList) {
                if (mortgage.getId_prikey() != null && mortgage.getDelFlag() == Constant.DELFLAG) {
                    loanMortgageDaoService.removeOne(mortgage.getId_prikey());
                } else if (mortgage.getId_prikey() != null && mortgage.getDelFlag() != Constant.DELFLAG) {
                    mortgage.setUpdateBy(userId);
                    mortgage.setUpdateOn(new Date());
                    loanMortgageDaoService.set(mortgage);
                } else if (mortgage.getId_prikey() == null) {
                    mortgage.setCreateBy(userId);
                    mortgage.setCreateOn(new Date());
                    mortgage.setUpdateBy(userId);
                    mortgage.setUpdateOn(new Date());
                    mortgage.setLoanContractNum(loanInfoBeanSource.getLoanContractNum());
                    loanMortgageDaoService.add(mortgage);
                }
            }
        }
        result.setSuccessResult();
        return result;
    }

    @Transactional
    public CallResult<String> delBacthLoanInfo(List<Integer> ids, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();
        Integer i = 0;
        for (Integer id : ids) {
            CallResult<String> delLoanInfo = delLoanInfo(id, userId);
            if (delLoanInfo.getCode() != 0) {
                i++;
            }
        }
        if (i == 0) {
            result.setSuccessResult();
        } else {
            result.setCode(0);
            result.setMsg("有部分数据已还款或已外访未删除");
        }
        return result;
    }

    @Transactional
    public CallResult<String> delLoanInfo(Integer id_prikey, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();
        LoanInfoBean lib = new LoanInfoBean();
        LoanDistributeBean ldb = new LoanDistributeBean();
        LoanGuarantee lg = new LoanGuarantee();
        LoanMortgage lm = new LoanMortgage();
        LoanVisitBean lvb = new LoanVisitBean();
        UpdateLogBean log = new UpdateLogBean();
        LoanRepayBean lrb = new LoanRepayBean();
        LoanInfoBean loanInfoById = loanInfoBeanDaoService.getById(id_prikey);
        if (loanInfoById == null) {
            result.setFailResult("主键有误");
            return result;
        }
        String loanContractNum = loanInfoById.getLoanContractNum();

        // 有还款记录不能删除
        lrb.setLoanContractNum(loanContractNum);
        List<LoanRepayBean> list = loanRepayBeanDaoService.getAll(lrb);
        if (list != null && list.size() != 0) {
            result.setFailResult("客户有还款记录，不能删除");
            return result;
        }

        // 有外放记录不能删除
        lvb.setLoanContractNum(loanContractNum);
        List<LoanVisitBean> list2 = loanVisitBeanDaoService.getAll(lvb);
        if (list2 != null && list2.size() != 0) {
            result.setFailResult("客户有外放记录，不能删除");
            return result;
        }

        // 添加updateLog
        Gson gson = new Gson();
        CallResult<LoanInfoBean> selectLoanInfo = selectLoanInfo(loanContractNum);
        if (selectLoanInfo.getCode() != 0) {
            result.setFailResult(selectLoanInfo.getMsg());
            return result;
        }
        log.setType(UpdateLogTypeEnum.UPDATElOG_TYPE_DELETE.getCode());
        log.setCreateBy(userId);
        log.setCreateOn(new Date());
        log.setOldContent(gson.toJson(selectLoanInfo.getData()));
        updateLogBeanDaoService.add(log);

        // 删除loanInfo
        lib.setLoanContractNum(loanContractNum);
        loanInfoBeanDaoService.remove(lib);

        // 删除LoanDistribute
        ldb.setLoanContractNum(loanContractNum);
        List<LoanDistributeBean> all = loanDistributeBeanDaoService.getAll(ldb);
        if (all != null && all.size() != 0) {
            loanDistributeBeanDaoService.remove(ldb);
        }

        // 删除LoanGuarantee
        lg.setLoanContractNum(loanContractNum);
        List<LoanGuarantee> all2 = loanGuaranteeDaoService.getAll(lg);
        if (all2 != null && all2.size() != 0) {
            loanGuaranteeDaoService.remove(lg);
        }

        // 删除LoanMortgage
        lm.setLoanContractNum(loanContractNum);
        List<LoanMortgage> all3 = loanMortgageDaoService.getAll(lm);
        if (all3 != null && all3.size() != 0) {
            loanMortgageDaoService.remove(lm);
        }

        result.setSuccessResult();
        return result;
    }

    public LoanInfoBean getLoanInfoByLoanContractNum(String loanContractNum) throws Exception {
        if (StringUtils.isEmpty(loanContractNum)) {
            return null;
        }
        LoanInfoBean param = new LoanInfoBean();
        param.setLoanContractNum(loanContractNum);
        return loanInfoBeanDaoService.getByField(param);
    }

    @Transactional
    public CallResult distributeLoanInfoBatch(LoanDistributeBean loanDistributeBean, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();
        String[] loanContractNums = loanDistributeBean.getLoanContractNum().split(";");
        for (String loanContractNum : loanContractNums) {
            LoanInfoBean loanInfoBean = getLoanInfoByLoanContractNum(loanContractNum);
            if (ObjectUtils.isNotEmpty(loanInfoBean)) {
                LoanDistributeBean param = new LoanDistributeBean();
                param.setNumber(loanDistributeBean.getNumber());
                param.setLoanContractNum(loanContractNum);
                param.setNumberType(loanDistributeBean.getNumberType());
                distributeLoanInfo(param, userId);
            }
        }
        result.setSuccessResult();
        return result;
    }

    @Transactional
    public CallResult distributeLoanInfo(LoanDistributeBean loanDistributeBean, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();
        if (StringUtils.isBlank(loanDistributeBean.getNumberType())) {
            result.setFailResult("编号类型有误");
            return result;
        }
        LoanDistributeBean ldParam = new LoanDistributeBean();
        ldParam.setLoanContractNum(loanDistributeBean.getLoanContractNum());
        LoanDistributeBean ldValidate = loanDistributeBeanDaoService.getByField(ldParam);
        if (ObjectUtils.isEmpty(ldValidate)) {
            loanDistributeBean.setUpdateBy(userId);
            loanDistributeBean.setUpdateOn(new Date());
            loanDistributeBean.setCreateOn(new Date());
            loanDistributeBean.setCreateBy(userId);
        } else {
            loanDistributeBeanDaoService.removeOne(ldValidate.getId_prikey());
            loanDistributeBean.setUpdateBy(userId);
            loanDistributeBean.setUpdateOn(new Date());
            loanDistributeBean.setCreateOn(ldValidate.getCreateOn());
            loanDistributeBean.setCreateBy(ldValidate.getCreateBy());
        }
        LoanDistributeLogBean ldlb = new LoanDistributeLogBean();
        ldlb.setLoanContractNum(loanDistributeBean.getLoanContractNum());
        ldlb.setNumber(loanDistributeBean.getNumber());
        ldlb.setNumberType(loanDistributeBean.getNumberType());
        ldlb.setOperationType(loanDistributeBean.getNumberType());
        ldlb.setCreateBy(userId);
        ldlb.setCreateOn(new Date());
        loanDistributeBeanDaoService.add(loanDistributeBean);
        loanDistributeLogBeanDaoService.add(ldlb);
        result.setSuccessResult();
        return result;
    }

    @Transactional
    public CallResult distributeLoanArea(String areaCode, String number, String ditributeType, String userId)
            throws Exception {
        CallResult<String> result = new CallResult<>();
        Date date = new Date();
        // 删除所选组所有整村分配
        loanDistributeBeanDaoService.delAllDistributeArea(number);
        // 删除所选组所有LoanDistributeOrgVillvgeBean
        LoanDistributeOrgVillvgeBean village = new LoanDistributeOrgVillvgeBean();
        village.setOrganizationNum(number);
        List<LoanDistributeOrgVillvgeBean> all = loanDistributeOrgVillvgeBeanDaoService.getAll(village);
        if (all != null && all.size() != 0) {
            loanDistributeOrgVillvgeBeanDaoService.remove(village);
        }

        String[] split = areaCode.split(";");
        for (String s : split) {
            if (s.length() != 12) {
                result.setFailResult("区域代码有误");
                return result;
            }
            LoanDistributeOrgVillvgeBean ldov = new LoanDistributeOrgVillvgeBean();
            ldov.setAreaNumber(s);
            ldov.setOrganizationNum(number);
            ldov.setNumberType(ditributeType);
            loanDistributeOrgVillvgeBeanDaoService.add(ldov);

            LoanInfoBean loanInfoBean = new LoanInfoBean();
            loanInfoBean.setDistributeFlag(DistributeEnum.DISTRIBUTE_ENUM_NO.getCode());
            loanInfoBean.setVillage(s);
            List<LoanInfoBean> list = loanInfoBeanDaoService.getAllData(loanInfoBean);
            if (list != null && list.size() != 0) {
                LoanDistributeBean ldb = new LoanDistributeBean();
                for (LoanInfoBean lib : list) {
                    ldb.setLoanContractNum(lib.getLoanContractNum());
                    List<LoanDistributeBean> ldbList = loanDistributeBeanDaoService.getAll(ldb);
                    if (null == ldbList || ldbList.size() == 0) {
                        ldb.setNumber(number);
                        ldb.setNumberType(ditributeType);
                        ldb.setOrgNum(s);
                        ldb.setCreateOn(date);
                        ldb.setCreateBy(userId);
                        loanDistributeBeanDaoService.add(ldb);
                        LoanDistributeLogBean ldlb = new LoanDistributeLogBean();
                        ldlb.setLoanContractNum(lib.getLoanContractNum());
                        ldlb.setNumber(number);
                        ldlb.setNumberType(ditributeType);
                        ldlb.setOperationType(DistributeOperationTypeEnum.DISTRIBUTE_VILLAGE.getCode());
                        ldlb.setCreateBy(userId);
                        ldlb.setCreateOn(date);
                        loanDistributeLogBeanDaoService.add(ldlb);
                    } else {
                        // 查询未分配客户 实际该客户已分配
                        throw new SqlNotChangeException("分配错误");
                    }
                }
            }
        }
        result.setSuccessResult();
        return result;
    }

    @Transactional
    public CallResult distributeLoanBank(String bankCode, String number, String ditributeType, String userId)
            throws Exception {
        CallResult<String> result = new CallResult<>();
        Date date = new Date();
        // 删除所选组所有支行分配
        loanDistributeBeanDaoService.delAllDistributeBank(number);
        // 删除所选组的所有LoanDistributeOrgBankBean
        LoanDistributeOrgBankBean bank = new LoanDistributeOrgBankBean();
        bank.setOrganizationNum(number);
        List<LoanDistributeOrgBankBean> all = loanDistributeOrgBankBeanDaoService.getAll(bank);
        if (all != null && all.size() != 0) {
            loanDistributeOrgBankBeanDaoService.remove(bank);
        }

        String[] split = bankCode.split(";");
        for (String s : split) {
            if (s.length() != 6) {
                result.setFailResult("银行代码有误");
                return result;
            }
            LoanDistributeOrgBankBean ldob = new LoanDistributeOrgBankBean();
            ldob.setBankNumber(s);
            ldob.setOrganizationNum(number);
            ldob.setNumberType(ditributeType);
            loanDistributeOrgBankBeanDaoService.add(ldob);

            LoanInfoBean loanInfoBean = new LoanInfoBean();
            loanInfoBean.setDistributeFlag(DistributeEnum.DISTRIBUTE_ENUM_NO.getCode());
            loanInfoBean.setOrg(s);
            List<LoanInfoBean> list = loanInfoBeanDaoService.getAllData(loanInfoBean);
            if (list != null && list.size() != 0) {
                LoanDistributeBean ldb = new LoanDistributeBean();
                for (LoanInfoBean lib : list) {
                    ldb.setLoanContractNum(lib.getLoanContractNum());
                    List<LoanDistributeBean> ldbList = loanDistributeBeanDaoService.getAll(ldb);
                    if (null == ldbList || ldbList.size() == 0) {
                        ldb.setNumber(number);
                        ldb.setNumberType(ditributeType);
                        ldb.setOrgNum(s);
                        ldb.setCreateOn(date);
                        ldb.setCreateBy(userId);
                        loanDistributeBeanDaoService.add(ldb);
                        LoanDistributeLogBean ldlb = new LoanDistributeLogBean();
                        ldlb.setLoanContractNum(lib.getLoanContractNum());
                        ldlb.setNumber(number);
                        ldlb.setNumberType(ditributeType);
                        ldlb.setOperationType(DistributeOperationTypeEnum.DISTRIBUTE_BANK.getCode());
                        ldlb.setCreateBy(userId);
                        ldlb.setCreateOn(date);
                        loanDistributeLogBeanDaoService.add(ldlb);
                    } else {
                        // 查询未分配客户 实际该客户已分配
                        throw new SqlNotChangeException("分配错误");
                    }
                }
            }
        }
        result.setSuccessResult();
        return result;
    }


    public CallResult<LoanInfoBean> selectLoanInfo(String loanContractNum) throws Exception {
        CallResult<LoanInfoBean> result = new CallResult<>();

        if (StringUtils.isNotEmpty(loanContractNum)) {
            LoanInfoBean loanInfoBean = getLoanInfoByLoanContractNum(loanContractNum);
            loanInfoBean.setMobile(V1Pass.pass_decode(loanInfoBean.getMobile()));
            loanInfoBean.setName(V1Pass.pass_decode(loanInfoBean.getName()));
            loanInfoBean.setIdNum(V1Pass.pass_decode(loanInfoBean.getIdNum()));
            if (loanInfoBean.getCollectionDate() != null) {
                loanInfoBean.setCollectionDateStr(DateUtil.formatDate(loanInfoBean.getCollectionDate(), "yyyy-MM-dd"));
            } else {
                loanInfoBean.setCollectionDateStr("");
            }
            if (loanInfoBean.getLoanBeginDate() != null) {
                loanInfoBean.setLoanBeginDateStr(DateUtil.formatDate(loanInfoBean.getLoanBeginDate(), "yyyy-MM-dd"));
            }
            if (loanInfoBean.getLoanEndDate() != null) {
                loanInfoBean.setLoanEndDateStr(DateUtil.formatDate(loanInfoBean.getLoanEndDate(), "yyyy-MM-dd"));
            }
            if (loanInfoBean.getDistributeFlag() != null) {
                loanInfoBean.setDistributeFlagInt(Integer.valueOf(loanInfoBean.getDistributeFlag()));
            }
            if (YesOrNoEnum.YES_OR_NO_YES.getCode().equals(loanInfoBean.getDistributeFlag())) {
                loanInfoBean.setIsDistrue(YesOrNoEnum.YES_OR_NO_YES.getName());
            }
            if (YesOrNoEnum.YES_OR_NO_NO.getCode().equals(loanInfoBean.getDistributeFlag())) {
                loanInfoBean.setIsDistrue(YesOrNoEnum.YES_OR_NO_NO.getName());
            }
            if (loanInfoBean != null) {
                //抵押物
               LoanMortgage loanMortgage = new LoanMortgage();
                loanMortgage.setLoanContractNum(loanInfoBean.getLoanContractNum());
                loanInfoBean.setMortgageList(loanMortgageDaoService.getAll(loanMortgage));
                //担保人
                LoanGuarantee loanGuarantee = new LoanGuarantee();
                loanGuarantee.setLoanContractNum(loanInfoBean.getLoanContractNum());
                loanGuarantee.setRelType(LoanGuaranteeRelEnum.LOAN_GUARANTEE_REL_LOANGUARANTEE.getCode());
                loanInfoBean.setGuaranteeList(loanGuaranteeDaoService.getAll(loanGuarantee));
                //配偶
                LoanGuarantee loanMate = new LoanGuarantee();
                loanMate.setLoanContractNum(loanInfoBean.getLoanContractNum());
                loanMate.setRelType(LoanGuaranteeRelEnum.LOAN_GUARANTEE_REL_MATE.getCode());
                loanInfoBean.setMateList(loanGuaranteeDaoService.getAll(loanMate));

                result.setSuccessResult(loanInfoBean);
            } else {
                result.setFailResult("暂无数据");
            }
        } else {
            result.setFailResult("主键参数错误");
        }
        return result;
    }


   /* @Transactional
    public CallResult<String> ddUpdateLoanInfo(LoanInfoBean loanInfoBean) throws Exception {
        CallResult result = new CallResult<>();
        if (ObjectUtils.isEmpty(loanInfoBean.getId_prikey())) {
            result.setFailResult("参数有误");
            return result;
        }
        LoanInfoBean loan = loanInfoBeanDaoService.getById(loanInfoBean.getId_prikey());
        if (ObjectUtils.isEmpty(loan)) {
            result.setFailResult("参数有误");
            return result;
        }
        //更新客户信息
        loanInfoBean.setIdNum(V1Pass.pass_encode(loanInfoBean.getIdNum()));
        loanInfoBean.setMobile(V1Pass.pass_encode(loanInfoBean.getMobile()));
        loanInfoBean.setName(V1Pass.pass_encode(loanInfoBean.getName()));
        loanInfoBean.setUpdateOn(new Date());
        loanInfoBean.setUpdateBy(loanInfoBean.getUserId());
        loanInfoBeanDaoService.set(loanInfoBean);
        if (LoanStatusEnum.LOAN_STATUS_PROSECUTION.getCode().equals(String.valueOf(loanInfoBean.getLoanStatus()))) {
            IndictmentInfo indictmentParam = new IndictmentInfo();
            indictmentParam.setLoanContractNum(loan.getLoanContractNum());
            List<IndictmentInfo> indictmentList = indictmentInfoDaoService.getAll(indictmentParam);
            if (indictmentList != null && indictmentList.size() == 0) {
                IndictmentInfo indictmentInfo = new IndictmentInfo();
                indictmentInfo.setLoanContractNum(loan.getLoanContractNum());
                indictmentInfo.setCreateBy(loanInfoBean.getUserId());
                indictmentInfo.setCreateOn(new Date());
                indictmentInfoDaoService.add(indictmentInfo);
            }
        }
        //更新担保人信息
        List<LoanGuarantee> mateList = new Gson().fromJson(loanInfoBean.getMates(), new TypeToken<List<LoanGuarantee>>() {
        }.getType());
        for (LoanGuarantee mate : mateList) {
            mate.setUpdateBy(loanInfoBean.getUserId());
            mate.setUpdateOn(new Date());
            loanGuaranteeDaoService.set(mate);
        }

        //更新配偶信息
        List<LoanGuarantee> guaranteeList = new Gson().fromJson(loanInfoBean.getGuarantees(), new TypeToken<List<LoanGuarantee>>() {
        }.getType());
        for (LoanGuarantee loanGuarantee : guaranteeList) {
            loanGuarantee.setUpdateBy(loanInfoBean.getUserId());
            loanGuarantee.setUpdateOn(new Date());
            loanGuaranteeDaoService.set(loanGuarantee);
        }

        result.setSuccessResult("修改成功");
        return result;
    }*/


    @Transactional
    public CallResult<String> importLoanInfo(String path, String userId) throws Exception {
        CallResult result = new CallResult<>();
        if (StringUtils.isBlank(path)) {
            result.setFailResult("文件未上传成功");
            return result;
        }

        CallResult<List<Map<String, Object>>> excelResult = ExcelUtil.readExcel(path, true, 0, 1);
        if (excelResult.isExec()) {
            for (Map map : excelResult.getData()) {
                LoanInfoBean loanInfo = new LoanInfoBean();
                loanInfo.setLoanContractNum(String.valueOf(SWId.getInstance().nextId()));

                if (map.get("借款人身份证号") == null) {
                    //continue;
                }
                String idNumb = String.valueOf(map.get("借款人身份证号"));
                if(StringUtils.isNotBlank(idNumb) && !"null".equals(idNumb)){
                    if (idNumb.length() == 18 || idNumb.length() == 15) {
                        loanInfo.setIdType("1");
                    } else {
                        loanInfo.setIdType("3");
                    }
                    loanInfo.setIdNum(V1Pass.pass_encode(idNumb));
                }
                loanInfo.setLoanAccountNum(String.valueOf(map.get("贷款账号")));
                LoanInfoImportError loanInfoImportError = new LoanInfoImportError();
                loanInfoImportError.setIdNum(idNumb);
                loanInfoImportError.setLoanAccountNum(loanInfo.getLoanAccountNum());
                loanInfoImportError.setImportStatus("未导入主体信息");
                //验证 贷款账号
                LoanInfoBean param = new LoanInfoBean();
                if(LoanBankEnum.LOAN_BANK_454_ENUM.getCode().equals(sysConfigInfo.getLoanBank())){
                    param.setLoanAccountNum(loanInfo.getLoanAccountNum());
                }else if(LoanBankEnum.LOAN_BANK_463_ENUM.getCode().equals(sysConfigInfo.getLoanBank())){
                    String receiptNo = String.valueOf(map.get("借据号"));
                    if(StringUtils.isBlank(receiptNo)){
                        loanInfoImportError.setRemark("借据号为空");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }else {
                        param.setReceiptNo(receiptNo);
                    }
                    loanInfo.setReceiptNo(receiptNo);
                }
                LoanInfoBean vloanInfoBean = loanInfoBeanDaoService.getByField(param);
                if (ObjectUtils.isNotEmpty(vloanInfoBean)) {
                    if(LoanBankEnum.LOAN_BANK_454_ENUM.getCode().equals(sysConfigInfo.getLoanBank())){
                        loanInfoImportError.setRemark("贷款账号已存在");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }else if(LoanBankEnum.LOAN_BANK_463_ENUM.getCode().equals(sysConfigInfo.getLoanBank())){
                        loanInfoImportError.setRemark("借据号已存在");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                }
                logger.info(loanInfo.getLoanAccountNum());
                if(StringUtils.isNotBlank(String.valueOf(map.get("借款人"))) && !"null".equals(String.valueOf(map.get("借款人")))){
                    loanInfo.setName(String.valueOf(map.get("借款人")));
                }
                Object begingDate = map.get("借款日期");
                if (ObjectUtils.isNotEmpty(begingDate)) {
                    if (begingDate instanceof Date) {
                        loanInfo.setLoanBeginDate((Date) map.get("借款日期"));
                    } else if (begingDate instanceof Integer) {
                        loanInfo.setLoanBeginDate(DateUtil.getDate(String.valueOf(begingDate)));
                    } else if (begingDate instanceof String) {
                        loanInfo.setLoanBeginDate(DateUtil.getDate(String.valueOf(begingDate)));
                    } else {
                        loanInfoImportError.setRemark("借款日期");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                }
                Object endDate = map.get("到期日期");
                if (ObjectUtils.isNotEmpty(endDate)) {
                    if (endDate instanceof Date) {
                        loanInfo.setLoanEndDate((Date) map.get("到期日期"));
                    } else if (endDate instanceof Integer) {
                        loanInfo.setLoanEndDate(DateUtil.getDate(String.valueOf(endDate)));
                    } else if (endDate instanceof String) {
                        loanInfo.setLoanEndDate(DateUtil.getDate(String.valueOf(endDate)));
                    } else {
                        loanInfoImportError.setRemark("到期日期");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                }
                Object interestStartDate = map.get("计息日");
                if (ObjectUtils.isNotEmpty(interestStartDate)) {
                    if (interestStartDate instanceof Date) {
                        loanInfo.setInterestStartDate((Date) interestStartDate);
                    } else if (interestStartDate instanceof Integer) {
                        loanInfo.setInterestStartDate(DateUtil.getDate(String.valueOf(interestStartDate)));
                    } else if (interestStartDate instanceof String) {
                        loanInfo.setInterestStartDate(DateUtil.getDate(String.valueOf(interestStartDate)));
                    } else {
                        loanInfoImportError.setRemark("计息日");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                }

                if (ObjectUtils.isNotEmpty(map.get("首贷金额"))) {
                    String earliestLoanAmount = String.valueOf(map.get("首贷金额"));
                    if (StringUtil.isNumber(earliestLoanAmount)) {
                        loanInfo.setEarliestLoanAmount(new BigDecimal(String.valueOf(earliestLoanAmount)));
                    } else {
                        loanInfoImportError.setRemark("首贷金额");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                } else {
                    loanInfo.setEarliestLoanAmount(new BigDecimal(0));
                }
                if (map.get("贷款类型") != null) {
                    if (StringUtil.sqlVer(String.valueOf(map.get("贷款类型")))) {
                        loanInfoImportError.setRemark("贷款类型");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                    DictBean loanTypeDict = dictBeanDao.selectDictDetailByName("d_loantype", String.valueOf(map.get("贷款类型")));
                    if (loanTypeDict != null) {
                        loanInfo.setLoanType(Integer.valueOf(loanTypeDict.getCode()));
                    }
                }

                Object hxDate = map.get("核销时间");
                if (ObjectUtils.isNotEmpty(hxDate)) {
                    if (hxDate instanceof Date) {
                        loanInfo.setHxDate((Date) hxDate);
                    } else if (hxDate instanceof Integer) {
                        loanInfo.setHxDate(DateUtil.getDate(String.valueOf(hxDate)));
                    } else if (hxDate instanceof String) {
                        loanInfo.setHxDate(DateUtil.getDate(String.valueOf(hxDate)));
                    } else {
                        loanInfoImportError.setRemark("核销时间");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                }


                if (map.get("支行名称") != null) {
                    if (StringUtil.sqlVer(String.valueOf(map.get("支行名称")))) {
                        loanInfoImportError.setRemark("支行名称");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                    DictBean loanOrgDict = dictBeanDao.selectDictDetailByName("d_org", String.valueOf(map.get("支行名称")));
                    if (loanOrgDict != null) {
                        loanInfo.setOrg(loanOrgDict.getCode());
                    }
                }
                if(StringUtils.isNotBlank(String.valueOf(map.get("行业"))) && !"null".equals(String.valueOf(map.get("行业")))){
                    loanInfo.setLoanBusiness(String.valueOf(map.get("行业")));
                }
                if(StringUtils.isNotBlank(String.valueOf(map.get("合同用途"))) && !"null".equals(String.valueOf(map.get("合同用途")))){
                    loanInfo.setLoanUsed(String.valueOf(map.get("合同用途")));
                }
                if (map.get("类别") != null) {
                    if (StringUtil.sqlVer(String.valueOf(map.get("类别")))) {
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                    DictBean loanMoldDict = dictBeanDao.selectDictDetailByName("d_loanMold", String.valueOf(map.get("类别")));
                    if (loanMoldDict != null) {
                        loanInfo.setLoanMold(Integer.valueOf(loanMoldDict.getCode()));
                    }
                }
                if (ObjectUtils.isNotEmpty(map.get("原利率（月利率）"))) {
                    String oldMonthRate = StringTrim(String.valueOf(map.get("原利率（月利率）")));
                    if (map.get("原利率（月利率）") instanceof Double) {
                        loanInfo.setLoanOldMonthRate(new BigDecimal(String.valueOf(map.get("原利率（月利率）"))));
                    } else if (StringUtil.isNumber(oldMonthRate)) {
                        loanInfo.setLoanOldMonthRate(new BigDecimal(oldMonthRate));
                    } else {
                        loanInfoImportError.setRemark("原利率（月利率）");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                } else {
                    loanInfo.setLoanOldMonthRate(new BigDecimal(0));
                }
                if (ObjectUtils.isNotEmpty(map.get("执行利率（月利率）"))) {
                    String loanNewMonthRate = StringTrim(String.valueOf(map.get("执行利率（月利率）")));
                    if (StringUtil.isNumber(loanNewMonthRate)) {
                        loanInfo.setLoanNewMonthRate(new BigDecimal(loanNewMonthRate));
                    } else {
                        loanInfoImportError.setRemark("执行利率（月利率）");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                } else {
                    loanInfo.setLoanNewMonthRate(new BigDecimal(0));
                }

                if (ObjectUtils.isNotEmpty(map.get("结欠利息"))) {
                    String leftInterestAmount = String.valueOf(map.get("结欠利息"));
                    if (map.get("结欠利息") instanceof Double) {
                        loanInfo.setLeftInterestAmount(new BigDecimal((Double) map.get("结欠利息")).setScale(2, BigDecimal.ROUND_HALF_UP));
                    } else if (StringUtil.isNumber(leftInterestAmount)) {
                        loanInfo.setLeftInterestAmount(new BigDecimal(leftInterestAmount));
                    } else {
                        loanInfoImportError.setRemark("结欠利息");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                } else {
                    loanInfo.setLeftInterestAmount(new BigDecimal(0));
                }

                if (ObjectUtils.isNotEmpty(map.get("打包本金金额"))) {
                    String dbCapitalAmount = String.valueOf(map.get("打包本金金额"));
                    if (map.get("打包本金金额") instanceof Double) {
                        loanInfo.setDbCapitalAmount(new BigDecimal((Double) map.get("打包本金金额")).setScale(2, BigDecimal.ROUND_HALF_UP));
                    } else if (StringUtil.isNumber(dbCapitalAmount)) {
                        loanInfo.setDbCapitalAmount(new BigDecimal(dbCapitalAmount));
                    } else {
                        loanInfoImportError.setRemark("打包本金金额");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                } else {
                    loanInfo.setDbCapitalAmount(new BigDecimal(0));
                }

                if (ObjectUtils.isNotEmpty(map.get("打包利息金额"))) {
                    String dbInterestAmount = String.valueOf(map.get("打包利息金额"));
                    if (map.get("打包利息金额") instanceof Double) {
                        loanInfo.setDbInterestAmount(new BigDecimal((Double) map.get("打包利息金额")).setScale(2, BigDecimal.ROUND_HALF_UP));
                    } else if (StringUtil.isNumber(dbInterestAmount)) {
                        loanInfo.setDbInterestAmount(new BigDecimal(dbInterestAmount));
                    } else {
                        loanInfoImportError.setRemark("打包利息金额");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                } else {
                    loanInfo.setDbInterestAmount(new BigDecimal(0));
                }

                if (ObjectUtils.isNotEmpty(map.get("贷款余额"))) {
                    String loanLeftAmount = String.valueOf(map.get("贷款余额"));
                    if (map.get("贷款余额") instanceof Double) {
                        loanInfo.setLoanLeftAmount(new BigDecimal((Double) map.get("贷款余额")).setScale(2, BigDecimal.ROUND_HALF_UP));
                    } else if (StringUtil.isNumber(loanLeftAmount)) {
                        loanInfo.setLoanLeftAmount(new BigDecimal(loanLeftAmount));
                    } else {
                        loanInfoImportError.setRemark("贷款余额");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                } else {
                    loanInfo.setLoanLeftAmount(new BigDecimal(0));
                }

                if (ObjectUtils.isNotEmpty(map.get("打包前归还金额"))) {
                    String dbBeforeBackAmount = String.valueOf(map.get("打包前归还金额"));
                    if (map.get("打包前归还金额") instanceof Double) {
                        loanInfo.setDbBeforeBackAmount(new BigDecimal((Double) map.get("打包前归还金额")).setScale(2, BigDecimal.ROUND_HALF_UP));
                    } else if (StringUtil.isNumber(dbBeforeBackAmount)) {
                        loanInfo.setDbBeforeBackAmount(new BigDecimal(dbBeforeBackAmount));
                    } else {
                        loanInfoImportError.setRemark("打包前归还金额");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                } else {
                    loanInfo.setDbBeforeBackAmount(new BigDecimal(0));
                }

                if (ObjectUtils.isNotEmpty(map.get("贷款方式"))) {
                    if (StringUtil.sqlVer(String.valueOf(map.get("贷款方式")))) {
                        loanInfoImportError.setRemark("贷款方式");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                    DictBean loanWayDict = dictBeanDao.selectDictDetailByName("d_loanway", String.valueOf(map.get("贷款方式")));
                    if (loanWayDict != null) {
                        loanInfo.setLoanWay(Integer.valueOf(loanWayDict.getCode()));
                    }
                }
                if (ObjectUtils.isNotEmpty(map.get("结欠本金"))) {
                    String leftCapitalAmount = String.valueOf(map.get("结欠本金"));
                    if (map.get("结欠本金") instanceof Double) {
                        loanInfo.setLeftCapitalAmount(new BigDecimal((Double) map.get("结欠本金")).setScale(2, BigDecimal.ROUND_HALF_UP));
                    } else if (StringUtil.isNumber(leftCapitalAmount)) {
                        loanInfo.setLeftCapitalAmount(new BigDecimal(leftCapitalAmount).setScale(2, BigDecimal.ROUND_HALF_UP));
                    } else {
                        loanInfoImportError.setRemark("结欠本金");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                } else {
                    loanInfo.setLeftCapitalAmount(new BigDecimal(0));
                }

                if (ObjectUtils.isNotEmpty(map.get("核销时欠息"))) {
                    String heXiaoLeftInterestAmount = String.valueOf(map.get("核销时欠息"));
                    if (map.get("核销时欠息") instanceof Double) {
                        loanInfo.setHeXiaoLeftInterestAmount(new BigDecimal((Double) map.get("核销时欠息")).setScale(2, BigDecimal.ROUND_HALF_UP));
                    } else if (StringUtil.isNumber(heXiaoLeftInterestAmount)) {
                        loanInfo.setHeXiaoLeftInterestAmount(new BigDecimal(heXiaoLeftInterestAmount).setScale(2, BigDecimal.ROUND_HALF_UP));
                    } else {
                        loanInfoImportError.setRemark("核销时欠息");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                } else {
                    loanInfo.setHeXiaoLeftInterestAmount(new BigDecimal(0));
                }

                if (ObjectUtils.isNotEmpty(map.get("表外欠息"))) {
                    String biaoWaiLeftCapitalAmount = String.valueOf(map.get("表外欠息"));
                    if (map.get("表外欠息") instanceof Double) {
                        loanInfo.setBiaoWaiLeftInterestAmount(new BigDecimal((Double) map.get("表外欠息")).setScale(2, BigDecimal.ROUND_HALF_UP));
                    } else if (StringUtil.isNumber(biaoWaiLeftCapitalAmount)) {
                        loanInfo.setBiaoWaiLeftInterestAmount(new BigDecimal(biaoWaiLeftCapitalAmount).setScale(2, BigDecimal.ROUND_HALF_UP));
                    } else {
                        loanInfoImportError.setRemark("表外欠息");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                } else {
                    loanInfo.setBiaoWaiLeftInterestAmount(new BigDecimal(0));
                }

                if (map.get("村或社区") != null) {
                    if (StringUtil.sqlVer(String.valueOf(map.get("村或社区")))) {
                        loanInfoImportError.setRemark("村或社区");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                    DictBean loanAreaDict = dictBeanDao.selectDictDetailByName("d_area", String.valueOf(map.get("村或社区")));
                    if (loanAreaDict != null) {
                        loanInfo.setVillage(loanAreaDict.getCode());
                    }
                }
                if (map.get("乡镇") != null) {
                    if (StringUtil.sqlVer(String.valueOf(map.get("乡镇")))) {
                        loanInfoImportError.setRemark("乡镇");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                    DictBean loanAreaDict = dictBeanDao.selectDictDetailByName("d_area", String.valueOf(map.get("乡镇")));
                    if (loanAreaDict != null) {
                        loanInfo.setTown(loanAreaDict.getCode());
                    }
                }

                if (map.get("县") != null) {
                    if (StringUtil.sqlVer(String.valueOf(map.get("县")))) {
                        loanInfoImportError.setRemark("县");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                    DictBean loanAreaDict = dictBeanDao.selectDictDetailByName("d_area", String.valueOf(map.get("县")));
                    if (loanAreaDict != null) {
                        loanInfo.setCounty(loanAreaDict.getCode());
                    }
                }

                if (loanInfo.getVillage() != null) {
                    String town = loanInfo.getVillage().substring(0, loanInfo.getVillage().length() - 3);
                    loanInfo.setTown(town);
                }
                if (loanInfo.getTown() != null) {
                    String county = loanInfo.getTown().substring(0, loanInfo.getTown().length() - 3);
                    loanInfo.setCounty(county);
                }

                loanInfo.setHomeAddr(String.valueOf(map.get("详细住址")));
                String mobile = String.valueOf(map.get("联系电话"));
                if (mobile != null && mobile != "无") {
                    loanInfo.setMobile(String.valueOf(map.get("联系电话")));
                }
                if (map.get("案件分类") != null) {
                    if (StringUtil.sqlVer(String.valueOf(map.get("案件分类")))) {
                        loanInfoImportError.setRemark("案件分类");
                        loanInfoImportErrorDaoService.add(loanInfoImportError);
                        continue;
                    }
                    DictBean loanLoanSatusDict = dictBeanDao.selectDictDetailByName("d_loanstatus", String.valueOf(map.get("案件分类")));
                    if (loanLoanSatusDict != null) {
                        loanInfo.setLoanStatus(Integer.valueOf(loanLoanSatusDict.getCode()));
                    }
                }
                loanInfo.setUpdateBy(userId);
                loanInfo.setUpdateOn(new Date());
                loanInfo.setCreateBy(userId);
                loanInfo.setCreateOn(new Date());
                loanInfo.setImportDate(new Date());
                loanInfo.setName(V1Pass.pass_encode(loanInfo.getName()));
                loanInfo.setMobile(V1Pass.pass_encode(loanInfo.getMobile()));

                loanInfo.setSourceLeftCapitalAmount(loanInfo.getLeftCapitalAmount());
                loanInfo.setSourceLeftInterestAmount(loanInfo.getLeftInterestAmount());
                loanInfo.setSourceBiaoWaiLeftInterestAmount(loanInfo.getBiaoWaiLeftInterestAmount());
                loanInfo.setSourceHeXiaoLeftInterestAmount(loanInfo.getHeXiaoLeftInterestAmount());
                logger.info(GsonUtil.gson.toJson(loanInfo));
                loanInfoBeanDaoService.add(loanInfo);
                loanInfoBeanDaoService.addSourceLoan(loanInfo);

                loanInfoImportError.setImportStatus("主体信息已导入");
                //配偶
                if(map.get("夫（妻）身份证号") != null || map.get("夫（妻）姓名") != null){
                    LoanGuarantee p = new LoanGuarantee();
                    p.setLoanContractNum(loanInfo.getLoanContractNum());
                    p.setRelType(LoanGuaranteeRelEnum.LOAN_GUARANTEE_REL_MATE.getCode());
                    p.setIdNum(String.valueOf(map.get("夫（妻）身份证号")));
                    if (p.getIdNum() != null && (p.getIdNum().length() == 18 || p.getIdNum().length() == 15)) {
                        p.setIdType("1");
                    } else {
                        p.setIdType("3");
                    }
                    p.setName(String.valueOf(map.get("夫（妻）姓名")));
                    p.setAddr("");
                    p.setMobile("");
                    p.setUpdateOn(new Date());
                    p.setUpdateBy(userId);
                    p.setCreateBy(userId);
                    p.setCreateOn(new Date());
                    loanGuaranteeDaoService.add(p);
                }
                //担保人
                String loanGuaranteeStr = String.valueOf(map.get("保证人名称/保证人证件号码/保证人住址（多个数据用分号隔开）"));
             /*   String loanGuaranteeNameStr = String.valueOf(map.get("保证人"));
                String loanGuaranteeAddrStr = String.valueOf(map.get("保证人住址（多个保证人用分号分来）"));
                String loanGuaranteeMateStr = String.valueOf(map.get("保证人夫（妻）姓名、身份证号"));*/
                if (StringUtils.isNotBlank(loanGuaranteeStr) && !"null".equals(loanGuaranteeStr)) {
                    String[] loanGuarantees = loanGuaranteeStr.split(";");
                    for(String item : loanGuarantees){
                        String[] items = item.split("/");
                            logger.info("items:{},items.length",items.toString(),items.length);
                            LoanGuarantee d = new LoanGuarantee();
                            d.setLoanContractNum(loanInfo.getLoanContractNum());
                            d.setRelType(LoanGuaranteeRelEnum.LOAN_GUARANTEE_REL_LOANGUARANTEE.getCode());

                            d.setIdType("1");
                            if(items.length == 1){
                                d.setName(items[0]);
                            }
                            if(items.length == 2){
                                d.setName(items[0]);
                                d.setIdNum(items[1]);
                            }
                            if(items.length == 3){
                                d.setName(items[0]);
                                d.setIdNum(items[1]);
                                d.setAddr(items[2]);
                            }
                            d.setMobile("");
                            d.setMobileMate("");
                            if (d.getNameMate() == null) {
                                d.setNameMate("");
                            }
                            if (d.getIdNumMate() == null) {
                                d.setIdNumMate("");
                            }
                            d.setUpdateOn(new Date());
                            d.setCreateOn(new Date());
                            d.setUpdateBy(userId);
                            d.setCreateBy(userId);
                            loanGuaranteeDaoService.add(d);
                        }
                }
                //抵押
                if(map.get("抵、质押物名称") != null){
                    String mortgage = String.valueOf(map.get("抵、质押物名称"));
                    LoanMortgage lm = new LoanMortgage();
                    lm.setName(mortgage);
                    lm.setCurrentStatus("");
                    lm.setEstimate(new BigDecimal(0));
                    lm.setHandleValue(new BigDecimal(0));
                    lm.setNum(new BigDecimal(0));
                    lm.setUnit("");
                    lm.setCreateOn(new Date());
                    lm.setUpdateOn(new Date());
                    lm.setLoanContractNum(loanInfo.getLoanContractNum());
                    lm.setUpdateBy(userId);
                    lm.setCreateBy(userId);
                    loanMortgageDaoService.add(lm);
                }

                //分配
                if(StringUtils.isNotBlank(String.valueOf(map.get("分配网点"))) && !"null".equals(String.valueOf(map.get("分配网点")))){
                    String local = String.valueOf(map.get("分配网点"));
                    Map deptMap = hbcmUserDao.findDeptByDeptName(local);
                    if(deptMap != null){
                        LoanDistributeBean loanDistributeBean = new LoanDistributeBean();
                        loanDistributeBean.setLoanContractNum(loanInfo.getLoanContractNum());
                        loanDistributeBean.setNumber(String.valueOf(deptMap.get("deptCode")));
                        loanDistributeBean.setNumberType(DistributeTypeEnum.DISTRIBUTE_TYPE_GROUP.getCode());
                        distributeLoanInfo(loanDistributeBean,"463498");
                    }
                }
            }
            result.setSuccessResult();
        } else {
            result.setFailResult("未读取到信息");
        }
        return result;
    }

    public boolean arrayLen(String[] strs) {
        /*for(String str : strs){
            if(str.length() > 30){
                return false;
            }
        }*/
        return true;
    }

    public LoanInfoBean loanInfoV1PassHandle(LoanInfoBean loanInfoBean) {
        if (StringUtils.isNotBlank(loanInfoBean.getName())) {
            loanInfoBean.setName(V1Pass.pass_encode(loanInfoBean.getName()));
        }
        if (StringUtils.isNotBlank(loanInfoBean.getIdNum())) {
            loanInfoBean.setIdNum(V1Pass.pass_encode(loanInfoBean.getIdNum()));
        }
        if (StringUtils.isNotBlank(loanInfoBean.getMobile())) {
            loanInfoBean.setMobile(V1Pass.pass_encode(loanInfoBean.getMobile()));
        }
        return loanInfoBean;
    }

    public String StringTrim(String str) {
        return str.replaceAll("[\\s\\u00A0]+", "").trim();
    }

    @Transactional
    public CallResult<String> updateDistributeManager(LoanDistributeBean loanDistributeBean, String userId)
            throws Exception {
        CallResult<String> callResult = new CallResult<>();
        LoanDistributeBean param = new LoanDistributeBean();
        param.setLoanContractNum(loanDistributeBean.getLoanContractNum());
        LoanDistributeBean lb = loanDistributeBeanDaoService.getByField(param);
        if (ObjectUtils.isNotEmpty(lb)) {
            param.setManager(loanDistributeBean.getManager());
            param.setUpdateOn(new Date());
            param.setUpdateBy(userId);
            param.setId_prikey(lb.getId_prikey());
            loanDistributeBeanDaoService.set(param);

            LoanDistributeLogBean log = new LoanDistributeLogBean();
            log.setCreateBy(userId);
            log.setCreateOn(new Date());
            log.setLoanContractNum(loanDistributeBean.getLoanContractNum());
            log.setNumber(loanDistributeBean.getManager());
            log.setOperationType(DistributeOperationTypeEnum.DISTRIBUTE_GROUP_MEMBER.getCode());
            log.setNumberType(DistributeTypeEnum.DISTRIBUTE_TYPE_PERSON.getCode());
            loanDistributeLogBeanDaoService.add(log);
        }
        callResult.setSuccessResult();
        return callResult;
    }

    @Transactional
    public CallResult<String> updateBatchDistributeManager(List<String> loanContractNums, String manager, String userId)
            throws Exception {
        CallResult<String> result = new CallResult<>();
        for (String loanContractNum : loanContractNums) {
            LoanDistributeBean bean = new LoanDistributeBean();
            bean.setLoanContractNum(loanContractNum);
            bean.setManager(manager);
            updateDistributeManager(bean, userId);
        }
        result.setSuccessResult();
        return result;
    }

    @Transactional
    public CallResult<String> loanClaim(String loanContractNum, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();
        Date date = new Date();

        // 通过贷款合同号和是否共享判断LoanInfoBean 是否存在
        LoanInfoBean lib = new LoanInfoBean();
        lib.setLoanContractNum(loanContractNum);
        LoanInfoBean libByField = loanInfoBeanDaoService.getByField(lib);
        if (libByField == null) {
            result.setFailResult("参数有误");
            return result;
        }
        if (YesOrNoEnum.YES_OR_NO_YES.getCode().equals(libByField.getClaimFlag())) {
            result.setFailResult("客户已被认领，不能再次认领");
            return result;
        }
        //开启流程
        ProcessVo processvo = new ProcessVo();
        processvo.setUserId(userId);
        processvo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_CLAIM.getCode());
        processvo.setNodeSatus(ProcessNodeStatusEnum.PROCESS_NODE_STATUS_YES.getCode());
        processvo.setBusinessId(String.valueOf(libByField.getId_prikey()));
        processvo.setNodeId(0);
        processvo.setBusinessCode(loanContractNum);
        if (YesOrNoEnum.YES_OR_NO_YES.getCode().equals(libByField.getIsShare())) {
            processvo.setBranch("2");
        } else {
            processvo.setBranch("1");
        }
        CallResult<ProcessHandleBean> processResult = processHandleService.processHandle(processvo);
        if (!processResult.isExec()) {
            result.setFailResult(processResult.getMsg());
            return result;
        }



       /* // 通过贷款合同号和是否共享判断LoanDistributeBean 是否存在
        LoanDistributeBean lb = new LoanDistributeBean();
        lb.setLoanContractNum(loanContractNum);
        LoanDistributeBean lbByField = loanDistributeBeanDaoService.getByField(lb);
        // 不存在 添加
        if (lbByField == null) {
            LoanDistributeBean lb2 = new LoanDistributeBean();
            lb2.setLoanContractNum(loanContractNum);
            lb2.setNumber(userId);
            lb2.setNumberType(DistributeTypeEnum.DISTRIBUTE_TYPE_PERSON.getCode());
            lb2.setUpdateBy(userId);
            lb2.setUpdateOn(date);
            lb2.setCreateOn(date);
            lb2.setCreateBy(userId);
            lb2.setOperationType(DistributeOperationTypeEnum.DISTRIBUTE_CLAIM.getCode());
            loanDistributeBeanDaoService.add(lb2);

            // 存在 修改
        } else {
            lbByField.setNumber(userId);
            lbByField.setNumberType(DistributeTypeEnum.DISTRIBUTE_TYPE_PERSON.getCode());
            lbByField.setUpdateBy(userId);
            lbByField.setUpdateOn(date);
            loanDistributeBeanDaoService.set(lbByField);
        }
        //添加日志
        LoanDistributeLogBean log = new LoanDistributeLogBean();
        log.setCreateBy(userId);
        log.setCreateOn(date);
        log.setLoanContractNum(loanContractNum);
        log.setNumber(userId);
        log.setOperationType(DistributeOperationTypeEnum.DISTRIBUTE_CLAIM.getCode());
        log.setNumberType(DistributeTypeEnum.DISTRIBUTE_TYPE_PERSON.getCode());
        loanDistributeLogBeanDaoService.add(log);

        //修改LoanInfoBean  认领
*/
        result.setSuccessResult();
        return result;

    }

    @Transactional
    public CallResult<String> updateLoanInfoShare(Integer id_prikey, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();
        Date date = new Date();

        LoanInfoBean loanInfoBean = loanInfoBeanDaoService.getById(id_prikey);
        if (loanInfoBean == null) {
            result.setFailResult("参数有误");
            return result;
        }else {
            if(YesOrNoEnum.YES_OR_NO_YES.getCode().equals(loanInfoBean.getIsShare())){
                result.setFailResult("已被共享");
                return result;
            }
        }
        String deptId = commonDao.getHbDeptIdByLoanInfoId(id_prikey);
        if (StringUtil.isEmpty(deptId)) {
            result.setFailResult("部门名称不匹配");
            return result;
        }
        LoanInfoBean lib = new LoanInfoBean();
        lib.setId_prikey(loanInfoBean.getId_prikey());
        lib.setIsShare(YesOrNoEnum.YES_OR_NO_YES.getCode());
        loanInfoBeanDaoService.set(lib);
        LoanDistributeBean ldb = new LoanDistributeBean();
        ldb.setLoanContractNum(loanInfoBean.getLoanContractNum());
        LoanDistributeBean ldbByField = loanDistributeBeanDaoService.getByField(ldb);
        if (ldbByField != null) {
            LoanDistributeSourcesBean lds = new LoanDistributeSourcesBean();
            BeanUtils.copyProperties(ldbByField, lds);
            lds.setRelId(ldbByField.getId_prikey());
            lds.setSourcesWay(DistributeOperationTypeEnum.DISTRIBUTE_SHARE.getCode());
            lds.setId_prikey(null);
            loanDistributeSourcesBeanDaoService.add(lds);

            ldbByField.setNumber(deptId);
            ldbByField.setNumberType(DistributeTypeEnum.DISTRIBUTE_TYPE_GROUP.getCode());
            ldbByField.setUpdateBy(userId);
            ldbByField.setOrgNum(" ");
            ldbByField.setManager(" ");
            ldbByField.setUpdateOn(new Date());
            ldbByField.setOperationType(DistributeOperationTypeEnum.DISTRIBUTE_SHARE.getCode());
            loanDistributeBeanDaoService.set(ldbByField);
        }

        LoanDistributeLogBean log = new LoanDistributeLogBean();
        log.setNumber(deptId);
        log.setNumberType(DistributeTypeEnum.DISTRIBUTE_TYPE_GROUP.getCode());
        log.setCreateBy(userId);
        log.setCreateOn(date);
        log.setLoanContractNum(loanInfoBean.getLoanContractNum());
        log.setOperationType(DistributeOperationTypeEnum.DISTRIBUTE_SHARE.getCode());
        loanDistributeLogBeanDaoService.add(log);
        result.setSuccessResult();
        return result;
    }

    @Transactional
    public CallResult<String> updateBatchLoanInfoShare(List<Integer> ids, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();
        synchronized (this){
            for (Integer id : ids) {
                updateLoanInfoShare(id, userId);
            }
        }
        result.setSuccessResult();
        return result;
    }

    @Transactional
    public CallResult<String> updateLoanInfoNoShare(Integer id_prikey, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();
        Date date = new Date();

        LoanInfoBean loanInfoBean = loanInfoBeanDaoService.getById(id_prikey);
        if (loanInfoBean == null) {
            result.setFailResult("参数有误");
            return result;
        }else{
            if(YesOrNoEnum.YES_OR_NO_NO.getCode().equals(loanInfoBean.getIsShare())){
                result.setFailResult("已取消共享");

                LoanDistributeBean ldParam = new LoanDistributeBean();
                ldParam.setLoanContractNum(loanInfoBean.getLoanContractNum());
                LoanDistributeBean loanDistributeBean = loanDistributeBeanDaoService.getByField(ldParam);

                LoanDistributeSourcesBean ldsParam = new LoanDistributeSourcesBean();
                ldsParam.setRelId(loanDistributeBean.getId_prikey());
                ldsParam.setSourcesWay(DistributeOperationTypeEnum.DISTRIBUTE_SHARE.getCode());
                List<LoanDistributeSourcesBean> sourcesList = loanDistributeSourcesBeanDaoService.getAll(ldsParam);
                for(LoanDistributeSourcesBean ls : sourcesList){
                    loanDistributeSourcesBeanDaoService.removeOne(ls.getId_prikey());
                }
                return result;
            }
        }
        LoanInfoBean lib = new LoanInfoBean();
        lib.setId_prikey(loanInfoBean.getId_prikey());
        lib.setIsShare(YesOrNoEnum.YES_OR_NO_NO.getCode());
        loanInfoBeanDaoService.set(lib);

        LoanDistributeBean ldParam = new LoanDistributeBean();
        ldParam.setLoanContractNum(loanInfoBean.getLoanContractNum());
        LoanDistributeBean loanDistributeBean = loanDistributeBeanDaoService.getByField(ldParam);
        if (loanDistributeBean != null) {
            LoanDistributeSourcesBean ldsParam = new LoanDistributeSourcesBean();
            ldsParam.setRelId(loanDistributeBean.getId_prikey());
            ldsParam.setSourcesWay(DistributeOperationTypeEnum.DISTRIBUTE_SHARE.getCode());
            LoanDistributeSourcesBean sources = loanDistributeSourcesBeanDaoService.findLastByField(ldsParam);
            if (sources != null) {
                loanDistributeBean.setOperationType(sources.getOperationType());
                loanDistributeBean.setCreateBy(sources.getCreateBy());
                loanDistributeBean.setCreateOn(sources.getCreateOn());
                loanDistributeBean.setUpdateBy(sources.getUpdateBy());
                loanDistributeBean.setUpdateOn(sources.getUpdateOn());
                loanDistributeBean.setNumber(sources.getNumber());
                loanDistributeBean.setNumberType(sources.getNumberType());
                loanDistributeBean.setManager(sources.getManager());
                loanDistributeBean.setOrgNum(sources.getOrgNum());
                loanDistributeBean.setLimitMonth(sources.getLimitMonth());
                loanDistributeBeanDaoService.setAllFiled(loanDistributeBean);
                loanDistributeSourcesBeanDaoService.removeOne(sources.getId_prikey());
            }
        }
        LoanDistributeLogBean log = new LoanDistributeLogBean();
        log.setCreateBy(userId);
        log.setCreateOn(date);
        log.setNumber(loanDistributeBean.getNumber());
        log.setNumberType(loanDistributeBean.getNumberType());
        log.setLoanContractNum(loanInfoBean.getLoanContractNum());
        log.setOperationType(DistributeOperationTypeEnum.DISTRIBUTE_NOSHARE.getCode());
        loanDistributeLogBeanDaoService.add(log);

        result.setSuccessResult();
        return result;
    }

    @Transactional
    public CallResult<String> updateBatchLoanInfoNoShare(List<Integer> ids, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();
        synchronized (this){
            for (Integer id : ids) {
                updateLoanInfoNoShare(id, userId);
            }
        }
        result.setSuccessResult();
        return result;
    }

//	@Transactional
//	public CallResult<String> loanUpdateApply(LoanInfoBean lib, String files,String remark, String userId) throws Exception {
//		CallResult<String> result = new CallResult<>();
//		Gson gson = new Gson();
//
//		String infoJson = gson.toJson(lib);
//
//		LoanInfoUpdateContent liuc = new LoanInfoUpdateContent();
//		liuc.setLoanContractNum(lib.getLoanContractNum());
//		liuc.setUpdateContent(infoJson);
//		liuc.setChangeRemark(remark);
//		liuc.setUpdateBy(userId);
//		liuc.setUpdateOn(new Date());
//		liuc.setStatus("0");
//		Integer addPrikey = loanInfoUpdateContentDaoService.addPrikey(liuc);
//
//		if (!StringUtil.isEmpty(files)) {
//			String[] file = files.split(";");
//			if (null != file) {
//				for (String f : file) {
//					String[] split = f.split(",");
//					if (null != split) {
//						LoanFileInfoBean lfb = new LoanFileInfoBean();
//						lfb.setRelId(addPrikey);
//						lfb.setType(LoanFileTypeEnum.LOAN_FILE_TYPE_UPDATE.getCode());
//						lfb.setFileType(split[0]);
//						lfb.setFilePath(split[1]);
//						lfb.setFileName("修改申请");
//						loanFileInfoBeanDaoService.add(lfb);
//					}
//				}
//			}
//		}
//		result.setSuccessResult();
//		return result;
//	}

    @Transactional
    public CallResult<String> loanUpdateApply2(LoanInfoBean lib, String files, Integer businessId, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();
        Gson gson = new Gson();

        ProcessVo vo = new ProcessVo();
        vo.setUserId(userId);
        vo.setRemark(lib.getRemark());
        vo.setNodeId(0);
        vo.setNodeSatus(ProcessNodeStatusEnum.PROCESS_NODE_STATUS_YES.getCode());
        vo.setBusinessCode(lib.getLoanContractNum());
        vo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_LOANINFO_UPDATE.getCode());
        vo.setFilePath(files);

        LoanInfoBean loanInfo = loanInfoBeanDaoService.getById(lib.getId_prikey());
        if (loanInfo == null) {
            result.setFailResult("主键有误");
            return result;
        }
        if (!loanInfo.getLoanContractNum().equals(lib.getLoanContractNum())) {
            result.setFailResult("主键和合同号不匹配");
            return result;
        }
        String infoJson = gson.toJson(lib);
        LoanInfoUpdateContent liuc = new LoanInfoUpdateContent();
        liuc.setLoanContractNum(lib.getLoanContractNum());
        if (businessId == null) {
            liuc.setUpdateContent(infoJson);
            Integer updateId = loanInfoUpdateContentDaoService.addPrikey(liuc);
            vo.setBusinessId(String.valueOf(updateId));
        } else {
            LoanInfoUpdateContent byId = loanInfoUpdateContentDaoService.getById(businessId);
            if (byId != null) {
                byId.setUpdateContent(infoJson);
                loanInfoUpdateContentDaoService.set(byId);
                vo.setBusinessId(String.valueOf(byId.getId_prikey()));
            } else {
                throw new RuntimeException("businessId有误");
            }
        }

        CallResult<ProcessHandleBean> processHandle = processHandleService.processHandle(vo);
        if (processHandle.getCode() != 0) {
            throw new RuntimeException(processHandle.getMsg());
        }
        result.setSuccessResult();
        return result;
    }

    //	@Transactional
//	public CallResult<String> loanUpdateAudit(LoanInfoUpdateContent liuc, String userId) throws Exception {
//		CallResult<String> result = new CallResult<>();
//		Gson gson = new Gson();
//
//		// 查询原始信息
//		LoanInfoUpdateContent byId = loanInfoUpdateContentDaoService.getById(liuc.getId_prikey());
//		CallResult<LoanInfoBean> selectLoanInfo = selectLoanInfo(byId.getLoanContractNum());
//		if (selectLoanInfo.getCode() != 0) {
//			result.setMsg(selectLoanInfo.getMsg());
//			return result;
//		}
//		LoanInfoBean data = selectLoanInfo.getData();
//		String json = gson.toJson(data);
//
//		// 修改 修改列表的状态
//		LoanInfoUpdateContent liuc2 = new LoanInfoUpdateContent();
//		liuc2.setOldContent(json);
//		liuc2.setId_prikey(liuc.getId_prikey());
//		if (!StringUtil.isEmpty(liuc.getAuditRemark())) {
//			liuc2.setAuditRemark(liuc.getAuditRemark());
//		}
//		liuc2.setStatus(liuc.getStatus());
//		liuc2.setAuditBy(userId);
//		liuc2.setAuditOn(new Date());
//		loanInfoUpdateContentDaoService.set(liuc2);
//
//		// 同意修改
//		if (ProcessNodeStatusEnum.PROCESS_NODE_STATUS_YES.getCode().equals(liuc.getStatus())) {
//			LoanInfoBean bean = gson.fromJson(byId.getUpdateContent(), LoanInfoBean.class);
//			result = updateLoanInfo(bean, userId);
//			// 拒绝修改
//		} else {
//			result.setSuccessResult();
//		}
//		return result;
//	}
//	
    @Transactional
    public CallResult<String> loanUpdateAudit2(LoanInfoUpdateContent liuc, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();
        Gson gson = new Gson();

        ProcessVo vo = new ProcessVo();
        vo.setNodeId(liuc.getNodeId());
        vo.setNodeSatus(liuc.getStatus());
        vo.setBusinessCode(liuc.getLoanContractNum());
        vo.setBusinessId(liuc.getId_prikey().toString());
        vo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_LOANINFO_UPDATE.getCode());
        vo.setRemark(liuc.getRemark());
        vo.setUserId(userId);

        CallResult<ProcessHandleBean> processHandle = processHandleService.processHandle(vo);
        if (processHandle.getCode() != 0) {
            result.setCode(processHandle.getCode());
            result.setMsg(processHandle.getMsg());
            return result;
        }

        // 查询原始信息
        LoanInfoUpdateContent byId = loanInfoUpdateContentDaoService.getById(liuc.getId_prikey());
        if (byId == null) {
            result.setFailResult("主键有误");
            return result;
        }
        if (!liuc.getLoanContractNum().equals(byId.getLoanContractNum())) {
            result.setFailResult("合同号有误");
            return result;
        }
        CallResult<LoanInfoBean> selectLoanInfo = selectLoanInfo(liuc.getLoanContractNum());
        if (selectLoanInfo.getCode() != 0) {
            result.setCode(selectLoanInfo.getCode());
            result.setMsg(selectLoanInfo.getMsg());
            return result;
        }
        LoanInfoBean data = selectLoanInfo.getData();
        data.setMates(gson.toJson(data.getMateList()));
        data.setMortgages(gson.toJson(data.getMortgageList()));
        data.setGuarantees(gson.toJson(data.getGuaranteeList()));
        String json = gson.toJson(data);

        if (ProcessNodeStatusEnum.PROCESS_NODE_STATUS_YES.getCode().equals(liuc.getStatus())) {
            LoanInfoBean bean = gson.fromJson(byId.getUpdateContent(), LoanInfoBean.class);
            result = updateLoanInfo(bean, userId);
            LoanInfoUpdateContent liuc2 = new LoanInfoUpdateContent();
            liuc2.setOldContent(json);
            liuc2.setId_prikey(liuc.getId_prikey());
            loanInfoUpdateContentDaoService.set(liuc2);
        }

        result.setSuccessResult();
        return result;
    }


    public CallResult<LoanInfoBean> DictCodeToName(LoanInfoBean lib) throws Exception {
        CallResult<LoanInfoBean> result = new CallResult<>();
        if (null != lib.getIdType()) {
            DictBean d = dictBeanDao.selectDictDetailByCode("d_idType", String.valueOf(lib.getIdType()));
            lib.setIdTypeName(d.getName());
        }
        if (null != lib.getLoanMold()) {
            DictBean d = dictBeanDao.selectDictDetailByCode("d_loanMold", String.valueOf(lib.getLoanMold()));
            lib.setLoanMoldName(d.getName());
        }
        if (null != lib.getLoanType()) {
            DictBean d = dictBeanDao.selectDictDetailByCode("d_loantype", String.valueOf(lib.getLoanType()));
            lib.setLoanTypeName(d.getName());
        }
        if (null != lib.getLoanStatus()) {
            DictBean d = dictBeanDao.selectDictDetailByCode("d_loanstatus", String.valueOf(lib.getLoanStatus()));
            lib.setLoanStatusName(d.getName());
        }
        if (null != lib.getLoanWay()) {
            DictBean d = dictBeanDao.selectDictDetailByCode("d_loanway", String.valueOf(lib.getLoanWay()));
            lib.setLoanWayName(d.getName());
        }
        if (null != lib.getOrg()) {
            DictBean d = dictBeanDao.selectDictDetailByCode("d_org", String.valueOf(lib.getOrg()));
            lib.setOrgName(d.getName());
        }
        if (null != lib.getCounty()) {
            DictBean d = dictBeanDao.selectDictDetailByCode("d_area", lib.getCounty());
            lib.setCountyName(d.getName());
        }
        if (null != lib.getTown()) {
            DictBean d = dictBeanDao.selectDictDetailByCode("d_area", lib.getTown());
            lib.setTownName(d.getName());
        }
        if (null != lib.getVillage()) {
            DictBean d = dictBeanDao.selectDictDetailByCode("d_area", lib.getVillage());
            lib.setVillageName(d.getName());
        }
        result.setCode(0);
        result.setData(lib);
        result.setMsg("操作成功");
        return result;
    }

    public Map<String, Object> getAlterLoanInfoList(UpdateLogBean updateLogBean) throws Exception{
        Map<String, Object> map = null;
        Gson gson = new Gson();
        List<LoanInfoBean> loanInfoBeans = new ArrayList<>();
        map = updateLogBeanDaoService.getPagingData(updateLogBean);
        if (null != map) {
            List<UpdateLogBean> list = (List<UpdateLogBean>)map.get("data");
            for (UpdateLogBean updateLog : list) {
                LoanInfoBean loanInfoBean = gson.fromJson(updateLog.getNewContent(),new TypeToken<LoanInfoBean>(){}
                .getType());
                if (null != loanInfoBean) {
                    LoanInfoBean param = new LoanInfoBean();
                    param.setId_prikey(loanInfoBean.getId_prikey());
                    LoanInfoBean loanInfoBean1 = loanInfoBeanDaoService.getByField(param);
                    loanInfoBean.setIdNum(V1Pass.pass_decode(loanInfoBean1.getIdNum()));
                    loanInfoBean.setMobile(V1Pass.pass_decode(loanInfoBean.getMobile()));
                    loanInfoBean.setName(V1Pass.pass_decode(loanInfoBean1.getName()));
                    if (null != loanInfoBean.getCounty() && !"".equals(loanInfoBean.getCounty())) {
                        loanInfoBean.setCountyName(loanInfoBean1.getCountyName());
                    }
                    if (null != loanInfoBean.getTown() && !"".equals(loanInfoBean.getTown())) {
                        loanInfoBean.setTownName(loanInfoBean1.getTownName());
                    }
                    if (null != loanInfoBean.getVillage() && !"".equals(loanInfoBean.getVillage())) {
                        loanInfoBean.setVillageName(loanInfoBean1.getVillageName());
                    }
                    if (null != loanInfoBean1.getCreateOn()) {
                        loanInfoBean.setCollectionDateStr(DateUtil.formatDate(loanInfoBean1.getCreateOn(), "yyyy-MM-dd"));
                    }
                    loanInfoBean.setCreateBy(loanInfoBean1.getCreateBy());
                    loanInfoBeans.add(loanInfoBean);
                }
            }

            map.put("data",loanInfoBeans);
        }
        return map;
    }

    public void exprotAlterLoanInfoList(HttpServletResponse response) throws Exception {
        List<UpdateLogBean> updateLogBeans = null;
        Gson gson = new Gson();
        List<LoanInfoBean> loanInfoBeans = new ArrayList<>();
        updateLogBeans = updateLogBeanDaoService.alterLoanInfoList();
        if (null != updateLogBeans && updateLogBeans.size() > 0) {
            for (UpdateLogBean updateLog : updateLogBeans) {
                if (null != updateLog.getNewContent() && !"".equals(updateLog.getNewContent())) {
                    try{
                        LoanInfoBean loanInfoBean = GsonUtil.gson.fromJson(updateLog.getNewContent(),new TypeToken<LoanInfoBean>(){}
                                .getType());
                        LoanInfoBean param = new LoanInfoBean();
                        param.setId_prikey(loanInfoBean.getId_prikey());
                        LoanInfoBean loanInfoBean1 = loanInfoBeanDaoService.getByField(param);
                        loanInfoBean.setIdNum(V1Pass.pass_decode(loanInfoBean1.getIdNum()));
                        loanInfoBean.setMobile(V1Pass.pass_decode(loanInfoBean.getMobile()));
                        loanInfoBean.setName(V1Pass.pass_decode(loanInfoBean1.getName()));
                        if (null != loanInfoBean.getCounty() && !"".equals(loanInfoBean.getCounty())) {
                            loanInfoBean.setCountyName(loanInfoBean1.getCountyName());
                        }
                        if (null != loanInfoBean.getTown() && !"".equals(loanInfoBean.getTown())) {
                            loanInfoBean.setTownName(loanInfoBean1.getTownName());
                        }
                        if (null != loanInfoBean.getVillage() && !"".equals(loanInfoBean.getVillage())) {
                            loanInfoBean.setVillageName(loanInfoBean1.getVillageName());
                        }
                        if (null != loanInfoBean1.getCreateOn()) {
                            loanInfoBean.setCollectionDateStr(DateUtil.formatDate(loanInfoBean1.getCreateOn(), "yyyy-MM-dd"));
                        }
                        loanInfoBean.setCreateBy(loanInfoBean1.getCreateBy());
                            loanInfoBeans.add(loanInfoBean);
                    }catch (Exception e) {
                        System.out.println(updateLog.getNewContent());
                    }

                }
            }
            List<String> title = new ArrayList<String>();
            title.add("姓名");
            title.add("证件号码");
            title.add("联系电话");
            title.add("贷款账号");
            title.add("首贷金额");
            title.add("原利率");
            title.add("执行利率");
            title.add("结欠本金");
            title.add("结欠利息");
            title.add("所属机构");
            title.add("县");
            title.add("镇");
            title.add("村");
            title.add("详细住址");
//            title.add("配偶信息");
//            title.add("担保人信息");
            title.add("创建时间");
            title.add("创建者");
            title.add("备注");


            List<String> dataOrder = new ArrayList<String>();
            dataOrder.add("name");
            dataOrder.add("idNum");
            dataOrder.add("mobile");
            dataOrder.add("loanAccountNum");
            dataOrder.add("earliestLoanAmount");
            dataOrder.add("loanOldMonthRate");
            dataOrder.add("loanNewMonthRate");
            dataOrder.add("leftCapitalAmount");
            dataOrder.add("leftInterestAmount");
            dataOrder.add("orgName");
            dataOrder.add("countyName");
            dataOrder.add("townName");
            dataOrder.add("villageName");
            dataOrder.add("addr");
//            dataOrder.add("mates");
//            dataOrder.add("guarantees");
            dataOrder.add("createOn");
            dataOrder.add("createBy");
            dataOrder.add("remark");

            List<Map<String, Object>> data = gson.fromJson(gson.toJson(loanInfoBeans), List.class);
            ExcelUtil.exportExcelBigData(response, "客户修改信息.xls", title, dataOrder, data);
        }
    }
}

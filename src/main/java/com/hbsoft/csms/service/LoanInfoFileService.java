package com.hbsoft.csms.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hb.bean.CallResult;
import com.hbsoft.common.constant.Constant;
import com.hbsoft.common.enumtype.LoanFileTypeEnum;
import com.hbsoft.csms.bean.LoanFileInfoBean;
import com.hbsoft.csms.dao.service.LoanFileInfoBeanDaoService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.Date;
import java.util.List;

@Service
public class LoanInfoFileService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoanFileInfoBeanDaoService loanFileInfoBeanDaoService;

    @Transactional
    public CallResult<String> handleLoanFile(String loanFiles,Integer relId,String userId,String loanFileType) throws  Exception{
        logger.info("loanFiles:{},relId:{},loanFileType:{}",loanFiles,relId,loanFileType);
        CallResult<String> result = new CallResult<>();
        if(StringUtils.isNotBlank(loanFiles)) {
            List<LoanFileInfoBean> loanFileList = new Gson().fromJson(loanFiles, new TypeToken<List<LoanFileInfoBean>>() {
            }.getType());
            if (ObjectUtils.isNotEmpty(loanFileList)) {
                for (LoanFileInfoBean lf : loanFileList) {
                    if (lf.getId_prikey() != null) {
                        if (lf.getDelFlag() == Constant.DELFLAG) {
                            loanFileInfoBeanDaoService.removeOne(lf.getId_prikey());
                        } else {
                            loanFileInfoBeanDaoService.set(lf);
                        }
                    } else {
                        lf.setType(loanFileType);
                        lf.setRelId(relId);
                        lf.setCreateBy(userId);
                        lf.setCreateOn(new Date());
                        loanFileInfoBeanDaoService.add(lf);
                    }
                }
            }
        }
        result.setSuccessResult();
        return result;
    }
}

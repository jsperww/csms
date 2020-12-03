package com.hbsoft.csms.service;

import com.hb.bean.hbcmsql.HB_User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SessionService {

    public HB_User getHbUserBySession(HttpSession session){
        return  (HB_User) session.getAttribute("hb_user");
    }
}

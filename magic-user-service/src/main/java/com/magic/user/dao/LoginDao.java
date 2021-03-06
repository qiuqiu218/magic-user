package com.magic.user.dao;

import com.magic.api.commons.atlas.core.mybatis.MyBatisDaoImpl;
import com.magic.user.entity.Login;
import com.magic.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class LoginDao extends MyBatisDaoImpl<Login, Long> {

}
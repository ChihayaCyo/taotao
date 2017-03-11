package com.taotao.portal.service;

import com.taotao.pojo.TbUser;

/**
 * Created by zh on 3/10/2017.
 */
public interface UserService {

    TbUser getUserByToken(String token);

}

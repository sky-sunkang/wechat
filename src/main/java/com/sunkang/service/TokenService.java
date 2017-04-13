package com.sunkang.service;

import com.sunkang.common.Constants;
import com.sunkang.dao.AccessTokenRepository;
import com.sunkang.entity.AccessToken;
import com.sunkang.exception.TokenException;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 孙康
 * @date 2017/4/11
 * Describe：获得token的
 */
@Service
public class TokenService {

    private static final Logger log=Logger.getLogger(TokenService.class);

    @Autowired
    private AccessTokenRepository accessTokenRepository;


    /**
     * 从redis数据库中获取access_token
     * @return
     */
    public String getAccessToken(){
        return accessTokenRepository.getAccessToken("access_token").getAccessToken();
    }
}

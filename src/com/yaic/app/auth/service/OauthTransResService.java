/*
 * Created By lujicong (2016-05-03 14:19:38)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.app.auth.dao.OauthTransResDao;
import com.yaic.app.auth.dto.domain.OauthTransResDto;
import com.yaic.fa.service.BaseService;

@Service
public class OauthTransResService extends BaseService<OauthTransResDto> {
    @Autowired
    private OauthTransResDao oauthTransResDao;
    
    public List<OauthTransResDto> findResourceInfo(OauthTransResDto oauthTransResDto) {
        return oauthTransResDao.findResourceInfo(oauthTransResDto);
    }
}


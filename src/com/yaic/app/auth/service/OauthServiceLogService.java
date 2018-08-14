/*
 * Created By lujicong (2016-05-05 16:25:04)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.app.auth.dao.OauthServiceLogDao;
import com.yaic.app.auth.dto.domain.OauthServiceLogDto;
import com.yaic.fa.service.BaseService;

@Service
public class OauthServiceLogService extends BaseService<OauthServiceLogDto> {

    @Autowired
    private OauthServiceLogDao oauthServiceLogDao;

    public void clearDbLog() {
        oauthServiceLogDao.clearDbLog();
    }

}

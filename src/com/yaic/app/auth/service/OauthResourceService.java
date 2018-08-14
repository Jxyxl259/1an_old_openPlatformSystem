/*
 * Created By lujicong (2016-04-27 16:36:56)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.app.auth.dao.OauthResourceDao;
import com.yaic.app.auth.dto.domain.OauthResourceDto;
import com.yaic.fa.service.BaseService;

@Service
public class OauthResourceService extends BaseService<OauthResourceDto> {

    @Autowired
    private OauthResourceDao oauthResourceDao;

    public List<OauthResourceDto> findResourceInfo(OauthResourceDto oauthResourceDto) {
        return oauthResourceDao.findResourceInfo(oauthResourceDto);
    }
}

/*
 * Created By lujicong (2016-04-27 16:36:56)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.auth.dao;

import java.util.List;

import com.yaic.app.auth.dto.domain.OauthResourceDto;
import com.yaic.fa.dao.BaseDao;

public interface OauthResourceDao extends BaseDao<OauthResourceDto> {
    
    public List<OauthResourceDto> findResourceInfo(OauthResourceDto oauthResourceDto);
    
}

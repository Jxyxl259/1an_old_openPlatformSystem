/*
 * Created By lujicong (2016-05-03 14:19:38)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.auth.dao;

import java.util.List;

import com.yaic.app.auth.dto.domain.OauthTransResDto;
import com.yaic.fa.dao.BaseDao;

public interface OauthTransResDao extends BaseDao<OauthTransResDto> {
    public List<OauthTransResDto> findResourceInfo(OauthTransResDto oauthTransResDto);
}

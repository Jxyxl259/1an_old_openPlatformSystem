/*
 * Created By lujicong (2016-05-05 16:25:04)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.auth.dao;

import com.yaic.app.auth.dto.domain.OauthServiceLogDto;
import com.yaic.fa.dao.BaseDao;

public interface OauthServiceLogDao extends BaseDao<OauthServiceLogDto> {
    void clearDbLog();
}

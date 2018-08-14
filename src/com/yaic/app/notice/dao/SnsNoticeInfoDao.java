/*
 * Created By lujicong (2016-08-31 09:36:55)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.notice.dao;

import java.util.List;

import com.yaic.app.notice.dto.domain.SnsNoticeInfoDto;
import com.yaic.fa.dao.BaseDao;

public interface SnsNoticeInfoDao extends BaseDao<SnsNoticeInfoDto> {
    
    public List<SnsNoticeInfoDto> queryNoticeInfo(SnsNoticeInfoDto snsNoticeInfoDto);
    
}

/*
 * Created By lujicong (2016-06-02 10:11:06)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.callback.dao;

import java.util.List;

import com.yaic.app.callback.dto.domain.SnsCallbackInfoDto;
import com.yaic.fa.dao.BaseDao;

public interface SnsCallbackInfoDao extends BaseDao<SnsCallbackInfoDto> {
    
    public List<SnsCallbackInfoDto> queryCallbackInfo(SnsCallbackInfoDto snsCallbackInfoDto);
    
    public List<SnsCallbackInfoDto> queryDealProcessData(SnsCallbackInfoDto snsCallbackInfoDto);
}

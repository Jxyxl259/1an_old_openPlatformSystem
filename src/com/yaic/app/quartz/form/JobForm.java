/************************************************************************
 * 描述 ：数据库表PMS_JOB对应的表单对象，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-12-15 14:18:13
 *
 ************************************************************************/

package com.yaic.app.quartz.form;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yaic.app.quartz.dto.domain.JobDto;

public class JobForm extends JobDto {

    private static final long serialVersionUID = JobForm.class.getName().hashCode();
    
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

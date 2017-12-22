package com.tooklili.admin.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.tooklili.admin.web.interceptor.PermissionInterceptor;

/**
 * @author shuai.ding
 * @date 2017年12月22日下午3:21:56
 */
public class HasPermissionTag extends TagSupport{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 权限名称
	 */
	private String name;

	
	@Override
	public int doStartTag() throws JspException {
		 String permission = getName();

        if (permission == null || permission.length() == 0) {
            String msg = "The 'name' tag attribute must be set.";
            throw new JspException(msg);
        }
        boolean show = showTagBody(permission);
        if (show) {
            return TagSupport.EVAL_BODY_INCLUDE;
        } else {
            return TagSupport.SKIP_BODY;
        }
	}
	
	private boolean showTagBody(String p){
		return PermissionInterceptor.isPermitted(p);
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

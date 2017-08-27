package com.tooklili.admin.web.permission;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;
import com.tooklili.admin.web.exception.UnauthorizedException;
import com.tooklili.service.util.MessageUtils;

public class PermissionList implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String CREATE_PERMISSION = "create";
    public static final String UPDATE_PERMISSION = "update";
    public static final String DELETE_PERMISSION = "delete";
    public static final String VIEW_PERMISSION = "view";
    
    /**
     * 资源前缀
     */
    private String resourceIdentity;
    
    /**
     * key:    权限
     * value : 资源
     */
    private Map<String, String> resourcePermissions = Maps.newHashMap();


    /**
     * 自动生成 create update delete 的权限串
     *
     * @param resourceIdentity
     * @return
     */
    public static PermissionList newPermissionList(String resourceIdentity) {

        PermissionList permissionList = new PermissionList();

        permissionList.resourceIdentity = resourceIdentity;

        permissionList.resourcePermissions.put(CREATE_PERMISSION, resourceIdentity + ":" + CREATE_PERMISSION);
        permissionList.resourcePermissions.put(UPDATE_PERMISSION, resourceIdentity + ":" + UPDATE_PERMISSION);
        permissionList.resourcePermissions.put(DELETE_PERMISSION, resourceIdentity + ":" + DELETE_PERMISSION);
        permissionList.resourcePermissions.put(VIEW_PERMISSION, resourceIdentity + ":" + VIEW_PERMISSION);

        return permissionList;
    }
    
    /**
     * 添加权限 自动生成如showcase:sample:permission
     *
     * @param permission
     */
    public void addPermission(String permission) {
        resourcePermissions.put(permission, resourceIdentity + ":" + permission);
    }
    
    public void assertHasCreatePermission() {
        assertHasPermission(CREATE_PERMISSION, "no.create.permission");
    }

    public void assertHasUpdatePermission() {
        assertHasPermission(UPDATE_PERMISSION, "no.update.permission");
    }

    public void assertHasDeletePermission() {
        assertHasPermission(DELETE_PERMISSION, "no.delete.permission");
    }


    public void assertHasViewPermission() {
        assertHasPermission(VIEW_PERMISSION, "no.view.permission");
    }
    
    public void assertHasPermission(String permission, String errorCode) {
        if (StringUtils.isEmpty(errorCode)) {
            errorCode = getDefaultErrorCode();
        }
        String resourcePermission = resourcePermissions.get(permission);
        if (resourcePermission == null) {
            resourcePermission = this.resourceIdentity + ":" + permission;
        }
        boolean flag = false;
        if (!flag) {
            throw new UnauthorizedException(MessageUtils.message(errorCode, resourcePermission));
        }
    }
    
    private String getDefaultErrorCode() {
        return "no.permission";
    }

}

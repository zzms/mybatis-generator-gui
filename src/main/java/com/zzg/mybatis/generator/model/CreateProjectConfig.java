package com.zzg.mybatis.generator.model;

/**
 * Created by leosoft on 2018/9/12.
 */
public class CreateProjectConfig {

    private String projectName;
    private String packageName;
    private String saveUrl;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSaveUrl() {
        return saveUrl;
    }

    public void setSaveUrl(String saveUrl) {
        this.saveUrl = saveUrl;
    }
}

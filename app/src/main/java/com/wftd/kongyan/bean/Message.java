package com.wftd.kongyan.bean;

/**
 * Created by liwei on 2018/6/19.
 */

public class Message {

    /**
     * id : 1
     * peopleId : gysc-010-000-00
     * title : 催缴消息
     * content :  xxx医院xx医师，您好据系统显示您最近一周未提交采集数据，如有特殊情况，请及时向项目组反馈，以免延误项目进行。敬请了解。谢谢配合！
     */

    private String id;
    private String peopleId;
    private String title;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(String peopleId) {
        this.peopleId = peopleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

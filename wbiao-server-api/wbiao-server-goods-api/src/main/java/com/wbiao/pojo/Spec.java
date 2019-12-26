package com.wbiao.pojo;

import java.io.Serializable;

public class Spec implements Serializable {
    private Integer id;
    private String name;
    private Integer group_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    @Override
    public String toString() {
        return "Spec{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", group_id=" + group_id +
                '}';
    }
}

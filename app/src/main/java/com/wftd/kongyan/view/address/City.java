package com.wftd.kongyan.view.address;

public class City implements CityInterface {
    private int id; //id
    private String name; //city
    private String code; //cityid
    private String parent = "0000";//父级id 默认0000

    public City(int id, String name, String code, String parent) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.parent = parent;
    }

    //省
    public City(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public City(String name, String code, String parent) {
        this.name = name;
        this.code = code;
        this.parent = parent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String getCityName() {
        return name;
    }

    @Override
    public String getCityCode() {
        return code;
    }
}

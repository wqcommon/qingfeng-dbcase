package com.dbcase.entity;


import com.dbcase.dbType.JsonbType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.geo.Point;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "ac_user",schema = "public")
@TypeDefs({@TypeDef(name = "JsonbType",typeClass = JsonbType.class),@TypeDef(name = "JsonType",typeClass = JsonbType.class)})
public class AcUserEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 不显示的映射数据字段名，默认跟属性名一致
     */
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(columnDefinition = "jsonb")
    @Type(type = "JsonbType")
    private Map<String,Object> info;

    @Column(name = "info2")
    private String info2;

    public int getId() {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }
}

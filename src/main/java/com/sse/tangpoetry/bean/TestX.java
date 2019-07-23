package com.sse.tangpoetry.bean;

import com.sun.javafx.beans.IDProperty;
import org.apache.ibatis.annotations.Property;

import java.util.Date;

public class TestX {

    Integer x1;
    String x2;
    Date x3;
    Date x4;

    @Override
    public String toString() {
        return "TestX{" +
                "x1=" + x1 +
                ", x2='" + x2 + '\'' +
                ", x3=" + x3 +
                ", x4=" + x4 +
                '}';
    }

    public Integer getX1() {
        return x1;
    }

    public void setX1(Integer x1) {
        this.x1 = x1;
    }

    public String getX2() {
        return x2;
    }

    public void setX2(String x2) {
        this.x2 = x2;
    }

    public Date getX3() {
        return x3;
    }

    public void setX3(Date x3) {
        this.x3 = x3;
    }

    public Date getX4() {
        return x4;
    }

    public void setX4(Date x4) {
        this.x4 = x4;
    }
}

package com.sqx.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestWrite {

    public static void main(String[] args) {

        // 写操作
        // 构建List集合
        List<UserDataTest> list = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            UserDataTest data = new UserDataTest();
            data.setUid(i);
            data.setUsername("lucy");
            list.add(data);
        }
        // 设置excel文件路径
        String fileName = "E:\\javaProjects\\resources\\01.xlsx";

        // 调用方法实现写操作
        EasyExcel.write(fileName, UserDataTest.class).sheet("用户信息")
        .doWrite(list);

    }
}

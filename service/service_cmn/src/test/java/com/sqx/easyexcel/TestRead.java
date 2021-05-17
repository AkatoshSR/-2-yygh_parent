package com.sqx.easyexcel;

import com.alibaba.excel.EasyExcel;

public class TestRead {

    public static void main(String[] args) {

        // 读取文件名
        String fileName = "E:\\javaProjects\\resources\\01.xlsx";

        // 调用方法
        EasyExcel.read(fileName,UserDataTest.class, new ExcelListener())
        .sheet().doRead();

    }

}

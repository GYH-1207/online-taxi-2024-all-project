package com.xiaoxi.serviceDriverUser.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MysqlGenerator {
    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-driver-user?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                "root","gyh155222334")
                .globalConfig(builder -> {
                    builder.author("小汐").fileOverride().outputDir("D:\\data\\马士兵\\网约车\\代码\\online-taxi-2023-myself\\service-driver-user\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.xiaoxi.serviceDriverUser").pathInfo(Collections.singletonMap(OutputFile.mapper,
                            "D:\\data\\马士兵\\网约车\\代码\\online-taxi-2023-myself\\service-driver-user\\src\\main\\java\\com\\xiaoxi\\serviceDriverUser\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("driver_user_work_status");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}

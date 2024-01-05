package com.xiaoxi.servicePrice.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MysqlGenerator {
    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-price?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                "root","gyh155222334")
                .globalConfig(builder -> {
                    builder.author("小汐").fileOverride().outputDir("D:\\data\\马士兵\\网约车\\代码\\online-taxi-2023-myself\\service-price\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.xiaoxi.servicePrice").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "D:\\data\\马士兵\\网约车\\代码\\online-taxi-2023-myself\\service-price\\src\\main\\java\\com\\xiaoxi\\servicePrice\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("price_rule");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}

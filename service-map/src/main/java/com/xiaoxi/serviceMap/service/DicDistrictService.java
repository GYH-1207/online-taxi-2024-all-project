package com.xiaoxi.serviceMap.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.xiaoxi.interfaceCommon.constant.AMapConfigConstants;
import com.xiaoxi.interfaceCommon.constant.CommonStatusEumn;
import com.xiaoxi.interfaceCommon.dto.DicDistrict;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceMap.mapper.DicDistrictMapper;
import com.xiaoxi.serviceMap.romete.MapDicDistrictClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DicDistrictService {

    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    /**
     * 将行政区域信息 通过高德API 录入数据库
     * @param keywords
     * @return
     */
    @Transactional
    public ResponseResult initDicDistrict(String keywords) {
        //调用api，获取行政区域信息
        String dicDistrictResult = mapDicDistrictClient.dicDistrict(keywords);
//        System.out.println(dicDistrictResult);
        //解析结果
        JSONObject districtJsonObject = JSONObject.parseObject(dicDistrictResult);
        int status = districtJsonObject.getIntValue(AMapConfigConstants.STATUS);
        if(status != 1) {
            return ResponseResult.fail(CommonStatusEumn.MAP_DIC_DISTRICT_ERROR.getCode(),CommonStatusEumn.MAP_DIC_DISTRICT_ERROR.getValue());
        }

        JSONArray districts = districtJsonObject.getJSONArray("districts");
        //递归调用返回结果
        List<DicDistrict> ans = new ArrayList<>();
        parseDistricts(districts,ans,"0",new HashSet<>());
        System.out.println(ans);

        //插入数据库
        for (DicDistrict dicDistrict : ans) {
            dicDistrictMapper.insert(dicDistrict);
        }

        return ResponseResult.success();
    }


    private void parseDistricts(JSONArray districts, List<DicDistrict> ans, String parentAddressCode, Set<String> hash) {
        if(districts.isEmpty()) {
            return;
        }
        for(int i = 0;i < districts.size();i++) {
            //这一层的信息
            JSONObject jsonObject = districts.getJSONObject(i);
            String addressCode = jsonObject.getString("adcode");
            String addressName = jsonObject.getString("name");
            String level = jsonObject.getString("level");
            //存储信息
            DicDistrict dicDistrict = new DicDistrict();
            dicDistrict.setAddressCode(addressCode);
            dicDistrict.setAddressName(addressName);
            dicDistrict.setLevel(parseLevel(level));
            dicDistrict.setParentAddressCode(parentAddressCode);
            if(!hash.contains(addressCode)) {
                hash.add(addressCode);
                ans.add(dicDistrict);
            }
            parseDistricts(jsonObject.getJSONArray("districts"),ans,addressCode,hash);
        }
    }

    public int parseLevel(String level) {
        int levelInt = 0;
        if(level.trim().equals("country")) {
            levelInt = 0;
        }else if (level.trim().equals("province")) {
            levelInt = 1;
        }else if (level.trim().equals("city")) {
            levelInt = 2;
        }else if (level.trim().equals("district")) {
            levelInt = 3;
        }
        return levelInt;
    }

//    public static void main(String[] args) {
//        String url = "{\n" +
//                "  \"status\": \"1\",\n" +
//                "  \"info\": \"OK\",\n" +
//                "  \"infocode\": \"10000\",\n" +
//                "  \"count\": \"1\",\n" +
//                "  \"suggestion\": {\n" +
//                "    \"keywords\": [\n" +
//                "      \n" +
//                "    ],\n" +
//                "    \"cities\": [\n" +
//                "      \n" +
//                "    ]\n" +
//                "  },\n" +
//                "  \"districts\": [\n" +
//                "    {\n" +
//                "      \"citycode\": \"022\",\n" +
//                "      \"adcode\": \"120119\",\n" +
//                "      \"name\": \"蓟州区\",\n" +
//                "      \"center\": \"117.408432,40.046544\",\n" +
//                "      \"level\": \"district\",\n" +
//                "      \"districts\": [\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"上仓镇\",\n" +
//                "          \"center\": \"117.406500,39.939171\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"东二营镇\",\n" +
//                "          \"center\": \"117.261968,39.947072\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"出头岭镇\",\n" +
//                "          \"center\": \"117.654884,40.075010\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"桑梓镇\",\n" +
//                "          \"center\": \"117.141796,39.919067\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"尤古庄镇\",\n" +
//                "          \"center\": \"117.205854,39.902874\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"礼明庄镇\",\n" +
//                "          \"center\": \"117.405642,39.935091\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"东赵各庄镇\",\n" +
//                "          \"center\": \"117.328769,39.983616\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"洇溜镇\",\n" +
//                "          \"center\": \"117.371603,39.981903\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"孙各庄满族乡\",\n" +
//                "          \"center\": \"117.625710,40.146945\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"罗庄子镇\",\n" +
//                "          \"center\": \"117.367641,40.173189\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"下营镇\",\n" +
//                "          \"center\": \"117.571996,40.138389\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"渔阳镇\",\n" +
//                "          \"center\": \"117.374915,40.072957\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"西龙虎峪镇\",\n" +
//                "          \"center\": \"117.795079,40.003402\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"许家台镇\",\n" +
//                "          \"center\": \"117.223717,40.094982\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"文昌街道\",\n" +
//                "          \"center\": \"117.364354,40.031821\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"官庄镇\",\n" +
//                "          \"center\": \"117.342728,40.099191\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"穿芳峪镇\",\n" +
//                "          \"center\": \"117.658102,40.009860\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"马伸桥镇\",\n" +
//                "          \"center\": \"117.645424,40.107479\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"州河湾镇\",\n" +
//                "          \"center\": \"117.471264,40.025038\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"下窝头镇\",\n" +
//                "          \"center\": \"117.432683,39.840030\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"白涧镇\",\n" +
//                "          \"center\": \"117.217391,40.064636\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"邦均镇\",\n" +
//                "          \"center\": \"117.274502,40.015031\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"别山镇\",\n" +
//                "          \"center\": \"117.505573,39.915426\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"杨津庄镇\",\n" +
//                "          \"center\": \"117.533196,39.854462\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"下仓镇\",\n" +
//                "          \"center\": \"117.409790,39.770304\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"侯家营镇\",\n" +
//                "          \"center\": \"117.378478,39.787680\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"citycode\": \"022\",\n" +
//                "          \"adcode\": \"120119\",\n" +
//                "          \"name\": \"东施古镇\",\n" +
//                "          \"center\": \"117.316668,39.916437\",\n" +
//                "          \"level\": \"street\",\n" +
//                "          \"districts\": [\n" +
//                "            \n" +
//                "          ]\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//
//        JSONObject districtJsonObject = JSONObject.parseObject(url);
//        int status = districtJsonObject.getIntValue(AMapConfigConstants.STATUS);
//
//        JSONArray districts = districtJsonObject.getJSONArray("districts");
//
//        List<DicDistrict> ans = new ArrayList<>();
//        parseDistricts(districts,ans,"0");
//
//        System.out.println(ans);
//
//    }

}

package com.xiaoxi.interfaceCommon.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName driver_user
*/
@Data
public class DriverUser implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 注册地行政区划代码
    */
    @ApiModelProperty("注册地行政区划代码")
    private String address;
    /**
    * 机动车驾驶员姓名
    */
    @Size(max= 16,message="编码长度不能超过16")
    @ApiModelProperty("机动车驾驶员姓名")
    @Length(max= 16,message="编码长度不能超过16")
    private String driverName;
    /**
    * 驾驶员手机号
    */
    @Size(max= 16,message="编码长度不能超过16")
    @ApiModelProperty("驾驶员手机号")
    @Length(max= 16,message="编码长度不能超过16")
    private String driverPhone;
    /**
    * 1：男，2：女
    */
    @ApiModelProperty("1：男，2：女")
    private Integer driverGender;
    /**
    * 出生日期
    */
    @ApiModelProperty("出生日期")
    private Date driverBirthday;
    /**
    * 驾驶员民族
    */
    @ApiModelProperty("驾驶员民族")
    private String driverNation;
    /**
    * 驾驶员通信地址
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("驾驶员通信地址")
    @Length(max= 255,message="编码长度不能超过255")
    private String driverContactAddress;
    /**
    * 机动车驾驶证号
    */
    @Size(max= 128,message="编码长度不能超过128")
    @ApiModelProperty("机动车驾驶证号")
    @Length(max= 128,message="编码长度不能超过128")
    private String licenseId;
    /**
    * 初次领取驾驶证日期
    */
    @ApiModelProperty("初次领取驾驶证日期")
    private Date getDriverLicenseDate;
    /**
    * 驾驶证有效期限起
    */
    @ApiModelProperty("驾驶证有效期限起")
    private Date driverLicenseOn;
    /**
    * 驾驶证有效期限止
    */
    @ApiModelProperty("驾驶证有效期限止")
    private Date driverLicenseOff;
    /**
    * 是否巡游出租车汽车驾驶员 1：是，0：否
    */
    @ApiModelProperty("是否巡游出租车汽车驾驶员 1：是，0：否")
    private Integer taxiDriver;
    /**
    * 网络预约出租车驾驶员资格证号
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("网络预约出租车驾驶员资格证号")
    @Length(max= 255,message="编码长度不能超过255")
    private String certificateOn;
    /**
    * 网络预约出租汽车驾驶员证发证机构
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("网络预约出租汽车驾驶员证发证机构")
    @Length(max= 255,message="编码长度不能超过255")
    private String networkCarIssueOrganization;
    /**
    * 资格证发证日期
    */
    @ApiModelProperty("资格证发证日期")
    private Date networkCarIssueDate;
    /**
    * 初次领取资格证日期
    */
    @ApiModelProperty("初次领取资格证日期")
    private Date getNetworkCarProofDate;
    /**
    * 资格证有效起始日期
    */
    @ApiModelProperty("资格证有效起始日期")
    private Date networkCarProofOn;
    /**
    * 资格证有效截至日期
    */
    @ApiModelProperty("资格证有效截至日期")
    private Date networkCarProofOff;
    /**
    * 报备日期
    */
    @ApiModelProperty("报备日期")
    private Date registerDate;
    /**
    * 1：网络预约出租汽车，2：巡游出租汽车，3：私人小客车合乘
    */
    @ApiModelProperty("1：网络预约出租汽车，2：巡游出租汽车，3：私人小客车合乘")
    private Integer commercialType;
    /**
    * 驾驶员合同签署公司
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("驾驶员合同签署公司")
    @Length(max= 255,message="编码长度不能超过255")
    private String contractCompany;
    /**
    * 合同有效期起
    */
    @ApiModelProperty("合同有效期起")
    private Date contractOn;
    /**
    * 合同有效期止
    */
    @ApiModelProperty("合同有效期止")
    private Date contractOff;
    /**
    * 司机状态 0：有效，1：失效
    */
    @ApiModelProperty("司机状态 0：有效，1：失效")
    private Integer state;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date gmtCreate;
    /**
    * 修改时间
    */
    @ApiModelProperty("修改时间")
    private Date gmtModified;
}

package cn.test.bookms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsAdmin implements Serializable {
    private Integer id;

    private String adminNumber;

    private String adminPwd;

    private String adminName;

    private Date loginPreTime;

    private Integer delFlg;
    
    private Integer identityId;	// 身份id

}
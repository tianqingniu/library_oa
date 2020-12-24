package cn.test.bookms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsBook implements Serializable {
    private Integer id;

    private String title;

    private String isbn;

    private String author;

    private String introduction;

    private String price;

    private String publishTime;

    private Integer categoryId;

    private String image;

    private Date createTime;

    private String createAdmin;

    private String updatePreAdmin;

    private Integer delFlg;
    
    private Integer sum;
    
    private Integer remainder;

}
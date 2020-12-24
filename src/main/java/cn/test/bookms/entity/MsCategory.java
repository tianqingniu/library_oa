package cn.test.bookms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer categoryId;

    private String categoryName;

    private Integer delFlg;



}
package ql.vn.qlsp.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "blog")
@Data
public class BlogEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String title;
    private String conten;

    @Column(name = "namecreateuser")
    private String nameCreateUser;
    @Column(name = "createtime")
    private Date createTime;
    @Column(name = "imgmain")
    private String imgMain;
    @Column(name = "imgbackground")
    private String imgBackGround;
}

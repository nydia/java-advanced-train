package com.nydia.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * <p>
 * 权限中心-菜单表
 * </p>
 *
 * @author lvhuaqiang
 * @since 2024-06-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("perm_menu")
public class PermMenu extends BaseEntity {

    @TableField("page_code")
    private String pageCode;

    @TableField("related_page_flag")
    private Boolean relatedPageFlag;

    @TableField("menu_name")
    private String menuName;

    @TableField("menu_code")
    private String menuCode;

    @TableField(value = "menu_code_segment")
    private String menuCodeSegment;

    @TableField("menu_icon")
    private String menuIcon;

    @TableField("menu_route")
    private String menuRoute;

    @TableField("tree_code")
    private String treeCode;

    @TableField("parent_id")
    private String parentId;

    @TableField(value = "state")
    private Boolean state;

    /**
     * 系统、厂标、用户,参加枚举 MetaPropertyEnum
     */
    @TableField(value = "category")
    private String category;

    @TableField(value = "belong_adm_id", fill = FieldFill.INSERT)
    private String belongAdmId;

    /**
     * 是、否 ,参加枚举 DownAdmFlagEnum
     */
    @TableField(value = "down_adm_flag")
    private Boolean downAdmFlag;

    /**
     * 创建时间
     */
//    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
//    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(value = "CREATE_BY", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新时间
     */
    @TableField(value = "MODIFY_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyTime;

    /**
     * 更新人
     */
    @TableField(value = "MODIFY_BY", fill = FieldFill.INSERT_UPDATE)
    private String modifyBy;

}

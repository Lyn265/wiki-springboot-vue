package com.lyn.wiki.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 电子书快照表
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="EbookSnapshot对象", description="电子书快照表")
public class EbookSnapshot implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "电子书id")
    private Long ebookId;

    @ApiModelProperty(value = "快照日期")
    private LocalDate date;

    @ApiModelProperty(value = "阅读数")
    private Integer viewCount;

    @ApiModelProperty(value = "点赞数")
    private Integer voteCount;

    @ApiModelProperty(value = "阅读增长")
    private Integer viewIncrease;

    @ApiModelProperty(value = "点赞增长")
    private Integer voteIncrease;


}

package com.lyn.wiki.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

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
@ApiModel(value="EbookSnapshot响应对象", description="电子书快照表")
public class EbookSnapshotResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "快照日期")
    @JsonFormat(pattern = "MM-dd",timezone = "Asia/Tokyo")
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

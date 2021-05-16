package com.lyn.wiki.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyn.wiki.domain.EbookSnapshot;
import com.lyn.wiki.resp.EbookSnapshotResp;

import java.util.List;

/**
 * <p>
 * 电子书快照表 Mapper 接口
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-13
 */
public interface EbookSnapshotMapper extends BaseMapper<EbookSnapshot> {

    void getSnapshot();

    List<EbookSnapshotResp> getStatistic();

    List<EbookSnapshotResp> get30Statistic();
}

package com.lyn.wiki.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyn.wiki.domain.EbookSnapshot;
import com.lyn.wiki.resp.EbookSnapshotResp;

import java.util.List;

/**
 * <p>
 * 电子书快照表 服务类
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-13
 */
public interface IEbookSnapshotService extends IService<EbookSnapshot> {

    void getSnapshot();

    List<EbookSnapshotResp> getStatistic();

    List<EbookSnapshotResp> get30Statistic();
}

package com.lyn.wiki.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyn.wiki.domain.EbookSnapshot;
import com.lyn.wiki.mapper.EbookSnapshotMapper;
import com.lyn.wiki.resp.EbookSnapshotResp;
import com.lyn.wiki.service.IEbookSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 电子书快照表 服务实现类
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-13
 */
@Service
public class EbookSnapshotServiceImpl extends ServiceImpl<EbookSnapshotMapper, EbookSnapshot> implements IEbookSnapshotService {
    @Autowired
    EbookSnapshotMapper snapshotMapper;

    @Override
    public void getSnapshot() {
        snapshotMapper.getSnapshot();
    }

    @Override
    public List<EbookSnapshotResp> getStatistic() {
        return snapshotMapper.getStatistic();
    }

    @Override
    public List<EbookSnapshotResp> get30Statistic() {
        return snapshotMapper.get30Statistic();
    }
}

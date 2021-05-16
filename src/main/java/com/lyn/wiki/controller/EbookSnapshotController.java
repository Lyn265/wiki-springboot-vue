package com.lyn.wiki.controller;


import com.lyn.wiki.resp.CommonResp;
import com.lyn.wiki.resp.EbookSnapshotResp;
import com.lyn.wiki.service.IEbookSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 电子书快照表 前端控制器
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-13
 */
@RestController
@RequestMapping("/ebook-snapshot")
public class EbookSnapshotController {
    @Autowired
    IEbookSnapshotService snapshotService;

    /**
     * 获取当天和前一天的电子书快照
     * @return
     */
    @GetMapping("/get-statistic")
    public CommonResp getStatistic(){
        CommonResp commonResp = new CommonResp();
       List<EbookSnapshotResp> list  = snapshotService.getStatistic();
        commonResp.setContent(list);
        return commonResp;
    }

    /**
     * 获取当天和前一天的电子书快照
     * @return
     */
    @GetMapping("/get-30-statistic")
    public CommonResp get30Statistic(){
        CommonResp commonResp = new CommonResp();
        List<EbookSnapshotResp> list  = snapshotService.get30Statistic();
        commonResp.setContent(list);
        return commonResp;
    }

}

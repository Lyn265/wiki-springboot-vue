package com.lyn.wiki.job;

import com.lyn.wiki.service.IEbookSnapshotService;
import com.lyn.wiki.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class EbookSnaptshotJob {

    @Autowired
    IEbookSnapshotService ebookSnapshotService;

    @Autowired
    SnowFlake snowFlake;


    @Scheduled(cron = "0 0/3 * * * ?")
    public void doSnapshot(){
        MDC.put("LOG_ID",String.valueOf(snowFlake.nextId()));
        Date date = new Date();
        log.info("更新电子书快照定时任务开始,当前时间:{}",date);
        long start = System.currentTimeMillis();
        ebookSnapshotService.getSnapshot();
        log.info("任务所需时间:{} 毫秒",System.currentTimeMillis() - start);


    }
}

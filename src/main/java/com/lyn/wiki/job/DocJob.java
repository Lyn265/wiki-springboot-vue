package com.lyn.wiki.job;

import com.lyn.wiki.service.IDocService;
import com.lyn.wiki.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DocJob {
    @Autowired
    IDocService docService;

    @Autowired
    SnowFlake snowFlake;

    @Scheduled(cron = "0 0/2 * * * ?")
    public void cron(){
        MDC.put("LOG_ID",String.valueOf(snowFlake.nextId()));
        log.info("更新电子书定时任务开始");
        long start = System.currentTimeMillis();
        docService.updateEbookInfo();
        log.info("任务所需时间:{} 毫秒",System.currentTimeMillis() - start);


    }
}

package dev.spider.service.impl;

import dev.spider.annotation.TimeBeat;
import dev.spider.entity.Result;
import dev.spider.service.TempService;
import org.springframework.stereotype.Service;

@Service
public class TempServiceImpl implements TempService {

    @TimeBeat
    @Override
    public Result foo() {
        return Result.success();
    }
}

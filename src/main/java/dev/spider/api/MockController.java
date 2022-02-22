package dev.spider.api;

import dev.spider.entity.Result;
import dev.spider.entity.ao.MockFoo;
import dev.spider.entity.dto.MockBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "mock")
public class MockController {

    private Logger logger = LoggerFactory.getLogger(MockController.class);

    @PostMapping("simple")
    public Result mock(@RequestBody MockFoo req) {
        MockBar mockBar = invokeRemote(req);
        return Result.success(mockBar);
    }

    public MockBar invokeRemote(MockFoo req) {
        //
        return new MockBar(req.getOrderNo(), "1");
    }
}

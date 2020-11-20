package com.yishang.xiaozhen.controller.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping
    public String listTasks(){
        return "任务列表";
    }

    /**
     *  查看当前登录信息
     * @return
     */
    @GetMapping("/body")
    public String newTasks(){
        if(SecurityContextHolder.getContext() == null) {
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication：{}",authentication);
        return "创建了一个新的任务";
    }

    @PostMapping("/{taskId}")
    public String updateTasks(@PathVariable("taskId")Integer id){
        return "更新了一下id为:"+id+"的任务";
    }

    @GetMapping("/delete/{taskId}")
    public String deleteTasks(@PathVariable("taskId")Integer id){
        return "删除了id为:"+id+"的任务";
    }
}

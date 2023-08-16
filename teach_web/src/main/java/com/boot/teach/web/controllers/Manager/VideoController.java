package com.boot.teach.web.controllers.Manager;

import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.vo.manager.manager.EditVideoVO;
import com.boot.teach.vo.manager.manager.QueryVideoVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(value = "管理员接口",tags = "管理员接口")
@RestController
@RequestMapping("/manager")
public class VideoController {
    @PostMapping(value = "videoList")
    public ServerResponseEntity videoManage(@RequestBody QueryVideoVO queryVedioVO){
        return null;
    }

    @PostMapping(value = "editVideo")
    public ServerResponseEntity editVideo(@RequestBody EditVideoVO editVideoVO){
        return null;
    }
}

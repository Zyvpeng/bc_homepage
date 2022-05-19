package com.bc208.blog.controller;

import com.bc208.blog.pojo.TeamTask;
import com.bc208.blog.pojo.UserTask;
import com.bc208.blog.service.TeamTaskService;
import com.bc208.blog.utils.ParamsException;
import com.bc208.blog.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bc208/api")
public class TeamTaskController {
    @Autowired
    private TeamTaskService teamTaskService;

    @RequestMapping("/teamtasks")
    @ResponseBody
    public ResultInfo ShowAllTasks(){
        ResultInfo resultInfo =new ResultInfo();
        List<TeamTask> teamTasks = teamTaskService.teamTodolist(0);



        try{
            resultInfo.setResult(teamTasks);
        }catch(ParamsException p){
            resultInfo.setCode(p.getCode());
            resultInfo.setMsg(p.getMsg());
            p.printStackTrace();
        }catch (Exception e){
            resultInfo.setCode(500);
            resultInfo.setMsg("查找失败");
        }
        System.out.println(resultInfo);
        System.out.println(teamTasks);

        return resultInfo;




    }
}

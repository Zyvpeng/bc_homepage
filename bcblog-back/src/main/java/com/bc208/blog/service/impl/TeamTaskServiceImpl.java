package com.bc208.blog.service.impl;

import com.bc208.blog.dao.TeamTasksMapper;
import com.bc208.blog.pojo.TeamTask;
import com.bc208.blog.pojo.UserTask;
import com.bc208.blog.service.TeamTaskService;
import com.bc208.blog.utils.nullOrNot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamTaskServiceImpl implements TeamTaskService {
    @Autowired
    private TeamTasksMapper tasksMapper;

    @Transactional
    public List<TeamTask> teamTodolist(Integer type) {
        List<TeamTask> teamTasks = tasksMapper.queryTeamTodoList(0);
        nullOrNot.istrue( teamTasks == null, "未找到您的任务");//判断是否存在task
        return teamTasks;
    }
}

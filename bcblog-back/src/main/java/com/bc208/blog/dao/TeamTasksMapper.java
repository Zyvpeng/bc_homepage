package com.bc208.blog.dao;
import com.bc208.blog.pojo.TeamTask;
import com.bc208.blog.pojo.UserTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TeamTasksMapper {
    List<TeamTask> queryTeamTodoList(@Param("task_type") Integer task_type );



}

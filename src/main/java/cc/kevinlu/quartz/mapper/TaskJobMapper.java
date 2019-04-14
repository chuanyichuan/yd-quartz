package cc.kevinlu.quartz.mapper;

import cc.kevinlu.quartz.model.TaskJobDO;
import cc.kevinlu.quartz.model.TaskJobDOExample;
import java.util.List;

public interface TaskJobMapper {
    long countByExample(TaskJobDOExample example);

    int deleteByExample(TaskJobDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TaskJobDO record);

    int insertSelective(TaskJobDO record);

    List<TaskJobDO> selectByExample(TaskJobDOExample example);

    TaskJobDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskJobDO record);

    int updateByPrimaryKey(TaskJobDO record);
}
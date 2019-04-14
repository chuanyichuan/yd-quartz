package cc.kevinlu.quartz.mapper;

import cc.kevinlu.quartz.model.TaskErrorRecordDO;
import cc.kevinlu.quartz.model.TaskErrorRecordDOExample;
import java.util.List;

public interface TaskErrorRecordMapper {
    long countByExample(TaskErrorRecordDOExample example);

    int deleteByExample(TaskErrorRecordDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TaskErrorRecordDO record);

    int insertSelective(TaskErrorRecordDO record);

    List<TaskErrorRecordDO> selectByExample(TaskErrorRecordDOExample example);

    TaskErrorRecordDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskErrorRecordDO record);

    int updateByPrimaryKey(TaskErrorRecordDO record);
}
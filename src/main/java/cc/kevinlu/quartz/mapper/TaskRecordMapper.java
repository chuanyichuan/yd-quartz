package cc.kevinlu.quartz.mapper;

import cc.kevinlu.quartz.model.TaskRecordDO;
import cc.kevinlu.quartz.model.TaskRecordDOExample;
import java.util.List;

public interface TaskRecordMapper {
    long countByExample(TaskRecordDOExample example);

    int deleteByExample(TaskRecordDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TaskRecordDO record);

    int insertSelective(TaskRecordDO record);

    List<TaskRecordDO> selectByExample(TaskRecordDOExample example);

    TaskRecordDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskRecordDO record);

    int updateByPrimaryKey(TaskRecordDO record);
}
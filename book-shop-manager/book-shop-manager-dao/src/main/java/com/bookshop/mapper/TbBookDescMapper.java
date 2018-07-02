package com.bookshop.mapper;

import com.bookshop.pojo.TbBookDesc;
import com.bookshop.pojo.TbBookDescExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbBookDescMapper {
    int countByExample(TbBookDescExample example);

    int deleteByExample(TbBookDescExample example);

    int deleteByPrimaryKey(Long bookId);

    int insert(TbBookDesc record);

    int insertSelective(TbBookDesc record);

    List<TbBookDesc> selectByExampleWithBLOBs(TbBookDescExample example);

    List<TbBookDesc> selectByExample(TbBookDescExample example);

    TbBookDesc selectByPrimaryKey(Long bookId);

    int updateByExampleSelective(@Param("record") TbBookDesc record, @Param("example") TbBookDescExample example);

    int updateByExampleWithBLOBs(@Param("record") TbBookDesc record, @Param("example") TbBookDescExample example);

    int updateByExample(@Param("record") TbBookDesc record, @Param("example") TbBookDescExample example);

    int updateByPrimaryKeySelective(TbBookDesc record);

    int updateByPrimaryKeyWithBLOBs(TbBookDesc record);

    int updateByPrimaryKey(TbBookDesc record);
}
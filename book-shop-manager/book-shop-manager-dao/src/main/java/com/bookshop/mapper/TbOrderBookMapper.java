package com.bookshop.mapper;

import com.bookshop.pojo.TbOrderBook;
import com.bookshop.pojo.TbOrderBookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbOrderBookMapper {
    int countByExample(TbOrderBookExample example);

    int deleteByExample(TbOrderBookExample example);

    int deleteByPrimaryKey(String id);

    int insert(TbOrderBook record);

    int insertSelective(TbOrderBook record);

    List<TbOrderBook> selectByExample(TbOrderBookExample example);

    TbOrderBook selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TbOrderBook record, @Param("example") TbOrderBookExample example);

    int updateByExample(@Param("record") TbOrderBook record, @Param("example") TbOrderBookExample example);

    int updateByPrimaryKeySelective(TbOrderBook record);

    int updateByPrimaryKey(TbOrderBook record);
}
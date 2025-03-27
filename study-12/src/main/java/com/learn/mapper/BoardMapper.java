package com.learn.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.learn.vo.BoardVO;

@Mapper
public interface BoardMapper {
	List<BoardVO> getBoards();
	BoardVO getBoardById(@Param("id") Long id);
	int createBoard(BoardVO boardVO);

}

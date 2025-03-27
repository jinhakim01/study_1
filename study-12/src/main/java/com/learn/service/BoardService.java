package com.learn.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.learn.mapper.BoardMapper;
import com.learn.vo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {

	@Autowired
	private BoardMapper boardMapper;
	private final Path uploadPath = Path.of(System.getProperty("user.dir"), "uploads");

	public List<BoardVO> getBoards() {
		List<BoardVO> result = null;
		result = boardMapper.getBoards();
		return result;
	}

	public BoardVO getBoardById(Long id) {
		BoardVO result = null;
		result = boardMapper.getBoardById(id);
		return result;
	}

	public Map<String, Object> createBoard(BoardVO boardVO, List<MultipartFile> files) {
		Map<String, Object> result = new HashMap<>();
		int createCnt = boardMapper.createBoard(boardVO);
		result.put("result", createCnt);

		boolean cond1 = files != null;
		boolean cond2 = files.size() > 0;
		if (cond1 && cond2) {
			for (MultipartFile file : files) {
				String randomUUID = UUID.randomUUID().toString();
				String originalFileName = file.getOriginalFilename();
				result.put(randomUUID, originalFileName);
				String Extension = originalFileName.substring(originalFileName.lastIndexOf("."));
				log.debug(randomUUID, " | ", originalFileName, " | ", Extension);
				log.info("writer", boardVO.getWriter());
				String saveFileName = randomUUID + Extension;
				Path target = uploadPath.resolve(saveFileName);
				try {
					file.transferTo(target);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("createBoard IllegalStateException raise!!!\n" + e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("createBoard IOException raise\n" + e.getMessage());
				} catch (Exception e) {
					log.error("createBoard Exception raise\n" + e.getMessage());
				}
			}
		}
		return result;
	}

}

package com.learn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.learn.service.BoardService;
import com.learn.vo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@GetMapping
	public List<BoardVO> getBoards(@RequestParam Map<String, Object> params) {
		log.info("*** getBoards Call! ***");
		Map<String, Object> result = new HashMap();
		List<BoardVO> content = new ArrayList<>();
		content = boardService.getBoards();
//		Map<String, Object> map1 = new HashMap<>();
//		try {
//			map1.put("content", Integer.valueOf((String) params.get("searchKeyWord")));
//		} catch (Exception e) {
//			map1.put("content", (String) params.get("searchKeyWord"));
//		}
//		map1.put("content", "test content1");
//		map1.put("writer","test Writer1");
//		map1.put("title","test Title1");
//		map1.put("id",1);
//		
//		Map<String, Object> map2 = new HashMap<>();
//		map2.put("content", "test Writer2");
//		map2.put("writer", "test Writer2");
//		map2.put("title", "test Title2");
//		map2.put("id", 2);
//		
//		content.add(map1);
//		content.add(map2);

		int totalPages = (int) Math.ceil(content.size() / 10.0);
		result.put("content", content);
		result.put("totalPages", totalPages);

		return content;
	}

	@GetMapping("/{id}")
	public BoardVO getBoardById(@PathVariable Long id) {
		BoardVO result = null;
//		result.put("content", "content text");
//		result.put("writer", "writer kjh");
//		result.put("title", "test title"+id);
//		result.put("createdAt", "2025/05/05"+id);
//		result.put("views",13);
//		result.put("id", id);
		result = boardService.getBoardById(id);
		return result;
	}

	@PostMapping
	public Map<String, Object> createBoard(MultipartHttpServletRequest req) {
		Map<String, Object> result = new HashMap<>();
		List<MultipartFile> files = new ArrayList<>();
		String writer = req.getParameter("writer");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		BoardVO boardVO = new BoardVO();
		boardVO.setWriter(writer);
		boardVO.setTitle(title);
		boardVO.setContent(content);
		boardVO.setCreatedId("writerId"); // "writerId"는 세션에서 취득

		for (int i = 0; i < req.getFiles("file").size(); i++) {
			files.add(req.getFiles("file").get(i));
		}
		result = boardService.createBoard(boardVO, files);

		return result;
	}
}

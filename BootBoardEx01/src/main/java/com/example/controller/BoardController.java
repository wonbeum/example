package com.example.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.model1.BoardDAO;
import com.example.model1.BoardTO;

@RestController
public class BoardController {
	
	@Autowired
	private BoardDAO dao;
	
	@RequestMapping("list.do")
	public ModelAndView list(Model model) {
		ModelAndView modelAndView = new ModelAndView();

		ArrayList<BoardTO> boardLists = dao.boardList();
		
		modelAndView.setViewName("board_list1");
		modelAndView.addObject("boardLists", boardLists );
		
		//model.addAttribute("boardLists", boardLists);	

		return modelAndView;
	}
	
	@RequestMapping("write.do")
	public ModelAndView write() {
		return new ModelAndView("board_write1");
	}
	
	@RequestMapping("write_ok.do")
	public ModelAndView wirte_ok( HttpServletRequest request , Model model ) {
		
		BoardTO to = new BoardTO();

		to.setSubject( request.getParameter( "subject" ) );
		to.setWriter( request.getParameter( "writer" ) );
		to.setMail( "" );
		if( !request.getParameter( "mail1" ).equals( "" ) 
				&& !request.getParameter( "mail2" ).equals( "" ) ) {
			to.setMail( request.getParameter( "mail1" ) + "@" + request.getParameter( "mail2" ) );
		}
		to.setPassword( request.getParameter( "password" ) );
		to.setContent( request.getParameter( "content" ) );
		to.setWip( request.getRemoteAddr() );
		
		
		int flag = dao.boardWriteOk( to );
		
		model.addAttribute("flag", flag);	

		return new ModelAndView("board_write1_ok");
	}
	
	@RequestMapping("view.do")
	public ModelAndView view(HttpServletRequest request , Model model) {
		BoardTO to = new BoardTO();
		to.setSeq( request.getParameter( "seq" ) );
		to = dao.boardView( to );

		model.addAttribute("to", to);
		return new ModelAndView("board_view1");
	}
	
	@RequestMapping("modify.do")
	public ModelAndView modify(HttpServletRequest request , Model model) {
		BoardTO to = new BoardTO();
		to.setSeq( request.getParameter( "seq" ) );
		to = dao.boardModify( to );

		model.addAttribute("to", to);
		return new ModelAndView("board_modify1");
	}
	
	@RequestMapping("modify_ok.do")
	public ModelAndView modify_ok(HttpServletRequest request , Model model) {
		
		BoardTO to = new BoardTO();
		to.setSeq( request.getParameter( "seq" ) );
		to.setSubject( request.getParameter( "subject" ) );
		to.setMail( "" );
		if(!request.getParameter( "mail1" ).equals( "" ) && !request.getParameter( "mail2" ).equals( "" )) {
			to.setMail( request.getParameter( "mail1" ) + "@" + request.getParameter( "mail2" ) );
		}
		to.setPassword( request.getParameter( "password" ) );
		to.setContent( request.getParameter( "content" ) );

		int flag = dao.boardModifyOk(to);
		
		model.addAttribute("flag", flag);
		model.addAttribute("seq", to.getSeq());
		return new ModelAndView("board_modify1_ok");
	}
	
	@RequestMapping("delete.do")
	public ModelAndView delete(HttpServletRequest request , Model model) {

		BoardTO to = new BoardTO();
		to.setSeq( request.getParameter( "seq" ) );
		to = dao.boardDelete( to );

		model.addAttribute("to", to);
		
		return new ModelAndView("board_delete1");
	}
	
	@RequestMapping("delete_ok.do")
	public ModelAndView delete_ok(HttpServletRequest request , Model model) {
		
		BoardTO to = new BoardTO();
		to.setSeq( request.getParameter( "seq" ) );
		to.setPassword( request.getParameter( "password" ) );

		int flag = dao.boardDeleteOk(to);
		
		model.addAttribute("flag", flag);
		return new ModelAndView("board_delete1_ok");
	}
}

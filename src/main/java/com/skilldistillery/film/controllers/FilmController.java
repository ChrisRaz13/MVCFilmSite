package com.skilldistillery.film.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skilldistillery.film.filmDAOImpl.FilmDAOImpl;

@Controller
public class FilmController {
	@Autowired
	private FilmDAOImpl filmDAO;
	
	@RequestMapping()
	public String home() {
		return "WEB-INF/views/home.jsp";
	}
}

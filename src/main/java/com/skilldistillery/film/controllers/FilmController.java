package com.skilldistillery.film.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.film.data.FilmDAOImpl;
import com.skilldistillery.film.entities.Film;

@Controller
public class FilmController {
	@Autowired
	private FilmDAOImpl filmDAO;

	@RequestMapping()
	public String home() {
		return "WEB-INF/views/home.jsp";
	}

	@RequestMapping(value = "createFilm", method = RequestMethod.POST)
	public ModelAndView createFilm(@RequestBody Film film) throws SQLException {
		ModelAndView mv = new ModelAndView();
		Film newFilm = filmDAO.createFilm(film);
		mv.addObject("state", newFilm);
		mv.setViewName("WEB-INF/results.jsp");
		return mv;
	}

	@RequestMapping(path = "filmbyID.do", params = "id", method = RequestMethod.GET)
	public ModelAndView getFilmById(@RequestParam("id") int id) throws SQLException {
	    ModelAndView mv = new ModelAndView();
	    Film film = filmDAO.findFilmById(id);
	    mv.addObject("film", film);
	    mv.setViewName("WEB-INF/results.jsp");
	    return mv;
	}

}

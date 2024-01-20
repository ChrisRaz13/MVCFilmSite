package com.skilldistillery.film.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
//	@RequestMapping(path = "createfilm.do", method = RequestMethod.POST)
//	public ModelAndView newState(Film film, RedirectAttributes redir) throws SQLException {
//		filmDAO.createFilm(film);
//		ModelAndView mv = new ModelAndView();
//		redir.addFlashAttribute("film", film); 
//		mv.setViewName("redirect:createnewfilm.do"); 
//		return mv;
//	}

	@RequestMapping(value = "createfilm.do", method = RequestMethod.GET)
	public ModelAndView createFilm(@RequestParam("id") int id, @RequestParam("title") String title,
			@RequestParam("description") String description, @RequestParam("language_id") int languageId)
			throws SQLException {
		Film film = new Film();
		film.setId(id);
		film.setTitle(title);
		film.setDescription(description);
		film.setLanguageId(languageId);
		ModelAndView mv = new ModelAndView();
		Film newFilm = filmDAO.createFilm(film);
		mv.addObject("film", newFilm);
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

	@RequestMapping(value = "deletefilm.do", method = RequestMethod.GET)
	public String deleteFilm(@RequestParam("filmId") int filmId) {
		try {
			boolean deletionResult = filmDAO.deleteFilm(filmId);

			if (deletionResult) {
				return "redirect:/success.html";
			} else {
				return "redirect:/error.html?error=1";
			}
		} catch (SQLException e) {
			return "redirect:/error.html?error=1";
		}
	}
	@RequestMapping("/redirectToHome")
	public String redirectToHome() {
	    return "redirect:/WEB-INF/views/home.jsp";
	}

	
	@RequestMapping(path = "filmbypattern.do", params = "filmKeyword", method = RequestMethod.GET)
	public ModelAndView getFilmByKeyword(@RequestParam("filmKeyword") String filmKeyword) throws SQLException {
	    ModelAndView mv = new ModelAndView();
	    List<Film> films = filmDAO.findFilmByKeyword(filmKeyword);
	    mv.addObject("films", films);
	    mv.setViewName("WEB-INF/results2.jsp"); 
	    return mv;
	}

	
	@RequestMapping(value = "updatefilm.do", method = RequestMethod.POST)
	public ModelAndView updateFilm(@ModelAttribute("film") Film film) throws SQLException {
		ModelAndView mv = new ModelAndView();
		boolean updateResult = filmDAO.updateFilm(film);
		mv.addObject("films", updateResult);
		mv.setViewName("WEB-INF/results.jsp"); 
		return mv;
		
	}

	
}

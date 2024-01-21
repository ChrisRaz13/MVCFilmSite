package com.skilldistillery.film.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    private static final String URL = "jdbc:mysql://localhost:3306/sdvid";
    private static final String USER = "student";
	private static final String PWD = "student";
	
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
	public ModelAndView deleteFilm(@RequestParam("filmId") int filmId) {
	    ModelAndView modelAndView = new ModelAndView();

	    try (Connection conn = DriverManager.getConnection(URL, USER, PWD)) {
	        boolean deletionResult = filmDAO.deleteFilm(filmId);

	        if (deletionResult) {
	            modelAndView.setViewName("redirect:/success.html");
	            modelAndView.addObject("successMessage", "Film deleted successfully.");
	        } else {
	            boolean hasChildRecords = filmDAO.hasChildRecords(filmId);

	            if (hasChildRecords) {
	                modelAndView.addObject("errorMessage", "Failed: Film has child records, cannot delete.");
	            } else {
	                modelAndView.addObject("errorMessage", "Failed: Film with the given ID does not exist.");
	            }

	            modelAndView.setViewName("redirect:/error.html");
	        }
	    } catch (SQLException e) {
	        modelAndView.addObject("errorMessage", "An error occurred while deleting the film.");
	        modelAndView.setViewName("redirect:/error.html");
	    }

	    return modelAndView;
	}

	
	@RequestMapping(path = "filmbypattern.do", params = "filmKeyword", method = RequestMethod.GET)
	public ModelAndView getFilmByKeyword(@RequestParam("filmKeyword") String filmKeyword) throws SQLException {
	    ModelAndView mv = new ModelAndView();
	    List<Film> films = filmDAO.findFilmByKeyword(filmKeyword);
	    mv.addObject("films", films);
	    mv.setViewName("WEB-INF/results2.jsp"); 
	    return mv;
	}

	
	 @RequestMapping(value = "updatefilm.do", method = RequestMethod.GET)
	    public ModelAndView showUpdateForm(@RequestParam("id") int id) throws SQLException {
	        Film film = filmDAO.findFilmById(id);
	        ModelAndView mv = new ModelAndView();
	        mv.addObject("film", film);
	        mv.setViewName("WEB-INF/views/results.jsp");
	        return mv;
	    }

	    @RequestMapping(value = "updatefilm.do", method = RequestMethod.POST)
	    @ResponseBody 
	    public boolean updateFilm(@ModelAttribute("film") Film film) throws SQLException {

	
	        return filmDAO.updateFilm(film);
	    }
	    

	
}

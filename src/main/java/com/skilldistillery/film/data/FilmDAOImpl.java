package com.skilldistillery.film.data;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.skilldistillery.film.entities.Film;

@Repository
public class FilmDAOImpl implements FilmDAO{
	
	
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid";
	private static final String USER = "student";
	private static final String PWD = "student";
	
	
	
	
	public Film createFilm(Film film) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean deleteFilm(Film film) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
}

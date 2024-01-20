package com.skilldistillery.film.data;

import java.sql.SQLException;

import com.skilldistillery.film.entities.Film;

public interface FilmDAO {
	 public Film createFilm(Film film) throws SQLException;
	  public boolean deleteFilm(Film film) throws SQLException;
}
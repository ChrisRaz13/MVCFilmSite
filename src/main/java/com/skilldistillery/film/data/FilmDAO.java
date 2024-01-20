package com.skilldistillery.film.data;

import java.sql.SQLException;
import java.util.List;

import com.skilldistillery.film.entities.Film;

public interface FilmDAO {
	public Film createFilm(Film film) throws SQLException;

	public boolean deleteFilm(int filmId) throws SQLException;

	public Film findFilmById(int filmId) throws SQLException;
	
	public  List<Film> findFilmByKeyword(String filmKeyword) throws SQLException;
	
	boolean updateFilm(Film film) throws SQLException;
}
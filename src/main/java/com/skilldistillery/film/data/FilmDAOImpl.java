package com.skilldistillery.film.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.skilldistillery.film.entities.Film;

@Repository
public class FilmDAOImpl implements FilmDAO {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid";
	private static final String USER = "student";
	private static final String PWD = "student";

	public Film createFilm(Film film) throws SQLException {
		Connection conn = DriverManager.getConnection(URL, USER, PWD);
		try {
			String sql = "INSERT INTO film (id, title, description, language_id) " + "VALUES (?,?,?,?)";

			conn.setAutoCommit(false);

			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, film.getId());
			stmt.setString(2, film.getTitle());
			stmt.setString(3, film.getDescription());
			stmt.setInt(4, film.getLanguageID());
			int updateCount = stmt.executeUpdate();
			if (updateCount == 1) {
				ResultSet keys = stmt.getGeneratedKeys();
				if (keys.next()) {
					int newFilmId = keys.getInt(1);
					film.setId(newFilmId);
				}
			} else {
				film = null;
			}
			conn.commit();
		} catch (SQLException sql) {
			sql.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException sqle2) {
					System.err.println("Error trying to rollback");
				}
			}
			throw new RuntimeException("Error inserting film " + film);
		}
		return film;
	}

	public boolean deleteFilm(Film film) throws SQLException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PWD);
			conn.setAutoCommit(false); // START TRANSACTION
			String sql = "DELETE FROM film WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, film.getId());
			int updateCount = stmt.executeUpdate();
			sql = "DELETE FROM actor WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, film.getId());
			updateCount = stmt.executeUpdate();
			conn.commit(); // COMMIT TRANSACTION
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException sqle2) {
					System.err.println("Error trying to rollback");
				}
			}
			return false;
		}
		return true;

	}
}

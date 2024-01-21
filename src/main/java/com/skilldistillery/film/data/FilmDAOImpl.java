package com.skilldistillery.film.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.skilldistillery.film.entities.Film;

@Repository
public class FilmDAOImpl implements FilmDAO {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid";
	private static final String USER = "student";
	private static final String PWD = "student";

	public Film findFilmById(int filmId) throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Film film = null;
		String sql = "SELECT film.*, language.id, language.name FROM film JOIN language ON film.language_id = language.id WHERE film.id = ?";
		Connection conn = DriverManager.getConnection(URL, USER, PWD);

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);

		ResultSet filmResult = stmt.executeQuery();
		if (filmResult.next()) {
			film = new Film();
			film.setId(filmResult.getInt("id"));
			film.setTitle(filmResult.getString("title"));
			film.setReleaseYear(filmResult.getInt("release_year"));
			film.setRating(filmResult.getString("rating"));
			film.setDescription(filmResult.getString("description"));
			film.setLanguage(filmResult.getString("language.name"));

		}

		filmResult.close();
		stmt.close();
		conn.close();

		return film;
	}

	public Film createFilm(Film film) throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = DriverManager.getConnection(URL, USER, PWD);
		try {
			String sql = "INSERT INTO film (id, title, description, language_id) " + "VALUES (?,?,?,?)";

			conn.setAutoCommit(false);

			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, film.getId());
			stmt.setString(2, film.getTitle());
			stmt.setString(3, film.getDescription());
			stmt.setInt(4, film.getLanguageId());
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

//	@Override
//	public boolean updateFilm(Film film) throws SQLException {
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String updateSql = "UPDATE film SET title = ?, description = ?, release_year = ?, "
//				+ "language_id = ?, rating = ? WHERE id = ?";
//
//		try (Connection conn = DriverManager.getConnection(URL, USER, PWD);
//				PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
//
//			conn.setAutoCommit(false);
//
//			try {
//				updateStmt.setString(1, film.getTitle());
//				updateStmt.setString(2, film.getDescription());
//				updateStmt.setInt(3, film.getReleaseYear());
//				updateStmt.setInt(4, film.getLanguageId());
//				updateStmt.setString(5, film.getRating());
//				updateStmt.setInt(6, film.getId());
//
//				int rowsAffected = updateStmt.executeUpdate();
//
//				if (rowsAffected > 0) {
//					conn.commit();
//					return true;
//				} else {
//					conn.rollback();
//					return false;
//				}
//			} catch (SQLException e) {
//				conn.rollback();
//				throw e;
//			}
//		}
//	}

	public boolean deleteFilm(int filmId) throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Failed to load JDBC driver");
			return false;
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PWD);
			conn.setAutoCommit(false);

			System.out.println("Attempting to delete film with ID: " + filmId);

			if (hasChildRecords(filmId)) {
				System.out.println("Failed: Film has child records, cannot delete.");
				return false;
			}

			String deleteFilmSql = "DELETE FROM film WHERE id = ?";
			try (PreparedStatement deleteFilmStmt = conn.prepareStatement(deleteFilmSql)) {
				deleteFilmStmt.setInt(1, filmId);
				System.out.println("Executing SQL: " + deleteFilmStmt.toString());
				int filmDeletionCount = deleteFilmStmt.executeUpdate();

				if (filmDeletionCount == 1) {
					System.out.println("Success: Film deleted successfully.");
					conn.commit();
					return true;
				} else {
					System.out.println("Failed: Film deletion unsuccessful. No rows affected.");
					conn.rollback();
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error executing SQL statement");
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
					System.err.println("Error trying to rollback");
				}
			}
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean hasChildRecords(int filmId) throws SQLException {
	    try (Connection conn = getConnection()) {
	        String checkChildRecordsSql = "SELECT COUNT(*) FROM other_table WHERE film_id = ?";
	        try (PreparedStatement checkStmt = conn.prepareStatement(checkChildRecordsSql)) {
	            checkStmt.setInt(1, filmId);
	            try (ResultSet resultSet = checkStmt.executeQuery()) {
	                if (resultSet.next()) {
	                    int count = resultSet.getInt(1);
	                    System.out.println("Child records count: " + count);
	                    return count > 0;
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Error checking for child records");
	        return false;
	    }
		return false;
	}

	private Connection getConnection() throws SQLException {
	    return DriverManager.getConnection(URL, USER, PWD);
	}


	public List<Film> findFilmByKeyword(String filmKeyword) throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		List<Film> films = new ArrayList<>();

		String sql = "SELECT film.*, language.id, language.name FROM film JOIN language ON film.language_id = language.id WHERE film.title LIKE ? OR film.description LIKE ?";
		Connection conn = DriverManager.getConnection(URL, USER, PWD);

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, "%" + filmKeyword + "%");
			stmt.setString(2, "%" + filmKeyword + "%");

			try (ResultSet filmResult = stmt.executeQuery()) {
				while (filmResult.next()) {
					Film film = new Film();
					film.setId(filmResult.getInt("id"));
					film.setTitle(filmResult.getString("title"));
					film.setReleaseYear(filmResult.getInt("release_year"));
					film.setRating(filmResult.getString("rating"));
					film.setDescription(filmResult.getString("description"));
					film.setLanguage(filmResult.getString("language.name"));

					films.add(film);
				}
			}
		}

		return films;
	}
	@Override
	public boolean updateFilm(Film film) throws SQLException {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    try (Connection conn = DriverManager.getConnection(URL, USER, PWD)) {
	        String updateSql = "UPDATE film SET title = ?, description = ? WHERE id = ?";

	        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
	            updateStmt.setString(1, film.getTitle());
	            updateStmt.setString(2, film.getDescription());
	            updateStmt.setInt(3, film.getId());

	            int rowsAffected = updateStmt.executeUpdate();

	            return rowsAffected > 0;
	        }
	    }
	}

}
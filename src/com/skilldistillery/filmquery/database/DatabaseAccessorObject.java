package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private String user = "student";
	private String pass = "student";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<Film> findFilmByKeyword(String keyword) {
		List<Film> keyFilm = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "Select * FROM film where title LIKE ? OR description like ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			ResultSet keyResult = stmt.executeQuery();
			while (keyResult.next()) {
				Film film = new Film();
				
				film = new Film(keyResult.getInt("id"), keyResult.getString("title"), keyResult.getString("description"),
						keyResult.getInt("release_year"), keyResult.getInt("language_id"), keyResult.getString("rating"));
				
				film.setActor(findActorsByFilmId(film.getFilmID()));
				film.setLanguage(languageOfFilm(film.getFilmID()));
				keyFilm.add(film);
				
				
				
			}
			
			
			keyResult.close();
			stmt.close();
			conn.close();
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return keyFilm;
	}

	@Override
	public Film findFilmById(int filmId) {
		// needs to return film OBJECT or null if no data.
		Film film = null;
		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT * FROM film WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();
			if (filmResult.next()) {
				film = new Film();
				film.setFilmID(filmResult.getInt("id"));
				film.setFilmTitle(filmResult.getString("title"));
				film.setFilmDesc(filmResult.getString("description"));
				film.setReleaseFilm(filmResult.getInt("release_year"));
				film.setLangFilm(filmResult.getInt("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLengthFilm(filmResult.getInt("length"));
				film.setReplaceCost(filmResult.getDouble("replacement_cost"));
				film.setRatingFilm(filmResult.getString("rating"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				film.setActor(findActorsByFilmId(film.getFilmID()));
				film.setLanguage(languageOfFilm(filmId));
			}
			filmResult.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		// needs to return actor OBJECT or null if no data.
		Actor actor = null;

		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();
			if (actorResult.next()) {
				actor = new Actor();
				actor.setActorId(actorResult.getInt("id"));
				actor.setActorFirstName(actorResult.getString("first_name"));
				actor.setActorLastName(actorResult.getString("last_name"));
			}
			actorResult.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		// implement list using ResultSet, preparedstatement with ? bind varialbes
		List<Actor> films = new ArrayList<>();

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT * FROM actor JOIN film_actor ON actor.id = film_actor.actor_id "
					+ "JOIN film       ON film.id = film_actor.film_id WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				Actor actor = new Actor();
				actor.setActorId(result.getInt("id"));
				actor.setActorFirstName(result.getString("first_name"));
				actor.setActorLastName(result.getString("last_name"));
//				int actorId = result.getInt("id");
//				String actorFirst = result.getString("first_name");
//				String actorLast = result.getString("last_name");
				films.add(actor);

			}
			result.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}
	
	public String languageOfFilm(int filmId) {
		String language = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT language.name FROM language JOIN film ON language.id = film.language_id WHERE film.id = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				language = result.getString("name");

			} else {
				return null;
			}
			result.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return language;
	}


}

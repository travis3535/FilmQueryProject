package com.skilldistillery.filmquery.database;

import java.util.List;
import java.sql.*;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		//needs to return film OBJECT or null if no data.
		
		return null;
	}

	@Override
	public Actor findActorById(int actorId) {
		// needs to return actor OBJECT or null if no data.
		Actor actor = null;
		String user = "student";
		String pass = "student";
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
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		// implement list using ResultSet, preparedstatement with ? bind varialbes
		
		return null;
	}

}

package com.skilldistillery.filmquery.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
	Scanner kb = new Scanner(System.in);

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
//    app.testActor();
		app.launch();
//    app.testActorFilms();
	}

	private void test() {
		Film film = db.findFilmById(1);
		System.out.println(film);
		System.out.println("**********");
	}

	private void testActor() {
		Actor actor = db.findActorById(1);
		System.out.println(actor);
		System.out.println("*************");
	}

	private void testActorFilms() {
		List<Actor> actor = new ArrayList<>();
		actor = db.findActorsByFilmId(1);
		System.out.print(actor);
		System.out.println();
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		// menu

		while (true) {
			System.out.println("Please select a menu option");
			System.out.println("----------------------------------");
			System.out.println("( 1 ) Look up a film by its id");
			System.out.println("( 2 ) Look up a film by a search keyword");
			System.out.println("( 3 ) Exit the application");
			int userInput = kb.nextInt();

			if (userInput == 1) {
				System.out.println("Enter Film ID :");
				
				int userFilmId = kb.nextInt();
				if (db.findFilmById(userFilmId) == null) {
					System.out.println("No film found by that ID");
					continue;
					// this works to re enter another film id.

				} else {
					System.out.println(db.findFilmById(userFilmId));
				}
			}

			if (userInput == 2) {
				System.out.println("Enter Search Keyword");
				String keyword = kb.next();

				if (db.findFilmByKeyword(keyword) == null) {
					System.out.println("No film found by that keyword");
					continue;
				} else {
					System.out.println(db.findFilmByKeyword(keyword));
//					db.findFilmByKeyword(keyword);
				}

			}

			if (userInput == 3) {
				System.out.println("Exiting Application...");
				break;
				// works
			}

		}

	}

}

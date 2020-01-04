package com.skilldistillery.filmquery.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();

  public static void main(String[] args) {
    FilmQueryApp app = new FilmQueryApp();
    app.test();
    app.testActor();
    app.launch();
    app.testActorFilms();
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
  }

}

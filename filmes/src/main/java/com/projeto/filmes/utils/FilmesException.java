package com.projeto.filmes.utils;

public class FilmesException extends Exception {
	public FilmesException() {
		super();
	}
	
	public FilmesException(String msg, Throwable thr) {
		super(msg,thr);
	}
	
	public FilmesException(String msg) {
		super(msg);
	}
	
	public FilmesException(Throwable thr) {
		super(thr);
	}
		
}

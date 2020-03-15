package br.com.jeramovies.presentation.ui.movies

import java.io.Serializable

interface MovieType : Serializable

class PopularMovies : MovieType
class TopRatedMovies : MovieType
class InTheatersMovies : MovieType
package br.com.jeramovies.domain.entity

interface MovieManagedStatus

class MovieRemoved : MovieManagedStatus
class MoviePersisted : MovieManagedStatus
class MoviePersistError : MovieManagedStatus
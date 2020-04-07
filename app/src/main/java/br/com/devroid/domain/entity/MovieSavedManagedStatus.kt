package br.com.devroid.domain.entity

interface MovieManagedStatus

class MovieRemoved : MovieManagedStatus
class MoviePersisted : MovieManagedStatus
class MoviePersistError : MovieManagedStatus
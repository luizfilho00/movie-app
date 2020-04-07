package br.com.devroid.data.util

interface Mapper<I, O> {
    fun transform(input: I): O
    fun transform(input: List<I>): List<O> = input.map(::transform)
}
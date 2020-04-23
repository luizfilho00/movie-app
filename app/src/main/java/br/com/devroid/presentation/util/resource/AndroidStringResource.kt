package br.com.devroid.presentation.util.resource

import android.content.Context
import br.com.devroid.domain.resource.StringResource
import br.com.devroid.moviesapp.R

class AndroidStringResource(
    private val context: Context
) : StringResource {

    override val ok: String get() = context.getString(R.string.ok)
    override val cancel: String get() = context.getString(R.string.cancel)
    override val trailerNotFound: String get() = context.getString(R.string.trailer_not_found)
    override val movieSavedToList: String get() = context.getString(R.string.movie_saved_to_list)
    override val movieRemovedFromList: String get() = context.getString(R.string.movie_removed_from_list)
    override val moviePersistError: String get() = context.getString(R.string.movie_persist_error)
    override val movieRatedSuccess: String get() = context.getString(R.string.movie_rated_success)
    override val movieRatedFailure: String get() = context.getString(R.string.movie_rated_failure)
}
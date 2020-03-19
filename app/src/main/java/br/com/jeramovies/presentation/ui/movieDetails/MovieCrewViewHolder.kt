package br.com.jeramovies.presentation.ui.movieDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ItemCrewBinding
import br.com.jeramovies.domain.entity.Actor
import coil.api.load

class MovieCrewViewHolder(
    private val binding: ItemCrewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(actor: Actor) {
        with(binding) {
            textViewName.text = actor.name
            textViewCharacter.text = actor.characterName
            imageView.load(actor.getProfileImage())
        }
    }

    companion object {
        fun inflate(parent: ViewGroup) = MovieCrewViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_crew,
                parent,
                false
            )
        )
    }
}
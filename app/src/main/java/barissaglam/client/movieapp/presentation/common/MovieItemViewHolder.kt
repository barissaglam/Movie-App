package barissaglam.client.movieapp.presentation.common

import barissaglam.client.movieapp.base.adapter.BaseViewHolder
import barissaglam.client.movieapp.data.model.uimodel.MovieViewItem
import barissaglam.client.movieapp.databinding.ItemMovieListBinding

class MovieItemViewHolder(val binding: ItemMovieListBinding, val itemClick: ((MovieViewItem) -> Unit)?) : BaseViewHolder<MovieViewItem?>(binding.root) {

    override fun bind(data: MovieViewItem?) {
        if (data == null) return

        binding.movie = data
        binding.root.setOnClickListener {
            itemClick?.invoke(data)
        }
        binding.executePendingBindings()
    }


}
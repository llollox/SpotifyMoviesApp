package com.lorenzorigato.movies.ui.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.lorenzorigato.base.components.architecture.SingleLiveData;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;
import com.lorenzorigato.movies.ui.component.movielist.MovieLayoutMapper;
import com.lorenzorigato.movies.ui.component.movielist.MovieViewHolder;

public class FavoritesViewModel extends ViewModel {


    // Static **************************************************************************************
    private static final int DATABASE_PAGE_SIZE = 20;


    // Private class attributes ********************************************************************
    private IMovieRepository movieRepository;
    private MovieLayoutMapper movieLayoutMapper = new MovieLayoutMapper();
    private LiveData<PagedList<MovieViewHolder.Layout>> layouts;
    private SingleLiveData<FavoritesView.Status> status = new SingleLiveData<>();


    // Constructor *********************************************************************************
    public FavoritesViewModel(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;

        DataSource.Factory<Integer, MovieViewHolder.Layout> factory = this.movieRepository
                .findFavorites()
                .map(movie -> this.movieLayoutMapper.mapToLayout(movie));

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(DATABASE_PAGE_SIZE)
                .setPageSize(DATABASE_PAGE_SIZE)
                .build();

        this.layouts = new LivePagedListBuilder(factory, config).build();
    }


    // Class methods *******************************************************************************
    public LiveData<FavoritesView.Status> getStatus() { return status; }

    public LiveData<PagedList<MovieViewHolder.Layout>> getLayouts() { return this.layouts; }

    public void onToggleFavorite(MovieViewHolder.Layout layout) {
        this.movieRepository.toggleFavorite(layout.getId(), (error, updatedMovie) -> {
            if (error != null) {
                this.status.setValue(FavoritesView.Status.FAVORITE_NOT_SET_ERROR);
            }
            else {
                if (updatedMovie.isFavorite()) {
                    this.status.setValue(FavoritesView.Status.FAVORITE_ADD_SUCCESS);
                }
                else {
                    this.status.setValue(FavoritesView.Status.FAVORITE_REMOVED_SUCCESS);
                }
            }
        });
    }
}

package com.popularmovies.vpaliy.popularmoviesapp.ui.season;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.Episode;
import com.popularmovies.vpaliy.domain.model.Trailer;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseFragment;
import java.util.List;
import com.popularmovies.vpaliy.popularmoviesapp.ui.season.SeasonContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.ElasticDismissLayout;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.FABToggle;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.ParallaxImageView;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.ParallaxRatioViewPager;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.TranslatableLayout;
import com.rd.PageIndicatorView;
import com.vpaliy.chips_lover.ChipsLayout;

import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;

import static com.google.common.base.Preconditions.checkNotNull;

public class SeasonFragment extends BaseFragment
    implements SeasonContract.View{

    @BindView(R.id.backdrop_pager)
    protected ParallaxRatioViewPager pager;

    @BindView(R.id.page_indicator)
    protected PageIndicatorView indicatorView;

    @BindView(R.id.back_wrapper)
    protected View actionBar;

    @BindView(R.id.details_background)
    protected TranslatableLayout detailsParent;

    @BindView(R.id.media_title)
    protected TextView mediaTitle;

    @BindView(R.id.media_ratings)
    protected TextView mediaRatings;

    @BindView(R.id.chipsContainer)
    protected ChipsLayout tags;

    @BindView(R.id.media_release_year)
    protected TextView releaseYear;

    @BindView(R.id.media_description)
    protected TextView mediaDescription;

    @BindView(R.id.details)
    protected RecyclerView info;

    @BindView(R.id.poster)
    protected ParallaxImageView poster;

    @BindView(R.id.share_fab)
    protected FABToggle toggle;

    @BindView(R.id.parent)
    protected ElasticDismissLayout parent;

    private ImagesAdapter imagesAdapter;

    private String seasonId;
    private Presenter presenter;

    public static SeasonFragment newInstance(Bundle args){
        SeasonFragment seasonFragment=new SeasonFragment();
        seasonFragment.setArguments(args);
        return seasonFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_season,container,false);
        bind(root);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null) savedInstanceState=getArguments();
        seasonId=savedInstanceState.getString(Constants.EXTRA_ID,null);
        seasonId=checkNotNull(seasonId);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            presenter.start(seasonId);
        }
    }

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .presenterModule(new PresenterModule())
                .applicationComponent(App.appInstance().appComponent())
                .build().inject(this);
    }

    @Override
    public void showDescription(@NonNull String description) {
        mediaDescription.setText(description);
    }

    @Override
    public void showTrailers(@NonNull List<Trailer> trailers) {

    }

    @Override
    public void showPoster(@NonNull String posterPath) {
        Glide.with(this)
                .load(posterPath)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(poster);

    }

    @Override
    public void showEpisodes(@NonNull List<Episode> episodes) {

    }

    @Override
    public void showImages(@NonNull List<String> images) {
        ImagesAdapter adapter=new ImagesAdapter(getContext());
        adapter.setData(images);
        pager.setAdapter(adapter);
    }

    @Override
    public void showCast(@NonNull List<ActorCover> cast) {

    }

    @Inject @Override
    public void attachPresenter(@NonNull SeasonContract.Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }
}
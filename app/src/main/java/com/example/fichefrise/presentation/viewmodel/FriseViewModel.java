package com.example.fichefrise.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.repository.FriseDisplayRepository;
import com.example.fichefrise.data.repository.ThemeDisplayRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FriseViewModel extends ViewModel {

    private FriseDisplayRepository friseDisplayRepository;
    private ThemeDisplayRepository themeDisplayRepository;
    private CompositeDisposable compositeDisposable;

    public FriseViewModel(FriseDisplayRepository friseDisplayRepository, ThemeDisplayRepository themeDisplayRepository) {
        this.friseDisplayRepository = friseDisplayRepository;
        this.themeDisplayRepository = themeDisplayRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    private MutableLiveData<List<Theme>> themes = new MutableLiveData<>();

    public MutableLiveData<List<Theme>> getThemes(){ return this.themes; }

    public void getAllThemes(){
        compositeDisposable.clear();
        compositeDisposable.add(themeDisplayRepository.getAllThemes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Theme>>() {
                    @Override
                    public void onSuccess(@NonNull List<Theme> allThemes) {
                        //A compléter
                        themes.setValue(allThemes);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.toString());
                    }

                }));
    }
}

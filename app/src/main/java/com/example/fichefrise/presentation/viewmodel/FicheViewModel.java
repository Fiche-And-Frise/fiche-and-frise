package com.example.fichefrise.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.repository.FicheDisplayRepository;
import com.example.fichefrise.data.repository.ThemeDisplayRepository;
import com.example.fichefrise.presentation.display.fiche.adapter.FicheViewItem;
import com.example.fichefrise.presentation.display.fiche.mapper.FicheToViewModelMapper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FicheViewModel extends ViewModel{

    private FicheDisplayRepository ficheDisplayRepository;
    private ThemeDisplayRepository themeDisplayRepository;
    private CompositeDisposable compositeDisposable;
    private FicheToViewModelMapper mapper;

    public FicheViewModel(FicheDisplayRepository ficheDisplayRepository, ThemeDisplayRepository themeDisplayRepository){
        this.ficheDisplayRepository = ficheDisplayRepository;
        this.themeDisplayRepository = themeDisplayRepository;
        this.compositeDisposable = new CompositeDisposable();
        this.mapper = new FicheToViewModelMapper();
    }

    //private MutableLiveData<List<FicheViewItem>> fiches = new MutableLiveData<>();
    private MutableLiveData<List<Theme>> themes = new MutableLiveData<>();

    //public MutableLiveData<List<FicheViewItem>> getFiches(){ return this.fiches; };
    public MutableLiveData<List<Theme>> getThemes(){ return this.themes; };

    /*public void getAllFiches(){
        compositeDisposable.clear();
        compositeDisposable.add(ficheDisplayRepository.getAllFiches()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Fiche>>() {
                    @Override
                    public void onSuccess(@NonNull List<Fiche> allFiches) {
                        //A compléter
                        fiches.setValue(mapper.map(allFiches));
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.toString());
                    }

                }));
    }*/

    public void getFicheById(int ficheId){
        compositeDisposable.clear();
        compositeDisposable.add(ficheDisplayRepository.getFicheById(ficheId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Fiche>(){

                    @Override
                    public void onSuccess(@NonNull Fiche fiche) {
                        // A compléter
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println(e.toString());
                    }

                }));
    }

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

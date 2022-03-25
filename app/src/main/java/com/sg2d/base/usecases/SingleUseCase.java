package com.sg2d.base.usecases;


import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;

public abstract class SingleUseCase<InputType, OutputType> {

    protected CompositeDisposable disposables = new CompositeDisposable();

    public abstract void execute(InputType input, SingleObserver<OutputType> subscriber);

    public void cancel() {
        disposables.clear();
    }

}

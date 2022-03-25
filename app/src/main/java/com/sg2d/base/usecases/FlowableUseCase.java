package com.sg2d.base.usecases;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;

public abstract class FlowableUseCase<InputType, OutputType> {

    protected CompositeDisposable disposables = new CompositeDisposable();

    public abstract void execute(InputType input, DisposableSubscriber<OutputType> subscriber);

    public void cancel() {
        disposables.clear();
    }

}

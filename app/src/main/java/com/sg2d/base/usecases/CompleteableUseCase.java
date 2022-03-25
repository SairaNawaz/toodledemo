package com.sg2d.base.usecases;


import io.reactivex.CompletableObserver;

public abstract class CompleteableUseCase<InputType, OutputType> {
    public abstract void execute(InputType input, CompletableObserver subscriber);
}

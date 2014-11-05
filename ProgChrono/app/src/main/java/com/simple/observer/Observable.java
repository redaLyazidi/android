package com.simple.observer;

import java.util.Collection;

/**
 * Created by reda on 9/21/14.
 */
public interface Observable {

    boolean addObserver(Observer observer);
    boolean addAllObserver(Collection<? extends Observer> observer);
    void notifyAllObservers();

}
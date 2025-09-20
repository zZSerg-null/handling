package ru.old.rest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateQueueWorkerThread implements Runnable {

    private final RestAdapter restAdapter;

    @Override
    public void run() {
//        try {
//            while (!Thread.currentThread().isInterrupted()) {
//                RestUpdateData updateData = restAdapter.getRequest();
//                restAdapter.proceed(updateData);
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
    }
}

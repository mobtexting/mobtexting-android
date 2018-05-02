package com.mobtexting;

public interface MobtextingInterface {
    void onResponse(ServerResponse response);
    void onError(ModelError modelError);
}

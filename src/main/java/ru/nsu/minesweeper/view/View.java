package ru.nsu.minesweeper.view;

import ru.nsu.minesweeper.model.Updater;

public interface View {
    void startGame();

    void endGame();

    void update(Updater data, long time);

    void showList(String message);

    void showText(String message);

    void showWarning(String message);

    void askUser(String message);

    void showGreetScreen();

    String waitAction();

    void setAlreadyCreated(boolean alreadyCreated);
}

package ru.nsu.minesweeper;

import ru.nsu.minesweeper.controller.commands.Marks;
import ru.nsu.minesweeper.model.GameManager;

public class MinesweeperRunner {

    public static void main(String[] args) {
        GameManager manager = new GameManager();
        Marks mark = null;

        while (mark != Marks.Exit) {
            mark = manager.start();
        }

    }
}
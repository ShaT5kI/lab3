package ru.nsu.minesweeper.controller.commands;

import ru.nsu.minesweeper.model.Field;

public class CommandIdentifier {
    public String [] args;
    public Field field;

    public CommandIdentifier(String [] args, Field field) {
        this.args = args;
        this.field = field;
    }
}

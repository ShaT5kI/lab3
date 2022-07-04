package ru.nsu.minesweeper.controller.commands;

public class NewGame implements Command {
    public NewGame(CommandIdentifier identifier) {
    }

    @Override
    public Marks run() {
        return Marks.True;
    }

    @Override
    public Object getArg() {
        return null;
    }
}
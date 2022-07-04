package ru.nsu.minesweeper.controller.commands;

public class Exit implements Command {
    public Exit(CommandIdentifier identifier) {
    }

    @Override
    public Marks run() {
        return Marks.Exit;
    }

    @Override
    public Object getArg() {
        return null;
    }
}
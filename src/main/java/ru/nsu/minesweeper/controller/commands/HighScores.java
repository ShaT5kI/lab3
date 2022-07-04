package ru.nsu.minesweeper.controller.commands;

public class HighScores extends AbstractRecordCommand {
    public HighScores(CommandIdentifier identifier) {
        super(identifier);
    }

    @Override
    protected String getFileName() {
        return arg;
    }
}
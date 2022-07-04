package ru.nsu.minesweeper.controller.commands;

public class About extends AbstractRecordCommand {
    public About(CommandIdentifier identifier) {
        super(identifier);
    }

    @Override
    protected String getFileName() {
        return "ru\\nsu\\minesweeper\\controller\\data\\about.txt";
    }
}

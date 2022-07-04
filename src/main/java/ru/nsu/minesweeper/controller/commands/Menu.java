package ru.nsu.minesweeper.controller.commands;

import java.io.IOException;

public class Menu implements Command {
    public Menu(CommandIdentifier identifier) {
    }

    @Override
    public Marks run() throws IOException {
        return Marks.Menu;
    }

    @Override
    public Object getArg() {
        return null;
    }
}
package ru.nsu.minesweeper.controller.commands;

import java.io.IOException;

public class Restart implements Command {
    public Restart(CommandIdentifier identifier) {
    }

    @Override
    public Marks run() throws IOException {
        return Marks.Restart;
    }

    @Override
    public Object getArg() {
        return null;
    }
}

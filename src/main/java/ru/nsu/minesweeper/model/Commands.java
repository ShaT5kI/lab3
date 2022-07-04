package ru.nsu.minesweeper.model;

import java.util.HashMap;

public class Commands {
    public static HashMap<String, String> commands = new HashMap<>() {
        {
            put("New game", "1");
            put("High scores", "2");
            put("About", "3");
            put("Exit", "4");
        }
    };
}

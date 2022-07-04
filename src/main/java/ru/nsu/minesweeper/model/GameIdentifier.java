package ru.nsu.minesweeper.model;

public record GameIdentifier (int bombs, int size, int safetyRad, int labyrinth) {
}

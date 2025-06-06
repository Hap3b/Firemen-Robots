package tests;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import field.Direction;
import paths.GPS;
import tests.category.NoGUITests;

public class TestReconstructPath {
    @Test
    @Category(NoGUITests.class)
    public void testReconstructPath() {
        Direction[] prev = {Direction.NONE, Direction.EST, Direction.NONE, Direction.OUEST, Direction.SUD, Direction.NORD, Direction.SUD, Direction.EST, Direction.EST};
        Deque<Direction> sut = GPS.reconstructPath(prev, 0, 5, 3);
        Deque<Direction> result =  new LinkedList<>();
        result.addFirst(Direction.NORD);
        result.addFirst(Direction.EST);
        result.addFirst(Direction.EST);
        result.addFirst(Direction.SUD);
        result.addFirst(Direction.OUEST);
        result.addFirst(Direction.SUD);
        result.addFirst(Direction.EST);
        assert sut.equals(result);
    }
}

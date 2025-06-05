package tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import field.Direction;
import paths.GPS;

public class TestReconstructPath {
    @Test
    public void testReconstructPath() {
        Direction[] prev = {Direction.NONE, Direction.EST, Direction.NONE, Direction.OUEST, Direction.SUD, Direction.NORD, Direction.SUD, Direction.EST, Direction.EST};
        List<Direction> sut = GPS.reconstructPath(prev, 0, 5, 3);
        List<Direction> result =  new ArrayList<>();
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

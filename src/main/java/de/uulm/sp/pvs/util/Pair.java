package de.uulm.sp.pvs.util;
import java.util.Objects;



public class Pair<F extends Comparable<F>,S extends Comparable<S>> implements Comparable<Pair<F,S>> {

    private final F first;
    private final S second;


    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                "}";
    }


    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != getClass()) {
            return false;
        }
        if (this == other) {
            return true;
        }
        Pair<?, ?> pair = (Pair<?, ?>) other;

        if (first == null) {
            if (pair.getFirst() != null)
                return false;
        } else if (!first.equals(pair.getFirst()))
            return false;
        if (second == null) {
            if (pair.getSecond() != null)
                return false;
        } else if (!second.equals(pair.getSecond()))
            return false;
        return true;


    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirst(), getSecond());
    }


    @Override
    public int compareTo(Pair <F,S> pair) throws NullPointerException, ClassCastException {
        F firstValue= pair.getFirst();
        S secondValue = pair.getSecond();
        int firstComp = first.compareTo(firstValue);
        int secondComp  = second.compareTo(secondValue);

        return (firstComp !=0) ? firstComp : secondComp ;
    }
}




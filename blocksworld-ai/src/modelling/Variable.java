package modelling;

import java.util.*;


public class Variable {

    private String name;
    private Set<Object> domain;


    public Variable (String name, Set<Object> domain) {
        this.name = name;
        this.domain = domain;
    }

    public String getName () {
        return this.name;
    }

    public Set<Object> getDomain () {
        return this.domain;
    }


    @Override
    public boolean equals (Object other) {
        if (other instanceof Variable) {
            Variable otherAsVariable = (Variable) other;
            return this.name.equals(otherAsVariable.getName());
        }
        return false;
    }

    @Override
    public int hashCode () {
        return Objects.hash(this.name);
    }


    @Override
    public String toString () {
        return this.name/* + " : " + this.domain*/;
    }

}
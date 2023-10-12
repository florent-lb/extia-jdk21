package dev.flb.domain.people;

import java.util.UUID;

import static java.lang.StringTemplate.RAW;

public sealed class Employee permits Customer, NextGenEmployee, OldEmployee {

    final public Identity identity;

    final protected Object id;

    public Employee(Identity identity, Object id) {
        this.identity = identity;
        this.id = id;
    }


    String returnLogin() {

        String suffix = "";
        var template = RAW. "\{ suffix }_\{ id }" ;
        switch (id) {
            case UUID uuid ->  {
                template = RAW. "NEW_\{ id }" ;
            }
            case Long oldId when oldId >= 2000 -> suffix = "MID";
            case Long oldId when oldId < 2000 -> suffix = "OLD";
            //Primitive are not allowed  case long oldId -> Long.toString(oldId);
            default -> throw new IllegalArgumentException(STR. "\{ id } has a format not managed byh the app" );
        }

        return template.interpolate();
    }
}

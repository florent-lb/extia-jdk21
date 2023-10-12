package dev.flb.domain.people;

import dev.flb.domain.authorization.Authorization;
import dev.flb.domain.authorization.Person;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

import static java.lang.StringTemplate.RAW;

public sealed class Employee extends Person permits NextGenEmployee, OldEmployee {

    @Getter
    private final Authorization authorization;
    @Getter
    final protected Object id;

    public Employee(Identity identity, Authorization authorization, Object id) {
        super(identity);
        this.authorization = authorization;
        this.id = id;
    }
    String returnLogin() {

        String suffix = "";
        var template = RAW. "\{ suffix }_\{ id }" ;
        switch (id) {
            case UUID uuid -> template = RAW. "NEW_\{ id }" ;
            case Long oldId when oldId >= 2000 -> suffix = "MID";
            case Long oldId when oldId < 2000 -> suffix = "OLD";
            //Primitive are not allowed  case long oldId -> Long.toString(oldId);
            default -> throw new IllegalArgumentException(STR. "\{ id } has a format not managed byh the app" );
        }

        return template.interpolate();
    }
}

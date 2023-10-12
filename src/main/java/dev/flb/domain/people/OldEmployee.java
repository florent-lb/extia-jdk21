package dev.flb.domain.people;

import dev.flb.domain.authorization.Authorization;
import lombok.Getter;

import java.util.List;

@Getter
public final class OldEmployee extends Employee {
    public OldEmployee(Identity identity, Authorization authorization, long id) {
        super(identity, authorization, id);
    }
}

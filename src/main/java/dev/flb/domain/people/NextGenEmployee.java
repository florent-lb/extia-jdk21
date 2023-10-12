package dev.flb.domain.people;


import dev.flb.domain.authorization.Authorization;
import dev.flb.domain.authorization.DepartmentName;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public final class NextGenEmployee extends Employee {

    private final DepartmentName departmentName;
    public NextGenEmployee(Identity identity, UUID id, Authorization authorization, DepartmentName departmentName) {
        super(identity, authorization, id);
        this.departmentName = departmentName;
    }

}

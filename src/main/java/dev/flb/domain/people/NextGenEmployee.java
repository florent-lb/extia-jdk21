package dev.flb.domain.people;


import dev.flb.domain.authorization.Authorization;
import dev.flb.domain.authorization.DepartmentName;
import lombok.Getter;

import java.util.UUID;

public final class NextGenEmployee extends Employee {

    private final Authorization authorization;

    private final DepartmentName departmentName;

    public NextGenEmployee(Identity identity, UUID id, Authorization authorization, DepartmentName departmentName) {
        super(identity, id);
        this.authorization = authorization;
        this.departmentName = departmentName;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public DepartmentName getDepartmentName() {
        return departmentName;
    }
}

package dev.flb.domain;

import dev.flb.domain.building.Floor;
import dev.flb.domain.people.Employee;

import static java.lang.StringTemplate.STR;

public class FloorAccessRestrictedException extends RuntimeException {

    public FloorAccessRestrictedException(
            Employee employee, Floor floor
    ) {
        super(STR. "\{ employee.identity.lastName() }\{ employee.identity.firstName() } has no acces to \{ floor.departmentName().name() }" );
    }
}

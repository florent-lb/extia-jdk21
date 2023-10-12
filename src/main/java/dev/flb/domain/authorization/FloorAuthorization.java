package dev.flb.domain.authorization;

public record FloorAuthorization(String floorName, DepartmentName name)
        implements Authorization {
}

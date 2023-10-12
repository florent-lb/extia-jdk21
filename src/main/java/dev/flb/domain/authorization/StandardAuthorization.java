package dev.flb.domain.authorization;

public record StandardAuthorization(DepartmentName name, Level level) implements Authorization {

}

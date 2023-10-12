package dev.flb.domain.building;

import dev.flb.domain.authorization.Authorization;
import dev.flb.domain.authorization.DepartmentName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.PositiveOrZero;

public record Floor(
        @PositiveOrZero int number,
        @Nonnull Authorization authorization,
        @Nonnull DepartmentName departmentName
) {
}

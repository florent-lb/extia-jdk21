package dev.flb.domain.people;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record Identity(
        @Nonnull UUID id,
        @Nonnull @NotEmpty String firstName,
        @Nonnull  @NotEmpty String lastName)
{

}

package dev.flb.domain.authorization;

import java.util.List;

public record SensibleAuthorization(Level level) implements Authorization {

    public SensibleAuthorization {

        if (!(level == Level.DIAMOND || level == Level.RUBY)) {
            throw new IllegalArgumentException(STR. "\{ level.name() } is not a sensible level" );
        }
    }

}

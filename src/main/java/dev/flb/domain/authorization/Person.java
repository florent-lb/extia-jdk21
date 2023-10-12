package dev.flb.domain.authorization;

import dev.flb.domain.people.Identity;

public class Person {
    final public Identity identity;

    public Person(Identity identity) {
        this.identity = identity;
    }
}

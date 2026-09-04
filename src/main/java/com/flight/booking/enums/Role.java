package com.flight.booking.enums;

/**
 * What a user is allowed to do.
 *
 * An enum instead of a String means the compiler catches typos.
 * Role.ADMIN either exists or the code will not build, whereas
 * "ADMNI" as a String would silently become a bug.
 */
public enum Role {
    USER,
    ADMIN
}

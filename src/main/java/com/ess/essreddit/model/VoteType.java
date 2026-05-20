package com.ess.essreddit.model;

import com.ess.essreddit.exceptions.EssRedditException;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);

    private Integer direction;

    VoteType(Integer direction) {
        this.direction = direction;
    }

    public Integer getDirection() {
        return this.direction;
    }

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(val -> val.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new EssRedditException("Direction invalid"));
    }
}

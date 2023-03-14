package com.nydia.agent.core.match;

import net.bytebuddy.description.type.TypeDescription;

public interface ClassMatch {

    boolean isMatch(TypeDescription typeDescription);
}

package com.greenebeans.make

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal

class MakeToolChain {
    @Input
    String getMakeVersion() {
        return "1.0"
    }

    @Internal
    File getFullyQualifiedMakeExecutable() {
        new File("make")
    }
}

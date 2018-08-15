package com.greenebeans.make

import org.gradle.api.tasks.Input

class EnvironmentVariable {
    @Input
    String key

    @Input
    String value

    @Override
    String toString() {
        return key + "=" + value
    }
}

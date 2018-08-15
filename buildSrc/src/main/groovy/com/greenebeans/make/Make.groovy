/**
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.greenebeans.make

import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.process.ExecSpec

class Make extends DefaultTask {
    @InputFiles
    final ConfigurableFileCollection source = project.files()

    @InputFile
    final RegularFileProperty makefile = newInputFile()

    @InputFiles
    final ConfigurableFileCollection dependencies = project.files()

    @Input
    final ListProperty<String> arguments = project.objects.listProperty(String)

    @Input
    final ListProperty<EnvironmentVariable> environment = project.objects.listProperty(EnvironmentVariable)

    @Nested
    final Property<MakeToolChain> makeToolChain = project.property(MakeToolChain)

    // version of tools being used (gcc, icc, etc.)
    // OS identification?
    // dependencies from OS implicitly used by make/gcc/etc

    @OutputDirectory
    final DirectoryProperty outputDirectory = newOutputDirectory()

    @TaskAction
    void runMake() {
        project.exec(new Action<ExecSpec>() {
            @Override
            void execute(ExecSpec execSpec) {
                execSpec.executable(makeToolChain.get().fullyQualifiedMakeExecutable)
                execSpec.args(arguments.get())
                // Environment variables need to be explicitly passed along
                execSpec.environment = environment.get().collectEntries {
                    [it.key, it.value]
                }
            }
        })
    }
}

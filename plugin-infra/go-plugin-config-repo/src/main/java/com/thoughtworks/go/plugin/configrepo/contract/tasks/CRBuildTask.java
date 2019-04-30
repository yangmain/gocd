/*
 * Copyright 2018 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thoughtworks.go.plugin.configrepo.contract.tasks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thoughtworks.go.plugin.configrepo.contract.ErrorCollection;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CRBuildTask extends CRTask {
    public static final String RAKE_TYPE_NAME = "rake";
    public static final String ANT_TYPE_NAME = "ant";
    public static final String NANT_TYPE_NAME = "nant";

    public static CRBuildTask rake() {
        return new CRBuildTask(RAKE_TYPE_NAME);
    }

    public static CRBuildTask rake(String rakeFile) {
        return new CRBuildTask(RAKE_TYPE_NAME, rakeFile, null, null);
    }

    public static CRBuildTask rake(String rakeFile, String target) {
        return new CRBuildTask(RAKE_TYPE_NAME, rakeFile, target, null);
    }

    public static CRBuildTask rake(String rakeFile, String target, String workingDirectory) {
        return new CRBuildTask(RAKE_TYPE_NAME, rakeFile, target, workingDirectory);
    }

    public static CRBuildTask ant() {
        return new CRBuildTask(ANT_TYPE_NAME, null, null, null);
    }

    public static CRBuildTask ant(String antFile) {
        return new CRBuildTask(ANT_TYPE_NAME, antFile, null, null);
    }

    public static CRBuildTask ant(String antFile, String target) {
        return new CRBuildTask(ANT_TYPE_NAME, antFile, target, null);
    }

    public static CRBuildTask ant(String antFile, String target, String workingDirectory) {
        return new CRBuildTask(ANT_TYPE_NAME, antFile, target, workingDirectory);
    }

    public static CRNantTask nant() {
        return new CRNantTask(NANT_TYPE_NAME, null, null, null, null);
    }

    public static CRNantTask nant(String nantPath) {
        return new CRNantTask(NANT_TYPE_NAME, null, null, null, nantPath);
    }

    public static CRNantTask nant(String nantFile, String target) {
        return new CRNantTask(NANT_TYPE_NAME, nantFile, target, null, null);
    }

    public static CRNantTask nant(String nantFile, String target, String workingDirectory) {
        return new CRNantTask(NANT_TYPE_NAME, nantFile, target, workingDirectory, null);
    }

    public static CRNantTask nant(String nantFile, String target, String workingDirectory, String nantPath) {
        return new CRNantTask(NANT_TYPE_NAME, nantFile, target, workingDirectory, nantPath);
    }

    public static CRBuildTask rake(CRRunIf runIf, CRTask onCancel,
                                   String buildFile, String target, String workingDirectory) {
        return new CRBuildTask(runIf, onCancel, buildFile, target, workingDirectory, CRBuildFramework.rake);
    }

    public static CRBuildTask ant(CRRunIf runIf, CRTask onCancel,
                                  String buildFile, String target, String workingDirectory) {
        return new CRBuildTask(runIf, onCancel, buildFile, target, workingDirectory, CRBuildFramework.ant);
    }

    @SerializedName("build_file")
    @Expose
    private String buildFile;
    @SerializedName("target")
    @Expose
    private String target;
    @SerializedName("working_directory")
    @Expose
    private String workingDirectory;

    public CRBuildTask(String type, String buildFile, String target, String workingDirectory) {
        super(type);
        this.buildFile = buildFile;
        this.target = target;
        this.workingDirectory = workingDirectory;
    }

    public CRBuildTask(String type) {
        super(type);
    }

    public CRBuildTask(CRRunIf runIf, CRTask onCancel,
                       String buildFile, String target, String workingDirectory, CRBuildFramework type) {
        super(runIf, onCancel);
        this.buildFile = buildFile;
        this.target = target;
        this.workingDirectory = workingDirectory;
        super.type = type.toString();
    }

    public CRBuildFramework getType() {
        if (type == null)
            return null;
        return CRBuildFramework.valueOf(type);
    }

    @Override
    public void getErrors(ErrorCollection errors, String parentLocation) {
        String location = getLocation(parentLocation);
        errors.checkMissing(location, "type", type);
    }

    @Override
    public String getLocation(String parent) {
        String myLocation = getLocation() == null ? parent : getLocation();
        String type = this.getType() == null ? "unknown" : this.getType().toString();
        return String.format("%s; %s build task", myLocation, type);
    }

}
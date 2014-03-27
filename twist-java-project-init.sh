#!/bin/sh

# Creates a src directory
# copy the installed skel file to the src directory

if [ -f "src/StepImplementation.java" ]; then
    echo "src/StepImplementation.java already exists"
else
    if [ ! -d "src" ]; then
      mkdir src
    fi
    SKEL_FILE=""
    CLASSPATH_JSON=""
    if [ -f "/usr/local/share/twist2/skel/java/StepImplementation.java" ]; then
        SKEL_FILE="/usr/local/share/twist2/skel/java/StepImplementation.java"
        CLASSPATH_JSON="/usr/local/share/twist2/skel/env/classpath.json"
    else
        if [ -f "/usr/share/twist2/skel/java/StepImplementation.java" ]; then
            SKEL_FILE="/usr/share/twist2/skel/java/StepImplementation.java"
            CLASSPATH_JSON="/usr/share/twist2/skel/env/classpath.json"
        fi
    fi
    cp "$SKEL_FILE" src
    cp "$CLASSPATH_JSON" env/default
fi

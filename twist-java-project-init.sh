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
    if [ -f "/usr/local/share/twist2/skel/java/StepImplementation.java" ]; then
        SKEL_FILE="/usr/local/share/twist2/skel/java/StepImplementation.java"
    else
        if [ -f "/usr/share/twist2/skel/java/StepImplementation.java" ]; then
            SKEL_FILE="/usr/share/twist2/skel/java/StepImplementation.java"
        fi
    fi
    cp "$SKEL_FILE" src
fi









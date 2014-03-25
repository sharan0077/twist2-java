#!/bin/sh

PROJECT_ROOT=`pwd`
LIBPATH=""

if [ -d "/usr/local/lib/twist2/java" ]; then
    LIBPATH="/usr/local/lib/twist2/java"
else
    if [ -d "/usr/lib/twist2/java" ]; then
        LIBPATH="/usr/lib/twist2/java"
    fi
fi

if [ "$LIBPATH" = "" ]; then
    echo "Can't infer libpath"
    exit 1
fi

# Look for IntelliJ out directory
INTELLIJ_OUT_DIR=""
if [ -d "$PROJECT_ROOT/out/production" ]; then
    cd "$PROJECT_ROOT/out/production"
    for entry in *
    do
        if [ "$INTELLIJ_OUT_DIR" = "" ]; then
            INTELLIJ_OUT_DIR="$PROJECT_ROOT/out/production/$entry"
        else
            INTELLIJ_OUT_DIR="$INTELLIJ_OUT_DIR:$PROJECT_ROOT/out/production/$entry"
        fi
    done
fi

CLASSPATH="$LIBPATH/*:$LIBPATH/libs/*"

if [ ! "$INTELLIJ_OUT_DIR" = "" ]; then
    CLASSPATH="$CLASSPATH:$INTELLIJ_OUT_DIR"
else
    # Looking for a bin directory where eclipse compiles files
    if [ -d "$PROJECT_ROOT/bin" ]; then
        CLASSPATH="$CLASSPATH:$PROJECT_ROOT/bin"
    else
        echo "Failed to find the compiled classes. Set additional_classpath variable"
        exit 1
    fi
fi

java -classpath "$CLASSPATH" TwistRuntime
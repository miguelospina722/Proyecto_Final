#!/bin/sh

# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

# ----------------------------------------------------------------------------
# Apache Maven Wrapper startup script, version 3.2.0
# ----------------------------------------------------------------------------

set -euf

if [ -n "${ZSH_VERSION:-}" ]; then
  SCRIPT_SOURCE=${(%):-%x}
else
  SCRIPT_SOURCE=${BASH_SOURCE[0]:-$0}
fi

# shellcheck disable=SC2039
while [ -h "$SCRIPT_SOURCE" ]; do
  ls=$(ls -ld "$SCRIPT_SOURCE")
  link=${ls#*' -> '}
  if [ "${link#/}" = "$link" ]; then
    SCRIPT_SOURCE=$(dirname "$SCRIPT_SOURCE")"/$link"
  else
    SCRIPT_SOURCE=$link
  fi
  done

# shellcheck disable=SC2164
SCRIPT_DIR=$(cd -P "$(dirname "$SCRIPT_SOURCE")" >/dev/null 2>&1 && pwd)

JAVA_EXEC=""
if [ -n "${JAVA_HOME:-}" ]; then
  if [ -x "$JAVA_HOME/bin/java" ]; then
    JAVA_EXEC="$JAVA_HOME/bin/java"
  fi
else
  JAVA_EXEC="java"
fi

if [ -z "$JAVA_EXEC" ]; then
  echo "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH." >&2
  exit 1
fi

WRAPPER_JAR="$SCRIPT_DIR/.mvn/wrapper/maven-wrapper.jar"
WRAPPER_PROPERTIES="$SCRIPT_DIR/.mvn/wrapper/maven-wrapper.properties"
WRAPPER_LAUNCHER="org.apache.maven.wrapper.MavenWrapperMain"

if [ -z "${MVNW_REPOURL:-}" ]; then
  DOWNLOAD_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
else
  DOWNLOAD_URL="$MVNW_REPOURL/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
fi

if [ -f "$WRAPPER_PROPERTIES" ]; then
  while IFS='=' read -r key value; do
    case "$key" in
    wrapperUrl)
      DOWNLOAD_URL=$(echo "$value" | tr -d '"\r')
      ;;
    esac
  done <"$WRAPPER_PROPERTIES"
fi

if [ ! -f "$WRAPPER_JAR" ]; then
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "$DOWNLOAD_URL" -o "$WRAPPER_JAR"
  elif command -v wget >/dev/null 2>&1; then
    wget -q "$DOWNLOAD_URL" -O "$WRAPPER_JAR"
  else
    echo "ERROR: Unable to find curl or wget for downloading maven-wrapper.jar" >&2
    exit 1
  fi
fi

exec "$JAVA_EXEC" -classpath "$WRAPPER_JAR" "$WRAPPER_LAUNCHER" "$@"

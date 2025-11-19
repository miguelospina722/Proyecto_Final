@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM ----------------------------------------------------------------------------
@REM Apache Maven Wrapper startup batch script, version 3.2.0
@REM
@REM Optional ENV vars
@REM   MVNW_REPOURL - repo url base for downloading maven distribution
@REM   MVNW_USERNAME/MVNW_PASSWORD - user and password for downloading
@REM   MVNW_VERBOSE - true: enable verbose log; others: silence the output
@REM ----------------------------------------------------------------------------

@if "%DEBUG%"=="" @echo off
@rem set title of command window
title %0
@rem enable echoing by setting MAVEN_BATCH_ECHO to 'on'
@if "%MAVEN_BATCH_ECHO%"=="on"  echo %MAVEN_BATCH_ECHO%
@setlocal

@set ERROR_CODE=0

@REM set %HOME% to equivalent of $HOME
if "%HOME%"=="" (set "HOME=%HOMEDRIVE%%HOMEPATH%")

@REM Execute a user defined script before this one
if not "%MAVEN_SKIP_RC%"=="" goto skipRcPre
@set MAVEN_RC="%USERPROFILE%\.mavenrc_pre.bat"
if exist %MAVEN_RC% call %MAVEN_RC%
@set MAVEN_RC="%HOME%\.mavenrc_pre.bat"
if exist %MAVEN_RC% call %MAVEN_RC%
:skipRcPre

@set MAVEN_JAVA_EXE=""
if not "%JAVA_HOME%"=="" goto findJavaFromJavaHome

@set MAVEN_JAVA_EXE=java.exe
%MAVEN_JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.
goto error

:findJavaFromJavaHome
@set MAVEN_JAVA_EXE="%JAVA_HOME%\bin\java.exe"
if exist %MAVEN_JAVA_EXE% goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory.
echo JAVA_HOME = "%JAVA_HOME%"
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.
goto error

:execute
@set WRAPPER_JAR="%~dp0\.mvn\wrapper\maven-wrapper.jar"
@set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain
set WRAPPER_JAR_PATH=%WRAPPER_JAR:"=%
for %%i in ("%WRAPPER_JAR_PATH%") do set "WRAPPER_DIR=%%~dpi"
for %%i in ("%~dp0.") do set "MAVEN_PROJECTBASEDIR=%%~fi"
set MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:\=\%
set MAVEN_CMD_LINE_ARGS=%*

set WRAPPER_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"

FOR /F "usebackq tokens=1,2 delims==" %%A IN ("%~dp0\.mvn\wrapper\maven-wrapper.properties") DO (
    IF "%%A"=="wrapperUrl" SET WRAPPER_URL=%%B
)

set DOWNLOAD_URL=%WRAPPER_URL%
set DOWNLOAD_URL=%DOWNLOAD_URL:"=%
if not "%MVNW_REPOURL%"=="" (
    set DOWNLOAD_URL=%MVNW_REPOURL%/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar
)
set DOWNLOAD_URL=%DOWNLOAD_URL:"=%

set CURL_EXE=
for /f "tokens=* usebackq" %%i in (`where curl 2^>nul`) do (
    if not defined CURL_EXE set "CURL_EXE=%%i"
)
if defined CURL_EXE goto curlDownload

if exist %WRAPPER_JAR% goto runMaven

if not "%MVNW_VERBOSE%"=="" echo Couldn't find %WRAPPER_JAR%, downloading it ...

@set POWERSHELL_EXE=powershell.exe
@for %%i in (%SYSTEMROOT%\system32\WindowsPowerShell\v1.0\powershell.exe) do if "%%~fsi"=="%%i" set POWERSHELL_EXE=%%i
if exist "%POWERSHELL_EXE%" goto powershellDownload

echo ERROR: Unable to find PowerShell executable for downloading maven-wrapper.jar
goto error

:curlDownload
if not "%MVNW_VERBOSE%"=="" echo Downloading %DOWNLOAD_URL% with %CURL_EXE%
if not exist "%WRAPPER_DIR%" mkdir "%WRAPPER_DIR%"
"%CURL_EXE%" -fsSL "%DOWNLOAD_URL%" -o "%WRAPPER_JAR_PATH%"
if %ERRORLEVEL% equ 0 goto runMaven
echo ERROR: Failed to download %DOWNLOAD_URL%
goto error

:powershellDownload
set PS_DOWNLOAD_ARGS=
if not "%MVNW_USERNAME%"=="" (
    set PS_DOWNLOAD_ARGS=-Credential (New-Object System.Management.Automation.PSCredential("%MVNW_USERNAME%",(ConvertTo-SecureString "%MVNW_PASSWORD%" -AsPlainText -Force)))
)

"%POWERSHELL_EXE%" -Command "& { try {
  $webclient = New-Object System.Net.WebClient;
  if ($env:MVNW_USERNAME -and $env:MVNW_PASSWORD) {
    $webclient.Credentials = New-Object System.Net.NetworkCredential($env:MVNW_USERNAME, $env:MVNW_PASSWORD);
  }
  $scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition;
  $scriptDir = [System.IO.Path]::Combine($scriptDir, '.mvn', 'wrapper');
  if (!(Test-Path -Path $scriptDir)) {
    New-Item -ItemType Directory -Path $scriptDir -Force | Out-Null;
  }
  $jarPath = [System.IO.Path]::Combine($scriptDir, 'maven-wrapper.jar');
  $url = '%DOWNLOAD_URL%';
  $webclient.DownloadFile($url, $jarPath);
} catch {
  Write-Error $_.Exception.Message;
  exit 1;
} }"
if %ERRORLEVEL% neq 0 goto error

:runMaven
if not "%MVNW_VERBOSE%"=="" echo Running MavenWrapperMain with %MAVEN_JAVA_EXE%
%MAVEN_JAVA_EXE% -classpath %WRAPPER_JAR% "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" %WRAPPER_LAUNCHER% %MAVEN_CMD_LINE_ARGS%
if %ERRORLEVEL% neq 0 goto error
goto end

:error
set ERROR_CODE=1

:end
@endlocal & set "ERROR_CODE=%ERROR_CODE%"
if not "%MAVEN_SKIP_RC%"=="" goto skipRcPost
@set MAVEN_RC="%USERPROFILE%\.mavenrc_post.bat"
if exist %MAVEN_RC% call %MAVEN_RC% %*
@set MAVEN_RC="%HOME%\.mavenrc_post.bat"
if exist %MAVEN_RC% call %MAVEN_RC% %*
:skipRcPost

if %ERROR_CODE% neq 0 (
    if not "%MAVEN_BATCH_PAUSE%"=="" pause
    exit /b %ERROR_CODE%
)

exit /b 0

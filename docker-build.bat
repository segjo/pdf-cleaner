@echo off

setlocal enableextensions enabledelayedexpansion

set APPNAME=hackathon-demo
set BUILDCONTAINER=registry.invalid/%APPNAME%_build

set "denv="
for /f "usebackq delims=" %%a in ("environment") do (
    set "denv=!denv!-e %%a=!%%a! "
)

echo Building new docker image ...
docker build -t %BUILDCONTAINER% ./buildcontainer > ./buildcontainer/build.log && (
    echo done
) || (
    echo error building image
    exit /b 1
)

setlocal disabledelayedexpansion

if "%1"=="it" (
    docker run -it --rm %denv% --mount type=bind,src=%cd%,dst=/build --mount type=volume,src=%APPNAME%_m2_repo,dst=/root/.m2/repository --entrypoint /bin/bash %BUILDCONTAINER%
    rem docker run -it --rm %denv% --mount type=bind,src=%cd%,dst=/build --mount type=bind,src=%USERPROFILE%\.m2\repository,dst=/root/.m2/repository --entrypoint /bin/bash %BUILDCONTAINER%
) else (
    docker run --rm %denv% --mount type=bind,src=%cd%,dst=/build --mount type=volume,src=%APPNAME%_m2_repo,dst=/root/.m2/repository %BUILDCONTAINER% %*
    rem docker run --rm %denv% --mount type=bind,src=%cd%,dst=/build --mount type=bind,src=%USERPROFILE%\.m2\repository,dst=/root/.m2/repository %BUILDCONTAINER% %*
)
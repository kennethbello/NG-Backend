@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  MozuDataConnector startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and MOZU_DATA_CONNECTOR_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\MozuDataConnector-1.0-SNAPSHOT.jar;%APP_HOME%\lib\mozu-api-java-1.24.11.jar;%APP_HOME%\lib\mozu-java-security-1.0.2.jar;%APP_HOME%\lib\mozu-api-jobs-1.0.23.jar;%APP_HOME%\lib\spring-aop-4.0.5.RELEASE.jar;%APP_HOME%\lib\spring-beans-4.0.5.RELEASE.jar;%APP_HOME%\lib\spring-context-4.0.5.RELEASE.jar;%APP_HOME%\lib\spring-core-4.0.5.RELEASE.jar;%APP_HOME%\lib\spring-expression-4.0.5.RELEASE.jar;%APP_HOME%\lib\spring-web-4.0.5.RELEASE.jar;%APP_HOME%\lib\spring-webmvc-4.0.5.RELEASE.jar;%APP_HOME%\lib\spring-test-4.0.5.RELEASE.jar;%APP_HOME%\lib\mozu-api-core-1.24.11.jar;%APP_HOME%\lib\httpcore-4.3.3.jar;%APP_HOME%\lib\httpclient-4.3.6.jar;%APP_HOME%\lib\jasypt-1.9.2.jar;%APP_HOME%\lib\commons-cli-1.2.jar;%APP_HOME%\lib\log4j-1.2.17.jar;%APP_HOME%\lib\commons-lang3-3.2.1.jar;%APP_HOME%\lib\jstl-1.2.jar;%APP_HOME%\lib\spring-oxm-3.2.4.RELEASE.jar;%APP_HOME%\lib\spring-integration-core-3.0.2.RELEASE.jar;%APP_HOME%\lib\servlet-api-2.5.jar;%APP_HOME%\lib\quartz-2.2.1.jar;%APP_HOME%\lib\spring-batch-admin-manager-1.3.1.RELEASE.jar;%APP_HOME%\lib\commons-lang-2.6.jar;%APP_HOME%\lib\commons-io-2.4.jar;%APP_HOME%\lib\commons-configuration-1.10.jar;%APP_HOME%\lib\jackson-databind-2.2.3.jar;%APP_HOME%\lib\jackson-datatype-joda-2.2.3.jar;%APP_HOME%\lib\commons-codec-1.9.jar;%APP_HOME%\lib\joda-time-2.9.jar;%APP_HOME%\lib\commons-logging-1.1.3.jar;%APP_HOME%\lib\xstream-1.3.jar;%APP_HOME%\lib\jettison-1.1.jar;%APP_HOME%\lib\spring-retry-1.0.3.RELEASE.jar;%APP_HOME%\lib\c3p0-0.9.1.1.jar;%APP_HOME%\lib\aspectjrt-1.8.1.jar;%APP_HOME%\lib\aspectjweaver-1.8.1.jar;%APP_HOME%\lib\spring-batch-integration-1.3.1.RELEASE.jar;%APP_HOME%\lib\spring-integration-jmx-3.0.2.RELEASE.jar;%APP_HOME%\lib\spring-integration-http-3.0.2.RELEASE.jar;%APP_HOME%\lib\spring-integration-file-3.0.2.RELEASE.jar;%APP_HOME%\lib\commons-dbcp-1.4.jar;%APP_HOME%\lib\commons-fileupload-1.3.1.jar;%APP_HOME%\lib\jcl-over-slf4j-1.7.6.jar;%APP_HOME%\lib\commons-collections-3.2.1.jar;%APP_HOME%\lib\freemarker-2.3.15.jar;%APP_HOME%\lib\spring-batch-admin-resources-1.3.1.RELEASE.jar;%APP_HOME%\lib\jackson-mapper-asl-1.9.13.jar;%APP_HOME%\lib\jackson-annotations-2.2.3.jar;%APP_HOME%\lib\jackson-core-2.2.3.jar;%APP_HOME%\lib\commons-pool-1.5.4.jar;%APP_HOME%\lib\spring-context-support-3.2.9.RELEASE.jar;%APP_HOME%\lib\jackson-core-asl-1.9.13.jar;%APP_HOME%\lib\aopalliance-1.0.jar;%APP_HOME%\lib\spring-tx-3.2.9.RELEASE.jar;%APP_HOME%\lib\slf4j-api-1.7.6.jar;%APP_HOME%\lib\spring-batch-core-2.2.7.RELEASE.jar;%APP_HOME%\lib\xpp3_min-1.1.4c.jar;%APP_HOME%\lib\spring-jdbc-3.2.9.RELEASE.jar;%APP_HOME%\lib\slf4j-log4j12-1.7.6.jar;%APP_HOME%\lib\spring-batch-infrastructure-2.2.7.RELEASE.jar

@rem Execute MozuDataConnector
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %MOZU_DATA_CONNECTOR_OPTS%  -classpath "%CLASSPATH%" dataconnector.GettingStarted.Exercise91 %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable MOZU_DATA_CONNECTOR_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%MOZU_DATA_CONNECTOR_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega

@Echo Off
Setlocal Enabledelayedexpansion
set fn=aj.text
REM for /f "skip=1" %%i in (%fn%) do (aj
REM for /f "skip=1 tokens=1 delims= " %%i in (%fn%) do (
for /f "tokens=4 delims= " %%i in (%fn%) do (
REM   echo %%i
  protoc.exe --js_out=import_style=commonjs,binary:. %%i
REM   start cmd /k C:\Users\lenovo\batch\uninstall-fk.bat %%i
) 
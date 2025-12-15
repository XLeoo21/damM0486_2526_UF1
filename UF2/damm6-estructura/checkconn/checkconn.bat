@echo off
setlocal enabledelayedexpansion

:: Ruta del fitxer de log
set LOGFILE=%~dp0internet_log.txt

:: IP o domini per comprovar connexió (pots canviar-ho per google.com o 1.1.1.1)
set HOST=8.8.8.8

echo =============================== >> "%LOGFILE%"
echo Inici del registre: %date% %time% >> "%LOGFILE%"
echo =============================== >> "%LOGFILE%"

:LOOP
    ping -n 1 %HOST% >nul 2>&1
    if errorlevel 1 (
        set STATUS=❌
    ) else (
        set STATUS=✅
	echo [%date% %time%] %STATUS% >> "%LOGFILE%"
    )

    echo %STATUS%

    :: Espera 60 segons abans de tornar a comprovar
    timeout /t 5 >nul
goto LOOP
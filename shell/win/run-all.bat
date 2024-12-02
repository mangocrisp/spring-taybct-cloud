@echo off
start cmd /k "timeout -nobreak 2 && run-auth.bat"
start cmd /k "timeout -nobreak 2 && run-gateway.bat"
start cmd /k "timeout -nobreak 2 && run-module-system.bat"
start cmd /k "timeout -nobreak 2 && run-admin-file.bat"
start cmd /k "timeout -nobreak 2 && run-admin-log.bat"
start cmd /k "timeout -nobreak 2 && run-module-scheduling.bat"
exit
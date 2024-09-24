chcp 65001
TITLE=%0
set java=java
set jar=%1
set vm=-Dfile.encoding=utf-8 ^
-Dmaven.wagon.http.ssl.insecure=true ^
-Dmaven.wagon.http.ssl.allowall=true ^
--add-opens java.base/java.util=ALL-UNNAMED ^
--add-opens java.base/java.lang=ALL-UNNAMED ^
--add-opens java.base/java.lang.reflect=ALL-UNNAMED ^
--add-opens java.base/java.lang.invoke=ALL-UNNAMED ^
--add-opens java.base/java.lang.io=ALL-UNNAMED
set params=--spring.profiles.active=dev ^
--spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848 ^
--spring.cloud.nacos.config.server-addr=127.0.0.1:8848 ^
--spring.cloud.nacos.username=nacos ^
--spring.cloud.nacos.password=nacos ^
--server.port=8080
%java% %vm% -jar %jar% %params%
pause
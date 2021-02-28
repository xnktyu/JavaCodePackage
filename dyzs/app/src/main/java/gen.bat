@echo off

set command=C:\wangzhiting\work\code\client\tools\command.jar
set protoc=C:\wangzhiting\work\code\client\tools\protoc.exe
set inDir=C:\wangzhiting\work\aiv\server\qyqy\proto
set outDir=%cd%
set packageName=com.lys.protobuf

set filenames=+
set filenames=%filenames%+protocol_common.proto
set filenames=%filenames%+protocol_app.proto
set filenames=%filenames%+protocol_tap.proto

java -jar %command% genAndroidProto %protoc% %inDir% %outDir% %packageName% %filenames%

rem echo press any key to exit ...
rem pause>nul

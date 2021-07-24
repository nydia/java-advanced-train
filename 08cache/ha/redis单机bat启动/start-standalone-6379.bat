@echo off
title redis-server
set ENV_HOME="C:\tools\Redis-x64-5.0.10\redis-x64-5.0.10-standalone-6379"
C:
color 0a
cd %ENV_HOME%
redis-server.exe redis.conf
exit
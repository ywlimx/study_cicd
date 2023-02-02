#!/usr/bin/env bash
rm -rf /home/ec2-user/deploy/
mkdir -p /home/ec2-user/deploy/

if [ -f /home/ec2-user/deploy/bin/shutdown.pid ]; then
    kill -7 $(cat ./bin/shutdown.pid)
    echo "kill boot process $(cat ./bin/shutdown.pid)"
else
    echo "pid file is not exist...pass kill process"
fi
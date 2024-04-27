#!/bin/bash

chmod +x /home/azureuser/app1-1.0-SNAPSHOT.jar
tee /etc/systemd/system/myapp.service << EOF
[Unit]
Description=My Spring Boot Application
After=syslog.target

[Service]
User=azureuser
ExecStart=/usr/bin/java -jar /home/azureuser/app1-1.0-SNAPSHOT.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
EOF

systemctl start myapp
systemctl enable myapp
systemctl daemon-reload

#!/bin/bash

sudo tee /etc/systemd/system/myapp.service << EOF
[Unit]
Description=My Spring Boot Application
After=syslog.target

[Service]
User=azureuser
ExecStart=/usr/bin/java -jar /var/www/myapp/app.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
EOF

systemctl start myapp
systemctl enable myapp
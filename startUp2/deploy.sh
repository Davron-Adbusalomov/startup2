#!/bin/bash

sudo tee /etc/systemd/system/myapp.service << EOF
[Unit]
Description=My Spring Boot Application
After=syslog.target

[Service]
User=azureuser
ExecStart=/usr/bin/java -jar /var/www/myapp/*.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
EOF

sudo systemctl start myapp
sudo systemctl enable myapp

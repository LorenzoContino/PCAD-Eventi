#!/bin/bash

kubectl apply -f event-mysql-deployment.yaml,event-mysql-service.yaml,event-server-deployment.yaml,event-server-service.yaml,kubebuilder-default-networkpolicy.yaml,phpmyadmin-deployment.yaml,phpmyadmin-service.yaml
